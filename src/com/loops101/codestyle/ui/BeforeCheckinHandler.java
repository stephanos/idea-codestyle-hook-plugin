package com.loops101.codestyle.ui;

import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinMetaHandler;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
import com.intellij.openapi.vfs.VirtualFile;
import com.loops101.codestyle.Processor;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

public class BeforeCheckinHandler extends CheckinHandler implements CheckinMetaHandler {

    private final Project myProject;
    private boolean applyCodeStyle = true;
    private final CheckinProjectPanel myPanel;


    public BeforeCheckinHandler(final Project project, final CheckinProjectPanel panel) {
        myProject = project;
        myPanel = panel;
    }


    @Override
    @Nullable
    public RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
        final JCheckBox codestyleBox = new JCheckBox("Code Style Hook");
        codestyleBox.setEnabled(false);
        codestyleBox.setSelected(true);

        return new RefreshableOnComponent() {
            @Override
            public JComponent getComponent() {
                final JPanel panel = new JPanel(new GridLayout(1, 0));
                panel.add(codestyleBox);
                return panel;
            }

            @Override
            public void refresh() {
            }

            @Override
            public void saveState() {
            }

            @Override
            public void restoreState() {
            }
        };

    }

    @Override
    public void runCheckinHandlers(final Runnable finishAction) {
        final Runnable performCheckoutAction = new Runnable() {
            @Override
            public void run() {
                FileDocumentManager.getInstance().saveAllDocuments();
                finishAction.run();
            }
        };

        //if (applyCodeStyle) {
        final Collection<VirtualFile> files = myPanel.getVirtualFiles();
        Processor.run(myProject, files, performCheckoutAction);
        //} else {
        //    performCheckoutAction.run();
        //}
    }

}
