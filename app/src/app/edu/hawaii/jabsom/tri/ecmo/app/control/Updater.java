package edu.hawaii.jabsom.tri.ecmo.app.control;

import java.io.PrintStream;

import king.lib.out.Error;
import king.lib.sandbox.model.ClassSandbox;
import king.lib.script.control.ScriptException;
import king.lib.script.control.ScriptRunner;
import king.lib.script.model.Compile;
import king.lib.script.model.Context;
import king.lib.util.StringSet;
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
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.BaselineGoal;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.Goal;

/**
 * The updater. 
 *
 * @author   king
 * @since    August 13, 2008
 */
public final class Updater {

  /** Our script runner. */
  private static ScriptRunner scriptRunner;
  
  /** 
   * Our static initializer.
   */
  static {
    // setup script runner: uses always the same permissions.
    Context context = new Context();
    context.setMaxDuration(50);
    StringSet accessibleClasses = new StringSet();
    accessibleClasses.add(System.class.getName());
    accessibleClasses.add(PrintStream.class.getName());
    accessibleClasses.add(String.class.getName());
    accessibleClasses.add(Math.class.getName());
    accessibleClasses.add(Integer.class.getName());
    context.setSandbox(new ClassSandbox(accessibleClasses));
    scriptRunner = new ScriptRunner(context);
  }
  
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
   * @param compile  A compiled script or null for none.
   * @param increment  The time increment in milliseconds.
   * @return  True, if goal is reached.
   */
  public static boolean execute(Game game, History history, Compile compile, long increment) {
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
      
      // if we are running a baseline goal, check if the trigger has to be executed
      if (goal instanceof BaselineGoal) {
        long timeInit = ((BaselineGoal)goal).getTimeInit();
        if ((timeInit <= elapsedTime) && (timeInit > (elapsedTime - increment))) {
          String trigger = ((BaselineGoal)goal).getTrigger();
          if (trigger.equals("arterial-cannula-kink")) {
            tube.setBrokenCannula(true, TubeComponent.Status.KINK, TubeComponent.problemLocation.arterial);      
          }
          else if (trigger.equals("patient-bleeding")) {
            patient.setBleeding(true);
          }
        }
      }
      
      // execute script as needed
       if (compile != null) {
        try {
          scriptRunner.execute(compile);
        }
        catch (ScriptException e) {
          Error.out(e);
        }
      }
      
      // load some local variables for Updater
      Mode mode = tube.getMode();
      HeartFunction heartFunction = patient.getHeartFunction();
      LungFunction lungFunction = patient.getLungFunction();
      double ccPerKg = pump.getFlow() * 1000 / patient.getWeight();
      if (!pump.isOn()) {
        ccPerKg = 0;
      }
      
      /* update equipment (physiologic monitor) */
      physiologicMonitor.setTemperature(Math.rint(patient.getTemperature()));
      physiologicMonitor.setHeartRate(patient.getHeartRate());
      physiologicMonitor.setRespiratoryRate(patient.getRespiratoryRate());
      physiologicMonitor.setO2Saturation(patient.getO2Saturation());
      physiologicMonitor.setDiastolicBloodPressure(patient.getDiastolicBloodPressure());
      physiologicMonitor.setSystolicBloodPressure(patient.getSystolicBloodPressure());
      physiologicMonitor.setCentralVenousPressure(patient.getCentralVenousPressure());
      
      /* update equipment (tubing) */
      double paO2 = ((99.663 * oxygenator.getFiO2()) - 6.17) * 7.5;  // see PaO2-FiO2.spv (SPSS)
      if (oxygenator.getClotting() > 1) { // oxygenator not working as efficiently
        paO2 = paO2 * 1 / oxygenator.getClotting();
      }
      if (paO2 == 0) {
        paO2 = 0.001;
      }  
      double tubeSaO2 = 0;   // see (b) in "Oxyhemoglobin Dissociation Curve.xls"
      tubeSaO2 = Mediator.calcOxygenSaturation(paO2);
      tube.setSaO2(tubeSaO2);
      
      // Pre-membrane tubing variables (come from patient)
      if (!(isConnected(tube) && pump.isOn())) {
        tube.setSvO2(tube.getSvO2() > 0 ? patient.getO2Saturation() - 0.20 : 0);
      }
      tube.setPrePH(patient.getPH());
      tube.setPrePCO2(patient.getPCO2());

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
        if (oxygenator.getClotting() < 1) {
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
        else { // oxygenator not as efficient
          tube.setPostPCO2(60);
//          tube.setPostPCO2(tube.getPrePCO2() * (1 + oxygenator.getClotting() / 100));
        }
      }
      
      double currentTubePH = tube.getPostPH();
      // TODO: reconfirm interaction as PCO2 increases PH falls
      tube.setPostPH(currentTubePH - ((tube.getPostPCO2() - currentTubePCO2) * 0.008));
      
      if ((pump.getPumpType() == PumpType.ROLLER) && (pump.isOn()) && (!tube.isVenousAOpen())) {
        tube.setVenousPressure(-100);
      }
      
      // Clamping behavior:
      /* End states: !AB!V, A!BV where ! indicates clamped
       *   !AB!V is clamped off circuit, see offCircuit()
       *   A!BV is on circuit, see onCircuit()
       * Timer for detection when end states are not in effect. Thus, after x seconds
       *   patient and circuit effects begin to happen.
       * Transition states are all other combinations of clamping, where if we are
       *   not in an end state by timer expiration bad things occur. 
       */
      if (!(onCircuit(tube) || offCircuit(tube))) {
        long x = 3;
        if (history.getStartClampTime() < 0) {
          history.setStartClampTime(elapsedTime);
        }
        if ((elapsedTime - history.getStartClampTime()) > (x*1000)) {
          doClampState(tube, pump, pressureMonitor, oxygenator, physiologicMonitor, patient);          
        }
      }
      else {
        if (history.getStartClampTime() > 0) { // was in a transition state
          if (oxygenator.getOxyType() == OxygenatorComponent.OxyType.QUADROX_D) { 
            // PMP
            tube.setPreMembranePressure(125);
            tube.setPostMembranePressure(120);
          }
          else { 
            // Silicon
            tube.setPreMembranePressure(240);
            tube.setPostMembranePressure(220);
          }
        }
        history.setStartClampTime(-1);
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
              // if centrifugal pump then pump flow to 0. 
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
      
      /* update equipment (oxygenator) */
      // currently handled in other tube component
      
      /* update equipment (bubble detector) */
      bubbleDetector.setAlarm(tube.isArterialBubbles());

      /* update equipment (CDI monitor) note CDI is in line post-oxygenator */
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

      /* update equipment (pump) */
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
            tube.setPreMembranePressure((((difference * 1000) * 0.0001) + 1) * pre);
            tube.setPostMembranePressure((((difference * 1000) * 0.0001) + 1) * post);
          }
          else {
            tube.setPreMembranePressure((1 - (Math.abs(difference * 1000) * 0.0001)) * pre);
            tube.setPostMembranePressure((1 - (Math.abs(difference * 1000) * 0.0001)) * post);
          }
        }
        else if (oxygenator.getOxyType().equals(OxygenatorComponent.OxyType.SILICONE)) {
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
              - Math.rint(difference * 3000 * (1 / (patient.getWeight() * 10))));
        }
        else {
          tube.setVenousPressure(curVenousPressure 
              + Math.rint(Math.abs(difference) * 3000 * (1 / (patient.getWeight() * 10))));          
        }
      }
      
      /* update equipment (pressure monitor) */
      pressureMonitor.setPreMembranePressure(tube.getPreMembranePressure());
      pressureMonitor.setPostMembranePressure(tube.getPostMembranePressure());
      pressureMonitor.setVenousPressure(tube.getVenousPressure());
      
      /* update equipment (ventilator) */
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

      /* update equipment (alarm) */
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
      
      /* update patient */
      // patient is connected to circuit: A and V are open
      if (isConnected(tube) && pump.isOn()) {
        double patientTemperature = patient.getTemperature();
        if (patientTemperature < heater.getTemperature()) {
          patient.setTemperature(patientTemperature + 0.001);
        }
        else if (patientTemperature > heater.getTemperature()) {
          patient.setTemperature(patientTemperature - 0.001);
        }

        if (onCircuit(tube) && pump.isOn()) {
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
            else if (mode == Mode.VV) {
              // pump flow to patient heart rate & blood pressure however in VV
              // NO blood pressure change
              if (difference > 0) {
                // flow higher then heart rate decreases
                patient.setHeartRate(patient.getHeartRate() - (hradjust * patient.getHeartRate() * difference));
              }
              else {
                // flow lower then heart rate increases
                patient.setHeartRate(patient.getHeartRate() + (hradjust * patient.getHeartRate() * difference));
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

          // pump flow to patient and tube SvO2
          try{ 
            double tubeSvO2 = Mediator.flowToSvO2(mode, ccPerKg, patient);
            tube.setSvO2(tubeSvO2); 
          }
          catch (Exception e) {
            // something wrong
            tube.setSvO2(0.0);
            Error.out(e.getMessage());
          }

          // pump flow to patient PaO2
          try {
            double patientSaturation = Mediator.flowToSPO2(mode, ccPerKg, patient);
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
        } // onCircuit
      } // isConnected
      else { 
        // patient is not on the pump
        patientDies(patient, ventilator, increment);
        
      }
            
      // TODO Patient bicarb and base excess calc? from Mark's table
      
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
      
      // Update patient bleeding status or hypovolemic
      if (patient.isBleeding() 
          || (patient.getBloodVolume() < (0.8 * patient.getMaxBloodVolume()))) {
        if (patient.getBloodVolume() < (0.5 * patient.getMaxBloodVolume())) {
          // patient bleeding but not compensating
          patient.setHeartRate(200);
          patient.setSystolicBloodPressure(40);
          patient.setCentralVenousPressure(0);
        }
        else {
          // patient bleeding but compensating
          if (patient.getHeartRate() < 200) {
            patient.setHeartRate(patient.getHeartRate() + 0.05);
          }
          if (patient.getSystolicBloodPressure() > 40) {
            patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() - 0.01);
          }
          if (patient.getCentralVenousPressure() > 2) {
            patient.setCentralVenousPressure(patient.getCentralVenousPressure() 
                - 0.01);
          }
        }
        if (patient.isBleeding()) {
          patient.setBloodVolume(patient.getBloodVolume() - 0.01);
          patient.setHgb(patient.getHgb() > 7 ? patient.getHgb() - 0.001 : 7);
        }
      }
      
      // update the patients life
      if (oxygenator.isBroken()) {
        tube.setPreMembranePressure(tube.getPreMembranePressure() + 0.1);
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
   * Do the clamp effect for transition states if exceeds time limit.
   * 
   * @param tube  The tube.
   * @param pump  The pump.
   * @param pressureMonitor  The pressure monitor.
   * @param oxygenator  The oxygenator.
   * @param physiologicMonitor  The physiologic monitor.
   * @param patient  The patient.
   */
  private static void doClampState(TubeComponent tube, PumpComponent pump, PressureMonitorComponent pressureMonitor, 
      OxygenatorComponent oxygenator, PhysiologicMonitorComponent physiologicMonitor, Patient patient) {
    // Arterial: Open, Venous: Open, Bridge: Open
    if (tube.isArterialBOpen() && tube.isVenousBOpen() && tube.isBridgeOpen()) {
      // heart rate increases,
      patient.setHeartRate(patient.getHeartRate() < 180 ? patient.getHeartRate() + 0.01 : 180);
      // systolic BP decreases,
      patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() > 50 
          ? patient.getSystolicBloodPressure() - 0.001 : 50);
      // central venous pressure decreases,
      patient.setCentralVenousPressure(patient.getCentralVenousPressure() > 1 
          ? patient.getCentralVenousPressure() - 0.001 : 1);
      // CDI pH equation, 
      // CDI PCO2 equation, 
      // CDI PO2 equation, 
      // CDI bicarb change,
      // patient PaO2 decreases,
      patient.setPO2(patient.getPO2() > 30 ? patient.getPO2() - 0.001 : 30);
      // no changes in pre- or post-membrane pressures, 
      // and flow will not change in roller pump, 
      // else if centrifugal will increase flow
      if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
        pump.setFlow(0.6 * 1.10); // assume neonate with 600mL/min flow
      }
    }
    // Arterial: Closed, Venous: Open, Bridge: Open
    // or
    // Arterial: Open, Venous: Closed, Bridge: Open
    //   Blood recirculates through bridge
    else if ((!tube.isArterialBOpen() && tube.isVenousBOpen() && tube.isBridgeOpen()) 
      || (tube.isArterialBOpen() && !tube.isVenousBOpen() && tube.isBridgeOpen())) {
      // premembrane and postmembrane pressures drop. 
      tube.setPreMembranePressure(tube.getPreMembranePressure() > 90 
          ? tube.getPreMembranePressure() - 0.001 : 90);
      tube.setPostMembranePressure(tube.getPostMembranePressure() > 70 
          ? tube.getPostMembranePressure() - 0.001 : 70);
      // Venous pressure increases.
      tube.setVenousPressure(tube.getVenousPressure() < 20 
          ? tube.getVenousPressure() + 0.001 : 20);
      // Blood gas changes
      patient.setPH(patient.getPH() > 7 ? patient.getPH() - 0.001 : 7);
      patient.setPCO2(patient.getPCO2() < 120 ? patient.getPCO2() + 0.001 : 120);
      patient.setPO2(patient.getPO2() > 30 ? patient.getPO2() - 0.001 : 30);
    }
    // Arterial: Closed, Venous: Closed, Bridge: Open
      // Standard operation termed recirculation: no change; end state
    
    // Arterial: Open, Venous: Open, Bridge: Closed
      // Standard operation: no change; end state

    // Arterial: Closed, Venous: Open, Bridge: Closed
    else if (!tube.isArterialBOpen() && tube.isVenousBOpen() && !tube.isBridgeOpen()) {
      if (pump.isOn() && (pump.getFlow() > 0.0)) {
        tube.setPreMembranePressure(500.0);
        tube.setPostMembranePressure(500.0);              

        if (pump.getPumpType() == PumpType.ROLLER) {
          oxygenator.setBroken(true);
        }
        
        // If limits set appropriately,  
        if (!pressureMonitor.isAlarm()) {
          // premembrane pressure will equal postmembrane pressure up to 750.
          tube.setPreMembranePressure(tube.getPostMembranePressure());
          // If roller then pressures will stay at 750, else it's centrifugal then it would stop and reset to 35. 
          if (pump.getPumpType() == PumpType.ROLLER) {
            // stay at 750???????
            if (tube.getPreMembranePressure() > 750) {
              tube.setPreMembranePressure(750.0);
              tube.setPostMembranePressure(750.0);
            }
          }
          if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
            // reset to 35 or decrease to 35???
            tube.setPreMembranePressure(35.0);
          }
        }
        else {
          // If limits not set appropriately then rupture for roller else centrifugal stop then reset to 35. 
          if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
            // reset to 35 or decrease to 35???
            tube.setPreMembranePressure(35.0);
          }
          // The venous pressure increases. Pump flow to 0. ??????
          tube.setVenousPressure(pressureMonitor.getVenousPressure() + 0.002);
          pump.setFlow(0.0); 
          // If limits not set appropriately then for roller pump tubing will rupture, else flow is 0.
          if ((pump.getPumpType() == PumpType.ROLLER) && (pump.isOn() && (pump.getFlow() > 0))) {
            // roller pump broken????
            oxygenator.setBroken(true);
          }
        }
      }
      // Blood gas changes
      patient.setPH(patient.getPH() > 7 ? patient.getPH() - 0.001 : 7);
      patient.setPCO2(patient.getPCO2() < 120 ? patient.getPCO2() + 0.001 : 120);
      patient.setPO2(patient.getPO2() > 30 ? patient.getPO2() - 0.001 : 30);
    }
    // Arterial: Open, Venous: Closed, Bridge: Closed
    else if (tube.isArterialBOpen() && !tube.isVenousBOpen() && !tube.isBridgeOpen()) {      
      // If roller pump then premembrane = mean BP, else if centrifugal then premembrane decreases by 35. 
      if (pump.getPumpType() == PumpType.ROLLER) {
        tube.setPreMembranePressure(physiologicMonitor.getMeanBloodPressure());
      }
      if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
        tube.setPreMembranePressure(115);
      } 
      
      // If both roller and silicon (SciMed) add another decrease of 10%. Venous pressure increases by 2. 
      if (pump.getPumpType() == PumpType.ROLLER && oxygenator.getOxyType() == OxyType.SILICONE) {
        tube.setPreMembranePressure(115*0.9);
        tube.setVenousPressure(tube.getVenousPressure() + 0.001);
      }
      
      // If limits are appropriate then pump flow = 0, else limits not set properly then 
      if (!pressureMonitor.isAlarm()) {
        pump.setFlow(0.0);
      }
      else {
        // if centrifugal pump then pump flow to 0. 
        if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
          pump.setFlow(0.0);
        }
        // Else if roller pump, air in venous line.
        if ((pump.getPumpType() == PumpType.ROLLER) && (pump.isOn() && (pump.getFlow() > 0.0))) {
          tube.setVenousBubbles(true);
        }   
      }
      // Blood gas changes
      patient.setPH(patient.getPH() > 7 ? patient.getPH() - 0.001 : 7);
      patient.setPCO2(patient.getPCO2() < 120 ? patient.getPCO2() + 0.001 : 120);
      patient.setPO2(patient.getPO2() > 30 ? patient.getPO2() - 0.001 : 30);
    }
    // Arterial: Closed, Venous: Closed, Bridge: Closed
    else if (!tube.isArterialBOpen() && !tube.isVenousBOpen() && !tube.isBridgeOpen()) {
      tube.setPreMembranePressure(750.0);
      tube.setPostMembranePressure(750.0);              
      // Massively bloody explosion with lots of alarms and noise. 
      // If roller pump then "God of War" blood shower. 
      if ((pump.getPumpType() == PumpType.ROLLER) && (pump.isOn() && (pump.getFlow() > 0))) {
        oxygenator.setBroken(true);
      }
      // If centrifugal then alarm, patient decompensates, pump stops.
      if (pump.getPumpType() == PumpType.CENTRIFUGAL) {
        pump.setAlarm(true);
        pump.setOn(false);
      }
      // Blood gas changes
      patient.setPH(patient.getPH() > 7 ? patient.getPH() - 0.001 : 7);
      patient.setPCO2(patient.getPCO2() < 120 ? patient.getPCO2() + 0.001 : 120);
      patient.setPO2(patient.getPO2() > 30 ? patient.getPO2() - 0.001 : 30);
    }
    patient.setO2Saturation(patient.getO2Saturation() > 0.65 ? patient.getO2Saturation() - 0.0001 : 0.65);
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
        patient.setPH(patient.getPH() > 6 ? patient.getPH() - 0.0005 : 6);
        patient.setPCO2(patient.getPCO2() < 120 ? patient.getPCO2() + 0.05 : 120);
        patient.setHeartRate(patient.getHeartRate() > 0 ? patient.getHeartRate() - 0.01 : 0);
        patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() > 0 
            ? patient.getSystolicBloodPressure()- 0.01 : 0);
        patient.setCentralVenousPressure(patient.getCentralVenousPressure() > 0
            ? patient.getCentralVenousPressure() - 0.005 : 0);
        patient.setO2Saturation(patient.getO2Saturation() > 0 ? patient.getO2Saturation() 
            - 0.0001 : 0);
        patient.setPO2(patient.getPO2() > 0 ? patient.getPO2() - 0.05 : 0);
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
        patient.setPH(patient.getPH() > 6 ? patient.getPH() - 0.0005 : 6);
        patient.setPCO2(patient.getPCO2() < 120 ? patient.getPCO2() + 0.05 : 120);
        patient.setHeartRate(patient.getHeartRate() > 0 ? patient.getHeartRate() - 0.01 : 0);
        patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() > 0 
            ? patient.getSystolicBloodPressure() - 0.01 : 0);
        patient.setCentralVenousPressure(patient.getCentralVenousPressure() > 0 
            ? patient.getCentralVenousPressure() - 0.005: 0);
        patient.setO2Saturation(patient.getO2Saturation() > 0 ? patient.getO2Saturation() 
            - 0.0001 : 0);
        patient.setPO2(patient.getPO2() > 0 ? patient.getPO2() - 0.05 : 0);
        double life = patient.getLife();
        life -= increment / 60000.0;   // 1 minute to die...
        if (life < 0.0) {
          life = 0.0;
        }
        patient.setLife(life);
      }
      else {
        // dies faster
        patient.setPH(patient.getPH() > 6 ? patient.getPH() - 0.001 : 6);
        patient.setPCO2(patient.getPCO2() < 120 ? patient.getPCO2() + 0.1 : 120);
        patient.setHeartRate(patient.getHeartRate() > 0 ? patient.getHeartRate() - 0.1 : 0);
        patient.setSystolicBloodPressure(patient.getSystolicBloodPressure() > 0 
            ? patient.getSystolicBloodPressure() - 0.1 : 0);
        patient.setCentralVenousPressure(patient.getCentralVenousPressure() > 0
            ? patient.getCentralVenousPressure() - 0.001 : 0);
        patient.setO2Saturation(patient.getO2Saturation() > 0 ? patient.getO2Saturation() 
            - 0.001 : 0);
        patient.setPO2(patient.getPO2() > 0 ? patient.getPO2() - 0.1 : 0);
        double life = patient.getLife();
        life -= increment / 30000.0;   // 30 seconds to die...
        if (life < 0.0) {
          life = 0.0;
        }
        patient.setLife(life);
      }
    }
  }

  /**
   * Private boolean whether we are on circuit or not.
   * 
   * @param tube  The tube.
   * @return boolean Whether patient is on circuit or not.
   */
  private static boolean onCircuit(TubeComponent tube) {
    // Are we on the circuit or clamped off? for clamping interaction
    return (tube.isArterialBOpen() && tube.isVenousBOpen() && !tube.isBridgeOpen());
  }

  /**
   * Private boolean whether we are off of the circuit or not.
   * 
   * @param tube  The tube.
   * @return boolean Whether patient is off the circuit or not.
   */
  private static boolean offCircuit(TubeComponent tube) {
    // Are we clamped off the circuit? for clamping interaction
    return (!tube.isArterialBOpen() && !tube.isVenousBOpen() && tube.isBridgeOpen());
  }
  
  /**
   * Private boolean whether we are connected to circuit or not. Meaning is patient
   * cannula both venous and arterial are open and able to receive some flow or not.
   * 
   * @param tube  The tube.
   * @return boolean Whether patient is connected to circuit or not.
   */
  private static boolean isConnected(TubeComponent tube) {
    return (tube.isArterialBOpen() && tube.isVenousBOpen() && !tube.isBrokenCannula());
  }

}
