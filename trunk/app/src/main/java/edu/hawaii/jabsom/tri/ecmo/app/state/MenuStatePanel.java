package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import edu.hawaii.jabsom.tri.ecmo.app.Configuration;
import edu.hawaii.jabsom.tri.ecmo.app.ECMOAppRelease;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.gui.TextToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioCreator;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
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
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import king.lib.access.ImageLoader;
import king.lib.util.Translator;

/**
 * The menu state panel. 
 *
 * @author   Christoph Aschwanden
 * @since    August 13, 2008
 */
public class MenuStatePanel extends JPanel implements KeyEventDispatcher {

  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage("conf/image/interface/menu/Base.png");
//      Translator.getString("image.menubase[i18n]: conf/image/interface/menu/Base.png"));

  /** The state. */
  private MenuState state;
  
  /** The scenario list panel. */
  private ScenarioListPanel scenarioListPanel;
  /** The simulation list panel. */
  private ScenarioListPanel simulationListPanel;
  
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
    this.state = state;
    
    // set look
    setOpaque(true);
    
    // set layout
    setLayout(null);
    
    // add text label
    TextLabel textLabel = new TextLabel(Translator.getString("title.WelcomeToECMOjo[i18n]: Welcome to ECMOjo"));
    textLabel.setHorizontalAlignment(JLabel.LEFT);
    textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 36f)); 
    textLabel.setLocation(60, 16);
    textLabel.setSize(600, 45);
    add(textLabel);
    
    // add about button
    Image aboutNormalImage = ImageLoader.getInstance().getImage(
        "conf/image/interface/menu/BtnBig.png");
    Image aboutRolloverImage = ImageLoader.getInstance().getImage(
        "conf/image/interface/menu/BtnBig.png");
    Image aboutSelectedImage = ImageLoader.getInstance().getImage(
        "conf/image/interface/menu/BtnBigSel.png");
    final ImageButton aboutButton = new ImageButton(aboutNormalImage, aboutRolloverImage, aboutSelectedImage);
    aboutButton.setText(
        Translator.getString("button.About[i18n]: About"));
    aboutButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        // and help
        state.aboutState();
      }      
    });
    aboutButton.setLocation(570, 18);
    aboutButton.setSize(120, 48);
    add(aboutButton);
    
    // add language select combobox
    String[] langStrings = Configuration.getInstance().getAvailableLanguages();
    String currentLanguage = Configuration.getInstance().getConfiguredLanguage();
    int currentLanguageIndex = 0;
    for (String language:langStrings) {
      if (currentLanguage.equals(language)) {
        break;
      }
      currentLanguageIndex++;
    }
    JComboBox<String> langList = new JComboBox<String>(langStrings);
    langList.setSelectedIndex(currentLanguageIndex);
//    ComboBoxRenderer renderer = new ComboBoxRenderer();
//    langList.setRenderer(renderer);
    langList.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        int selected = (int)cb.getSelectedIndex();
        Configuration.getInstance().setLang(selected);
        state.menuState();
      }
    });
    langList.setLocation(690, 30);
    langList.setSize(100, 30);
    add(langList);
    
    // button group
    ButtonGroup buttonGroup = new ButtonGroup();
    
    // add scenario toggle button
    String sScenario = Translator.getString("text.Scenario[i18n]: Scenario");
    TextToggleButton scenarioButton 
          = new TextToggleButton.Builder(sScenario).
                                 normal(Color.WHITE).
                                 rollover(Color.GREEN).
                                 pressed(Color.WHITE).build();
    scenarioButton.setFont(scenarioButton.getFont().deriveFont(Font.BOLD, 12f)); 
    scenarioButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        // activate the correct panel
        scenarioListPanel.setVisible(true);
        simulationListPanel.setVisible(false);
      }
    });
    scenarioButton.setSize(96, 40);
    scenarioButton.setLocation(79, 130);
    add(scenarioButton);
    buttonGroup.add(scenarioButton);

    // add simulation toggle button
    String sSimulation = Translator.getString("text.Simulation[i18n]: Simulation");
    TextToggleButton simulationButton 
