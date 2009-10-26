package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import king.lib.access.AudioPlayer;
import king.lib.access.ImageLoader;
import king.lib.out.Error;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.AlarmIndicatorComponent;

/**
 * The component panel. 
 *
 * @author   Kin Lik Wang
 * @since    September 25, 2008
 */
public class AlarmIndicatorComponentPanel extends ComponentPanel implements Runnable {

  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedLarge.png");

  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackLarge.png");
  
  /** The green alert image. */
  private Image greenAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-GreenLarge.png");
  
  /** The component. */
  private AlarmIndicatorComponent component;

  /** The updater thread. */
  private Thread thread;
  
  /** The alarm audio. */
  private AudioPlayer alarmFx;
  
  /** The sound off button. */
  private ImageButton fxOffButton;
  /** The sound off label. */
  private JLabel fxOffLabel;
  /** True for turned off. */
  private boolean fxOff;
  
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  public AlarmIndicatorComponentPanel(AlarmIndicatorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(734, 7);
    setSize(64, 100);
    
    // set layout
    setLayout(null);
    
    // set the tooltip
    setToolTipText(component.getName());
    
    // add sound off button and icon
    Image fxOffButtonImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Reset.png");
    Image fxOffButtonRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ResetRol.png");
    Image fxOffButtonSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ResetSel.png");
    fxOffButton = new ImageButton(fxOffButtonImage, fxOffButtonRolloverImage, fxOffButtonSelectedImage);
    fxOffButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        alarmFx.pause();
        fxOff = true;
        fxOffButton.setVisible(false);
        fxOffLabel.setVisible(false);
      }
    });
    fxOffButton.setLocation(12, 45);
    fxOffButton.setSize(32, 32);
    add(fxOffButton);   
    fxOffButton.setVisible(false);
    Image soundOffImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Sound-Off.png");
    fxOffLabel = new JLabel(new ImageIcon(soundOffImage));
    fxOffLabel.setLocation(43, 53);
    fxOffLabel.setSize(16, 16);
    add(fxOffLabel);
    fxOffLabel.setVisible(false);
    fxOff = false;
    
    // the alarm audio player
    alarmFx = AudioPlayer.create("conf/sound/alarm.mp3");
    alarmFx.setLooping(true);
  }

  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    repaint();
  }

  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // start thread
    this.thread = new Thread(this);
    this.thread.setPriority(Thread.MIN_PRIORITY);
    this.thread.start();
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // stop thread
    this.thread = null;
    
    // remove the audio player
    alarmFx.stop();
    
    super.removeNotify();
  }
  
  /**
   * The time updater thread.
   */
  public void run() {
    Thread currentThread = Thread.currentThread();
    while (this.thread == currentThread) {
      // update
      repaint();
      
      // wait for next update
      try {
        Thread.sleep(50);
      }
      catch (InterruptedException e) {
        Error.out(e);
      }
    }
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  @Override
  public void paintComponent(Graphics g) {
    // draws the image
    if (component.isAlarm()) {
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 0, 0, this);
      }
      else {
        g.drawImage(blackAlertImage, 0, 0, this);        
      }
      
      // play alarm
      if (!fxOff) {
        alarmFx.play();
        fxOffButton.setVisible(true);
        fxOffLabel.setVisible(true);
      }
    }
    else {
      // draw green light
      g.drawImage(greenAlertImage, 0, 0, this);      
      
      // stop the alarm
      alarmFx.pause();
      fxOff = false;
    }
  }
  
  /**
   * Assigns the component to a group.
   * 
   * @param group  The group.
   */
  public void assign(ButtonGroup group) {
    // not needed for this component
  }  
}
