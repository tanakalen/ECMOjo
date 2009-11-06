package king.lib.out;

import javax.swing.JTextPane;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import javax.swing.text.StyleConstants;

import java.awt.Cursor;
import java.awt.Font;
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
 * Advanced text pane with many many fancy-pantsy features. 
 * <p>
 * NOTE: Serif type fonts appear to produce strange line breaks occasionally!? (CA)
 * 
 * @author Christoph Aschwanden
 * @since February 27, 2008
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
  
  /** A Serif (or Roman) font: "Times", if not found then the default. */
  public static final String FONT_TYPE_SERIF = "${serif}";
  /** A Sans-Serif font: "Arial", if not found then the default. */
  public static final String FONT_TYPE_SANS_SERIF = "${sans-serif}";
  /** A Monospaced font: "Courier New", if not found then "Courier", then the default. */
  public static final String FONT_TYPE_MONOSPACED = "${monospaced}";
  
  /** Left alignment. */
  public static final int ALIGN_LEFT = StyleConstants.ALIGN_LEFT;
  /** Right alignment. */
  public static final int ALIGN_RIGHT = StyleConstants.ALIGN_RIGHT;
  /** Center alignment. */
  public static final int ALIGN_CENTER = StyleConstants.ALIGN_CENTER;
  /** Justified alignment. */
  public static final int ALIGN_JUSTIFIED = StyleConstants.ALIGN_JUSTIFIED;
  
  /** The link attribute. */
  private final String linkAttribute = "linkAttribute";
  
  /** The default color. */
  private Color defaultColor = Color.BLACK;
  /** The default font. */
  private String defaultFont = FONT_TYPE_MONOSPACED;
  /** The default decoration. */
  private int defaultDecoration = NO_DECORATION;
  /** The default size. */
  private int defaultSize = 12;
  /** The default line spacing. */
  private float defaultLineSpacing = 0f;
  /** The default alignment. */
  private int defaultAlign = ALIGN_LEFT;
  
  
  /**
   * Constructor for chat area panel.
   */
  public AdvancedTextPane() {
    // set defaults
    setEnabled(true);
    setEditable(false);
    setOpaque(true);
        
    // build default style
    Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
    StyleConstants.setSpaceAbove(defaultStyle, 0f);
    StyleConstants.setSpaceBelow(defaultStyle, 0f);
    StyledDocument doc = getStyledDocument();
    doc.setParagraphAttributes(0, 0, defaultStyle, true);

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
   * Adds text to this pane. Uses the default settings for decoration, color, font and size.
   * 
   * @param text  The text to add.
   */
  public void addText(String text) {
    addText(text, defaultColor, defaultDecoration);
  }
  
  /**
   * Adds text to this pane. Uses the default settings for color, font and size.
   * 
   * @param text  The text to add.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   */
  public void addText(String text, int decoration) {
    addText(text, defaultColor, decoration);
  }

  /**
   * Adds text to this pane. Uses the default settings for decoration, font and size.
   * 
   * @param text  The text to add.
   * @param color  The color of the text.
   */
  public void addText(String text, Color color) {
    addText(text, color, defaultDecoration);
  }

  /**
   * Adds text to this pane. Uses the default settings for font and size.
   * 
   * @param text  The text to add.
   * @param color  The color of the text.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   */
  public void addText(String text, Color color, int decoration) {
    addText(text, color, decoration, defaultFont, defaultSize);
  }
  
  /**
   * Adds text to this pane.
   * 
   * @param text  The text to add.
   * @param color  The color of the text.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   * @param font  The font. Either a font-family name (e.g. "Arial") or one of the FONT_TYPE_* constants.
   * @param size  The text size.
   */
  public void addText(String text, Color color, int decoration, String font, int size) {
    addText(text, color, decoration, font, size, defaultLineSpacing, defaultAlign);
  }
  
  /**
   * Adds text to this pane.
   * 
   * @param text  The text to add.
   * @param color  The color of the text.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   * @param font  The font. Either a font-family name (e.g. "Arial") or one of the FONT_TYPE_* constants.
   * @param size  The text size.
   * @param lineSpacing  The line spacing as a factor of size. 0=normal, -1=overlaps previous, 1=double-spacing.
   * @param align  The alignment. Using one of the ALIGN_* constants.
   */
  public void addText(String text, Color color, int decoration, String font, int size, float lineSpacing, int align) {
    StyledDocument doc = getStyledDocument();
    SimpleAttributeSet att = att(color, decoration, font, size, lineSpacing, align);
    
    // Load the text pane with styled text.
    try {
      doc.setParagraphAttributes(doc.getLength(), 0, att, true);
      doc.insertString(doc.getLength(), text, att);
    }
    catch (BadLocationException e) {
      Error.out(e);
    }
  }
  
  /**
   * Adds a link. Use DECORATION_UNDERLINE as needed. Uses the default settings for font and size.
   *  
   * @param url  The URL. Will also be used as the text.
   * @param color  The color.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   */
  public void addLink(String url, Color color, int decoration) {
    addLink(url, url, color, decoration, defaultFont, defaultSize);
  }

  /**
   * Adds a link. Use DECORATION_UNDERLINE as needed. Uses the default settings for font and size.
   *  
   * @param url  The URL.
   * @param text  The text for the URL.
   * @param color  The color.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   */
  public void addLink(String url, String text, Color color, int decoration) {
    addLink(url, text, color, decoration, defaultFont, defaultSize);
  }
  
  /**
   * Adds a link. Use DECORATION_UNDERLINE as needed.
   *  
   * @param url  The URL. Will also be used as the text.
   * @param color  The color.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   * @param font  The font. Either a font-family name (e.g. "Arial") or one of the FONT_TYPE_* constants.
   * @param size  The text size.
   */
  public void addLink(String url, Color color, int decoration, String font, int size) {
    addLink(url, url, color, decoration, font, size);
  }
  
  /**
   * Adds a link. Use DECORATION_UNDERLINE as needed.
   *  
   * @param url  The URL. Will also be used as the text.
   * @param color  The color.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   * @param font  The font. Either a font-family name (e.g. "Arial") or one of the FONT_TYPE_* constants.
   * @param size  The text size.
   * @param lineSpacing  The line spacing as a factor of size. 0=normal, -1=overlaps previous, 1=double-spacing.
   * @param align  The alignment. Using one of the ALIGN_* constants.
   */
  public void addLink(String url, Color color, int decoration, String font, int size, float lineSpacing, int align) {
    addLink(url, url, color, decoration, font, size, lineSpacing, align);
  }
  
  /**
   * Adds a link. Use DECORATION_UNDERLINE as needed.
   *  
   * @param url  The URL.
   * @param text  The text for the URL.
   * @param color  The color.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   * @param font  The font. Either a font-family name (e.g. "Arial") or one of the FONT_TYPE_* constants.
   * @param size  The text size.
   */
  public void addLink(String url, String text, Color color, int decoration, String font, int size) {
    addLink(url, text, color, decoration, font, size, defaultLineSpacing, defaultAlign);
  }
  
  /**
   * Adds a link. Use DECORATION_UNDERLINE as needed.
   *  
   * @param url  The URL.
   * @param text  The text for the URL.
   * @param color  The color.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   * @param font  The font. Either a font-family name (e.g. "Arial") or one of the FONT_TYPE_* constants.
   * @param size  The text size.
   * @param lineSpacing  The line spacing as a factor of size. 0=normal, -1=overlaps previous, 1=double-spacing.
   * @param align  The alignment. Using one of the ALIGN_* constants.
   */
  public void addLink(String url, String text, Color color, int decoration, String font, int size
                    , float lineSpacing, int align) {
    StyledDocument doc = getStyledDocument();
    SimpleAttributeSet att = att(color, decoration, font, size, lineSpacing, align);
    att.addAttribute(linkAttribute, url);
    
    // Load the text pane with styled text.
    try {
      doc.setParagraphAttributes(doc.getLength(), 0, att, true);
      doc.insertString(doc.getLength(), text, att);
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
    SimpleAttributeSet att = att(defaultColor, defaultDecoration, defaultFont, defaultSize
                               , defaultLineSpacing, defaultAlign);
    StyleConstants.setIcon(att, new ImageIcon(image));
    att.addAttribute(linkAttribute, url);

    // Insert the image at the end of the text
    try {
      doc.setParagraphAttributes(doc.getLength(), 0, att, true);
      doc.insertString(doc.getLength(), "[img-" + image.hashCode() + "]", att);
    }
    catch (BadLocationException e) {
      Error.out(e);
    }
  }

  /**
   * Adds an image to this text pane.
   * 
   * @param image  The image to add.
   */
  public void addImage(Image image) {
    StyledDocument doc = getStyledDocument();

    // The image must first be wrapped in a style
    SimpleAttributeSet att = att(defaultColor, defaultDecoration, defaultFont, defaultSize
                               , defaultLineSpacing, defaultAlign);
    StyleConstants.setIcon(att, new ImageIcon(image));

    // Insert the image at the end of the text
    try {
      doc.setParagraphAttributes(doc.getLength(), 0, att, true);
      doc.insertString(doc.getLength(), "[img-" + image.hashCode() + "]", att);
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
   * Returns the default color.
   *
   * @return  The default color.
   */
  public Color getDefaultColor() {
    return defaultColor;
  }

  /**
   * Sets the default color.
   *
   * @param defaultColor  The default color.
   */
  public void setDefaultColor(Color defaultColor) {
    this.defaultColor = defaultColor;
  }

  /**
   * Returns the default font.
   *
   * @return  The default font.
   */
  public String getDefaultFont() {
    return defaultFont;
  }

  /**
   * Sets the default font.
   *
   * @param defaultFont  The default font.
   */
  public void setDefaultFont(String defaultFont) {
    this.defaultFont = defaultFont;
  }

  /**
   * Returns the default decoration.
   *
   * @return  The default decoration.
   */
  public int getDefaultDecoration() {
    return defaultDecoration;
  }

  /**
   * Sets the default decoration.
   *
   * @param defaultDecoration  The default decoration.
   */
  public void setDefaultDecoration(int defaultDecoration) {
    this.defaultDecoration = defaultDecoration;
  }

  /**
   * Returns the default font size.
   *
   * @return  The default font size.
   */
  public int getDefaultSize() {
    return defaultSize;
  }

  /**
   * Sets the default font size.
   *
   * @param defaultSize  The default font size.
   */
  public void setDefaultSize(int defaultSize) {
    this.defaultSize = defaultSize;
  }
  
  /**
   * Returns the default line spacing.
   *
   * @return  The default line spacing. 0=normal, -1=overlaps previous, 1=double-spacing, 0.5=1.5-spacing.
   */
  public float getDefaultLineSpacing() {
    return defaultLineSpacing;
  }

  /**
   * Sets the default line spacing.
   *
   * @param defaultLineSpacing  The default spacing. 0=normal, -1=overlaps previous, 1=double-spacing, 0.5=1.5-spacing.
   */
  public void setDefaultLineSpacing(float defaultLineSpacing) {
    this.defaultLineSpacing = defaultLineSpacing;
  }

  /**
   * Returns the default align.
   *
   * @return  The default align.
   */
  public int getDefaultAlign() {
    return defaultAlign;
  }

  /**
   * Sets the default align.
   *
   * @param defaultAlign  The default align.
   */
  public void setDefaultAlign(int defaultAlign) {
    this.defaultAlign = defaultAlign;
  }

  /**
   * Creates and returns the attribute set.
   *  
   * @param color  The color.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   * @param font  The font. Either a font-family name (e.g. "Arial") or one of the FONT_TYPE_* constants.
   * @param size  The text size.
   * @param lineSpacing  The line spacing as a factor of size. 0=normal, -1=overlaps previous, 1=double-spacing.
   * @param align  The alignment. Using one of the ALIGN_* constants.
   * @return  The attribute set.
   */
  private SimpleAttributeSet att(Color color, int decoration, String font, int size, float lineSpacing, int align) {
    SimpleAttributeSet att = new SimpleAttributeSet();
    StyleConstants.setForeground(att, color);
    StyleConstants.setFontSize(att, size);
    StyleConstants.setFontFamily(att, matchingFont(font));
    StyleConstants.setLineSpacing(att, lineSpacing);
    StyleConstants.setAlignment(att, align);
    StyleConstants.setBold(att, (decoration & DECORATION_BOLD) > 0);
    StyleConstants.setUnderline(att, (decoration & DECORATION_UNDERLINE) > 0);
    StyleConstants.setItalic(att, (decoration & DECORATION_ITALIC) > 0);
    StyleConstants.setSuperscript(att, (decoration & DECORATION_SUPERSCRIPT) > 0);
    StyleConstants.setSubscript(att, (decoration & DECORATION_SUBSCRIPT) > 0);
    StyleConstants.setStrikeThrough(att, (decoration & DECORATION_STRIKETHROUGH) > 0);  
    return att;
  }
  
  /**
   * Returns the actual or matching font for the given input font.
   * 
   * @param font  The font. Either a font-family name (e.g. "Arial") or one of the FONT_TYPE_* constants.
   * @return  The actual or matching font.
   */
  private String matchingFont(String font) {
    if (font.equals(FONT_TYPE_SERIF)) {
      Font match = Font.decode("Times");
      if (match == null) {
        return Font.SERIF;
      }
      else {
        return match.getFamily();
      }
    }
    else if (font.equals(FONT_TYPE_SANS_SERIF)) {
      Font match = Font.decode("Arial");
      if (match == null) {
        return Font.SANS_SERIF;
      }
      else {
        return match.getFamily();
      }
    }
    else if (font.equals(FONT_TYPE_MONOSPACED)) {
      Font match = Font.decode("Courier New");
      if (match == null) {
        match = Font.decode("Courier");
        if (match == null) {
          return Font.SANS_SERIF;
        }
        else {
          return match.getFamily();
        }
      }
      else {
        return match.getFamily();
      }
    }
    else {
      return font;
    }
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
