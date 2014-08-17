package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.hawaii.jabsom.tri.ecmo.app.model.ScenarioFile;
import jsyntaxpane.DefaultSyntaxKit;
import king.lib.script.control.CompileException;
import king.lib.script.control.ScriptCompiler;
import king.lib.script.model.Language;
import king.lib.script.model.Script;
import king.lib.script.view.ScriptConsolePanel;
import king.lib.script.view.ScriptSyntaxPanel;
//import king.lib.util.DateTime;
import king.lib.util.Translator;

/**
 * The scenario editor.
 *
 * @author noblemaster
 * @since December 8, 2009
 */
public class ScenarioEditPanel extends JPanel {

  /** The parameters panel. */
  private JEditorPane parametersPanel;
  /** The syntax panel. */
  private ScriptSyntaxPanel scriptPanel;
  /** The console panel. */
  private ScriptConsolePanel consolePanel;

  
  /**
   * The constructor.
   */
  public ScenarioEditPanel() {  
    // set layout
    setOpaque(false);
    setLayout(new FormLayout(
          "fill:1px:grow"
        , "pref, fill:3px:grow, pref, fill:2px:grow, pref, fill:60px"
    ));
    CellConstraints cc = new CellConstraints();
    
    // add the parameters panel
    add(new JLabel("Scenario Parameters"), cc.xy(1, 1));
    parametersPanel = new JEditorPane();
    add(new JScrollPane(parametersPanel), cc.xy(1, 2));
    DefaultSyntaxKit.initKit();
    parametersPanel.setContentType("text/properties");
    
    // add the script panel
    add(new JLabel("Scenario Script (Optional)"), cc.xy(1, 3));
    scriptPanel = new ScriptSyntaxPanel();
    add(scriptPanel, cc.xy(1, 4));
    Script script = new Script();
    script.setLang(Language.PNUTS.getName());
    script.setCode("");
    scriptPanel.setScript(script);
    
    // add the console panel
    add(new JLabel("Console Output"), cc.xy(1, 5));
    consolePanel = new ScriptConsolePanel();
    add(consolePanel, cc.xy(1, 6));   
  }
  
  /** 
   * Compiles a scenario. Errors are output to the console.
   */
  public void compile() {
    try {
//      String timestamp = new DateTime().toString();
      String timestamp = ZonedDateTime.now(ZoneId.of("UTC")).format(
          DateTimeFormatter.ofPattern("yyyy.MM.dd G 'at' HH:mm:ss z"));
      consolePanel.setOutput(timestamp + " | " + Translator.getString("text.Compiling[i18n]: Compiling...") +"\n");
      ScriptCompiler.compile(scriptPanel.getScript());
      consolePanel.addSuccess(Translator.getString("text.CompiledOK[i18n]: Compiled. OK!") + "\n");
    }
    catch (CompileException e) {
      consolePanel.addError(Translator.getString("error.CompilingErrors[i18n]: Compiling Errors:") + "\n");
      consolePanel.addError(Translator.getString(e.getMessage()));
      
      // select the line if any
      if (e.getLine() > 0) {
        scriptPanel.select(e.getLine());
      }
    }
  }
  
  /**
   * Returns the scenario.
   * 
   * @return  The scenario.
   */
  public ScenarioFile getScenario() {
    ScenarioFile scenario = new ScenarioFile();
    scenario.setParameters(parametersPanel.getText());
    scenario.setScript(scriptPanel.getScript());
    return scenario;
  }
  
  /**
   * Sets the scenario.
   * 
   * @param scenario  The scenario.
   */
  public void setScenario(ScenarioFile scenario) {
    parametersPanel.setText(scenario.getParameters());
    parametersPanel.setCaretPosition(0);
    scriptPanel.setScript(scenario.getScript());       
    scriptPanel.setCaretPosition(0);
  }
}
