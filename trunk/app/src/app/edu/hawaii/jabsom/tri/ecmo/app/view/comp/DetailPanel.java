package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.ACTComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.BubbleDetectorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.HeaterComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.InterventionComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PressureMonitorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component.UpdateListener;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 5, 2008
 */
public abstract class DetailPanel extends JPanel implements UpdateListener {

  /** Listener for  changes. */
  public static interface DetailActionListener {
    
    /**
     * Called when an action occured.
     * 
     * @param action  The action.
     */
    void handleAction(Action action);
  };
  
  /** Listeners for changes. */
  private List<DetailActionListener> listeners = new ArrayList<DetailActionListener>();

  /** The component. */
  private Component component;
  
  
  /**
   * Default constructor.
   * 
   * @param component  The component
   */
  protected DetailPanel(Component component) {
    this.component = component;
    
    // set transparent
    setOpaque(false);
  }
  
  /**
   * Creates an component panel instance for the inputted component.
   * 
   * @param game  The game.
   * @param component  The component.
   * @return  The panel for the component or null if none matched.
   */
  public static DetailPanel createInstance(Game game, Component component) {
    if (component instanceof Patient) {
      TubeComponent tubeComponent = (TubeComponent)game.getEquipment().getComponent(TubeComponent.class);
      return new PatientDetailPanel((Patient)component, tubeComponent);
    }
    else if (component instanceof PumpComponent) {
      return new PumpDetailPanel((PumpComponent)component);
    }
    else if (component instanceof OxygenatorComponent) {
      return new OxyDetailPanel((OxygenatorComponent)component);
    }
    else if (component instanceof PressureMonitorComponent) {
      return new PressureMonitorDetailPanel((PressureMonitorComponent)component);
    }
    else if (component instanceof HeaterComponent) {
      return new HeaterDetailPanel((HeaterComponent)component);
    }
    else if (component instanceof InterventionComponent) {
      return new InterventionDetailPanel((InterventionComponent)component, game.getTracker());
    }
    else if (component instanceof BubbleDetectorComponent) {
      return new BubbleDetectorDetailPanel((BubbleDetectorComponent)component);
    }
    else if (component instanceof ACTComponent) {
      return new ACTDetailPanel((ACTComponent)component);
    }
    else if (component instanceof LabComponent) {
      return LabDetailPanel.createInstance((LabComponent)component);
    }
    else if (component instanceof VentilatorComponent) {
      return new VentilatorDetailPanel((VentilatorComponent)component);
    }    
    else {
      // not found
      return null;
    }
  }
  
  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // add listener
    component.addUpdateListener(this);
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // remove listener
    component.removeUpdateListener(this);
    
    super.removeNotify();
  }

  /**
   * Returns the component.
   *
   * @return  The component.
   */
  public Component getComponent() {
    return component;
  }

  /**
   * Sets the component.
   *
   * @param component  The component to set.
   */
  public void setComponent(Component component) {
    this.component = component;
  }
  
  /**
   * Adds a listener.
   * 
   * @param listener  The listener to add.
   */
  public void addDetailActionListener(DetailActionListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeDetailActionListener(DetailActionListener listener) {
    listeners.remove(listener);
  }

  /**
   * Removes all the listeners.
   */
  public void clearDetailActionListeners() {
    listeners.clear();
  }
  
  /**
   * Notifies all the listeners.
   * 
   * @param action  The action that occured.
   */
  protected void notifyActionListeners(Action action) {
    for (DetailActionListener listener: listeners) {
      listener.handleAction(action);
    }
  }
}
