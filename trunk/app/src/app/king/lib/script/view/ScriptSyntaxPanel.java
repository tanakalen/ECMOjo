package king.lib.script.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import king.lib.script.model.Script;
import king.lib.script.model.ScriptType;

/**
 * A syntax editor panel.
 *
 * @author noblemaster
 * @since November 2, 2009
 */
public class ScriptSyntaxPanel extends JPanel {

  /** The ID. */
  private long id;
  /** The language. */
  private ScriptType lang;
  
  /** The text. */
  private JTextArea codeArea;
  
  
  /**
   * The constructor.
   */
  public ScriptSyntaxPanel() {
    // set the look
    setOpaque(true);
    setLayout(new BorderLayout());
    
    // add the code area
    codeArea = new JTextArea();
    add(new JScrollPane(codeArea), BorderLayout.CENTER);
  }
  
  /**
   * Returns the script.
   * 
   * @return  The script.
   */
  public Script getScript() {
    Script script = new Script();
    script.setId(id);
    script.setLang(lang);
    script.setCode(codeArea.getText());
    return script;
  }
  
  /**
   * Sets a script.
   * 
   * @param script  The script.
   */
  public void setScript(Script script) {
    id = script.getId();
    lang = script.getLang();
    codeArea.setText(script.getCode());
   
    // set the syntax panel settings
    // FIXME: set syntax style, e.g. "text/pnuts"???
  }
  
  /**
   * Selects the inputed line number.
   * 
   * @param line  The line number starting with 1.
   */
  public void select(long line) {
    codeArea.select(1, 100);  
    // FIXME: select the correct line number & scroll to it!!!
  }
}
