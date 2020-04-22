/** */
package mw.adr.command;

import com.intellij.ide.util.EditorHelper;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import mw.adr.model.ADLDocument;
import mw.adr.model.ADLRecord;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Subcommand the create index, numbered Architecture Description Record (ADR).
 *
 * @author adoble
 */
public class CreateADLCommand {

  private PsiDirectory root;

  public CreateADLCommand(PsiDirectory root) {

    this.root = root;
  }

  public static CreateADLCommand from(PsiDirectory root) {
    return new CreateADLCommand(root);
  }

  public void execute() {
    var adl = ADLDocument.from(adrRecords());

    var file = root.findFile(adl.filename());

    if (file == null) {
      file = root.createFile(adl.filename());
    }

    var document = PsiDocumentManager.getInstance(root.getProject()).getDocument(file);

    document.setText(adl.toContent());
    FileDocumentManager.getInstance().saveDocument(document);
    EditorHelper.openInEditor(file);
  }

  private List<ADLRecord> adrRecords() {
    return Arrays.stream(root.getFiles())
        .filter(it -> isRecord(it))
        .map(it -> ADLRecord.from(it))
        .sorted()
        .collect(Collectors.toList());
  }

  private boolean isRecord(PsiFile it) {
    Pattern patt = ADLRecord.FILE_NAME_PATTERN;
    Matcher matcher = patt.matcher(it.getName());

    if (!matcher.matches()) {
      return false;
    }

    var ids = it.getName().substring(0, 4);

    int i = 0;

    try {
      i = Integer.valueOf(ids);
    } catch (NumberFormatException e) {
      return false;
    }
    if (i == 0) {
      // index=0 only for adl
      return false;
    }
    return true;
  }


}
