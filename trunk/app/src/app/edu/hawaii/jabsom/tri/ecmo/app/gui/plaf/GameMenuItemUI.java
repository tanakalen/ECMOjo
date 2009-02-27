package edu.hawaii.jabsom.tri.ecmo.app.gui.plaf;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuItemUI;

/**
 * The menu item look and feel class.
 * 
 * @author king
 * @since March 30, 2008
 */
public class GameMenuItemUI extends BasicMenuItemUI {

  /**
   * Returns an instance of the UI delegate for the specified component.
   * <p> 
   * NOTE: This method is required!
   * 
   * @param component  The component.
   * @return  The UI.
   */
  public static ComponentUI createUI(JComponent component) {
    return new GameMenuItemUI();
  }

  /**
   * Paints this component.
   *
   * @param g  Where to draw to.
   * @param c  The component.
   */
  @Override
  public void update(Graphics g, JComponent c) {
    // set antialised text
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
    // execute drawing
    super.update(g, c);
  }  
}
