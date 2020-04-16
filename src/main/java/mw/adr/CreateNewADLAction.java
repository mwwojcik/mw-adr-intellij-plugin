package mw.adr;

import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.actions.CreateFromTemplateAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import mw.adr.command.CreateADLCommand;
import mw.adr.command.CreateNewADRCommand;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;

public class CreateNewADLAction extends CreateFromTemplateAction<PsiFile>{

    public CreateNewADLAction(){
        super("New ADL File", "Creates new ADL file", null);
    }

    public CreateNewADLAction(@Nls(capitalization = Nls.Capitalization.Title) String text, @Nls(capitalization = Nls.Capitalization.Sentence) String description, Icon icon) {
        super("New ADL File", "Creates new ADL file", null);
    }

    @Nullable
  @Override
  protected PsiFile createFile(String name, String templateName, PsiDirectory dir) {
        return CreateADLCommand.from(dir).execute();
  }

  @Override
  protected void buildDialog(
      Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
      builder
              .setTitle("ADL")
              .addKind("ADL", null, "adr_template.md");
    System.out.println();
  }

  @Override
  protected String getActionName(
      PsiDirectory directory, @NotNull String newName, String templateName) {
    return "CreateNewADLAction";
  }
}


