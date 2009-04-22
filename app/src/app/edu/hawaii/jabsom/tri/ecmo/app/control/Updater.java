package edu.hawaii.jabsom.tri.ecmo.app.control;

import king.lib.out.Error;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.AlarmIndicatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.BubbleDetectorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.CDIMonitorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.HeaterComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxigenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PhysiologicMonitorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PressureMonitorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.HeartFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.LungFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent.PumpType;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent.Mode;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent.ConventionalSubtype;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.Goal;

/**
 * The updater. 
 *
 * @author   king
 * @since    August 13, 2008
 */
public final class Updater {
    
  /**
   * Private constructor to prevent instantiation.
   */
  private Updater() {
    // not used
  }
  
  /**
   * Stores game values in the history.
   * 
   * @param game  The game.
   * @param history  The history.
   */
  public static void store(Game game, History history) {
    // equipment variables
    Patient patient = game.getPatient();
    Equipment equipment = game.getEquipment();
    PumpComponent pump = (PumpComponent)equipment
        .getComponent(PumpComponent.class);
    TubeComponent tube = (TubeComponent)equipment
        .getComponent(TubeComponent.class);
    OxigenatorComponent oxigenator = (OxigenatorComponent)equipment
        .getComponent(OxigenatorComponent.class);

    // store the heart rate
    history.setPatientTemperature(patient.getTemperature());
    // Set last updated flow to check pump flow, for membrane pressure interaction
    history.setFlow(pump.getFlow());
    // Set last updated sweep rate, for CDI update interaction
    history.setSweep(oxigenator.getTotalSweep());
    // Set last updated venous pressure, for pump interaction
    history.setVenousPressure(tube.getVenousPressure());
  }
  
