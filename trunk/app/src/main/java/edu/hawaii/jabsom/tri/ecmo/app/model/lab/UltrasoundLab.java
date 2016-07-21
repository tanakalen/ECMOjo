package edu.hawaii.jabsom.tri.ecmo.app.model.lab;

import king.lib.util.Translator;

/**
 * The Ultrasound lab test. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class UltrasoundLab extends ImagingLab {
  
  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return Translator.getString("text.Ultrasound[i18n]: Ultrasound");
  }
}
