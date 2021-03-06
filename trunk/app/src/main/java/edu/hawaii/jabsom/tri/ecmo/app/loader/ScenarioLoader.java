package edu.hawaii.jabsom.tri.ecmo.app.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.LabRequestAction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;
import edu.hawaii.jabsom.tri.ecmo.app.model.ScenarioFile;
import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline.CannulaFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline.PowerFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline.SuctionEttFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline.TubeFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline.UrineOutputFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.HeaterComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.InterventionComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.VentilatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxygenatorComponent.OxyType;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.Gender;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.HeartFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.LungFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.Species;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent.PumpType;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent.Mode;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.BaselineGoal;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.SimulationGoal;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal.Item;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.BloodGasLab;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ChemistryLab;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.EchoLab;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.HematologyLab;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ImagingLab;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.UltrasoundLab;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.XRayLab;

import king.lib.out.Error;
import king.lib.script.model.Language;
import king.lib.script.model.Script;
import king.lib.access.Hookup;
import king.lib.access.LocalHookup;

/**
 * The scenario loader. 
 *
 * @author   king
 * @since    October 27, 2008
 */
public final class ScenarioLoader {
  
  /**
   * Private constructor to prevent instantiation.
   */
  private ScenarioLoader() {
    // not used
  }
  
  /**
   * Loads a scenario.
   * 
   * @param hookup  The location.
   * @param path  The path.
   * @return  The scenario.
   * @throws IOException  If something goes wrong.
   */
  public static Scenario load(Hookup hookup, String path) throws IOException {
    return load(hookup.getInputStream(path));
  }
  
