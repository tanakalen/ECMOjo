package king.lib.out;

import javax.swing.JTextPane;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyleConstants;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.text.BadLocationException;

import king.lib.access.Access;

/**
 * Advanced text area with many fancy features. 
 *
 * @author    king 
 * @since     February 27, 2008
 */
public class AdvancedTextPane extends JTextPane {
 
  /** No decoration. */
  public static final int NO_DECORATION = 0x00;
  /** The bold decoration. */ 
  public static final int DECORATION_BOLD = 0x01;
  /** The bold decoration. */ 
  public static final int DECORATION_UNDERLINE = 0x02;
  /** The bold decoration. */ 
  public static final int DECORATION_ITALIC = 0x04;
  /** The bold decoration. */ 
  public static final int DECORATION_SUPERSCRIPT = 0x08;
  /** The bold decoration. */ 
  public static final int DECORATION_SUBSCRIPT = 0x10;
  /** The bold decoration. */ 
  public static final int DECORATION_STRIKETHROUGH = 0x20;
  
  /** The link attribute. */
  private final String linkAttribute = "linkAttribute";
  
  /** The font size. */
  private int fontSize;
  
  
  /**
   * Constructor for chat area panel.
   */
  public AdvancedTextPane() {
    this(11);
  }
  
  /**
   * Constructor for advanced text area panel.
   * 
   * @param fontSize  The font size.
   */
  public AdvancedTextPane(int fontSize) {
    // set defaults
    setEnabled(true);
    setEditable(false);
    setOpaque(true);
    
    // build default style
    setFontSize(fontSize);    
    Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
    StyleConstants.setLineSpacing(defaultStyle, 0f);
    StyleConstants.setSpaceAbove(defaultStyle, 0f);
    StyleConstants.setSpaceBelow(defaultStyle, 0f);
    
    // add link listeners
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent event) {
        try{
          Element element = getStyledDocument().getCharacterElement(viewToModel(event.getPoint()));
          String url = (String)element.getAttributes().getAttribute(linkAttribute);
          if (url != null) {
            // open URL!
            Access.getInstance().openURL(url);
          }
        }
        catch(Exception e) {
          // output error - could not open link
          Error.out(e);
        }
      }
    });
    addMouseMotionListener(new MouseInputAdapter() {
      public void mouseMoved(MouseEvent event) {
        Element element = getStyledDocument().getCharacterElement(viewToModel(event.getPoint()));
        String url = (String)element.getAttributes().getAttribute(linkAttribute);
        if (url != null) {
          setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        else {
          setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
      }
    });
  }

  /**
   * Returns the default font size. 
   * 
   * @return  The font size.
   */
  public int getFontSize() {
    return this.fontSize;
  }
  
  /**
   * Sets the default font size.
   * 
   * @param fontSize  The font size.
   */
  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }
    
  /**
   * Adds text to this pane.
   * 
   * @param text  The text to add.
   * @param color  The color of the text.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   * @param size  The text size.
   */
  public void addText(String text, Color color, int decoration, int size) {
    StyledDocument doc = getStyledDocument();
    Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
    Style style = doc.addStyle("text-" + color.hashCode() + "-" + decoration, defaultStyle);  
    updateStyle(style, color, decoration, size);
    
    // Load the text pane with styled text.
    try {
      doc.insertString(doc.getLength(), text, style);
    }
    catch (BadLocationException e) {
      Error.out(e);
    }
  }
  
  /**
   * Adds a link.
   *  
   * @param url  The URL.
   * @param color  The color.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   * @param size  The text size.
   */
  public void addLink(String url, Color color, int decoration, int size) {
    addLink(url, url, color, decoration, size);
  }
  
  /**
   * Adds a link.
   *  
   * @param url  The URL.
   * @param text  The text.
   * @param color  The color.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   * @param size  The text size.
   */
  public void addLink(String url, String text, Color color, int decoration, int size) {
    StyledDocument doc = getStyledDocument();
    Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
    Style style = doc.addStyle("link-" + url + "-" + color.hashCode() + "-" + decoration, defaultStyle);  
    updateStyle(style, color, decoration, size);
    style.addAttribute(linkAttribute, url);
    
    // Load the text pane with styled text.
    try {
      doc.insertString(doc.getLength(), text, style);
    }
    catch (BadLocationException e) {
      Error.out(e);
    }
  }
  
  /**
   * Adds a link.
   *  
   * @param url  The URL.
   * @param image  The image.
   */
  public void addLink(String url, Image image) {
    StyledDocument doc = getStyledDocument();

    // The image must first be wrapped in a style
    Style style = doc.addStyle("image-" + url + "-" + image.hashCode(), null);
    StyleConstants.setIcon(style, new ImageIcon(image));
    style.addAttribute(linkAttribute, url);

    // Insert the image at the end of the text
    try {
      doc.insertString(doc.getLength(), "[img-" + image.hashCode() + "]", style);
    }
    catch (BadLocationException e) {
      Error.out(e);
    }
  }

  /**
   * Adds an image to this pane.
   * 
   * @param image  The image to add.
   */
  public void addImage(Image image) {
    StyledDocument doc = getStyledDocument();

    // The image must first be wrapped in a style
    Style style = doc.addStyle("image-" + image.hashCode(), null);
    StyleConstants.setIcon(style, new ImageIcon(image));

    // Insert the image at the end of the text
    try {
      doc.insertString(doc.getLength(), "[img-" + image.hashCode() + "]", style);
    }
    catch (BadLocationException e) {
      Error.out(e);
    }
  }  
  
  /**
   * Clears the text area.
   */
  public void clear() {
    try {
      StyledDocument doc = getStyledDocument();
      doc.remove(0, doc.getLength());
    }
    catch (BadLocationException e) {
      Error.out(e);
    }
  }
  
  /**
   * Updates the style with given parameters.
   *  
   * @param style  The style.
   * @param color  The color.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   * @param size  The text size.
   */
  private void updateStyle(Style style, Color color, int decoration, int size) {
    StyleConstants.setForeground(style, color);
    StyleConstants.setFontSize(style, size);
    StyleConstants.setBold(style, (decoration & DECORATION_BOLD) > 0);
    StyleConstants.setUnderline(style, (decoration & DECORATION_UNDERLINE) > 0);
    StyleConstants.setItalic(style, (decoration & DECORATION_ITALIC) > 0);
    StyleConstants.setSuperscript(style, (decoration & DECORATION_SUPERSCRIPT) > 0);
    StyleConstants.setSubscript(style, (decoration & DECORATION_SUBSCRIPT) > 0);
    StyleConstants.setStrikeThrough(style, (decoration & DECORATION_STRIKETHROUGH) > 0);
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
    super.paintComponent(g);
  }
}
