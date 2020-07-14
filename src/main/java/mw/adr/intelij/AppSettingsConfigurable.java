package mw.adr.intelij;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/** Provides controller functionality for application settings. */
public class AppSettingsConfigurable implements Configurable {
  private AppSettingsComponent mySettingsComponent;

  // A default constructor with no arguments is required because this implementation
  // is registered as an applicationConfigurable EP

  @Nls(capitalization = Nls.Capitalization.Title)
  @Override
  public String getDisplayName() {
    return "SDK: ADR Plugin Settings";
  }

  @Override
  public JComponent getPreferredFocusedComponent() {
    return mySettingsComponent.getPreferredFocusedComponent();
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    mySettingsComponent = new AppSettingsComponent();
    return mySettingsComponent.getPanel();
  }

  @Override
  public boolean isModified() {
    AppSettingsState settings = AppSettingsState.getInstance();
    boolean modified = !mySettingsComponent.getCategoriesText().equals(settings.categories);
    modified |= !mySettingsComponent.getLanguage().equals(settings.language);

    return modified;
  }

  @Override
  public void apply() throws ConfigurationException {
    AppSettingsState settings = AppSettingsState.getInstance();
    settings.categories = mySettingsComponent.getCategoriesText();
    settings.language = mySettingsComponent.getLanguage();
  }

  @Override
  public void reset() {
    AppSettingsState settings = AppSettingsState.getInstance();
    mySettingsComponent.setCategoriesText(settings.categories);
    mySettingsComponent.setLanguage(settings.language);
  }

  @Override
  public void disposeUIResources() {
    mySettingsComponent = null;
  }
}
