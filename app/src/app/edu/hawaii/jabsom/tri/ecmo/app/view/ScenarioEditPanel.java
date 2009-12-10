package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import jsyntaxpane.DefaultSyntaxKit;

import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioLoader;
import edu.hawaii.jabsom.tri.ecmo.app.model.ScenarioFile;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogOption;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogType;

import king.lib.access.Access;
import king.lib.access.LocalHookup;
import king.lib.script.control.CompileException;
import king.lib.script.control.ScriptCompiler;
import king.lib.script.model.Language;
import king.lib.script.model.Script;
import king.lib.script.view.ScriptConsolePanel;
import king.lib.script.view.ScriptSyntaxPanel;
import king.lib.util.DateTime;
import king.lib.util.SimpleFileFilter;
import king.lib.util.Translator;

/**
 * The scenario editor.
 *
 * @author noblemaster
 * @since December 8, 2009
 */
public class ScenarioEditPanel extends JPanel {

  /** Listener for selections. */
  public static interface ScenarioEditListener {
    
    /**
     * Called when "Run" got selected.
     * 
     * @param path  The path selected.
     */
    void handleRun(String path);
    
    /**
     * Called when "Exit" got selected.
     */
    void handleExit();
  };
  
  /** Listeners for changes. */
  private List<ScenarioEditListener> listeners = new ArrayList<ScenarioEditListener>();

  /** The file path. */
  private String path = null;
  
  /** The open button. */
  private JButton openButton;
  /** The save button. */
  private JButton saveButton;
  /** The save as button. */
  private JButton saveAsButton;
  /** The compile button. */
  private JButton compileButton;
  /** The run button. */
  private JButton runButton;
  /** The exit button. */
  private JButton exitButton;

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
        , "pref, pref, fill:3px:grow, pref, fill:2px:grow, pref, fill:60px"
    ));
    CellConstraints cc = new CellConstraints();
    
    // add the menu panel
    JPanel menuPanel = new JPanel();
    menuPanel.setLayout(new FormLayout(
        "0px, 100px, 2px, 100px, 2px, 100px, 2px, 100px, 2px, 100px, 2px, 100px, 0px"
      , "2px, pref, 5px"
    ));
    menuPanel.setOpaque(false);
    add(menuPanel, cc.xy(1, 1));
    
    openButton = new JButton(Translator.getString("action.Open[i18n]: Open..."));
    openButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // choose a file
        SimpleFileFilter filter = new SimpleFileFilter(new String[]{"scn"}, "ECMOjo Scenario");
        JFileChooser chooser = new JFileChooser(Access.getInstance().getScenarioDir());
        chooser.addChoosableFileFilter(filter);
        int returnVal = chooser.showOpenDialog(ScenarioEditPanel.this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
          // obtain the file
          File file = chooser.getSelectedFile();
          if (file.exists()) {
            // load the scenario
            load(file.getAbsolutePath());
          }
        }
      }      
    });
    menuPanel.add(openButton, cc.xy(2, 2));   

    saveButton = new JButton(Translator.getString("action.Save[i18n]: Save"));
    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // save the scenario
        save(path);
      }      
    });
    menuPanel.add(saveButton, cc.xy(4, 2));   
    saveButton.setEnabled(false);
    
    saveAsButton = new JButton(Translator.getString("action.SaveAs[i18n]: Save As..."));
    saveAsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // choose a file
        SimpleFileFilter filter = new SimpleFileFilter(new String[]{"scn"}, "ECMOjo Scenario");
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(filter);
        chooser.setSelectedFile(new File(path));
        int returnVal = chooser.showSaveDialog(ScenarioEditPanel.this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
          // save the file
          save(chooser.getSelectedFile().getAbsolutePath());
          path = chooser.getSelectedFile().getAbsolutePath();
        }
      }      
    });
    menuPanel.add(saveAsButton, cc.xy(6, 2));   

    compileButton = new JButton(Translator.getString("action.Compile[i18n]: Compile"));
    compileButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        try {
          String timestamp = new DateTime().toString();
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
    });
    menuPanel.add(compileButton, cc.xy(8, 2));
    
    runButton = new JButton(Translator.getString("action.Run[i18n]: Run..."));
    runButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // save the scenario first
        save(path);
        
        // run it...
        for (ScenarioEditListener listener: listeners) {
          listener.handleRun(path);
        }
      }      
    });
    menuPanel.add(runButton, cc.xy(10, 2));   

    exitButton = new JButton(Translator.getString("action.Exit[i18n]: Exit"));
    exitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // exit the editor
        for (ScenarioEditListener listener: listeners) {
          listener.handleExit();
        }
      }      
    });
    menuPanel.add(exitButton, cc.xy(12, 2));   

    // add the parameters panel
    add(new JLabel("Scenario Parameters"), cc.xy(1, 2));
    parametersPanel = new JEditorPane();
    add(new JScrollPane(parametersPanel), cc.xy(1, 3));
    DefaultSyntaxKit.initKit();
    parametersPanel.setContentType("text/properties");
    
    // add the script panel
    add(new JLabel("Scenario Script (Optional)"), cc.xy(1, 4));
    scriptPanel = new ScriptSyntaxPanel();
    add(scriptPanel, cc.xy(1, 5));
    Script script = new Script();
    script.setLang(Language.PNUTS.getName());
    script.setCode("");
    scriptPanel.setScript(script);
    
    // add the console panel
    add(new JLabel("Console Output"), cc.xy(1, 6));
    consolePanel = new ScriptConsolePanel();
    add(consolePanel, cc.xy(1, 7));   
  }
    
  /**
   * Loads the scenario.
   * 
   * @param path  The path.
   */
  private void load(String path) {
    try{
      ScenarioFile scenario = ScenarioLoader.loadFile(LocalHookup.getInstance(), path);
      
      // populate the GUI components with scenario values
      parametersPanel.setText(scenario.getParameters());
      parametersPanel.setCaretPosition(0);
      scriptPanel.setScript(scenario.getScript());       
      scriptPanel.setCaretPosition(0);
      saveButton.setEnabled(true);
      
      // save the path
      this.path = path;
    }
    catch (IOException e) {
      // output the error message
      StandardDialog.showDialog(ScenarioEditPanel.this, DialogType.ERROR, DialogOption.OK
          , "Error Encountered"
          , e.getMessage());
    }
  }
  
  /**
   * Saves the scenario.
   * 
   * @param path  The path.
   */
  private void save(String path) {
    try{
      // the file
      ScenarioFile scenario = new ScenarioFile();
      scenario.setParameters(parametersPanel.getText());
      scenario.setScript(scriptPanel.getScript());
      
      // and save it
      ScenarioLoader.saveFile(LocalHookup.getInstance(), path, scenario);
    }
    catch (IOException e) {
      // output the error message
      StandardDialog.showDialog(ScenarioEditPanel.this, DialogType.ERROR, DialogOption.OK
          , "Error Encountered"
          , e.getMessage());
    }
  }
  
  /**
   * Adds a listener.
   * 
   * @param listener  The listener to add.
   */
  public void addScenarioEditListener(ScenarioEditListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeScenarioEditListener(ScenarioEditListener listener) {
    listeners.remove(listener);
  }
}
