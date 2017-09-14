/**
 * 
 */
package table_component;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * @author lzy05
 *
 */


public class ButtonRenderer extends JButton implements TableCellRenderer {

	  public ButtonRenderer() {
	    setOpaque(true);
	  }

	  public Component getTableCellRendererComponent(JTable table, Object value,
	      boolean isSelected, boolean hasFocus, int row, int column) {
	    if (isSelected) {
	      setForeground(table.getSelectionForeground());
	      setBackground(table.getSelectionBackground());
	    } else {
	      setForeground(table.getForeground());
	      setBackground(UIManager.getColor("Button.background"));
	    }
	    setText((value == null) ? "" : value.toString());
	    return this;
	  }
	}