  /**
   * Loads a scenario.
   * 
   * @param inputStream  The input stream.
   * @return  The scenario.
   * @throws IOException  If something goes wrong.
   */
  public static Scenario load(InputStream inputStream) throws IOException {
    // open stream
    BufferedReader reader = null;
    try {
      // open the reader
      reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

      // read the scenario data
      Map<String, String> parameters = new HashMap<String, String>();
      String line = reader.readLine();
      while (line != null) {
        if ((line.length() == 0) || line.startsWith("#") || (line.startsWith(" "))) {
          // ignore comments
        }
        else {
          // store parameter
          String[] tokens = line.split("=");
          parameters.put(tokens[0].trim(), tokens[1].trim());
        }
        
        // read next line
        line = reader.readLine();
      }

      // parse and load data
      Scenario scenario = ScenarioCreator.create();
      
      // the equipment
      Equipment equipment = scenario.getEquipment();
      
      // the name
      scenario.setName(parameters.get("name"));
      scenario.setDescription(parameters.get("desc"));

      // the parameters
      String tubeType = parameters.get("scenario-ecmo-mode");
      TubeComponent tube = (TubeComponent)equipment.getComponent(TubeComponent.class);
      if (tubeType.equalsIgnoreCase("va")) {
        tube.setMode(Mode.VA);
      }
      else if (tubeType.equalsIgnoreCase("vv")) {
        tube.setMode(Mode.VV);
      }
      
      String oxyType = parameters.get("scenario-oxy-type");
      OxygenatorComponent oxy = (OxygenatorComponent)equipment.getComponent(OxygenatorComponent.class);
      if (oxyType.equalsIgnoreCase("quadrox")) {
        oxy.setOxyType(OxyType.QUADROX_D);
      }
      else if (oxyType.equalsIgnoreCase("silicone") 
          || oxyType.equalsIgnoreCase("silicon")) {
        oxy.setOxyType(OxyType.SILICONE);
      }
      
      String pumpType = parameters.get("scenario-pump-type");
      PumpComponent pump = (PumpComponent)equipment.getComponent(PumpComponent.class);
      if (pumpType.equalsIgnoreCase("centrifugal")) {
        pump.setPumpType(PumpType.CENTRIFUGAL);
      }
      else if (pumpType.equalsIgnoreCase("roller")) {
        pump.setPumpType(PumpType.ROLLER);
      }

      String ventType = parameters.get("scenario-vent-type");
      VentilatorComponent vent = (VentilatorComponent)equipment.getComponent(VentilatorComponent.class);
      if (ventType.equalsIgnoreCase("conventional")) {
        vent.setSubtype(new VentilatorComponent.ConventionalSubtype());
      }
      else if (ventType.equalsIgnoreCase("highfrequency")) {
        vent.setSubtype(new VentilatorComponent.HighFrequencySubtype());
      }
      
      // the goal
      String goalName = parameters.get("goal");
      if (goalName != null) {
        if (goalName.equals("Baseline")) {
          // create goal
          BaselineGoal goal = new BaselineGoal();
          goal.setTimeInit(Long.parseLong(parameters.get("time-init")) * 1000);       // time-init in seconds...
          goal.setTimeLimit(Long.parseLong(parameters.get("time-limit")) * 1000);      // time-limit in seconds...
          goal.setInfoSuccess(parameters.get("info-success"));
          goal.setInfoFailure(parameters.get("info-failure"));
          goal.setTrigger(parameters.get("trigger"));
          scenario.setGoal(goal);            
        }
        else if (goalName.equals("Tutorial")) {
          // create goal
          TutorialGoal goal = new TutorialGoal();
          List<Item> items = new ArrayList<Item>();
          int index = 1;
          String prefix = "tutorial-item";
          while (parameters.get(prefix + index + "-text") != null) {
            String text = parameters.get(prefix + index + "-text");
            String trigger = parameters.get(prefix + index + "-trigger");
            Item item = new Item();
            item.setText(text);
            item.setTrigger(trigger.equals("N/A") ? null : trigger);
            items.add(item);
            index++;
          }
          goal.setItems(items);
          scenario.setGoal(goal);
        }
        else if (goalName.equals("Simulation")) {
          // create goal
          SimulationGoal goal = new SimulationGoal();
          scenario.setGoal(goal);            
        }
        else {
          // not implemented
          Error.out("Cannot parse goal: " + goalName);
        }
      }
      
      // the script
      String code = parameters.get("script");
      if (code != null) {
        Script script = new Script();
        script.setLang(Language.PNUTS.getName());
        script.setCode(decodeScript(code));
        scenario.setScript(script);
      }
      
      // the baseline
      Baseline baseline = scenario.getBaseline();
      baseline.setSedated(Boolean.parseBoolean(parameters.get("baseline-sedated")));
      baseline.setBleeding(Boolean.parseBoolean(parameters.get("baseline-bleeding")));
      baseline.setMinHeartRate(Double.parseDouble(parameters.get("baseline-min-heart-rate")));
      baseline.setMaxHeartRate(Double.parseDouble(parameters.get("baseline-max-heart-rate")));
      baseline.setMinO2Saturation(Double.parseDouble(parameters.get("baseline-min-O2-saturation")));
      baseline.setMaxO2Saturation(Double.parseDouble(parameters.get("baseline-max-O2-saturation")));
      baseline.setMinHgb(Double.parseDouble(parameters.get("baseline-min-HGB")));
      baseline.setMaxHgb(Double.parseDouble(parameters.get("baseline-max-HGB")));
      baseline.setMinPh(Double.parseDouble(parameters.get("baseline-min-pH")));
      baseline.setMaxPh(Double.parseDouble(parameters.get("baseline-max-pH")));
      baseline.setMinPco2(Double.parseDouble(parameters.get("baseline-min-pCO2")));
      baseline.setMaxPcO2(Double.parseDouble(parameters.get("baseline-max-pCO2")));
      baseline.setMinAct(Double.parseDouble(parameters.get("baseline-min-ACT")));
      baseline.setMaxAct(Double.parseDouble(parameters.get("baseline-max-ACT")));
      baseline.setMinTemperature(Double.parseDouble(parameters.get("baseline-min-temperature")));
      baseline.setMaxTemperature(Double.parseDouble(parameters.get("baseline-max-temperature")));
      baseline.setArterialA(TubeFunction.parse(parameters.get("baseline-arterial-A")));
      baseline.setArterialB(TubeFunction.parse(parameters.get("baseline-arterial-B")));
      baseline.setVenousA(TubeFunction.parse(parameters.get("baseline-venous-A")));
      baseline.setVenousB(TubeFunction.parse(parameters.get("baseline-venous-B")));
      baseline.setBridge(TubeFunction.parse(parameters.get("baseline-bridge")));
      baseline.setCannula(CannulaFunction.parse(parameters.get("baseline-cannula")));
      baseline.setUrineOutput(UrineOutputFunction.parse(parameters.get("baseline-urine-output")));
      baseline.setSuctionEtt(SuctionEttFunction.parse(parameters.get("baseline-suction-ETT")));
      baseline.setMinPreMembranePressure(Double.parseDouble(parameters.get("baseline-min-premembrane-pressure")));
      baseline.setMaxPreMembranePressure(Double.parseDouble(parameters.get("baseline-max-premembrane-pressure")));
      baseline.setMinPostMembranePressure(Double.parseDouble(parameters.get("baseline-min-postmembrane-pressure")));
      baseline.setMaxPostMembranePressure(Double.parseDouble(parameters.get("baseline-max-postmembrane-pressure")));
      baseline.setMinVenousPressure(Double.parseDouble(parameters.get("baseline-min-venous-pressure")));
      baseline.setMaxVenousPressure(Double.parseDouble(parameters.get("baseline-max-venous-pressure")));
      baseline.setArterialBubbles(Boolean.parseBoolean(parameters.get("baseline-arterial-bubbles")));
      baseline.setVenousBubbles(Boolean.parseBoolean(parameters.get("baseline-venous-bubbles")));
      baseline.setMinFiO2(Double.parseDouble(parameters.get("baseline-min-fiO2")));
      baseline.setMaxFiO2(Double.parseDouble(parameters.get("baseline-max-fiO2")));
      baseline.setBroken(Boolean.parseBoolean(parameters.get("baseline-broken")));
      baseline.setPower(PowerFunction.parse(parameters.get("baseline-power")));
      baseline.setFlow(parseNum(parameters, "baseline-flow"));
      baseline.setAlarming(Boolean.parseBoolean(parameters.get("baseline-alarming")));
      
      // the patient
      Patient patient = scenario.getPatient();
      patient.setSpecies(Species.parse(parameters.get("patient-species")));
      patient.setGender(Gender.parse(parameters.get("patient-gender")));
      patient.setAge(Double.parseDouble(parameters.get("patient-age")));
      patient.setWeight(Double.parseDouble(parameters.get("patient-weight")));
      patient.setLungFunction(LungFunction.parse(parameters.get("patient-lung-function")));
      patient.setHeartFunction(HeartFunction.parse(parameters.get("patient-heart-function")));
      patient.setSedated(Boolean.parseBoolean(parameters.get("patient-sedated")));
      patient.setBleeding(Boolean.parseBoolean(parameters.get("patient-bleeding")));
      patient.setBloodVolume(parseNum(parameters, "patient-blood-volume"));
      patient.setTemperature(Double.parseDouble(parameters.get("patient-temperature")));
      
      // load the heater component
      HeaterComponent heater = (HeaterComponent)equipment.getComponent(HeaterComponent.class);
      heater.setTemperature(Double.parseDouble(parameters.get("heater-temperature")));
      heater.setBroken(Boolean.parseBoolean(parameters.get("heater-broken")));
      
      // load the oxygenator component
      oxy.setTotalSweep(Double.parseDouble(parameters.get("oxygenator-total-sweep")));
      oxy.setFiO2(Double.parseDouble(parameters.get("oxygenator-fio2")));
      oxy.setBroken(Boolean.parseBoolean(parameters.get("oxygenator-broken")));
      oxy.setClotting(Double.parseDouble(parameters.get("oxygenator-clotting")));
      
      // load the pump component
      pump.setOn(Boolean.parseBoolean(parameters.get("pump-on")));
      pump.setFlow(Double.parseDouble(parameters.get("pump-flow")));
      
      // load the intervention point information
      for (int i = 0; i < equipment.size(); i++) {
        Component component = equipment.get(i);
        if (component instanceof InterventionComponent) {
          InterventionComponent intervention = (InterventionComponent)component;
          String loc = intervention.getInterventionLocation().getName();
          String key = "intervention-" + loc + "-cracked-pigtail";
          intervention.setCrackedPigtail(Boolean.parseBoolean(parameters.get(key)));
        }
      }
      
      // load the tube component
      tube.setArterialAOpen(TubeFunction.parse(parameters.get("tube-clamp-arterial-A")) == TubeFunction.OPEN);
      tube.setArterialBOpen(TubeFunction.parse(parameters.get("tube-clamp-arterial-B")) == TubeFunction.OPEN);
      tube.setVenousAOpen(TubeFunction.parse(parameters.get("tube-clamp-venous-A")) == TubeFunction.OPEN);
      tube.setVenousBOpen(TubeFunction.parse(parameters.get("tube-clamp-venous-A")) == TubeFunction.OPEN);
      tube.setBridgeOpen(TubeFunction.parse(parameters.get("tube-clamp-bridge")) == TubeFunction.OPEN);
      tube.setArterialBubbles(Boolean.parseBoolean(parameters.get("tube-bubbles-arterial")));
      tube.setVenousBubbles(Boolean.parseBoolean(parameters.get("tube-bubbles-venous")));
      String prob = parameters.get("tube-cannula-status");
      TubeComponent.Status problem;
      if (prob.equals("Kink")) {
        problem = TubeComponent.Status.KINK;
      }
      else if (prob.equals("High")) {
        problem = TubeComponent.Status.HIGH;
      }
      else if (prob.equals("Low")) {
        problem = TubeComponent.Status.LOW;
      }
      else if (prob.equals("Leak")) {
        problem = TubeComponent.Status.LEAK;
      }
      else {
        problem = TubeComponent.Status.NORMAL;
      }
      String loc = parameters.get("tube-cannula-problem-location");
      TubeComponent.problemLocation place;
      if (loc.equals("Arterial")) {
        place = TubeComponent.problemLocation.arterial;
      }
      else if (loc.equals("Venous")) {
        place = TubeComponent.problemLocation.venous;
      }
      else if (loc.equals("Cephalad")) {
        place = TubeComponent.problemLocation.cephalad;
      }
      else {
        place = TubeComponent.problemLocation.none;
      }
      tube.setBrokenCannula(Boolean.parseBoolean(parameters.get("tube-cannula-broken")), problem, place);
      tube.setArterialBOpen(TubeFunction.parse(parameters.get("tube-cannula-arterial-B")) == TubeFunction.OPEN);
      tube.setVenousBOpen(TubeFunction.parse(parameters.get("tube-cannula-venous-B")) == TubeFunction.OPEN);
      Double val = parseNum(parameters, "tube-cannula-premembrane-pressure");
      if (val.isNaN()) {
        if (oxy.getOxyType() == OxyType.QUADROX_D) {
          tube.setPreMembranePressure(125);
        }
        else if (oxy.getOxyType() == OxyType.SILICONE) {
          tube.setPreMembranePressure(240);
        }
        else {
          //Error here if oxygenator not selected in scn file, however
          //  tube is expecting a value to be set, so set to NaN.
          tube.setPreMembranePressure(val);
        }
      }
      else {
        tube.setPreMembranePressure(val);
      }
      val = parseNum(parameters, "tube-cannula-postmembrane-pressure");
      if (val.isNaN()) {
        if (oxy.getOxyType() == OxyType.QUADROX_D) {
          tube.setPreMembranePressure(120);
        }
        else if (oxy.getOxyType() == OxyType.SILICONE) {
          tube.setPreMembranePressure(220);
        }
        else {
          //Error here if oxygenator not selected in scn file, however
          //  tube is expecting a value to be set, so set to NaN.
          tube.setPreMembranePressure(val);
        }
      }
      else {
        tube.setPostMembranePressure(val);
      }
      tube.setVenousPressure(parseNum(parameters, "tube-cannula-venous-pressure"));
      tube.setCoagulopathy(Boolean.parseBoolean(parameters.get("tube-coagulopathy")));
      
      // Begin of normal initial labs displayed on start
      //act
      patient.setAct(Double.NaN);
      //FIXME: Code to initialize ACT prior to game start, Neither
//        for (Component component: equipment) {
//          if (component instanceof ACTComponent) {
//            ACTComponent actComponent = (ACTComponent)component;
//            ACT act = new ACT();
//            act.setValue(patient.getAct());
//            act.setTime(0);
//            actComponent.addACT(act);
//          }
//        }
//        FIXME: nor the following work. See Manager.java for kludge fix.
//        ACTComponent actComponent = (ACTComponent) equipment.getComponent(ACTComponent.class);
//        ACT act = new ACT();
//        act.setValue(patient.getAct());
//        act.setTime(0);
//        actComponent.addACT(act);

      //blood gas
      patient.setPH(Double.NaN);
      patient.setPCO2(Double.NaN);
      patient.setPO2(Double.NaN);
      LabComponent labComponent = null;
      for (Component component: equipment) {
        if (component instanceof LabComponent) {
          if (((LabComponent)component).getLabTest().isAssignableFrom(BloodGasLab.class)) {
            labComponent = (LabComponent)component;
          }
        }
      }
      LabRequestAction abgact = new LabRequestAction();
      labComponent.addResult(abgact.getBloodGas(patient));
      
      //chemistry
      for (Component component: equipment) {
        if (component instanceof LabComponent) {
          if (((LabComponent)component).getLabTest().isAssignableFrom(ChemistryLab.class)) {
            labComponent = (LabComponent)component;
          }
        }
      }
      LabRequestAction chemact = new LabRequestAction();
      labComponent.addResult(chemact.getChemistry(patient));
      
      //hematology
      patient.setFibrinogen(Double.NaN);
      patient.setPlatelets(Double.NaN);
      patient.setPt(Double.NaN);
      patient.setPtt(Double.NaN);
      for (Component component: equipment) {
        if (component instanceof LabComponent) {
          if (((LabComponent)component).getLabTest().isAssignableFrom(HematologyLab.class)) {
            labComponent = (LabComponent)component;
          }
        }
      }
      LabRequestAction hemeact = new LabRequestAction();
      labComponent.addResult(hemeact.getHematology(patient));
      // End of initial lab display

      // load scenario specific patient/lab values
      patient.setAct(parseNum(parameters, "act-value"));
      patient.setPH(parseNum(parameters, "lab-component-abg-ph"));
      patient.setPCO2(parseNum(parameters, "lab-component-abg-pco2"));
      patient.setPO2(parseNum(parameters, "lab-component-abg-po2"));
      patient.setFibrinogen(parseNum(parameters, "lab-component-heme-fibrinogen"));
      patient.setPlatelets(parseNum(parameters, "lab-component-heme-platelets"));
      patient.setPt(parseNum(parameters, "lab-component-heme-pt"));
      patient.setPtt(parseNum(parameters, "lab-component-heme-ptt"));
      patient.setHgb(parseNum(parameters, "lab-component-heme-hgb"));
 
      // load the xray, ultrasound and echo images (comma-delimited list)
      LabComponent imagingComponent = null;
      for (Component component: equipment) {
        if (component instanceof LabComponent) {
          if (((LabComponent)component).getLabTest().isAssignableFrom(ImagingLab.class)) {
            imagingComponent = (LabComponent)component;
          }
        }
      }
      String[] images;
      
      //cxr
      HashMap<TubeComponent.Mode, String> cxrMap = new HashMap<Mode, String>();
      images = parameters.get("lab-img-xray").split(",");
      for (int i = 0; i < images.length; i++) {
        String image = images[i];
        if (tube.getMode() == null) {
          if (image.contains("-va-")) { //TODO: verify filenames
            cxrMap.put(Mode.VA, image);
          }
          else if (image.contains("-vv-")) { //TODO: verify filenames
            cxrMap.put(Mode.VV, image);
          }
        }
        else {
          XRayLab labTest = new XRayLab();
          labTest.setDescription("Chest, X-Ray");
          labTest.setImageName(image + ".png");
          labTest.setTime(0);
          imagingComponent.addResult(labTest);
        }
      }
      imagingComponent.putScenarioImaging("X-Ray", cxrMap);
      
      //ultrasound
      images = parameters.get("lab-img-us").split(",");
      for (int i = 0; i < images.length; i++) {
        String image = images[i];
        UltrasoundLab labTest = new UltrasoundLab();
        labTest.setDescription("Head, US");
        labTest.setImageName(image + ".png");
        labTest.setTime(0);
        imagingComponent.addResult(labTest);
      }
      
      //echo
      HashMap<TubeComponent.Mode, String> echoMap = new HashMap<Mode, String>();
      images = parameters.get("lab-img-echo").split(",");
      for (int i = 0; i < images.length; i++) {
        String image = images[i];
        if (tube.getMode() == null) {
          if (image.contains("-va-")) {
            echoMap.put(Mode.VA, image);
          }
          else if (image.contains("-vv-")) {
            echoMap.put(Mode.VV, image);
          }
        }
        else {
          EchoLab labTest = new EchoLab();
          labTest.setDescription("Echo");
          labTest.setImageName(image + ".png");
          labTest.setTime(0);
          imagingComponent.addResult(labTest);
        }
      }
      imagingComponent.putScenarioImaging("Echo", echoMap);
      
      parameters.clear();
      
      // and return the scenario
      return scenario;
    }
    finally {
      // close stream
      if (reader != null) {
        reader.close();
        reader = null;
      }
    }
  }
  
