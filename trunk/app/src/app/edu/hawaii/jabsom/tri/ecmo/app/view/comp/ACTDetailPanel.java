package edu.hawaii.jabsom.tri.ecmo.app.view.comp;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swingx.JXTable;

import king.lib.access.ImageLoader;

import edu.hawaii.jabsom.tri.ecmo.app.control.action.ACTRequestAction;
import edu.hawaii.jabsom.tri.ecmo.app.gui.ImageButton;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.ACTComponent;
import edu.hawaii.jabsom.tri.ecmo.app.model.comp.ACTComponent.ACT;
import edu.hawaii.jabsom.tri.ecmo.app.model.lab.LabTestList.LabTestListener;

/**
 * The detail panel. 
 *
 * @author   Christoph Aschwanden
 * @since    September 30, 2008
 */
public class ACTDetailPanel extends DetailPanel implements LabTestListener {

  /** The detail image. */
  private Image detailImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Detail-Lab.png");
  
  /** The table model. */
  private DefaultTableModel tableModel;
  
  /** The table. */
  private JXTable table;
  
  
  /**
   * Constructor for panel.
   * 
   * @param component  The associated component.
   */
  protected ACTDetailPanel(final ACTComponent component) {
    super(component);
    
    // set size and location
    setLocation(289, 81);
    setSize(320, 320);
    
    // set layout and look
    setLayout(null);
    setOpaque(false);
    
    // add title
    JLabel titleLabel = new JLabel("ACT");
    titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
    titleLabel.setLocation(28, 34);
    titleLabel.setSize(150, 20);
    add(titleLabel);

    Image requestButtonImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-Lab.png");
    Image requestButtonRolloverImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-LabRol.png");
    Image requestButtonSelectedImage = ImageLoader.getInstance().getImage("conf/image/interface/game/Btn-LabSel.png");
    
    // add lab request button
    ImageButton requestButton 
      = new ImageButton(requestButtonImage, requestButtonRolloverImage, requestButtonSelectedImage);
    requestButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        ACTRequestAction action = new ACTRequestAction();
        notifyActionListeners(action);
      }    
    });
    requestButton.setLocation(173, 34);
    requestButton.setSize(70, 20);
    add(requestButton);

    // add table
    tableModel = new DefaultTableModel() {
      public int getColumnCount() {
        // ACT has 2 values!
        return 2;
      }
      public int getRowCount() {
        return component.getACTs().size();
      }
      public String getColumnName(int col) {
        if (col == 0) {
          return "Time";
        }
        else if (col == 1){
          return "ACT"; 
        }
        else {
          return " ";
        }
      }
      public Object getValueAt(int row, int col) {
        int index = component.getACTs().size() - 1;
        if (row <= index) {
          switch (col) {
            case 0:
              return ((ACT)component.getACTs().get(index - row)).getTime();
            case 1:
              return Math.round(((ACT)component.getACTs().get(index - row)).getValue());
            default:
              // error condition
              return null;
          }
        }
        else {
          switch (col) {
            case 0:
              return "N/A";
            case 1:
              return "N/A";
            default:
              // error condition
              return null;
          }
        }
      }
      public boolean isCellEditable(int row, int col) { 
        return false;
      }
    };
    
    table = new JXTable(tableModel);
    updateTable();
    
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setLocation(28, 59);
    scrollPane.setSize(215, 185);
    add(scrollPane);    
  }

  
  /**
   * Called when panel is added.
   */
  @Override
  public void addNotify() {
    super.addNotify();
    
    // add listener
    // component.getACTs().addLabTestListener(this);
  }
  
  /**
   * Called when panel is removed.
   */
  @Override
  public void removeNotify() {
    // remove listener
    // component.getACTs().removeLabTestListener(this);
    
    super.removeNotify();
  }

  /**
   * Called when the lab tests have been updated.
   */
  public void handleLabTestUpdate() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        tableModel.fireTableStructureChanged();       
        tableModel.fireTableDataChanged();
        updateTable();
      }
    });
  }
  
  /**
   * Update the table.
   */
  private void updateTable() {    
    TableCellRenderer firstRowStyle = new DefaultTableCellRenderer() {  
      public Component getTableCellRendererComponent(JTable table,
          Object value, boolean isSelected, boolean hasFocus, int row,
          int column) {
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(JLabel.CENTER);   
        setFont(getFont().deriveFont(Font.BOLD));
        setText((value == null) ? "" : value.toString());
        return this;
      }      
    };
    
    TableCellRenderer otherRowStyle = new DefaultTableCellRenderer() {
      public Component getTableCellRendererComponent(JTable table,
          Object value, boolean isSelected, boolean hasFocus, int row,
          int column) {
        setHorizontalAlignment(JLabel.CENTER);
        setText((value == null) ? "" : value.toString());
        return this;
      }
    };

    table.getColumn(0).setHeaderRenderer(firstRowStyle);
    table.getColumn(1).setHeaderRenderer(firstRowStyle);
    table.getColumn(0).setCellRenderer(otherRowStyle);
    table.getColumn(1).setCellRenderer(otherRowStyle); 
    table.setFillsViewportHeight(true);
    table.setHorizontalScrollEnabled(false); 
  }
  
  /**
   * Called when the component got updated.
   */
  public void handleUpdate() {
    repaint();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        tableModel.fireTableStructureChanged();       
        tableModel.fireTableDataChanged();
        updateTable();
      }
    });    
  }
  
  /**
   * Paints this component.
   * 
   * @param g  Where to draw to.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    // set antialised text
    Graphics2D g2 = (Graphics2D)g;
    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    // draw base
    g2.drawImage(detailImage, 0, 0, this);
  }
}
