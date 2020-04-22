package mw.adr.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ADR {

  private String template;
  private String createDate;
  private Integer id;
  private String author;
  private String name;

  public ADR(Builder builder) {
    template=builder.template;
    createDate=builder.createDate;
    id=builder.id;
    author=builder.author;
    name=builder.name;
  }

  public String content() {
    return template.replaceAll("ADR_TITLE",name)
            .replaceAll("ADR_CREATE_DATE",createDate)
            .replaceAll("ADR_AUTHOR",author)
            .replaceAll("ADR_ID",idS());
  }

  public String filename(){


    var s = idS() + "-" + toName(name);
    return s;

  }

  private String idS() {
   return String.format("%04d", id) ;
   }

  private String toName(String name) {
    var cat=name.substring(0,name.indexOf(" ")).toUpperCase();
    var simpleName=name.substring(name.indexOf(" ")+1).toLowerCase();
    return cat+"-"+Arrays.stream(simpleName.split(" ")).map(String::toLowerCase).collect(Collectors.joining("-")) + ".md" ;
  }

  public static class Builder {
    private String template;
    private String createDate;
    private Integer id;
    private String author;
    private String name;

    public static Builder of() {
      return new Builder();
    }

    public Builder withTemplate(String template) {
      this.template = template;
      return this;
    }

    public Builder withDate(String date) {
      this.createDate = date;
      return this;
    }

    public Builder withId(Integer id) {
      this.id = id;
      return this;
    }

    public Builder withAuthor(String author) {
      this.author = author;
      return this;
    }

    public Builder withName(String name) {
      this.name= name;
      return this;
    }

    public ADR build() {
      return new ADR(this);
    }
  }
}