//        = new TextToggleButton(sSimulation, Color.WHITE, Color.GREEN, Color.WHITE);
          = new TextToggleButton.Builder(sSimulation).
                                 normal(Color.WHITE).
                                 rollover(Color.GREEN).
                                 pressed(Color.WHITE).build();
    simulationButton.setFont(simulationButton.getFont().deriveFont(Font.BOLD, 12f)); 
    simulationButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        // activate the correct panel
        simulationListPanel.setVisible(true);
        scenarioListPanel.setVisible(false);
      }
    });
    simulationButton.setSize(96, 40);
    simulationButton.setLocation(176, 130);
    add(simulationButton);
    buttonGroup.add(simulationButton);
   
    // add scenario list panel
    scenarioListPanel = new ScenarioListPanel(state.getScenarios());
    Image scenarioImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/scenario-background.png");
    scenarioListPanel.setBackgroundImage(scenarioImage);
    scenarioListPanel.setSize(385, 460);
    scenarioListPanel.setLocation(37, 110);
    add(scenarioListPanel);

    // add simulation list panel
    simulationListPanel = new ScenarioListPanel(state.getSimulations());
    Image simulationImage = ImageLoader.getInstance().getImage("conf/image/interface/menu/simulation-background.png");
    simulationListPanel.setBackgroundImage(simulationImage);
    simulationListPanel.setSize(385, 460);
    simulationListPanel.setLocation(37, 110);
    add(simulationListPanel);

    // add label: ECMO Circuit i18n
    JLabel lblCompSelPanel = new JLabel(Translator.getString("label.ECMOCircuit[i18n]: ECMO Circuit"));
    lblCompSelPanel.setFont(textLabel.getFont().deriveFont(Font.BOLD, 14f));
    lblCompSelPanel.setForeground(Color.GRAY);
    lblCompSelPanel.setLocation(458, 103);
    lblCompSelPanel.setSize(150, 20);
    add(lblCompSelPanel);

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
    // add label: Mode VV or VA i18n
    JLabel lblModeVV = new TextLabel(Translator.getString("label.ModeVV[i18n]: Veno-venous"));
    lblModeVV.setHorizontalAlignment(JLabel.LEFT);
    lblModeVV.setFont(textLabel.getFont().deriveFont(Font.BOLD, 14f));
    lblModeVV.setLocation(32, 15);
    lblModeVV.setSize(120, 45);
    componentSelectionPanel.add(lblModeVV);
    JLabel lblModeVA = new TextLabel(Translator.getString("label.ModeVA[i18n]: Veno-arterial"));
    lblModeVA.setHorizontalAlignment(JLabel.LEFT);
    lblModeVA.setFont(textLabel.getFont().deriveFont(Font.BOLD, 14f));
    lblModeVA.setLocation(174, 15);
    lblModeVA.setSize(120, 45);
    componentSelectionPanel.add(lblModeVA);

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
    // add label: Oxygenator Silicon or PMP i18n
    JLabel lblOxySil = new TextLabel(Translator.getString("label.OxySil[i18n]: Silicon"));
    lblOxySil.setHorizontalAlignment(JLabel.LEFT);
    lblOxySil.setFont(textLabel.getFont().deriveFont(Font.BOLD, 14f));
    lblOxySil.setLocation(32, 48);
    lblOxySil.setSize(120, 45);
    componentSelectionPanel.add(lblOxySil);
    JLabel lblOxyPMP = new TextLabel(Translator.getString("label.OxyPMP[i18n]: PMP"));
    lblOxyPMP.setHorizontalAlignment(JLabel.LEFT);
    lblOxyPMP.setFont(textLabel.getFont().deriveFont(Font.BOLD, 14f));
    lblOxyPMP.setLocation(174, 48);
    lblOxyPMP.setSize(120, 45);
    componentSelectionPanel.add(lblOxyPMP);
    
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
    // add label: Pump roller or centrifugal i18n
    JLabel lblPumpRol = new TextLabel(Translator.getString("label.PumpRol[i18n]: Roller Pump"));
    lblPumpRol.setHorizontalAlignment(JLabel.LEFT);
    lblPumpRol.setFont(textLabel.getFont().deriveFont(Font.BOLD, 13f));
    lblPumpRol.setLocation(30, 80);
    lblPumpRol.setSize(120, 45);
    componentSelectionPanel.add(lblPumpRol);
    JLabel lblPumpCen = new TextLabel(Translator.getString("label.PumpCen[i18n]: Centrifugal"));
    lblPumpCen.setHorizontalAlignment(JLabel.LEFT);
    lblPumpCen.setFont(textLabel.getFont().deriveFont(Font.BOLD, 13f));
    lblPumpCen.setLocation(174, 80);
    lblPumpCen.setSize(120, 45);
    componentSelectionPanel.add(lblPumpCen);
    
    Icon disabled = new ImageIcon(ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-CheckmarkDis.png"));
    JLabel ventilatorCheckmark = new JLabel(disabled);
    ventilatorCheckmark.setSize(32, 32);
    ventilatorCheckmark.setLocation(0, 103);
    componentSelectionPanel.add(ventilatorCheckmark);
    // add label: Ventilator conventional or HFOV i18n
    JLabel lblVentCon = new TextLabel(Translator.getString("label.VentCon[i18n]: Conventional"));
    lblVentCon.setHorizontalAlignment(JLabel.LEFT);
    lblVentCon.setFont(textLabel.getFont().deriveFont(Font.BOLD, 14f));
    lblVentCon.setLocation(30, 111);
    lblVentCon.setSize(120, 45);
    componentSelectionPanel.add(lblVentCon);
    JLabel lblVentHFOV = new TextLabel(Translator.getString("label.VentHFOV[i18n]: HFOV"));
    lblVentHFOV.setHorizontalAlignment(JLabel.LEFT);
    lblVentHFOV.setFont(textLabel.getFont().deriveFont(Font.BOLD, 14f));
    lblVentHFOV.setLocation(174, 111);
    lblVentHFOV.setSize(120, 45);
    componentSelectionPanel.add(lblVentHFOV);
    
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

          // update oxygenator
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

          // finalize scenario setup
          ScenarioCreator.setup(scenario);
          
          // show dialog and ask for the username
          String user;
          if (simulationListPanel.isVisible()) {
            // let's ask for a username to continue to the simulation
            user = "TODO:DialogWindow";  // TODO: add a input dialog for username
          }
          else {
            // Scenario: we don't set a username
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
    scenarioListPanel.addScenarioSelectionListener(scenarioSelectionListener);
    simulationListPanel.addScenarioSelectionListener(scenarioSelectionListener);

    // enable scenario
    boolean scenarioSelection = Configuration.getInstance().isSelectionScenarioTab();
    scenarioButton.setSelected(scenarioSelection);
    scenarioListPanel.setVisible(scenarioSelection);
    simulationButton.setSelected(!scenarioSelection);
    simulationListPanel.setVisible(!scenarioSelection);
  }
  
  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // listen to key presses
    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {  
    // remove key listener
    KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
    
    super.removeNotify();
  }

  /** 
   * Listen to key events. 
   * 
   * @param event  The event.
   * @return  True, if the event was handled.
   */
  public boolean dispatchKeyEvent(KeyEvent event) {
    // handle key event
    if (event.getID() == KeyEvent.KEY_RELEASED) {
      char ch = event.getKeyChar();
      if ((ch == 'e') || (ch == 'E')) {
        // go to edit state
        state.editState();
      }
    }
    
    // if the key should not be dispatched to the focused component, set discard event to true
    return false;
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
    g2.setFont(g.getFont().deriveFont(10f));
    g2.setColor(Color.DARK_GRAY);

    // draw editor info
    String editor = Translator.getString("text.editor[i18n]: Press 'E' to open the scenario editor");
    g2.drawString(editor, 5, 595);
    
    // draw the release info
    String release = Translator.getString("text.ReleaseVersion[i18n]: Release Version")
        + ": " + ECMOAppRelease.getReleaseVersion() + " | " + ECMOAppRelease.getReleaseTime();
    int width = g.getFontMetrics().stringWidth(release);
    g2.drawString(release, 800 - width - 5, 595);
  }
}
