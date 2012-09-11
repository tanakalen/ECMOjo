package king.lib.access;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sun.media.sound.AiffFileReader;
import com.sun.media.sound.AuFileReader;
import com.sun.media.sound.WaveFileReader;
import king.lib.out.Error;

import java.io.IOException;
import java.io.InputStream;

//TODO: JavaFX imports
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;

/**
 * Plays a sound. Internally used by AudioLoader. Audio player is for short sound and looping sound.
 * Conversion to Java 7 FX will allow removal of libraries:
 * <ul>
 *   <li>jl1.0.jar</li>
 *   <li>mp3spi1.9.2.jar</li>
 *   <li>tritonus_share.jar</li>
 * </ul>
 * The following audio file formats are supported:
 * <ul>
 *   <li>wav</li>
 *   <li>aiff</li>
 *   <li>au</li>
 *   <li>mp3</li>
 *   <li>future: fxm, flv, mp4</li>
 * </ul>
 *
 * @author    king 
 * @since     March 23, 2004
 */
public final class AudioPlayer {

  /** The samples to play. */
  private AudioInputStream audioStream;
  /** The clip to play. */
  private Clip clip;
  
  /** True for looping. Defaults to false. */
  private boolean looping = false;
  /** The sampling speed. Defaults to 1.0 = normal. */
  private float speed = 1.0f;
  /** The playing flag. */
  private boolean playing = false;
    
  /**
   * Constructor for player.
   * 
   * @param audioStream   Where the audio data resides.
   */
  private AudioPlayer(AudioInputStream audioStream) {
    this.audioStream = audioStream;
  }
  
  /**
   * Returns a new audio player for the given path.
   * 
   * @param path  The path.
   * @return  The audio player.
   */
  public static AudioPlayer create(String path) {
    return AudioPlayer.create(path, ResourceHookup.getInstance());
  }
  
  /**
   * Returns a new audio player for the given path and hookup.
   * 
   * @param path  The path.
   * @param hookup  The hookup.
   * @return  The audio player.
   */
  public static AudioPlayer create(String path, Hookup hookup) {
    try {
      // open stream to sound file
      InputStream inputStream = hookup.getInputStream(path);
      
      //JavaFX Media and MediaPlayer
      //try {
      //  media = new Media(inputStream)
      //  try {
      //    mediaPlayer = new MediaPlayer(media)
      //  } catch (Exception MediaPlayerException) {
      //    // Handle exception in MediaPlayer constructor.
      //  }
      //} catch (Exception mediaException) {
      //  // Handle exception in Media constructor.
      //
      
      // *** HACK START
      // *** Only audioStream = AudioSystem.getAudioInputStream(inputStream); 
      // *** does not work, WAV files are not loaded correctly because of mp3spi1.9.2.jar
      AudioInputStream audioStream;
      if (path.endsWith(".mp3")) {
        audioStream = AudioSystem.getAudioInputStream(inputStream);
      }
      else if (path.endsWith(".wav")) {
        WaveFileReader reader = new WaveFileReader();
        audioStream = reader.getAudioInputStream(inputStream);
      }
      else if ((path.endsWith(".aif")) || (path.endsWith(".aiff"))) {
        AiffFileReader reader = new AiffFileReader();
        audioStream = reader.getAudioInputStream(inputStream);
      }
      else if (path.endsWith(".au")) {
        AuFileReader reader = new AuFileReader();
        audioStream = reader.getAudioInputStream(inputStream);
      }
      else {
        // the default!
        audioStream = AudioSystem.getAudioInputStream(inputStream);
      }
      // *** HACK END

      // get audio format
      AudioFormat audioFormat = audioStream.getFormat();

      // convert, so sound can be played
      if (path.endsWith(".mp3")) {
        if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
          AudioFormat newFormat = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED, 
            audioFormat.getSampleRate(),
            16,
            audioFormat.getChannels(),
            audioFormat.getChannels() * 2,
            audioFormat.getSampleRate(),
            false);
          AudioInputStream newStream = AudioSystem.getAudioInputStream(newFormat, audioStream);
          audioFormat = newFormat;
          audioStream = newStream;
        }      
      }
      // Create new player
      return new AudioPlayer(audioStream);
    }
    catch (UnsupportedAudioFileException e) {
      Error.out(e);
      return null;
    }
    catch (IOException e) {
      Error.out(e);
      return null;
    }
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
   * Plays a song.
   */
  public void play() {
    // Play the file
    if (audioStream == null) {
      return;
    }    
    try {
      if (clip == null) {
        clip = AudioSystem.getClip();
        clip.open(audioStream);
      }
      if (!playing) {
        if (looping) {
          clip.loop(Clip.LOOP_CONTINUOUSLY);
        } 
        else {
          clip.start();
        }
        playing = true;
      }
    }
    catch (LineUnavailableException e) {
      Error.out(e);
    }
    catch (IOException e) {
      Error.out(e);
    }
  }

  /**
   * Pauses a song.
   */
  public void pause() {
    // Pause
    if (clip != null) {
      if (clip.isRunning()) {
        clip.stop();
        playing = false;
      }
    }
  }

  /**
   * Stops playing a song.
   */
  public void stop() {
    // Stop
    if (clip != null) {
      if (clip.isRunning()) {
        clip.stop();
        playing = false;
        clip.setFramePosition(0);
      }
      clip.drain();
      clip.close();
    }
    clip = null;
  }
  
}
