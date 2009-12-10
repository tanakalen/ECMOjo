package edu.hawaii.jabsom.tri.ecmo.app.control.script;

import java.util.HashMap;
import java.util.Map;

/**
 * The notepad implementation for scripting. 
 *
 * @author noblemaster
 * @since December 10, 2009
 */
public class Notepad {

  /** The hash map. */
  private Map<String, String> notes = new HashMap<String, String>();
  
  
  /**
   * Returns the value for the 'null' key.
   * 
   * @return  The value or null if not set.
   */
  public String get() {
    return get(null);
  }
  
  /**
   * Sets the value for the 'null' key.
   * 
   * @param value  The value or null.
   */
  public void set(String value) {
    set(null, value);
  }
  
  /**
   * Returns the value for a key.
   * 
   * @param key  The key.
   * @return  The value or null if not found.
   */
  public String get(String key) {
    return notes.get(key);
  }
  
  /**
   * Sets a value for a key.
   * 
   * @param key  The key.
   * @param value  The value or null to remove the key.
   */
  public void set(String key, String value) {
    if (value == null) {
      notes.remove(key);
    }
    else {
      notes.put(key, value);
    }
  }
}
