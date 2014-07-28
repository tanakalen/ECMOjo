package edu.hawaii.jabsom.tri.ecmo.app.model;

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
import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioCreator;

/**
 * TestGame is a JUnit test for Game class.
 * 
 * @author tanaka
 *
 */
public class GameTest {

  /** Static string for system user dir. */
  private static String origUsrDir;
  /** Static game variable for testing. */
  private static Game game;
  /** User string variable for testing. */
  private String user;
  /** Scenario variable for testing. */
  private Scenario scenario;

  /**
   * SetUpBeforeClass makes a one time initialization.
   * 
   * @throws Exception If error occurs.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
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
    scenario = ScenarioCreator.create();
    ScenarioCreator.setup(scenario);
    user = "(user)";
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
   * testGame: Tests Game class constructor.
   * 
   */
  @Test
  public void testGame() {
    game = new Game(scenario, user);
    assertNotNull("Game constructor yields new game object.", game);
  }

  /**
   * testGetUser: Tests Game class getUser() method.
   * 
   */
  @Test
  public void testGetUser() {
    assertEquals("Game yields user.", game.getUser(), this.user);
  }

  /**
   * testGetName: Tests Game class getName() method returns scenario name.
   * 
   */
  @Test
  public void testGetName() {
    assertEquals("Game yields scenario name.", game.getName(), this.scenario.getName());
  }

  /**
   * testGetDescription: Tests Game class getDescription() method.
   * 
   */
  @Test
  public void testGetDescription() {
    fail("Not yet implemented");
  }

  /**
   * testGetGoal: Tests Game class getGoal() method.
   * 
   */
  @Test
  public void testGetGoal() {
    fail("Not yet implemented");
  }

  /**
   * testGetBaseline: Tests Game class getBaseline() method.
   * 
   */
  @Test
  public void testGetBaseline() {
    fail("Not yet implemented");
  }

  /**
   * testGetScript: Tests Game class getScript() method.
   * 
   */
  @Test
  public void testGetScript() {
    fail("Not yet implemented");
  }

  /**
   * testGetElapsedTime: Tests Game class getElapsedTime() method.
   * 
   */
  @Test
  public void testGetElapsedTime() {
    fail("Not yet implemented");
  }

  /**
   * testSetElapsedTime: Tests Game class setElapsedTime() method.
   * 
   */
  @Test
  public void testSetElapsedTime() {
    fail("Not yet implemented");
  }

  /**
   * testGetEquipment: Tests Game class getEquipment() method.
   * 
   */
  @Test
  public void testGetEquipment() {
    fail("Not yet implemented");
  }

  /**
   * testSetEquipment: Tests Game class setEquipment() method.
   * 
   */
  @Test
  public void testSetEquipment() {
    fail("Not yet implemented");
  }

  /**
   * testGetPatient: Tests Game class getPatient() method.
   * 
   */
  @Test
  public void testGetPatient() {
    fail("Not yet implemented");
  }

  /**
   * testSetPatient: Tests Game class setPatient() method.
   * 
   */
  @Test
  public void testSetPatient() {
    fail("Not yet implemented");
  }

  /**
   * testGetTracker: Tests Game class getTracker() method.
   * 
   */
  @Test
  public void testGetTracker() {
    fail("Not yet implemented");
  }

  /**
   * testSetTracker: Tests Game class setTracker() method.
   * 
   */
  @Test
  public void testSetTracker() {
    fail("Not yet implemented");
  }

  /**
   * testGetActions: Tests Game class getActions() method.
   * 
   */
  @Test
  public void testGetActions() {
    fail("Not yet implemented");
  }

  /**
   * testSetActions: Tests Game class setActions() method.
   * 
   */
  @Test
  public void testSetActions() {
    fail("Not yet implemented");
  }

}
