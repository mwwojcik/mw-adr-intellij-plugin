package mw.adr.command;

import com.intellij.ide.util.EditorHelper;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import mw.adr.model.ADR;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.OptionalInt;

public class CreateADRCommand {

  private String templateContent;
  private String title;
  private PsiDirectory root;

  public CreateADRCommand(String templateContent, String title, PsiDirectory dir) {

    this.templateContent = templateContent;
    this.title = title;
    this.root = dir;
  }

  public static CreateADRCommand from(String templateContent, String title, PsiDirectory dir) {
    return new CreateADRCommand(templateContent, title, dir);
  }

  public PsiFile execute() {
    var adr = ADR.Builder.of()
            .withId(highestIndex() + 1)
            .withName(title)
            .withDate(dateFormatter().format(LocalDate.now()))
            .withAuthor(author())
            .withTemplate(templateContent)
            .build();

    var file = root.createFile(adr.filename());
    var document = PsiDocumentManager.getInstance(root.getProject()).getDocument(file);
    document.setText(adr.content());
    FileDocumentManager.getInstance().saveDocument(document);
    EditorHelper.openInEditor(file);
    return file;
  }

  private String author() {
    return System.getProperty("user.name");
  }

  private DateTimeFormatter dateFormatter() {
    return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
  }

  private int highestIndex() {
    OptionalInt highestIndex;
    highestIndex = Arrays.stream(root.getFiles()).mapToInt(CreateADRCommand::toInt).max();
    return (highestIndex.isPresent() ? highestIndex.getAsInt() : 0);
  }

  private static int toInt(PsiFile p) {
    String name = p.getName();
    // Extract the first 4 characters
    String id = name.substring(0, 4);
    try{
      return Integer.valueOf(id);
    }catch (Exception e){
      //ntd
    }
    return 0;
  }


}
