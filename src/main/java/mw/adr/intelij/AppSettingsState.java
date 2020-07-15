package mw.adr.intelij;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import mw.adr.command.LanguagePropertyCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Supports storing the application settings in a persistent way. The State and Storage annotations
 * define the name of the data and the file name where these persistent application settings are
 * stored.
 */
// storages = {@Storage(file = StoragePathMacros.APP_CONFIG+"/other.xml"
@State(
    name = "org.intellij.sdk.settings.AppSettingsState",
    storages = {@Storage("SdkSettingsPlugin.xml")})
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

  public String language = "ENGLISH";
  public String categories = "PRJ=Strategic decisions;TCH=Technical decisions";

  public static AppSettingsState getInstance() {
    return ServiceManager.getService(AppSettingsState.class);
  }

  @Nullable
  @Override
  public AppSettingsState getState() {
    return this;
  }

  @Override
  public void loadState(@NotNull AppSettingsState state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  public LanguagePropertyCommand toCommand() {
    return LanguagePropertyCommand.from(this.getState().categories, this.getState().language);
  }
}
