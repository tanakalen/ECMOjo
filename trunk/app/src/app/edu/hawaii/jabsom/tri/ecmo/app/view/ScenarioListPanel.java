package edu.hawaii.jabsom.tri.ecmo.app.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.hawaii.jabsom.tri.ecmo.app.gui.RolloverList;
import edu.hawaii.jabsom.tri.ecmo.app.gui.RolloverList.RolloverListener;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * The scenario list panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 23, 2008
 */
public class ScenarioListPanel extends JPanel {

  /** Listener for selections. */
  public static interface ScenarioSelectionListener {
    
    /**
     * Called when a scenario was selected.
     * 
     * @param scenario  The selected scenario or null for none.
     */
    void handleSelection(Scenario scenario);
  };
  
  /** Listeners for changes. */
  private List<ScenarioSelectionListener> listeners = new ArrayList<ScenarioSelectionListener>();

  /** The panel image. */
  private Image background;

  /** The list panel. */
  private RolloverList listPanel;
  
  
  /**
   * Constructor for panel. 
   * 
   * @param scenarios  The scenarios.
   */
  public ScenarioListPanel(final List<Scenario> scenarios) {
    // set look
    setOpaque(false);
    
    // set layout
    setLayout(new FormLayout("20px, fill:1px:grow, 33px"
                           , "70px, fill:1px:grow, 20px"));
    CellConstraints cc = new CellConstraints();
    
    // add list
    listPanel = new RolloverList();
    listPanel.setSelectionForeground(new Color(0.2f, 0.2f, 0.2f));
    listPanel.setForeground(new Color(0.2f, 0.2f, 0.2f));
    listPanel.setSelectionBackground(new Color(0.9f, 0.9f, 0.9f, 0.5f)); 
    listPanel.setBackground(new Color(0.9f, 0.9f, 0.9f, 0.0f)); 
    listPanel.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent event) {
        if (!event.getValueIsAdjusting()) {
          // notify about selection
          notifyScenarioSelection(getSelectedValue());
        }
      }      
    });
    listPanel.addRolloverListener(new RolloverListener() {
      public void rollover(int index) {
        if (index >= 0) {
          Scenario scenario = scenarios.get(index);
          listPanel.setToolTipText("<html><u>" 
              + scenario.getName() + "</u><br>" 
              + scenario.getDescription() + "</html>");
        }
        else {
          listPanel.setToolTipText(null);
        }
      }      
    });
    listPanel.setOpaque(false);
    listPanel.setFont(listPanel.getFont().deriveFont(Font.BOLD, 16.0f));
    listPanel.setListData(new Vector<Scenario>(scenarios));
    listPanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scrollPane = new JScrollPane(listPanel);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setBorder(null);
    add(scrollPane, cc.xy(2, 2));
  }

  /**
   * Called when this panel is being made visible/invisible.
   * 
   * @param visible  True for visible.
   */
  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
    
    // notify if this panel is being made visible
    if (visible) {
      notifyScenarioSelection(getSelectedValue());
    }
  }
  
  /**
   * Returns the selected index.
   * 
   * @return  The selected index.
   */
  public int getSelectedIndex() {
    return listPanel.getSelectedIndex();
  }
  
  /**
   * Sets the selected index.
   * 
   * @param index  The selected index.
   */
  public void setSelectedIndex(int index) {
    listPanel.setSelectedIndex(index);
  }
  
  /**
   * Returns the selected value.
   * 
   * @return  The selected value.
   */
  public Scenario getSelectedValue() {
    return (Scenario)listPanel.getSelectedValue();
  }
  
  /**
   * Sets the selected value.
   * 
   * @param scenario  The selected value.
   */
  public void setSelectedValue(Scenario scenario) {
    listPanel.setSelectedValue(scenario, true);
  }
  
  /**
   * Returns the background image.
   *
   * @return  The background.
   */
  public Image getBackgroundImage() {
    return background;
  }

  /**
   * Sets the background image.
   *
   * @param background  The background to set.
   */
  public void setBackgroundImage(Image background) {
    this.background = background;
  }

  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    // draws the image as background
    if (background != null) {
      g.drawImage(background, 0, 0, this);
    }
  }
  
  /**
   * Adds a listener.
   * 
   * @param listener  The listener to add.
   */
  public void addScenarioSelectionListener(ScenarioSelectionListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeScenarioSelectionListener(ScenarioSelectionListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all the listeners.
   * 
   * @param scenario  The scenario to notify about.
   */
  protected void notifyScenarioSelection(Scenario scenario) {
    for (ScenarioSelectionListener listener: listeners) {
      listener.handleSelection(scenario);
    }
  }
}
