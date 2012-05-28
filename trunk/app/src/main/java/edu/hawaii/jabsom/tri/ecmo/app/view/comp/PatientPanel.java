package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

import king.lib.access.ImageLoader;
import king.lib.out.Error;

import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.Patient.Species;

/**
 * The patient panel. 
 *
 * @author   kinwang
 * @since    November 17, 2008
 */
public class PatientPanel extends ComponentPanel implements Runnable {

  /** The patient image. */
  private Image patientImage;
  /** The rollover image. */
  private Image patientRolImage;
  /** The selected image. */
  private Image patientSelImage;
  /** The patient blue image. */
  private Image patientBlueImage;
  
  /** The dripping blood image. */
  private Image drippingBloodImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-Blood.png");
  /** The dripping blood time. */
  private long[] elapsedTime = new long[3];
  /** The dripping blood start flag. */
  private boolean[] startFlag = new boolean[3];
  /** The dripping blood random flag. */
  private boolean[] randomFlag = new boolean[3]; 
  /** The last update in nano second. */
  private long lastUpdate;
  /** The updater thread. */
  private Thread thread;

  /** The selection button. */
  private AbstractButton selectionButton;

  
  /**
   * Default constructor.
   * 
   * @param patient  The patient.
   * @param name  The name.
   * @param description  The description.
   */
  public PatientPanel(Patient patient, String name, String description) {
    super(patient);
    
    // set transparent
    setOpaque(false);
    
    // set size and location
    setLocation(16, 332);
    setSize(256, 256);
    
    // set layout
    setLayout(null);
    
    // setup blood dripping
    lastUpdate = 0;
    for (int i = 0; i < 3; i++) {
      elapsedTime[i] = 0;
      startFlag[i] = false;
      randomFlag[i] = false;
    }

    // load the images
    String species = "Baby";
    if (patient.getSpecies() == Species.PIG) {
      // species = "Pig";
    }
    patientImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Patient-" + species + ".png");
    patientRolImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Patient-" + species + "Rol.png");
    patientSelImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Patient-" + species + "Sel.png");
    patientBlueImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Patient-" + species + "Blue.png");

    // add toggle button
    selectionButton = new ImageToggleButton(null, patientRolImage, patientSelImage, patientSelImage);
    selectionButton.setToolTipText("<html><table><tr><td width=\"250\" valign=\"top\"><b>" 
        + name + "</b><br>" 
        + description + "</td></tr></table></html>");
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(33, 46);
    selectionButton.setSize(190, 165);
    add(selectionButton); 
  }

  /**
   * Returns the patient.
   * 
   * @return  The patient.
   */
  public Patient getPatient() {
    return (Patient)getComponent();
  }
  
  /**
   * Sets the patient.
   * 
   * @param patient  The patient.
   */
  public void setPatient(Patient patient) {
    setComponent(patient);
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

    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // draw base
    g2.drawImage(patientImage, 0, 0, this);

    // do blue overlay
    Patient patient = getPatient();
    float alpha = (float)(0.85 - patient.getO2Saturation()) * 2;
    if (alpha < 0.0f) {
      alpha = 0.0f;
    }
    else if (alpha > 1.0f) {
      alpha = 1.0f;
    }
    
    Composite oldComposite = g2.getComposite();
    Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
    g2.setComposite(alphaComposite);
    g2.drawImage(patientBlueImage, 0, 0, this);
    g2.setComposite(oldComposite);   
    
    // draw blood dripping as needed
    if (patient.isBleeding()) {
      int[] positions = new int[3];
      
      // draws the dripping blood
      for (int i = 0; i < 3; i++) {
        if (elapsedTime[i] > 0) {
          positions[i] = (int) (0.00000001 * elapsedTime[i] * 0.00000001 * elapsedTime[i]);
          g.drawImage(drippingBloodImage, 125, 110 + positions[i], this);
          if (positions[i] > 98) {
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
