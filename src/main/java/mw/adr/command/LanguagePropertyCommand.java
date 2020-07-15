package mw.adr.command;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LanguagePropertyCommand {
  private final LanguageEnum language;
  private final Map<String, Category> categories;

  public LanguagePropertyCommand(LanguageEnum language, List<Category> categoriesList) {
    this.language = language;
    this.categories = categoriesList.stream().collect(Collectors.toMap(c -> c.getKey(), c -> c));
  }

  public static LanguagePropertyCommand from(String categories, String language) {
    var collect =
        Arrays.stream(categories.split(";"))
            .map(it -> Category.of(it))
            .collect(Collectors.toList());
    return new LanguagePropertyCommand(LanguageEnum.valueOf(language), collect);
  }

  public LanguageEnum getLanguage() {
    return language;
  }

  public Map<String, Category> getCategories() {
    return categories;
  }
}
