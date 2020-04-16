package mw.adr.model;

import java.util.List;

public class ADLDocument {
  String name = "0000-adl-index.md";

  private List<ADLRecord> records;

  private ADLDocument(List<ADLRecord> records) {
    this.records = records;
  }

  public static ADLDocument from(List<ADLRecord> records) {
    return new ADLDocument(records);
  }

  public String toContent() {
    StringBuilder s = new StringBuilder();
    s.append("\n");
    s.append("# Katalog decyzji architektonicznych");
    s.append("\n");
    records.forEach(it -> s.append(it.toLine()));
    s.append("\n");
    return s.toString();
  }

  public String filename() {
    return name;
  }
}
