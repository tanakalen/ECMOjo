package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * The tutorial glass pane. It is transparent for MouseEvents, and respects
 * underneath component's cursors by default. It is also friedly for other users,
 * if someone adds a mouseListener to this GlassPane or set a new cursor it will
 * respect them.
 * <p>
 * "A well-behaved GlassPane" - http://weblogs.java.net/blog/alexfromsun/
 * 
 * @author Christoph Aschwanden
 * @author Alexander Potochkin
 * @since Januarty 12, 2009
 */
public class TutorialGlassPane extends JPanel implements AWTEventListener {
  
  /** The point. */
  private Point point = new Point();

  
  /**
   * The constructor.
   */
  public TutorialGlassPane() {
    super(null);
    
    // make transparent
    setOpaque(false);
  }

  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // add listener
    Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_MOTION_EVENT_MASK 
                                                        | AWTEvent.MOUSE_EVENT_MASK);
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // remove listener
    Toolkit.getDefaultToolkit().removeAWTEventListener(this);
    
    super.removeNotify();
  }
  
  /**
   * Sets the point.
   * 
   * @param point  The point.
   */
  public void setPoint(Point point) {
    this.point = point;
  }

  /**
   * Paints the component.
   * 
   * @param g  Where to paint to.
   */
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Point point = this.point;
    if (point != null) {
      // draw 'x'
      Graphics2D g2 = (Graphics2D)g;
      g2.setColor(Color.BLACK);
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
      int x = (int)point.getX();
      int y = (int)point.getY();
      int d = 5;
      g2.setStroke(new BasicStroke(3.0f));
      g2.drawLine(x - d, y - d, x + d, y + d);
      g2.drawLine(x - d, y + d, x + d, y - d);
    } 
  }

  /**
   * Dispatches an event.
   * 
   * @param event  The event.
   */
  public void eventDispatched(AWTEvent event) {
    if (event instanceof MouseEvent) {
      MouseEvent mouseEvent = (MouseEvent)event;
      MouseEvent converted = SwingUtilities.convertMouseEvent(mouseEvent.getComponent(), mouseEvent, this);
      Point point = converted.getPoint();
          
      // do we need to consume the event
      int id = mouseEvent.getID();
      int x = (int)point.getX();
      int y = (int)point.getY();
      if ((x < 293) || (x > 745) || (y < 2) || (y > 76)) {
        if ((id == MouseEvent.MOUSE_CLICKED) || (id == MouseEvent.MOUSE_PRESSED) || (id == MouseEvent.MOUSE_RELEASED)) {
          mouseEvent.consume();
        }
        this.point = point;
      }
      else {
        this.point = null;
      }
      repaint();
    }
  }

  /**
   * If someone adds a mouseListener to the GlassPane or set a new cursor we
   * expect that he knows what he is doing and return the super.contains(x, y)
   * otherwise we return false to respect the cursors for the underneath
   * components.
   * 
   * @param x  The x.
   * @param y  The y.
   * @return  True if it is contained.
   */
  public boolean contains(int x, int y) {
    if ((getMouseListeners().length == 0) 
          && (getMouseMotionListeners().length == 0)
          && (getMouseWheelListeners().length == 0) 
          && (getCursor() == Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))) {
      return false;
    }
    return super.contains(x, y);
  }
}
