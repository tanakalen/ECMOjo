package edu.hawaii.jabsom.tri.ecmo.app.gui.plaf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalScrollBarUI;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ArrowButton;

/**
 * The scroll bar look and feel class.
 * 
 * @author king
 * @since August 26, 2007
 */
public class GameScrollBarUI extends MetalScrollBarUI {
  
  /** The translucent color. */
  private static final Color TRANSLUCENT_COLOR = new Color(1.0f, 1.0f, 1.0f, 0.30f);

  /** The thumb color. */
  private Color thumbColor;
  /** The thumb highlight color. */
  private Color thumbHighlightColor;
  /** The thumb shadow color. */
  private Color thumbShadowColor;
  
  
  /**
   * Returns an instance of the UI delegate for the specified component.
   * <p> 
   * NOTE: This method is required!
   * 
   * @param component  The component.
   * @return  The UI.
   */
  public static ComponentUI createUI(JComponent component) {
    return new GameScrollBarUI();
  }
  
  /**
   * Returns the decrease button.
   * 
   * @param direction  The orientation of the button to create.
   * @return  The created button.
   */
  protected JButton createDecreaseButton(int direction) {
    return new ArrowButton(direction);
  }
  
  /**
   * Returns the decrease button.
   * 
   * @param direction  The orientation of the button to create.
   * @return  The created button.
   */
  protected JButton createIncreaseButton(int direction) {
    return new ArrowButton(direction);
  }
  
  /** 
   * Configure colors.
   */
  protected void configureScrollBarColors() {
      super.configureScrollBarColors();
      thumbColor = UIManager.getColor("ScrollBar.thumb");
      thumbShadowColor = UIManager.getColor("ScrollBar.thumbShadow");
      thumbHighlightColor = UIManager.getColor("ScrollBar.thumbHighlight");
  } 

  /**
   * Paints the track of the component.
   * 
   * @param g  Where to draw to.
   * @param component  The component we draw to?
   * @param trackBounds  The original bounds.
   */
  protected void paintTrack(Graphics g, JComponent component, Rectangle trackBounds) {
    g.setColor(trackColor);
    g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    g.setColor(trackColor.brighter());
    g.drawLine(trackBounds.x, trackBounds.y, trackBounds.x, trackBounds.y + trackBounds.height - 1);
  }
  
  /**
   * Paints the thumb of the component.
   * 
   * @param g  Where to draw to.
   * @param component  The component we draw to?
   * @param trackBounds  The original bounds.
   */
  protected void paintThumb(Graphics g, JComponent component, Rectangle trackBounds) {
    int x = trackBounds.x;
    int y = trackBounds.y;
    int width = trackBounds.width;
    int height = trackBounds.height;
    
    g.setColor(thumbColor);
    g.fillRect(x + 1, y + 1, width - 2, height - 2);

    g.setColor(thumbShadowColor);
    g.drawLine(x + 2, y, x + width - 2, y);
    g.drawLine(x + 1, y + 1, x + 1, y + height - 2);
    g.drawLine(x + 2, y + height - 1, x + width - 2, y + height - 1);
    g.drawLine(x + width - 1, y + 1, x + width - 1, y + height - 2);
    
    // draw horizontal lines to indicate thumb
    g.drawLine(x + 3, y + height / 2 - 4, x + width - 4, y + height / 2 - 4);
    g.drawLine(x + 3, y + height / 2,     x + width - 4, y + height / 2);
    g.drawLine(x + 3, y + height / 2 + 4, x + width - 4, y + height / 2 + 4);
    g.setColor(thumbHighlightColor);
    g.drawLine(x + 3, y + height / 2 - 5, x + width - 4, y + height / 2 - 5);
    g.drawLine(x + 3, y + height / 2 - 1, x + width - 4, y + height / 2 - 1);
    g.drawLine(x + 3, y + height / 2 + 3, x + width - 4, y + height / 2 + 3);
    
    // draw translucent
    int width2 = width / 2 - 1;
    g.setColor(TRANSLUCENT_COLOR);
    g.fillRect(x, y, width2, height);
    g.fillRect(x + width2, y + 3, 1, height - 6);
  }
}
