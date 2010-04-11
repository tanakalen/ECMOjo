package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

import java.util.HashMap;

import edu.hawaii.jabsom.tri.ecmo.app.model.lab.LabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.LabTestList;

/**
 * The lab component. 
 *
 * @author   king
 * @since    September 30, 2008
 */
public class LabComponent extends Component {
  
  /** The lab test assigned. */
  private Class<? extends LabTest> labTest;
  
  /** A hashmap (dictionary) of imaging. */
  private HashMap<String, HashMap> scnMap = new HashMap();
  
  /** A list of lab results. */
  private LabTestList results = new LabTestList();

  
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
   * Returns the results.
   *
   * @return  The results.
   */
  public LabTestList getResults() {
    return results;
  }

  /**
   * Adds a new result.
   * 
   * @param labTest  The lab test to add.
   */
  public void addResult(LabTest labTest) {
    results.add(labTest);
    notifyUpdate();
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return "Lab";
  }
  
  /**
   * Inserts string name for imaging file from Scenario by mode.
   *
   * @param  imaging The kind of imaging [Echo, Ultrasound, X-Ray].
   * @param  map The filename for custom imaging from scenario
   */
  public void putScenarioImaging(String imaging, HashMap map) {
    scnMap.put(imaging, map);
  }
  
  /**
   * Returns hashmap for imaging file from Scenario.
   *
   * @param  imaging The kind of imaging [Echo, Ultrasound, X-Ray].
   * @return  Hashmap of specified imaging by ECMO Mode.
   */
  public HashMap getScenarioImaging(String imaging) {
    return scnMap.get(imaging);
  }
  
  /**
   * Checks if there is imaging in scenario.
   *
   * @return  True if scenario has no imaging.
   */
  public boolean isScenarioEmpty() {
//    return scnMap.isEmpty();
    return scnMap.size() == 0;
  }
}
