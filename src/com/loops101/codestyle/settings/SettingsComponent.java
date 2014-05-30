package com.loops101.codestyle.settings;

import com.intellij.openapi.components.*;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.loops101.codestyle.ui.SettingsForm;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

@State(name = "CodeStyleHookSettings",
        storages = {@Storage(id = "other", file = StoragePathMacros.PROJECT_FILE)})
public class SettingsComponent implements Configurable, ProjectComponent, PersistentStateComponent<Settings> {

    @NotNull
    private Settings settings;

    @Nullable
    private SettingsForm form;


    public SettingsComponent() {
    }

    public static SettingsComponent getInstance(Project project) {
        return project.getComponent(SettingsComponent.class);
    }


    @NotNull
    public String getComponentName() {
        return "CodeStyleHookComponent";
    }

    @Nls
    public String getDisplayName() {
        return "Code Style Hook";
    }

    @Nullable
    @NonNls
    public String getHelpTopic() {
        return null;
    }

    @NotNull
    public JComponent createComponent() {
        if (form == null) {
            form = new SettingsForm();
            form.setFileExtensionsIncludesFilter(settings.getFileExtensionsInclude());
        }
        return form.getRootComponent();
    }

    @Override
    public boolean isModified() {
        return form != null && form.isModified(settings);
    }

    public void apply() throws ConfigurationException {
        if (form != null) {
            settings.setFileExtensionsInclude(form.getFileExtensionsIncludesFilter());
        }
    }

    public void reset() {
        if (form != null) {
            form.setFileExtensionsIncludesFilter(settings.getFileExtensionsInclude());
        }
    }

    public void disposeUIResources() {
        form = null;
    }

    @NotNull
    public Settings getState() {
        return settings;
    }

    public void loadState(@NotNull Settings state) {
        settings = state;
    }

    @Override
    public void projectOpened() {
    }

    @Override
    public void projectClosed() {
    }

    @Override
    public void initComponent() {
        if (settings == null) {
            settings = new Settings();
        }
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    public Settings getSettings() {
        return settings;
    }
}