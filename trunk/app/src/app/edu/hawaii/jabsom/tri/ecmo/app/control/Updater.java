package edu.hawaii.jabsom.tri.ecmo.app.control;

import king.lib.out.Error;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.AlarmIndicatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.BubbleDetectorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.CDIMonitorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.HeaterComponent;
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
   * Private boolean whether we are on pump or not.
   */
  private static boolean onPump = true; // Are we on pump? for clamping interaction

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
    OxygenatorComponent oxigenator = (OxygenatorComponent)equipment
        .getComponent(OxygenatorComponent.class);

    // store current update cycle if patient isSedated
    if (patient.isSedated()) {
      history.setPatientSedatedTime(history.getPatientSedatedTime() + 1);
    }
    else {
      history.setPatientSedatedTime(0);
    }
    // store the temperature
    history.setPatientTemperature(patient.getTemperature());
    // Set last updated flow to check pump flow, for membrane pressure interaction
    history.setFlow(pump.getFlow());
    // Set last updated sweep rate, for CDI update interaction
    history.setSweep(oxigenator.getTotalSweep());
    // Set last updated venous pressure, for pump interaction
    history.setVenousPressure(tube.getVenousPressure());
    // Set last clamp information
    history.setArterialAOpen(tube.isArterialAOpen());
    history.setArterialBOpen(tube.isArterialBOpen());
    history.setBridgeOpen(tube.isBridgeOpen());
    history.setVenousAOpen(tube.isVenousAOpen());
    history.setVenousBOpen(tube.isVenousBOpen());
  }
  
  /**
   * Updates the inputed game. Called from Manager.java about every 20ms. 50 updates/sec
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
      OxygenatorComponent oxygenator = (OxygenatorComponent)equipment
           .getComponent(OxygenatorComponent.class);
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
      Mode mode = tube.getMode();
      HeartFunction heartFunction = patient.getHeartFunction();
      LungFunction lungFunction = patient.getLungFunction();
      double ccPerKg = pump.getFlow() * 1000 / patient.getWeight();
      if (!pump.isOn()) {
        ccPerKg = 0;
      }
      
      // update equipment (physiologic monitor)
      physiologicMonitor.setTemperature(Math.rint(patient.getTemperature()));
      physiologicMonitor.setHeartRate(patient.getHeartRate());
      physiologicMonitor.setRespiratoryRate(patient.getRespiratoryRate());
      physiologicMonitor.setO2Saturation(patient.getO2Saturation());
      physiologicMonitor.setDiastolicBloodPressure(patient.getDiastolicBloodPressure());
      physiologicMonitor.setSystolicBloodPressure(patient.getSystolicBloodPressure());
      physiologicMonitor.setCentralVenousPressure(patient.getCentralVenousPressure());
      
      // update equipment (tubing)
      double paO2 = ((99.663 * oxygenator.getFiO2()) - 6.17) * 7.5;  // see PaO2-FiO2.spv (SPSS)
      if (paO2 == 0) {
        paO2 = 0.001;
      }  
      double tubeSaO2 = 0;   // see (b) in "Oxyhemoglobin Dissociation Curve.xls"
      tubeSaO2 = Mediator.calcOxygenSaturation(paO2);
      tube.setSaO2(tubeSaO2);
      
      try{ 
        double tubeSvO2 = Mediator.flowToSvO2(mode, ccPerKg, patient);
        tube.setSvO2(tubeSvO2); 
      }
      catch (Exception e) {
        // something wrong
        tube.setSvO2(0.0);
        Error.out(e.getMessage());
      }
      tube.setPrePH(patient.getPH());  // TODO: Reconfirm if this is trully patient
      tube.setPrePCO2(patient.getPCO2());  // TODO: Reconfirm if this is trully patient
      // TODO: reconfirm if this is valid
//      tube.setPreMembranePressure(tube.getPreMembranePressure() + (oxygenator.getClotting() * 50));
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
      // tube.setPostPCO2(currentTubePCO2 * pump.getFlow() / history.getFlow());
      // change in sweep changes pCO2: increase drops pCO2, decrease raises pCO2
      
      // TODO: reconfirm following behavior as rate of change is tiny! and reconfirm interaction
      // LT: tube.setPostPCO2(currentTubePCO2 - ((oxygenator.getTotalSweep() - history.getSweep()) * 0.15));
      if (pump.getFlow() == 0) {
        tube.setPostPCO2(0);
      }
      else {
        double sweepFactor = oxygenator.getTotalSweep() / pump.getFlow();
        if (sweepFactor < 0.5) {
          tube.setPostPCO2(80 - 30 / 0.5 * sweepFactor);
        }
        else if (sweepFactor < 3) {
          tube.setPostPCO2(60 - 10 / 0.5 * sweepFactor);
        }
        else {
          tube.setPostPCO2(0);
        }
      }
      
      double currentTubePH = tube.getPostPH();
      // TODO: reconfirm interaction as PCO2 increases PH falls
      tube.setPostPH(currentTubePH - ((tube.getPostPCO2() - currentTubePCO2) * 0.008));
      
      if ((pump.getPumpType() == PumpType.ROLLER) && (pump.isOn()) && (!tube.isVenousAOpen())) {
        tube.setVenousPressure(-100);
      }
      
      if (tube.isArterialAOpen() != history.isArterialAOpen() || tube.isArterialBOpen() != history.isArterialBOpen() 
       || tube.isBridgeOpen() != history.isBridgeOpen()
       || tube.isVenousAOpen() != history.isVenousAOpen() || tube.isVenousBOpen() != history.isVenousBOpen()) { 
        // do interaction of clamping  
        tubeUndo(tube, pump, pressureMonitor, oxygenator, physiologicMonitor, patient, history);
        tubeDo(tube, pump, pressureMonitor, oxygenator, physiologicMonitor, patient);
      }
      
      if ((pump.isOn() && (pump.getFlow() > 0)) && ((!tube.isVenousAOpen()) || (!tube.isVenousBOpen())
                                                 || (!tube.isArterialAOpen()) || (!tube.isArterialBOpen()))) {
        pump.setOn(false);
      }
      
      if ((tube.getVenousPressure() < -75) && (pump.isOn()) && (pump.getFlow() > 0)) { // Consider abstract 75 out
        tube.setVenousBubbles(true);
      }
      else if (pump.isOn()) {
        tube.setVenousBubbles(false);
      }
      // check tubing status (normal, kink, high or low) then location
      if (tube.isBrokenCannula()) {
        if (tube.getCannulaProblem() == TubeComponent.Status.KINK) {
          if (tube.getCannlaProblemLocation() == TubeComponent.problemLocation.arterial) {
            if (tube.getPreMembranePressure() > 750) {
              tube.setPreMembranePressure(750.0);
              tube.setPostMembranePressure(750.0);              
            }
            else {
              tube.setPreMembranePressure(tube.getPreMembranePressure() + 0.03);
              tube.setPostMembranePressure(tube.getPostMembranePressure() + 0.03);
            }
            // If limits set appropriately,  
            if (!pressureMonitor.isAlarm()) {
              // premembrane pressure will equal postmembrane pressure up to 750.
//              tube.setPreMembranePressure(tube.getPostMembranePressure());
              // If roller then pressures will stay at 750, else it's centrifugal then it would stop and reset to 35. 
              if (pump.getPumpType() == PumpType.ROLLER) {
//                tube.setPreMembranePressure(tube.getPreMembranePressure() + 0.03);
                // stay at 750???????
                if (tube.getPreMembranePressure() > 750) {
//                  tube.setPreMembranePressure(750.0);
//                  tube.setPostMembranePressure(750.0);
                }
              }
              if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
                // reset to 35 or decrease to 35???
//                tube.setPreMembranePressure(35.0);
              }
            }
            else {
              // If limits not set appropriately then rupture for roller else centrifugal stop then reset to 35. 
              if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
                // reset to 35 or decrease to 35???
//                tube.setPreMembranePressure(35.0);
              }
              // The venous pressure increases. Pump flow to 0. ??????
              tube.setVenousPressure(pressureMonitor.getVenousPressure() + 0.002);
              //pump.setFlow(0.0);
            }
            // patient gets worse
            patientDies(patient, ventilator, increment);
          }
          else if (tube.getCannlaProblemLocation() == TubeComponent.problemLocation.venous) {
            //TODO: venous kink
            // If roller pump then premembrane = mean BP, else if centrifugal then premembrane decreases. 
            if (pump.getPumpType() == PumpType.ROLLER) {
//              tube.setPreMembranePressure(physiologicMonitor.getMeanBloodPressure());
            }
            if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
//              tube.setPreMembranePressure(tube.getPreMembranePressure() - 0.03);
            } 
            
            // If both roller and silicon (SciMed) add another decrease of 10%. Venous pressure increases. 
            if (pump.getPumpType() == PumpType.ROLLER && oxygenator.getOxyType() == OxyType.SILICONE) {
//              tube.setPreMembranePressure(tube.getPreMembranePressure() * 0.999);
              tube.setVenousPressure(tube.getVenousPressure() + 0.002);
            }
            
            // If limits are appropriate then pump flow = 0, else limits not set properly then 
            if (!pressureMonitor.isAlarm()) {
              pump.setFlow(0.0);
            }
            else {
              // if cenrifugal pump then pump flow to 0. 
              if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
                pump.setFlow(0.0);
              }
              // Else if roller pump, air in venous line.
              if ((pump.getPumpType() == PumpType.ROLLER) && (pump.isOn() && (pump.getFlow() > 0.0))) {
                tube.setVenousBubbles(true);
              }   
            }
          }
          else { //cephalad,none
            //TODO: no kink or cephalad
          }
        }
        else if (tube.getCannulaProblem() == TubeComponent.Status.LEAK) {
          //TODO: for roller pump, raceway tubing leak
          /*
           * What happens here is there is small drops of blood usually on the 
           * part of the tubing in the roller pump. There may be reduced premembrane
           * pressures. There may be air entering the circuit.
           * 
           * The appropriate intervention is to start emergency vent and clamp off artery,
           * open bridge, clamp off vein, then fix circuit.
           * 
           * For this portion we need to determine pump type, if roller, show leak,
           * adjust pressures, bubble alarm and air in circuit venous (premembrane)
           * for the Quadrox or air in both venous and artery for Scimed. Probably
           * need x seconds to complete the interventions before the scenario ends.
           */
        }
        else { //high,low,normal
          //TODO: cannula malposition; how to make ECMOjo chatter?; normal -> do nothing
        }
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
        if (oxygenator.getOxyType().equals(OxygenatorComponent.OxyType.QUADROX_D)) {
          if (difference > 0) {
//            tube.setPreMembranePressure((((difference * 1000) * 0.0001) + 1) * pre);
//            tube.setPostMembranePressure((((difference * 1000) * 0.0001) + 1) * post);
          }
          else {
//            tube.setPreMembranePressure((1 - (Math.abs(difference * 1000) * 0.0001)) * pre);
//            tube.setPostMembranePressure((1 - (Math.abs(difference * 1000) * 0.0001)) * post);
          }
        }
        else if (oxygenator.getOxyType().equals(OxygenatorComponent.OxyType.SILICONE)) {
          if (difference > 0) {
//            tube.setPreMembranePressure((((difference * 1000) * 0.00055) + 1) * pre);
//            tube.setPostMembranePressure((((difference * 1000) * 0.0001) + 1) * post);            
          }
          else {
//            tube.setPreMembranePressure((1 - (Math.abs(difference * 1000) * 0.00055)) * pre);
//            tube.setPostMembranePressure((1 - (Math.abs(difference * 1000) * 0.0001)) * post);
          }
        }
        
        // change in pump flow changes tube venous pressure
        double curVenousPressure = tube.getVenousPressure();
        if (difference > 0) {
          tube.setVenousPressure(curVenousPressure 
              - Math.rint(difference * 3000 * (1 / (patient.getWeight() * 10))));
        }
        else {
          tube.setVenousPressure(curVenousPressure 
              + Math.rint(Math.abs(difference) * 3000 * (1 / (patient.getWeight() * 10))));          
        }
      }
      
      // update equipment (pressure monitor)
      pressureMonitor.setPreMembranePressure(tube.getPreMembranePressure());
      pressureMonitor.setPostMembranePressure(tube.getPostMembranePressure());
      pressureMonitor.setVenousPressure(tube.getVenousPressure());
      
      // update equipment (ventilator)
      // TODO: What happens when on emergency ventilator and on ECMO?
      if (ventilator.isEmergencyFuction()) {
        if (ventilator.getName().equals("High Frequency Ventilator")) {
          patient.setRespiratoryRate(0);
          ventilator.setFiO2(1.0);
        }
        if (ventilator.getName().equals("Conventional Ventilator")) {
          patient.setRespiratoryRate(50);
          ((ConventionalSubtype) ventilator.getSubtype()).setRate(50);
          ((ConventionalSubtype) ventilator.getSubtype()).setPip(40);
          ventilator.setFiO2(1.0);
        }
      }
      else {
        if (ventilator.getName().equals("Conventional Ventilator")) {
          patient.setRespiratoryRate(12);
          ((ConventionalSubtype) ventilator.getSubtype()).setRate(12);
          ((ConventionalSubtype) ventilator.getSubtype()).setPip(25);
          ventilator.setFiO2(0.3);
        }
      }

      // update equipment (alarm)
      if (pressureMonitor.isAlarm()
          || physiologicMonitor.isAlarm()
          || bubbleDetector.isAlarm()
          || pump.isAlarm() 
          || oxygenator.isAlarm()
          || heater.isAlarm()) {
        
        // the alarm indicator becomes true
        alarmIndicator.setAlarm(true);
        
        // pump action necessary?
        if (bubbleDetector.isAlarm() || pressureMonitor.isAlarm()) {
          pump.setOn(false);
        }
      }
      else {
        alarmIndicator.setAlarm(false);
      }
      
      // update patient if on pump
      if (onPump) {
        double patientTemperature = patient.getTemperature();
        if (patientTemperature < heater.getTemperature()) {
          patient.setTemperature(patientTemperature + 0.001);
        }
        else if (patientTemperature > heater.getTemperature()) {
          patient.setTemperature(patientTemperature - 0.001);
        }

        // update patient pH, pCO2, HCO3, base excess
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

        // simplified version: interaction sweep to patient PCO2
        // problems: does not take into account ventilation through the ventilator 
        //   with good lungs, or CO2 removal with slower flow. Also issues with,
        //   venous recirculation in VV mode where the pump CDI would not be 
        //   reflective of the patient's values.
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

        // temperature effect
        if ((patient.getTemperature() < 36.0) || (patient.getTemperature() > 37.0)) {
          if (patient.getTemperature() != history.getPatientTemperature()) {
            double bpadjust = 0.0001;
            double hradjust = 0.0001;
            double actadjust = 0.1;
            if (patient.getTemperature() > history.getPatientTemperature()) {
              patient.setHeartRate(patient.getHeartRate() + (hradjust * patient.getHeartRate()));
              patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() * (1 + bpadjust));
              if (Math.rint(patient.getTemperature()) != Math.rint(history.getPatientTemperature())) {
                patient.setAct(patient.getAct() * (1 - actadjust));
              }
            }
            else {
              patient.setHeartRate(patient.getHeartRate() - (hradjust * patient.getHeartRate()));
              patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() * (1 - bpadjust));
              if (Math.rint(patient.getTemperature()) != Math.rint(history.getPatientTemperature())) {
                patient.setAct(patient.getAct() * (1 + actadjust));
                patient.setFibrinogen(patient.getFibrinogen() * (1 - (2 * actadjust)));
                patient.setPt(patient.getPt() * (1 + actadjust));
                patient.setPtt(patient.getPtt() * (1 + actadjust));
                patient.setPlatelets(patient.getPlatelets() * (1 - (2 * actadjust)));
              }
            }
          }
        }

        // Link of flow to heart rate and blood pressure
        if ((difference != 0) && (history.getFlow() != 0)) {
          // SBP increase 10% if flow increase by 20mL/kg/min in VA AND bad heart
          double hradjust = 0.5;
          if ((mode == Mode.VA) && (heartFunction == HeartFunction.BAD)) {
            double bpadjust = 10 / (20 * patient.getWeight()); // % change SBP for 1mL/min flow change
            if (difference > 0) {
              patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() * (1 + bpadjust));
              patient.setHeartRate(patient.getHeartRate() - (hradjust * patient.getHeartRate() * difference));
            }
            else {
              patient.setHeartRate(patient.getHeartRate() + (hradjust * patient.getHeartRate() * difference));
              double newbp = patient.getSystolicBloodPressure() * (1 - bpadjust);
              if (newbp < 50) {
                patient.setSystolicBloodPressure(50);
              }
              else {
                patient.setSystolicBloodPressure(newbp);
              }
            }
          }
          else {
            double bpadjust = 0.007; // Constant to adjust bp (note: 5% change too big!!!)
            // pump flow to patient heart rate & blood pressure
            if (difference > 0) {
              // flow higher then BP increases & heart rate decreases
              patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() * (1 + bpadjust));
              patient.setHeartRate(patient.getHeartRate() - (hradjust * patient.getHeartRate() * difference));
            }
            else {
              // flow lower then blood pressure drops & heart rate increases
              patient.setHeartRate(patient.getHeartRate() + (hradjust * patient.getHeartRate() * difference));
              double newbp = patient.getSystolicBloodPressure() * (1 - bpadjust);
              if (newbp < 50) {
                patient.setSystolicBloodPressure(50);
              }
              else {
                patient.setSystolicBloodPressure(newbp);
              }
            }
          }
        }
        
        // Link change in pre-membrane pressure to patient CVP
        double venpresdiff = tube.getVenousPressure() - history.getVenousPressure();
        if ((venpresdiff != 0) && (history.getVenousPressure() != 0)) {
          // if pumptype is centrifugal
          double pumpadj = 75;
          // else
          if (pump.getPumpType() == PumpType.ROLLER) {
            pumpadj = 50;
          }
          double cvpadjust = Math.abs(venpresdiff / pumpadj);
          if (venpresdiff > 0) {
            double newcvp = patient.getCentralVenousPressure() * (1 + cvpadjust);
            if (newcvp <= 50) {
              patient.setCentralVenousPressure(newcvp);
            }
          }
          else {
            double newcvp = patient.getCentralVenousPressure() * (1 - cvpadjust);
            if (newcvp >= 0) {
              patient.setCentralVenousPressure(newcvp);
            }
          }
        }

        // pump flow to patient PaO2
        try {
          double patientSaturation = Mediator.flowToSPO2(mode, ccPerKg, patient);
          if (tube.isBridgeOpen()) {
            patientSaturation -= patientSaturation * 0.3;
          }
          if (ventilator.isEmergencyFuction()) {
            patientSaturation *= 1.25;
            if (patientSaturation > 1.0) {
              patientSaturation = 1.0;
            }
          }
          // see (b) in "Oxyhemoglobin Dissociation Curve.xls"
          double po2 = Mediator.calcPaO2(patientSaturation, tube.getMode());
          patient.setO2Saturation(patientSaturation);
          patient.setPO2(po2); // TODO: set from graph FiO2 with varying shunts?
        }
        catch (Exception e) {
          Error.out(e.getMessage());
        }
      }
      else { 
        // patient is not on the pump
        patientDies(patient, ventilator, increment);
        
      }
            
      // TODO Patient bicarb and base excess calc? from Mark's table
      
      // Update patient sedated status
      //   This is independent of on or off pump
      //   *adjust are constants to change rate of rise
      //   limits are if HR >180 or SBP >120 then no change
      if (!patient.isSedated()) {
        double bpadjust = 0.0001;
        double hradjust = 0.0001;
        if (patient.getHeartRate() < 180) {
          patient.setHeartRate(patient.getHeartRate() + (hradjust * patient.getHeartRate()));
        }
        if (patient.getSystolicBloodPressure() < 120) {
          patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() * (1 + bpadjust));
        }
      }
      else {
        // Arbitrary run out of sedation in 3000 cycles (1min)
        if (history.getPatientSedatedTime() >= 3000) {
          // CA: let's take that out for now - the patient will stay sedated and does not wake up
          //     -> we can use it later
          //patient.setSedated(false);
        }
      }
      
      // update the patients life
      if (oxygenator.isBroken()) {
        double life = patient.getLife();
        life -= increment / 5000.0;   // 5 seconds to die...
        if (life < 0.0) {
          life = 0.0;
        }
        patient.setLife(life);
      }
      
      // and return if goal is reached
      return goal.isReached(game);
    }
    else {
      return true;
    }
  }
  
  /**
   * Undo the tube clamp effect.
   * 
   * @param tube  The tube.
   * @param pump  The pump.
   * @param pressureMonitor  The pressure monitor.
   * @param oxigenator  The oxigenator.
   * @param physiologicMonitor  The physiologic monitor.
   * @param patient  The patient.
   * @param history  The history.
   */
  private static void tubeUndo(TubeComponent tube, PumpComponent pump, PressureMonitorComponent pressureMonitor, 
      OxygenatorComponent oxigenator, PhysiologicMonitorComponent physiologicMonitor, Patient patient, 
      History history) {
    // Arterial: Open, Venous: Open, Bridge: Open
    if (history.isArterialBOpen() && history.isVenousBOpen() && history.isBridgeOpen()) {
      // heart rate increase 10%, 
      patient.setHeartRate(patient.getHeartRate() / 1.10);
      // systolic BP decrease by 15%,
      patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() / 0.85);
      // central venous pressure decrease by 10%,
      patient.setCentralVenousPressure(patient.getCentralVenousPressure() / 0.90);
      // CDI pH equation, 
      // CDI PCO2 equation, 
      // CDI PO2 equation, 
      // CDI bicarb change, 
      // patient PaO2 decrease by 15%, 
      patient.setPO2(patient.getPO2() / 0.85);
      // no chanages in pre- or post-membrane pressures, 
      // and flow ill not change in roller pump, 
      // else if centrifugal will increase flow by 10%.
      if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
        pump.setFlow(pump.getFlow() / 1.10);
      }
    }
    // Arterial: Closed, Venous: Open, Bridge: Open
    else if (!history.isArterialBOpen() && history.isVenousBOpen() && history.isBridgeOpen()) {
      // premembrane and postmembrane pressures drop by 10%. 
//      tube.setPreMembranePressure(tube.getPreMembranePressure() / 0.90);
//      tube.setPostMembranePressure(tube.getPostMembranePressure() / 0.90);
      // Venous pressure increase by 1.
      tube.setVenousPressure(tube.getVenousPressure() - 1.0);
    }
    // Arterial: Open, Venous: Closed, Bridge: Open
    else if (history.isArterialBOpen() && !history.isVenousBOpen() && history.isBridgeOpen()) {
      // premembrane pressure decreases by 10%. 
//      tube.setPreMembranePressure(tube.getPreMembranePressure() / 0.90);
      // Venous pressure increases by 4.
      tube.setVenousPressure(tube.getVenousPressure() - 4.0);
    }
    // Arterial: Closed, Venous: Closed, Bridge: Open
    else if (!history.isArterialBOpen() && !history.isVenousBOpen() && history.isBridgeOpen()) {
      // Standard operation termed recirculation: no change
      // TODO: if patient is sick and not on emergency vent would steady decline to death
      onPump = false;
    }
    // Arterial: Open, Venous: Open, Bridge: Closed
    else if (history.isArterialBOpen() && history.isVenousBOpen() && !history.isBridgeOpen()) {
      // Standard operation: no change
      onPump = true;
    }
    // Arterial: Closed, Venous: Open, Bridge: Closed
    else if (!history.isArterialBOpen() && history.isVenousBOpen() && !history.isBridgeOpen()) {
      // If limits set appropriately,  
      if (!pressureMonitor.isAlarm()) {
        // Undo premembrane pressure will equal postmembrane pressure up to 750.    
        // tube.setPreMembranePressure(tube.getPostMembranePressure());
        // If roller then pressures will stay at 750, else it's centrifugal then it would stop and reset to 35. 
        if (pump.getPumpType() == PumpType.ROLLER) {
          // undo stay at 750???????
          if (tube.getPreMembranePressure() > 750) {
            // tube.setPreMembranePressure(750.0);
            // tube.setPostMembranePressure(750.0);
          }
        }
        if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
          // reset to 35 or decrease to 35???
          // tube.setPreMembranePressure(35.0);
        }
      }
      else {
        // If limits not set appropriately then rupture for roller else centrifugal stop then reset to 35. 
        if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
          // reset to 35 or decrease to 35???
          // tube.setPreMembranePressure(35.0);
        }
        // The venous pressure increases by 2. Pump flow to 0. ??????
        tube.setVenousPressure(pressureMonitor.getVenousPressure() - 2.0);
        // ???? Undo flow ????
        // pump.setFlow(0.0); 
        // If limits not set appropriately then for roller pump tubing will rupture, else flow is 0.
        if (pump.getPumpType() == PumpType.ROLLER) {
          // undo roller pump broken????
          // oxigenator.setBroken(true);
        }
      }
    }
    // Arterial: Open, Venous: Closed, Bridge: Closed
    else if (history.isArterialBOpen() && !history.isVenousBOpen() && !history.isBridgeOpen()) {
      // If roller pump then premembrane = mean BP, else if centrifugal then premembrane decreases by 35. 
      if (pump.getPumpType() == PumpType.ROLLER) {
        // undo the mean BP
        // tube.setPreMembranePressure(physiologicMonitor.getMeanBloodPressure());
      }
      if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
//        tube.setPreMembranePressure(tube.getPreMembranePressure() + 35.0);
      } 
      
      // If both roller and silicon (SciMed) add another decrease of 10%. Venous pressure increases by 2. 
      if (pump.getPumpType() == PumpType.ROLLER && oxigenator.getOxyType() == OxyType.SILICONE) {
//        tube.setPreMembranePressure(tube.getPreMembranePressure() / 0.90);
        tube.setVenousPressure(tube.getVenousPressure() - 2.0);
      }
      
      // If limits are appropriate then pump flow = 0, else limits not set properly then 
      if (!pressureMonitor.isAlarm()) {
        // ???? undo the flow
        // pump.setFlow(0.0);
      }
      else {
        // if cenrifugal pump then pump flow to 0. 
        if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
          // ??? undo the flow
          // pump.setFlow(0.0);
        }
        // Else if roller pump, air in venous line.
        if (pump.getPumpType() == PumpType.ROLLER) {
          // ??? undo the bubble
          // tube.setVenousBubbles(true);
        }   
      }
    }
    // Arterial: Closed, Venous: Closed, Bridge: Closed
    else if (!history.isArterialBOpen() && !history.isVenousBOpen() && !history.isBridgeOpen()) {
      // Massively bloody explosion with lots of alarms and noise. 
      // If roller pump then "God of War" blood shower. 
      if (pump.getPumpType() == PumpType.ROLLER) {
        // ????? undo oxigenator still broken
        // oxigenator.setBroken(true);
      }
      // If centrifugal then alarm, patient decompensates, pump stops.
      if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
        // ???? undo alarm go off
        // pump.setAlarm(true);
        // pump.setOn(false);
      }
    }  
  }
  
  /**
   * Do the tube clamp effect.
   * 
   * @param tube  The tube.
   * @param pump  The pump.
   * @param pressureMonitor  The pressure monitor.
   * @param oxigenator  The oxigenator.
   * @param physiologicMonitor  The physiologic monitor.
   * @param patient  The patient.
   */
  private static void tubeDo(TubeComponent tube, PumpComponent pump, PressureMonitorComponent pressureMonitor, 
      OxygenatorComponent oxigenator, PhysiologicMonitorComponent physiologicMonitor, Patient patient) {
    // Arterial: Open, Venous: Open, Bridge: Open
    if (tube.isArterialBOpen() && tube.isVenousBOpen() && tube.isBridgeOpen()) {
      // heart rate increase 10%, 
      patient.setHeartRate(patient.getHeartRate() * 1.10);
      // systolic BP decrease by 15%,
      patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() * 0.85);
      // central venous pressure decrease by 10%,
      patient.setCentralVenousPressure(patient.getCentralVenousPressure() * 0.90);
      // CDI pH equation, 
      // CDI PCO2 equation, 
      // CDI PO2 equation, 
      // CDI bicarb change, 
      // patient PaO2 decrease by 15%, 
      patient.setPO2(patient.getPO2() * 0.85);
      // no chanages in pre- or post-membrane pressures, 
      // and flow ill not change in roller pump, 
      // else if centrifugal will increase flow by 10%.
      if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
        pump.setFlow(pump.getFlow() * 1.10);
      }
    }
    // Arterial: Closed, Venous: Open, Bridge: Open
    else if (!tube.isArterialBOpen() && tube.isVenousBOpen() && tube.isBridgeOpen()) {
      // premembrane and postmembrane pressures drop by 10%. 
//      tube.setPreMembranePressure(tube.getPreMembranePressure() * 0.90);
//      tube.setPostMembranePressure(tube.getPostMembranePressure() * 0.90);
      // Venous pressure increase by 1.
      tube.setVenousPressure(tube.getVenousPressure() + 1.0);
    }
    // Arterial: Open, Venous: Closed, Bridge: Open
    else if (tube.isArterialBOpen() && !tube.isVenousBOpen() && tube.isBridgeOpen()) {
      // premembrane pressure decreases by 10%. 
//      tube.setPreMembranePressure(tube.getPreMembranePressure() * 0.90);
      // Venous pressure increases by 4.
      tube.setVenousPressure(tube.getVenousPressure() + 4.0);
    }
    // Arterial: Closed, Venous: Closed, Bridge: Open
    else if (!tube.isArterialBOpen() && !tube.isVenousBOpen() && tube.isBridgeOpen()) {
      // Standard operation termed recirculation: no change
      // TODO: if patient is sick, vital signs should decrease
      onPump = false;
    }
    // Arterial: Open, Venous: Open, Bridge: Closed
    else if (tube.isArterialBOpen() && tube.isVenousBOpen() && !tube.isBridgeOpen()) {
      // Standard operation: no change
      onPump = true;
    }
    // Arterial: Closed, Venous: Open, Bridge: Closed
    else if (!tube.isArterialBOpen() && tube.isVenousBOpen() && !tube.isBridgeOpen()) {
      if (pump.isOn() && (pump.getFlow() > 0.0)) {
        if (pump.getPumpType() == PumpType.ROLLER) {
          oxigenator.setBroken(true);
        }
        
        // If limits set appropriately,  
        if (!pressureMonitor.isAlarm()) {
          // premembrane pressure will equal postmembrane pressure up to 750.
//          tube.setPreMembranePressure(tube.getPostMembranePressure());
          // If roller then pressures will stay at 750, else it's centrifugal then it would stop and reset to 35. 
          if (pump.getPumpType() == PumpType.ROLLER) {
            // stay at 750???????
            if (tube.getPreMembranePressure() > 750) {
//              tube.setPreMembranePressure(750.0);
//              tube.setPostMembranePressure(750.0);
            }
          }
          if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
            // reset to 35 or decrease to 35???
//            tube.setPreMembranePressure(35.0);
          }
        }
        else {
          // If limits not set appropriately then rupture for roller else centrifugal stop then reset to 35. 
          if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
            // reset to 35 or decrease to 35???
//            tube.setPreMembranePressure(35.0);
          }
          // The venous pressure increases by 2. Pump flow to 0. ??????
          tube.setVenousPressure(pressureMonitor.getVenousPressure() + 2.0);
          pump.setFlow(0.0); 
          // If limits not set appropriately then for roller pump tubing will rupture, else flow is 0.
          if ((pump.getPumpType() == PumpType.ROLLER) && (pump.isOn() && (pump.getFlow() > 0))) {
            // roller pump broken????
            oxigenator.setBroken(true);
          }
        }
      }
    }
    // Arterial: Open, Venous: Closed, Bridge: Closed
    else if (tube.isArterialBOpen() && !tube.isVenousBOpen() && !tube.isBridgeOpen()) {      
      // If roller pump then premembrane = mean BP, else if centrifugal then premembrane decreases by 35. 
      if (pump.getPumpType() == PumpType.ROLLER) {
//        tube.setPreMembranePressure(physiologicMonitor.getMeanBloodPressure());
      }
      if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
//        tube.setPreMembranePressure(tube.getPreMembranePressure() - 35.0);
      } 
      
      // If both roller and silicon (SciMed) add another decrease of 10%. Venous pressure increases by 2. 
      if (pump.getPumpType() == PumpType.ROLLER && oxigenator.getOxyType() == OxyType.SILICONE) {
//        tube.setPreMembranePressure(tube.getPreMembranePressure() * 0.90);
        tube.setVenousPressure(tube.getVenousPressure() + 2.0);
      }
      
      // If limits are appropriate then pump flow = 0, else limits not set properly then 
      if (!pressureMonitor.isAlarm()) {
        pump.setFlow(0.0);
      }
      else {
        // if cenrifugal pump then pump flow to 0. 
        if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
          pump.setFlow(0.0);
        }
        // Else if roller pump, air in venous line.
        if ((pump.getPumpType() == PumpType.ROLLER) && (pump.isOn() && (pump.getFlow() > 0.0))) {
          tube.setVenousBubbles(true);
        }   
      }
    }
    // Arterial: Closed, Venous: Closed, Bridge: Closed
    else if (!tube.isArterialBOpen() && !tube.isVenousBOpen() && !tube.isBridgeOpen()) {
      // Massively bloody explosion with lots of alarms and noise. 
      // If roller pump then "God of War" blood shower. 
      if ((pump.getPumpType() == PumpType.ROLLER) && (pump.isOn() && (pump.getFlow() > 0))) {
        oxigenator.setBroken(true);
      }
      // If centrifugal then alarm, patient decompensates, pump stops.
      if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
        pump.setAlarm(true);
        pump.setOn(false);
      }
    }
  }
  
  /**
   * Patient dying process.
   * 
   * @param patient  The patient.
   * @param ventilator  The ventilator.
   * @param increment  The game time increment.
   */
  private static void patientDies(Patient patient, VentilatorComponent ventilator,
      long increment) {
    if (ventilator.isEmergencyFuction()) {
      if ((patient.getHeartFunction() == Patient.HeartFunction.GOOD) 
          && (patient.getLungFunction() == Patient.LungFunction.GOOD)) {
        // stays alive
      }
      else {
        // dies slower
        patient.setPH(patient.getPH() - 0.0001);
        patient.setPCO2(patient.getPCO2() + 0.01);
        patient.setHeartRate(patient.getHeartRate() - 0.1);
        patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() - 0.01);
        patient.setO2Saturation(patient.getO2Saturation() - 0.0001);
        patient.setPO2(patient.getPO2() - 0.01);
        double life = patient.getLife();
        life -= increment / 60000.0;   // 1 minute to die...
        if (life < 0.0) {
          life = 0.0;
        }
        patient.setLife(life);
      }          
    }
    else {
      if ((patient.getHeartFunction() == Patient.HeartFunction.GOOD) 
          && (patient.getLungFunction() == Patient.LungFunction.GOOD)) {
        // dies slower
        patient.setPH(patient.getPH() - 0.0001);
        patient.setPCO2(patient.getPCO2() + 0.01);
        patient.setHeartRate(patient.getHeartRate() - 0.01);
        patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() - 0.01);
        patient.setO2Saturation(patient.getO2Saturation() - 0.0001);
        patient.setPO2(patient.getPO2() - 0.01);
        double life = patient.getLife();
        life -= increment / 60000.0;   // 1 minute to die...
        if (life < 0.0) {
          life = 0.0;
        }
        patient.setLife(life);
      }
      else {
        // dies faster
        patient.setPH(patient.getPH() - 0.001);
        patient.setPCO2(patient.getPCO2() + 0.1);
        patient.setHeartRate(patient.getHeartRate() - 0.1);
        patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() - 0.1);
        patient.setO2Saturation(patient.getO2Saturation() - 0.001);
        patient.setPO2(patient.getPO2() - 0.1);
        double life = patient.getLife();
        life -= increment / 30000.0;   // 30 seconds to die...
        if (life < 0.0) {
          life = 0.0;
        }
        patient.setLife(life);
      }
    }
  }

}
