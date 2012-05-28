package edu.hawaii.jabsom.tri.ecmo.app.model;

import java.io.Serializable;

import king.lib.script.model.Script;

/**
 * The scenario file. 
 *
 * @author noblemaster
 * @since November 6, 2009
 */
public class ScenarioFile implements Serializable {

  /** The parameters. */
  private String parameters;
  /** The script. */
  private Script script;
  
  
  /**
   * Returns the parameters.
   *
   * @return  The parameters.
   */
  public String getParameters() {
    return parameters;
  }
  
  /**
   * Sets the parameters.
   *
   * @param parameters  The parameters.
   */
  public void setParameters(String parameters) {
    this.parameters = parameters;
  }
  
  /**
   * Returns the script.
   *
   * @return  The script.
   */
  public Script getScript() {
    return script;
  }
  
  /**
   * Sets the script.
   *
   * @param script  The script.
   */
  public void setScript(Script script) {
    this.script = script;
  }
}
