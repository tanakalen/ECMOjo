/**
 * 
 */
package edu.hawaii.jabsom.tri.ecmo.app.model.comp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent.Mode;

/**
 * @author tanaka
 *
 */
public class TubeComponentTest {

  /**
   * Private variable TubeComponent.
   */
  private TubeComponent tubeComponent;

  /**
   * @throws java.lang.Exception  For errors.
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception  For errors.
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * @throws java.lang.Exception  For errors.
   */
  @Before
  public void setUp() throws Exception {
    this.tubeComponent = new TubeComponent();
  }

  /**
   * @throws java.lang.Exception  For errors.
   */
  @After
  public void tearDown() throws Exception {
  }

  /**
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getName()}.
   */
  @Test
  public void testGetName() {
    // Testing English strings TODO: i18n testing?
    tubeComponent.setMode(Mode.VA);
    assertEquals("Returns mode name: VA ECMO.", tubeComponent.getName(), "VA ECMO");
    tubeComponent.setMode(Mode.VV);
    assertEquals("Returns mode name: VV ECMO.", tubeComponent.getName(), "VV ECMO");
  }

  /**
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setMode(Mode)}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getMode()}.
   */
  @Test
  public void testSetGetMode() {
    tubeComponent.setMode(Mode.VA);
    assertEquals("Returns mode: VA.", tubeComponent.getMode(), Mode.VA);
    tubeComponent.setMode(Mode.VV);
    assertEquals("Returns mode: VV.", tubeComponent.getMode(), Mode.VV);
  }

  /**
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#isArterialAOpen()}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#isArterialBOpen()}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#isBridgeOpen()}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#isVenousAOpen()}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#isVenousBOpen()}.
   * And, tests respective setters.
   */
  @Test
  public void testAreClampsOpen() {
    tubeComponent.setArterialAOpen(true);
    assertTrue("Arterial A: open.", tubeComponent.isArterialAOpen());
    tubeComponent.setArterialBOpen(true);
    assertTrue("Arterial B: open.", tubeComponent.isArterialBOpen());
    tubeComponent.setBridgeOpen(true);
    assertTrue("Bridge: open.", tubeComponent.isBridgeOpen());
    tubeComponent.setVenousAOpen(true);
    assertTrue("Venous A: open.", tubeComponent.isVenousAOpen());
    tubeComponent.setVenousBOpen(true);
    assertTrue("Venous B: open.", tubeComponent.isVenousBOpen());
  }

  /**
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#isCoagulopathy()}.
   */
  @Test
  public void testIsCoagulopathy() {
    tubeComponent.setCoagulopathy(true);
    assertTrue("DIC, coagulopathy present.", tubeComponent.isCoagulopathy());
  }

  /**
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#
   *                          setBrokenCannula(boolean, Status, problemLocation)}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getCannulaProblem()}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getCannulaProblemLocation()}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#isBrokenCannula()}.
   */
  @Test
  public void testBrokenCannula() {
    tubeComponent.setBrokenCannula(false, TubeComponent.Status.NORMAL, 
        TubeComponent.problemLocation.none);
    assertFalse("Cannula not broken.", tubeComponent.isBrokenCannula());
    assertEquals("Cannula normal.", TubeComponent.Status.NORMAL, 
                 tubeComponent.getCannulaProblem());
    assertEquals("No problem location.", TubeComponent.problemLocation.none,
                 tubeComponent.getCannulaProblemLocation());
    tubeComponent.setBrokenCannula(true, TubeComponent.Status.HIGH, 
                                   TubeComponent.problemLocation.arterial);
    assertTrue("Cannula broken.", tubeComponent.isBrokenCannula());
    assertEquals("Cannula problem is too high.", TubeComponent.Status.HIGH, 
                 tubeComponent.getCannulaProblem());
    assertEquals("Arterial cannula problem.", TubeComponent.problemLocation.arterial, 
                 tubeComponent.getCannulaProblemLocation());
  }

  /**
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setBrokenETT(boolean)}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#isBrokenETT()}.
   */
  @Test
  public void testBrokenETT() {
    tubeComponent.setBrokenETT(true);
    assertTrue("ETT is malpositioned.", tubeComponent.isBrokenETT());
  }

  /**
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setSuctionETT(boolean)}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#isSuctionETT()}.
   */
  @Test
  public void testSuctionETT() {
    tubeComponent.setSuctionETT(true);
    assertTrue("Suctioned ETT.", tubeComponent.isSuctionETT());
  }

  /**
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setPostMembranePressure(double)}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getPostMembranePressure()}.
   */
  @Test
  public void testSetPostMembranePressure() {
    tubeComponent.setPostMembranePressure(100);
    assertEquals("Post pressure is 100.", 100, tubeComponent.getPostMembranePressure(), 0);
  }

  /**
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setPreMembranePressure(double)}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getPreMembranePressure()}.
   */
  @Test
  public void testSetPreMembranePressure() {
    tubeComponent.setPreMembranePressure(100);
    assertEquals("Pre pressure is 100.", 100, tubeComponent.getPreMembranePressure(), 0);
  }

  /**
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setVenousPressure(double)}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getVenousPressure()}.
   */
  @Test
  public void testSetVenousPressure() {
    tubeComponent.setVenousPressure(100);
    assertEquals("Venous pressure is 100.", 100, tubeComponent.getVenousPressure(), 0);
  }

  /**
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setArterialBubbles(boolean)}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#isArterialBubbles()}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setVenousBubbles(boolean)}.
   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#isVenousBubbles()}.
   */
  @Test
  public void testBubbles() {
    tubeComponent.setArterialBubbles(true);
    assertTrue("Arterial bubbles present.", tubeComponent.isArterialBubbles());
    tubeComponent.setVenousBubbles(true);
    assertTrue("Venous bubbles present.", tubeComponent.isVenousBubbles());
  }

// Following tests will be moved to future Quantun (QuantityUnknown) class.
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getSvO2()}.
//   */
//  @Test
//  public void testGetSvO2() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setSvO2(double)}.
//   */
//  @Test
//  public void testSetSvO2() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getPrePH()}.
//   */
//  @Test
//  public void testGetPrePH() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setPrePH(double)}.
//   */
//  @Test
//  public void testSetPrePH() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getPrePCO2()}.
//   */
//  @Test
//  public void testGetPrePCO2() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setPrePCO2(double)}.
//   */
//  @Test
//  public void testSetPrePCO2() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getPreBE(double)}.
//   */
//  @Test
//  public void testGetPreBE() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getPreHCO3()}.
//   */
//  @Test
//  public void testGetPreHCO3() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getSaO2()}.
//   */
//  @Test
//  public void testGetSaO2() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setSaO2(double)}.
//   */
//  @Test
//  public void testSetSaO2() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getPostPH()}.
//   */
//  @Test
//  public void testGetPostPH() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setPostPH(double)}.
//   */
//  @Test
//  public void testSetPostPH() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getPostPCO2()}.
//   */
//  @Test
//  public void testGetPostPCO2() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#setPostPCO2(double)}.
//   */
//  @Test
//  public void testSetPostPCO2() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getPostBE(double)}.
//   */
//  @Test
//  public void testGetPostBEDouble() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getPostBE()}.
//   */
//  @Test
//  public void testGetPostBE() {
//    fail("Not yet implemented"); // TODO
//  }
//
//  /**
//   * Test method for {@link edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent#getPostHCO3()}.
//   */
//  @Test
//  public void testGetPostHCO3() {
//    fail("Not yet implemented"); // TODO
//  }

}
