package edu.hawaii.jabsom.tri.ecmo.app.view;

import java.awt.AWTEvent;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import king.lib.access.ImageLoader;

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
  
  /** Our view cursor. */
  private Cursor cursor;
  
  
  /**
   * The constructor.
   */
  public TutorialGlassPane() {
    super(null);
    
    // make transparent
    setOpaque(false);
    
    // load our view cursor
    Image image = ImageLoader.getInstance().getImage("conf/cursor/view-cursor.gif");
    cursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(13, 13), "View");  
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
      if ((x < 293) || (x > 800) || (y < 2) || (y > 76)) {
        setCursor(cursor);
        if (((x < 364) || (x > 538) || (y < 370) || (y > 399))         // can still click dialog buttons
            && ((x < 304) || (x > 584) || (y < 95) || (y > 343))) {   // can still click selected component
          if ((id == MouseEvent.MOUSE_CLICKED) || (id == MouseEvent.MOUSE_PRESSED) 
                                               || (id == MouseEvent.MOUSE_RELEASED)) {
            mouseEvent.consume();
          }
        }
      }
      else {
        setCursor(Cursor.getDefaultCursor());
      }
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
