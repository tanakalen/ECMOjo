package king.lib.access;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sun.media.sound.AiffFileReader;
import com.sun.media.sound.AuFileReader;
import com.sun.media.sound.WaveFileReader;
import king.lib.out.Error;
import java.io.IOException;
import java.io.InputStream;

//TODO: JavaFX imports
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;

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
 * @author king
 * @since March 23, 2004
 */
public final class MusicPlayer implements Runnable {

  /** The thread which plays the file. */
  private Thread thread;

  /** The path to the file. */
  private String path;
  /** The hookup to the file. */
  private Hookup hookup;
  /** The format of the audio file to play. */
  private AudioFormat audioFormat;
  /** The samples to play. */
  private AudioInputStream audioStream;

  /** The sampling speed. Defaults to 1.0 = normal. */
  private float speed = 1.0f;

  /** Temporary array for data to play. */
  private byte[] playData = new byte[4096];

  /** True for playing. */
  private boolean playing = false;
  /** True for pause. */
  private boolean pause = false;

  /**
   * Constructor for player.
   * 
   * @param path The path.
   * @param hookup The hookup.
   * @throws UnsupportedAudioFileException Handles unsupported audio file exception.
   * @throws IOException Handles IO exception.
   */
  private MusicPlayer(String path, Hookup hookup) throws UnsupportedAudioFileException, IOException {
    this.hookup = hookup;
    this.path = path;
    load();
  }

  /**
   * Returns a new audio player for the given path.
   * 
   * @param path The path.
   * @return The audio player.
   */
  public static MusicPlayer create(String path) {
    return MusicPlayer.create(path, ResourceHookup.getInstance());
  }

  /**
   * Returns a new audio player for the given path and hookup.
   * 
   * @param path The path.
   * @param hookup The hookup.
   * @return The audio player.
   */
  public static MusicPlayer create(String path, Hookup hookup) {
    // Create new player
    try {
      return new MusicPlayer(path, hookup);
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
   * Loads the music file.
   * 
   * @throws UnsupportedAudioFileException Handles unsupported audio file exception.
   * @throws IOException Handles IO exception.
   */
  public void load() throws UnsupportedAudioFileException, IOException {
    if (audioStream != null) {
      return;
    }
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
    audioFormat = audioStream.getFormat();

    // convert, so sound can be played
    if (path.endsWith(".mp3")) {
      if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
        AudioFormat newFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat.getSampleRate(), 16,
            audioFormat.getChannels(), audioFormat.getChannels() * 2, audioFormat.getSampleRate(), false);
        AudioInputStream newStream = AudioSystem.getAudioInputStream(newFormat, audioStream);
        audioFormat = newFormat;
        audioStream = newStream;
      }
    }
  }

  /**
   * Returns the speed.
   * 
   * @return The speed.
   */
  public float getSpeed() {
    return speed;
  }

  /**
   * Sets the speed.
   * 
   * @param speed The speed.
   */
  public void setSpeed(float speed) {
    this.speed = speed;
  }

  /**
   * Plays a song.
   */
  public void play() {
    // Thread which plays the file.
    if (this.thread == null) {
      this.thread = new Thread(this);
      this.thread.start();
    }
    this.pause = false;
  }

  /**
   * Pauses a song.
   */
  public void pause() {
    // Pause play thread
    this.pause = true;
  }

  /**
   * Stops playing a song.
   */
  public void stop() {
    // Stop play thread
    this.thread = null;
    
    // Create new player
    try {
      load();
    }
    catch (UnsupportedAudioFileException e) {
      Error.out(e);
    }
    catch (IOException e) {
      Error.out(e);
    }
  }

  /**
   * Main thread to play an audio file.
   */
  public void run() {
    if (this.audioStream == null) {
      // Stop play thread
      this.thread = null;
      return;
    }

    // wait for previous play to stop
    while (this.playing) {
      try {
        Thread.sleep(10);
      }
      catch (InterruptedException e) {
        Error.out(e);
      }
    }

    // play new
    this.playing = true;
    try {
      // open line to play to
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
      SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
      line.open(audioFormat);

      // write data to line and wait until finished
      line.start();

      // write data piece for piece
      Thread currentThread = Thread.currentThread();
      int bytesRead = 0;
      while ((this.thread == currentThread) && (bytesRead != -1) && !this.pause) {
        bytesRead = audioStream.read(playData, 0, playData.length);
        if (bytesRead != -1) {
          line.write(playData, 0, bytesRead);
        }
      }

      // end sound stream
      line.drain();
      line.close();
      line = null;
    }
    catch (LineUnavailableException e) {
      Error.out(e);
    }
    catch (IOException e) {
      Error.out(e);
    }

    // close player
    this.thread = null;
    this.playing = false;

    if (!this.pause) {
      // recycle
      try {
        this.audioStream.close();
      }
      catch (IOException e) {
        Error.out(e);
      }
      this.audioStream = null;
      this.audioFormat = null;
    }
  }
}