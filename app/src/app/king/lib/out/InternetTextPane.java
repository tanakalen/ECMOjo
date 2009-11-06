package king.lib.out;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.StringReader;
import java.util.Stack;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.parser.ParserDelegator;

import king.lib.util.ImageResolver;

/**
 * Internet HTML text pane that outputs HTML text. 
 *
 * @author noblemaster
 * @since November 4, 2009
 */
public class InternetTextPane extends DocumentTextPane {
  
  /** The parser callback for HTML code. */
  class MyParserCallback extends ParserCallback {    
    
    /** The colors. */
    private Stack colors = new Stack();
    /** The fonts. */
    private Stack fonts = new Stack();
    /** The decorations. */
    private Stack decorations = new Stack();
    /** The sizes. */
    private Stack sizes = new Stack();   
    /** The link or null for none. */
    private String link = null;
    
    /**
     * Pushes an item to the stack.
     * 
     * @param item  The item.
     */
    public void pushColor(Color item) {
      colors.push(item);
    }
    
    /**
     * Pops an item from the stack.
     * 
     * @return  The item.
     */
    public Color popColor() {
      if (colors.size() > 0) {
        return (Color)colors.pop();
      }
      else {
        return getDefaultColor();
      }
    }
    
    /**
     * Peeks at the item on top of the stack.
     * 
     * @return  The item.
     */
    public Color peekColor() {
      if (colors.size() > 0) {
        return (Color)colors.peek();
      }
      else {
        return getDefaultColor();
      }
    }
    
    /**
     * Pushes an item to the stack.
     * 
     * @param item  The item.
     */
    public void pushFont(String item) {
      fonts.push(item);
    }
    
    /**
     * Pops an item from the stack.
     * 
     * @return  The item.
     */
    public String popFont() {
      if (fonts.size() > 0) {
        return (String)fonts.pop();
      }
      else {
        return getDefaultFont();
      }
    }
    
    /**
     * Peeks at the item on top of the stack.
     * 
     * @return  The item.
     */
    public String peekFont() {
      if (fonts.size() > 0) {
        return (String)fonts.peek();
      }
      else {
        return getDefaultFont();
      }
    }

    /**
     * Pushes an item to the stack.
     * 
     * @param item  The item.
     */
    public void pushDecoration(int item) {
      decorations.push(new Integer(item));
    }
    
    /**
     * Pops an item from the stack.
     * 
     * @return  The item.
     */
    public int popDecoration() {
      if (decorations.size() > 0) {
        return ((Integer)decorations.pop()).intValue();
      }
      else {
        return getDefaultDecoration();
      }
    }
    
    /**
     * Peeks at the item on top of the stack.
     * 
     * @return  The item.
     */
    public int peekDecoration() {
      if (decorations.size() > 0) {
        return ((Integer)decorations.peek()).intValue();
      }
      else {
        return getDefaultDecoration();
      }
    }

    /**
     * Pushes an item to the stack.
     * 
     * @param item  The item.
     */
    public void pushSize(int item) {
      sizes.push(new Integer(item));
    }
    
    /**
     * Pops an item from the stack.
     * 
     * @return  The item.
     */
    public int popSize() {
      if (sizes.size() > 0) {
        return ((Integer)sizes.pop()).intValue();
      }
      else {
        return getDefaultSize();
      }
    }
    
    /**
     * Peeks at the item on top of the stack.
     * 
     * @return  The item.
     */
    public int peekSize() {
      if (sizes.size() > 0) {
        return ((Integer)sizes.peek()).intValue();
      }
      else {
        return getDefaultSize();
      }
    }

    /**
     * Handles text.
     * 
     * @param data  The text.
     * @param pos  The position.
     */
    public void handleText(char[] data, int pos) {
      if (link != null) {
        addLink(link, new String(data), peekColor(), peekDecoration(), peekFont(), peekSize());
      }
      else {
        addText(new String(data), peekColor(), peekDecoration(), peekFont(), peekSize());
      }
    }
    
    /**
     * Handles an simple tag such as &lt;br&gt;.
     * 
     * @param tag  The tag.
     * @param att  The attributes.
     * @param pos  The position.
     */
    public void handleSimpleTag(Tag tag, MutableAttributeSet att, int pos) {
      if (tag == Tag.BR) {
        addText("\n", peekColor(), peekDecoration(), peekFont(), peekSize());
      }
      else if (tag == Tag.IMG) {
        String src = (String)att.getAttribute(HTML.Attribute.SRC);
        Image image = resolver.resolve(src);
        if (image != null) {
          if (link != null) {
            addImage(image, link);
          }
          else {
            addImage(image);
          }
        }
      }
    }    
    
