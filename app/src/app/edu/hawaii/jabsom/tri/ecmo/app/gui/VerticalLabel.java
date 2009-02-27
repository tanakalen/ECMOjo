package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;

/**
 * Vertical label with antialiased text. 
 *
 * @author    king 
 * @since     October 16, 2007
 */
public class VerticalLabel extends JLabel {
 
  /**
   * The vertical label class.
   */
  class VerticalLabelUI extends BasicLabelUI {

    /** True for clockwise. */
    protected boolean clockwise;

    /**
     * Constructor. 
     * 
     * @param clockwise  True for clockwise.
     */
    VerticalLabelUI(boolean clockwise) {
      this.clockwise = clockwise;
    }

    /**
     * Preferred size.
     * 
     * @param c  The component.
     * @return  The preferred size.
     */
    public Dimension getPreferredSize(JComponent c) {
      Dimension dim = super.getPreferredSize(c);
      return new Dimension(dim.height, dim.width);
    }

    /**
     * Paint method.
     * 
     * @param g  Where to draw to.
     * @param c  The component.
     */
    public void paint(Graphics g, JComponent c) {
      JLabel label = (JLabel)c;
      String text = label.getText();
      Icon icon = (label.isEnabled()) ? label.getIcon() : label.getDisabledIcon();

      if ((icon == null) && (text == null)) {
        return;
      }

      Rectangle paintIconR = new Rectangle();
      Rectangle paintTextR = new Rectangle();
      Rectangle paintViewR = new Rectangle();
      Insets paintViewInsets = new Insets(0, 0, 0, 0);

      FontMetrics fm = g.getFontMetrics();
      paintViewInsets = c.getInsets(paintViewInsets);

      paintViewR.x = paintViewInsets.left;
      paintViewR.y = paintViewInsets.top;

      // Use inverted height & width
      paintViewR.height = c.getWidth() - (paintViewInsets.left + paintViewInsets.right);
      paintViewR.width = c.getHeight() - (paintViewInsets.top + paintViewInsets.bottom);

      paintIconR.x = 0;
      paintIconR.y = 0;
      paintIconR.width = 0;
      paintIconR.height = 0;
      paintTextR.x = 0;
      paintTextR.y = 0;
      paintTextR.width = 0;
      paintTextR.height = 0;

      String clippedText = layoutCL(label, fm, text, icon, paintViewR, paintIconR, paintTextR);

      Graphics2D g2 = (Graphics2D) g;
      AffineTransform tr = g2.getTransform();
      if (clockwise) {
        g2.rotate(Math.PI / 2);
        g2.translate(0, -c.getWidth());
      } 
      else {
        g2.rotate(-Math.PI / 2);
        g2.translate(-c.getHeight(), 0);
      }

      if (icon != null) {
        icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
      }

      if (text != null) {
        int textX = paintTextR.x;
        int textY = paintTextR.y + fm.getAscent();

        if (label.isEnabled()) {
          paintEnabledText(label, g, clippedText, textX, textY);
        } 
        else {
          paintDisabledText(label, g, clippedText, textX, textY);
        }
      }
      g2.setTransform(tr);
    }
  }
  
  /**
   * Constructor for vertical label.
   */
  public VerticalLabel() {
    this(false);
  }
  
  /**
   * Constructor for fancy lable. Sets default appearance.
   * 
   * @param clockwise  True for clockwise.
   */
  public VerticalLabel(boolean clockwise) {
    // set vertical label ui
    setUI(new VerticalLabelUI(clockwise));
  }
  
  /**
   * Writes antialiased text.
   * 
   * @param g  Where to write the text to.
   */ 
  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2.setFont(g2.getFont().deriveFont(Font.BOLD));
    super.paintComponent(g);
  }
}
