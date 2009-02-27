package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;

/**
 * The scenario info panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 23, 2008
 */
public class ScenarioInfoPanel extends JPanel {

  /** The scenario or null for none. */
  private Scenario scenario;
  
  /** The name label. */
  private JLabel nameLabel;
  /** The description area. */
  private JTextArea descriptionArea;
  
  
  /**
   * Constructor for panel. 
   */
  public ScenarioInfoPanel() {
    // set look
    setOpaque(false);
    
    // set layout
    setLayout(new FormLayout("20px, fill:1px:grow, 20px"
                           , "20px, pref, 2px, pref, 20px"));
    CellConstraints cc = new CellConstraints();
    
    // add the scenario name
    nameLabel = new JLabel();
    nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 16.0f));
    add(nameLabel, cc.xy(2, 2));
    
    // add the description
    descriptionArea = new JTextArea();
    descriptionArea.setOpaque(false);
    descriptionArea.setFont(descriptionArea.getFont().deriveFont(Font.PLAIN, 14.0f));
    descriptionArea.setWrapStyleWord(true);
    descriptionArea.setLineWrap(true);
    add(descriptionArea, cc.xy(2, 4));
  }

  /**
   * Returns the scenario.
   *
   * @return  The scenario.
   */
  public Scenario getScenario() {
    return scenario;
  }

  /**
   * Sets the scenario.
   *
   * @param scenario  The scenario to set.
   */
  public void setScenario(Scenario scenario) {
    this.scenario = scenario;
    
    // set the data
    if (scenario != null) {
      nameLabel.setText(scenario.getName());
      descriptionArea.setText(scenario.getDescription());
    }
    else {
      // reset everything
      nameLabel.setText("");
    }
  }
}
