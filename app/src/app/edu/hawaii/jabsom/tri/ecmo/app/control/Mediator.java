package edu.hawaii.jabsom.tri.ecmo.app.control;

import edu.hawaii.jabsom.tri.ecmo.app.model.comp.*;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent.Mode;

/**
 * Mediator class to separate links between models from Apple Developer website.
 * http://developer.apple.com/documentation/Cocoa/Conceptual/CocoaFundamentals/
 * CocoaDesignPatterns/CocoaDesignPatterns.html
 * 
 * Collection of methods to simplify Updater.java
 * 
 * @author tanaka
 * @since December 2, 2010
 * 
 */
public final class Mediator {
  
  /**
   * Unused constructor.
   */
  private Mediator() {
    // cannot be used
  }

  /**
   * flowToPH provides linkage information of Pump flow to patient pH. Dependent
   * on mode of pump, kind of lung, kind of heart, and flow.
   * 
   * @param ecmo
   *          Mode of ecmo {VA, VV}
   * @param patient
   *          Current patient instantiation
   * @param flow
   *          Rate of blood flow in mL/kg/min
   * @return pH by curve fit of Mark's chart
   * @throws Exception for flow < 0, heartFunction bad, and data limit
   */
  public static double flowToPH(Mode ecmo, Patient patient, double flow) throws Exception {
    if (flow < 0) {
      throw new Exception("Flow cannot be less than 0");
    }
    if (ecmo == Mode.VV) {
      if (patient.getHeartFunction() == Patient.HeartFunction.BAD) {
        throw new Exception("VV ECMO and bad heart function not ideal");
      }
      else { // Heart function is good
        if (patient.getLungFunction() == Patient.LungFunction.BAD) {
          if ((flow >= 0) && (flow < 120)) {
            return ((7 * Math.pow(10, -7)) * Math.pow(flow, 3)) 
                    - (0.0001 * Math.pow(flow, 2)) + (0.0062 * flow) + 7.0291;
          }
          else if ((flow >= 120) && (flow <= 175)) {
            return (-0.0029 * flow) + 7.7503;
          }
          else {
            throw new Exception("Reached data limit"); // no data beyond 175cc/kg/min flow
          }
        }
        else { // Lungs are working why are we on ECMO?
          if ((flow >= 0) && (flow < 120)) {
            return (0.0021 * flow) + 7.148;
          }
          else if ((flow >= 120) && (flow <= 175)) {
            return (-0.0029 * flow) + 7.7503;
          }
          else {
            throw new Exception("Reached data limit"); // no data beyond 175cc/kg/min flow
          }
        }
      }
    }
    else { // must be VA
      if ((patient.getHeartFunction() == Patient.HeartFunction.BAD) 
          && (patient.getLungFunction() == Patient.LungFunction.BAD)) {
        if ((flow >= 0) && (flow < 100)) {
          return (3 * Math.pow(10, -8) * Math.pow(flow, 4)) 
          - (5 * Math.pow(10, -6) * Math.pow(flow, 3)) 
          + (0.0003 * Math.pow(flow, 2)) - (0.0019 * flow) + 7.05;
        }
        else if ((flow >= 100) && (flow <= 150)) {
          return (-0.001 * flow) + 7.4983;
        }
        else {
          throw new Exception("Reached data limit"); // no data beyond 150cc/kg/min flow
        }
      }
      else if ((patient.getHeartFunction() == Patient.HeartFunction.BAD) 
          && (patient.getLungFunction() == Patient.LungFunction.GOOD)) {
        if ((flow >= 0) && (flow < 100)) {
          return (3 * Math.pow(10, -8) * Math.pow(flow, 4)) 
          - (5 * Math.pow(10, -6) * Math.pow(flow, 3)) 
          + (0.0003 * Math.pow(flow, 2)) - (0.0037 * flow) + 7.12;
        }
        else if ((flow >= 100) && (flow <= 150)) {
          return (-0.0022 * flow) + 7.615;
        }
        else {
          throw new Exception("Reached data limit"); // no data beyond 150cc/kg/min flow
        }
      }
      else if ((patient.getHeartFunction() == Patient.HeartFunction.GOOD) 
          && (patient.getLungFunction() == Patient.LungFunction.BAD)) {
        if ((flow >= 0) && (flow < 100)) {
          return (4 * Math.pow(10, -7) * Math.pow(flow, 3)) 
          - (3 * Math.pow(10, -5) * Math.pow(flow, 2)) 
          + (0.0028 * flow) + 7.069;
        }
        else if ((flow >= 100) && (flow <= 150)) {
          return (6 * Math.pow(10, -5) * Math.pow(flow, 2)) - (0.017 * flow) + 8.54;
        }
        else {
          throw new Exception("Reached data limit"); // no data beyond 150cc/kg/min flow
        }
      }
      else { // if ((patient.getHeartFunction() == Patient.HeartFunction.GOOD) 
             // && (patient.getLungFunction() == Patient.LungFunction.GOOD)) {
        if ((flow >= 0) && (flow < 100)) {
          return (3 * Math.pow(10, -7) * Math.pow(flow, 3)) 
          - (4 * Math.pow(10, -5) * Math.pow(flow, 2)) 
          + (0.0031 * flow) + 7.1197;
        }
        else if ((flow >= 100) && (flow <= 150)) {
          return (-0.0018 * flow) + 7.5783;
        }
        else {
          throw new Exception("Reached data limit"); // no data beyond 175cc/kg/min flow
        }
      }
    }
  }

