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
  public static boolean execute(Game game, long increment) {
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
      physiologicMonitor.setHeartRate(patient.getHeartRate());
      physiologicMonitor.setO2Saturation(patient.getO2Saturation());
      physiologicMonitor.setDiastolicBloodPressure(patient.getDiastolicBloodPressure());
      physiologicMonitor.setSystolicBloodPressure(patient.getSystolicBloodPressure());
      
      // update equipment (tubing)
      double cdiPo2 = cdiMonitor.getPO2();
      double tubeSaO2 = 0;
      if (cdiPo2 != 0) {
        // see (b) in "Oxyhemoglobin Dissociation Curve.xls"
        tubeSaO2 = 1 / ((23400 / ((cdiPo2 * cdiPo2 * cdiPo2) + (150 * cdiPo2))) + 1);
      }
      tube.setSaO2(tubeSaO2);
      tube.setSvO2(tube.getSaO2() * 0.75);
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

      // update equipment (CDI monitor)
      cdiMonitor.setSaO2(tube.getSaO2());
      cdiMonitor.setSvO2(tube.getSvO2());
      cdiMonitor.setTemperature(heater.getTemperature());
      cdiMonitor.setHct(patient.getHct());
      cdiMonitor.setHgb(patient.getHgb());
      cdiMonitor.setHCO3(patient.getHCO3());
      cdiMonitor.setBE(patient.getBE());
      cdiMonitor.setPH(patient.getPH());
      cdiMonitor.setPO2((oxigenator.getFiO2() * 600) + (oxigenator.getTotalSweep() * 10));
      cdiMonitor.setPCO2(patient.getPCO2());

      // update equipment (pump)
      if (bubbleDetector.isAlarm() || pressureMonitor.isAlarm()) {
        // turn off the pump
        pump.setOn(false);
      }
      if ((pump.getFlow() < (0.02f * patient.getWeight())) || (!pump.isOn())) {
        pump.setAlarm(true);
      }

      // update equipment (alarm)
      boolean alarm = false;
      if (pressureMonitor.isAlarm()) {
        alarm = true;
      }
      if (bubbleDetector.isAlarm()) {
        alarm = true;
      }
      if (pump.isAlarm()) {
        alarm = true;
      }
      if (oxigenator.isAlarm()) {
        alarm = true;
      }
      if (heater.isAlarm()) {
        alarm = true;
      }
      alarmIndicator.setAlarm(alarm);
      
      // update patient
      double ccPerKg = pump.getFlow() * 1000 / patient.getWeight();
      Mode mode = tube.getMode();
      HeartFunction heartFunction = patient.getHeartFunction();
      LungFunction lungFunction = patient.getLungFunction();
      double patientPH;
      double patientPCO2;
      if (mode == Mode.VA) {
        // VA ECMO
        if (heartFunction == HeartFunction.GOOD) {
          // GOOD heart
          if (lungFunction == LungFunction.GOOD) {
            // GOOD lung
// TODO patientPH = f(ccPerKg);
// TODO patientPCO2 = f(ccPerKg);
          }
          else {
            // BAD lung
// TODO patientPH = f(ccPerKg);
// TODO patientPCO2 = f(ccPerKg);
          }
        }
        else {
          // BAD heart
          if (lungFunction == LungFunction.GOOD) {
            // GOOD lung
// TODO patientPH = f(ccPerKg);
// TODO patientPCO2 = f(ccPerKg);
          }
          else {
            // BAD lung
// TODO patientPH = f(ccPerKg);
// TODO patientPCO2 = f(ccPerKg);
          }
        }
      }
      else if (mode == Mode.VV) {
        // VV ECMO
        if (heartFunction == HeartFunction.GOOD) {
          // GOOD heart
          if (lungFunction == LungFunction.GOOD) {
            // GOOD lung
// TODO patientPH = f(ccPerKg);
// TODO patientPCO2 = f(ccPerKg);
          }
          else {
            // BAD lung
// TODO patientPH = f(ccPerKg);
// TODO patientPCO2 = f(ccPerKg);
          }
        }
        else {
          // BAD heart
          Error.out("Using VV ECMO with poor heart function is not a good idea!");
          patientPH = 7;
          patientPCO2 = 50;
        }
      }
      else {
        Error.out("Tubing Mode not Defined: " + mode);
      }
// TODO: uncomment      patient.setPH(patientPH);
// TODO: uncomment      patient.setPCO2(patientPCO2);
      
      double po2 = oxigenator.getFiO2() * oxigenator.getTotalSweep() * pump.getFlow() * 100;
      double patientSaturation = 0;
      if (po2 != 0) {
        // see (b) in "Oxyhemoglobin Dissociation Curve.xls"
        patientSaturation = 1 / ((23400 / ((po2 * po2 * po2) + (150 * po2))) + 1);
      }
      patient.setO2Saturation(patientSaturation);     
      patient.setPO2((oxigenator.getFiO2() * (760 - 47)) - (patient.getPCO2() / 0.8));

      // and return if goal is reached
      return goal.isReached(game);
    }
    else {
      return true;
    }
  }
}
