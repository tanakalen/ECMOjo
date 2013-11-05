package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

/**
 * The text renderer. 
 *
 * @author   Christoph Aschwanden
 * @since    October 27, 2008
 */
public final class TextRenderer {

  /**
   * Private constructor to prevent instantiation.
   */
  private TextRenderer() {
    // not used
  }
  
  /**
   * Returns the width for the text to render.
   * 
   * @param g  Where to render to.
   * @param text  The text.
   * @return  The width.
   */
  public static int renderWidth(Graphics2D g, String text) {
    return g.getFontMetrics().stringWidth(text) + getOffset(g, text);
  }
  
  /**
   * Returns the height for the text to render.
   * 
   * @param g  Where to render to.
   * @param text  The text.
   * @return  The height.
   */
  public static int renderHeight(Graphics2D g, String text) {
    return g.getFontMetrics().getHeight() + getOffset(g, text);
  }

  /**
   * Returns the graphics offset to draw the shade.
   * 
   * @param g  Where to render to.
   * @param text  The text.
   * @return  The offset.
   */
  private static int getOffset(Graphics2D g, String text) {
    int offset = g.getFont().getSize() / 10;
    if (offset == 0) {
      offset = 1;
    }
    return offset;
  }
  
  /**
   * Renders an outlined text.
   * 
   * @param g  Where to render to.
   * @param text  The text.
   * @param x  The x coordinate.
   * @param y  The y coordinate.
   * @param fillPaint  The fill paint.
   * @param borderPaint  The border paint.
   * @param shadow  True for shadow.
   */
  public static void renderOutline(Graphics2D g, String text, int x, int y
                                 , Paint fillPaint, Paint borderPaint, boolean shadow) {   
    FontRenderContext fontRenderContext = g.getFontRenderContext();
    TextLayout textLayout = new TextLayout(text, g.getFont(), fontRenderContext);
    AffineTransform transform = AffineTransform.getTranslateInstance(x, y);
    Shape outline = textLayout.getOutline(transform);
    
    // draw shadow if selected
    if (shadow) {
      int offset = getOffset(g, text);
      g.translate(offset, offset);
      g.setPaint(new Color(0.0f, 0.0f, 0.0f, 0.3f));
      g.fill(outline);
      g.translate(-offset, -offset);
    }
    
    // draw outlined text
    g.setPaint(fillPaint);
    textLayout.draw(g, (float)x, (float)y);
    if (borderPaint != null) {
      g.setPaint(borderPaint);
      g.draw(outline);
    }
  }
}
