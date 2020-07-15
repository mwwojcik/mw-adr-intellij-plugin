package mw.adr.model;

import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ADLRecord implements Comparable<ADLRecord> {
  public static final Pattern FILE_NAME_PATTERN =
      Pattern.compile("([0-9]{4})-((PRJ|TCH)(-[A-Z]{3})*).+");
  public static final String WFL_ADR_DATE = "wfl-adr-date";
  public static final String WFL_ADR_TITLE = "wfl-adr-title";
  public static final String WFL_ADR_AUTHOR = "wfl-adr-author";
  private final int id;
  private final RecordType type;
  private final String category;
  private final String idS;
  private final String filename;
  private final String date;
  private final String title;
  private final String author;

  private ADLRecord(int id, String idS, String filename, String date, String title, String author) {
    this.id = id;
    this.idS = idS;
    this.filename = filename;
    this.date = date;
    this.title = title;
    this.author = author;
    this.type = type(filename);
    this.category = category(filename);
  }

  private RecordType type(String filename) {
    Matcher matcher = FILE_NAME_PATTERN.matcher(filename);
    matcher.matches();
    var txt = matcher.group(2);

    if (txt.contains("-")) {
      return RecordType.valueOf(txt.split("-")[0]);
    }
    return RecordType.valueOf(txt);
  }

  private String category(String filename) {
    Matcher matcher = FILE_NAME_PATTERN.matcher(filename);
    matcher.matches();
    return matcher.group(2);
  }

  public static ADLRecord from(PsiFile file) {
    List<String> lines = readLines(file);
    return new ADLRecord(
        toId(file), toIdS(file), file.getName(), toDate(lines), toTitle(lines), toAuthor(lines));
  }

  private static List<String> readLines(PsiFile path) {

    List<String> lines = new ArrayList<>();
    var document = PsiDocumentManager.getInstance(path.getProject()).getDocument(path);

    String[] split = document.getText().split("\n");

    return Arrays.asList(split);
  }

  private static String toDate(List<String> contentLines) {
    String s = withPattern(contentLines, WFL_ADR_DATE).orElse("");
    return s.substring(s.indexOf(":") + 1);
  }

  private static String toTitle(List<String> contentLines) {
    String s = withPattern(contentLines, WFL_ADR_TITLE).orElse("");
    return s.substring(s.indexOf(":") + 1);
  }

  private static String toAuthor(List<String> contentLines) {
    String s = withPattern(contentLines, WFL_ADR_AUTHOR).orElse("");
    return s.substring(s.indexOf(":") + 1);
  }

  private static int toId(PsiFile p) {
    String id = toIdS(p);
    return new Integer(id);
  }

  private static String toIdS(PsiFile p) {
    String name = p.getName();
    // Extract the first 4 characters
    return name.substring(0, 4);
  }

  private static Optional<String> withPattern(List<String> lines, String pattern) {
    return lines.stream().filter(it -> it.contains(pattern)).findFirst();
  }

  @Override
  public int compareTo(ADLRecord o) {
    return this.category.compareTo(o.category);
  }

  String toLine() {
    return String.format("\n - %s  %s  [%s](%s)", idS, date, title, filename);
  }

  String toRow() {
    return String.format("\n|%s|%s|%s|[%s](%s)|", idS, category, date, title, filename);
  }

  public Boolean isPRJRecord() {
    return type == RecordType.PRJ;
  }

  public Boolean isTCHRecord() {
    return type == RecordType.TCH;
  }

  enum RecordType {
    PRJ,
    TCH
  }

  public String getCategory() {
    return category;
  }

  public int getId() {
    return id;
  }
}
