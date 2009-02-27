package edu.hawaii.jabsom.tri.ecmo.app.gui.plaf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
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

  /** The translucent color. */
  private static final Color TRANSLUCENT_COLOR = new Color(1.0f, 1.0f, 1.0f, 0.30f);
  
  
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

  /**
   * If necessary paints the background of the component, then
   * invokes <code>paint</code>.
   *
   * @param g Graphics to paint to
   * @param c JComponent painting on
   */
  public void update(Graphics g, JComponent c) {
    // set antialised text
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
    // draw bg
    AbstractButton button = (AbstractButton)c;
    ButtonModel model = button.getModel();
    if (c.isEnabled()) {
      // ENABLED
      if (model.isPressed() || model.isSelected()) {
        g.setColor(getPressedColor());
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
      }
      else if (model.isRollover()) {
        g.setColor(getRolloverColor());
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
      }
      else {
        g.setColor(button.getBackground());
        g.fillRect(0, 0, c.getWidth(), c.getHeight());
      }
      
      // draw box
      g.setColor(button.getBackground().darker());
      g.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
    }
    else {
      // DISABLED
      g.setColor(getRolloverColor());
      g.fillRect(0, 0, c.getWidth(), c.getHeight());
      
      // draw box
      g.setColor(button.getBackground());
      g.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
    }
    
    // and paint rest
    paint(g, c);
    
    // paint translucent over
    g.setColor(TRANSLUCENT_COLOR);
    int height2 = c.getHeight() / 2 - 1;
    int width = c.getWidth();
    g.fillRect(0, 0, width, height2);
    g.fillRect(3, height2, width - 6, 1);
  }
}
