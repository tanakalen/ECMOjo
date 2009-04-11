package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
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
//    LabTest bgBaby = null, bgPre = null, bgPost = null;
    if (labTest.equals(BloodGasLabTest.class)) {
      BloodGasLabTest baby = new BloodGasLabTest();
//      BloodGasLabTest pre = new BloodGasLabTest();
//      BloodGasLabTest post = new BloodGasLabTest();
      
      //baby gas
      Patient patient = game.getPatient();
      baby.setBloodGasType(BloodGasType.BABY);
      baby.setPH(patient.getPH());
      baby.setPCO2(patient.getPCO2());
      baby.setPO2(patient.getPO2());
      baby.setHCO3(patient.getHCO3());
      baby.setBE(patient.getBE());
      result = baby;
//      bgBaby = baby;
      
//      //pre-oxygenator gas
//      TubeComponent tube = (TubeComponent)game.getEquipment().getComponent(TubeComponent.class);
//      pre.setBloodGasType(BloodGasType.PRE);
//      pre.setPH(tube.getPrePH());
//      pre.setPCO2(tube.getPrePCO2());
//      pre.setPO2((tube.getSvO2() * (760 - 47)) - (tube.getPrePCO2() / 0.8)); //is this correct?
//      pre.setHCO3(tube.getPreHCO3());
//      pre.setBE(tube.getPreBE(patient.getHgb()));
//      bgPre = pre;
//      
//      //post-oxygenator gas
//      post.setBloodGasType(BloodGasType.POST);
//      post.setPH(tube.getPostPH());
//      post.setPCO2(tube.getPostPCO2());
//      post.setPO2((tube.getSaO2() * (760 - 47)) - (tube.getPostPCO2() / 0.8)); //is this correct?
//      post.setHCO3(tube.getPostHCO3());
//      post.setBE(tube.getPostBE(patient.getHgb()));
//      bgPost = post;
    }
    else if (labTest.equals(ChemistryLabTest.class)) {
      ChemistryLabTest labTest = new ChemistryLabTest();
      
      // fill in details
      // TODO
      
      result = labTest;
    }
    else if (labTest.equals(HematologyLabTest.class)) {
      HematologyLabTest labTest = new HematologyLabTest();
      
      // fill in details
      // TODO
      
      result = labTest;
    }
    else if (labTest.equals(UltrasoundLabTest.class)) {
      UltrasoundLabTest labTest = new UltrasoundLabTest();
      
      // FIX!!!!!!!!!! -> need real lab tests... (from scenario)!?
      // TODO

      labTest.setDescription("Abdomen, Ultrasound");
      labTest.setImageName("CXR-normal.jpg");
      labTest.setTime(game.getElapsedTime() / 1000);
      result = labTest;
    }
    else if (labTest.equals(XRayLabTest.class)) {
      XRayLabTest labTest = new XRayLabTest();
      
      // FIX!!!!!!!!!! -> need real lab tests... (from scenario)!?
      // TODO

      labTest.setDescription("Chest, X-Ray");
      labTest.setImageName("CXR-normal.jpg");
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
          if (labTest.equals(BloodGasLabTest.class)) { // TODO: Ugly please refactor
            labComponent.addResult(result);
//            labComponent.addResult(bgBaby);
//            labComponent.addResult(bgPre);
//            labComponent.addResult(bgPost);
          }
          else {
            labComponent.addResult(result);
          }
        }
      }
    }
  }
}
