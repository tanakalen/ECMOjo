package edu.hawaii.jabsom.tri.ecmo.app.gui.plaf;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalRadioButtonUI;

/**
 * The button look and feel class.
 * 
 * @author king
 * @since August 26, 2007
 */
public class GameRadioButtonUI extends MetalRadioButtonUI {

  /**
   * Returns an instance of the UI delegate for the specified component.
   * <p> 
   * NOTE: This method is required!
   * 
   * @param component  The component.
   * @return  The UI.
   */
  public static ComponentUI createUI(JComponent component) {
    return new GameRadioButtonUI();
  }
  
  /**
   * If necessary paints the background of the component, then
   * invokes <code>paint</code>.
   *
   * @param g Graphics to paint to
   * @param c JComponent painting on
   */
  @Override
  public void update(Graphics g, JComponent c) {
    // use antialiasing
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

    // actual drawing
    super.update(g, c);
  }
}
