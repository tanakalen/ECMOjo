package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
//import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
//import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
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

    /** True for label to be read top to bottom. */
    protected boolean topdown = false;

    /** Shadow color. */
    private Color shadowColor = new Color(0.0f, 0.0f, 0.0f, 0.3f);

    /**
     * Constructor. 
     * 
     * @param clockwise  True for clockwise.
     */
    VerticalLabelUI(boolean clockwise) {
      this.clockwise = clockwise;
    }

    /**
     * Set topdown boolean to be true (read label top to bottom) or not.
     * 
     * @param isTrue  True or false.
     */
    public void setTopDown(boolean isTrue) {
      this.topdown = isTrue;
    }

    /**
     * Preferred size.
     * 
     * @param c  The component.
     * @return  The preferred size.
     */
    @Override
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
    @Override
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
      // Setup shadow in textLayout
      Graphics2D g2 = (Graphics2D) g;
      g.setFont(getFont());
//      Paint fillPaint = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f)
//                                        , 0, getFont().getSize(), new Color(0.9f, 0.9f, 0.9f));
      FontRenderContext fontRenderContext = g2.getFontRenderContext();

      TextLayout textLayout = new TextLayout(clippedText, g2.getFont(), fontRenderContext);

      AffineTransform tr = g2.getTransform();
      if (!topdown) {
        if (clockwise) {
          g2.rotate(Math.PI / 2);
          g2.translate(0, -c.getWidth());
        } 
        else {
          g2.rotate(-Math.PI / 2);
          g2.translate(-c.getHeight(), 0);
        }
      }
      if (icon != null) {
        icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
      }

      if (text != null) {
        int textX = paintTextR.x;
        int textY = paintTextR.y + fm.getAscent();
        
        if (topdown) {
          topDown(g, textX, textY, text);
        }
        else {
          if (label.isEnabled()) {
            // Draws shadow
            g2.setPaint(shadowColor);
            textLayout.draw(g2, textX - 3, textY + 3);
            // Paints text
            paintEnabledText(label, g, clippedText, textX, textY);
          } 
          else {
            // Draws shadow
            g2.setPaint(shadowColor);
            textLayout.draw(g2, textX - 3, textY + 3);
            // Paints text
            paintDisabledText(label, g, clippedText, textX, textY);
          }
        }
      }
      g2.setTransform(tr);
    }
    
    /**
     * Top to down: rotate displayed text and font to be read top to bottom.
     * 
     * @param g  Graphics.
     * @param x  X-coordinate.
     * @param y  Y-coordinate.
     * @param label  String label to rotate.
     */
    private void topDown(Graphics g, double x, double y, String label) {
      Graphics2D g2D = (Graphics2D)g;
      Font theFont = g2D.getFont();
      FontMetrics fm = g2D.getFontMetrics(theFont);
      int fCharHeight = fm.getAscent() + fm.getDescent();
//      int fDescent = fm.getDescent();
      int len = label.length();
      char[] data = new char[len];
      int[] fCharWidths = new int[len];
      String[] fCharStrings = new String[len];
      label.getChars(0, len, data, 0);
      int fWidth = 0; // find widest char
      char ch;
      for (int i = 0; i < len; i++) {
        ch = data[i];
        fCharStrings[i] = new String(data, i, 1);
        fCharWidths[i] = fm.charWidth(ch);
        if (fCharWidths[i] > fWidth) {
          fWidth = fCharWidths[i];
        }
      }
//      int fHeight = fCharHeight * len + fDescent;
      // draw string by character top to bottom
      g.setFont(theFont);
      int yPos = (int)y + fCharHeight;
      for (int i = 0; i < len; i++) {
        g.setColor(shadowColor);
        g.drawString(fCharStrings[i], (int)x+((fWidth-fCharWidths[i])/2) + 3, yPos + 3);
        g.setColor(getForeground());
        g.drawString(fCharStrings[i], (int)x+((fWidth-fCharWidths[i])/2), yPos);
        yPos += fCharHeight;
      }
    }
  }

  /**
   * Constructor for vertical top to bottom reading label.
   * 
   * @param text  String for vertical label.
   */
  public VerticalLabel(String text) {
    setText(text);
    VerticalLabelUI topdownUI = new VerticalLabelUI(false);
    topdownUI.setTopDown(true);
    setUI(topdownUI);
  }

  /**
   * Constructor for fancy label. Sets default appearance.
   * 
   * @param text  String for vertical label.
   * @param clockwise  True for clockwise.
   */
  public VerticalLabel(String text, boolean clockwise) {
    setText(text);
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
