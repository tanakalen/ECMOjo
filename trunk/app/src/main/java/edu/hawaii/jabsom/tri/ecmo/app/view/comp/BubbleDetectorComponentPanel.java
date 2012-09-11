package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import king.lib.access.ImageLoader;
import king.lib.out.Error;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.BubbleDetectorComponent;

/**
 * The component panel. 
 *
 * @author   Kin Lik Wang
 * @since    September 25, 2008
 */
public class BubbleDetectorComponentPanel extends ComponentPanel implements Runnable {

  /** The rollover image. */
  private Image rolloverImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-BubbleDetectorRol.png");
  /** The selected image. */
  private Image selectedImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-BubbleDetectorSel.png");
  
  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedSmall.png");

  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackSmall.png");
  
  /** The component. */
  private BubbleDetectorComponent component;
  
  /** The selection button. */
  private AbstractButton selectionButton;  
  
  /** The updater thread. */
  private Thread thread;
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  public BubbleDetectorComponentPanel(BubbleDetectorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(428, 440);
    setSize(80, 80);
    
    // set layout
    setLayout(null);

    // add toggle button
    selectionButton = new ImageToggleButton(null, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(0, 0);
    selectionButton.setSize(80, 80);
    add(selectionButton);
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
    if (component.isAlarm()){
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 12, 48, this);
      }
      else {
        g.drawImage(blackAlertImage, 12, 48, this);        
      }      
    }    
  }
  
  /**
   * Assigns the component to a group.
   * 
   * @param group  The group.
   */
  public void assign(ButtonGroup group) {
    group.add(selectionButton);
  }
}
