package edu.hawaii.jabsom.tri.ecmo.app.model.goal;

import java.util.ArrayList;
import java.util.List;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.PumpAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.ViewAction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;

/**
 * It's the tutorial with no "real" goal. 
 *
 * @author   king
 * @since    Jan 12, 2009
 */
public class TutorialGoal extends Goal {
  
  /** A tutorial item. */
  public static class Item {
    
    /** The text. */
    private String text;
    /** The thing we are looking for. */
    private String trigger;
    
    /**
     * Returns the text.
     *
     * @return  The text.
     */
    public String getText() {
      return text;
    }
    
    /**
     * Sets the text.
     *
     * @param text  The text.
     */
    public void setText(String text) {
      this.text = text;
    }
    
    /**
     * Returns the trigger.
     *
     * @return  The trigger or null if display only.
     */
    public String getTrigger() {
      return trigger;
    }
    
    /**
     * Sets the trigger.
     *
     * @param trigger  The trigger or null if display only.
     */
    public void setTrigger(String trigger) {
      this.trigger = trigger;
    }
  }
  
  /** Listener for changes. */
  public static interface TutorialListener {
    
    /**
     * Called when the component changed.
     */
    void handleTutorial();
  };
  
  /** Listeners for changes. */
  private List<TutorialListener> listeners = new ArrayList<TutorialListener>();

  /** The progress indicator. */
  private int progress = 0;
  /** The items to work through. */
  private List<Item> items;
  
  
  /**
   * Proceeds to the next item. 
   */
  public void proceed() {
    // proceed
    if (getItem().getTrigger() == null) {
      progress++;
      notifyUpdate();
    }
  }
  
  /**
   * Handles an action. The following trigger actions are supported:
   * <p>
   *   Action:View:[Component]              viewing, where [Component] is "PumpComponent", "ACTComponent", "Patient"... 
   *   Action:View:LabTest:[LabText]        viewing, where [LabTest] is "BloodGasLabTest", "ChemistryLabTest", ...
   *   Action:View:Intervention:[Location]  viewing, where [Location] is "BEFORE_PUMP", "PATIENT", ...
   *   Action:Pump                          action on pump performed
   *   Action:Pump:on                       pump turned on
   *   Action:Pump:off                      pump turned off
   *   Action:Pump:flow:[>|<][flow]         pump flow changed: e.g.  Action:Pump:flow:>1.304  or  Action:Pump:flow:<1.4
   *   Action:Oxi                           oxigenator action performed
   *   Action:Oxi:O2:[>|<][flow]            O2 flow changed.
   *   Action:Oxi:CO2:[>|<][flow]           CO2 flow changed.
   *   Action:Oxi:fiO2:[>|<][flow]          fiO2 flow changed.
   *   Action:Intervention                  intervention performed
   *   Action:Intervention:[Name]           intervention executed where [Name] is "Albumin", "Blood", "Dopamine", ...
   *   Action:Tube                          action on tubs performed
   *   Action:Tube:[+|-][Location]          tubing change where [Location] is "ARTERIAL_A", "ARTERIAL_B", ...
   *   Action:Heater                        action on heater performed
   *   Action:Heater:[>|<][temp]            heater changed: e.g. Action:Heater:>38.9
   *   Action:Patient                       patient action performed.
   *   Action:Patient:[Check]               patient check performed where [Check] is "CANULA_SITE", "BLEEDING", ...
   *   Action:Pressure                      action on pressure monitor performed.
   *   Action:Pressure:[Pref]:[>|<][x]      pressure monitor changed where [Pref] is "premin", "premax", "postmin", ...
   *   Action:Ventilator:Emergency:on       emergency ventilator action: on
   *   Action:Ventilator:Emergency:off      emergency ventilator action: off
   *   Action:Bubble                        bubble action performed
   *   Action:Bubble:[+|-][Location]        bubble action where [Location] is "arterial" or "venous".
   * </p>
   * 
   * @param action  The action to handle.
   */
  @Override
  public void handle(Action action) {
    
    // TODO: Implement all the action triggers as listed above! 
    //       -> add missing ones as needed...
    
    String trigger = getItem().getTrigger();
    if (trigger != null) {
      String[] items = trigger.split(":");
      if (items[0].equals("Action")) {
        if (items[1].equals("View")) {
          // Viewing a component
          if (items.length <= 3) {
            // [Component] 
            if (action instanceof ViewAction) {
              String triggerComponent = items[2];
              Class<? extends Component> component = ((ViewAction)action).getComponent();
              String actualComponent = component.getName();
              actualComponent = actualComponent.substring(actualComponent.lastIndexOf(".") + 1);
              if (actualComponent.equals(triggerComponent)) {
                progress++;
                notifyUpdate();
              }
            }
          }
          else {
            if (items[2].equals("LabTest")) {
              // LabTest  

            }
            else if (items[2].equals("Intervention")) {          
              // Intervention
            }
          }
        }
        else if (items[1].equals("Pump")) {
          // Changing pump parameters
          if (action instanceof PumpAction) {
            if (items.length <= 2) {
              progress++;
              notifyUpdate();
            }
            else {
              if (items[2].equals("on")) {
                // "on"
              
              }
              else if (items[2].equals("off")) {
                // "off"
              
              }
              else if (items[2].equals("flow")) { 
                // "flow" 
              }
              
            }
          }
        }
      }
    }
  }
  
  /**
   * Returns the current active tutorial item.
   * 
   * @return  The active tutorial item or null if completed.
   */
  public Item getItem() {
    if (progress < items.size()) {
      return items.get(progress);
    }
    else {
      return null;
    }
  }
  
  /**
   * Returns the items.
   *
   * @return  The items.
   */
  public List<Item> getItems() {
    return items;
  }

  /**
   * Sets the items.
   *
   * @param items  The items.
   */
  public void setItems(List<Item> items) {
    this.items = items;
  }

  /**
   * Returns true if the goal is reached.
   * 
   * @param game  The game.
   * @return  True for reached.
   */
  @Override
  public boolean isReached(Game game) {
    return progress >= items.size();
  }

  /**
   * Returns true if the "game" is "won". I.e. if the player succeeded.
   * 
   * @param game  The game.
   * @return  True for won.
   */
  @Override
  public boolean isWon(Game game) {
    return true;
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  @Override
  public String getName() {
    return "Tutorial";
  }
  
  /**
   * Adds a listener.
   * 
   * @param listener  The listener to add.
   */
  public void addTutorialListener(TutorialListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeTutorialListener(TutorialListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all the listeners.
   */
  private void notifyUpdate() {
    for (TutorialListener listener: listeners) {
      listener.handleTutorial();
    }
  }
}
