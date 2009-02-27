package edu.hawaii.jabsom.tri.ecmo.app.model.lab;


/**
 * The hematology lab test. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class HematologyLabTest extends LabTest {
  
  /** The Hct in percent / 100 [0.0, 1.0]. */
  private double hct;
  /** The Hgb in gm/dl [0.0, 50.0]. */
  private double hgb;
  /** The platelets [0, 1000]. */
  private double platelets;
  /** The WBC [0.0, 100.0]. */
  private double wbc;
  /** The PT in seconds [0, 50]. */
  private double pt;
  /** The PTT in seconds [0, 300]. */
  private double ptt;
  /** The fibrinogen in mg/dl [0, 1000]. */
  private double fibrinogen;
  /** The fsp (fibrin split products) in mcg/ml [0, 25]. */
  private double fsp;
  
  
  /**
   * Returns the fsp (fibrin split products).
   *
   * @return  The fsp (fibrin split products).
   */
  public double getFsp() {
    return fsp;
  }

  /**
   * Sets the fsp (fibrin split products).
   *
   * @param fsp  The fsp (fibrin split products) to set.
   */
  public void setFsp(double fsp) {
    this.fsp = fsp;
  }

  /**
   * Returns the fibrinogen.
   *
   * @return  The fibrinogen.
   */
  public double getFibrinogen() {
    return fibrinogen;
  }

  /**
   * Sets the fibrinogen.
   *
   * @param fibrinogen  The fibrinogen to set.
   */
  public void setFibrinogen(double fibrinogen) {
    this.fibrinogen = fibrinogen;
  }

  /**
   * Returns the Hct.
   *
   * @return  The Hct.
   */
  public double getHct() {
    return hct;
  }

  /**
   * Sets the Hct.
   *
   * @param hct  The Hct to set.
   */
  public void setHct(double hct) {
    this.hct = hct;
  }

  /**
   * Returns the Hgb.
   *
   * @return  The Hgb.
   */
  public double getHgb() {
    return hgb;
  }

  /**
   * Sets the Hgb.
   *
   * @param hgb  The Hgb to set.
   */
  public void setHgb(double hgb) {
    this.hgb = hgb;
  }

  /**
   * Returns the platelets.
   *
   * @return  The platelets.
   */
  public double getPlatelets() {
    return platelets;
  }

  /**
   * Sets the platelets.
   *
   * @param platelets  The platelets to set.
   */
  public void setPlatelets(double platelets) {
    this.platelets = platelets;
  }

  /**
   * Returns the PT.
   *
   * @return  The PT.
   */
  public double getPt() {
    return pt;
  }

  /**
   * Sets the PT.
   *
   * @param pt  The PT to set.
   */
  public void setPt(double pt) {
    this.pt = pt;
  }

  /**
   * Returns the PTT.
   *
   * @return  The PTT.
   */
  public double getPtt() {
    return ptt;
  }

  /**
   * Sets the PTT.
   *
   * @param ptt  The PTT to set.
   */
  public void setPtt(double ptt) {
    this.ptt = ptt;
  }

  /**
   * Returns the WBC.
   *
   * @return  The WBC.
   */
  public double getWbc() {
    return wbc;
  }

  /**
   * Sets the WBC.
   *
   * @param wbc  The WBC to set.
   */
  public void setWbc(double wbc) {
    this.wbc = wbc;
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return "Hematology";
  }
}
