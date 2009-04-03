package edu.hawaii.jabsom.tri.ecmo.app.control.action;

import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.BloodGasLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ChemistryLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.HematologyLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.LabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.UltrasoundLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.XRayLabTest;

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
    LabTest result;
    if (labTest.equals(BloodGasLabTest.class)) {
      BloodGasLabTest labTest = new BloodGasLabTest();
      
      // fill in details
      // TODO
      
      result = labTest;
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
          labComponent.addResult(result);
        }
      }
    }
  }
}
