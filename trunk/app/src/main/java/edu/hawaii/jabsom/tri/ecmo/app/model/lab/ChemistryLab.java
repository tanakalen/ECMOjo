package edu.hawaii.jabsom.tri.ecmo.app.model.lab;

import king.lib.util.Translator;


/**
 * The chemistry lab test. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class ChemistryLab extends Lab {
  
  /** The Na (sodium) in mmol/l [100-180]. */
  private double na;
  /** The K (potassium) in mmol/l [0, 15]. */
  private double k;
  /** The ionized calcium in mmol/l [0.00, 2.00]. */
  private double ionCa;
  /** The glucose in mg/dl [0, 500]. */
  private double gluc;
  /** The lactate value in mmol/l [0, 5]. */
  private double lactate;
  
  
  /**
   * Returns the gluc.
   *
   * @return  The gluc.
   */
  public double getGluc() {
    return gluc;
  }

  /**
   * Sets the gluc.
   *
   * @param gluc  The gluc to set.
   */
  public void setGluc(double gluc) {
    this.gluc = gluc;
  }

  /**
   * Returns the ionized calcium.
   *
   * @return  The ionized calcium.
   */
  public double getIonCa() {
    return ionCa;
  }

  /**
   * Sets the ionized calcium.
   *
   * @param ionCa  The ionized calcium to set.
   */
  public void setIonCa(double ionCa) {
    this.ionCa = ionCa;
  }

  /**
   * Returns the K (potassium).
   *
   * @return  The K (potassium).
   */
  public double getK() {
    return k;
  }

  /**
   * Sets the K (potassium).
   *
   * @param k  The K (potassium) to set.
   */
  public void setK(double k) {
    this.k = k;
  }

  /**
   * Returns the lactate.
   *
   * @return  The lactate.
   */
  public double getLactate() {
    return lactate;
  }

  /**
   * Sets the lactate.
   *
   * @param lactate  The lactate to set.
   */
  public void setLactate(double lactate) {
    this.lactate = lactate;
  }

  /**
   * Returns the Na (sodium).
   *
   * @return  The Na (sodium).
   */
  public double getNa() {
    return na;
  }

  /**
   * Sets the Na (sodium).
   *
   * @param na  The Na (sodium) to set.
   */
  public void setNa(double na) {
    this.na = na;
  }

  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return Translator.getString("label.Chemistry[i18n]: Chemistry");
  }
}
