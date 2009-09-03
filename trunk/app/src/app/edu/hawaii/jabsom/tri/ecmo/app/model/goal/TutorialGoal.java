package edu.hawaii.jabsom.tri.ecmo.app.model.goal;

import java.util.ArrayList;
import java.util.List;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.ACTRequestAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.BubbleAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.HeaterAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.InterventionAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.LabRequestAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.OxigenatorAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.PatientAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.PressureMonitorAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.PumpAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.TubeAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.VentilatorAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.ViewAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.PatientAction.Check;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.TubeAction.Location;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.InterventionComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.AlbuminIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.BloodIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.CatecholamineIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.FFPIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.HeparinBolusIntervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.Intervention;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.InterventionLocation;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.PlateletsIntervention;

/**
 * It's the tutorial with no "real" goal. 
 *
 * @author   Christoph Aschwanden
 * @author   Kin Lik Wang
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
   * And exit the tutorial.
   */
  public void exit() {
    progress = items.size();
    notifyUpdate();
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
   *   Action:Oxi:totalSweep:[>|<][flow]    total sweep flow changed.
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
    String trigger = getItem().getTrigger();
    if (trigger != null) {
      String[] items = trigger.split(":");
      if (items[0].equals("Action")) {
        if (items[1].equals("View")) {
          // Viewing a component          
          if (items.length <= 3) {
            // Action:View:[Component]  viewing, where [Component] is "PumpComponent", "ACTComponent", "Patient"... 
            if (action instanceof ViewAction) {
              String triggerComponent = items[2];
              Component component = ((ViewAction)action).getComponent();
              String actualComponent = component.getClass().getName();
              actualComponent = actualComponent.substring(actualComponent.lastIndexOf(".") + 1);
              if (actualComponent.equals(triggerComponent)) {
                progress++;
                notifyUpdate();
              }
            }
          }
          else {
            if (items[2].equals("LabTest")) {
              if (items.length <= 4) {
                // Action:View:LabTest:[LabTest]  viewing, where [LabTest] is "BloodGasLabTest", "ChemistryLabTest", ...
                if (action instanceof ViewAction) {
                  String requiredLabTest = items[3];
                  LabComponent component = (LabComponent)((ViewAction)action).getComponent();
                  String actualLabTest = component.getLabTest().getName();
                  actualLabTest = actualLabTest.substring(actualLabTest.lastIndexOf(".") + 1);
                  if (actualLabTest.equals(requiredLabTest)) {
                    progress++;
                    notifyUpdate();
                  }
                }
              }
            }
            else if (items[2].equals("Intervention")) {          
              if (items.length <= 4) {
                // Action:View:Intervention:[Location]  viewing, where [Location] is "BEFORE_PUMP", "PATIENT", ...
                if (action instanceof ViewAction) {
                  Component component = ((ViewAction)action).getComponent();
                  String actualComponent = component.getClass().getName();
                  actualComponent = actualComponent.substring(actualComponent.lastIndexOf(".") + 1);
                  if (actualComponent.equals("InterventionComponent")) {                    
                    InterventionLocation triggerLocation = null;
                    if (items[3].equals("BEFORE_PUMP")) {
                      triggerLocation = InterventionLocation.BEFORE_PUMP;
                    }
                    else if (items[3].equals("BEFORE_OXIGENATOR")) {
                      triggerLocation = InterventionLocation.BEFORE_OXIGENATOR;
                    }
                    else if (items[3].equals("AFTER_OXIGENATOR")) {
                      triggerLocation = InterventionLocation.AFTER_OXIGENATOR; 
                    }
                    else if (items[3].equals("PATIENT")) {
                      triggerLocation = InterventionLocation.PATIENT;
                    } 
                    InterventionComponent actualIntervention = (InterventionComponent) component;
                    InterventionLocation actualLocation = actualIntervention.getInterventionLocation();
                    if (actualLocation == triggerLocation) {                    
                      progress++;
                      notifyUpdate();
                    }
                  }
                }
              }
            }
          }
        }
        else if (items[1].equals("Pump")) {
          // Changing pump parameters
          if (action instanceof PumpAction) {
            if (items.length <= 2) {
              // Action:Pump  action on pump performed
              progress++;
              notifyUpdate();
            }
            else {           
              if (items[2].equals("on")) {
                // Action:Pump:on  pump turned on
                boolean on = ((PumpAction) action).isOn();
                if (on == true) {
                  progress++;
                  notifyUpdate();
                }
              }
              else if (items[2].equals("off")) {
                // Action:Pump:off  pump turned off
                boolean on = ((PumpAction) action).isOn();
                if (on == false) {
                  progress++;
                  notifyUpdate();
                }
              }
              else if (items[2].equals("flow")) { 
                // Action:Pump:flow:[>|<][flow]  pump flow changed: 
                // e.g.  Action:Pump:flow:>1.304  or  Action:Pump:flow:<1.4
                String operator = items[3].substring(0, 1);
                double triggerFlow = Double.parseDouble(items[3].substring(1));
                double actualFlow = ((PumpAction) action).getFlow();
                checkFlow(operator, actualFlow, triggerFlow);
              }              
            }
          }
        }
        else if (items[1].equals("Oxi")) {
          // Changing oxigenator parameters
          if (action instanceof OxigenatorAction) {
            if (items.length <= 2) {
              // Action:Oxi  oxigenator action performed
              progress++;
              notifyUpdate();
            }
            else {
              if (items[2].equals("totalSweep")) {
                // Action:Oxi:totalSweep:[>|<][flow]  total sweep flow changed.
                String operator = items[3].substring(0, 1);
                double triggerFlow = Double.parseDouble(items[3].substring(1));
                double actualFlow = ((OxigenatorAction) action).getTotalSweep();
                checkFlow(operator, actualFlow, triggerFlow);
              }
              else if (items[2].equals("fiO2")) {
                // Action:Oxi:fiO2:[>|<][flow]  fiO2 flow changed.
                String operator = items[3].substring(0, 1);
                double triggerFlow = Double.parseDouble(items[3].substring(1));
                double actualFlow = ((OxigenatorAction) action).getFiO2();
                checkFlow(operator, actualFlow, triggerFlow);
              }
            }
          }
        }
        else if (items[1].equals("Intervention")) {
          // Changing intervention parameters
          if (action instanceof InterventionAction) {
            if (items.length <= 2) {
              // Action:Intervention  intervention performed
              progress++;
              notifyUpdate();
            }
            else {          
              // Action:Intervention:[Name]  intervention executed where [Name] is "Albumin", "Blood", "Dopamine", ...
              Intervention actualIntervention = ((InterventionAction) action).getIntervention();
              if (items[2].equals("Blood")) {
                if (actualIntervention instanceof BloodIntervention) {
                  progress++;
                  notifyUpdate();
                }
              }
              else if (items[2].equals("Catecholamine")) {
                if (actualIntervention instanceof CatecholamineIntervention) {
                  progress++;
                  notifyUpdate();
                }
              }
              else if (items[2].equals("HeparinBolus")) {
                if (actualIntervention instanceof HeparinBolusIntervention) {
                  progress++;
                  notifyUpdate();
                }
              }
              else if (items[2].equals("FFP")) {
                if (actualIntervention instanceof FFPIntervention) {
                  progress++;
                  notifyUpdate();
                }
              }
              else if (items[2].equals("Albumin")) {
                if (actualIntervention instanceof AlbuminIntervention) {
                  progress++;
                  notifyUpdate();
                }
              }
              else if (items[2].equals("Platelets")) {
                if (actualIntervention instanceof PlateletsIntervention) {
                  progress++;
                  notifyUpdate();
                }
              }
            }
          }
        }
        else if (items[1].equals("Tube")) {
          // Changing tube parameters
          if (action instanceof TubeAction) {
            if (items.length <= 2) {
              // Action:Tube  action on tubs performed
              progress++;
              notifyUpdate();
            }
            else {
              // Action:Tube:[+|-][Location]  tubing change where [Location] is "ARTERIAL_A", "ARTERIAL_B", ...
              String operator = items[2].substring(0, 1);
              String triggerLocationString = items[2].substring(1);
              Location actualLocation = ((TubeAction) action).getLocation();
              boolean actualOpen = ((TubeAction) action).isOpen();
              
              Location triggerLocation = null;
              boolean triggerOpen = false;
              if (operator.equals("+")) {
                triggerOpen = false;
              }
              else if (operator.equals("-")) {
                triggerOpen = true;
              }
              
              if (triggerLocationString.equals("ARTERIAL_A")) {
                triggerLocation = Location.ARTERIAL_A;
              }
              else if (triggerLocationString.equals("ARTERIAL_B")) {
                triggerLocation = Location.ARTERIAL_B;
              }
              else if (triggerLocationString.equals("BRIDGE")) {
                triggerLocation = Location.BRIDGE;
              }
              else if (triggerLocationString.equals("VENOUS_A")) {
                triggerLocation = Location.VENOUS_A;
              }
              else if (triggerLocationString.equals("VENOUS_B")) {
                triggerLocation = Location.VENOUS_B;
              }
              
              if (actualLocation == triggerLocation && actualOpen == triggerOpen) {                    
                progress++;
                notifyUpdate();
              }
            }
          }

        }
        else if (items[1].equals("Heater")) {
          // Changing heater parameters
          if (action instanceof HeaterAction) {
            if (items.length <= 2) {
              // Action:Heater  action on heater performed
              progress++;
              notifyUpdate();
            }
            else {
              // Action:Heater:[>|<][temp]  heater changed: e.g. Action:Heater:>38.9
              String operator = items[2].substring(0, 1);
              double triggerTemperature = Double.parseDouble(items[2].substring(1));
              double actualTemperature = ((HeaterAction) action).getTemperature();
              checkFlow(operator, actualTemperature, triggerTemperature);
            }
          }
        }
        else if (items[1].equals("Patient")) {
          // Changing patient parameters
          if (action instanceof PatientAction) {
            if (items.length <= 2) {
              // Action:Patient  patient action performed.
              progress++;
              notifyUpdate();
            }
            else {
              // Action:Patient:[Check]  patient check performed where [Check] is "CANULA_SITE", "BLEEDING", ...
              Check actualCheck = ((PatientAction) action).getCheck();
              Check triggerCheck = null;
              if (items[2].equals("CANULA_SITE")) {
                triggerCheck = Check.CANULA_SITE;
              }
              else if (items[2].equals("BLEEDING")) {
                triggerCheck = Check.BLEEDING;
              }
              else if (items[2].equals("URINE_OUTPUT")) {
                triggerCheck = Check.URINE_OUTPUT;
              }
              else if (items[2].equals("SUCTION_ETT")) {
                triggerCheck = Check.SUCTION_ETT;
              }
              else if (items[2].equals("DIAPER")) {
                triggerCheck = Check.DIAPER;
              }
              else if (items[2].equals("SEDATION")) {
                triggerCheck = Check.SEDATION;
              }
              if (actualCheck == triggerCheck) {                    
                progress++;
                notifyUpdate();
              }
            }
          }
        }
        else if (items[1].equals("Pressure")) {
          // Changing pressure parameters
          if (action instanceof PressureMonitorAction) {
            if (items.length <= 2) {
              // Action:Pressure  action on pressure monitor performed.
              progress++;
              notifyUpdate();
            }
            else {
              // Action:Pressure:[Pref]:[>|<][x]  pressure monitor changed 
              // where [Pref] is "premin", "premax", "postmin", ...
              double actualTemperature = 0;
              if (items[2].equals("premin")) {
                actualTemperature = ((PressureMonitorAction) action).getPreMembranePressureMin();
              }
              else if (items[2].equals("premax")) {
                actualTemperature = ((PressureMonitorAction) action).getPreMembranePressureMax();
              }
              else if (items[2].equals("postmin")) {
                actualTemperature = ((PressureMonitorAction) action).getPostMembranePressureMin();
              }
              else if (items[2].equals("postmax")) {
                actualTemperature = ((PressureMonitorAction) action).getPostMembranePressureMax();
              }
              else if (items[2].equals("venousmin")) {
                actualTemperature = ((PressureMonitorAction) action).getVenousPressureMin();
              }
              else if (items[2].equals("venousmax")) {
                actualTemperature = ((PressureMonitorAction) action).getVenousPressureMax();
              }
              String operator = items[3].substring(0, 1);
              double triggerTemperature = Double.parseDouble(items[3].substring(1));
              checkFlow(operator, actualTemperature, triggerTemperature);
            }
          }
        }
        else if (items[1].equals("Ventilator")) {
          // Changing ventilator parameters
          if (action instanceof VentilatorAction) {
            if (items.length <= 2) {
              // Action:Ventilator  ventilator performed
              progress++;
              notifyUpdate();
            }
            else {
              if (items[2].equals("Emergency")) {
                if (items[3].equals("on")) {
                  // Action:Ventilator:Emergency:on  emergency ventilator action: on
                  boolean on = ((VentilatorAction) action).isEmergencyFunction();
                  if (on == true) {
                    progress++;
                    notifyUpdate();
                  }
                }
                else if (items[3].equals("off")) {
                  // Action:Ventilator:Emergency:off  emergency ventilator action: off
                  boolean on = ((VentilatorAction) action).isEmergencyFunction();
                  if (on == false) {
                    progress++;
                    notifyUpdate();
                  }
                }
              }
            }
          }

        }
        else if (items[1].equals("Bubble")) {
          // Changing bubble parameters
          if (action instanceof BubbleAction) {
            if (items.length <= 2) {
              // Action:Bubble  bubble action performed
              progress++;
              notifyUpdate();
            }
            else {
              // Action:Bubble:[+|-][Location]  bubble action where [Location] is "arterial" or "venous".
              String operator = items[2].substring(0, 1);
              String triggerLocationString = items[2].substring(1);

              boolean triggerHasBubble = false;
              if (operator.equals("+")) {
                triggerHasBubble = true; 
              }
              else if (operator.endsWith("-")) {
                triggerHasBubble = false;
              }
              
              boolean actualHasBubble = false;
              if (triggerLocationString.equals("arterial")) {
                actualHasBubble = ((BubbleAction) action).isArterialBubbles();
              }
              else if (triggerLocationString.equals("venous")) {
                actualHasBubble = ((BubbleAction) action).isVenousBubbles();
              }
              
              if (actualHasBubble == triggerHasBubble) {
                progress++;
                notifyUpdate();
              }
            }
          }
        }
        else if (items[1].equals("LabRequest")) {
          // Requesting lab values
          if (action instanceof LabRequestAction) {
            if (items.length <= 2) {
              // Action:LabRequest  lab request action performed
              progress++;
              notifyUpdate();
            }
          }
        }
        else if (items[1].equals("ACTRequest")) {
          // Requesting lab values
          if (action instanceof ACTRequestAction) {
            if (items.length <= 2) {
              // Action:LabRequest  lab request action performed
              progress++;
              notifyUpdate();
            }
          }
        }
      }
    }
  }
  
  /**
   * Check actual flow is greater or less than the trigger flow. 
   * 
   * @param operator  The operator (greater or less).
   * @param actualFlow  The actual flow.
   * @param triggerFlow  The trigger flow.
   */
  private void checkFlow(String operator, double actualFlow, double triggerFlow) {
    if (operator.equals(">")) {
      if (actualFlow > triggerFlow) {
        progress++;
        notifyUpdate();
      }
    }
    else if (operator.equals("<")) {
      if (actualFlow < triggerFlow) {
        progress++;
        notifyUpdate();
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
