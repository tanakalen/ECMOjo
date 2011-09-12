package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.SwingUtilities;

import king.lib.access.ImageLoader;
import king.lib.out.Error;
import king.lib.util.Translator;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.TubeAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.TubeAction.Location;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageToggleButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.TubeComponent.Mode;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogOption;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.StandardDialog.DialogType;

/**
 * The tube component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class TubeComponentPanel extends ComponentPanel implements Runnable {

  /** The panel image. */
  private Image image = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-Tube.png");

  /** The rollover non toggle clamp down image. */
  private Image rolloverNonToggleClampDownImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ClampDownRolNonToggle.png");
  /** The rollover toggle clamp down image. */
  private Image rolloverToggleClampDownImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ClampDownRolToggle.png");
  /** The selected clamp down image. */
  private Image selectedClampDownImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ClampDownSel.png");
  
  /** The rollover non toggle clamp image. */
  private Image rolloverNonToggleClampImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ClampRolNonToggle.png");
  /** The rollover toggle clamp image. */
  private Image rolloverToggleClampImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ClampRolToggle.png");
  /** The selected clamp image. */
  private Image selectedClampImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ClampSel.png");
 
  /** The rollover non toggle clamp up image. */
  private Image rolloverNonToggleClampUpImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ClampUpRolNonToggle.png");
  /** The rollover toggle clamp up image. */
  private Image rolloverToggleClampUpImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ClampUpRolToggle.png");
  /** The selected clamp up image. */
  private Image selectedClampUpImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ClampUpSel.png");
  
  /** The bubble dot 1. */
  private Image bubbleDot1Image = ImageLoader.getInstance().getImage("conf/image/interface/game/Icon-BubbleDot1.png");
  /** The bubble dot 2. */
  private Image bubbleDot2Image = ImageLoader.getInstance().getImage("conf/image/interface/game/Icon-BubbleDot2.png");
  /** The bubble dot 3. */
  private Image bubbleDot3Image = ImageLoader.getInstance().getImage("conf/image/interface/game/Icon-BubbleDot3.png");
  /** The bubble rect 1. */
  private Image bubbleRect1Image = ImageLoader.getInstance().getImage("conf/image/interface/game/Icon-BubbleRect1.png");
  /** The bubble rect 2. */
  private Image bubbleRect2Image = ImageLoader.getInstance().getImage("conf/image/interface/game/Icon-BubbleRect2.png");
  /** The bubble rect 3. */
  private Image bubbleRect3Image = ImageLoader.getInstance().getImage("conf/image/interface/game/Icon-BubbleRect3.png");
  
  /** The tube image. */
  private Image tubeImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-VATube.png");
  /** The venous image. */
  private Image venousImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-VATubeVenous.png");

  /** The component. */
  protected TubeComponent component;
  
  /** The selection clamp button 1. */
  private AbstractButton selectionClampButton1;
  /** The selection clamp button 2. */
  private AbstractButton selectionClampButton2;
  /** The selection clamp button 3. */
  private AbstractButton selectionClampButton3;
  /** The selection clamp button 4. */
  private AbstractButton selectionClampButton4;
  /** The selection clamp button 5. */
  private AbstractButton selectionClampButton5;

  /** The selection button. */
  private AbstractButton selectionButton;

  /** The updater thread. */
  private Thread thread;

  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected TubeComponentPanel(TubeComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(113, 368);
    setSize(533, 235);
        
    // set layout
    setLayout(null);
    
    // load tubing images
    if (component.getMode() == Mode.VA) {
      tubeImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-VATube.png");
      venousImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-VATubeVenous.png");
    }
    else {
      tubeImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-VVTube.png");
      venousImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Comp-VVTubeVenous.png");
    }
    
    // Arterial B
    selectionClampButton1 = new ImageToggleButton(null, rolloverNonToggleClampDownImage
                                                      , rolloverToggleClampDownImage
                                                      , selectedClampDownImage);
    selectionClampButton1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        TubeAction action = new TubeAction();
        action.setLocation(Location.ARTERIAL_B);
        action.setOpen(!selectionClampButton1.isSelected());
        notifyActionListeners(action);
      }
    });
    selectionClampButton1.setLocation(182, 72);
    selectionClampButton1.setSize(32, 52);
    selectionClampButton1.setSelected(!component.isArterialBOpen());
    add(selectionClampButton1); 

    // Arterial A
    selectionClampButton2 = new ImageToggleButton(null, rolloverNonToggleClampDownImage
                                                      , rolloverToggleClampDownImage
                                                      , selectedClampDownImage);
    selectionClampButton2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        StandardDialog.showDialog(SwingUtilities.getRootPane(selectionClampButton2), DialogType.WARNING, DialogOption.OK
            , "Clamp Placement", "Dr. Ogino says: don't add a clamp there!");
        selectionClampButton2.setSelected(false);
      }
    });
    selectionClampButton2.setLocation(262, 72);
    selectionClampButton2.setSize(32, 52);
    add(selectionClampButton2); 

    // Bridge
    selectionClampButton3 = new ImageToggleButton(null, rolloverNonToggleClampImage
                                                      , rolloverToggleClampImage
                                                      , selectedClampImage);
    selectionClampButton3.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        TubeAction action = new TubeAction();
        action.setLocation(Location.BRIDGE);
        action.setOpen(!selectionClampButton3.isSelected());
        notifyActionListeners(action);
      }
    });
    selectionClampButton3.setLocation(235, 130);
    selectionClampButton3.setSize(52, 32);
    selectionClampButton3.setSelected(!component.isBridgeOpen());
    add(selectionClampButton3); 
 
    // Venous B
    selectionClampButton4 = new ImageToggleButton(null, rolloverNonToggleClampUpImage
                                                      , rolloverToggleClampUpImage
                                                      , selectedClampUpImage);
    selectionClampButton4.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        TubeAction action = new TubeAction();
        action.setLocation(Location.VENOUS_B);
        action.setOpen(!selectionClampButton4.isSelected());
        notifyActionListeners(action);
      }
    });
    selectionClampButton4.setLocation(202, 183);
    selectionClampButton4.setSize(32, 52);
    selectionClampButton4.setSelected(!component.isVenousBOpen());
    add(selectionClampButton4); 
 
    // Venous A
    selectionClampButton5 = new ImageToggleButton(null, rolloverNonToggleClampUpImage
                                                      , rolloverToggleClampUpImage
                                                      , selectedClampUpImage);
    selectionClampButton5.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        StandardDialog.showDialog(SwingUtilities.getRootPane(selectionClampButton5), DialogType.WARNING, DialogOption.OK
            , "Clamp Placement", "Dr. Ogino says: don't add a clamp there!");
        selectionClampButton5.setSelected(false);
      }
    });
    selectionClampButton5.setLocation(283, 183);
    selectionClampButton5.setSize(32, 52);
    add(selectionClampButton5); 
    
    // add toggle button
    Image normalImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCircuitCheck[i18n]: conf/image/interface/game/Btn-CircuitCheck.png"));
    Image rolloverImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCircuitCheckRol[i18n]: conf/image/interface/game/Btn-CircuitCheckRol.png"));
    Image selectedImage = ImageLoader.getInstance().getImage(
        Translator.getString("image.ButtonCircuitCheckSel[i18n]: conf/image/interface/game/Btn-CircuitCheckSel.png"));
    selectionButton = new ImageToggleButton(normalImage, rolloverImage, selectedImage, selectedImage);
    selectionButton.setToolTipText(component.getName());
    selectionButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        notifyActivationListeners();
      }
    });
    selectionButton.setLocation(220, 30);
    selectionButton.setSize(150, 32);
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
    
    // antialiased text rendering
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // draws the tube image
    g.drawImage(tubeImage, 0, 48, this);

    // do blending
    float alpha = (float)(1.0 - component.getSvO2()) * 2;
    if (alpha < 0.0f) {
      alpha = 0.0f;
    }
    else if (alpha > 1.0f) {
      alpha = 1.0f;
    }
    Composite oldComp = g2.getComposite();
    Composite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
    g2.setComposite(alphaComp);

    // draws the image
    g.drawImage(image, 176, 1, this);
    
    // draws the venous image
    g.drawImage(venousImage, 0, 48, this);

    // Restore the old composite.
    g2.setComposite(oldComp);
    
    // draw the bubbles
    if (component.isArterialBubbles()) {
      if ((((System.nanoTime()) / 200000000) % 3) == 0) {
        g.drawImage(bubbleDot1Image, 350, 95, this);      
      } 
      else if ((((System.nanoTime()) / 200000000) % 3) == 1) {
        g.drawImage(bubbleDot2Image, 350, 95, this);
      } 
      else {
        g.drawImage(bubbleDot3Image, 350, 95, this);
      }
    }
    if (component.isVenousBubbles()) {
      if ((((System.nanoTime()) / 200000000) % 3) == 0) {
        g.drawImage(bubbleRect1Image, 301, 189, this);     
      } 
      else if ((((System.nanoTime()) / 200000000) % 3) == 1) {
        g.drawImage(bubbleRect2Image, 301, 189, this);
      } 
      else {
        g.drawImage(bubbleRect3Image, 301, 189, this);
      }
    }
        
    // draw what ECMO we are using
    g.setFont(g.getFont().deriveFont(Font.BOLD, 14f));
    g.setColor(Color.DARK_GRAY);
    g.drawString(component.getName(), 89, 10);
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
