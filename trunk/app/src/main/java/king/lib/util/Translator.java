package king.lib.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import king.lib.out.Error;

/**
 * The translator.
 * 
 * @author noblemaster
 * @since November 2, 2009
 */
public final class Translator {
  
  /** The resource bundle. */
  private static ResourceBundle bundle = null;
  
  
  /**
   * The constructor is private to prevent instantiation.
   */
  private Translator() {
    // not used
  }

  /**
   * Returns the bundle.
   *
   * @return  The bundle.
   */
  public static ResourceBundle getBundle() {
    return bundle;
  }

  /**
   * Sets the bundle.
   *
   * @param bundle  The bundle.
   */
  public static void setBundle(ResourceBundle bundle) {
    Translator.bundle = bundle;
  }

  /**
   * Returns the message for the given key.
   * <p>
   * Example: Translator.translate("label.Greeting");<br>
   * Example: Translator.translate("label.Greeting[i18n]: Hello World!");
   * <p>
   * Common prefixes:
   * <ul>
   *   <li>label.*    for labels (exclude trailing ':').
   *   <li>action.*   for action such as buttons.
   *   <li>title.*    for titles and headers.
   *   <li>text.*     for texts which end with dot, question or exclamation mark.
   *   <li>error.*    for error related texts.
   * </ul>
   * 
   * @param key  The key. E.g. "label.Housing".
   * @return  The message.
   */
  public static String getString(String key) {
    String value = key;
    try {
      if (key.matches("(?s)([a-z]+\\.[A-Za-z0-9_]+)(\\[i18n\\]: )(.*)")) {
        // get the correct parts
        value = key.substring(key.indexOf(":") + 2);
        key = key.substring(0, key.indexOf("["));
      }
      if (bundle != null) {
        return bundle.getString(key);
      }
      else {
        return value;
      }
    }
    catch (MissingResourceException e) {
      // returns the value, if resource cannot be found!
      Error.out("Missing resource for key: " + key);
      return value;
    }
  }

  /**
   * Returns the message for the given key and arguments.
   * <p>
   * Example: Translator.translate("label.PersonalGreeting", "Master");<br>
   * Example: Translator.translate("label.PersonalGreeting[i18n]: Hello {0}!", "Master");
   * <p>
   * Common prefixes:
   * <ul>
   *   <li>label.*    for labels (exclude trailing ':').
   *   <li>action.*   for action such as buttons.
   *   <li>title.*    for titles and headers.
   *   <li>text.*     for texts which end with dot, question or exclamation mark.
   *   <li>error.*    for error related texts.
   * </ul>
   * 
   * @param key  The key. E.g. "label.Housing".
   * @param arguments  The arguments.
   * @return  The message.
   */
  public static String getString(String key, Object... arguments) {
    return MessageFormat.format(getString(key), arguments);
  }
}