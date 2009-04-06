package edu.hawaii.jabsom.tri.ecmo.app.gui.plaf;

import java.awt.Color;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 * The button look and feel class.
 * 
 * @author king
 * @since August 26, 2007
 */
public class GameButtonUI extends MetalButtonUI {

  /**
   * Returns an instance of the UI delegate for the specified component.
   * <p> 
   * NOTE: This method is required!
   * 
   * @param component  The component.
   * @return  The UI.
   */
  public static ComponentUI createUI(JComponent component) {
    return new GameButtonUI();
  }
  
  /**
   * Returns the rollover color.
   * 
   * @return  The rollover color.
   */
  protected Color getRolloverColor() {
    return UIManager.getColor(getPropertyPrefix() + "rolloverBackground");
  }

  /**
   * Returns the selected color.
   * 
   * @return  The selected color.
   */
  protected Color getPressedColor() {
    return UIManager.getColor(getPropertyPrefix() + "pressedBackground");
  }

  /**
   * Installs the button defaults.
   * 
   * @param b  The button.
   */
  public void installDefaults(AbstractButton b) {
    super.installDefaults(b);
    
    // do NOT paint border + focus
    b.setBorderPainted(false);
    b.setFocusPainted(false);
    b.setContentAreaFilled(false);
  }
}
