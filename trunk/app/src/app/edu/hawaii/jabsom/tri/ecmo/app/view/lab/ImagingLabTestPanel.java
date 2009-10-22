package edu.hawaii.jabsom.tri.ecmo.app.view.lab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import king.lib.access.ImageLoader;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.LabRequestAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.LinkButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.RolloverTable;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.EchoLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ImagingLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.UltrasoundLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.XRayLabTest;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.LabTestList.LabTestListener;
import edu.hawaii.jabsom.tri.ecmo.app.view.comp.LabDetailPanel;
import edu.hawaii.jabsom.tri.ecmo.app.view.dialog.AbstractDialog;

/**
 * The imaging lab test panel. 
 *
 * @author   Christoph Aschwanden
 * @since    October 7, 2008
 */
public class ImagingLabTestPanel extends LabDetailPanel implements LabTestListener {

  /** The lab component. */
  private LabComponent component;
  
  /** The table model. */
  private DefaultTableModel tableModel;
  
  /** The table. */
  private JTable table;
  
  /**
   * Default constructor.
   * 
   * @param component  The component
   */
  public ImagingLabTestPanel(final LabComponent component) {
    super(component);
    this.component = component;
    
    // add title
    JLabel titleLabel = new JLabel("Imaging");
    titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
    titleLabel.setLocation(28, 34);
    titleLabel.setSize(150, 20);
    add(titleLabel);
    
    Image echoButtonImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Echo.png");
    Image echoButtonRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-EchoRol.png");
    Image echoButtonSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-EchoSel.png");
    
    // add lab request button
    ImageButton echoButton 
      = new ImageButton(echoButtonImage, echoButtonRolloverImage, echoButtonSelectedImage);
    echoButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        LabRequestAction action = new LabRequestAction();
        action.setLabTest(EchoLabTest.class);
        notifyActionListeners(action);
      }    
    });
    echoButton.setLocation(82, 34);
    echoButton.setSize(54, 22);
    add(echoButton);
    
    Image ultraButtonImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Ultra.png");
    Image ultraButtonRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-UltraRol.png");
    Image ultraButtonSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-UltraSel.png");
    
    // add lab request button
    ImageButton ultraButton 
      = new ImageButton(ultraButtonImage, ultraButtonRolloverImage, ultraButtonSelectedImage);
    ultraButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        LabRequestAction action = new LabRequestAction();
        action.setLabTest(UltrasoundLabTest.class);
        notifyActionListeners(action);
      }    
    });
    ultraButton.setLocation(138, 34);
    ultraButton.setSize(54, 22);
    add(ultraButton);

    Image xrayButtonImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-XRay.png");
    Image xrayButtonRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-XRayRol.png");
    Image xrayButtonSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-XRaySel.png");
    
    // add lab request button
    ImageButton xrayButton 
      = new ImageButton(xrayButtonImage, xrayButtonRolloverImage, xrayButtonSelectedImage);
    xrayButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        LabRequestAction action = new LabRequestAction();
        action.setLabTest(XRayLabTest.class);
        notifyActionListeners(action);
      }    
    });
    xrayButton.setLocation(196, 34);
    xrayButton.setSize(54, 22);
    add(xrayButton);
    
    // add scrollable area with list of imaging tests
    tableModel = new DefaultTableModel() {
      public int getColumnCount() {
        // Imaging has 2 values!
        return 2;
      }
      public int getRowCount() {
        return component.getResults().size();
      }
      public String getColumnName(int col) {
        if (col == 0) {
          return "Description";
        }
        else if (col == 1){
          return "Time"; 
        }
        else {
          // error condition
          return null;
        }
      }
      public Object getValueAt(int row, int col) {
        int index = component.getResults().size() - 1;
        if (col == 0) {
          return ((ImagingLabTest)component.getResults().get(index - row));
        }
        else if (col == 1) {
          return ((ImagingLabTest)component.getResults().get(index - row)).getTime();
        }
        else {
          // error condition
          return null;
        }
      }
      public void setValueAt(Object value, int row, int col) {
        fireTableCellUpdated(row, col);
      }
      public boolean isCellEditable(int row, int col) { 
        return col == 0;
      }
    };
    
    table = new RolloverTable(tableModel);
    
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setLocation(28, 59);
    scrollPane.setSize(215, 185);
    add(scrollPane);    
    
    // update table column styles
    TableCellRenderer headerStyle = new DefaultTableCellRenderer() {  
      public Component getTableCellRendererComponent(JTable table
           , Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(JLabel.CENTER);   
        setFont(getFont().deriveFont(Font.BOLD));
        setText((value == null) ? "" : value.toString());
        return this;
      }      
    };
    table.getColumnModel().getColumn(0).setHeaderRenderer(headerStyle);
    table.getColumnModel().getColumn(1).setHeaderRenderer(headerStyle);
    
    /**
     * Represents the action column.
     * 
     * @author caschwan
     */
    class ActionColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

      /** The action panel. */
      private JPanel actionPanel;
      
      /** The lab test associated. */
      private ImagingLabTest labTest;
      
      /** The link button. */
      private LinkButton viewButton;
      
      /** The image dialog. */
      private JDialog imageDialog;

      /**
       * The action column.
       */
      public ActionColumn() {
        // The panel
        actionPanel = new JPanel();
        actionPanel.setOpaque(true); //prior true
        actionPanel.setLayout(new FormLayout(
            "fill:1px:grow"
          , "16px"
        ));
        CellConstraints cc = new CellConstraints();
       
        // The edit button
        viewButton = new LinkButton();
        viewButton.setOpaque(false);        
        viewButton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            // get the lab test image
            ImagingLabTest labTest = (ImagingLabTest)getCellEditorValue();
            Image img = ImageLoader.getInstance().getImage("conf/image/interface/game/lab/" + labTest.getImageName());

            // shrink image as need
            int maxWidth = 700;
            int maxHeight = 500;
            if ((img.getWidth(null) > maxWidth) || (img.getHeight(null) > maxHeight)) {
              int width;
              int height;
              if ((((float)img.getWidth(null)) / maxWidth) > (((float)img.getHeight(null)) / maxHeight)) {
                width = maxWidth;
                height = img.getHeight(null) * maxWidth / img.getWidth(null);
              }
              else {
                width = img.getWidth(null) * maxHeight / img.getHeight(null);
                height = maxHeight;
              }
              Image smallImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
              Graphics g = smallImg.getGraphics();
              g.drawImage(img, 0, 0, null);
              img = smallImg;
            }
            
            // place a close button onto image and draw black rectangle around
            int width = img.getWidth(null);
            int height = img.getHeight(null);
            Image closeableImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = closeableImg.getGraphics();
            Image closeButton = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Close.gif");
            g.drawImage(img, 0, 0, null);
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, width - 1, height - 1);
            g.drawImage(closeButton, width - closeButton.getWidth(null) - 5, 5, null);
            img = closeableImg;
            
            // create image label to display image on dialog
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setSize(img.getWidth(null), img.getHeight(null));
            
            // open dialog           
            imageDialog = new AbstractDialog(ImagingLabTestPanel.this) {
              // not used
            };
            
            // lt add: create pane, add image to fix blank dialog
            JPanel contentPane = new JPanel();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(imageLabel, BorderLayout.CENTER);
            contentPane.setOpaque(true);
            imageDialog.setContentPane(contentPane);
            if ((System.getProperty("os.name").equals("Mac OS X")) 
              && (System.getProperty("os.version").startsWith("10.4"))) {
              imageDialog.setComponentZOrder(contentPane, 1); // lt add: Mac fix for z-order
            }
            imageDialog.setUndecorated(true);
            imageDialog.setSize(700, 500);
            imageDialog.pack();
            imageDialog.setLocationRelativeTo(JOptionPane.getFrameForComponent(ImagingLabTestPanel.this));
            imageDialog.addMouseListener(new MouseAdapter() {
              public void mousePressed(MouseEvent event) {
                imageDialog.setVisible(false);
                imageDialog.dispose();
                JOptionPane.getFrameForComponent(ImagingLabTestPanel.this).repaint();
              }
            });
            imageDialog.setVisible(true);
            fireEditingStopped();
          }          
        });
        actionPanel.add(viewButton, cc.xy(1, 1));
      }

      /**
       * Gets the table cell renderer.
       * 
       * @param table The table.
       * @param value The value.
       * @param isSelected is selected or not.
       * @param hasFocus has focus or not.
       * @param row The row.
       * @param column The column.
       * @return The component.
       */
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected
                                                   , boolean hasFocus, int row, int column) {
        // set data
        labTest = (ImagingLabTest)value;
        viewButton.setText(labTest.getDescription());
        
        return actionPanel;
      }

      /**
       * Gets the table cell editor component.
       * 
       * @param table The table.
       * @param value The value.
       * @param isSelected is selected or not.
       * @param row The row.
       * @param col The column.
       * @return The component. 
       */
      public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected
                                                 , int row, int col) {
        // set data
        labTest = (ImagingLabTest)value;
        viewButton.setText(labTest.getDescription());
        
        return actionPanel;
      }

      /**
       * Get the cell editor value.
       * 
       * @return The cell editor value.
       */
      public Object getCellEditorValue() {
        return labTest;
      }
    }
    table.getColumnModel().getColumn(0).setCellRenderer(new ActionColumn());
    table.getColumnModel().getColumn(0).setCellEditor(new ActionColumn());

    TableCellRenderer timeRenderer = new DefaultTableCellRenderer() {
      public Component getTableCellRendererComponent(JTable table
           , Object value, boolean isSelected, boolean hasFocus, int row,  int column) {
        setHorizontalAlignment(JLabel.CENTER);
        
        long seconds = (Long)value;
        int hours = (int)(seconds / 3600);
        setText("Time: " + hours + "h");
        return this;
      }
    };   
    table.getColumnModel().getColumn(1).setCellRenderer(timeRenderer); 

    // set column sizes (relative)
    for (int i = 0; i < tableModel.getColumnCount(); i++) {
      TableColumn column = table.getColumnModel().getColumn(i);
      if (i == 0) {
        column.setPreferredWidth(100);
      }
      else if (i == 1) {
        column.setPreferredWidth(50);
      }
    }       
  }
  
  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // add listener
    component.getResults().addLabTestListener(this);
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // remove listener
    component.getResults().removeLabTestListener(this);
    
    super.removeNotify();
  }

  /**
   * Called when the lab tests have been updated.
   */
  public void handleLabTestUpdate() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        tableModel.fireTableDataChanged();
      }
    });
  }
}
