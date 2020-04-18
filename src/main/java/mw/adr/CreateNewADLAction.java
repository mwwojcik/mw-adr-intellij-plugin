package mw.adr;

import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import mw.adr.command.CreateADLCommand;
import mw.adr.intelij.SimplifiedCreateFromTemplateAction;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class CreateNewADLAction extends SimplifiedCreateFromTemplateAction<PsiFile> {

    public CreateNewADLAction(){
        super("Create ADL", "Creates ADL file", null);
    }

    public CreateNewADLAction(@Nls(capitalization = Nls.Capitalization.Title) String text, @Nls(capitalization = Nls.Capitalization.Sentence) String description, Icon icon) {
        super("Create ADL", "Creates ADL file", null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        var selectedElements = ProjectView.getInstance(e.getProject()).getCurrentProjectViewPane().getSelectedElements();
        if(selectedElements!=null && selectedElements.length>0&&isDir(selectedElements[0])){
            var dir=(PsiDirectory)selectedElements[0];
            ApplicationManager.getApplication().runWriteAction(()->CreateADLCommand.from(dir).execute());
        }
    }

    private Boolean isDir(Object param) {
     return param instanceof PsiDirectory ;
     }




}


