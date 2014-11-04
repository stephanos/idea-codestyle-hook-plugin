package com.loops101.codestyle;

import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilCore;
import com.loops101.codestyle.settings.Settings;
import com.loops101.codestyle.settings.SettingsComponent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.intellij.codeInsight.CodeInsightBundle.message;

public class Processor {

    private static final String REFORMAT_COMMAND_NAME = message("process.reformat.code.before.commit");


    public static void run(final Project project, final Collection<VirtualFile> files, final Runnable performCommit) {
        Settings settings = SettingsComponent.getInstance(project).getSettings();
        PsiFile[] psiFiles = filter(Util.getPsiFiles(project, files), settings);
        new ReformatCodeProcessor(project, psiFiles, REFORMAT_COMMAND_NAME, performCommit, true).run();
    }


    private static PsiFile[] filter(PsiFile[] psiFiles, Settings settings) {
        ArrayList<PsiFile> result = new ArrayList<PsiFile>();
        List<String> fileExtensionsInclude = Arrays.asList(settings.getFileExtensionsInclude().split("\n"));

        for (PsiFile psiFile : psiFiles) {
            if (includeFile(psiFile.getVirtualFile(), fileExtensionsInclude)) {
                result.add(psiFile);
            }
        }

        return PsiUtilCore.toPsiFileArray(result);
    }

    private static boolean includeFile(VirtualFile virtualFile, List<String> fileExtensionsInclude) {
        String fileExt = virtualFile.getExtension();
        return fileExtensionsInclude.contains(fileExt);
    }


    private static class Util {

        private static final String CHECKING_HANDLER_UTIL_PCKG = "com.intellij.openapi.vcs.checkin.";

        static PsiFile[] getPsiFiles(Project project, Collection<VirtualFile> files) {
            try {
                return getPsiFiles("BeforeCheckinHandlerUtil", project, files);
            } catch (Exception e1) {
                try {
                    return getPsiFiles("CheckinHandlerUtil", project, files);
                } catch (Exception e2) {
                    throw new RuntimeException("unable to invoke (Before)CheckinHandlerUtil", e2);
                }
            }
        }

        private static PsiFile[] getPsiFiles(String source, Project project, Collection<VirtualFile> files) throws Exception {
            Class<?> utilClass = Class.forName(CHECKING_HANDLER_UTIL_PCKG + source);
            Method method = utilClass.getMethod("getPsiFiles", Project.class, Collection.class);
            return (PsiFile[]) method.invoke(null, project, files);
        }
    }
}
