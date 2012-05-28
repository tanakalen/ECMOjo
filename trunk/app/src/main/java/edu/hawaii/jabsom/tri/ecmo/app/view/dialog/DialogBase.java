package edu.hawaii.jabsom.tri.ecmo.app.view.dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
 * @author noblemaster
 * @since April 23, 2010
 */
public class DialogBase extends PanelDialog {

  /** The dialog types available. */
  public enum DialogType { PLAIN, INFORMATION, SUCCESS, QUESTION, WARNING, ERROR };
  /** The dialog options available. */
  public enum DialogOption { OK, OK_CANCEL, YES_NO, YES_NO_CANCEL };
  /** The dialog results available. */
  public enum DialogResult { OK, CANCEL, YES, NO };
  
  /** The result or null for none. */
  private DialogResult result;
  
  /** The listener. */
  public static interface DialogListener {
    
    /**
     * Called result.
     * 
     * @param result  The result or null for none.
     */
    void handleResult(DialogResult result);
  }
  
  /** Listeners. */
  private List<DialogListener> listeners = new ArrayList<DialogListener>();

  
  /**
   * Creates a dialog.
   * 
   * @param parent  The parent component of the dialog.
   * @param title  The dialog title.
   */
  public DialogBase(Component parent, final String title) {
    super(parent, true);
    
    // set content pane
    final Image backgroundImage = ImageLoader.getInstance().getImage("conf/gui/dialog-wshadow.png");
    JPanel contentPane = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // set antialiased text
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // draw background
        g.drawImage(backgroundImage, 0, 0, null);
        
        // draw title
        g.setFont(g.getFont().deriveFont(Font.BOLD, 20f));
        g.setColor(new Color(0x404040));
        g.drawString(title, 35, 38);
      }
    };
    contentPane.setOpaque(false);
    setPanel(contentPane);
  }
  
  /**
   * Creates a dialog.
   * 
   * @param parent  The parent component of the dialog.
   * @param dialogType  The dialog type.
   * @param dialogOption  The dialog option, i.e. buttons.
   * @param title  The dialog title.
   * @param message  The dialog message.
   */
  public DialogBase(Component parent, DialogType dialogType, DialogOption dialogOption
                                        , String title, String message) {
    this(parent, title);
    
    // set look
    Container contentPane = (Container)getPanel();
    contentPane.setLayout(new FormLayout("14px, 16px, 16px, 4px, fill:402px, 30px"
                                       , "14px, 32px, 4px, fill:250px, 4px, 46px, 14px"));
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
          notifyResult(result);
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
          notifyResult(result);
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
          notifyResult(result);
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
          notifyResult(result);
        }
      });
      buttonPanel.add(button);
    }
    
    // update the size
    contentPane.setSize(contentPane.getPreferredSize());
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
   */
  public static void showDialog(Component parent, DialogType dialogType, DialogOption dialogOption
                              , String title, String message) {
    showDialog(parent, dialogType, dialogOption, title, message, null);
  }
  
  /**
   * Shows a dialog.
   * 
   * @param parent  The parent component of the dialog.
   * @param dialogType  The dialog type.
   * @param dialogOption  The dialog option, i.e. buttons.
   * @param title  The dialog title.
   * @param message  The dialog message.
   * @param listener  The listener.
   */
  public static void showDialog(Component parent, DialogType dialogType, 
                                DialogOption dialogOption, String title, 
                                String message, DialogListener listener) {
    DialogBase dialog = new DialogBase(parent, dialogType, dialogOption, title, message);
    if (listener != null) {  
      dialog.addDialogListener(listener);
    }
    dialog.setVisible(true);
  }

  /**
   * Adds a listener.
   * 
   * @param listener  The listener to add.
   */
  public void addDialogListener(DialogListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a listener.
   * 
   * @param listener  The listener to add.
   */
  public void removeDialogListener(DialogListener listener) {
    listeners.remove(listener);
  }
  
  /**
   * Notify about result.
   * 
   * @param result  The result.
   */
  private void notifyResult(DialogResult result) {
    for (int i = 0; i < listeners.size(); i++) {
      listeners.get(i).handleResult(result);
    }
  }
}