  /**
   * Loads a scenario.
   * 
   * @param hookup  The location.
   * @param path  The path.
   * @return  The scenario.
   * @throws IOException  If something goes wrong.
   */
  public static ScenarioFile loadFile(Hookup hookup, String path) throws IOException {
    return loadFile(hookup.getInputStream(path));
  }
  
  /**
   * Loads a scenario.
   * 
   * @param inputStream  The input stream.
   * @return  The scenario.
   * @throws IOException  If something goes wrong.
   */
  public static ScenarioFile loadFile(InputStream inputStream) throws IOException {
    // read the data
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
    StringBuilder parameters = new StringBuilder();
    String code = "";
    String line = reader.readLine();
    while (line != null) {
      if (line.startsWith("script=") || (line.startsWith("script ="))) {
        code = line.substring(line.indexOf("=") + 1).trim();
      }
      else {
        parameters.append(line).append("\n");
      }
      line = reader.readLine();
    }
    reader.close();
    
    // create and return the scenario
    ScenarioFile scenario = new ScenarioFile();
    scenario.setParameters(parameters.toString());
    Script script = new Script();
    script.setLang(Language.PNUTS.getName());
    script.setCode(decodeScript(code));
    scenario.setScript(script);
    return scenario;
  }
  
  /**
   * Saves a scenario.
   * 
   * @param hookup  The location.
   * @param path  The path.
   * @param scenario  The scenario.
   * @throws IOException  If something goes wrong.
   */
  public static void saveFile(Hookup hookup, String path, ScenarioFile scenario) throws IOException {
    if (hookup instanceof LocalHookup) {
      LocalHookup localHookup = (LocalHookup)hookup;
      saveFile(localHookup.getOutputStream(path), scenario);
    }
  }
  
