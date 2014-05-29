package com.loops101.ui;

import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import org.jetbrains.annotations.NotNull;

public class CodestyleCheckinHandlerFactory extends CheckinHandlerFactory {

    @Override
    @NotNull
    public CheckinHandler createHandler(final CheckinProjectPanel panel, CommitContext commitContext) {
        return new CodestyleBeforeCheckinHandler(panel.getProject(), panel);
    }
}