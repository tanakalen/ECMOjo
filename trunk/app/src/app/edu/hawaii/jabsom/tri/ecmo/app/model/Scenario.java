package edu.hawaii.jabsom.tri.ecmo.app.model;

import java.io.Serializable;

import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.Goal;

/**
 * The scenario. 
 *
 * @author   king
 * @since    August 13, 2008
 */
public class Scenario implements Serializable {

  /** The name of the scenario. */
  private String name;
  /** The scenario description. */
  private String description;
  
  /** The goal. */
  private Goal goal;
  
  /** The baseline values. */
  private Baseline baseline;
  
  /** The patient start values (e.g./i.e. the baby). */
  private Patient patient;
  /** The equipment start values. */
  private Equipment equipment;
  
    
  /**
   * Returns the name.
   *
   * @return  The name.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Sets the name.
   *
   * @param name  The name to set.
   */
  public void setName(String name) {
    this.name = name;
  }
  
  /**
   * Returns the description.
   *
   * @return  The description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description  The description to set.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Returns the goal.
   *
   * @return  The goal.
   */
  public Goal getGoal() {
    return goal;
  }

  /**
   * Sets the goal.
   *
   * @param goal  The goal to set.
   */
  public void setGoal(Goal goal) {
    this.goal = goal;
  }

  /**
   * Returns the baseline.
   *
   * @return  The baseline.
   */
  public Baseline getBaseline() {
    return baseline;
  }

  /**
   * Sets the baseline.
   *
   * @param baseline  The baseline.
   */
  public void setBaseline(Baseline baseline) {
    this.baseline = baseline;
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
   * Returns the scenario name.
   * 
   * @return  The scenario name.
   */
  public String toString() {
    return getName();
  }
}
