package gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import model.Specialty;


public class MyJTable extends JTable implements model.Constants {

	private static final int rowCount=50;
	private static final long serialVersionUID = 1L;
	private MyCellRenderer myCellRenderer;
	private Specialty mySpecialty;
	int curActiveColumn=-1;
	Object[][] cells;
	
	public MyJTable(Object[][] cells, String[] titles, Specialty mySpecialty){
		super(cells, titles);
		this.cells = cells;
		this.mySpecialty = mySpecialty;
		TableColumn column = getColumnModel().getColumn(0); // размер первого столбца
		column.setMinWidth(17);
		column.setMaxWidth(17);
		column.setPreferredWidth(17);
		getColumnModel().getColumn(0).setHeaderRenderer(new MyHeadRenderer());
		for (int i=1; i<titles.length; i++) {
			getColumnModel().getColumn(i).setMinWidth(100);
			getColumnModel().getColumn(i).setHeaderRenderer(new MyHeadRenderer());
		}
		for(int j=2; j<rowCount; j+=8) 			// пронумеровуем пары + дни недели
			for(Integer i=0; i<7; i++){
				Integer k = i+1;
				String s = "";
				switch ((j/8+1) + ((i>0)?12:0)) {
				case 1:		s+="<html><b>пн</b></html>";	break;
				case 2:		s+="<html><b>вт</b></html>";	break;
				case 3:		s+="<html><b>ср</b></html>";	break;
				case 4:		s+="<html><b>чт</b></html>";	break;
				case 5:		s+="<html><b>пт</b></html>";	break;
				case 6:		s+="<html><b>сб</b></html>";	break;
				default:	s+=" " + k.toString(); break;
				}
				this.setValueAt(s, j+i, 0);
			}
		for(int j=0; j<rowCount;j++)
			this.setRowHeight(j, 38);		// 22
		this.setRowHeight(1, 3);
		for(int i=2; i<rowCount;i+=8)				//отделяем дни недели	
			this.setRowHeight(i+7,3);		
		myCellRenderer = new MyCellRenderer();
		for (int j=0; j<cells[0].length; j++)
			getColumnModel().getColumn(j).setCellEditor(new MyCellEditor(new JCheckBox()));
		addMouseListener(new MyMouseListener());
		getTableHeader().setReorderingAllowed(false);
		addKeyListener(new MyKeyListener());
		setVisible(true);
	}
	
	public TableCellRenderer getCellRenderer(int row, int column) {	
        if (((row-1)%8 == 0) && row>0) {					// отделяем дни недели цветом
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    		renderer.setBackground(darkBlue);
            return renderer;
        }
        if (((row-2)%8 == 0) && row>0 && column==0) {		// пн вт ср чт пт сб
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    		renderer.setBackground(new Color(240, 240, 240));
            return renderer;
        }
        if (row==0 && column==0) {							//  0 0
        	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    		renderer.setBackground(new Color(220,225,235));
            return renderer;
        }
        return myCellRenderer;
    }
	
	public Specialty getMySpecialty (){
		return mySpecialty;
	}
	
	public void switchToColumn(int column){ // получаем на вход курс
		curActiveColumn=column;
	}
	
	static private class MyHeadRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		JPanel line = new JPanel();
		public MyHeadRenderer(){
			setBackground(new Color(200,205,215));
			line.setBackground(darkBlue);
			add(line);
			setForeground(darkBlue);
			setHorizontalAlignment(CENTER);
		}
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			line.setBounds(table.getColumnModel().getColumn(column).getWidth()-1, 0, 1, 22);
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}
	
	static private class MyCellEditor extends DefaultCellEditor {
		private static final long serialVersionUID = 1L;
		public MyCellEditor(JCheckBox arg0) {
			super(arg0);
			clickCountToStart=1236;
		}
		public boolean isCellEditable(EventObject e) {
			return false;
		}
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			return null;
		}
	}
	
	private class MyMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			select(e);
			// without lesson selection
		}
		/*public void mouseEntered(MouseEvent e) {
			select(e);
		}*/
		public void mousePressed(MouseEvent e) {
			select(e);
			if (e.getClickCount() == 2) {
				if (GUI.tabbedPanel.selectedLesson != null) {
					try {
						GUI.chooser.setLesson(GUI.tabbedPanel.selectedLesson);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else if (GUI.tabbedPanel.selectCol == -1) {
					int row, col;
					int x, y;
					x = e.getX();
					y = e.getY();
					x-=getColumnWidth(0);
					y-=getRowHeight(0)+3;
					if (x>0 && y>0 && y%(getRowHeight(3)*7+3)<getRowHeight(3)*7) {
						col = x/getColumnWidth(1);
						row = (y-y/(getRowHeight(3)*7+3)*3)/getRowHeight(3);
						((MyCell)getValueAt(row+2+y/(getRowHeight(3)*7+3), col+1)).selectMyYear();
						repaint();
					}
				}
			}
			// with lesson selection
		}
		public void select(MouseEvent e) {
			int row, col;
			int x, y;
			x = e.getX();
			y = e.getY();
			x-=getColumnWidth(0);
			y-=getRowHeight(0)+3;
			if (x>0 && y>0 && y%(getRowHeight(3)*7+3)<getRowHeight(3)*7) {
				col = x/getColumnWidth(1);
				row = (y-y/(getRowHeight(3)*7+3)*3)/getRowHeight(3);
				((MyCell)getValueAt(row+2+y/(getRowHeight(3)*7+3), col+1)).select(
						x%getColumnWidth(1), (y-y/(getRowHeight(3)*7+3)*3)%getRowHeight(3));
				repaint();
				//if (isSelectedLesson)		// it.. for  selecting for deleting...
				//	"Добавить".setEnable(false);
				//else
				//	"Добавить".setEnable(true);
			}
		}
	}
	
	private class MyKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (GUI.tabbedPanel.selectedLesson != null  &&  e.getKeyCode()==KeyEvent.VK_DELETE) {
				GUI.tabbedPanel.setSelectedNull();
				GUI.tabbedPanel.selectedLesson.deleteFromTimeTable();
				GUI.tabbedPanel.selectedLesson = null;
				repaint();
			}
			else if (e.getKeyCode()==KeyEvent.VK_ENTER) {
				if (GUI.chooser.setbut.isEnabled()) {
					GUI.tabbedPanel.addLessonToSelectedCell(GUI.chooser.curLesson);
					GUI.tabbedPanel.repaint();
				}
			}
		}
	}
	
	public int getColumnWidth(int col) {
		return getColumnModel().getColumn(col).getWidth();
	}
}
