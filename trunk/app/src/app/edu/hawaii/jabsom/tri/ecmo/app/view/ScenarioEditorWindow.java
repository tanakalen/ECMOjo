package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;

import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioLoader;
import edu.hawaii.jabsom.tri.ecmo.app.model.ScenarioFile;

import king.lib.access.LocalHookup;
import king.lib.script.model.Language;
import king.lib.script.model.Script;
import king.lib.script.view.ScriptEditorPanel;

/**
 * The scenario editor.
 *
 * @author noblemaster
 * @since December 8, 2009
 */
public class ScenarioEditorWindow extends JFrame {

  /** The file path. */
  private String path;
  
  /** The script editor panel. */
  private ScriptEditorPanel scriptPanel;
  
  
  /**
   * The constructor.
   *
   * @param path  The file path.
   */
  public ScenarioEditorWindow(String path) {
    this.path = path;
    
    // set title and close behavior
    setTitle("ECMOjo Scenario Editor");
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // load the scenario
    ScenarioFile scenario;
    try{
      scenario = ScenarioLoader.loadFile(LocalHookup.getInstance(), path);
    }
    catch (IOException e) {
      // set error information
      scenario = new ScenarioFile();
      scenario.setParameters("Error loading scenario file: " + e.getMessage());
      Script script = new Script();
      script.setLang(Language.PNUTS.getName());
      script.setCode("Error loading scenario file: " + e.getMessage());
      scenario.setScript(script);
      
      // reset path
      path = null;
    }
    
    // set layout
    getContentPane().setBackground(Color.BLACK);
    getContentPane().setLayout(new BorderLayout());
    
    // add the parameters panel
    
    
    
    // add the script panel
    scriptPanel = new ScriptEditorPanel();
    scriptPanel.setScript(scenario.getScript());
    getContentPane().add(scriptPanel, BorderLayout.CENTER);
  }
}
