package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
import king.lib.access.ImageLoader;
import king.lib.access.LocalHookup;
import king.lib.script.control.CompileException;
import king.lib.script.control.ScriptCompiler;
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
public class ScenarioEditorWindow extends JFrame {

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

  /** The parameters panel. */
  private JEditorPane parametersPanel;
  /** The syntax panel. */
  private ScriptSyntaxPanel scriptPanel;
  /** The console panel. */
  private ScriptConsolePanel consolePanel;

  
  /**
   * The constructor.
   */
  public ScenarioEditorWindow() {
    // set title and close behavior
    setTitle("ECMOjo Editor");
    setIconImage(ImageLoader.getInstance().getImage("conf/logo/window-icon.gif"));
    
    // set layout
    getContentPane().setBackground(Color.LIGHT_GRAY);
    getContentPane().setLayout(new FormLayout(
          "fill:1px:grow"
        , "pref, pref, fill:3px:grow, pref, fill:2px:grow, pref, fill:60px"
    ));
    CellConstraints cc = new CellConstraints();
    
    // add the menu panel
    JPanel menuPanel = new JPanel();
    menuPanel.setLayout(new FormLayout(
        "2px, 100px, 2px, 100px, 2px, 100px, 2px, 100px, 2px, 100px, 2px"
      , "2px, pref, 2px"
    ));
    menuPanel.setOpaque(false);
    getContentPane().add(menuPanel, cc.xy(1, 1));
    
    openButton = new JButton(Translator.getString("action.Open[i18n]: Open..."));
    openButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // chose a file
        SimpleFileFilter filter = new SimpleFileFilter(new String[]{"scn"}, "ECMOjo Scenario");
        JFileChooser chooser = new JFileChooser(Access.getInstance().getScenarioDir());
        chooser.addChoosableFileFilter(filter);
        int returnVal = chooser.showOpenDialog(ScenarioEditorWindow.this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
          // obtain the file
          File file = chooser.getSelectedFile();
          if (file.exists()) {
            // load the scenario
            try{
              path = file.getAbsolutePath();   
              ScenarioFile scenario = ScenarioLoader.loadFile(LocalHookup.getInstance(), path);
              
              // populate the GUI components with scenario values
              parametersPanel.setText(scenario.getParameters());
              parametersPanel.setCaretPosition(0);
              scriptPanel.setScript(scenario.getScript());       
              saveButton.setEnabled(true);
            }
            catch (IOException e) {
              // output the error message
              StandardDialog.showDialog(ScenarioEditorWindow.this, DialogType.ERROR, DialogOption.OK
                  , "Error Encountered"
                  , e.getMessage());
              
              // reset path
              path = null;
              saveButton.setEnabled(false);
            }
          }
        }
      }      
    });
    menuPanel.add(openButton, cc.xy(2, 2));   

    saveButton = new JButton(Translator.getString("action.Save[i18n]: Save"));
    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        
      }      
    });
    menuPanel.add(saveButton, cc.xy(4, 2));   
    saveButton.setEnabled(false);
    
    saveAsButton = new JButton(Translator.getString("action.SaveAs[i18n]: Save As..."));
    saveAsButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        
      }      
    });
    menuPanel.add(saveAsButton, cc.xy(6, 2));   

    compileButton = new JButton(Translator.getString("action.Compile[i18n]: Compile"));
    compileButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        try {
          String timestamp = new DateTime().toString();
          consolePanel.setSuccess(timestamp + " | " + Translator.getString("text.Compiling[i18n]: Compiling...") +"\n");
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
        
      }      
    });
    menuPanel.add(runButton, cc.xy(10, 2));   

    // add the parameters panel
    getContentPane().add(new JLabel("Scenario Parameters"), cc.xy(1, 2));
    parametersPanel = new JEditorPane();
    getContentPane().add(new JScrollPane(parametersPanel), cc.xy(1, 3));
    DefaultSyntaxKit.initKit();
    parametersPanel.setContentType("text/properties");
    
    // add the script panel
    getContentPane().add(new JLabel("Scenario Script (Optional)"), cc.xy(1, 4));
    scriptPanel = new ScriptSyntaxPanel();
    getContentPane().add(scriptPanel, cc.xy(1, 5));
    
    // add the console panel
    getContentPane().add(new JLabel("Console Output"), cc.xy(1, 6));
    consolePanel = new ScriptConsolePanel();
    getContentPane().add(consolePanel, cc.xy(1, 7));   
  }
}
