package mw.adr;

import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.actions.CreateFromTemplateAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import mw.adr.command.CreateNewADRCommand;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;

public class CreateNewADRAction extends CreateFromTemplateAction<PsiFile>{

    public CreateNewADRAction(){
        super("New ADR File", "Creates new ADR file", null);
    }

    public CreateNewADRAction(@Nls(capitalization = Nls.Capitalization.Title) String text, @Nls(capitalization = Nls.Capitalization.Sentence) String description, Icon icon) {
        super("New ADR File", "Creates new ADR file", null);
    }

    @Nullable
  @Override
  protected PsiFile createFile(String name, String templateName, PsiDirectory dir) {
        //dir.

        var resTemplate = this.getClass().getClassLoader().getResourceAsStream("adr_template.md");
        try {
            var content = new String(resTemplate.readAllBytes());
            return CreateNewADRCommand.from(content,name,dir).execute();

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        //final PsiFileFactory factory = PsiFileFactory.getInstance(project);
        //final PsiFile file = factory.createFileFromText(language, text);
        //return null;
  }

  @Override
  protected void buildDialog(
      Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
      builder
              .setTitle("ADR")
              .addKind("ADR", null, "adr_template.md");

    System.out.println();
  }

  @Override
  protected String getActionName(
      PsiDirectory directory, @NotNull String newName, String templateName) {
    return "CreateNewADRAction";
  }
}


