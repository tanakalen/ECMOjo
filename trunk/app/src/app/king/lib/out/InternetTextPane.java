package king.lib.out;

import java.io.IOException;
import java.io.StringReader;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.parser.ParserDelegator;

/**
 * Internet HTML text pane that outputs HTML text. 
 *
 * @author noblemaster
 * @since November 4, 2009
 */
public class InternetTextPane extends DocumentTextPane {
  
  /**
   * Adds the inputed HTML text.
   * <p>
   * Supported tags and attributes (everything else is NOT supported):
   * <ul>
   *   <li>&lt;h1&gt; - &lt;h6&gt; - Header, where &lt;h1&gt; is the biggest header.
   *   <li>&lt;br&gt; - Line break.
   *   <li>&lt;font color="#XXXXXX"&gt; - Set the color using a 6 digit HEX code for XXXXXX.
   * </ul>
   * 
   * @param html  The HTML text.
   */
  public void addHTML(String html) {
    // parse and add HTML
    HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback() {
      public void handleText(char[] data, int pos) {
        addText(new String(data));
      }
      public void handleSimpleTag(Tag tag, MutableAttributeSet arg1, int arg2) {
        
      }     
      public void handleStartTag(Tag tag, MutableAttributeSet arg1, int arg2) {
        if (tag == Tag.H1) {
          
        }
      }
      public void handleEndTag(Tag tag, int arg1) {
        
      }
    };
    try {
      new ParserDelegator().parse(new StringReader(html), callback, false);
    }
    catch (IOException e) {
      // it's a string, we shouldn't get any I/O exceptions?
      Error.out(e);
    }
  }
}
