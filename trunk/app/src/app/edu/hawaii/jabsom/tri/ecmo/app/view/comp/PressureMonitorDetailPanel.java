package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import king.lib.access.ImageLoader;
import king.lib.out.Error;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.KillAction;
import edu.hawaii.jabsom.tri.ecmo.app.control.action.PressureMonitorAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PressureMonitorComponent;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 12, 2008
 */
public class PressureMonitorDetailPanel extends DetailPanel implements Runnable {

  /** The detail image. */
  private Image detailImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Detail-PressureMonitor.png");

  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedMedium.png");

  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackMedium.png");
  
  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);
  
  /** The component. */
  private PressureMonitorComponent component;
  
  /** The special code. */
  private int code;

  /** The updater thread. */
  private Thread thread;  
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected PressureMonitorDetailPanel(final PressureMonitorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);

    // adjustment value
    final double increment = 5;
    
    // load images
    Image decButtonImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Left.png");    
    Image decButtonRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-LeftRol.png");
    Image decButtonSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-LeftSel.png");
    Image incButtonImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Right.png");
    Image incButtonRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-RightRol.png");
    Image incButtonSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-RightSel.png");
    Image midButtonImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Middle.png");
    Image midButtonRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-MiddleRol.png");
    Image midButtonSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-MiddleSel.png");
  
    // add "-" max button 1
    ImageButton decMax1Button = new ImageButton(decButtonImage, decButtonRolloverImage, decButtonSelectedImage);
    decMax1Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin());
        action.setVenousPressureMax(component.getVenousPressureMax() - increment);
        action.setPreMembranePressureMin(component.getPreMembranePressureMin());
        action.setPreMembranePressureMax(component.getPreMembranePressureMax());
        action.setPostMembranePressureMin(component.getPostMembranePressureMin());
        action.setPostMembranePressureMax(component.getPostMembranePressureMax());
        notifyActionListeners(action);
      }
    });
    decMax1Button.setLocation(176, 34);
    decMax1Button.setSize(24, 24);
    add(decMax1Button);  
    
    // add "+" max button 1
    ImageButton incMax1Button = new ImageButton(incButtonImage, incButtonRolloverImage, incButtonSelectedImage);  
    incMax1Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin());
        action.setVenousPressureMax(component.getVenousPressureMax() + increment);
        action.setPreMembranePressureMin(component.getPreMembranePressureMin());
        action.setPreMembranePressureMax(component.getPreMembranePressureMax());
        action.setPostMembranePressureMin(component.getPostMembranePressureMin());
        action.setPostMembranePressureMax(component.getPostMembranePressureMax());
        notifyActionListeners(action);
      }
    });
    incMax1Button.setLocation(200, 34);
    incMax1Button.setSize(24, 24);
    add(incMax1Button); 

    // add middle button 1
    ImageButton middle1Button = new ImageButton(midButtonImage, midButtonRolloverImage, midButtonSelectedImage);  
    middle1Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        code *= 10;
        code += 1;
        
        if (code == 2211) {
          KillAction action = new KillAction();
          notifyActionListeners(action);
        }
      }
    });
    middle1Button.setLocation(176, 58);
    middle1Button.setSize(48, 12);
    add(middle1Button); 
    
    // add "-" min button 1
    ImageButton decMin1Button = new ImageButton(decButtonImage, decButtonRolloverImage, decButtonSelectedImage);  
    decMin1Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin() - increment);
        action.setVenousPressureMax(component.getVenousPressureMax());
        action.setPreMembranePressureMin(component.getPreMembranePressureMin());
        action.setPreMembranePressureMax(component.getPreMembranePressureMax());
        action.setPostMembranePressureMin(component.getPostMembranePressureMin());
        action.setPostMembranePressureMax(component.getPostMembranePressureMax());
        notifyActionListeners(action);
      }
    });
    decMin1Button.setLocation(176, 69);
    decMin1Button.setSize(24, 24);
    add(decMin1Button);  
    
    // add "+" min button 1
    ImageButton incMin1Button = new ImageButton(incButtonImage, incButtonRolloverImage, incButtonSelectedImage);
    incMin1Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin() + increment);
        action.setVenousPressureMax(component.getVenousPressureMax());
        action.setPreMembranePressureMin(component.getPreMembranePressureMin());
        action.setPreMembranePressureMax(component.getPreMembranePressureMax());
        action.setPostMembranePressureMin(component.getPostMembranePressureMin());
        action.setPostMembranePressureMax(component.getPostMembranePressureMax());
        notifyActionListeners(action);
      }
    });
    incMin1Button.setLocation(200, 69);
    incMin1Button.setSize(24, 24);
    add(incMin1Button);     

    // add "-" max button 2
    ImageButton decMax2Button = new ImageButton(decButtonImage, decButtonRolloverImage, decButtonSelectedImage);
    decMax2Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin());
        action.setVenousPressureMax(component.getVenousPressureMax());
        action.setPreMembranePressureMin(component.getPreMembranePressureMin());
        action.setPreMembranePressureMax(component.getPreMembranePressureMax() - increment);
        action.setPostMembranePressureMin(component.getPostMembranePressureMin());
        action.setPostMembranePressureMax(component.getPostMembranePressureMax());
        notifyActionListeners(action);
      }
    });
    decMax2Button.setLocation(176, 114);
    decMax2Button.setSize(24, 24);
    add(decMax2Button);  
    
    // add "+" max button 2
    ImageButton incMax2Button = new ImageButton(incButtonImage, incButtonRolloverImage, incButtonSelectedImage);
    incMax2Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin());
        action.setVenousPressureMax(component.getVenousPressureMax());
        action.setPreMembranePressureMin(component.getPreMembranePressureMin());
        action.setPreMembranePressureMax(component.getPreMembranePressureMax() + increment);
        action.setPostMembranePressureMin(component.getPostMembranePressureMin());
        action.setPostMembranePressureMax(component.getPostMembranePressureMax());
        notifyActionListeners(action);
      }
    });
    incMax2Button.setLocation(200, 114);
    incMax2Button.setSize(24, 24);
    add(incMax2Button); 

    // add middle button 2
    ImageButton middle2Button = new ImageButton(midButtonImage, midButtonRolloverImage, midButtonSelectedImage);
    middle2Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        code *= 10;
        code += 2;
      }
    });
    middle2Button.setLocation(176, 137);
    middle2Button.setSize(48, 12);
    add(middle2Button); 
    
    // add "-" min button 2
    ImageButton decMin2Button = new ImageButton(decButtonImage, decButtonRolloverImage, decButtonSelectedImage);    
    decMin2Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin());
        action.setVenousPressureMax(component.getVenousPressureMax());
        action.setPreMembranePressureMin(component.getPreMembranePressureMin() - increment);
        action.setPreMembranePressureMax(component.getPreMembranePressureMax());
        action.setPostMembranePressureMin(component.getPostMembranePressureMin());
        action.setPostMembranePressureMax(component.getPostMembranePressureMax());
        notifyActionListeners(action);
      }
    });
    decMin2Button.setLocation(176, 149);
    decMin2Button.setSize(24, 24);
    add(decMin2Button);  
    
    // add "+" min button 2
    ImageButton incMin2Button = new ImageButton(incButtonImage, incButtonRolloverImage, incButtonSelectedImage);
    incMin2Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin());
        action.setVenousPressureMax(component.getVenousPressureMax());
        action.setPreMembranePressureMin(component.getPreMembranePressureMin() + increment);
        action.setPreMembranePressureMax(component.getPreMembranePressureMax());
        action.setPostMembranePressureMin(component.getPostMembranePressureMin());
        action.setPostMembranePressureMax(component.getPostMembranePressureMax());
        notifyActionListeners(action);
      }
    });
    incMin2Button.setLocation(200, 149);
    incMin2Button.setSize(24, 24);
    add(incMin2Button);
    
    // add "-" max button 3
    ImageButton decMax3Button = new ImageButton(decButtonImage, decButtonRolloverImage, decButtonSelectedImage);    
    decMax3Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin());
        action.setVenousPressureMax(component.getVenousPressureMax());
        action.setPreMembranePressureMin(component.getPreMembranePressureMin());
        action.setPreMembranePressureMax(component.getPreMembranePressureMax());
        action.setPostMembranePressureMin(component.getPostMembranePressureMin());
        action.setPostMembranePressureMax(component.getPostMembranePressureMax() - increment);
        notifyActionListeners(action);
      }
    });
    decMax3Button.setLocation(176, 194);
    decMax3Button.setSize(24, 24);
    add(decMax3Button);  
    
    // add "+" max button 3
    ImageButton incMax3Button = new ImageButton(incButtonImage, incButtonRolloverImage, incButtonSelectedImage);
    incMax3Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin());
        action.setVenousPressureMax(component.getVenousPressureMax());
        action.setPreMembranePressureMin(component.getPreMembranePressureMin());
        action.setPreMembranePressureMax(component.getPreMembranePressureMax());
        action.setPostMembranePressureMin(component.getPostMembranePressureMin());
        action.setPostMembranePressureMax(component.getPostMembranePressureMax() + increment);
        notifyActionListeners(action);
      }
    });
    incMax3Button.setLocation(200, 194);
    incMax3Button.setSize(24, 24);
    add(incMax3Button); 

    // add middle button 3
    ImageButton middle3Button = new ImageButton(midButtonImage, midButtonRolloverImage, midButtonSelectedImage);
    middle3Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        code *= 10;
        code += 3;
      }
    });
    middle3Button.setLocation(176, 217);
    middle3Button.setSize(48, 12);
    add(middle3Button); 
    
    // add "-" min button 3
    ImageButton decMin3Button = new ImageButton(decButtonImage, decButtonRolloverImage, decButtonSelectedImage);    
    decMin3Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin());
        action.setVenousPressureMax(component.getVenousPressureMax());
        action.setPreMembranePressureMin(component.getPreMembranePressureMin());
        action.setPreMembranePressureMax(component.getPreMembranePressureMax());
        action.setPostMembranePressureMin(component.getPostMembranePressureMin() - increment);
        action.setPostMembranePressureMax(component.getPostMembranePressureMax());
        notifyActionListeners(action);
      }
    });
    decMin3Button.setLocation(176, 229);
    decMin3Button.setSize(24, 24);
    add(decMin3Button);  
    
    // add "+" min button 3
    ImageButton incMin3Button = new ImageButton(incButtonImage, incButtonRolloverImage, incButtonSelectedImage);
    incMin3Button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PressureMonitorAction action = new PressureMonitorAction();
        action.setVenousPressureMin(component.getVenousPressureMin());
        action.setVenousPressureMax(component.getVenousPressureMax());
        action.setPreMembranePressureMin(component.getPreMembranePressureMin());
        action.setPreMembranePressureMax(component.getPreMembranePressureMax());
        action.setPostMembranePressureMin(component.getPostMembranePressureMin() + increment);
        action.setPostMembranePressureMax(component.getPostMembranePressureMax());
        notifyActionListeners(action);
      }
    });
    incMin3Button.setLocation(200, 229);
    incMin3Button.setSize(24, 24);
    add(incMin3Button);    
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
    g2.drawImage(detailImage, 0, 0, this);

    if (component.isVenousPressureAlarm()){
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 32, 63, this);
      }
      else {
        g.drawImage(blackAlertImage, 32, 63, this);        
      }      
    }

    if (component.isPreMembranePressureAlarm()){
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 32, 143, this);
      }
      else {
        g.drawImage(blackAlertImage, 32, 143, this);        
      }      
    }
    
    if (component.isPostMembranePressureAlarm()){
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 32, 223, this);
      }
      else {
        g.drawImage(blackAlertImage, 32, 223, this);        
      }      
    }
    
    // draw text for max and min
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 20f));
    String text = String.valueOf((int)component.getVenousPressureMax());
    g.drawString(text, 128, 60);    
    text = String.valueOf((int)component.getVenousPressureMin());
    g.drawString(text, 128, 87);

    text = String.valueOf((int)component.getPreMembranePressureMax());
    g.drawString(text, 128, 137);
    text = String.valueOf((int)component.getPreMembranePressureMin());
    g.drawString(text, 128, 169);
    
    text = String.valueOf((int)component.getPostMembranePressureMax());
    g.drawString(text, 128, 215);
    text = String.valueOf((int)component.getPostMembranePressureMin());
    g.drawString(text, 128, 247);
    
    // draw text for pressure
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 28f));
    text = String.valueOf((int)component.getVenousPressure());
    g.drawString(text, 80, 75);
    text = String.valueOf((int)component.getPreMembranePressure());
    g.drawString(text, 80, 155);
    text = String.valueOf((int)component.getPostMembranePressure());
    g.drawString(text, 80, 235);
  }
}
