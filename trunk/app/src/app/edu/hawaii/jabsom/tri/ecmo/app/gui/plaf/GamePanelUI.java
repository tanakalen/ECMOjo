package edu.hawaii.jabsom.tri.ecmo.app.gui.plaf;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;

/**
 * The panel look and feel class.
 * 
 * @author king
 * @since September 20, 2007
 */
public class GamePanelUI extends BasicPanelUI {

  /**
   * Returns an instance of the UI delegate for the specified component.
   * <p> 
   * NOTE: This method is required!
   * 
   * @param component  The component.
   * @return  The UI.
   */
  public static ComponentUI createUI(JComponent component) {
    return new GamePanelUI();
  }
}
