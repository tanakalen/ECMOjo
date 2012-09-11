package edu.hawaii.jabsom.tri.ecmo.app.control.script;

import edu.hawaii.jabsom.tri.ecmo.app.model.Game;

/**
 * The context. 
 *
 * @author noblemaster
 * @since December 10, 2009
 */
public class Context {

  /** The game. */
  private Game game;
  /** The progress. */
  private Progress progress;
  /** The console. */
  private Console console;
  /** The notepad. */
  private Notepad notepad;
  /** The recorder. */
  private Recorder recorder;
  
  
  /**
   * The constructor.
   *
   * @param game  The game.
   * @param progress  The progress.
   * @param console  The console.
   * @param notepad  The notepad.
   * @param recorder  The recorder.
   */
  public Context(Game game, Progress progress, Console console, Notepad notepad, Recorder recorder) {
    this.game = game;
    this.progress = progress;
    this.console = console;
    this.notepad = notepad;
    this.recorder = recorder;
  }
  
  /**
   * Returns the game.
   *
   * @return  The game.
   */
  public Game getGame() {
    return game;
  }
  
  /**
   * Returns the progress information.
   * 
   * @return  The progress.
   */
  public Progress getProgress() {
    return progress;
  }

  /**
   * Returns the notepad to take notes.
   * 
   * @return  The notepad with the notes.
   */
  public Notepad getNotepad() {
    return notepad;
  }
  
  /**
   * Returns the console for debug output.
   * 
   * @return  The console.
   */
  public Console getConsole() {
    return console;
  }
  
  /**
   * Returns the recorder to record actions.
   * 
   * @return  The recorder.
   */
  public Recorder getRecorder() {
    return recorder;
  }
}
