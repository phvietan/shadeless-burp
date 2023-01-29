package com.nccgroup.loggerplusplus.exports;

import com.coreyd97.BurpExtenderUtilities.Preferences;
import com.google.gson.JsonObject;
import com.nccgroup.loggerplusplus.LoggerPlusPlus;
import com.nccgroup.loggerplusplus.filter.logfilter.LogFilter;
import com.nccgroup.loggerplusplus.logentry.LogEntry;
import com.nccgroup.loggerplusplus.logentry.LogEntryField;
import com.nccgroup.loggerplusplus.logentry.Status;
import com.nccgroup.loggerplusplus.util.Globals;
import com.nccgroup.loggerplusplus.util.HttpFilePool;
import com.nccgroup.loggerplusplus.util.HttpPacketPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ShadelessExporter extends AutomaticLogExporter implements ExportPanelProvider, ContextMenuExportProvider {

    ArrayList<LogEntry> pendingEntries;
    LogFilter logFilter;
    private ScheduledFuture indexTask;

    private final ScheduledExecutorService executorService;
    private final ShadelessExporterControlPanel controlPanel;

    private Logger logger = LogManager.getLogger(this);

    protected ShadelessExporter(ExportController exportController, Preferences preferences) {
        super(exportController, preferences);
        executorService = Executors.newScheduledThreadPool(1);
        controlPanel = new ShadelessExporterControlPanel(this);
    }

    @Override
    void setup() throws Exception {
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
            String packetUrl = preferences.getSetting(Globals.PREF_SHADELESS_URL);
            String project = preferences.getSetting(Globals.PREF_SHADELESS_PROJECT);

            ArrayList<JsonObject> entriesJson = convertLogEntriesToJsons(entriesInBulk, project);
            HttpPacketPool packetsPool = new HttpPacketPool(packetUrl + "/api/burp/packets", entriesJson);
            packetsPool.send();

            HttpFilePool filesPool = new HttpFilePool(packetUrl + "/api", project, entriesInBulk);
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

}
