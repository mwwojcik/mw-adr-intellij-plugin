package mw.adr.command;

public class Category {
  private final String key;
  private final String desc;

  private Category(String key, String desc) {
    this.key = key;
    this.desc = desc;
  }

  public static Category of(String prop) {
    if (!prop.contains("=")) {
      throw new IllegalArgumentException("wrong format");
    }
    var keyDesc = prop.split("=");
    return new Category(keyDesc[0], keyDesc[1]);
  }

  public String getKey() {
    return key;
  }

  public String getDesc() {
    return desc;
  }
}
