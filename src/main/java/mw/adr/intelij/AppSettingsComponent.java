package mw.adr.intelij;

import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBRadioButton;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

/** Supports creating and managing a JPanel for the Settings Dialog. */
public class AppSettingsComponent {
  private final JPanel myMainPanel;
  private final ButtonGroup G = new ButtonGroup();
  private final JBRadioButton engButton = new JBRadioButton("ENGLISH");
  private final JBRadioButton polButton = new JBRadioButton("POLISH");
  private final JBTextField categoriesText =
      new JBTextField("PRJ=Strategic decisions;TCH=Technical decisions");

  public AppSettingsComponent() {

    G.add(engButton);
    G.add(polButton);

    myMainPanel =
        FormBuilder.createFormBuilder()
            .addComponent(new JLabel("Language"))
            .addComponent(engButton, 1)
            .addComponent(polButton, 1)
            .addComponent(new JLabel("Categories (format:PRJ=Strategic decisions;TCH=Technical decisions"))
            .addLabeledComponent(new JBLabel("Enter categories: "), categoriesText, 1, false)
            .addComponentFillVertically(new JPanel(), 0)
            .getPanel();
  }

  public JPanel getPanel() {
    return myMainPanel;
  }

  public String getLanguage() {
    if (polButton.isSelected()) {
      return polButton.getText();
    } else {
      return engButton.getText();
    }
  }

  public void setLanguage(String value) {
    if (value.equals(polButton.getText())) {
      polButton.setSelected(true);
    } else {
      engButton.setSelected(true);
    }
  }

  public String getCategoriesText() {
    return categoriesText.getText();
  }

  public void setCategoriesText(String value) {
    categoriesText.setText(value);
  }

  public JComponent getPreferredFocusedComponent() {
    return categoriesText;
  }
}
