package edu.hawaii.jabsom.tri.ecmo.app;

import king.lib.access.Access;
import king.lib.access.AccessException;
import king.lib.access.ImageLoader;

import java.awt.Color;
import javax.swing.JApplet;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import king.lib.out.Error;
import king.lib.out.ErrorOutputUnit;
import king.lib.out.FileOutputUnit;
import king.lib.out.Info;
import king.lib.out.MultiOutputUnit;

import java.awt.BorderLayout;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;

//import edu.hawaii.jabsom.tri.ecmo.app.gui.plaf.GameLookAndFeel;
import edu.hawaii.jabsom.tri.ecmo.app.state.LoadState;
import edu.hawaii.jabsom.tri.ecmo.app.state.StateMachine;

/**
 * Creates an application. 
 *
 * @author   king
 * @since    January 10, 2007
 */
public final class ECMOApp extends JApplet {

  /** The panel. */
  private static ECMOPanel panel = null;

  
  /**
   * Initializes the app with the given args.
   * 
   * @param args  The args used for initialization.
   */
  private static void initialize(String[] args) {
    // set info/error log
    try {
      Calendar now = Calendar.getInstance();
      SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
      String path = Access.getInstance().getScenarioDir() + "/logs/Log-"
                  + timestampFormatter.format(now.getTime()) + ".txt";
      FileOutputUnit logOutput = new FileOutputUnit(path, false);
      logOutput.out("# " + timestampFormatter.format(now.getTime()) + "\n");
      MultiOutputUnit multiOutput = new MultiOutputUnit();
      multiOutput.add(new ErrorOutputUnit());
      multiOutput.add(logOutput);
      Error.setOutputUnit(multiOutput);
      Info.setOutputUnit(logOutput);
    }
    catch (IOException e) {
      System.err.println("Error setting up log file: " + e);
    }
    
    // set look and feel
    try {
      //UIManager.setLookAndFeel(new GameLookAndFeel());
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // lt_add: broken mac LAF
    }
    catch (UnsupportedLookAndFeelException e) {
      Error.out(e);
    }
    catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // load configuration
    Configuration.init();   

    // create state machine
    StateMachine stateMachine = new StateMachine();

    // create components
    panel = new ECMOPanel(stateMachine);

    // init state machine
    stateMachine.init(new LoadState());
  }
  
  /** Inits the applet. */
  public void init() {
    try {
      Access.init(this);
    }
    catch (AccessException e) {
      Error.out(e);
    }
    
    initialize(getParameter("args").split(" "));

    // set layout
    getContentPane().setBackground(Color.BLACK);
    getContentPane().setLayout(new BorderLayout());
    
    // add panel
    getContentPane().add(panel, BorderLayout.CENTER);
  }
  
  /**
   * Main method.
   * 
   * @param args  The arguments
   */
  public static void main(String[] args) {
    try {
     Access.init();
    }
    catch (AccessException e) {
      Error.out(e);
    }
    
    // init
    initialize(args);
    
    // window
    JFrame window = new JFrame();
    window.setTitle("ECMOjo");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setIconImage(ImageLoader.getInstance().getImage("conf/logo/window-icon.gif"));
    
    // set layout
    window.getContentPane().setBackground(Color.BLACK);
    window.getContentPane().setLayout(new BorderLayout());
    
    // add panel
    window.getContentPane().add(panel, BorderLayout.CENTER);
    
    // and show
    window.setResizable(false);
    window.pack();
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }
}