  /**
   * Updates the inputed game. Called from Manager.java about every 20ms.
   * 
   * @param game  The game.
   * @param history  The historical values.
   * @param increment  The time increment in milliseconds.
   * @return  True, if goal is reached.
   */
  public static boolean execute(Game game, History history, long increment) { 
    Goal goal = game.getGoal();
    if (!goal.isReached(game)) {
      // increment time
      long elapsedTime = game.getElapsedTime() + increment;
      game.setElapsedTime(elapsedTime);

      // equipment variables
      Patient patient = game.getPatient();
      Equipment equipment = game.getEquipment();
      PhysiologicMonitorComponent physiologicMonitor = (PhysiologicMonitorComponent)equipment
           .getComponent(PhysiologicMonitorComponent.class);
      CDIMonitorComponent cdiMonitor = (CDIMonitorComponent)equipment
           .getComponent(CDIMonitorComponent.class);
      PumpComponent pump = (PumpComponent)equipment
           .getComponent(PumpComponent.class);
      TubeComponent tube = (TubeComponent)equipment
           .getComponent(TubeComponent.class);
      OxigenatorComponent oxigenator = (OxigenatorComponent)equipment
           .getComponent(OxigenatorComponent.class);
      VentilatorComponent ventilator = (VentilatorComponent)equipment
           .getComponent(VentilatorComponent.class);
      HeaterComponent heater = (HeaterComponent)equipment
           .getComponent(HeaterComponent.class);
      BubbleDetectorComponent bubbleDetector = (BubbleDetectorComponent)equipment
           .getComponent(BubbleDetectorComponent.class);
      PressureMonitorComponent pressureMonitor = (PressureMonitorComponent)equipment
           .getComponent(PressureMonitorComponent.class);
      AlarmIndicatorComponent alarmIndicator = (AlarmIndicatorComponent)equipment
           .getComponent(AlarmIndicatorComponent.class);
      
      // load some local variables for Updater
      double ccPerKg = pump.getFlow() * 1000 / patient.getWeight();

      // update equipment (physiologic monitor)
      physiologicMonitor.setTemperature(Math.rint(patient.getTemperature()));
      physiologicMonitor.setHeartRate(patient.getHeartRate());
      physiologicMonitor.setRespiratoryRate(patient.getRespiratoryRate());
      physiologicMonitor.setO2Saturation(patient.getO2Saturation());
      physiologicMonitor.setDiastolicBloodPressure(patient.getDiastolicBloodPressure());
      physiologicMonitor.setSystolicBloodPressure(patient.getSystolicBloodPressure());
      physiologicMonitor.setCentralVenousPressure(patient.getCentralVenousPressure());
      
      // update equipment (tubing)
      double paO2 = ((99.663 * oxigenator.getFiO2()) - 6.17) * 7.5;  // see PaO2-FiO2.spv (SPSS)
      if (paO2 == 0) {
        paO2 = 0.001;
      }  
      double tubeSaO2 = 0;   // see (b) in "Oxyhemoglobin Dissociation Curve.xls"
      tubeSaO2 = Mediator.calcOxygenSaturation(paO2);
      tube.setSaO2(tubeSaO2);
      tube.setSvO2(tube.getSaO2() * 0.75); // TODO: Need SvO2 curves
      tube.setPrePH(patient.getPH());  // TODO: Reconfirm if this is trully patient
      tube.setPrePCO2(patient.getPCO2());  // TODO: Reconfirm if this is trully patient
      // TODO: reconfirm if this is valid
      tube.setPreMembranePressure(tube.getPreMembranePressure() + (oxigenator.getClotting() * 50));
      // change in venous pressure changes pump flow
      double diffVenousPressure = tube.getVenousPressure() - history.getVenousPressure();
      if (diffVenousPressure != 0) {
        if (Mediator.isVenousPressureNormal(patient, tube.getVenousPressure())) {
          // Change in venous pressure by 5 increase/decease by 10%
          pump.setFlow(pump.getFlow() * (1 + (diffVenousPressure * 0.02)));
        }
        else {
          // Change in venous pressure by 2 increase/decrease by 10%
          pump.setFlow(pump.getFlow() * (1 + (diffVenousPressure * 0.05)));            
        }
      }
      // change in pump flow changes post-membrane CO2
      double currentTubePCO2 = tube.getPostPCO2();
      tube.setPostPCO2(currentTubePCO2 * pump.getFlow() / history.getFlow());
      // change in sweep changes pCO2: increase drops pCO2, decrease raises pCO2
      // TODO: reconfirm following behavior as rate of change is tiny! and reconfirm interaction
      tube.setPostPCO2(currentTubePCO2 - ((oxigenator.getTotalSweep() - history.getSweep()) * 0.15));
      //for debug:
//        if (oxigenator.getTotalSweep() - oldSweep != 0) {
//          System.out.println("old: " + oldSweep + ", new: " + oxigenator.getTotalSweep());
//          System.out.println(tube.getPostPCO2());
//        }
      double currentTubePH = tube.getPostPH();
      // TODO: reconfirm interaction as PCO2 increases PH falls
      tube.setPostPH(currentTubePH - ((tube.getPostPCO2() - currentTubePCO2) * 0.008));
      
      if ((pump.getPumpType() == PumpType.ROLLER) && (pump.isOn()) && (!tube.isVenousAOpen())) {
        tube.setVenousPressure(-100);
      }
//      else {
//        tube.setVenousPressure(0);
//      }
      // interaction of clamping
      
      // TODO: reconfirm bubbles
      if (!tube.isArterialAOpen()) {
        tube.setArterialBubbles(true);
      }
      if ((!tube.isArterialBOpen()) && (!tube.isBridgeOpen())) {
        tube.setArterialBubbles(true);
      }
      if ((tube.getVenousPressure() < -75) && (pump.isOn())) {
        tube.setVenousBubbles(true);
      }
      else if (pump.isOn()) {
        tube.setVenousBubbles(false);
      }
      
      // update equipment (bubble detector)
      bubbleDetector.setAlarm(tube.isArterialBubbles());

      // update equipment (CDI monitor) note CDI is in line post-oxygenator
      cdiMonitor.setSaO2(tube.getSaO2());
      cdiMonitor.setSvO2(tube.getSvO2());
      cdiMonitor.setTemperature(heater.getTemperature());
      cdiMonitor.setHct(patient.getHct());
      cdiMonitor.setHgb(patient.getHgb());
      cdiMonitor.setHCO3(tube.getPostHCO3());
      //cdiMonitor.setBE(tube.getPostBE(patient.getHgb()));
      cdiMonitor.setBE(tube.getPostBE()); // TODO: Reconfirm which version to set BE?
      cdiMonitor.setPH(tube.getPostPH());
      cdiMonitor.setPO2(paO2); // paO2 set in tubing update
      cdiMonitor.setPCO2(tube.getPostPCO2());

      // update equipment (pump)
      if ((pump.getFlow() < (0.02f * patient.getWeight())) || (!pump.isOn())) {
        pump.setAlarm(true);
      }
      else {
        pump.setAlarm(false);
      }
      // change in pump flow changes tubing pressures
      double difference = pump.getFlow() - history.getFlow();
      if ((difference != 0) && (history.getFlow() != 0)) {
        double pre = tube.getPreMembranePressure();
        double post = tube.getPostMembranePressure();
        if (oxigenator.getOxiType().equals(OxigenatorComponent.OxiType.QUADROX_D)) {
          if (difference > 0) {
            tube.setPreMembranePressure((((difference * 1000) * 0.0001) + 1) * pre);
            tube.setPostMembranePressure((((difference * 1000) * 0.0001) + 1) * post);
          }
          else {
            tube.setPreMembranePressure((1 - (Math.abs(difference * 1000) * 0.0001)) * pre);
            tube.setPostMembranePressure((1 - (Math.abs(difference * 1000) * 0.0001)) * post);
          }
        }
        else if (oxigenator.getOxiType().equals(OxigenatorComponent.OxiType.SCI_MED)) {
          if (difference > 0) {
            tube.setPreMembranePressure((((difference * 1000) * 0.00055) + 1) * pre);
            tube.setPostMembranePressure((((difference * 1000) * 0.0001) + 1) * post);            
          }
          else {
            tube.setPreMembranePressure((1 - (Math.abs(difference * 1000) * 0.00055)) * pre);
            tube.setPostMembranePressure((1 - (Math.abs(difference * 1000) * 0.0001)) * post);            
          }
        }
        
        // change in pump flow changes tube venous pressure
        double curVenousPressure = tube.getVenousPressure();
        if (difference > 0) {
          tube.setVenousPressure(curVenousPressure 
              - Math.rint(difference * 1000 * (1 / (patient.getWeight() * 10))));
        }
        else {
          tube.setVenousPressure(curVenousPressure 
              + Math.rint(Math.abs(difference) * 1000 * (1 / (patient.getWeight() * 10))));          
        }
      }
      
      // update equipment (pressure monitor)
      pressureMonitor.setPreMembranePressure(tube.getPreMembranePressure());
      pressureMonitor.setPostMembranePressure(tube.getPostMembranePressure());
      pressureMonitor.setVenousPressure(tube.getVenousPressure());
      
      // update equipment (ventilator)
      if (ventilator.isEmergencyFuction()) {
        if (ventilator.getName().equals("High Frequency Ventilator")) {
          patient.setRespiratoryRate(0);
        }
        if (ventilator.getName().equals("Conventional Ventilator")) {
          patient.setRespiratoryRate(((ConventionalSubtype) ventilator.getSubtype()).getRate());
        }
      }
      else {
        if (ventilator.getName().equals("Conventional Ventilator")) {
          patient.setRespiratoryRate(10); // non rescue rate?
        }
      }

      // update equipment (alarm)
      if (pressureMonitor.isAlarm()
          || bubbleDetector.isAlarm()
          || pump.isAlarm() 
          || oxigenator.isAlarm()
          || heater.isAlarm()) {
        alarmIndicator.setAlarm(true);
        if (bubbleDetector.isAlarm()) {
          // turn off the pump
          pump.setOn(false);
        }
        if (pressureMonitor.isAlarm()) {
          if (pump.getPumpType() == PumpComponent.PumpType.ROLLER) {
            // Pump flow stops
            pump.setOn(false);
          }
          else {
            // Pump flow slows to very low rate
            pump.setFlow(0.02f * patient.getWeight()); // from pump update above
          }
        }
      }
      else {
        alarmIndicator.setAlarm(false);
      }
      
      // update patient
      double patientTemperature = patient.getTemperature();
      if (patientTemperature < heater.getTemperature()) {
        patient.setTemperature(patientTemperature + 0.01);
      }
      else if (patientTemperature > heater.getTemperature()) {
        patient.setTemperature(patientTemperature - 0.01);
      }
      
      // update patient pH, pCO2, HCO3, base excess
      Mode mode = tube.getMode();
      HeartFunction heartFunction = patient.getHeartFunction();
      LungFunction lungFunction = patient.getLungFunction();
      try {
        double patientPH = Mediator.flowToPH(mode, ccPerKg, patient);
        patient.setPH(patientPH);
      }
      catch (Exception e) {
        double patientPH = patient.getPH();
        if ((mode == Mode.VV) && (heartFunction == HeartFunction.BAD)) {
          // TODO BAD heart steady decline to death
          Error.out("Using VV ECMO with poor heart function is not a good idea!");
          patient.setPH(patientPH - 0.001); //Heading down 0.001unit per 20ms
        }
        else if (e.getMessage().equals("Reached data limit")) {
          double patientPCO2 = patient.getPCO2();
          double newPH = 7;
          if (lungFunction == LungFunction.GOOD) {
            newPH = patientPH - (((cdiMonitor.getPCO2() * 1.11) - patientPCO2) * 0.008);
          }
          else {
            if (heartFunction == HeartFunction.GOOD) {
              newPH = patientPH - (((cdiMonitor.getPCO2() * 1.25) - patientPCO2) * 0.008);
            }
            else {
              newPH = patientPH - (((cdiMonitor.getPCO2() * 1.35) - patientPCO2) * 0.008);
            }
          }
          patient.setPH(newPH);
        }
        else {
          Error.out(e.getMessage());
        }
      }
      
      try {
        double patientPCO2 = Mediator.flowToPCO2(mode, ccPerKg, patient);
        patient.setPCO2(patientPCO2);
      }
      catch (Exception e) {
        if ((mode == Mode.VV) && (heartFunction == HeartFunction.BAD)) {
          // TODO BAD heart steady decline to death
          double patientPCO2 = patient.getPCO2();
          patient.setPCO2(patientPCO2 + 0.1); //Heading up 0.1 per 20ms
        }
        else if (e.getMessage().equals("Reached data limit")) {
          if (lungFunction == LungFunction.GOOD) {
            patient.setPCO2(cdiMonitor.getPCO2() * 1.11);
          }
          else {
            if (heartFunction == HeartFunction.GOOD) {
              patient.setPCO2(cdiMonitor.getPCO2() * 1.25);
            }
            else {
              patient.setPCO2(cdiMonitor.getPCO2() * 1.35);
            }
          }
        }
        else {
          Error.out(e.getMessage());
        }
      }
      
      // heart rate
      if (patient.getTemperature() != history.getPatientTemperature()) {
        if (patient.getTemperature() > history.getPatientTemperature()) {
          patient.setHeartRate(patient.getHeartRate() + 0.1 * patient.getHeartRate()
                            * (patient.getTemperature() - history.getPatientTemperature()));
        }
        else {
          patient.setHeartRate(patient.getHeartRate() - 0.1 * patient.getHeartRate()
                            * (history.getPatientTemperature() - patient.getTemperature()));
        }
      }
      
      // SBP increase 10% if flow increase by 20mL/kg/min in VA AND bad heart
      if ((mode == Mode.VA) && (heartFunction == HeartFunction.BAD)) {
        double bpadjust = 10 / (20 * patient.getWeight()); // % change SBP for 1mL/min flow change
        if (difference > 0) {
          patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() * (1 + bpadjust));
        }
        else {
          patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() * (1 - bpadjust));
        }
      }
      
      // TODO: pump flow to patient PaO2
      //currently a hedge
      double po2 = oxigenator.getFiO2() * 2 * pump.getFlow() * 100;
      double patientSaturation = 0;
      if (po2 != 0) {
        // see (b) in "Oxyhemoglobin Dissociation Curve.xls"
        patientSaturation = Mediator.calcOxygenSaturation(po2);
      }
      patient.setO2Saturation(patientSaturation);
      patient.setPO2(po2); // TODO: set from graph FiO2 with varying shunts?
      // Following is wrong, calculates the alveolar partial pressure of oxygen not arterial
      //patient.setPO2((oxigenator.getFiO2() * (760 - 47)) - (patient.getPCO2() / 0.8));
      
      //for debugging
//      if (pump.getFlow() != oldFlow) {
//        System.out.println("po2 through pump: " + po2 + ", po2 through patient: " + patient.getPO2());
//        System.out.println("pump fio2: " + oxigenator.getFiO2() + ", pco2: " + patient.getPCO2());
//      }
      
      // TODO Patient bicarb and base excess calc? from Mark's table
      
      // and return if goal is reached
      return goal.isReached(game);
    }
    else {
      return true;
    }
  }
}
