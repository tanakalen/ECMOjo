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

import edu.hawaii.jabsom.tri.ecmo.app.control.action.BubbleAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.BubbleDetectorComponent;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 12, 2008
 */
public class BubbleDetectorDetailPanel extends DetailPanel implements Runnable {

  /** The component. */
  private BubbleDetectorComponent component;
  
  /** The detail image. */
  private Image detailImage 
    = ImageLoader.getInstance().getImage("conf/image/interface/game/Detail-BubbleDetector.png");

  /** The red alert image. */
  private Image redAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-RedMedium.png");
  /** The black alert image. */
  private Image blackAlertImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Alrt-BlackMedium.png");

  /** The bubble dot 1. */
  private Image bubbleDot1Image = ImageLoader.getInstance().getImage("conf/image/interface/game/Icon-BubbleDot1.png");
  /** The bubble dot 2. */
  private Image bubbleDot2Image = ImageLoader.getInstance().getImage("conf/image/interface/game/Icon-BubbleDot2.png");
  /** The bubble dot 3. */
  private Image bubbleDot3Image = ImageLoader.getInstance().getImage("conf/image/interface/game/Icon-BubbleDot3.png");
  
  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);
  
  /** The updater thread. */
  private Thread thread;
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected BubbleDetectorDetailPanel(final BubbleDetectorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);
       
    // add reset button 
    Image resetButtonImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Reset.png");
    Image resetButtonRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ResetRol.png");
    Image resetButtonSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-ResetSel.png");
    ImageButton resetButton = new ImageButton(resetButtonImage, resetButtonRolloverImage, resetButtonSelectedImage);
    resetButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        BubbleAction action = new BubbleAction();
        action.setArterialBubbles(false);
        action.setVenousBubbles(false);
        notifyActionListeners(action);
      }
    });
    resetButton.setLocation(64, 204);
    resetButton.setSize(32, 32);
    add(resetButton);   
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
    
    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 40f));
    
    if (component.isAlarm()){
      // draw bubbles
      int bubbles = (int)(System.nanoTime() / 180000000) % 3;
      Image bubbleImage1;
      Image bubbleImage2;
      Image bubbleImage3;
      switch (bubbles) {
        case 0:
          bubbleImage1 = bubbleDot1Image;
          bubbleImage2 = bubbleDot2Image;
          bubbleImage3 = bubbleDot3Image;
          break;
        case 1:
          bubbleImage1 = bubbleDot3Image;
          bubbleImage2 = bubbleDot1Image;
          bubbleImage3 = bubbleDot2Image;
          break;
        default:
          bubbleImage1 = bubbleDot2Image;
          bubbleImage2 = bubbleDot3Image;
          bubbleImage3 = bubbleDot1Image;
      }
      g.drawImage(bubbleImage1, 106, 128, this);
      g.drawImage(bubbleImage2, 117, 123, this);
      g.drawImage(bubbleImage3, 122, 127, this);
      g.drawImage(bubbleImage1, 133, 117, this);
      g.drawImage(bubbleImage2, 138, 121, this);
      g.drawImage(bubbleImage1, 140, 113, this);
      g.drawImage(bubbleImage3, 146, 112, this);
      g.drawImage(bubbleImage1, 155, 107, this);
      g.drawImage(bubbleImage3, 154, 114, this);
      g.drawImage(bubbleImage2, 161, 104, this);
      g.drawImage(bubbleImage1, 168, 99, this);
      g.drawImage(bubbleImage3, 168, 107, this);
      g.drawImage(bubbleImage1, 174, 96, this);
      g.drawImage(bubbleImage2, 185, 95, this);
      g.drawImage(bubbleImage3, 189, 87, this);

      // draw blinking red light
      if ((((System.nanoTime()) / 500000000) % 2) == 0) {
        g.drawImage(redAlertImage, 64, 176, this);
      }
      else {
        g.drawImage(blackAlertImage, 64, 176, this);        
      }      
    }
    
  }
}
