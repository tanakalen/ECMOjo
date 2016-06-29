package edu.hawaii.jabsom.tri.ecmo.app.model.lab;

import king.lib.util.Translator;

/**
 * The X-Ray lab test. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public class XRayLab extends ImagingLab {
  
  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return Translator.getString("text.XRay[i18n]: X-Ray");
  }
}
