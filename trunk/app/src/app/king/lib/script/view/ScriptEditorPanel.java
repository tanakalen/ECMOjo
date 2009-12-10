package king.lib.script.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import king.lib.script.control.CompileException;
import king.lib.script.control.ScriptCompiler;
import king.lib.script.model.Script;
import king.lib.util.DateTime;
import king.lib.util.Translator;

/**
 * A script editor with syntax pane, console output.
 *
 * @author noblemaster
 * @since November 2, 2009
 */
public class ScriptEditorPanel extends JPanel {

  /** The split pane. */
  private JSplitPane splitPane;
  
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
    // set the look
    setOpaque(true);
    setLayout(new BorderLayout());
    
    // syntax panel
    syntaxPanel = new ScriptSyntaxPanel();
    
    // console panel
    consolePanel = new ScriptConsolePanel();
    
    // compile button
    compileButton = new JButton(Translator.getString("action.Compile[i18n]: Compile"));
    compileButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        try {
          String timestamp = new DateTime().toString();
          consolePanel.setOutput(timestamp + " | " + Translator.getString("text.Compiling[i18n]: Compiling...") +"\n");
          ScriptCompiler.compile(getScript());
          consolePanel.addSuccess(Translator.getString("text.CompiledOK[i18n]: Compiled. OK!") + "\n");
        }
        catch (CompileException e) {
          consolePanel.addError(Translator.getString("error.CompilingErrors[i18n]: Compiling Errors:") + "\n");
          consolePanel.addError(Translator.getString(e.getMessage()));
          
          // select the line if any
          if (e.getLine() > 0) {
            syntaxPanel.select(e.getLine());
          }
        }
      }      
    });

    // action panel
    JPanel actionPanel = new JPanel();
    actionPanel.setLayout(new BorderLayout());
    actionPanel.add(consolePanel, BorderLayout.CENTER);
    actionPanel.add(compileButton, BorderLayout.EAST);
    
    // add a split pane
    splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, syntaxPanel, actionPanel);
    splitPane.setResizeWeight(0.90);
    splitPane.setBorder(null);
    add(splitPane, BorderLayout.CENTER);    
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
