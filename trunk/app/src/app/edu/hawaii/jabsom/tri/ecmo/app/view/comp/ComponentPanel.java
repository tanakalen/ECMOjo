package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.ACTComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.AlarmIndicatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.BubbleDetectorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.CDIMonitorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.HeaterComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.InterventionComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PhysiologicMonitorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PressureMonitorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component.UpdateListener;

/**
 * The component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public abstract class ComponentPanel extends JPanel implements UpdateListener {

  /** Listener for  changes. */
  public static interface ComponentActionListener {
    
    /**
     * Called when the component is activated.
     * 
     * @param component  The component that got activated.
     */
    void handleActivation(ComponentPanel component);
    
    /**
     * Called when an action occured.
     * 
     * @param action  The action.
     */
    void handleAction(Action action);
  };
  
  /** Listeners for changes. */
  private List<ComponentActionListener> listeners = new ArrayList<ComponentActionListener>();

  /** The component. */
  private Component component;
  
  
  /**
   * Default constructor.
   * 
   * @param component  The component
   */
  protected ComponentPanel(Component component) {
    this.component = component;
    
    // set transparent
    setOpaque(false);
  }
  
  /**
   * Creates an component panel instance for the inputted component.
   * 
   * @param component  The component.
   * @return  The panel for the component or null if none matched.
   */
  public static ComponentPanel createInstance(Component component) {
    if (component instanceof ACTComponent) {
      return new ACTComponentPanel((ACTComponent)component);
    }
    else if (component instanceof AlarmIndicatorComponent) {
      return new AlarmIndicatorComponentPanel((AlarmIndicatorComponent)component);
    }
    else if (component instanceof BubbleDetectorComponent) {
      return new BubbleDetectorComponentPanel((BubbleDetectorComponent)component);
    }
    else if (component instanceof CDIMonitorComponent) {
      return new CDIMonitorComponentPanel((CDIMonitorComponent)component);
    }
    else if (component instanceof PumpComponent) {
      return new PumpComponentPanel((PumpComponent)component);
    }    
    else if (component instanceof VentilatorComponent) {
      return new VentilatorComponentPanel((VentilatorComponent)component);
    }    
    else if (component instanceof HeaterComponent) {
      return new HeaterComponentPanel((HeaterComponent)component);
    }    
    else if (component instanceof PhysiologicMonitorComponent) {
      return new PhysiologicMonitorComponentPanel((PhysiologicMonitorComponent)component);
    }
    else if (component instanceof PressureMonitorComponent) {
      return new PressureMonitorComponentPanel((PressureMonitorComponent)component);
    }    
    else if (component instanceof OxygenatorComponent) {
      return new OxyComponentPanel((OxygenatorComponent)component);
    }
    else if (component instanceof TubeComponent) {
      return new TubeComponentPanel((TubeComponent)component);
    }
    else if (component instanceof InterventionComponent) {
      return new InterventionComponentPanel((InterventionComponent)component);
    }
    else if (component instanceof LabComponent) {
      return new LabComponentPanel((LabComponent)component);
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
   * Assigns the component to a group.
   * 
   * @param group  The group.
   */
  public abstract void assign(ButtonGroup group);
  
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
  public void addComponentActionListener(ComponentActionListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeComponentActionListener(ComponentActionListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all the listeners.
   */
  protected void notifyActivationListeners() {
    for (ComponentActionListener listener: listeners) {
      listener.handleActivation(this);
    }
  }
  
  /**
   * Notifies all the listeners.
   * 
   * @param action  The action that occured.
   */
  protected void notifyActionListeners(Action action) {
    for (ComponentActionListener listener: listeners) {
      listener.handleAction(action);
    }
  }
}
