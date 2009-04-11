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
   * Updates the inputted game.
   * 
   * @param game  The game.
   * @param increment  The time increment in milliseconds.
   * @return  True, if goal is reached.
   */
  public static boolean execute(Game game, long increment) { // increment called in Manager.java (20ms)
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
      tubeSaO2 = 1 / ((23400 / ((paO2 * paO2 * paO2) + (150 * paO2))) + 1);
      tube.setSaO2(tubeSaO2);
      tube.setPostPH(patient.getPH());  // TODO: Reconfirm if this is trully patient
      tube.setPostPCO2(10);  // TODO: Reconfirm relation, usually lower due to sweep
      tube.setSvO2(tube.getSaO2() * 0.75);
      tube.setPrePH(patient.getPH());  // TODO: Reconfirm if this is trully patient
      tube.setPrePCO2(patient.getPCO2());  // TODO: Reconfirm if this is trully patient
      tube.setPreMembranePressure((pump.getFlow() * 400) + (oxigenator.getClotting() * 50));
      tube.setPostMembranePressure(tube.getPreMembranePressure() - 40);
      if ((pump.getPumpType() == PumpType.ROLLER) && (pump.isOn()) && (!tube.isVenusAOpen())) {
        tube.setVenousPressure(-100);
      }
      else {
        tube.setVenousPressure(0);
      }
      if (!tube.isArterialAOpen()) {
        tube.setArterialBubbles(true);
      }
      if ((!tube.isArterialBOpen()) && (!tube.isBridgeOpen())) {
        tube.setArterialBubbles(true);
      }
      if ((tube.getVenousPressure() < -75) && (pump.isOn())) {
        tube.setVenusBubbles(true);
      }
      else if (pump.isOn()) {
        tube.setVenusBubbles(false);
      }
      
      // update equipment (pressure monitor)
      pressureMonitor.setPreMembranePressure(tube.getPreMembranePressure());
      pressureMonitor.setPostMembranePressure(tube.getPostMembranePressure());
      pressureMonitor.setVenousPressure(tube.getVenousPressure());
      
      // update equipment (bubble detector)
      bubbleDetector.setAlarm(tube.isArterialBubbles());

      // update equipment (CDI monitor) note CDI is in line post-oxygenator
      cdiMonitor.setSaO2(tube.getSaO2());
      cdiMonitor.setSvO2(tube.getSvO2());
      cdiMonitor.setTemperature(heater.getTemperature());
      cdiMonitor.setHct(patient.getHct());
      cdiMonitor.setHgb(patient.getHgb());
      cdiMonitor.setHCO3(tube.getPostHCO3());
      cdiMonitor.setBE(tube.getPostBE(patient.getHgb()));
      cdiMonitor.setPH(tube.getPostPH());
      cdiMonitor.setPO2((oxigenator.getFiO2() * 600) + (oxigenator.getTotalSweep() * 10));
      cdiMonitor.setPCO2(tube.getPostPCO2());

      // update equipment (pump)
      if (bubbleDetector.isAlarm() || pressureMonitor.isAlarm()) {
        // turn off the pump
        pump.setOn(false);
      }
      if ((pump.getFlow() < (0.02f * patient.getWeight())) || (!pump.isOn())) {
        pump.setAlarm(true);
      }
      else {
        pump.setAlarm(false);
      }
      
      // update equipment (ventilator)
      if (ventilator.isEmergencyFuction()) {
        if (ventilator.getName().equals("High Freqency Ventilator")) {
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
      }
      else {
        alarmIndicator.setAlarm(false);
      }
      
      // update patient
      // update patient temperature
      double patientTemperature = patient.getTemperature();
      if (patientTemperature < heater.getTemperature()) {
        patient.setTemperature(patientTemperature + 0.01);
      }
      else if (patientTemperature > heater.getTemperature()) {
        patient.setTemperature(patientTemperature - 0.01);
      }
      
      // update patient pH, pCO2, HCO3, base excess
      double ccPerKg = pump.getFlow() * 1000 / patient.getWeight();
      Mode mode = tube.getMode();
      HeartFunction heartFunction = patient.getHeartFunction();
      //LungFunction lungFunction = patient.getLungFunction();
      double patientPH = Mediator.flowToPH(mode, ccPerKg, patient);
      patient.setPH(patientPH);
      double patientPCO2 = Mediator.flowToPCO2(mode, ccPerKg, patient);
      patient.setPCO2(patientPCO2);
      
      if ((mode == Mode.VV) && (heartFunction == HeartFunction.BAD)) {
        // TODO BAD heart steady decline to death
        Error.out("Using VV ECMO with poor heart function is not a good idea!");
        patientPH = patient.getPH();
        patient.setPH(patientPH - 0.001); //Heading down 0.001unit per 20ms
        patientPCO2 = patient.getPCO2();
        patient.setPCO2(patientPCO2 + 0.1); //Heading up 0.1 per 20ms
      }
      
      double po2 = oxigenator.getFiO2() * oxigenator.getTotalSweep() * pump.getFlow() * 100;
      double patientSaturation = 0;
      if (po2 != 0) {
        // see (b) in "Oxyhemoglobin Dissociation Curve.xls"
        patientSaturation = 1 / ((23400 / ((po2 * po2 * po2) + (150 * po2))) + 1);
      }
      patient.setO2Saturation(patientSaturation);     
      patient.setPO2((oxigenator.getFiO2() * (760 - 47)) - (patient.getPCO2() / 0.8));
      
      // TODO Patient bicarb and base excess calc? from Mark's table

      // and return if goal is reached
      return goal.isReached(game);
    }
    else {
      return true;
    }
  }
}
