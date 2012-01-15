package gui;
import java.awt.Color;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


public class MyJTable extends JTable {

	private static final int rowCount=50;
	private static final long serialVersionUID = 1L;
	private MyCellRenderer myCellRenderer;
	
	public MyJTable(Object[][] cells, String[] titles){
		super(cells, titles);
		
		TableColumn firstColumn = this.getColumnModel().getColumn(0); // размер первого столбца
		firstColumn.setMinWidth(15);
		firstColumn.setMaxWidth(15);
		firstColumn.setPreferredWidth(15);			
		for(int j=2; j<rowCount; j+=8) 			// пронумеровуем пары				
			for(Integer i=0; i<7; i++){
				Integer k = i+1;
				String s = " "+k.toString();
				this.setValueAt(s, j+i, 0);
			}
		for(int j=0; j<rowCount;j++)
			this.setRowHeight(j, 22*2);
		this.setRowHeight(1, 3);
		for(int i=2; i<rowCount;i+=8)				//отделяем дни недели	
			this.setRowHeight(i+7,3);		
		myCellRenderer = new MyCellRenderer();
		setColumnSelectionAllowed(false);
		setRowSelectionAllowed(false);
		setCellSelectionEnabled(false);
		setVisible(true);
	}
	
	public TableCellRenderer getCellRenderer(int row, int column) {	
        if (((row-1)%8 == 0) && row>0) {					// отделяем дни недели цветом
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    		renderer.setBackground(new Color(0, 0, 0));
            return renderer;
        }
        return myCellRenderer;
    }
}
