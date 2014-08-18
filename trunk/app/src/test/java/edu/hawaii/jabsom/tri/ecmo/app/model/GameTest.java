package edu.hawaii.jabsom.tri.ecmo.app.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Iterator;

import king.lib.access.Access;
import king.lib.access.AccessException;
import king.lib.out.Error;
import edu.hawaii.jabsom.tri.ecmo.app.AppType;
import edu.hawaii.jabsom.tri.ecmo.app.Configuration;
import edu.hawaii.jabsom.tri.ecmo.app.loader.ScenarioCreator;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Component;
import edu.hawaii.jabsom.tri.ecmo.app.control.Action;
import edu.hawaii.jabsom.tri.ecmo.app.control.ActionList;

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
    game = new Game(scenario, user);
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
    Game newGame = new Game(scenario, user);
    game = newGame;
    assertNotNull("Game constructor creates a game object.", game);
    assertSame("Game object is same.", game, newGame);
    assertNotSame("Game object is different.", game, new Game(scenario, user));
  }

  /**
   * testGetUser: Tests Game class getUser() method.
   * 
   */
  @Test
  public void testGetUser() {
    assertEquals("Game yields user.", this.user, game.getUser());
  }

  /**
   * testGetName: Tests Game class getName() method returns scenario name.
   * 
   */
  @Test
  public void testGetName() {
    assertEquals("Game yields scenario name.",
                 this.scenario.getName(),
                 game.getName());
  }

  /**
   * testGetDescription: Tests Game class getDescription() method.
   * 
   */
  @Test
  public void testGetDescription() {
    assertEquals("Game has empty description.", null, game.getDescription());
  }

  /**
   * testGetGoal: Tests Game class getGoal() method.
   * 
   */
  @Test
  public void testGetGoal() {
    assertSame("Game has same BaselineGoal class.",
               this.scenario.getGoal(),
               game.getGoal());
  }

  /**
   * testGetBaseline: Tests Game class getBaseline() method.
   * 
   */
  @Test
  public void testGetBaseline() {
    assertSame("Game has same Baseline class.",
               this.scenario.getBaseline(),
               game.getBaseline());
  }

  /**
   * testGetScript: Tests Game class getScript() method.
   * 
   */
  @Test
  public void testGetScript() {
    assertSame("Game has same script.",
               this.scenario.getScript(), game.getScript());
  }

  /**
   * testSetElapsedTime: Tests Game class setElapsedTime() method.
   * testGetElapsedTime: Tests Game class getElapsedTime() method.
   * 
   */
  @Test
  public void testSetElapsedTime() {
    game.setElapsedTime(10);
    assertEquals("Get set elapsed time to 10.", 10, game.getElapsedTime());
  }

  /**
   * testGetEquipment: Tests Game class getEquipment() method.
   * 
   */
  @Test
  public void testGetEquipment() {
    Iterator<Component> s = scenario.getEquipment().iterator();
    Iterator<Component> g = game.getEquipment().iterator();
    while (s.hasNext() && g.hasNext()) {
      assertEquals("Scenario & game equipment name equal.",
                   s.next().getName(), g.next().getName());
      
    }
  }

  /**
   * testGetPatient: Tests Game class getPatient() method.
   * 
   */
  @Test
  public void testGetPatient() {
    // WARNING: tests only that species and age are same.
    assertEquals("Patient from scenario is human.", scenario.getPatient().getSpecies(), 
                 game.getPatient().getSpecies());
    assertEquals("Patient from scenario is 1.", scenario.getPatient().getAge(), 
                 game.getPatient().getAge(), 0);
  }

  /**
   * testGetTracker: Tests Game class getTracker() method.
   * 
   */
  @Test
  public void testGetTracker() {
    assertNotNull("Tracker present in game.", game.getTracker());
  }

  /**
   * testGetActions: Tests Game class getActions() method.
   * 
   */
  @Test
  public void testGetActions() {
    assertNotNull("ActionList present in game.", game.getActions());
    assertEquals("ActionList is 0.", 0, game.getActions().size());
  }

  /**
   * testSetActions: Tests Game class setActions() method.
   * 
   */
  @Test
  public void testSetActions() {
    ActionList actions = new ActionList();
    game.setActions(actions);
    assertEquals("Action list is same size.", actions.size(), game.getActions().size());
    // Create mock for future, or stub in package?
    actions.add(new Action() {

      /* (non-Javadoc)
       * @see edu.hawaii.jabsom.tri.ecmo.app.control.Action#execute(edu.hawaii.jabsom.tri.ecmo.app.model.Game)
       */
      @Override
      public void execute(Game game) {
        System.out.println("Stubbed Action.");
      }

      /* (non-Javadoc)
       * @see edu.hawaii.jabsom.tri.ecmo.app.control.Action#toString()
       */
      @Override
      public String toString() {
        return "Stub Action in testSetActions.";
      } 
      
    });
    game.setActions(actions);
    assertEquals("Action list is 1.", 1, game.getActions().size());
    
  }

}