    /**
     * Handles an start tag.
     * 
     * @param tag  The tag.
     * @param att  The attributes.
     * @param pos  The position.
     */
    public void handleStartTag(Tag tag, MutableAttributeSet att, int pos) {
      if (tag == Tag.H1) {
        pushDecoration(peekDecoration() | DECORATION_BOLD);
        pushSize(getDefaultSize() + 5);
      }
      else if (tag == Tag.H2) {
        pushDecoration(peekDecoration() | DECORATION_BOLD);
        pushSize(getDefaultSize() + 4);
      }
      else if (tag == Tag.H3) {
        pushDecoration(peekDecoration() | DECORATION_BOLD);
        pushSize(getDefaultSize() + 3);
      }
      else if (tag == Tag.H4) {
        pushDecoration(peekDecoration() | DECORATION_BOLD);
        pushSize(getDefaultSize() + 2);
      }
      else if (tag == Tag.H5) {
        pushDecoration(peekDecoration() | DECORATION_BOLD);
        pushSize(getDefaultSize() + 1);
      }
      else if (tag == Tag.FONT) {
        String color = (String)att.getAttribute(HTML.Attribute.COLOR);
        if (color == null) {
          // default color
          pushColor(peekColor());
        }
        else {
          if (color.startsWith("#")) {
            pushColor(new Color(Integer.parseInt(color.substring(1), 16)));
          }
          else {
            if (color.equalsIgnoreCase("red")) {
              pushColor(Color.RED);
            }
            else if (color.equalsIgnoreCase("green")) {
              pushColor(Color.GREEN);
            }
            else if (color.equalsIgnoreCase("blue")) {
              pushColor(Color.BLUE);
            }
            else if (color.equalsIgnoreCase("black")) {
              pushColor(Color.BLACK);
            }
            else if (color.equalsIgnoreCase("gray")) {
              pushColor(Color.GRAY);
            }
            else if (color.equalsIgnoreCase("white")) {
              pushColor(Color.WHITE);
            }
            else if (color.equalsIgnoreCase("yellow")) {
              pushColor(Color.YELLOW);
            }
            else if (color.equalsIgnoreCase("orange")) {
              pushColor(Color.ORANGE);
            }
            else if (color.equalsIgnoreCase("pink")) {
              pushColor(Color.PINK);
            }
            else if (color.equalsIgnoreCase("magenta")) {
              pushColor(Color.MAGENTA);
            }
            else {
              // default color
              pushColor(getDefaultColor());
            }
          }
        }
        String size = (String)att.getAttribute(HTML.Attribute.SIZE);
        if (size == null) {
          pushSize(peekSize());
        }
        else {
          if (size.startsWith("+")) {
            pushSize(peekSize() + Integer.parseInt(size.substring(1)));
          }
          else if (size.startsWith("-")) {
            pushSize(peekSize() - Integer.parseInt(size.substring(1)));
          }
          else {
            pushSize(Integer.parseInt(size));
          }
        }
      }     
      else if (tag == Tag.A) {
        link = (String)att.getAttribute(HTML.Attribute.HREF);
        pushColor(getLinkColor());
        pushDecoration(getLinkDecoration());
      }
      else if (tag == Tag.B) {
        pushDecoration(peekDecoration() | DECORATION_BOLD);
      }
      else if (tag == Tag.I) {
        pushDecoration(peekDecoration() | DECORATION_ITALIC);
      }
      else if (tag == Tag.U) {
        pushDecoration(peekDecoration() | DECORATION_UNDERLINE);
      }
      else if (tag == Tag.S) {
        pushDecoration(peekDecoration() | DECORATION_STRIKETHROUGH);
      }
      else if (tag == Tag.CODE) {
        pushFont(FONT_TYPE_MONOSPACED);
      }
    }
    
    /**
     * Handles an end tag.
     * 
     * @param tag  The tag.
     * @param pos  The position.
     */
    public void handleEndTag(Tag tag, int pos) {
      if (tag == Tag.H1) {
        addLineBreak();
        popDecoration();
        popSize();
      }
      else if (tag == Tag.H2) {
        addLineBreak();
        popDecoration();
        popSize();
      }
      else if (tag == Tag.H3) {
        addLineBreak();
        popDecoration();
        popSize();
      }
      else if (tag == Tag.H4) {
        addLineBreak();
        popDecoration();
        popSize();
      }
      else if (tag == Tag.H5) {
        addLineBreak();
        popDecoration();
        popSize();
      }
      else if (tag == Tag.FONT) {
        popColor();
        popSize();
      }
      else if (tag == Tag.A) {
        link = null;
        popColor();
        popDecoration();
      }
      else if (tag == Tag.B) {
        popDecoration();
      }
      else if (tag == Tag.I) {
        popDecoration();
      }
      else if (tag == Tag.U) {
        popDecoration();
      }
      else if (tag == Tag.S) {
        popDecoration();
      }
      else if (tag == Tag.CODE) {
        popFont();
      }
    }
  };

