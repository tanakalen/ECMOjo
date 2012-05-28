package king.lib.script.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import king.lib.out.AdvancedTextPane;

/**
 * A script console panel to output regular text (green) and errors (red).
 *
 * @author noblemaster
 * @since November 2, 2009
 */
public class ScriptConsolePanel extends JPanel {

  /** The output color. */
  private Color outputColor = new Color(0xff000000);
  /** The output color. */
  private Color successColor = new Color(0xff009700);
  /** The error color. */
  private Color errorColor = new Color(0xffc70000);
  
  /** The console area. */
  private AdvancedTextPane consoleArea;
  
  
  /**
   * The constructor.
   */
  public ScriptConsolePanel() {
    // set the look
    setOpaque(true);
    setLayout(new BorderLayout());
    
    // add the code area
    consoleArea = new AdvancedTextPane();
    consoleArea.setDefaultFont(AdvancedTextPane.FONT_TYPE_MONOSPACED);
    consoleArea.setDefaultSize(12);
    add(new JScrollPane(consoleArea), BorderLayout.CENTER);
  }
  
  /**
   * Sets a standard message.
   * 
   * @param message  The message.
   */
  public void setOutput(String message) {
    consoleArea.clear();
    addOutput(message);
  }
  
  /**
   * Adds an standard message.
   * 
   * @param message  The message.
   */
  public void addOutput(String message) {
    consoleArea.addText(message, outputColor);
  }
  
  /**
   * Sets a success message.
   * 
   * @param message  The message.
   */
  public void setSuccess(String message) {
    consoleArea.clear();
    addSuccess(message);
  }

  /**
   * Adds a success message.
   * 
   * @param message  The message.
   */
  public void addSuccess(String message) {
    consoleArea.addText(message, successColor);
  }

  /**
   * Sets an error message.
   * 
   * @param message  The message.
   */
  public void setError(String message) {
    consoleArea.clear();
    addError(message);
  }
  
  /**
   * Adds an error message.
   * 
   * @param message  The message.
   */
  public void addError(String message) {
    consoleArea.addText(message, errorColor);
  }

  /**
   * Returns the output color.
   *
   * @return  The output color.
   */
  public Color getOutputColor() {
    return outputColor;
  }

  /**
   * Sets the output color.
   *
   * @param outputColor  The output color.
   */
  public void setOutputColor(Color outputColor) {
    this.outputColor = outputColor;
  }
  
  /**
   * Returns the success color.
   *
   * @return  The success color.
   */
  public Color getSuccessColor() {
    return successColor;
  }

  /**
   * Sets the success color.
   *
   * @param successColor  The success color.
   */
  public void setSuccessColor(Color successColor) {
    this.successColor = successColor;
  }

  /**
   * Returns the error color.
   *
   * @return  The error color.
   */
  public Color getErrorColor() {
    return errorColor;
  }

  /**
   * Sets the error color.
   *
   * @param errorColor  The error color.
   */
  public void setErrorColor(Color errorColor) {
    this.errorColor = errorColor;
  }
  
  
  /**
   * Sets the position of the text insertion caret for the TextComponent. Note that the 
   * caret tracks change, so this may move if the underlying text of the component is 
   * changed. If the document is null, does nothing. The position must be between 0 
   * and the length of the component's text or else an exception is thrown. 
   * 
   * @param position  The position.
   */
  public void setCaretPosition(int position) {
    consoleArea.setCaretPosition(position);
  }
}
