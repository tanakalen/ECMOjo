package edu.hawaii.jabsom.tri.ecmo.app.model.lab;

/**
 * The imaging lab test. 
 *
 * @author   king
 * @since    August 19, 2008
 */
public abstract class ImagingLabTest extends LabTest {

  /** The time in seconds relative to the start time. */
  private long time;
  
  /** The lab description. E.g. "Chest, X-Ray". */
  private String description;
  /** The image name. */
  private String imageName;

  
  /**
   * Returns the time.
   *
   * @return  The time in seconds relative to the start time.
   */
  public long getTime() {
    return time;
  }

  /**
   * Sets the time.
   *
   * @param time  The time in seconds relative to the start time.
   */
  public void setTime(long time) {
    this.time = time;
  }

  /**
   * Returns the description.
   *
   * @return  The description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description  The description to set.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Returns the image name.
   * 
   * @return the image name.
   */
  public String getImageName() {
    return imageName;
  }

  /**
   * Sets the image name. 
   * 
   * @param imageName the image name to set.
   */
  public void setImageName(String imageName) {
    this.imageName = imageName;
  }
}
