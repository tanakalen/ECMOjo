package edu.hawaii.jabsom.tri.ecmo.app.view.lab;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

import edu.hawaii.jabsom.tri.ecmo.app.gui.LinkButton;
import edu.hawaii.jabsom.tri.ecmo.app.gui.RolloverTable;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.LabComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.ImagingLabTest;
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
        actionPanel.setOpaque(true);
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
            // get the lab test
            ImagingLabTest labTest = (ImagingLabTest)getCellEditorValue();
            Image image = ImageLoader.getInstance().getImage("conf/image/interface/game/lab/" + labTest.getImageName());
            JLabel imageLabel = new JLabel(new ImageIcon(image));
            
            // open dialog           
            imageDialog = new AbstractDialog(viewButton) {
              // not used
            };
            imageDialog.getContentPane().add(imageLabel);
            imageDialog.setUndecorated(true);
            imageDialog.setSize(700, 500);
            imageDialog.setLocationRelativeTo(JOptionPane.getFrameForComponent(viewButton));
            imageDialog.addMouseListener(new MouseAdapter() {
              public void mousePressed(MouseEvent event) {
                imageDialog.setVisible(false);
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
        setText("Hours: " + hours);
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
