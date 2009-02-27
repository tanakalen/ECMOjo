package edu.hawaii.jabsom.tri.ecmo.app.gui.plaf;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

/**
 * The combo box UI.
 * 
 * @author king
 * @since September 17, 2007
 */
public class GameComboBoxUI extends MetalComboBoxUI {

  /**
   * Returns an instance of the UI delegate for the specified component.
   * <p> 
   * NOTE: This method is required!
   * 
   * @param component  The component.
   * @return  The UI.
   */
  public static ComponentUI createUI(JComponent component) {
    return new GameComboBoxUI();
  }
  
  /**
   * Returns the arrow button.
   * 
   * @return  The created button.
   */
  protected JButton createArrowButton() {
    return super.createArrowButton();
  }
  
  /**
   * Creates the combo popup with the scroll bar.
   * 
   * @return  The new combo popup with scroll bar.
   */
  protected ComboPopup createPopup() {
    return super.createPopup();
  }
}
