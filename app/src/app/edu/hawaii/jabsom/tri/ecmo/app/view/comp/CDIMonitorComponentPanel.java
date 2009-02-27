package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;

import king.lib.access.ImageLoader;

import edu.hawaii.jabsom.tri.ecmo.app.model.comp.CDIMonitorComponent;

/**
 * The ACT component panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 4, 2008
 */
public class CDIMonitorComponentPanel extends ComponentPanel {

  /** The panel image. */
  private Image image = ImageLoader.getInstance().getImage("conf/image/interface/game/Mtr-Cdi.png");

  /** The monitor formatter. */
  private final DecimalFormat pHFormatter = new DecimalFormat("0.00");
  
  /** The monitor formatter. */
  private final DecimalFormat monitorFormatter = new DecimalFormat("0.0");
  
  /** The font color. */
  private final Color textColor = new Color(0.2f, 0.2f, 0.2f);

  /** The component. */
  private CDIMonitorComponent component;
  
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected CDIMonitorComponentPanel(CDIMonitorComponent component) {
    super(component);
    this.component = component;
    
    // set size and location
    setLocation(0, 0);
    setSize(288, 152);
    
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
    String text = pHFormatter.format(component.getPH());
    g.drawString(text, 88, 34);
    text = String.valueOf(Math.round(component.getPCO2()));
    g.drawString(text, 88, 60);
    text = String.valueOf(Math.round(component.getPO2()));
    g.drawString(text, 88, 84);
    text = String.valueOf(Math.round(component.getHCO3()));
    g.drawString(text, 88, 108);
    text = monitorFormatter.format(component.getTemperature());
    g.drawString(text, 88, 131);
    
    text = monitorFormatter.format(component.getHct());
    g.drawString(text, 212, 34);
    text = monitorFormatter.format(component.getHgb());
    g.drawString(text, 212, 60);
    text = String.valueOf(Math.round(component.getSvO2() * 100)) + "%";
    g.drawString(text, 212, 84);
    text = monitorFormatter.format(component.getBE());
    g.drawString(text, 212, 108);
    text = String.valueOf(Math.round(component.getSaO2() * 100)) + "%";
    g.drawString(text, 212, 131);
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
