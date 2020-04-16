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
    return idS()+"-"+Arrays.stream(name.split(" ")).map(String::toLowerCase).collect(Collectors.joining("-"))+".md";
  }

  private String idS() {
   return String.format("%04d", id) ;
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
