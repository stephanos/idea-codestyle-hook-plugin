package com.loops101.codestyle.ui;

import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import org.jetbrains.annotations.NotNull;

public class CheckinHandlerFactory extends com.intellij.openapi.vcs.checkin.CheckinHandlerFactory {

    @Override
    @NotNull
    public CheckinHandler createHandler(final CheckinProjectPanel panel, CommitContext commitContext) {
        return new BeforeCheckinHandler(panel.getProject(), panel);
    }

}