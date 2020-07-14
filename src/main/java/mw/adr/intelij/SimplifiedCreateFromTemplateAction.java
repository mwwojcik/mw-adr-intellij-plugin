package mw.adr.intelij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.application.WriteActionAware;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nls;

import javax.swing.*;

public abstract class SimplifiedCreateFromTemplateAction<T extends PsiElement> extends AnAction
    implements WriteActionAware {
  protected static final Logger LOG = Logger.getInstance(SimplifiedCreateFromTemplateAction.class);

  public SimplifiedCreateFromTemplateAction(
      @Nls(capitalization = Nls.Capitalization.Title) String text,
      @Nls(capitalization = Nls.Capitalization.Sentence) String description,
      Icon icon) {
    super(text, description, icon);
  }
}
