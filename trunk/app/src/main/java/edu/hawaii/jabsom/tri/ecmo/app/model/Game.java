package edu.hawaii.jabsom.tri.ecmo.app.model;

import java.io.Serializable;

import edu.hawaii.jabsom.tri.ecmo.app.control.ActionList;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.Tracker;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.Goal;
import king.lib.script.model.Script;
import king.lib.util.ObjectCloner;

/**
 * The game. 
 *
 * @author   king
 * @since    August 13, 2008
 */
public class Game implements Serializable {

  /** The scenario associated. */
  private Scenario scenario;
  /** The user that is executing the game. */
  private String user;
  
  /** The elapsed time (in milliseconds). */
  private long elapsedTime;
  
  /** The patient (e.g. the baby). */
  private Patient patient;
  /** The equipment. */
  private Equipment equipment;
  
  /** The tracker. */
  private Tracker tracker;
  /** The actions executed. */
  private ActionList actions;
  
  
  /**
   * Constructor for game.
   * 
   * @param scenario  The initial scenario.
   * @param user  The user that is executing the game.
   */
  public Game(Scenario scenario, String user) {
    this.scenario = scenario;
    this.user = user;
    
    // setup parameters
    elapsedTime = 0;
    patient = (Patient)ObjectCloner.deepCopy(scenario.getPatient());
    equipment = (Equipment)ObjectCloner.deepCopy(scenario.getEquipment());
    
    // for statistics
//    tracker = new Tracker();
    this.setTracker(new Tracker());
    actions = new ActionList();
  }
  
  /**
   * Returns the user that is executing the game.
   * 
   * @return  The user.
   */
  public String getUser() {
    return user;
  }
  
  /**
   * Returns the name.
   * 
   * @return  The name.
   */
  public String getName() {
    return scenario.getName();
  }
  
  /**
   * Returns the description.
   * 
   * @return  The description.
   */
  public String getDescription() {
    return scenario.getDescription();
  }
  
  /**
   * Returns the goal.
   * 
   * @return  The goal.
   */
  public Goal getGoal() {
    return scenario.getGoal();
  }
  
  /**
   * Returns the baseline.
   *
   * @return  The baseline.
   */
  public Baseline getBaseline() {
    return scenario.getBaseline();
  }

  /**
   * Returns the script.
   * 
   * @return  The script or null for none.
   */
  public Script getScript() {
    return scenario.getScript();
  }

  /**
   * Returns the elapsed time.
   *
   * @return  The elapsed time (in milliseconds).
   */
  public long getElapsedTime() {
    return elapsedTime;
  }

  /**
   * Sets the elapsed time.
   *
   * @param elapsedTime  The elapsed time (in milliseconds).
   */
  public void setElapsedTime(long elapsedTime) {
    this.elapsedTime = elapsedTime;
  }
  
  /**
   * Returns the equipment.
   *
   * @return  The equipment.
   */
  public Equipment getEquipment() {
    return equipment;
  }
  
  /**
   * Sets the equipment.
   *
   * @param equipment  The equipment to set.
   */
  public void setEquipment(Equipment equipment) {
    this.equipment = equipment;
  }
  
  /**
   * Returns the patient.
   *
   * @return  The patient.
   */
  public Patient getPatient() {
    return patient;
  }
  
  /**
   * Sets the patient.
   *
   * @param patient  The patient to set.
   */
  public void setPatient(Patient patient) {
    this.patient = patient;
  }
  
  /**
   * Returns the tracker.
   *
   * @return  The tracker.
   */
  public Tracker getTracker() {
    return tracker;
  }

  /**
   * Sets the tracker.
   *
   * @param tracker  The tracker to set.
   */
  private void setTracker(Tracker tracker) {
    this.tracker = tracker;
  }

  /**
   * Returns the actions that have been executed.
   *
   * @return  The actions.
   */
  public ActionList getActions() {
    return actions;
  }

  /**
   * Sets the actions that have been executed.
   *
   * @param actions  The actions.
   */
  public void setActions(ActionList actions) {
    this.actions = actions;
  }
}
