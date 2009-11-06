package king.lib.out;

import java.awt.Color;

/**
 * Document text area pane with anti-aliased and colored text. 
 *
 * @author    king 
 * @since     February 28, 2008
 */
public class DocumentTextPane extends AdvancedTextPane {
  
  /** The header color. */
  private Color headerColor = new Color(0x111111);
  /** The header decoration. */
  private int headerDecoration = DECORATION_BOLD;
  
  /** The paragraph color. */
  private Color paragraphColor = new Color(0x444444);
  /** The paragraph decoration. */
  private int paragraphDecoration = NO_DECORATION;
  
  /** The link color. */
  private Color linkColor = new Color(0x0000ff);
  /** The link decoration. */
  private int linkDecoration = DECORATION_UNDERLINE;

  
  /**
   * The constructor.
   */
  public DocumentTextPane() {
    setDefaultFont(FONT_TYPE_SANS_SERIF);
  }
  
  /**
   * Adds header to this pane.
   * 
   * @param header  The header to add.
   */
  public void addHeader(String header) {
    addHeader(header, 1);
  }
  
  /**
   * Adds header to this pane.
   * 
   * @param header  The header to add.
   * @param size  The header size. Sizes start at 1 (smallest and go up to 9 (biggest).
   */
  public void addHeader(String header, int size) {
    if ((size < 1) || (size > 9)) {
      throw new IllegalArgumentException("Incorrect header size (not 1-9): " + size);
    }
    
    // add message (no parsing)
    addText(header, headerColor, headerDecoration, getDefaultFont(), getDefaultSize() + size);
  }

  /**
   * Adds paragraph to this pane. The message is parsed for special codes.
   * 
   * @param paragraph  The paragraph to add.
   */
  public void addParagraph(String paragraph) {
    addParagraph(paragraph, paragraphColor);
  }
  
  /**
   * Adds paragraph to this pane. The message is parsed for special codes.
   * 
   * @param paragraph  The paragraph to add.
   * @param color  The color.
   */
  public void addParagraph(String paragraph, Color color) {
    // parse and add message
    addParagraph(paragraph, paragraphColor, false);
  }

  /**
   * Adds paragraph to this pane. The message is parsed for special codes.
   * 
   * @param paragraph  The paragraph to add.
   * @param color  The color.
   * @param bold  True for bold.
   */
  public void addParagraph(String paragraph, Color color, boolean bold) {
    // parse and add message
    int decoration = paragraphDecoration;
    if (bold) {
      decoration |= DECORATION_BOLD;
    }
    output(paragraph, color, decoration);
  }

  /**
   * Adds a line break.
   */
  public void addLineBreak() {
    addParagraph("\n");
  }
  
  /**
   * Adds a line break.
   * 
   * @param height  The height of the break. 0=normal, -1=overlaps previous, 1=double-spacing, 0.5=1.5-spacing.
   */
  public void addLineBreak(float height) {
    addText("\n", getDefaultColor(), getDefaultDecoration(), getDefaultFont(), getDefaultSize()
                , height, getDefaultAlign());
  }

  /**
   * Parses and outputs the message.
   * 
   * @param message  The message.
   * @param color  The color.
   * @param decoration  The decoration. Combination of DECORATION_? constants (additive).
   */
  private void output(String message, Color color, int decoration) {
    // check if link?
    int index = message.indexOf("http://");
    if (index < 0) {
      index = message.indexOf("https://");
    }
    if (index >= 0) {
      // output as link
      int endIndex = message.indexOf(" ", index);
      if (endIndex < 0) {
        endIndex = message.length();
      }
      
      // split up work
      output(message.substring(0, index), color, decoration);
      addLink(message.substring(index, endIndex), linkColor, decoration | linkDecoration);
      output(message.substring(endIndex), color, decoration);
      
      // exit
      return;
    }
    
    // no split found
    addText(message, color, decoration);
  }

  /**
   * Returns the header color.
   * 
   * @return the header color.
   */
  public Color getHeaderColor() {
    return headerColor;
  }

  /**
   * Sets the header color.
   * 
   * @param color  The header color to set.
   */
  public void setHeaderColor(Color color) {
    this.headerColor = color;
  }

  /**
   * Returns the header decoration.
   * 
   * @return  The header decoration.
   */
  public int getHeaderDecoration() {
    return headerDecoration;
  }

  /**
   * Sets the header decoration.
   * 
   * @param decoration  The header decoration to set.
   */
  public void setHeaderDecoration(int decoration) {
    this.headerDecoration = decoration;
  }

  /**
   * Returns the paragraph color.
   * 
   * @return  The paragraph color.
   */
  public Color getParagraphColor() {
    return paragraphColor;
  }

  /**
   * Sets the paragraph color.
   * 
   * @param color  The paragraph color to set.
   */
  public void setParagraphColor(Color color) {
    this.paragraphColor = color;
  }

  /**
   * Returns the paragraph decoration.
   * 
   * @return  The paragraph decoration.
   */
  public int getParagraphDecoration() {
    return paragraphDecoration;
  }

  /**
   * Sets the paragraph decoration.
   * 
   * @param decoration the paragraph decoration to set.
   */
  public void setParagraphDecoration(int decoration) {
    this.paragraphDecoration = decoration;
  }

  /**
   * Returns the link color.
   *
   * @return  The link color.
   */
  public Color getLinkColor() {
    return linkColor;
  }

  /**
   * Sets the link color.
   *
   * @param linkColor  The link color.
   */
  public void setLinkColor(Color linkColor) {
    this.linkColor = linkColor;
  }

  /**
   * Returns the link decoration.
   *
   * @return  The link decoration.
   */
  public int getLinkDecoration() {
    return linkDecoration;
  }

  /**
   * Sets the link decoration.
   *
   * @param linkDecoration  The link decoration.
   */
  public void setLinkDecoration(int linkDecoration) {
    this.linkDecoration = linkDecoration;
  } 
}
