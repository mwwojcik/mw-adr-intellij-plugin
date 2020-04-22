package mw.adr.model;

import java.util.Comparator;
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
    s.append("\n");
    s.append("- [Decyzje projektowe](#decyzje-projektowe)");
    s.append("\n");
    s.append("- [Decyzje techniczne](#decyzje-techniczne)");
    s.append("\n");
    s.append("\n");
    s.append("## Decyzje projektowe");
    s.append("\n");
    s.append(tabela());
    records.stream().filter(it->it.isPRJRecord()).sorted(Comparator.comparing(ADLRecord::getCategory).thenComparing(ADLRecord::getId)).forEach(it -> s.append(it.toRow()));


    s.append("\n");
    s.append("## Decyzje techniczne");
    s.append("\n");
    s.append(tabela());
    records.stream().filter(it->it.isTCHRecord()).sorted(Comparator.comparing(ADLRecord::getCategory).thenComparing(ADLRecord::getId)).forEach(it -> s.append(it.toRow()));
    s.append("\n");
    return s.toString();
  }

  public String filename() {
    return name;
  }
}
