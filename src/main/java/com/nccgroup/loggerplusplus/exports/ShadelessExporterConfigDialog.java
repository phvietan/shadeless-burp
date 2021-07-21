package com.nccgroup.loggerplusplus.exports;

import com.coreyd97.BurpExtenderUtilities.Alignment;
import com.coreyd97.BurpExtenderUtilities.ComponentGroup;
import com.coreyd97.BurpExtenderUtilities.PanelBuilder;
import com.coreyd97.BurpExtenderUtilities.Preferences;
import com.nccgroup.loggerplusplus.LoggerPlusPlus;
import com.nccgroup.loggerplusplus.filter.logfilter.LogFilter;
import com.nccgroup.loggerplusplus.filter.parser.ParseException;
import com.nccgroup.loggerplusplus.filterlibrary.FilterLibraryController;
import com.nccgroup.loggerplusplus.logentry.LogEntryField;
import com.nccgroup.loggerplusplus.util.Globals;
import com.nccgroup.loggerplusplus.util.MoreHelp;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;

import static com.nccgroup.loggerplusplus.util.Globals.*;

public class ShadelessExporterConfigDialog extends JDialog {

    ShadelessExporterConfigDialog(Frame owner, ShadelessExporter elasticExporter){
        super(owner, "Shadeless Exporter Configuration", true);

        this.setLayout(new BorderLayout());
        Preferences preferences = elasticExporter.getPreferences();

        JTextField packetUrlField = PanelBuilder.createPreferenceTextField(preferences, PREF_SHADELESS_PACKETS_URL);
        JTextField fileUrlField = PanelBuilder.createPreferenceTextField(preferences, PREF_SHADELESS_FILES_URL);
        JTextField fileCheckUrlField = PanelBuilder.createPreferenceTextField(preferences, PREF_SHADELESS_FILES_CHECK_URL);
        JTextField projectField = PanelBuilder.createPreferenceTextField(preferences, PREF_SHADELESS_PROJECT);
        JTextField codeName = PanelBuilder.createPreferenceTextField(preferences, PREF_CODE_NAME);

        JSpinner delaySpinner = PanelBuilder.createPreferenceSpinner(preferences, PREF_SHADELESS_DELAY);
        ((SpinnerNumberModel) delaySpinner.getModel()).setMaximum(99999);
        ((SpinnerNumberModel) delaySpinner.getModel()).setMinimum(10);
        ((SpinnerNumberModel) delaySpinner.getModel()).setStepSize(10);

        ComponentGroup connectionGroup = new ComponentGroup(ComponentGroup.Orientation.VERTICAL, "Connection");
        connectionGroup.addComponentWithLabel("Packet url: ", packetUrlField);
        connectionGroup.addComponentWithLabel("File url: ", fileUrlField);
        connectionGroup.addComponentWithLabel("File check url: ", fileCheckUrlField);
        connectionGroup.addComponentWithLabel("Project: ", projectField);
        connectionGroup.addComponentWithLabel("Codename: ", codeName);
        connectionGroup.addComponentWithLabel("Upload frequency (in seconds): ", delaySpinner);

//        ComponentGroup miscGroup = new ComponentGroup(ComponentGroup.Orientation.VERTICAL, "Misc");
//        miscGroup.add(PanelBuilder.build(new Component[][]{
//                new JComponent[]{new JLabel("Upload Frequency (Seconds): "), elasticDelaySpinner},
//        }, new int[][]{
//                new int[]{0, 1},
//        }, Alignment.FILL, 1, 1));

        this.add(PanelBuilder.build(new JComponent[][]{
                new JComponent[]{connectionGroup},
//                new JComponent[]{miscGroup}
        }, new int[][]{
                new int[]{1},
                new int[]{1},
                new int[]{1},
        }, Alignment.CENTER, 1.0, 1.0, 5, 5), BorderLayout.CENTER);

        this.setMinimumSize(new Dimension(600, 200));

        this.pack();
        this.setResizable(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ShadelessExporterConfigDialog.this.dispose();
                super.windowClosing(e);
            }
        });
    }
}
