package mw.adr.model;

import java.util.Comparator;
import java.util.List;

public class EnglishADLDocument implements ArchDocument {
  String name = "0000-adl-index.md";

  private final List<ADLRecord> records;

  private EnglishADLDocument(List<ADLRecord> records) {
    this.records = records;
  }

  public static EnglishADLDocument from(List<ADLRecord> records) {
    return new EnglishADLDocument(records);
  }

  private String table() {
    StringBuilder s = new StringBuilder("\n");
    s.append("|Rownum|Area|Date|Name|\n");
    s.append("|----|----|----|----|");
    return s.toString();
  }

  public String toContent() {
    StringBuilder s = new StringBuilder();
    s.append("\n");
    s.append("\n");
    s.append("# Architecture decision log");
    s.append("\n");
    s.append("\n");
    s.append("|*" + ADR.NAMING_PATTERN + "*|\n");
    s.append("|:----:|\n");
    s.append("\n");
    s.append("\n");
    s.append("- [Strategic decisions](#strategic-decisions)");
    s.append("\n");
    s.append("- [Technical decisions](#technical-decisions)");
    s.append("\n");
    s.append("\n");
    s.append("## Strategic decisions");
    s.append("\n");
    s.append("\n");
    s.append(table());
    records.stream()
        .filter(it -> it.isPRJRecord())
        .sorted(Comparator.comparing(ADLRecord::getCategory).thenComparing(ADLRecord::getId))
        .forEach(it -> s.append(it.toRow()));

    s.append("\n");
    s.append("\n");
    s.append("## Technical decisions");
    s.append("\n");
    s.append("\n");
    s.append(table());
    records.stream()
        .filter(it -> it.isTCHRecord())
        .sorted(Comparator.comparing(ADLRecord::getCategory).thenComparing(ADLRecord::getId))
        .forEach(it -> s.append(it.toRow()));
    s.append("\n");
    return s.toString();
  }

  public String filename() {
    return name;
  }
}
