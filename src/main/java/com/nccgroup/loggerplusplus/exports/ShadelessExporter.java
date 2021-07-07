package com.nccgroup.loggerplusplus.exports;

import com.coreyd97.BurpExtenderUtilities.Preferences;
import com.google.gson.JsonObject;
import com.nccgroup.loggerplusplus.LoggerPlusPlus;
import com.nccgroup.loggerplusplus.filter.logfilter.LogFilter;
import com.nccgroup.loggerplusplus.filter.parser.ParseException;
import com.nccgroup.loggerplusplus.logentry.LogEntry;
import com.nccgroup.loggerplusplus.logentry.LogEntryField;
import com.nccgroup.loggerplusplus.logentry.Status;
import com.nccgroup.loggerplusplus.util.FileObject;
import com.nccgroup.loggerplusplus.util.Globals;
import com.nccgroup.loggerplusplus.util.HttpFilePool;
import com.nccgroup.loggerplusplus.util.HttpPacketPool;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ShadelessExporter extends AutomaticLogExporter implements ExportPanelProvider, ContextMenuExportProvider {

    ArrayList<LogEntry> pendingEntries;
    LogFilter logFilter;
    private List<LogEntryField> fields;
    private ScheduledFuture indexTask;

    private final ScheduledExecutorService executorService;
    private final ShadelessExporterControlPanel controlPanel;

    private Logger logger = LogManager.getLogger(this);

    protected ShadelessExporter(ExportController exportController, Preferences preferences) {
        super(exportController, preferences);
        this.fields = new ArrayList<>(preferences.getSetting(Globals.PREF_PREVIOUS_ELASTIC_FIELDS));
        executorService = Executors.newScheduledThreadPool(1);

        if ((boolean) preferences.getSetting(Globals.PREF_ELASTIC_AUTOSTART_GLOBAL)
                || (boolean) preferences.getSetting(Globals.PREF_ELASTIC_AUTOSTART_PROJECT)) {
            //Autostart exporter.
            try {
                this.exportController.enableExporter(this);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(LoggerPlusPlus.instance.getLoggerFrame(), "Could not start elastic exporter: " +
                        e.getMessage() + "\nSee the logs for more information.", "Shadeless Exporter", JOptionPane.ERROR_MESSAGE);
                logger.error("Could not automatically start elastic exporter:", e);
            }
        }
        controlPanel = new ShadelessExporterControlPanel(this);
    }

    @Override
    void setup() throws Exception {
        if (this.fields == null || this.fields.isEmpty())
            throw new Exception("No fields configured for export.");

        String projectPreviousFilterString = preferences.getSetting(Globals.PREF_ELASTIC_FILTER_PROJECT_PREVIOUS);
        String filterString = preferences.getSetting(Globals.PREF_ELASTIC_FILTER);

        if (!Objects.equals(projectPreviousFilterString, filterString)) {
            //The current filter isn't what we used to export last time.
            int res = JOptionPane.showConfirmDialog(LoggerPlusPlus.instance.getLoggerFrame(),
                    "Heads up! Looks like the filter being used to select which logs to export to " +
                            "ShadelessSearch has changed since you last ran the exporter for this project.\n" +
                            "Do you want to continue?", "ShadelessSearch Export Log Filter", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (res == JOptionPane.NO_OPTION) {
                throw new Exception("Export cancelled.");
            }
        }

        if (!StringUtils.isBlank(filterString)) {
            try {
                logFilter = new LogFilter(exportController.getLoggerPlusPlus().getLibraryController(), filterString);
            } catch (ParseException ex) {
                logger.error("The log filter configured for the Shadeless exporter is invalid!", ex);
            }
        }

        pendingEntries = new ArrayList<>();
        int delay = preferences.getSetting(Globals.PREF_SHADELESS_DELAY);
        indexTask = executorService.scheduleAtFixedRate(this::indexPendingEntries, delay, delay, TimeUnit.SECONDS);
    }

    @Override
    public void exportNewEntry(final LogEntry logEntry) {
        if(logEntry.getStatus() == Status.PROCESSED) {
            if (logFilter != null && !logFilter.matches(logEntry)) return;
            pendingEntries.add(logEntry);
        }
    }

    @Override
    public void exportUpdatedEntry(final LogEntry updatedEntry) {
        if(updatedEntry.getStatus() == Status.PROCESSED) {
            if (logFilter != null && !logFilter.matches(updatedEntry)) return;
            pendingEntries.add(updatedEntry);
        }
    }

    @Override
    void shutdown() throws Exception {
        if(this.indexTask != null){
            indexTask.cancel(true);
        }
        this.pendingEntries = null;
    }

    @Override
    public JComponent getExportPanel() {
        return controlPanel;
    }

    @Override
    public JMenuItem getExportEntriesMenuItem(List<LogEntry> entries) {
        return null;
    }

    private ArrayList<JsonObject> convertLogEntriesToJsons(ArrayList<LogEntry> entries, String project) {
        String codeName = this.preferences.getSetting(Globals.PREF_CODE_NAME);
        ArrayList<JsonObject> result = new ArrayList<>();
        for  (LogEntry entry : entries) {
            JsonObject json = entry.toJsonObject(project, codeName);
            result.add(json);
        }
        return result;
    }

    public void debug(String msg) {
        var callbacks = this.getExportController().getLoggerPlusPlus().callbacks;
        PrintWriter stderr = new PrintWriter(callbacks.getStdout(), true);
        stderr.println(msg);
    }

    private void indexPendingEntries(){
        try {
            if (this.pendingEntries.size() == 0) return;
            ArrayList<LogEntry> entriesInBulk;
            synchronized (pendingEntries) {
                entriesInBulk = new ArrayList<>(pendingEntries);
                pendingEntries.clear();
            }
            String packetUrl = preferences.getSetting(Globals.PREF_SHADELESS_PACKETS_URL);
            String fileUrl = preferences.getSetting(Globals.PREF_SHADELESS_FILES_URL);
            String fileCheckUrl = preferences.getSetting(Globals.PREF_SHADELESS_FILES_CHECK_URL);
            String project = preferences.getSetting(Globals.PREF_SHADELESS_PROJECT);

            ArrayList<JsonObject> entriesJson = convertLogEntriesToJsons(entriesInBulk, project);
            HttpPacketPool packetsPool = new HttpPacketPool(packetUrl, entriesJson);
            packetsPool.send();

            // Sorry for passing data like this, but it is for performance purpose :(
            // Creating new class for these files would consume too much RAM
            HttpFilePool filesPool = new HttpFilePool(fileUrl, fileCheckUrl, project, entriesInBulk);
            filesPool.sendAllEntries();

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            pendingEntries.clear();
        }
    }

    public ExportController getExportController() {
        return this.exportController;
    }

    public List<LogEntryField> getFields() {
        return fields;
    }

    public void setFields(List<LogEntryField> fields) {
        preferences.setSetting(Globals.PREF_PREVIOUS_ELASTIC_FIELDS, fields);
        this.fields = fields;
    }
}
