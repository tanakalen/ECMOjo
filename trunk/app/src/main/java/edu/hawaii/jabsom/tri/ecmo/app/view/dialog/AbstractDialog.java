package edu.hawaii.jabsom.tri.ecmo.app.view.dialog;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * The abstract dialog. 
 *
 * @author   king
 * @since    August 13, 2008
 */
public abstract class AbstractDialog extends JDialog {

  /**
   * Creates a dialog.
   * 
   * @param parent  The parent component of the dialog.
   */
  public AbstractDialog(Component parent) {
    this(parent, "");
  }
  
  /**
   * Creates a dialog.
   * 
   * @param parent  The parent component of the dialog.
   * @param title  The title.
   */
  public AbstractDialog(Component parent, String title) {
    super(JOptionPane.getFrameForComponent(parent), title);
    
    // make it modal
    setModal(true);
  }
}
