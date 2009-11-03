package king.lib.script.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import king.lib.script.control.ScriptException;
import king.lib.script.control.ScriptRunner;
import king.lib.script.model.Script;

/**
 * A script editor with syntax pane, console output.
 *
 * @author noblemaster
 * @since November 2, 2009
 */
public class ScriptEditorPanel extends JPanel {

  /** The syntax panel. */
  private ScriptSyntaxPanel syntaxPanel;
  /** The console panel. */
  private ScriptConsolePanel consolePanel;
  
  /** The compile button. */
  private JButton compileButton;
  

  /**
   * The constructor.
   */
  public ScriptEditorPanel() {
    
    // add the syntax panel
    syntaxPanel = new ScriptSyntaxPanel();
    add(syntaxPanel);
    
    // add the console panel
    consolePanel = new ScriptConsolePanel();
    add(consolePanel);
    
    // add the compile button
//    compileButton = new JButton(Translator.translate("action.Compile[i18n]: Compile"));
    compileButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        try {
//          consolePanel.setOutput(Translator.translate("action.Compiling[i18n]: Compiling...") + "\n");
          ScriptRunner.compile(getScript());
//          consolePanel.addOutput(Translator.translate("??????.CompiledOK[i18n]: Compiled. OK!") + "\n");
        }
        catch (ScriptException e) {
//          consolePanel.addOutput(Translator.translate("error.CompilingErrors[i18n]: Compiling Errors:") + "\n");
//          consolePanel.addOutput(Translator.translate.getMessage());
        }
      }      
    });
    add(compileButton);    
  }
  
  /**
   * Returns the script.
   * 
   * @return  The script.
   */
  public Script getScript() {
    return syntaxPanel.getScript();
  }
  
  /**
   * Sets a script.
   * 
   * @param script  The script.
   */
  public void setScript(Script script) {
    syntaxPanel.setScript(script);
  }
}
