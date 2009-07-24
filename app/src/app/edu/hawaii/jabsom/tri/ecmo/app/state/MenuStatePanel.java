package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxigenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxigenatorComponent.OxiType;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent.PumpType;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent.Mode;
import edu.hawaii.jabsom.tri.ecmo.app.view.ScenarioInfoPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.ScenarioListPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.ScenarioListPanel.ScenarioSelectionListener;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.lib.access.ImageLoader;

/**
 * The menu state panel. 
 *
 * @author   Christoph Aschwanden
 * @since    August 13, 2008
 */
public class MenuStatePanel extends JPanel {

  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage("conf/image/interface/menu/Base.jpg");

  /** The tutorial list panel. */
  private ScenarioListPanel tutorialListPanel;
  /** The scenario list panel. */
  private ScenarioListPanel scenarioListPanel;
  
  /** The scenario info panel. */
  private ScenarioInfoPanel scenarioInfoPanel;
  
  /** The vv radio button. */
  private ImageToggleButton vvRadio;
  /** The va radio button. */
  private ImageToggleButton vaRadio;
  /** The sciMed radio button. */  
  private ImageToggleButton sciMedRadio; 
  /** The quadrox D radio button. */
  private ImageToggleButton quadroxDRadio;
  /** The roller pump radio button. */
  private ImageToggleButton rollerRadio;
  /** The centrifugal pump radio button. */
  private ImageToggleButton centrifugalRadio;
  /** The conventional ventilator radio button. */
  private ImageToggleButton conventionalRadio;
  /** The high frequency ventilator radio button. */
  private ImageToggleButton highfrequencyRadio;    
  /** The start button. */
  private ImageButton startButton;
  

  
  /**
   * Constructor for panel. 
   * 
   * @param state  The state for this panel.
   */
  public MenuStatePanel(final MenuState state) {
    // set look
    setOpaque(true);
    
    // set layout
    setLayout(null);
    
    // add text label
    TextLabel textLabel = new TextLabel("Welcome to ECMOjo");
    textLabel.setHorizontalAlignment(JLabel.CENTER);
    textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 36f)); 
    textLabel.setLocation(100, 16);
    textLabel.setSize(600, 45);
    add(textLabel);
    
    // add help button
    Image helpNormalImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Help.png");
    Image helpRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-HelpRol.png");
    Image helpSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-HelpSel.png");
    ImageButton helpButton 
      = new ImageButton(helpNormalImage, helpRolloverImage, helpSelectedImage);
    helpButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        // and help
        state.helpState();
      }      
    });
    helpButton.setLocation(680, 18);
    helpButton.setSize(120, 48);
    add(helpButton);    
    
    // button group
    ButtonGroup buttonGroup = new ButtonGroup();
    
    // add tutorial toogle button
    Image tutorialNormalImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/Btn-Tutorial.png");
    Image tutorialRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/Btn-TutorialRol.png");
    Image tutorialSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/Btn-TutorialSel.png");
    ImageToggleButton tutorialButton 
      = new ImageToggleButton(tutorialNormalImage, tutorialRolloverImage, tutorialSelectedImage);
    tutorialButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        // activate the correct panel
        tutorialListPanel.setVisible(true);
        scenarioListPanel.setVisible(false);
      }
    });
    tutorialButton.setSize(96, 40);
    tutorialButton.setLocation(79, 130);
    add(tutorialButton);
    buttonGroup.add(tutorialButton);

    // add scenario toogle button
    Image scenarioNormalImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/Btn-Scenario.png");
    Image scenarioRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/Btn-ScenarioRol.png");
    Image scenarioSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/Btn-ScenarioSel.png");
    ImageToggleButton scenarioButton 
      = new ImageToggleButton(scenarioNormalImage, scenarioRolloverImage, scenarioSelectedImage);
    scenarioButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        // activate the correct panel
        scenarioListPanel.setVisible(true);
        tutorialListPanel.setVisible(false);
      }
    });
    scenarioButton.setSize(96, 40);
    scenarioButton.setLocation(176, 130);
    add(scenarioButton);
    buttonGroup.add(scenarioButton);

    // add start button
    Image startNormalImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/Btn-Start.png");
    Image startRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/Btn-StartRol.png");
    Image startSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/Btn-StartSel.png");
    startButton = new ImageButton(startNormalImage, startRolloverImage, startSelectedImage);
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        // build scenario
        Scenario scenario = getSelectedScenario();
        
        // add selected components
        Equipment equipment = scenario.getEquipment();
        
        // update tubing
        TubeComponent tube = (TubeComponent)equipment.getComponent(TubeComponent.class);
        if (vvRadio.isSelected()) {
          tube.setMode(Mode.VV);
        }
        else {
          tube.setMode(Mode.VA);
          // ECMO Mode VA reduces patient HR 10%
          Patient patient = scenario.getPatient();
          double hr = patient.getHeartRate();
          patient.setHeartRate(hr * 0.9);
        }
        
        // update oxigenator
        OxigenatorComponent oxi = (OxigenatorComponent)equipment.getComponent(OxigenatorComponent.class);
        if (sciMedRadio.isSelected()) {
          oxi.setOxiType(OxiType.SCI_MED);
        }
        else {
          oxi.setOxiType(OxiType.QUADROX_D);
        }
        
        // update pump
        PumpComponent pump = (PumpComponent)equipment.getComponent(PumpComponent.class);
        if (rollerRadio.isSelected()) {
          pump.setPumpType(PumpType.ROLLER);
        }
        else {
          pump.setPumpType(PumpType.CENTRIFUGAL);
        }

        // update ventilator
        VentilatorComponent ventilator = (VentilatorComponent)equipment.getComponent(VentilatorComponent.class);
        if (conventionalRadio.isSelected()) {
          ventilator.setSubtype(new VentilatorComponent.ConventionalSubtype());
        }
        else {
          ventilator.setSubtype(new VentilatorComponent.HighFrequencySubtype());
        }
        
        // init tube values depending on selection
        tube.setPreMembranePressure((pump.getFlow() * 400) + (oxi.getClotting() * 50));
        if (oxi.getOxiType() == OxigenatorComponent.OxiType.QUADROX_D) { 
          // PMP
          tube.setPostMembranePressure(tube.getPreMembranePressure());
        }
        else { 
          // Silicon
          tube.setPostMembranePressure(tube.getPreMembranePressure() / 1.23);
        }
        tube.setPostPCO2(35);
        tube.setPostPH(7.4);

        // show dialog and ask for the username
        String user;
        if (scenarioListPanel.isVisible()) {
          // let's ask for a username to continue to the scenario
          user = "TODO:DialogWindow";  // TODO: add a input dialog for username
        }
        else {
          // Tutorial: we don't set a username
          user = "N/A";
        }
        
        // proceed to game state
        state.gameState(scenario, user);
      }
    });
    startButton.setSize(120, 48);
    startButton.setLocation(274, 126);
    startButton.setVisible(false);
    add(startButton);

    // add tutorial list panel
    tutorialListPanel = new ScenarioListPanel(state.getTutorials());
    Image tutorialImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/tutorial-background.png");
    tutorialListPanel.setBackgroundImage(tutorialImage);
    tutorialListPanel.setSize(385, 460);
    tutorialListPanel.setLocation(37, 110);
    add(tutorialListPanel);

    // add scenario list panel
    scenarioListPanel = new ScenarioListPanel(state.getScenarios());
    Image scenarioImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/scenario-background.png");
    scenarioListPanel.setBackgroundImage(scenarioImage);
    scenarioListPanel.setSize(385, 460);
    scenarioListPanel.setLocation(37, 110);
    add(scenarioListPanel);
    
    // add component selection panel
    JPanel componentSelectionPanel = new JPanel();
    componentSelectionPanel.setOpaque(false);
    componentSelectionPanel.setLocation(452, 120);
    componentSelectionPanel.setSize(300, 140);
    componentSelectionPanel.setLayout(null);
    add(componentSelectionPanel);
    
    // button group
    ButtonGroup modeTubeButtonGroup = new ButtonGroup();
    Image vvRadioRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image vvRadioSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    vvRadio = new ImageToggleButton(null, vvRadioRolloverImage, vvRadioSelectedImage);
    vvRadio.setOpaque(false);
    vvRadio.setSize(32, 32);
    vvRadio.setLocation(120, 6);
    vvRadio.setSelected(true);
    componentSelectionPanel.add(vvRadio);
    modeTubeButtonGroup.add(vvRadio);
    Image vaRadioRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image vaRadioSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    vaRadio = new ImageToggleButton(null, vaRadioRolloverImage, vaRadioSelectedImage);    
    vaRadio.setOpaque(false);
    vaRadio.setSize(32, 32);
    vaRadio.setLocation(262, 6);
    componentSelectionPanel.add(vaRadio);   
    modeTubeButtonGroup.add(vaRadio);
    
    ButtonGroup oxigenatorButtonGroup = new ButtonGroup();
    Image sciMedRadioRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image sciMedRadioSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    sciMedRadio = new ImageToggleButton(null, sciMedRadioRolloverImage, sciMedRadioSelectedImage);    
    sciMedRadio.setOpaque(false);
    sciMedRadio.setSize(32, 32);
    sciMedRadio.setLocation(120, 37);
    sciMedRadio.setSelected(true);
    componentSelectionPanel.add(sciMedRadio);    
    oxigenatorButtonGroup.add(sciMedRadio);
    Image quadroxDRadioRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image quadroxDRadioSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    quadroxDRadio = new ImageToggleButton(null, quadroxDRadioRolloverImage, quadroxDRadioSelectedImage);    
    quadroxDRadio.setOpaque(false);
    quadroxDRadio.setSize(32, 32);
    quadroxDRadio.setLocation(262, 37);
    componentSelectionPanel.add(quadroxDRadio);    
    oxigenatorButtonGroup.add(quadroxDRadio);
    
    ButtonGroup pumpButtonGroup = new ButtonGroup();
    Image rollerRadioRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image rollerRadioSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    rollerRadio = new ImageToggleButton(null, rollerRadioRolloverImage, rollerRadioSelectedImage);    
    rollerRadio.setOpaque(false);
    rollerRadio.setSize(32, 32);
    rollerRadio.setLocation(120, 69);
    rollerRadio.setSelected(true);
    componentSelectionPanel.add(rollerRadio);    
    pumpButtonGroup.add(rollerRadio);
    Image centrifugalRadioRolloverImage
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image centrifugalRadioSelectedImage
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    centrifugalRadio = new ImageToggleButton(null, centrifugalRadioRolloverImage, centrifugalRadioSelectedImage);       
    centrifugalRadio.setOpaque(false);
    centrifugalRadio.setSize(32, 32);
    centrifugalRadio.setLocation(262, 69);
    componentSelectionPanel.add(centrifugalRadio);    
    pumpButtonGroup.add(centrifugalRadio);

    ButtonGroup ventilatorButtonGroup = new ButtonGroup();
    Image conventionalRadioRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image conventionalRadioSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    conventionalRadio 
      = new ImageToggleButton(null, conventionalRadioRolloverImage, conventionalRadioSelectedImage);
    conventionalRadio.setOpaque(false);
    conventionalRadio.setSize(32, 32);
    conventionalRadio.setLocation(120, 101);
    conventionalRadio.setSelected(true);
    componentSelectionPanel.add(conventionalRadio);    
    ventilatorButtonGroup.add(conventionalRadio);
    Image highfrequencyRadioRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image highfrequencyRadioSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    highfrequencyRadio 
      = new ImageToggleButton(null, highfrequencyRadioRolloverImage, highfrequencyRadioSelectedImage);    
    highfrequencyRadio.setOpaque(false);
    highfrequencyRadio.setSize(32, 32);
    highfrequencyRadio.setLocation(262, 101);
    componentSelectionPanel.add(highfrequencyRadio);    
    ventilatorButtonGroup.add(highfrequencyRadio);
    
    // add scenario description panel
    scenarioInfoPanel = new ScenarioInfoPanel();
    scenarioInfoPanel.setSize(307, 261);
    scenarioInfoPanel.setLocation(446, 298);
    add(scenarioInfoPanel);
    
    // add scenario selection listener
    ScenarioSelectionListener scenarioSelectionListener = new ScenarioSelectionListener() {
      public void handleSelection(Scenario scenario) {
        // set the new scenario
        if (scenario != null) {
          startButton.setVisible(true);
        }
        else {
          startButton.setVisible(false);
        }
        scenarioInfoPanel.setScenario(scenario); 
      }      
    };
    tutorialListPanel.addScenarioSelectionListener(scenarioSelectionListener);
    scenarioListPanel.addScenarioSelectionListener(scenarioSelectionListener);
    
    // enable tutorial
    tutorialButton.setSelected(true);
    tutorialListPanel.setVisible(true);
  }
  
  /**
   * Returns the selected scenario.
   * 
   * @return  The selected scenario or null for none.
   */
  private Scenario getSelectedScenario() {
    if (tutorialListPanel.isVisible()) {
      return tutorialListPanel.getSelectedValue();
    }
    else if (scenarioListPanel.isVisible()) {
      return scenarioListPanel.getSelectedValue();
    }
    else {
      return null;
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
