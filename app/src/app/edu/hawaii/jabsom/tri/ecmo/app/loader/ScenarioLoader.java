package edu.hawaii.jabsom.tri.ecmo.app.loader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;
import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline.CannulaFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline.EttFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline.PowerFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline.SuctionEttFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.Baseline.TubeFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.HeaterComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.HeartFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.LungFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.BaselineGoal;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal;
import edu.hawaii.jabsom.tri.ecmo.app.model.goal.TutorialGoal.Item;

import king.lib.out.Error;
import king.lib.access.Hookup;
import king.lib.access.LocalHookup;

/**
 * The scenario loader. 
 *
 * @author   king
 * @since    October 27, 2008
 */
public final class ScenarioLoader {
  
  /** The current version. */
  private static final int CURRENT_VERSION = 1;
  
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
      reader = new BufferedReader(new InputStreamReader(inputStream));    
    
      // determine scenario file version
      int version = Integer.parseInt(reader.readLine());
      if ((version >= 1) && (version <= CURRENT_VERSION)) {
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
        
        // the name
        scenario.setName(parameters.get("name"));
        scenario.setDescription(parameters.get("desc"));
        
        // the goal
        String goalName = parameters.get("goal");
        if (goalName != null) {
          if (goalName.equals("Baseline")) {
            // create goal
            BaselineGoal goal = new BaselineGoal();
            goal.setTimeLimit(Long.parseLong(parameters.get("time")) * 1000);      // time-limit in seconds...
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
          else {
            // not implemented
            Error.out("Cannot parse goal: " + goalName);
          }
        }
        
        // the baseline
        Baseline baseline = scenario.getBaseline();
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
        baseline.setEtt(EttFunction.parse(parameters.get("baseline-ETT")));
        baseline.setSuctionEtt(SuctionEttFunction.parse(parameters.get("baseline-suction-ETT")));
        baseline.setMinPreMembranePressure(Double.parseDouble(parameters.get("baseline-min-pre-membrane-pressure")));
        baseline.setMaxPreMembranePressure(Double.parseDouble(parameters.get("baseline-max-pre-membrane-pressure")));
        baseline.setMinPostMembranePressure(Double.parseDouble(parameters.get("baseline-min-post-membrance-pressure")));
        baseline.setMaxPostMembranePressure(Double.parseDouble(parameters.get("baseline-max-post-membrance-pressure")));
        baseline.setMinVenousPressure(Double.parseDouble(parameters.get("baseline-min-venous-pressure")));
        baseline.setMaxVenousPressure(Double.parseDouble(parameters.get("baseline-max-venous-pressure")));
        baseline.setArterialBubbles(Boolean.parseBoolean(parameters.get("baseline-arterial-bubbles")));
        baseline.setVenousBubbles(Boolean.parseBoolean(parameters.get("baseline-venous-bubbles")));
        baseline.setMinFiO2(Double.parseDouble(parameters.get("baseline-min-fiO2")));
        baseline.setMaxFiO2(Double.parseDouble(parameters.get("baseline-max-fiO2")));
        baseline.setBroken(Boolean.parseBoolean(parameters.get("baseline-broken")));
        baseline.setPower(PowerFunction.parse(parameters.get("baseline-power")));
        baseline.setAlarming(Boolean.parseBoolean(parameters.get("baseline-alarming")));
        
        // the patient
        Patient patient = scenario.getPatient();
        patient.setAge(Double.parseDouble(parameters.get("patient-age")));
        patient.setWeight(Double.parseDouble(parameters.get("patient-weight")));
        patient.setLungFunction(LungFunction.parse(parameters.get("patient-lung-function")));
        patient.setHeartFunction(HeartFunction.parse(parameters.get("patient-heart-function")));
        patient.setBleeding(Boolean.parseBoolean(parameters.get("patient-bleeding")));
        // TODO: load other patient values
        
        // the equipment
        Equipment equipment = scenario.getEquipment();
        
        HeaterComponent heater = (HeaterComponent)equipment.getComponent(HeaterComponent.class);
        heater.setTemperature(Double.parseDouble(parameters.get("heater-temperature")));
        
        // TODO: load other equipment values
        
        // and return the scenario
        return scenario;
      }
      else {
        throw new IOException("Invalid scenario file version: " + version);
      }
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
   * Saves a scenario.
   * 
   * @param hookup  The location.
   * @param path  The path.
   * @param scenario  The the scenario.
   * @throws IOException  If something goes wrong.
   */
  public static void save(Hookup hookup, String path, Scenario scenario) throws IOException {
    if (hookup instanceof LocalHookup) {
      LocalHookup localHookup = (LocalHookup)hookup;
      save(localHookup.getOutputStream(path), scenario);
    }
  }
  
  /**
   * Saves a scenario.
   * 
   * @param outputStream  Where to save it to.
   * @param scenario  The the scenario.
   * @throws IOException  If something goes wrong.
   */
  public static void save(OutputStream outputStream, Scenario scenario) throws IOException {
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    
    // write the version
    writer.write(String.valueOf(CURRENT_VERSION));
    writer.newLine();
    
    // write the scenario name
    writer.write(scenario.getName());
    writer.newLine();
  }
}