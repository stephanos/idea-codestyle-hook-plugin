package com.loops101.codestyle.ui;

import com.loops101.codestyle.settings.Settings;

import javax.swing.*;

public class SettingsForm {

    private JPanel rootComponent;
    private JLabel fileExtensionsIncludeLabel;
    private JTextArea fileExtensionsIncludeField;
    private JLabel fileExtensionsIncludeExample;


    public JPanel getRootComponent() {
        return rootComponent;
    }

    public boolean isModified(Settings settings) {
        return fileExtensionsIncludeField.getText() != null ?
                !fileExtensionsIncludeField.getText().equals(settings.getFileExtensionsInclude()) : settings.getFileExtensionsInclude() != null;
    }

    public String getFileExtensionsIncludesFilter() {
        return fileExtensionsIncludeField.getText();
    }

    public void setFileExtensionsIncludesFilter(final String fileExtensionsIncludesFilter) {
        this.fileExtensionsIncludeField.setText(fileExtensionsIncludesFilter);
    }

}
