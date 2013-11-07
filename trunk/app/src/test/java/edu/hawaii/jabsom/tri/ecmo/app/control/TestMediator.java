package edu.hawaii.jabsom.tri.ecmo.app.control;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit tests for Mediator class.
 * 
 * @author tanaka
 *
 */
public class TestMediator {

//  @Test
//  public void testFlowToPH() {
//    fail("Not yet implemented");
//  }
//
//  @Test
//  public void testFlowToPCO2() {
//    fail("Not yet implemented");
//  }

  /**
   * Test function calcOxygenSaturation.
   * 
   */
  @Test
  public void testCalcOxygenSaturation() {
    double po2 = 500;
    double expected = 0.9999;
    double result = Mediator.calcOxygenSaturation(po2);
    assertEquals(expected, result, 0.0001);
  }

}
