package edu.hawaii.jabsom.tri.ecmo.app.gui.plaf;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JToolTip;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalToolTipUI;

/**
 * The tooltip UI class.
 * 
 * @author noblemaster
 * @since April 18, 2010
 */
public class GameToolTipUI extends MetalToolTipUI {

  /** The singleton instance for increased performance. */
  private static GameToolTipUI instance = new GameToolTipUI();
  
  /** The ancestor listener. */
  private static AncestorListener ancestorListener = new AncestorListener() {
    public void ancestorAdded(AncestorEvent event) {
      // make sure the component is transparent
      JComponent component = event.getComponent();
      component.setOpaque(false);
      component.setBorder(null);
      Component parent = component.getParent();
      if ((parent != null) && (parent instanceof JComponent)) {
        // make sure parent is transparent
        JComponent jparent = (JComponent)parent;
        if(jparent.isOpaque()) {
          jparent.setOpaque(false);
          jparent.setBorder(null);
        }
      }
    }
    public void ancestorMoved(AncestorEvent event) {
      // not used
    }
    public void ancestorRemoved(AncestorEvent event) {
      // not used
    }    
  };
  
  /** The width. */
  private int width = 240;
  
 
  
  /**
   * Returns an instance of the UI delegate for the specified component.
   * <p> 
   * NOTE: This method is required!
   * 
   * @param component  The component.
   * @return  The UI.
   */
  public static ComponentUI createUI(JComponent component) { 
    // add the ancestor listener which makes sure the component is transparent
    component.addAncestorListener(ancestorListener);

    // and return the singleton
    return instance;
  }

  /**
   * Returns the preferred size.
   * 
   * @param c  The component.
   * @return  The size.
   */
  @Override
  public synchronized Dimension getPreferredSize(JComponent c) {
    JToolTip component = (JToolTip)c;
    String tipText = component.getTipText();
    component.setTipText(fixedWidthString(tipText, c.getFontMetrics(c.getFont())));
    Dimension size = super.getPreferredSize(component);
    component.setTipText(tipText);
    return size;
  }

  /**
   * Paints the component.
   * 
   * @param g  Where to paint to.
   * @param c  The component.
   */
  @Override
  public synchronized void update(Graphics g, JComponent c) {  
    // set antialising to on
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

    // and render...
    Color background = UIManager.getColor("ToolTip.background"); 
    Composite oldComp = g2.getComposite();
    Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, ((float)background.getAlpha()) / 255f);
    g2.setComposite(alphaComp);

    // draw box
    Border border = UIManager.getBorder("ToolTip.border");
    Color borderColor;
    boolean rounded;
    if (border instanceof LineBorder) {
      LineBorder lineBorder = (LineBorder)border;
      borderColor = lineBorder.getLineColor();
      rounded = lineBorder.getRoundedCorners();
    }
    else {
      borderColor = Color.BLACK;
      rounded = true;
    }
    if (rounded) {
      g2.setColor(background);
      g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 8, 8);
      g2.setColor(borderColor);
      g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 8, 8);
    }
    else {
      g2.setColor(background);
      g2.fillRect(0, 0, c.getWidth(), c.getHeight());
      g2.setColor(borderColor);
      g2.drawRect(0, 0, c.getWidth() - 1, c.getHeight() - 1);
    }
    
    // draw text
    JToolTip component = (JToolTip)c;
    String tipText = component.getTipText();
    component.setTipText(fixedWidthString(tipText, c.getFontMetrics(c.getFont())));
    super.paint(g, component);
    component.setTipText(tipText); 
    
    g2.setComposite(oldComp);
  }

  /**
   * Converts the text to a fixed width string.
   * 
   * @param text  The text.
   * @param fontMetrics  The font metrics.
   * @return  The fixed text.
   */
  private String fixedWidthString(String text, FontMetrics fontMetrics) {
    if (fontMetrics.stringWidth(text) < width) {
      // we don't format if the string is short enough
      return text;
    }
    else {
      String lowerText = text.toLowerCase();
      if (lowerText.startsWith("<html>")) {
        if (lowerText.contains("<table>")) {
          // let's leave it alone
          return text;
        }
        else {
          // wrap with TABLE
          int endIndex = lowerText.lastIndexOf("</html>");
          text = text.substring("<html>".length(), endIndex);
          return "<html><table><tr><td width=\"" + width + "\" valign=\"top\">" + text + "</td></tr></table></html>";
        }
      }
      else {
        // wrap in HTML and TABLE
        return "<html><table><tr><td width=\"" + width + "\" valign=\"top\">" + text + "</td></tr></table></html>";
      }
    }
  }
  
  /**
   * Returns the maximum size.
   * 
   * @param c  The component.
   * @return  The size.
   */
  @Override
  public Dimension getMaximumSize(JComponent c) {
    return getPreferredSize(c);
  }

  /**
   * Returns the minimum size.
   * 
   * @param c  The component.
   * @return  The size.
   */
  @Override
  public Dimension getMinimumSize(JComponent c) {
    return getPreferredSize(c);
  }
}
