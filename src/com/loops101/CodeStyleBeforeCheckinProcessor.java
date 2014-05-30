package com.loops101;

import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.checkin.BeforeCheckinHandlerUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.intellij.codeInsight.CodeInsightBundle.message;

public class CodeStyleBeforeCheckinProcessor {

    private static final String REFORMAT_COMMAND_NAME = message("process.reformat.code.before.commit");

    private static final List<String> INCLUDED_FILE_EXTENSIONS = Arrays.asList("java", "groovy", "xml");


    public static void run(final Project project, final Collection<VirtualFile> files, final Runnable performCommit) {
        final PsiFile[] psiFiles = filter(BeforeCheckinHandlerUtil.getPsiFiles(project, files));
        new ReformatCodeProcessor(project, psiFiles, REFORMAT_COMMAND_NAME, performCommit, true).run();
    }


    private static PsiFile[] filter(PsiFile[] psiFiles) {
        ArrayList<PsiFile> result = new ArrayList<PsiFile>();

        for (PsiFile psiFile : psiFiles) {
            if (INCLUDED_FILE_EXTENSIONS.contains(psiFile.getVirtualFile().getExtension())) {
                result.add(psiFile);
            }
        }

        return PsiUtilCore.toPsiFileArray(result);
    }

}
