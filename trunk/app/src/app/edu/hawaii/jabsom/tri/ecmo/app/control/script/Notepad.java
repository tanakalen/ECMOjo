package edu.hawaii.jabsom.tri.ecmo.app.control.script;

/**
 * The notepad for scripting. 
 *
 * @author noblemaster
 * @since December 10, 2009
 */
public interface Notepad {

  /**
   * Returns the value for the 'null' key.
   * 
   * @return  The value or null if not set.
   */
  String get();
  
  /**
   * Sets the value for the 'null' key.
   * 
   * @param value  The value or null.
   */
  void set(String value);
  
  /**
   * Returns the value for a key.
   * 
   * @param key  The key.
   * @return  The value or null if not found.
   */
  String get(String key);
  
  /**
   * Sets a value for a key.
   * 
   * @param key  The key.
   * @param value  The value or null to remove the key.
   */
  void set(String key, String value);
}
