package edu.hawaii.jabsom.tri.ecmo.app.control;

import static org.junit.Assert.*;

import java.io.File;

import king.lib.access.Access;
import king.lib.access.AccessException;
import king.lib.out.Error;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.hawaii.jabsom.tri.ecmo.app.AppType;
import edu.hawaii.jabsom.tri.ecmo.app.Configuration;
import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;
import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioCreator;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Equipment;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.HeartFunction;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent.Mode;

/**
 * TestUpdater is a JUnit test for Updater.
 * 
 * @author tanaka
 *
 */
public class UpdaterTest {
  
  /** Static game variable for testing. */
  static Game game;
  /** Static string for system user dir. */
  static String origUsrDir;

  /**
   * SetUpBeforeClass makes a one time initialization.
   * 
   * @throws Exception If error occurs.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
//    game = new Game(ScenarioCreator.create());
    origUsrDir = System.getProperty("user.dir");
    if ((origUsrDir.substring(origUsrDir.lastIndexOf(File.separator) + 1)).equals("test_output")) {
      System.setProperty("user.dir", origUsrDir);
    }
    else {
      System.setProperty("user.dir", "target");
    }
    try {
      Access.init();
     }
     catch (AccessException e) {
       Error.out(e);
     }
  }

  /**
   * TearDownAfterClass cleans up after completed all tests.
   * 
   * @throws Exception If error occurs.
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    System.setProperty("user.dir", origUsrDir);
  }

  /**
   * SetUp creates elements needed prior to running each test.
   * 
   * @throws Exception If error occurs.
   */
  @Before
  public void setUp() throws Exception {
    Configuration.init(AppType.INFANT);
    Scenario scenario = ScenarioCreator.create();
    ScenarioCreator.setup(scenario);
    String user = "(user)";
    game = new Game(scenario, user);
    game.setElapsedTime(0);
    History history = new History();
    Updater.store(game, history);
    Updater.execute(game, history, 20, null, null, null); // sets oldFlow to 0.6
  }

  /**
   * TearDown cleans up after each test is complete.
   * 
   * @throws Exception If error occurs.
   */
  @After
  public void tearDown() throws Exception {
  }
  
  /**
   * TestSBPVAInteraction checks that if VA flow increased on a bad heart SBP increases.
   * 
   */
  @Test
  public void testSBPVAinteraction() {
    // ScenarioCreator sets patient SBP to 58
    Patient patient = game.getPatient();
    patient.setHeartFunction(HeartFunction.BAD);
    Equipment equipment = game.getEquipment();
    TubeComponent tube = (TubeComponent)equipment.getComponent(TubeComponent.class);
    tube.setMode(Mode.VA);
    History history = new History();
    
    // Increase flow increases SBP
    // TODO: Verify correctness
    double expected = 58.0 * 1.1; // increase SBP by 10%
    int increment = 20;
    for (int i = 0; i < 1; i++) {
      Updater.store(game, history);
      PumpComponent pump = (PumpComponent)equipment.getComponent(PumpComponent.class);
      pump.setFlow(pump.getFlow() + 0.1); // increased by 20 mL/kg/min
      Updater.execute(game, history, increment, null, null, null);
    }
    patient = game.getPatient();
    System.out.println("Exp: " + expected + " SBP: " + patient.getSystolicBloodPressure());
    assertEquals(expected, patient.getSystolicBloodPressure(), 5);
    
//    pump = (PumpComponent)equipment.getComponent(PumpComponent.class);
//    pump.setFlow(0.6); // decrease by 20 mL/kg/min
//    expected = expected * 0.9; // decrease SBP by 10%
//    Updater.store(game, history);
//    Updater.execute(game, history, 1000, null, null, null);
//    patient = game.getPatient();
//    System.out.println("2 Exp: " + expected + " SBP: " + patient.getSystolicBloodPressure());
//    assertEquals(expected, patient.getSystolicBloodPressure(), 5);
  }
}
