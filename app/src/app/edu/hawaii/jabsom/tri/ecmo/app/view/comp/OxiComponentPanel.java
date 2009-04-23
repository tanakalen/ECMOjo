package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
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
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxigenatorComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.OxigenatorComponent.OxiType;

/**
 * The component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class OxiComponentPanel extends ComponentPanel implements Runnable {

  /** The oxi image. */
  private Image oxiImage;
  /** The oxigenator broken image. */
  private Image brokenImage;

  /** The rollover image. */
  private Image rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-OxiRol.png");
  /** The selected image. */
  private Image selectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-OxiSel.png");

  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedSmall.png");

  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackSmall.png");

  /** The top bar image. */
  private Image topBarImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Bar-Top.png");

  /** The middle bar image. */
  private Image middleBarImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Bar-Middle.png");
  
  /** The bottom bar image. */
  private Image bottomBarImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Bar-Bottom.png");
  
  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);
  
  /** The component. */
  protected OxigenatorComponent component;
  
  /** The selection button. */
  private AbstractButton selectionButton;
  
  /** The updater thread. */
  private Thread thread;
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected OxiComponentPanel(OxigenatorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(530, 303);
    setSize(270, 190);
    
    // set layout
    setLayout(null);
    
    // load images
    if (component.getOxiType() == OxiType.QUADROX_D) {
      oxiImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-QuadroxDOxigenator.png");
      brokenImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-QuadroxDOxigenatorBroken.png");
    }
    else {
      oxiImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-SciMedOxigenator.png");
      brokenImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-SciMedOxigenatorBroken.png");
    }
    
    // add toggle button
    selectionButton = new ImageToggleButton(null, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(125, 28);
    selectionButton.setSize(121, 163);
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

    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // draws the image
    if (component.getOxiType() == OxiType.QUADROX_D) {
      g.drawImage(oxiImage, 13, 0, this);
    }
    else {
      g.drawImage(oxiImage, 5, 33, this);
    }
    
    if (component.isBroken()) {
      // draws the broken image
      if (component.getOxiType() == OxiType.QUADROX_D) {
        g.drawImage(brokenImage, 13, 0, this);
      }
      else {
        g.drawImage(brokenImage, 5, 33, this);
      }
    }
    
    // 0-10 bar
    g.drawImage(bottomBarImage, 197, 137, this);    
    int co2barTopHeight = 0;  // TODO: alex (int) (component.getCo2Flow() * 150 * 0.38);
    for (int i = 0; i < (co2barTopHeight - 2); i++) {
      g.drawImage(middleBarImage, 197, 136 - i, this); 
    }  
    g.drawImage(topBarImage, 197, 135 - co2barTopHeight, this);
    
    // O2 bar
    g.drawImage(bottomBarImage, 214, 137, this);
    int o2barTopHeight = 0;   // TODO: alex (int) (component.getO2Flow() * 15 * 0.38);
    for (int i = 0; i < (o2barTopHeight - 2); i++) {
      g.drawImage(middleBarImage, 214, 136 - i, this);    
    }
    g.drawImage(topBarImage, 214, 135 - o2barTopHeight, this);
    
    if (component.isAlarm()){
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 178, 77, this);
      }
      else {
        g.drawImage(blackAlertImage, 178, 77, this);        
      }      
    } 
    
    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 15f));
    
    // draw text
    String text = Math.round(component.getFiO2() * 100) + "%";
    g.drawString(text, 148, 120);
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