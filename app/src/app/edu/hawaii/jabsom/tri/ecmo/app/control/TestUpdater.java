package edu.hawaii.jabsom.tri.ecmo.app.control;

import static org.junit.Assert.*;

import java.io.IOException;

import king.lib.access.Access;
import king.lib.access.AccessException;
import king.lib.access.LocalHookup;
import king.lib.out.Error;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.hawaii.jabsom.tri.ecmo.app.model.Game;
import edu.hawaii.jabsom.tri.ecmo.app.model.Scenario;
import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioLoader;
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
public class TestUpdater {
  
  /** Static game variable for testing. */
  static Game game;

  /**
   * SetUpBeforeClass makes a one time initialization.
   * 
   * @throws Exception If error occurs.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
//    game = new Game(ScenarioCreator.create());
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
  }

  /**
   * SetUp creates elements needed prior to running each test.
   * 
   * @throws Exception If error occurs.
   */
  @Before
  public void setUp() throws Exception {
    LocalHookup hookup = LocalHookup.getInstance();
    try {
      Scenario scenario = ScenarioLoader.load(hookup, "scenario/Tutorial-1.scn");
      game = new Game(scenario);
      game.setElapsedTime(0);
      History history = new History();
      Updater.store(game, history);
      Updater.execute(game, history, 20); // sets oldFlow to 0.6
    }
    catch (IOException e) {
      Error.out(e);
    }
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
    PumpComponent pump = (PumpComponent)equipment.getComponent(PumpComponent.class);
    pump.setFlow(0.7); // increased by 20 mL/kg/min
    double expected = 58.0 * 1.1; // increase SBP by 10%
    Updater.store(game, history);
    Updater.execute(game, history, 20);
    patient = game.getPatient();
    assertEquals(expected, patient.getSystolicBloodPressure());
    
    pump = (PumpComponent)equipment.getComponent(PumpComponent.class);
    pump.setFlow(0.6); // decrease by 20 mL/kg/min
    expected = expected * 0.9; // increase SBP by 10%
    Updater.store(game, history);
    Updater.execute(game, history, 20);
    patient = game.getPatient();
    assertEquals(expected, patient.getSystolicBloodPressure());    
  }

}
