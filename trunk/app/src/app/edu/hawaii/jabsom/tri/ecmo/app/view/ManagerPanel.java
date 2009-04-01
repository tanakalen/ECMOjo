package edu.hawaii.jabsom.tri.ecmo.app.view;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.control.Manager;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.ViewAction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.ComponentPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.DetailPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.PatientPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.TubeComponentPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.DetailPanel.DetailActionListener;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.ComponentPanel.ComponentActionListener;

import java.awt.Graphics;
import java.awt.Image;

import king.lib.access.ImageLoader;
import king.lib.out.Error;

/**
 * The manager panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class ManagerPanel extends JPanel {

  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage("conf/image/interface/game/Base.png");

  /** The current detail panel. null for none. */
  private DetailPanel detailPanel;
  
  /** The info label. */
  private JTextArea infoLabel;
  
  
  /**
   * Constructor for panel. 
   * 
   * @param manager  The manager for this panel.
   */
  public ManagerPanel(final Manager manager) {
    // set look
    setOpaque(true);
    
    // no layout
    setLayout(null);

    // add game components
    final Game game = manager.getGame();
    
    // add the time
    TimePanel timePanel = new TimePanel(game);
    add(timePanel);
    
    // set the detail panel
    detailPanel = null;
    
    // the action listener and create button group
    ButtonGroup componentGroup = new ButtonGroup();
    ComponentActionListener componentActionListener = new ComponentActionListener() {
      public void handleActivation(ComponentPanel component) {
        // remove old detail panel
        if (detailPanel != null) {
          detailPanel.clearDetailActionListeners();
          remove(detailPanel);
        }
        
        // add new detail panel
        detailPanel = DetailPanel.createInstance(game, component.getComponent());
        detailPanel.addDetailActionListener(new DetailActionListener() {
          public void handleAction(Action action) {
            // and execute the action
            manager.execute(action);
          }
        });
        add(detailPanel);
        
        // update info label
        infoLabel.setText(component.getComponent().toString());
        
        // send view action
        manager.execute(new ViewAction(component.getComponent().getClass()));
        
        // update
        revalidate();
        repaint();
      }
      
      public void handleAction(Action action) {
        // and execute the action
        manager.execute(action);
      }
    };
    
    // add patient
    PatientPanel patientPanel = new PatientPanel(game.getPatient());
    add(patientPanel);
    patientPanel.assign(componentGroup);
    patientPanel.addComponentActionListener(componentActionListener);
    
    // add equipment
    Equipment equipment = game.getEquipment();
    for (Component component: equipment) {
      ComponentPanel componentPanel = ComponentPanel.createInstance(component);
      if (componentPanel != null) {
        add(componentPanel);
        componentPanel.assign(componentGroup);
        componentPanel.addComponentActionListener(componentActionListener);
      }
      else {
        Error.out("Component not supported: " + component);
      }
    }
    
    // order components (drawn first)
    order(TubeComponentPanel.class);
    
    // add info label
    infoLabel = new JTextArea();
    infoLabel.setOpaque(false);
    infoLabel.setFont(infoLabel.getFont().deriveFont(14f));
    infoLabel.setWrapStyleWord(true);
    infoLabel.setLineWrap(true);
    infoLabel.setSize(140, 45);
    infoLabel.setLocation(345, 378);
    add(infoLabel);
  }

  /**
   * Orders a component and makes it drawn first.
   * 
   * @param componentType  The type of the component.
   */
  private void order(Class<? extends ComponentPanel> componentType) {
    java.awt.Component[] components = getComponents();
    for (java.awt.Component component: components) {
      if (componentType.isAssignableFrom(component.getClass())) {
//        setComponentZOrder(component, getComponentCount() - 1); // Cycles once, TubeComp set to 19
        return;
      }
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
