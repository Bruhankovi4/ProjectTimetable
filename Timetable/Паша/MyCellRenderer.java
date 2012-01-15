package gui;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class MyCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 2998382168146854729L;
	private JPanel panel = new JPanel();
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (column==0 && row>0)
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (column>0 && row==0){
			// implement me (groups titles)
			JLabel jlabel = new JLabel(" Groups");
			jlabel.setBackground(new Color(194,198,205));
			return jlabel;
		}
		if (value.getClass() == MyCell.class)
			return ((MyCell)value).getPanel();
		return panel;
		//return new MyCell(new Time((byte)(7/8+1),(byte)(2%8),Constants.STANDART)).getPanel();
	}
}


