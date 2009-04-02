package edu.hawaii.jabsom.tri.ecmo.app.model;

import java.io.Serializable;

import edu.hawaii.jabsom.tri.ecmo.app.control.Mediator;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.Tracker;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.Goal;
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
  
  /** The elapsed time (in milliseconds). */
  private long elapsedTime;
  
  /** The tracker. */
  private Tracker tracker;
  
  /** The patient (e.g. the baby). */
  private Patient patient;
  /** The equipment. */
  private Equipment equipment;
  /** The class of links. */
  private Mediator mediator;
  

  /**
   * Constructor for game.
   * 
   * @param scenario  The initial scenario.
   */
  public Game(Scenario scenario) {
    this.scenario = scenario;

    // setup parameters
    elapsedTime = 0;
    tracker = new Tracker();
    patient = (Patient)ObjectCloner.deepCopy(scenario.getPatient());
    equipment = (Equipment)ObjectCloner.deepCopy(scenario.getEquipment());
    mediator = new Mediator();
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
  public void setTracker(Tracker tracker) {
    this.tracker = tracker;
  }

  /**
   * Returns the mediator.
   *
   * @return  The mediator of equations.
   */
  public Mediator getMediator() {
    return mediator;
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
}
