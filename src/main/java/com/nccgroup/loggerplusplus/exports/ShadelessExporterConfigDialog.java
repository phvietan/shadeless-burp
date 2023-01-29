package com.nccgroup.loggerplusplus.exports;

import com.coreyd97.BurpExtenderUtilities.Alignment;
import com.coreyd97.BurpExtenderUtilities.ComponentGroup;
import com.coreyd97.BurpExtenderUtilities.PanelBuilder;
import com.coreyd97.BurpExtenderUtilities.Preferences;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.nccgroup.loggerplusplus.util.Globals.*;

public class ShadelessExporterConfigDialog extends JDialog {

    ShadelessExporterConfigDialog(Frame owner, ShadelessExporter elasticExporter){
        super(owner, "Shadeless Exporter Configuration", true);

        this.setLayout(new BorderLayout());
        Preferences preferences = elasticExporter.getPreferences();

        JTextField packetUrlField = PanelBuilder.createPreferenceTextField(preferences, PREF_SHADELESS_URL);
        JTextField projectField = PanelBuilder.createPreferenceTextField(preferences, PREF_SHADELESS_PROJECT);
        JTextField codeName = PanelBuilder.createPreferenceTextField(preferences, PREF_CODE_NAME);

        JSpinner delaySpinner = PanelBuilder.createPreferenceSpinner(preferences, PREF_SHADELESS_DELAY);
        ((SpinnerNumberModel) delaySpinner.getModel()).setMaximum(99999);
        ((SpinnerNumberModel) delaySpinner.getModel()).setMinimum(10);
        ((SpinnerNumberModel) delaySpinner.getModel()).setStepSize(10);

        ComponentGroup connectionGroup = new ComponentGroup(ComponentGroup.Orientation.VERTICAL, "Connection");
        connectionGroup.addComponentWithLabel("Shadeless url: ", packetUrlField);
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
