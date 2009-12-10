package edu.hawaii.jabsom.tri.ecmo.app.state;

import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.hawaii.jabsom.tri.ecmo.app.gui.TextLabel;
import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioCreator;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;
import edu.hawaii.jabsom.tri.ecmo.app.view.ScenarioEditPanel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import king.lib.access.ImageLoader;

/**
 * The edit state panel. 
 *
 * @author Christoph Aschwanden
 * @since December 9, 2009
 */
public class EditStatePanel extends JPanel {
    
  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage("conf/image/interface/help/Base.jpg");

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
    
    // add editor panel
    ScenarioEditPanel scenarioEditPanel = new ScenarioEditPanel();
    scenarioEditPanel.addScenarioEditListener(new ScenarioEditPanel.ScenarioEditListener() {
      public void handleExit() {
        state.menuState();
      }
      public void handleRun(Scenario scenario) {
        ScenarioCreator.setup(scenario);
        state.gameState(scenario);
      }
    });
    scenarioEditPanel.setLocation(15, 95);
    scenarioEditPanel.setSize(770, 490);
    add(scenarioEditPanel);
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
