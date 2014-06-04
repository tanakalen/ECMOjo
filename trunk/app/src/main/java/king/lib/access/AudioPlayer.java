package king.lib.access;

import java.net.URL;

import javafx.scene.media.AudioClip;

/**
 * Plays a sound. Internally used by AudioLoader. Audio player is for short sound and looping sound.
 * Conversion to JavaFX will allow removal of libraries:
 * <ul>
 *   <li>jl1.0.jar</li>
 *   <li>mp3spi1.9.2.jar</li>
 *   <li>tritonus_share.jar</li>
 * </ul>
 * The following audio file formats are supported:
 * <ul>
 *   <li>wav</li>
 *   <li>aiff</li>
 *   <li>mp3</li>
 *   <li>fxm, flv, mp4</li>
 * </ul>
 *
 * @author    king 
 * @since     December 14, 2013
 */
public final class AudioPlayer {

  /** The JavaFX clip to play. */
  private AudioClip myClip;
  /** The JavaFX clip URL. */
  private URL url;
  
  /** True for looping. Defaults to false. */
  private boolean looping = false;
  /** The sampling speed. Defaults to 1.0 = normal. */
  private float speed = 1.0f;
  /** The playing flag. */
  private boolean playing = false;
  
  /**
   * Constructor for player using JavaFX.
   * 
   * @param url   Where the audio data resides.
   */
  private AudioPlayer(URL url) {
      this.url = url;
      this.myClip = new AudioClip(url.toExternalForm());
  }
  
  /**
   * Returns a new audio player for the given path.
   * 
   * @param path  The path.
   * @return  The audio player.
   */
  public static AudioPlayer create(String path) {
    ResourceHookup hookup = ResourceHookup.getInstance();
    URL url = hookup.getMedia(path);
    return new AudioPlayer(url);
  }
  
  /**
   * Returns the speed.
   *
   * @return  The speed.
   */
  public float getSpeed() {
    return speed;
  }

  /**
   * Sets the speed.
   *
   * @param speed  The speed.
   */
  public void setSpeed(float speed) {
    this.speed = speed;
  }
  
  /**
   * Returns true for looping.
   *
   * @return  True for looping.
   */
  public boolean isLooping() {
    return looping;
  }

  /**
   * Set true for looping.
   *
   * @param looping  True for looping.
   */
  public void setLooping(boolean looping) {
    this.looping = looping;
  }

  /**
   * Plays the sound.
   */
  public void play() {
    // Play the file
    if (!playing) {
      if (looping) {
        myClip.setCycleCount(AudioClip.INDEFINITE);
      } 
      playing = true;
      myClip.play();
    }
  }

  /**
   * Pauses a song.
   */
  public void pause() {
    // Pause
    if (playing) {
      myClip.stop();
      playing = false;
    }
  }

  /**
   * Stops playing a song.
   */
  public void stop() {
    // Stop
    if (playing) {
      myClip.stop();
      playing = false;
    }
  }
  
}
