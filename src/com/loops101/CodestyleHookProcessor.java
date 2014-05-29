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

public class CodestyleHookProcessor {

    private static final String REFORMAT_COMMAND_NAME = message("process.reformat.code.before.commit");
    private static final String REARRANGE_COMMAND_NAME = message("process.rearrange.code.before.commit");
    private static final String OPTIMIZE_IMPORTS_COMMAND_NAME = message("process.optimize.imports.before.commit");

    private static final List<String> INCLUDED_FILE_EXTENSIONS = Arrays.asList("java", "groovy", "xml");


    public static void reformat(Project project, Collection<VirtualFile> files, Runnable performCheckoutAction) {
        PsiFile[] psiFiles = filter(BeforeCheckinHandlerUtil.getPsiFiles(project, files));

        new ReformatCodeProcessor(project, psiFiles, REFORMAT_COMMAND_NAME, performCheckoutAction, true).run();

        // TODO:
        //new RearrangeCodeProcessor(
        //        myProject, psiFiles, COMMAND_NAME, performCheckoutAction
        //).run();

        // TODO:
        // new OptimizeImportsProcessor(myProject, psiFiles, COMMAND_NAME, performCheckoutAction).run();
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
