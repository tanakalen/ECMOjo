package edu.hawaii.jabsom.tri.ecmo.app.model.lab;

import king.lib.util.Translator;

/**
 * The Echo lab test. 
 *
 * @author   king
 * @since    June 26, 2009
 */
public class EchoLab extends ImagingLab {
  
  /**
   * Returns the name of the component.
   * 
   * @return  The name.
   */
  public String getName() {
    return Translator.getString("text.Echo[i18n]: Echo");
  }
}
