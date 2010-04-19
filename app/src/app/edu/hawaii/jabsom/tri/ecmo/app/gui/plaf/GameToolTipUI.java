package edu.hawaii.jabsom.tri.ecmo.app.gui.plaf;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JToolTip;
import javax.swing.JComponent;
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
    component.setTipText(fixedWidthString(tipText));
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
  public synchronized void paint(Graphics g, JComponent c) {
    // set antialised text
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // and render...
    JToolTip component = (JToolTip)c;
    String tipText = component.getTipText();
    component.setTipText(fixedWidthString(tipText));
    super.paint(g, component);
    component.setTipText(tipText);
  }

  /**
   * Converts the text to a fixed width string.
   * 
   * @param text  The text.
   * @return  The fixed text.
   */
  private String fixedWidthString(String text) {
    String lowerText = text.toLowerCase();
    if (lowerText.startsWith("<!-- NOHTML -->")) {
      // we ignore HTML if first text indicates no HTML
      return text.substring("<!-- NOHTML -->".length(), text.length());
    }
    else if (lowerText.startsWith("<html>")) {
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
