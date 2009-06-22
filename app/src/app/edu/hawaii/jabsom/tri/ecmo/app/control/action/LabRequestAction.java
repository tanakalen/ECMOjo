package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.HeartFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.LungFunction;
//import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.BloodGasLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ChemistryLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.HematologyLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.LabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.UltrasoundLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.XRayLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.BloodGasLabTest.BloodGasType;

/**
 * The lab request action. 
 *
 * @author   king
 * @since    August 29, 2008
 */
public class LabRequestAction extends Action {

  /** The lab test assigned. */
  private Class<? extends LabTest> labTest;
  
  
  /**
   * Returns the lab test.
   *
   * @return  The lab test.
   */
  public Class<? extends LabTest> getLabTest() {
    return labTest;
  }

  /**
   * Sets the lab test.
   *
   * @param labTest  The lab test to set.
   */
  public void setLabTest(Class<? extends LabTest> labTest) {
    this.labTest = labTest;
  }

  /**
   * Executes the action on the game.
   * 
   * @param game  The game.
   */
  public void execute(Game game) {
    // create a new lab result
    LabTest result = null;
    if (labTest.equals(BloodGasLabTest.class)) {
      BloodGasLabTest labTest = new BloodGasLabTest();
      
      //baby gas
      Patient patient = game.getPatient();
      labTest.setBloodGasType(BloodGasType.BABY);
      labTest.setPH(patient.getPH());
      labTest.setPCO2(patient.getPCO2());
      labTest.setPO2(patient.getPO2());
      labTest.setHCO3(patient.getHCO3());
      labTest.setBE(patient.getBE());
      result = labTest;
    }
    else if (labTest.equals(ChemistryLabTest.class)) {
      ChemistryLabTest labTest = new ChemistryLabTest();
      
      // fill in details
      Patient patient = game.getPatient();
      double weight = patient.getWeight();
      
      // lactate from patient
      labTest.setLactate(patient.getLactate());

      if (weight < 5) {
        // glucose 50-80 range
        labTest.setGluc(50 + (Math.random() * 5) + (patient.hashCode() % 25));
        // ion ca 0.95-1.50 range
        labTest.setIonCa(0.95 + (Math.random() * 0.55));
        // K 3-7 range
        labTest.setK(3 + (Math.random() * 4));
        // Na 139-146 range
        labTest.setNa(139 + (Math.random() * 5) + (patient.hashCode() % 2));
      }
      else if (weight < 10) {
        // glucose 60-100 range
        labTest.setGluc(60 + (Math.random() * 5) + (patient.hashCode() % 35));        
        // ion ca 0.95-1.50 range
        labTest.setIonCa(0.95 + (Math.random() * 0.55));
        // K 3.5-5 range
        labTest.setK(3.5 + (Math.random() * 1.5));
        // Na 138-145 range
        labTest.setNa(138 + (Math.random() * 5) + (patient.hashCode() % 2));     
      }
      else if (weight < 50) {
        // glucose 70-105 range
        labTest.setGluc(70 + (Math.random() * 5) + (patient.hashCode() % 30));                     
        // ion ca 0.95-1.50 range
        labTest.setIonCa(0.95 + (Math.random() * 0.55));
        // K 3.5-5.0 range
        labTest.setK(3.5 + (Math.random() * 1.5));
        // Na 139-145 range
        labTest.setNa(139 + (Math.random() * 6));
      }
      else {
        // glucose 60-100 range
        labTest.setGluc(60 + (Math.random() * 5) + (patient.hashCode() % 35));  
        // ion ca 0.95-1.50 range
        labTest.setIonCa(0.95 + (Math.random() * 0.55));
        // K 3.5-4.5 range
        labTest.setK(3.5 + (Math.random()));
        // Na 136-146 range
        labTest.setNa(136 + (Math.random() * 5) + (patient.hashCode() % 5));
      }
      result = labTest;
    }
    else if (labTest.equals(HematologyLabTest.class)) {
      HematologyLabTest labTest = new HematologyLabTest();
      
      // fill in details
      Patient patient = game.getPatient();
      double weight = patient.getWeight();

      // Hct from patient Hgb*0.3
      labTest.setHct(patient.getHct());
      // Hgb from patient 14.5-22.5 range
      labTest.setHgb(patient.getHgb());
      
      if (weight < 5) {
        // Fibrinogen 125-300 range
        labTest.setFibrinogen(patient.getFibrinogen() + (Math.random() * 5) + (patient.hashCode() % 70));
        // platelets 100-478 * 10(9) range
        labTest.setPlatelets(patient.getPlatelets() + (Math.random() * 5) + (patient.hashCode() % 373));
        // PT 13-18 range
        labTest.setPt(patient.getPt() + (Math.random() * 5));
        // PTT 80-100 range
        labTest.setPtt(patient.getPtt() + (Math.random() * 5) + (patient.hashCode() % 15));
        // WBC 9-30 * 10(9) range
        labTest.setWbc(patient.getWbc() + (Math.random() * 5) + (patient.hashCode() % 16));
      }
      else if (weight < 10) {
        // Fibrinogen 200-400 range
        labTest.setFibrinogen(patient.getFibrinogen() + (Math.random() * 5) + (patient.hashCode() % 195));
        // platelets 100-400 * 10(9) range
        labTest.setPlatelets(patient.getPlatelets() + (Math.random() * 5) + (patient.hashCode() % 295));
        // PT 11-15 range
        labTest.setPt(patient.getPt() + (Math.random() * 4));
        // PTT 80-100 range
        labTest.setPtt(patient.getPtt() + (Math.random() * 5) + (patient.hashCode() % 15));
        // WBC 5-19.5 * 10(9) range
        labTest.setWbc(patient.getWbc() + (Math.random() * 4.5) + (patient.hashCode() % 10));
      }
      else if (weight < 50) {
        // Fibrinogen 200-400 range
        labTest.setFibrinogen(patient.getFibrinogen() + (Math.random() * 5) + (patient.hashCode() % 195));
        // platelets 100-400 * 10(9) range
        labTest.setPlatelets(patient.getPlatelets() + (Math.random() * 5) + (patient.hashCode() % 295));
        // PT 11-15 range
        labTest.setPt(patient.getPt() + (Math.random() * 4));
        // PTT 80-100 range
        labTest.setPtt(patient.getPtt() + (Math.random() * 5) + (patient.hashCode() % 15));
        // WBC 5-16 * 10(9) range
        labTest.setWbc(patient.getWbc() + (Math.random() * 5) + (patient.hashCode() % 6));
      }
      else {
        // Fibrinogen 200-400 range
        labTest.setFibrinogen(patient.getFibrinogen() + (Math.random() * 5) + (patient.hashCode() % 195));
        // platelets 100-400 * 10(9) range
        labTest.setPlatelets(patient.getPlatelets() + (Math.random() * 5) + (patient.hashCode() % 295));
        // PT 11-15 range
        labTest.setPt(patient.getPt() + (Math.random() * 4));
        // PTT 80-100 range
        labTest.setPtt(patient.getPtt() + (Math.random() * 5) + (patient.hashCode() % 15));
        // WBC 4.5-11 * 10(9) range
        labTest.setWbc(patient.getWbc() + (Math.random() * 4.5) + (patient.hashCode() % 2));
      }
      result = labTest;
    }
    else if (labTest.equals(UltrasoundLabTest.class)) {
      UltrasoundLabTest labTest = new UltrasoundLabTest();
      
      // Create image name depending on the patient
      Patient patient = game.getPatient();
      String name = "echo-";
      name = name + ((patient.getHeartFunction() == HeartFunction.GOOD) ? "good-" : "bad-");
      name = name + ((patient.getLungFunction() == LungFunction.GOOD) ? "good-" : "bad-");
      labTest.setDescription("Ultrasound");
      labTest.setImageName("echo-noIVH-va-newborn-good-good-good.png");
      
      labTest.setTime(game.getElapsedTime() / 1000);
      result = labTest;
    }
    else if (labTest.equals(XRayLabTest.class)) {
      XRayLabTest labTest = new XRayLabTest();
      
      // Create image name depending on the patient
      Patient patient = game.getPatient();      
      String name = "xray-";
      name = name + ((patient.getHeartFunction() == HeartFunction.GOOD) ? "good-" : "bad-");
      name = name + ((patient.getLungFunction() == LungFunction.GOOD) ? "good-" : "bad-");
      labTest.setDescription("Chest, X-Ray");
      labTest.setImageName("xray-CXR-normal.png");
      
      labTest.setTime(game.getElapsedTime() / 1000);
      result = labTest;
    }
    else {
      // error
      result = null;
    }
    
    // add lab test to the correct lab component
    for (Component component: game.getEquipment()) {
      if (component instanceof LabComponent) {
        LabComponent labComponent = (LabComponent)component;
        if (labComponent.getLabTest().isAssignableFrom(labTest)) {
          labComponent.addResult(result);
        }
      }
    }
  }
}
