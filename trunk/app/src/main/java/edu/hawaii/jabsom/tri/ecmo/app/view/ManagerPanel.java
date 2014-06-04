package edu.hawaii.jabsom.tri.ecmo.app.view;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.control.Manager;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.ViewAction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.BaselineGoal;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.ComponentPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.DetailPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.PatientPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.TubeComponentPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.DetailPanel.DetailActionListener;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.ComponentPanel.ComponentActionListener;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import king.lib.access.ImageLoader;
import king.lib.out.Error;
import king.lib.util.Translator;

/**
 * The manager panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class ManagerPanel extends JPanel {

  /** The panel image. */
  private Image background = ImageLoader.getInstance().getImage(
      "conf/image/interface/game/Base.png");
//      Translator.getString("image.gamebase[i18n]: conf/image/interface/game/Base.png"));

  /** The current detail panel. null for none. */
  private DetailPanel detailPanel;
  
  
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
    if (game.getGoal() instanceof BaselineGoal) {
      TimePanel timePanel = new TimePanel(game);
      add(timePanel);
    }
    
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
        
        // send view action
        manager.execute(new ViewAction(component.getComponent()));
        
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
    PatientPanel patientPanel = new PatientPanel(game.getPatient(), game.getName(), game.getDescription());
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
        setComponentZOrder(component, new Integer(getComponentCount() - 1));
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
    // draws the image as background
    g.drawImage(background, 0, 0, this);
    
    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
    // text properties
    g2.setFont(g.getFont().deriveFont(10f));
    g2.setColor(Color.DARK_GRAY);
    
    // label pressure monitor
    String lblPresMon = Translator.getString("label.PressureMonitor[i18n]: Pressure Monitor");
    g2.drawString(lblPresMon, centerStringX(g2, lblPresMon, 704), 100);
    // label heater controls
    String lblHeatCon = Translator.getString("label.HeaterControls[i18n]: Heater Controls");
    g2.drawString(lblHeatCon, centerStringX(g2, lblHeatCon, 710), 252);
    // label gas blender
    String lblGasBlen = Translator.getString("label.GasBlender[i18n]: Gas Blender");
    g2.drawString(lblGasBlen, centerStringX(g2, lblGasBlen, 728), 337);
    // label bubble detector
    String lblBubDet = Translator.getString("label.BubbleDet[i18n]: Bubble Det.");
    g2.drawString(lblBubDet, centerStringX(g2, lblBubDet, 466), 523);
    // label pump controls
    String lblPumpCtl = Translator.getString("label.PumpControls[i18n]: Pump Controls");
    g2.drawString(lblPumpCtl, centerStringX(g2, lblPumpCtl, 704), 500);
  }
  
  /**
   * Helper function for centering labels passing in x-position center.
   * 
   * @param g2d  The {@link Graphics2D} environment.
   * @param s  The string to center.
   * @param xPos  The x-position as int for current center point.
   * @return int for starting position to draw string.
   */
  private int centerStringX(Graphics2D g2d, String s, int xPos) {
    int stringLen = (int)
        g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
    int start = stringLen/2;
    return xPos - start;
  }
}