  /** The image resolver. */
  private ImageResolver resolver;
  
  
  /**
   * The constructor.
   */
  public InternetTextPane() {
    this(ImageResolver.getDefault());
  }
  
  /**
   * The constructor.
   * 
   * @param resolver  The image resolver. Responsible for retrieving images when they are used.
   */
  public InternetTextPane(ImageResolver resolver) {
    this.resolver = resolver;
    
    // set the default font size
    setDefaultSize(13);
  }

  /**
   * Adds the inputed HTML text.
   * <p>
   * Supported tags and attributes:
   * <ul>
   *   <li>&lt;h1&gt; to &lt;h5&gt; - Headers, where &lt;h1&gt; is the biggest.
   *   <li>&lt;br&gt; - Line break.
   *   <li>&lt;font color="#ff0000"&gt; - Set the color using a 6 digit HEX code.
   *   <li>&lt;a href="http://www.company.com"&gt;Company&lt;/a&gt; - A link.
   *   <li>&lt;img src="file:///c:\image.gif"&gt; - An image.
   *   <li>&lt;b&gt; - Bolded text.
   *   <li>&lt;i&gt; - Italic text.
   *   <li>&lt;u&gt; - Underlined text.
   *   <li>&lt;s&gt; - Strikethrough text.
   *   <li>&lt;code&gt; - Monospaced font.
   * </ul>
   * 
   * @param html  The HTML text.
   */
  public void addHTML(String html) {
    // parse and add HTML
    try {
      new ParserDelegator().parse(new StringReader(html), new MyParserCallback(), false);
    }
    catch (IOException e) {
      // it's a string, we shouldn't get any I/O exceptions?
      Error.out(e);
    }
  }
    
  /**
   * Adds the inputed BBCode.
   * <p>
   * Supported tags and attributes:
   * <ul>
   *   <li>[br] - Line break.
   *   <li>[color=ff0000] - Set the color using a 6 digit HEX code.
   *   <li>[size=+1] - Font size.
   *   <li>[url=http://www.company.com] - A link.
   *   <li>[mail=user@email.com] - An email link.
   *   <li>[img] - An image.
   *   <li>[b] - Bolded text.
   *   <li>[i] - Italic text.
   *   <li>[u] - Underlined text.
   *   <li>[s] - Strikethrough text.
   *   <li>[code] - Monospaced font.
   * </ul>
   * Line breaks will be converted to [br] first.
   * 
   * @param bbCode  The BBCode.
   */
  public void addBBCode(String bbCode) {
    // convert to special HTML characters as needed 
    StringBuilder text = new StringBuilder();
    for (int i = 0; i < bbCode.length(); i++) {
      char ch = bbCode.charAt(i);
      switch (ch) { 
        case '<': 
          text.append("&lt;");
          break;       
        case '>':
          text.append("&gt;");
          break;        
        case '&':
          text.append("&amp;");
          break;           
        default:
          text.append(ch);
      }     
    }
    
    // replace linebreaks with [br]
    String html = text.toString();
    html = html.replace("\r\n", "[br]");
    html = html.replace("\n", "[br]");

    // convert BBCode -> HTML
    html = html.replaceAll("\\[br\\]", "<br>");
    html = html.replaceAll("\\[b\\](.*?)\\[/b\\]", "<b>$1</b>");
    html = html.replaceAll("\\[i\\](.*?)\\[/i\\]", "<i>$1</i>");
    html = html.replaceAll("\\[u\\](.*?)\\[/u\\]", "<u>$1</u>");
    html = html.replaceAll("\\[s\\](.*?)\\[/s\\]", "<s>$1</s>");
    html = html.replaceAll("\\[url\\=(.*?)\\](.*?)\\[/url\\]", "<a href=\"$1\">$2</a>");
    html = html.replaceAll("\\[url\\](.*?)\\[/url\\]", "<a href=\"$1\">$1</a>");
    html = html.replaceAll("\\[img\\](.*?)\\[/img\\]", "<img src=\"$1\">");
    html = html.replaceAll("\\[mail\\=(.*?)\\](.*?)\\[/mail\\]", "<a href=\"mailto:$1\">$2</a>");
    html = html.replaceAll("\\[mail\\](.*?)\\[/mail\\]", "<a href=\"mailto:$1\">$1</a>");
    html = html.replaceAll("\\[size\\=(.*?)\\](.*?)\\[/size\\]", "<font size=\"$1\">$2</font>");
    html = html.replaceAll("\\[color\\=(.*?)\\](.*?)\\[/color\\]", "<font color=\"$1\">$2</font>");
    
    // and add as HTML
System.out.println(html);
    addHTML(html);
  }
}
