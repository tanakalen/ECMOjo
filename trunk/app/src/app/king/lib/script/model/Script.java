package king.lib.script.model;

import java.io.Serializable;

/**
 * A script.
 *
 * @author  noblemaster
 * @since  October 21, 2009
 */
public class Script implements Serializable {

  /** The ID. 0 for invalid. */
  private long id;
  
  /** The language, such as Pnuts, Java, JavaScript etc. */
  private String lang;
  /** The source code. */
  private String code;
  
  
  /**
   * Returns the ID.
   *
   * @return  The ID. 0 for invalid.
   */
  public long getId() {
    return id;
  }
  
  /**
   * Sets the ID.
   *
   * @param id  The ID. 0 for invalid.
   */
  public void setId(long id) {
    this.id = id;
  }
  
  /**
   * Returns the language.
   *
   * @return  The language, such as Pnuts, Java, JavaScript etc.
   */
  public String getLang() {
    return lang;
  }
  
  /**
   * Sets the language.
   *
   * @param lang  The language, such as Pnuts, Java, JavaScript etc.
   */
  public void setLang(String lang) {
    this.lang = lang;
  }
  
  /**
   * Returns the source code.
   *
   * @return  The source code.
   */
  public String getCode() {
    return code;
  }
  
  /**
   * Sets the source code.
   *
   * @param code  The source code.
   */
  public void setCode(String code) {
    this.code = code;
  }
}
