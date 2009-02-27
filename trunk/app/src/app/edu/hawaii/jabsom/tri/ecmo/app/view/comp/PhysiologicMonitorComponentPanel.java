package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ButtonGroup;

import king.lib.access.ImageLoader;

import edu.hawaii.jabsom.tri.ecmo.app.model.comp.PhysiologicMonitorComponent;

/**
 * The ACT component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class PhysiologicMonitorComponentPanel extends ComponentPanel {

  /** The panel image. */
  private Image image = ImageLoader.getInstance().getImage("conf/image/interface/game/Mtr-Physiologic.png");

  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);

  /** The component. */
  private PhysiologicMonitorComponent component;
  
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected PhysiologicMonitorComponentPanel(PhysiologicMonitorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(0, 152);
    setSize(288, 105);
    
    // set layout
    setLayout(null);
    
    
  }

  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    repaint();
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // draws the image
    g.drawImage(image, 0, 0, this);
    
    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // set text properties
    g.setColor(textColor);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 16f));
    
    // draw text
    String text = String.valueOf((int)component.getHeartRate());
    g.drawString(text, 88, 34);
    text = String.valueOf((int)(component.getO2Saturation() * 100)) + "%";
    g.drawString(text, 88, 60);

    text = String.valueOf((int)component.getSystolicBloodPressure());
    g.drawString(text, 212, 34);
    text = String.valueOf((int)component.getDiastolicBloodPressure());
    g.drawString(text, 212, 60);
    text = String.valueOf((int)component.getMeanBloodPressure());
    g.drawString(text, 212, 84);
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
