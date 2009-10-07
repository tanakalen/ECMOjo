package edu.hawaii.jabsom.tri.ecmo.app.control;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import edu.hawaii.jabsom.tri.ecmo.app.model.Game;

/**
 * The manager. 
 *
 * @author   king
 * @since    August 13, 2008
 */
public class Manager implements Runnable {

  /** Listener for  changes. */
  public static interface ManagerListener {
    
    /**
     * Called when the game is finished.
     */
    void goalReached();
  };
  
  /** Listeners for changes. */
  private List<ManagerListener> listeners = new ArrayList<ManagerListener>();

  /** The game. */
  private Game game;
  
  /** The updater thread. */
  private Thread thread;
  /** True if playing. */
  private boolean play;
  
  /** The actions inputted by a user. */
  private List<Action> actions = new ArrayList<Action>();
  
  
  /**
   * Constructor for the game updater.
   * 
   * @param game  The game updater.
   */
  public Manager(Game game) {
    this.game = game;
    this.play = false;
  }
  
  /**
   * Starts the updater.
   */
  public void start() {
    this.thread = new Thread(this);
    this.thread.start();
  }
  
  /** 
   * Stops the updater.
   */
  public void stop() {
    this.thread = null;
  }
  
  /**
   * Returns the game associated.
   * 
   * @return  The game associated.
   */
  public Game getGame() {
    return game;
  }
  
  /**
   * Start playing.
   */
  public void play() {
    this.play = true;
  }
  
  /**
   * Pause.
   */
  public void pause() {
    this.play = false;
  }
  
  /**
   * Returns true for paused.
   * 
   * @return  True for pause.
   */
  public boolean isPaused() {
    return !this.play;
  }
  
  /**
   * Executes the action.
   * 
   * @param action  The action to execute.
   */
  public void execute(Action action) {
    synchronized(actions) {
      actions.add(action);
    }
  }
  
  /**
   * The main updater method.
   */
  public void run() {
    long increment = 20;
    long nextLoop = (System.nanoTime() / 1000000) + increment;
    History history = new History();
    
//    // initialize game views
//    ACTRequestAction actact = new ACTRequestAction();
//    actact.execute(game);
    
    Thread currentThread = Thread.currentThread();
    while (this.thread == currentThread) {
      // execute if in play mode
      if (play) {        
        // store previous values
        Updater.store(game, history);
        
        // execute actions (that the user inputted)
        synchronized(actions) {
          for (Action action: actions) {
            action.execute(game); 
            
            // store action
            game.getActions().add(action);
            
            // pass on to goal
            game.getGoal().handle(game, action);
          }
          actions.clear();
        }

        // update the game (equipment and patient)
        if (Updater.execute(game, history, increment)) {
          play = false;
          notifyGoalReached();
        }
      }
      
      // and pause a while
      try {
        long sleep = nextLoop - (System.nanoTime() / 1000000);
        if (sleep > 0) {
          Thread.sleep(sleep);
        }
        nextLoop += increment;
      }
      catch (InterruptedException e) {
        throw new IllegalThreadStateException(e.getMessage());
      }
    }
  }
  
  /**
   * Adds a listener.
   * 
   * @param listener  The listener to add.
   */
  public void addManagerListener(ManagerListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeManagerListener(ManagerListener listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all the listeners.
   */
  private void notifyGoalReached() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        for (ManagerListener listener: listeners) {
          listener.goalReached();
        }
      }
    });
  }
}
