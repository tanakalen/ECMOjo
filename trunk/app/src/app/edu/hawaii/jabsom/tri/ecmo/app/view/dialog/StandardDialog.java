package edu.hawaii.jabsom.tri.ecmo.app.view.dialog;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import king.lib.access.ImageLoader;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.hawaii.jabsom.tri.ecmo.app.Configuration;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;

/**
 * The standard dialog. 
 *
 * @author   king
 * @since    October 30, 2008
 */
public class StandardDialog extends DefaultDialog {

  /** The dialog types available. */
  public enum DialogType { PLAIN, INFORMATION, SUCCESS, QUESTION, WARNING, ERROR };
  /** The dialog options available. */
  public enum DialogOption { OK, OK_CANCEL, YES_NO, YES_NO_CANCEL };
  /** The dialog results available. */
  public enum DialogResult { OK, CANCEL, YES, NO };
  
  /** The result or null for none. */
  private DialogResult result;
  
  /**
   * Creates a dialog.
   * 
   * @param parent  The parent component of the dialog.
   * @param dialogType  The dialog type.
   * @param dialogOption  The dialog option, i.e. buttons.
   * @param title  The dialog title.
   * @param message  The dialog message.
   */
  public StandardDialog(Component parent, DialogType dialogType, DialogOption dialogOption
                                        , String title, String message) {
    super(parent, title);
    
    // set look
    Container contentPane = getContentPane();
    contentPane.setLayout(new FormLayout("4px, 16px, 16px, 4px, fill:402px, 20px"
                                       , "4px, 32px, 4px, fill:250px, 4px, 46px, 4px"));
    CellConstraints cc = new CellConstraints();
    
    // add icon
    JLabel iconLabel = new JLabel();
    iconLabel.setVerticalAlignment(JLabel.TOP);
    if (dialogType == DialogType.INFORMATION) {
      iconLabel.setIcon(new ImageIcon(ImageLoader.getInstance().getImage("conf/icon/information.png")));
    }
    else if (dialogType == DialogType.SUCCESS) {
      iconLabel.setIcon(new ImageIcon(ImageLoader.getInstance().getImage("conf/icon/success.png")));
    }
    else if (dialogType == DialogType.QUESTION) {
      iconLabel.setIcon(new ImageIcon(ImageLoader.getInstance().getImage("conf/icon/question.png")));
    }
    else if (dialogType == DialogType.WARNING) {
      iconLabel.setIcon(new ImageIcon(ImageLoader.getInstance().getImage("conf/icon/warning.png")));
    }
    else if (dialogType == DialogType.ERROR) {
      iconLabel.setIcon(new ImageIcon(ImageLoader.getInstance().getImage("conf/icon/error.png")));
    }
    contentPane.add(iconLabel, cc.xyw(2, 2, 2));
    
    // add text
    JTextArea textArea = new JTextArea();
    textArea.setText(Configuration.getInstance().getString(message));
    textArea.setWrapStyleWord(true);
    textArea.setLineWrap(true);
    textArea.setEditable(false);
    textArea.setOpaque(false);
    textArea.setFont(textArea.getFont().deriveFont(Font.PLAIN, 14f));
    textArea.setMargin(new Insets(5, 5, 5, 5));
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    contentPane.add(scrollPane, cc.xyw(3, 4, 3));
    
    // add button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setOpaque(false);
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
    contentPane.add(buttonPanel, cc.xyw(3, 6, 3));
        
    if ((dialogOption == DialogOption.OK) || (dialogOption == DialogOption.OK_CANCEL)) {
      // add OK button
      Image okButtonImage = ImageLoader.getInstance().getImage("conf/gui/Btn-Ok.png");    
      Image okButtonRolloverImage = ImageLoader.getInstance().getImage("conf/gui/Btn-OkRol.png");
      Image okButtonSelectedImage = ImageLoader.getInstance().getImage("conf/gui/Btn-OkSel.png");
      ImageButton button = new ImageButton(okButtonImage, okButtonRolloverImage, okButtonSelectedImage);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          result = DialogResult.OK;
          setVisible(false);
        }
      });
      buttonPanel.add(button);
    }
   
    if ((dialogOption == DialogOption.YES_NO) || (dialogOption == DialogOption.YES_NO_CANCEL)) {
      // add Yes button
      Image yesButtonImage = ImageLoader.getInstance().getImage("conf/gui/Btn-Yes.png");    
      Image yesButtonRolloverImage = ImageLoader.getInstance().getImage("conf/gui/Btn-YesRol.png");
      Image yesButtonSelectedImage = ImageLoader.getInstance().getImage("conf/gui/Btn-YesSel.png");
      ImageButton button = new ImageButton(yesButtonImage, yesButtonRolloverImage, yesButtonSelectedImage);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          result = DialogResult.YES;
          setVisible(false);
        }
      });
      buttonPanel.add(button);
    }

    if ((dialogOption == DialogOption.YES_NO) || (dialogOption == DialogOption.YES_NO_CANCEL)) {
      // add No button
      Image noButtonImage = ImageLoader.getInstance().getImage("conf/gui/Btn-No.png");    
      Image noButtonRolloverImage = ImageLoader.getInstance().getImage("conf/gui/Btn-NoRol.png");
      Image noButtonSelectedImage = ImageLoader.getInstance().getImage("conf/gui/Btn-NoSel.png");
      ImageButton button = new ImageButton(noButtonImage, noButtonRolloverImage, noButtonSelectedImage);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          result = DialogResult.NO;
          setVisible(false);
        }
      });
      buttonPanel.add(button);
    }

    if ((dialogOption == DialogOption.OK_CANCEL) || (dialogOption == DialogOption.YES_NO_CANCEL)) {
      // add Cancel button
      Image cancelButtonImage = ImageLoader.getInstance().getImage("conf/gui/Btn-Cancel.png");    
      Image cancelButtonRolloverImage = ImageLoader.getInstance().getImage("conf/gui/Btn-CancelRol.png");
      Image cancelButtonSelectedImage = ImageLoader.getInstance().getImage("conf/gui/Btn-CancelSel.png");
      ImageButton button = new ImageButton(cancelButtonImage, cancelButtonRolloverImage, cancelButtonSelectedImage);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          result = DialogResult.CANCEL;
          setVisible(false);
        }
      });
      buttonPanel.add(button);
    }
    
    // fix up size and location
    pack();
    setLocationRelativeTo(parent);
  }
  
  /**
   * Returns the dialog result.
   * 
   * @return  The dialog result or null for none.
   */
  public DialogResult getResult() {
    return result;
  }
  
  /**
   * Shows a dialog.
   * 
   * @param parent  The parent component of the dialog.
   * @param dialogType  The dialog type.
   * @param dialogOption  The dialog option, i.e. buttons.
   * @param title  The dialog title.
   * @param message  The dialog message.
   * @return  The result.
   */
  public static DialogResult showDialog(Component parent, DialogType dialogType, DialogOption dialogOption
                                      , String title, String message) {
    StandardDialog dialog = new StandardDialog(parent, dialogType, dialogOption, title, message);
    dialog.setVisible(true);
    
    // and return result
    return dialog.getResult();
  }
}