  /**
   * flowToPCO2 provides linkage information of Pump flow to patient pCO2.
   * Dependent on mode of pump, kind of lung, kind of heart, and flow.
   * 
   * @param ecmo
   *          Mode of ecmo {VA, VV}
   * @param patient
   *          Current patient instantiation
   * @param flow
   *          Rate of blood flow in mL/kg/min
   * @return pCO2 by curve fit of Mark's chart
   * @throws Exception for flow < 0, heartFunction bad, and data limit
   */
  public static double flowToPCO2(Mode ecmo, Patient patient, double flow) throws Exception {
    if (flow < 0) {
      throw new Exception("Flow cannot be less than 0");
    }
    if (ecmo == Mode.VV) {
      if (patient.getHeartFunction() == Patient.HeartFunction.BAD) {
        throw new Exception("VV ECMO and bad heart function not ideal");
      }
      else { // Heart function is good
        if (patient.getLungFunction() == Patient.LungFunction.BAD) {
          if ((flow >= 0) && (flow < 120)) {
            return (-6 * Math.pow(10, -5) * Math.pow(flow, 3)) 
                    + (0.0105 * Math.pow(flow, 2)) - (0.7277 * flow) + 83.074;
          }
          else if ((flow >= 120) && (flow <= 175)) {
            return (0.2352 * flow) + 11.451;
          }
          else {
            throw new Exception("Reached data limit");
          }
        }
        else { // Lungs are working why are we on ECMO?
          if ((flow >= 0) && (flow < 120)) {
            return (-8 * Math.pow(10, -6) * Math.pow(flow, 3)) 
                    + (0.0019 * Math.pow(flow, 2)) - (0.3402 * flow) + 66.01;
          }
          else if ((flow >= 120) && (flow <= 175)) {
            return (0.2352 * flow) + 11.451;
          }
          else {
            throw new Exception("Reached data limit");
          }
        }
      }
    }
    else { // must be VA
      if ((patient.getHeartFunction() == Patient.HeartFunction.BAD) 
          && (patient.getLungFunction() == Patient.LungFunction.BAD)) {
        if ((flow >= 0) && (flow < 100)) {
          return ((-3 * Math.pow(10, -6)) * Math.pow(flow, 4)) 
          + (0.0006 * Math.pow(flow, 3)) - (0.0316 * Math.pow(flow, 2)) 
          + (0.2 * flow) + 81;
        }
        else if ((flow >= 100) && (flow <= 150)) {
          return (0.06 * flow) + 34.833;
        }
        else {
          throw new Exception("Reached data limit");
        }
      }
      else if ((patient.getHeartFunction() == Patient.HeartFunction.BAD) 
          && (patient.getLungFunction() == Patient.LungFunction.GOOD)) {
        if ((flow >= 0) && (flow < 100)) {
          return ((-4 * Math.pow(10, -6)) * Math.pow(flow, 4)) 
          + (0.0007 * Math.pow(flow, 3)) - (0.0458 * Math.pow(flow, 2)) 
          + (0.59 * flow) + 72;
        }
        else if ((flow >= 100) && (flow <= 150)) {
          return (0.18 * flow) + 23.167;
        }
        else {
          throw new Exception("Reached data limit");
        }
      }
      else if ((patient.getHeartFunction() == Patient.HeartFunction.GOOD) 
          && (patient.getLungFunction() == Patient.LungFunction.BAD)) {
        if ((flow >= 0) && (flow < 100)) {
          return (3 * Math.pow(10, -6) * Math.pow(flow, 4)) 
          - (0.0005 * Math.pow(flow, 3)) + (0.0278 * Math.pow(flow, 2)) 
          - (0.77 * flow) + 80;
        }
        else if ((flow >= 100) && (flow <= 150)) {
          return (-0.004 * Math.pow(flow, 2)) + (1.26 * flow) - 45;
        }
        else {
          throw new Exception("Reached data limit");
        }
      }
      else { // if ((patient.getHeartFunction() == Patient.HeartFunction.GOOD) 
        // && (patient.getLungFunction() == Patient.LungFunction.GOOD)) {
        if ((flow >= 0) && (flow < 100)) {
          return (-0.292 * flow) + 70.8;
        }
        else if ((flow >= 100) && (flow <= 150)) {
          return (0.14 * flow) + 26.833;
        }
        else {
          throw new Exception("Reached data limit");
        }
      }
    }
  }

