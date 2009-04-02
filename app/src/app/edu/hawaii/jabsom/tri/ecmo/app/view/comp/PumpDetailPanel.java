package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JToggleButton;

import king.lib.access.ImageLoader;
import king.lib.out.Error;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.PumpAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.KnobButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.SwitchButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.KnobButton.RotationListener;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PumpComponent;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 5, 2008
 */
public class PumpDetailPanel extends DetailPanel implements Runnable {

  /** The component. */
  private PumpComponent component;
  

  /** The detail image. */
  private Image detailImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Detail-Pump.png");

  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedMedium.png");

  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackMedium.png");
  
  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);
  
  /** The flow formatter. */
  private final DecimalFormat flowFormatter = new DecimalFormat("0.000");
  /** True, if the value is adjusting. */
  private boolean adjusting = false;
  /** The adjusted flow. */
  private double adjustedValue;
  
  /** The power button. */
  private JToggleButton powerButton;
  
  /** The updater thread. */
  private Thread thread;
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected PumpDetailPanel(final PumpComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);
    
    // add knob button
    final double flowFactor = 0.05;
    Image knobImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-KnobBase.png");
    Image dialImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-KnobDial.png");    
    Image rolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-KnobRollover.png");
    KnobButton knobButton = new KnobButton(knobImage, dialImage, rolloverImage, 1, 18);
    knobButton.addRotationListener(new RotationListener() {
      public void handleRotation(double angle, boolean valueAdjusting) {
        adjusting = valueAdjusting;
        double value = angle * flowFactor;
        if (adjusting) {
          adjustedValue = value;
          repaint();
        }
        else {
          PumpAction action = new PumpAction();
          action.setOn(component.isOn());
          action.setFlow(value);
          notifyActionListeners(action);
        }
      }    
    });
    knobButton.setAngle(component.getFlow() / flowFactor);
    knobButton.setMinAngle(0.0f);
    knobButton.setMaxAngle(5.0f / flowFactor);
    knobButton.setLocation(120, 143);
    knobButton.setSize(96, 96);
    add(knobButton); 
    
    // add on/off button
    Image switchImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Switch.png");
    Image switchRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-SwitchRol.png");   
    powerButton = new SwitchButton(switchImage, switchRolloverImage, 20);
    powerButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        PumpAction action = new PumpAction();
        action.setOn(powerButton.isSelected());
        action.setFlow(component.getFlow());
        notifyActionListeners(action);
      }
    });
    powerButton.setLocation(228, 98);
    powerButton.setSize(24, 44);
    add(powerButton);  
    powerButton.setSelected(component.isOn());
  }

  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    // update power button
    powerButton.setSelected(component.isOn());
    
    // and redraw
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
    
    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 36f));
    
    // draw text
    if (component.isOn()) {
      // draw base
      double value = adjusting ? adjustedValue : component.getFlow();
      String text = flowFormatter.format(value);
      g.drawString(text, 95, 120);
    } 
    else if (component.isAlarm()){
      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 224, 54, this);
      }
      else {
        g.drawImage(blackAlertImage, 224, 54, this);        
      }      
    }
    else {
      // draw the red light
      g.drawImage(redAlertImage, 224, 54, this);      
    }
  }
}
