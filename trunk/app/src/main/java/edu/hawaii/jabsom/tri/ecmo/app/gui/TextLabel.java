package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.JLabel;

/**
 * Text label with 3D text. 
 *
 * @author    king 
 * @since     November 3, 2008
 */
public class TextLabel extends JLabel {
 
  /** The border color. */
  private Color borderColor = new Color(0.0f, 0.0f, 0.0f, 0.85f);
  
  /** The gradient top color. */
  private Color gradientTopColor = new Color(1.0f, 1.0f, 1.0f);
  /** The gradient bottom color. */
  private Color gradientBottomColor = new Color(0.9f, 0.9f, 0.9f);
  
  /** True to draw a shadow. */
  boolean drawShadow = true;
  
  /** True to draw border. */
  boolean drawBorder = true;
  
  
  /**
   * Constructor for text label.
   */
  public TextLabel() {
    this("");
  }
  
  /**
   * Constructor for text label.
   * 
   * @param text  The text.
   */
  public TextLabel(String text) {
    super(text);
  }
  
  /**
   * Returns the preferred size.
   * 
   * @return  The preferred size.
   */
  @Override
  public Dimension getPreferredSize() {
    Graphics2D g = (Graphics2D)getGraphics();
    String text = getText();
    return new Dimension(TextRenderer.renderWidth(g, text), TextRenderer.renderHeight(g, text));
  }

  /**
   * True to draw a shadow.
   *
   * @return  True to draw a shadow.
   */
  public boolean isDrawShadow() {
    return drawShadow;
  }

  /**
   * True to draw a shadow.
   *
   * @param drawShadow  True to draw a shadow.
   */
  public void setDrawShadow(boolean drawShadow) {
    this.drawShadow = drawShadow;
  }

  /**
   * True to draw border.
   *
   * @param drawBorder  True to draw border.
   */
  public void setDrawBorder(boolean drawBorder) {
    this.drawBorder = drawBorder;
  }

  /**
   * Returns the border color.
   *
   * @return  The border color.
   */
  public Color getBorderColor() {
    return borderColor;
  }

  /**
   * Sets the border color.
   *
   * @param borderColor  The border color to set.
   */
  public void setBorderColor(Color borderColor) {
    this.borderColor = borderColor;
  }

  /**
   * Returns the gradient bottom color.
   *
   * @return  The gradient bottom color.
   */
  public Color getGradientBottomColor() {
    return gradientBottomColor;
  }

  /**
   * Sets the gradient bottom color.
   *
   * @param gradientBottomColor  The gradient bottom color to set.
   */
  public void setGradientBottomColor(Color gradientBottomColor) {
    this.gradientBottomColor = gradientBottomColor;
  }

  /**
   * Returns the gradient top color.
   *
   * @return  The gradient top color.
   */
  public Color getGradientTopColor() {
    return gradientTopColor;
  }

  /**
   * Sets the gradient top color.
   *
   * @param gradientTopColor  The gradient top color to set.
   */
  public void setGradientTopColor(Color gradientTopColor) {
    this.gradientTopColor = gradientTopColor;
  }

  /**
   * Writes antialiased text.
   * 
   * @param g  Where to write the text to.
   */ 
  public void paintComponent(Graphics g) {
    Toolkit tk = Toolkit.getDefaultToolkit();
    Map desktopHints = (Map)(tk.getDesktopProperty("awt.font.desktophints"));
    Graphics2D g2 = (Graphics2D)g;

    if(desktopHints != null) {
        g2.addRenderingHints(desktopHints);
    }
    g.setFont(getFont()); 
    String text = getText();
    int textWidth = TextRenderer.renderWidth(g2, text);
    int x = 0;
    if (getHorizontalAlignment() == JLabel.CENTER) {
      x = (getWidth() - textWidth) / 2;
    }
    else if (getHorizontalAlignment() == JLabel.RIGHT) {
      x = (getWidth() - textWidth);
    }
    int y = g.getFontMetrics().getAscent();
    Paint fillPaint = new GradientPaint(0, 0, gradientTopColor
                                      , 0, getFont().getSize(), gradientBottomColor);
    if (drawBorder) {
      TextRenderer.renderOutline(g2, text, x, y, fillPaint, borderColor, drawShadow);
    } 
    else {
      TextRenderer.renderOutline(g2, text, x, y, fillPaint, null, drawShadow);
    }
  }
}
