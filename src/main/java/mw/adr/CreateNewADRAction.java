package mw.adr;

import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.actions.CreateFromTemplateAction;
import com.intellij.ide.util.EditorHelper;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import mw.adr.command.CreateADRCommand;
import mw.adr.intelij.AppSettingsState;
import mw.adr.model.ADR;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CreateNewADRAction extends CreateFromTemplateAction<PsiFile> {

  public CreateNewADRAction() {
    super("Create ADR", "Creates ADR file", null);
  }

  public CreateNewADRAction(
      @Nls(capitalization = Nls.Capitalization.Title) String text,
      @Nls(capitalization = Nls.Capitalization.Sentence) String description,
      Icon icon) {
    super("Create ADR", "Creates ADR file", null);
  }

  @Nullable
  @Override
  protected PsiFile createFile(String name, String templateName, PsiDirectory dir) {
    var configComponent = ApplicationManager.getApplication().getService(AppSettingsState.class);
    var languagePropertyCommand = configComponent.toCommand();

    if (!isNameValid(name, languagePropertyCommand.getCategories().keySet())) {
      PsiFile file = dir.findFile("error.txt");
      if (file == null) {
        file = dir.createFile("error.txt");
      }
      var document = PsiDocumentManager.getInstance(dir.getProject()).getDocument(file);

      StringBuilder s = new StringBuilder();
      s.append("Nieprawidlowy wzorzec nazwy!\n");
      s.append("Powinno być=>" + ADR.NAMING_PATTERN + "\n");
      s.append(
          "Uwaga! Separatorem poziomu (ciągi trzyliterowe, pisane Capsem i oddzielone myślnikami) od TYTULU (dowolny bez znaków"
              + " specjalnych) jest "
              + "SPACJA.\n");
      s.append("Np: PRJ-SYS Wybór stosu Red Hat Fuse ; TCH-ESB Wybór bazy danych;\n");

      document.setText(s.toString());
      FileDocumentManager.getInstance().saveDocument(document);
      EditorHelper.openInEditor(file);
      return file;
    }

    var resTemplate =
        this.getClass()
            .getClassLoader()
            .getResourceAsStream(languagePropertyCommand.getLanguage() + "_adr_template.md");
    try {
      // Reader reader = new InputStreamReader(resTemplate, "UTF-8");
      String content = IOUtils.toString(resTemplate, StandardCharsets.UTF_8.name());

      // var content = new String(resTemplate.readAllBytes());
      return CreateADRCommand.from(content, name, dir).execute();

    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private boolean isNameValid(String name, Set<String> keys) {
    Pattern patt =
        Pattern.compile(
            "(((" + keys.stream().collect(Collectors.joining("|")) + ")(-[A-Z]{3})*)\\s(.+))");
    Matcher matcher = patt.matcher(name);
    return matcher.matches();
  }

  @Override
  protected void buildDialog(
      Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
    builder.setTitle("ADR").addKind("ADR", null, "adr_template.md");

    System.out.println();
  }

  @Override
  protected String getActionName(
      PsiDirectory directory, @NotNull String newName, String templateName) {
    return "CreateNewADRAction";
  }
}
