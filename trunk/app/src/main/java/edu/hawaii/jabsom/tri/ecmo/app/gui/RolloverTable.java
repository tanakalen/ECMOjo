package edu.hawaii.jabsom.tri.ecmo.app.gui;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.*;

/**
 * The rollover table which has row rollover.
 * <p>
 * See also: http://forums.sun.com/thread.jspa?threadID=640414
 * 
 * @author Torgil  
 * @author Christoph Aschwanden
 * @since October 22, 2008
 */
public class RolloverTable extends JTable {

  /** True to show selection. */
  private boolean showSelection;
  
  /** The rollover index. */
  private int rollOverRowIndex = -1;


  /**
   * Constructor for table.
   * 
   * @param model  The table model.
   */
  public RolloverTable(TableModel model) {
    super(model);
    RolloverListener listener = new RolloverListener();
    addMouseMotionListener(listener);
    addMouseListener(listener);
  }

  /**
   * Prepares the renderer.
   * 
   * @param renderer  The renderer.
   * @param row  The row.
   * @param column  The column.
   * @return  The component.
   */
  public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
    Component c = super.prepareRenderer(renderer, row, column);
    if ((showSelection && isRowSelected(row)) || (row == rollOverRowIndex)) {
      c.setForeground(getSelectionForeground());
      c.setBackground(getSelectionBackground());
    }
    else {
      c.setForeground(getForeground());
      c.setBackground(getBackground());
    }
    return c;
  }

  /**
   * Returns true if selected rows are displayed.
   *
   * @return  True to show selections.
   */
  public boolean isShowSelection() {
    return showSelection;
  }

  /**
   * True if selected rows are displayed.
   *
   * @param showSelection  True to show selections.
   */
  public void setShowSelection(boolean showSelection) {
    this.showSelection = showSelection;
  }
  
  /**
   * The rollover listener.
   * 
   * @author Christoph Aschwanden
   */
  private class RolloverListener extends MouseInputAdapter {

    /**
     * Called for mouse exits.
     * 
     * @param e  The event.
     */
    public void mouseExited(MouseEvent e) {
      rollOverRowIndex = -1;
      repaint();
    }

    /**
     * Called when mouse moved.
     * 
     * @param e  The event.
     */
    public void mouseMoved(MouseEvent e) {
      int row = rowAtPoint(e.getPoint());
      if (row != rollOverRowIndex) {
        rollOverRowIndex = row;
        repaint();
      }
    }
  }
}
