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

  private String tabela() {
    StringBuilder s=new StringBuilder("\n");
    s.append("|LP|Obszar|Data|Nazwa|\n");
    s.append("|-|-|-|-|");
   return s.toString() ;
   }

  public String toContent() {
    StringBuilder s = new StringBuilder();
    s.append("\n");
    s.append("# Katalog decyzji architektonicznych");
    s.append("\n");
    s.append("## Decyzje projektowe");
    s.append("\n");
    s.append(tabela());
    records.stream().filter(it->it.isPRJRecord()).forEach(it -> s.append(it.toRow()));


    s.append("\n");
    s.append("## Decyzje techniczne");
    s.append("\n");
    s.append(tabela());
    records.stream().filter(it->it.isTCHRecord()).forEach(it -> s.append(it.toRow()));
    s.append("\n");
    return s.toString();
  }

  public String filename() {
    return name;
  }
}
