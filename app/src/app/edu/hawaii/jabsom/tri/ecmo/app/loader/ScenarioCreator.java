package edu.hawaii.jabsom.tri.ecmo.app.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.ACTComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.AlarmIndicatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.BubbleDetectorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.CDIMonitorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.HeaterComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.InterventionComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PhysiologicMonitorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PressureMonitorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent.OxyType;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.HeartFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.LungFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent.PumpType;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent.Mode;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.InterventionLocation;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.BaselineGoal;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.BloodGasLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ChemistryLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.HematologyLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ImagingLabTest;

/**
 * The scenario creator. 
 *
 * @author   king
 * @since    October 27, 2008
 */
public final class ScenarioCreator {
  
  /**
   * Private constructor to prevent instantiation.
   */
  private ScenarioCreator() {
    // not used
  }
  
  /**
   * Creates a new scenario. The scenario is created using "default" values.
   * 
   * @return  The scenario created.
   */
  public static Scenario create() {
    Scenario scenario = new Scenario();
    
    // set the name
    scenario.setName("Demo");
    
    // set the goal
    scenario.setGoal(new BaselineGoal());
    
    // set baseline values
    scenario.setBaseline(new Baseline());
    
    // set the patient
    Patient patient = new Patient();
    patient.setLife(1.0);
    patient.setAge(1);
    patient.setWeight(5);
    patient.setLungFunction(LungFunction.BAD);
    patient.setHeartFunction(HeartFunction.GOOD);
    patient.setSedated(true);
    patient.setBleeding(false);
    patient.setHeartRate(140);
    patient.setO2Saturation(0.85);
    patient.setSystolicBloodPressure(58);
    patient.setCentralVenousPressure(10);
    patient.setHgb(15.0);
    scenario.setPatient(patient);
    
    // set the equipment
    Equipment equipment = new Equipment();
    scenario.setEquipment(equipment);
    
    TubeComponent tubeComponent = new TubeComponent();
    tubeComponent.setMode(Mode.VA);
    tubeComponent.setArterialAOpen(true);
    tubeComponent.setArterialBOpen(true);
    tubeComponent.setVenousAOpen(true);
    tubeComponent.setVenousBOpen(true);
    tubeComponent.setBridgeOpen(false);
    tubeComponent.setBrokenCannula(false, TubeComponent.Status.normal, TubeComponent.problemLocation.none);
    tubeComponent.setBrokenETT(false);
    tubeComponent.setSuctionETT(false);
    tubeComponent.setVenousPressure(-10);
    tubeComponent.setArterialBubbles(false);
    tubeComponent.setVenousBubbles(false);
    equipment.add(tubeComponent);
    
    OxygenatorComponent oxiComponent = new OxygenatorComponent();
    oxiComponent.setOxyType(OxyType.SILICONE);
    oxiComponent.setTotalSweep(0.6);
    oxiComponent.setFiO2(0.5);
    oxiComponent.setClotting(0.0);
    oxiComponent.setBroken(false);
    equipment.add(oxiComponent);

    PumpComponent pumpComponent = new PumpComponent();
    pumpComponent.setPumpType(PumpType.CENTRIFUGAL);
    pumpComponent.setOn(true);
    pumpComponent.setFlow(0.6);
    equipment.add(pumpComponent);

    VentilatorComponent ventilatorComponent = new VentilatorComponent();
    ventilatorComponent.setSubtype(new VentilatorComponent.ConventionalSubtype());
    ventilatorComponent.setEmergencyFuction(false);
    ventilatorComponent.setFiO2(0.3);
    ventilatorComponent.setITime(0.5);
    equipment.add(ventilatorComponent);

    AlarmIndicatorComponent alarmIndicatorComponent = new AlarmIndicatorComponent();
    alarmIndicatorComponent.setAlarm(false);
    equipment.add(alarmIndicatorComponent);
    
    HeaterComponent heaterComponent = new HeaterComponent();
    heaterComponent.setTemperature(37.0);
    equipment.add(heaterComponent);
    
    PressureMonitorComponent pressureMonitorComponent = new PressureMonitorComponent();
    pressureMonitorComponent.setPreMembranePressureMin(100);
    pressureMonitorComponent.setPreMembranePressureMax(500);
    pressureMonitorComponent.setPostMembranePressureMin(100);
    pressureMonitorComponent.setPostMembranePressureMax(400);
    pressureMonitorComponent.setVenousPressureMin(-50);
    pressureMonitorComponent.setVenousPressureMax(20);
    equipment.add(pressureMonitorComponent);
    
    ACTComponent actComponent = new ACTComponent();
    equipment.add(actComponent);
    
    BubbleDetectorComponent bubbleDetectorComponent = new BubbleDetectorComponent();
    bubbleDetectorComponent.setAlarm(false);
    equipment.add(bubbleDetectorComponent);
    
    PhysiologicMonitorComponent physiologicMonitorComponent = new PhysiologicMonitorComponent();
    equipment.add(physiologicMonitorComponent);
    
    CDIMonitorComponent cdiMonitorComponent = new CDIMonitorComponent();
    equipment.add(cdiMonitorComponent);
    
    InterventionComponent interventionComponent1 = new InterventionComponent();
    interventionComponent1.setInterventionLocation(InterventionLocation.BEFORE_PUMP);
    equipment.add(interventionComponent1);

    InterventionComponent interventionComponent2 = new InterventionComponent();
    interventionComponent2.setInterventionLocation(InterventionLocation.BEFORE_OXYGENATOR);
    equipment.add(interventionComponent2);

    InterventionComponent interventionComponent3 = new InterventionComponent();
    interventionComponent3.setInterventionLocation(InterventionLocation.PATIENT);
    equipment.add(interventionComponent3);

    InterventionComponent interventionComponent4 = new InterventionComponent();
    interventionComponent4.setInterventionLocation(InterventionLocation.AFTER_OXYGENATOR);
    equipment.add(interventionComponent4);
    
    LabComponent labComponent1 = new LabComponent();
    labComponent1.setLabTest(BloodGasLabTest.class);
    equipment.add(labComponent1);

    LabComponent labComponent2 = new LabComponent();
    labComponent2.setLabTest(ChemistryLabTest.class);
    equipment.add(labComponent2);

    LabComponent labComponent3 = new LabComponent();
    labComponent3.setLabTest(HematologyLabTest.class);
    equipment.add(labComponent3);

    LabComponent labComponent4 = new LabComponent();
    labComponent4.setLabTest(ImagingLabTest.class);
    equipment.add(labComponent4);

    // and return the scenario...
    return scenario;
  }
  
  /**
   * Clones a scenario. Uses the internal load/save methods to create a "quick"
   * clone. This method is expected to be faster than "ObjectCloner.java".
   * 
   * @param scenario  The scenario to clone.
   * @return  The cloned scenario or null if there was an error.
   */
  public static Scenario clone(Scenario scenario) {
    ByteArrayOutputStream bos = null;
    ByteArrayInputStream bin = null;
    try {
      // store in byte array
      bos = new ByteArrayOutputStream();
      ScenarioLoader.save(bos, scenario);
      bos.flush();
      bos.close();
      
      // load from byte array
      bin = new ByteArrayInputStream(bos.toByteArray()); 
      return ScenarioLoader.load(bin); 
    } 
    catch (IOException e) {
      return null;
    } 
    finally {
      try {
        if (bos != null) {
          bos.close();
          bos = null;
        }
        
        bin.close();
        bin = null;
      }
      catch (IOException e) {
        return null;
      }
    }
  }
}
