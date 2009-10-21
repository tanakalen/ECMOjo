package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.hawaii.jabsom.tri.ecmo.app.Configuration;
import edu.hawaii.jabsom.tri.ecmo.app.ECMOAppRelease;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent.OxyType;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent.PumpType;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent.Mode;
import edu.hawaii.jabsom.tri.ecmo.app.view.ScenarioListPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.ScenarioListPanel.ScenarioSelectionListener;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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
  
  /** The vv radio button. */
  private ImageToggleButton vvRadio;
  /** The va radio button. */
  private ImageToggleButton vaRadio;
  /** The sciMed radio button. */  
  private ImageToggleButton siliconeRadio; 
  /** The quadrox D radio button. */
  private ImageToggleButton quadroxDRadio;
  /** The roller pump radio button. */
  private ImageToggleButton rollerRadio;
  /** The centrifugal pump radio button. */
  private ImageToggleButton centrifugalRadio;
  
  
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
    vvRadio.setLocation(0, 8);
    componentSelectionPanel.add(vvRadio);
    modeTubeButtonGroup.add(vvRadio);
    Image vaRadioRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image vaRadioSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    vaRadio = new ImageToggleButton(null, vaRadioRolloverImage, vaRadioSelectedImage);    
    vaRadio.setOpaque(false);
    vaRadio.setSize(32, 32);
    vaRadio.setLocation(142, 8);
    componentSelectionPanel.add(vaRadio);   
    modeTubeButtonGroup.add(vaRadio);
    if (Configuration.getInstance().isSelectionVVModeECMO()) {
      vvRadio.setSelected(true);
    }
    else {
      vaRadio.setSelected(true);
    }
    
    ButtonGroup oxygenatorButtonGroup = new ButtonGroup();
    Image sciMedRadioRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image sciMedRadioSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    siliconeRadio = new ImageToggleButton(null, sciMedRadioRolloverImage, sciMedRadioSelectedImage);    
    siliconeRadio.setOpaque(false);
    siliconeRadio.setSize(32, 32);
    siliconeRadio.setLocation(0, 39);
    componentSelectionPanel.add(siliconeRadio);    
    oxygenatorButtonGroup.add(siliconeRadio);
    Image quadroxDRadioRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image quadroxDRadioSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    quadroxDRadio = new ImageToggleButton(null, quadroxDRadioRolloverImage, quadroxDRadioSelectedImage);    
    quadroxDRadio.setOpaque(false);
    quadroxDRadio.setSize(32, 32);
    quadroxDRadio.setLocation(142, 39);
    componentSelectionPanel.add(quadroxDRadio);    
    oxygenatorButtonGroup.add(quadroxDRadio);
    if (Configuration.getInstance().isSelectionSiliconeOxygenator()) {
      siliconeRadio.setSelected(true);
    }
    else {
      quadroxDRadio.setSelected(true);
    }
    
    ButtonGroup pumpButtonGroup = new ButtonGroup();
    Image rollerRadioRolloverImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image rollerRadioSelectedImage 
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    rollerRadio = new ImageToggleButton(null, rollerRadioRolloverImage, rollerRadioSelectedImage);    
    rollerRadio.setOpaque(false);
    rollerRadio.setSize(32, 32);
    rollerRadio.setLocation(0, 71);
    componentSelectionPanel.add(rollerRadio);    
    pumpButtonGroup.add(rollerRadio);
    Image centrifugalRadioRolloverImage
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkRol.png");
    Image centrifugalRadioSelectedImage
      = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkSel.png");
    centrifugalRadio = new ImageToggleButton(null, centrifugalRadioRolloverImage, centrifugalRadioSelectedImage);       
    centrifugalRadio.setOpaque(false);
    centrifugalRadio.setSize(32, 32);
    centrifugalRadio.setLocation(142, 71);
    componentSelectionPanel.add(centrifugalRadio);    
    pumpButtonGroup.add(centrifugalRadio);
    if (Configuration.getInstance().isSelectionRollerPump()) {
      rollerRadio.setSelected(true);
    }
    else {
      centrifugalRadio.setSelected(true);
    }

    Icon disabled = new ImageIcon(ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkDis.png"));
    JLabel ventilatorCheckmark = new JLabel(disabled);
    ventilatorCheckmark.setSize(32, 32);
    ventilatorCheckmark.setLocation(0, 103);
    componentSelectionPanel.add(ventilatorCheckmark);    
    
    // add scenario selection listener
    ScenarioSelectionListener scenarioSelectionListener = new ScenarioSelectionListener() {
      public void handleSelection(Scenario scenario) {
        // set the new scenario
        if (scenario != null) {
          // add selected components
          Equipment equipment = scenario.getEquipment();
          
          // update tubing
          TubeComponent tube = (TubeComponent)equipment.getComponent(TubeComponent.class);
          if (tube.getMode() == null) {
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
            Configuration.getInstance().setSelectionVVModeECMO(vvRadio.isSelected());
          }

          // update oxigenator
          OxygenatorComponent oxy = (OxygenatorComponent)equipment.getComponent(OxygenatorComponent.class);
          if (oxy.getOxyType() == null) {
            if (siliconeRadio.isSelected()) {
              oxy.setOxyType(OxyType.SILICONE);
            }
            else {
              oxy.setOxyType(OxyType.QUADROX_D);
            }
            Configuration.getInstance().setSelectionSiliconeOxygenator(siliconeRadio.isSelected());
          }
          
          // update pump
          PumpComponent pump = (PumpComponent)equipment.getComponent(PumpComponent.class);
          if (pump.getPumpType() == null) {
            if (rollerRadio.isSelected()) {
              pump.setPumpType(PumpType.ROLLER);
            }
            else {
              pump.setPumpType(PumpType.CENTRIFUGAL);
            }
            Configuration.getInstance().setSelectionRollerPump(rollerRadio.isSelected());
          }
          
          // update ventilator
          VentilatorComponent ventilator = (VentilatorComponent)equipment.getComponent(VentilatorComponent.class);
          ventilator.setSubtype(new VentilatorComponent.ConventionalSubtype());
          Configuration.getInstance().setSelectionConventionalVentilator(true);

          // init tube values depending on selection
//          tube.setPreMembranePressure((pump.getFlow() * 400) + (oxi.getClotting() * 50));
          if ((((Double)tube.getPreMembranePressure()).isNaN()) || ((Double)tube.getPostMembranePressure()).isNaN()) {
            if (oxy.getOxyType() == OxygenatorComponent.OxyType.QUADROX_D) { 
              // PMP
              //            tube.setPostMembranePressure(tube.getPreMembranePressure());
              tube.setPreMembranePressure(125);
              tube.setPostMembranePressure(120);
            }
            else { 
              // Silicon
              //            tube.setPostMembranePressure(tube.getPreMembranePressure() / 1.23);
              tube.setPreMembranePressure(140);
              tube.setPostMembranePressure(120);
            }
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
          Configuration.getInstance().setSelectionScenarioTab(scenarioListPanel.isVisible());
          Configuration.getInstance().saveConfiguration();
          
          // show cursor busy
          setCursor(new Cursor(Cursor.WAIT_CURSOR));
          
          // proceed to game state
          state.gameState(scenario, user);
        }
      }      
    };
    tutorialListPanel.addScenarioSelectionListener(scenarioSelectionListener);
    scenarioListPanel.addScenarioSelectionListener(scenarioSelectionListener);
    
    // enable tutorial
    boolean scenarioSelection = Configuration.getInstance().isSelectionScenarioTab();
    tutorialButton.setSelected(!scenarioSelection);
    tutorialListPanel.setVisible(!scenarioSelection);
    scenarioButton.setSelected(scenarioSelection);
    scenarioListPanel.setVisible(scenarioSelection);
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
    
    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
    // text properties
    g.setFont(g.getFont().deriveFont(10f));
    g.setColor(Color.DARK_GRAY);

    // draw the release info
    String release = "Release Version: " + ECMOAppRelease.getReleaseVersion() + " | " + ECMOAppRelease.getReleaseTime();
    int width = g.getFontMetrics().stringWidth(release);
    g.drawString(release, 800 - width - 5, 595);
  }
}