  /**
   * Saves a scenario.
   * 
   * @param outputStream  Where to save it to.
   * @param scenario  The the scenario.
   * @throws IOException  If something goes wrong.
   */
  public static void saveFile(OutputStream outputStream, ScenarioFile scenario) throws IOException {
    PrintWriter out = new PrintWriter(outputStream);
    out.println(scenario.getParameters());
    String code = scenario.getScript().getCode();
    out.println("script = " + encodeScript(code));
    out.close();
  }
  
  /**
   * Parses a number.
   * 
   * @param parameters  The parameters list.
   * @param key  Key to process.
   * @return double number or NaN if something goes wrong.
   */
  private static double parseNum(Map<String, String> parameters, String key) {
    if (!parameters.containsKey(key)) {
      return Double.NaN;
    }
    if ((parameters.get(key) != null) && (parameters.get(key).length() > 0)) {
      return Double.parseDouble(parameters.get(key));
    }
    else {
      return Double.NaN;
    }
  }
  
  /**
   * Encodes a script.
   * 
   * @param code  The code to encode.
   * @return  The encoded code.
   */
  private static String encodeScript(String code) {
    code = code.replace("\r\n", "${linebreak}");
    code = code.replace("\n", "${linebreak}");
    code = code.replace("=", "${equals}");
    return code;
  }
  
  /**
   * Decodes a script.
   * 
   * @param code  The code to decode.
   * @return  The decoded code.
   */
  private static String decodeScript(String code) {
    code = code.replace("${linebreak}", "\n");
    code = code.replace("${equals}", "=");
    return code;
  }
}
