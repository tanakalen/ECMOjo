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
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.InterventionComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.engage.InterventionLocation;

/**
 * The intervention component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 30, 2008
 */
public class InterventionComponentPanel extends ComponentPanel implements Runnable {

  /** The selection button. */
  private AbstractButton selectionButton;
  
  /** The rollover image. */
  private Image rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-InterventionRol.png");
  /** The selected image. */
  private Image selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-InterventionSel.png");
  
  /** The dripping blood image. */
  private Image drippingBloodImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-Blood.png");
  /** The dripping blood time. */
  private long[] elapsedTime = new long[3];
  /** The dripping blood start flag. */
  private boolean[] startFlag = new boolean[3];
  /** The dripping blood random flag. */
  private boolean[] randomFlag = new boolean[3]; 
  /** The start x. */
  private int dripX0;
  /** The start y. */
  private int dripY0;
  /** The lenght. */
  private int dripLength;
  /** The last update in nano second. */
  private long lastUpdate;
  /** The updater thread. */
  private Thread thread;

  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected InterventionComponentPanel(InterventionComponent component) {
    super(component);

    // set transparent
    setOpaque(false);
    
    // set layout
    setLayout(null);
    
    // setup blood dripping
    lastUpdate = 0;
    for (int i = 0; i < 3; i++) {
      elapsedTime[i] = 0;
      startFlag[i] = false;
      randomFlag[i] = false;
    }

    // determine parameters
    InterventionLocation interventionLocation = component.getInterventionLocation();    
    if (interventionLocation == InterventionLocation.PATIENT) {   
      setLocation(116, 364);  
      dripX0 = 25;
      dripY0 = 80;
      dripLength = 95;
    }
    else if (interventionLocation == InterventionLocation.BEFORE_PUMP) {
      setLocation(336, 484);
      dripX0 = 185;
      dripY0 = 76;
      dripLength = 45;
    }
    else if (interventionLocation == InterventionLocation.BEFORE_OXYGENATOR) {
      setLocation(452, 428);
      dripX0 = 97;
      dripY0 = 51;
      dripLength = 120;
    }
    else if (interventionLocation == InterventionLocation.AFTER_OXYGENATOR) {
      setLocation(339, 348);
      dripX0 = 185;
      dripY0 = 76;
      dripLength = 178;
    }
    else {
      throw new RuntimeException("Intervention location not defined: " + interventionLocation);
    }
    setSize(200, 178);
    
    // add toggle button
    selectionButton = new ImageToggleButton(null, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(176, 0);
    selectionButton.setSize(24, 72);
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
    super.paintComponent(g);

    // draw blood dripping as needed
    InterventionComponent component = (InterventionComponent)getComponent();
    if (component.isCrackedPigtail()) {
      int[] positions = new int[3];
      
      // draws the dripping blood
      for (int i = 0; i < 3; i++) {
        if (elapsedTime[i] > 0) {
          positions[i] = (int) (0.00000001 * elapsedTime[i] * 0.00000001 * elapsedTime[i]);
          g.drawImage(drippingBloodImage, dripX0, dripY0 + positions[i], this);
          if (positions[i] > dripLength - 8) {
            elapsedTime[i] = 0;
            startFlag[i] = false;
          }
        }
      }
      long currentUpdate = System.nanoTime();
      long delta = currentUpdate - lastUpdate;
      if (lastUpdate > 0) {
        for (int i = 0; i < 3; i++) {
          if (startFlag[i]) {
            elapsedTime[i] += delta;
          }
          else {
            // random start the flag
            if ((((System.nanoTime()) / 200000000) % 2) == 0) {
              // run one time
              if (randomFlag[i]) {
                if (Math.random() > 0.5) {
                  startFlag[i] = true;
                }
              }
              randomFlag[i] = false;
            } 
            else {
              randomFlag[i] = true;  
            }
          }
        }
      }
      lastUpdate = currentUpdate;
    }
    else {
      lastUpdate = 0;
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
