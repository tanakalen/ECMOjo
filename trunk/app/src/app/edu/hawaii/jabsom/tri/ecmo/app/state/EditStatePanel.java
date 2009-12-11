package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioLoader;
import edu.hawaii.jabsom.tri.ecmo.app.model.ScenarioFile;
import edu.hawaii.jabsom.tri.ecmo.app.view.ScenarioEditPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogOption;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogType;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import king.lib.access.Access;
import king.lib.access.ImageLoader;
import king.lib.access.LocalHookup;
import king.lib.util.SimpleFileFilter;
import king.lib.util.Translator;

/**
 * The edit state panel. 
 *
 * @author Christoph Aschwanden
 * @since December 9, 2009
 */
public class EditStatePanel extends JPanel {
    
  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage("conf/image/interface/help/Base.jpg");

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

  /** The scenario edit panel. */
  private ScenarioEditPanel scenarioEditPanel;
  

  /**
   * Constructor for panel. 
   * 
   * @param state  The state for this panel.
   */
  public EditStatePanel(final EditState state) {
    // set look
    setOpaque(true);
    
    // set layout
    setLayout(null);
    
    // add text label
    TextLabel textLabel = new TextLabel("ECMOjo Scenario Editor");
    textLabel.setHorizontalAlignment(JLabel.CENTER);
    textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 36f)); 
    textLabel.setLocation(100, 16);
    textLabel.setSize(600, 50);
    add(textLabel);
    
    // add the menu panel
    JPanel menuPanel = new JPanel();
    menuPanel.setLayout(new FormLayout(
        "0px, 100px, 2px, 100px, 2px, 100px, 2px, 100px, 2px, 100px, 2px, 100px, 0px"
      , "2px, pref, 5px"
    ));
    CellConstraints cc = new CellConstraints();
    menuPanel.setOpaque(false);
    menuPanel.setLocation(15, 90);
    menuPanel.setSize(770, 30);
    add(menuPanel);
    
    openButton = new JButton(Translator.getString("action.Open[i18n]: Open..."));
    openButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // choose a file
        SimpleFileFilter filter = new SimpleFileFilter(new String[]{"scn"}, "ECMOjo Scenario");
        JFileChooser chooser = new JFileChooser(Access.getInstance().getScenarioDir());
        chooser.addChoosableFileFilter(filter);
        int returnVal = chooser.showOpenDialog(EditStatePanel.this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
          // obtain the file
          File file = chooser.getSelectedFile();
          if (file.exists()) {
            // load the scenario
            load(file.getAbsolutePath());
            state.setPath(file.getAbsolutePath());
          }
        }
      }      
    });
    menuPanel.add(openButton, cc.xy(2, 2));   

    saveButton = new JButton(Translator.getString("action.Save[i18n]: Save"));
    saveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // save the scenario
        save(state.getPath());
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
        chooser.setSelectedFile(new File(state.getPath()));
        int returnVal = chooser.showSaveDialog(EditStatePanel.this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
          // save the file
          save(chooser.getSelectedFile().getAbsolutePath());
          state.setPath(chooser.getSelectedFile().getAbsolutePath());
        }
      }      
    });
    menuPanel.add(saveAsButton, cc.xy(6, 2));   

    compileButton = new JButton(Translator.getString("action.Compile[i18n]: Compile"));
    compileButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        scenarioEditPanel.compile();
      }      
    });
    menuPanel.add(compileButton, cc.xy(8, 2));
    
    runButton = new JButton(Translator.getString("action.Run[i18n]: Run..."));
    runButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // save the scenario first
        save(state.getPath());
        
        // run it...
        state.evalState();
      }      
    });
    menuPanel.add(runButton, cc.xy(10, 2));   

    exitButton = new JButton(Translator.getString("action.Exit[i18n]: Exit"));
    exitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // exit the editor
        state.menuState();
      }      
    });
    menuPanel.add(exitButton, cc.xy(12, 2));   

    // add scenario panel
    scenarioEditPanel = new ScenarioEditPanel();
    scenarioEditPanel.setLocation(15, 125);
    scenarioEditPanel.setSize(770, 460);
    add(scenarioEditPanel);
    
    // load the data
    load(state.getPath());
  }
  
  
  /**
   * Loads the scenario.
   * 
   * @param path  The path.
   */
  private void load(String path) {
    if (path != null) {
      try{
        ScenarioFile scenario = ScenarioLoader.loadFile(LocalHookup.getInstance(), path);
        
        // populate the GUI components with scenario values
        scenarioEditPanel.setScenario(scenario);
        saveButton.setEnabled(true);
      }
      catch (IOException e) {
        // output the error message
        StandardDialog.showDialog(EditStatePanel.this, DialogType.ERROR, DialogOption.OK
            , "Error Encountered"
            , e.getMessage());
      }
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
      ScenarioFile scenario = scenarioEditPanel.getScenario();
      
      // and save it
      ScenarioLoader.saveFile(LocalHookup.getInstance(), path, scenario);
    }
    catch (IOException e) {
      // output the error message
      StandardDialog.showDialog(EditStatePanel.this, DialogType.ERROR, DialogOption.OK
          , "Error Encountered"
          , e.getMessage());
    }
  }

  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    // draws the image as background
    g.drawImage(background, 0, 0, this);
  }
}
