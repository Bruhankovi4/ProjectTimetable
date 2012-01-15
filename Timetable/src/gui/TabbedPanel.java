package gui;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Lesson;
import model.Specialty;
import model.TimeList;
import accessor.DataDispatcher;


public class TabbedPanel extends JPanel implements ListSelectionListener{
	private static final long serialVersionUID = 1L;

	byte selectedWeekType=-1;
	private static final int rowCount=50;
	private List<Specialty> specialties;
	int selectCol=0,selectRow=0, selectTab=0; // изначально выделенные €чейки
	Lesson selectedLesson=null;
	JTabbedPane tabbedPane;
	private List<MyJTable> tables;	//собственно таблицы
	private JFrame parentFrame;
	
	public JFrame getParentFrame() {
		return parentFrame;
	}

	public TabbedPanel(JFrame frame) throws Exception{
		setLayout(null);
		parentFrame=frame;
		DataDispatcher dataDispatcher = new DataDispatcher((byte)2);
		dataDispatcher.viewData();
		specialties = dataDispatcher.getSpecialties();
		tabbedPane = new JTabbedPane();
		tables = new Vector<MyJTable>();
		createTabs();
		this.add(tabbedPane);
		setVisible(true);
	}
	
	private void initCells(Object[][] cells, Specialty specialty) {
		for(int l=1; l<cells[0].length; l++)
			((MyCell)cells[0][l]).init(specialty);
		for (int j=1; j<cells.length; j++)
			if ((j-1)%8 != 0)
				for (int k=1; k<cells[j].length; k++)
					((MyCell)cells[j][k]).init(specialty);
	}
	
	private void createTabs(){
		byte tabNum=0;
		for(Specialty specialty : specialties){
			String[] titles = new String[specialty.getYears().size()+1];
			Object[][] cells = new Object[rowCount][specialty.getYears().size()+1];
			//---------------------------------------------------
			cells[0][0] = "";
			for(int j=2; j<rowCount; j+=8) 				// пронумеровуем пары				
				for(Integer i=0; i<7; i++){
					Integer k = i+1;
					cells[j+1][0] = k;
				}
			//---------------------------------------------------
			for(int l=1; l<cells[0].length; l++)
				cells[0][l] = new MyCell(tabNum, 0, l, null);
			//---------------------------------------------------
			for (int j=1; j<cells.length; j++){
				if ((j-1)%8 == 0){						// skiping the underline
					for (int k=1; k<cells[j].length; k++)
						cells[j][k] = "";
					continue;
				}
				byte day = (byte)(((j-2)-(j-2)/8)/7+1);
				byte lessNum = (byte)(((j-2)-(j-2)/8)%7  /*+1 ???*/);
				for (int k=1; k<cells[j].length; k++) {	// time cells
					cells[j][k] = new MyCell(tabNum, j, k, TimeList.get(day, lessNum, (byte)0));
				}
			}
			//---------------------------------------------------
			titles[0] = "";
			for (int p=1; p<titles.length; p++){
				if (p==1 || p==4 || p==5)
					titles[p] = "" + specialty.getYears().get(p-1).getYear()+"-ый  урс";
				if (p==2 || p==6)
					titles[p] = "" + specialty.getYears().get(p-1).getYear()+"-ой  урс";
				if (p==3)
					titles[p] = "" + specialty.getYears().get(p-1).getYear()+"-ий  урс";
			}
			//---------------------------------------------------
			MyJTable table = new MyJTable(cells, titles, specialty);
			tables.add(table);
			table.setLayout(null);
			JScrollPane scrol = new JScrollPane(table);
			// fixme: problem with horizontal scroll
			tabbedPane.addTab(specialty.getName(), scrol);
			initCells(cells,specialty);
			tabNum++;
		}
	}
	
	void setSelected(int tab, int row, int col, byte selectedWeekType){
		selectCol=col;
		selectRow=row;
		selectTab=tab;
		this.selectedWeekType = selectedWeekType;
	}
	
	void setSelectedNull(){
		selectCol=-1;
		selectRow=-1;
		//this.selectedWeekType = -1;
	}
	
	public void addLessonToSelectedCell(Lesson lesson){
		if (tables.get(selectTab).getValueAt(selectRow, selectCol) instanceof  MyCell)
			((MyCell)tables.get(selectTab).getModel().getValueAt(selectRow, selectCol)).addLesson(lesson);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		//bigTable.setValueAt("asd", 0, 0);
		// TODO Auto-generated method stub
	}
	
	public void switchToTab(int tabNumber){  // jump to chosen tab
		tabbedPane.setSelectedIndex(tabNumber);
		selectTab = tabNumber;
	}
	
	public int getIndexOfSelectedTable() {
		return selectTab;
	}

	public List<Specialty> getSpecialties() {
		return specialties;
	}

	public MyJTable getSelectedTable() {
		return tables.get(selectTab);
	}

	public MyJTable getTableAt(int i) {
		return tables.get(i);
	}
}