  /**
   * flowToSPO2 provides linkage information of Pump flow to patient oxygen saturation. 
   * Dependent on mode of pump, kind of lung, kind of heart, and flow. Created fit
   * equations in Excel on flow versus saturation with trendlines and best fit by
   * R-squared criterion. (PatientPaO2Values.xls)
   * 
   * @param ecmo
   *          Mode of ecmo {VA, VV}
   * @param flow
   *          Rate of blood flow in mL/kg/min
   * @param patient
   *          Current patient instantiation
   * @throws Exception for flow < 0, heartFunction bad, and data limit
   * @return Saturation
   */
  public static double flowToSPO2(Mode ecmo, double flow, Patient patient) throws Exception {
    if (ecmo == Mode.VV) {
      if (flow > 120) {
        // the saturation is going down
        flow = 2 * 120 - flow;
      }
    }
    
    // linear relation to flow
    double value = 0.50 + flow / 120 * 0.49;
    if (value < 0.5) {
      return 0.5;
    }
    else if (value > 1.0) {
      return 1.0;
    }
    else {
      return value;
    }
  }
    
  /**
   * flowToSvO2 provides linkage information of Pump flow to patient SvO2. Dependent
   * on mode of pump, kind of lung, kind of heart, and flow.
   * 
   * @param ecmo
   *          Mode of ecmo {VA, VV}
   * @param flow
   *          Rate of blood flow in L/min
   * @param patient
   *          Current patient instantiation
   * @throws Exception for flow < 0, heartFunction bad, and data limit
   * @return SvO2 by linear interpolation of Mark's chart
   */
  public static double flowToSvO2(Mode ecmo, double flow, Patient patient) throws Exception {
    if (ecmo == Mode.VV) {
      // VV ECMO
      if (patient.getHeartFunction() == Patient.HeartFunction.BAD) {
        // SvO2_VV_LGHP and SvO2_VV_LPHP
        throw new Exception("VV ECMO and bad heart function not ideal");
      }
      else { 
        // Heart function is good
        if (flow < 50) {
          return 0.1 + flow / 50 * 0.4;
        }
        else if (flow < 120) {
          return 0.5 + (flow - 50) / 70 * 0.28;
        }
        else if (flow < 150) {
          return 0.78 + (flow - 120) / 30 * 0.12;
        }
        else {
          return 0.9;
        }
      }
    }
    else { 
      // VA ECMO
      if (flow < 50) {
        return 0.1 + flow / 50 * 0.4;
      }
      else if (flow < 120) {
        return 0.5 + (flow - 50) / 70 * 0.25;
      }
      else if (flow < 150) {
        return 0.75 + (flow - 120) / 30 * 0.05;
      }
      else {
        return 0.8;
      }
    }
  }
  
  /**
   * calcOxygenSaturation calculates oxygen saturation from PaO2.
   * 
   * @param paO2
   *          partial pressure of oxygen in mmHg
   * @return saturation in form 0.00 (multiply by 100 to get %)
   */
  public static double calcOxygenSaturation(double paO2) {
    return(1 / ((23400 / (Math.pow(paO2, 3) + (150 * paO2))) + 1));
  }
  
  /**
   * calcPaO2 calculates partial pressure of oxygen from saturation.
   * 
   * @param spO2  Saturation of oxygen as form (0.00).
   * @param mode  VV or VA.
   * @return paO2 as mmHg
   */
  public static double calcPaO2(double spO2, Mode mode) {
    // From CubeEquation-Sat-PaO2.xls
    double pao2 = ((366.214 * Math.pow(spO2, 3)) - (455.117 * Math.pow(spO2, 2)) + (188.15 * spO2) - 2.609);
    
    if (spO2 == 1.0) { // To strictly follow Patient PaO2 excel sheet
      if (mode == Mode.VA) {
        pao2 += 100;
      }
      else {
        pao2 += 50;
      }
    }
    
    // and return PaO2
    return pao2;
  }
  
  /**
   * Check if venous pressure within range of normal for patient.
   * 
   * @param patient
   *          Patient with age and weight, etc.
   * @param venousPressure
   *          The current venous pressure of the system.
   * @return Boolean value if value in range
   */
  public static boolean isVenousPressureNormal(Patient patient,
                                               double venousPressure) {
    if ((venousPressure >= 0) && (venousPressure <= 20)) {
      return true;      
    }
    else {
      return false;
    }
  }
}