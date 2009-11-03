package king.lib.script.view;

import javax.swing.JPanel;
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
  private JTextArea code;
  
  
  /**
   * The constructor.
   */
  public ScriptSyntaxPanel() {

    // add the code area
    
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
    script.setCode(code.getText());
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
    code.setText(script.getCode());
    
    // set the syntax panel settings
    // TODO: set syntax style, e.g. "text/pnuts"???
  }
}
