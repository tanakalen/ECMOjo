package king.lib;

/**
 * Provides system release information such as version and release date/time.
 *
 * @author   king
 * @since    August 1, 2002
 */
public final class KingLibRelease {

  /** Release version value supplied during build process by Ant.  */
  private static String releaseVersion = "@release.version@-ECMO";
  /** Release time value supplied during build process by Ant.  */
  private static String releaseTime = "@release.time@";


  /**
   * Private constructor to disable class instantiation.
   */
  private KingLibRelease() {
    // to disable class instatiation which would be pointless
  }
  
  /**
   * Gets the release version of the program.
   *
   * @return   The release value.
   */
  public static String getReleaseVersion() {
    return releaseVersion;
  }

  /**
   * Gets the release time of the program.
   *
   * @return   The release value.
   */
  public static String getReleaseTime() {
    return releaseTime;
  }
}
