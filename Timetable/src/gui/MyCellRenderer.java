package gui;
import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import model.Lesson;
import model.Time;
import model.Year;


public class MyCellRenderer extends DefaultTableCellRenderer implements model.Constants {
	
	// это должно аккуратно следить за измени€ми в €чейках, и если перерисовка не требуетс€, то нужно избегать еЄ..
	// точнее не перерисовка, а создание новых объектов MyCellPanel
	
	private static final long serialVersionUID = 1L;
	private JPanel panel = new JPanel();
	private JPanel cellPanel;
	private JPanel allowablePanel;
	private JPanel selectionPanel;
	private JPanel lessonPanel;
	static Color colorAllowable = new Color(225,235,230);
	static Color colorBusyTeacher = new Color(242,210,205);
	static Color colorSelectionBorder = new Color(205,215,210);
	static Color colorNormalLesson = new Color(250,255,255);
	static Color colorSkipedCollision = new Color(245,215,215);
	static Color colorHeadBlue = new Color(220,225,235);
	static Color colorHeadRed = new Color(235,215,215);
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (column==0 && row>0)
			return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (column>0 && row==0){
			return formColumnHead(
								((MyJTable)table).getMySpecialty().getYears().get(column-1), 
								table.getColumnModel().getColumn(column).getWidth(), 
								table.getRowHeight(row));
		}
		if (value instanceof MyCell)
			return getMyCellComponent(table, (MyCell)value);
		return panel;
	}
	
	private Component getMyCellComponent (JTable table, MyCell cell) {
		boolean[][] allowable = cell.getAllowableMatrix();
		byte[] UDLR = getUpDownLeftRight(allowable);
		cellPanel = new JPanel();
		cellPanel.setSize((int) (allowable[0].length*cell.getGridWidth()), 2*cell.getGridHeight());
		cellPanel.setLayout(null);
		// FULL and ANY_FLASHED is different!!! improve it
	/*	byte weekType;
		if (GUI.chooser.getCurLesson()!=null && GUI.chooser.getCurLesson().isFinished())
			weekType = ((UDLR[1]-UDLR[0]==(byte)2)? ANY_FLASHER- : ((UDLR[1]==(byte)1)? UPPER : LOWER));*/		// fixme
		addAllowablePanel(cell, UDLR);
		addLessonPanel(cell, UDLR);
		return cellPanel;
	}
	
	private JPanel formColumnHead (Year year, int width, int height) {
		byte groupCount = year.getGroupCount();
		JPanel head = new JPanel(null);
		JLabel label;
		label = new JLabel(year.getAbbreviation(1));
		head.add(label);
		label.setBounds(
				width/groupCount/2-label.getText().length()*4, 
				0, 
				width/groupCount, 
				height);
		label.setForeground(darkBlue);
		for (int i=1; i<groupCount; i++) {
			JPanel separator = new JPanel();
			separator.setBackground(darkBlue);
			separator.setBounds(width/groupCount*i, 0, 1, height);
			head.add(separator);
			label = new JLabel(year.getAbbreviation(i+1));
			head.add(label);
			label.setBounds(
					width/groupCount/2-label.getText().length()*4+width/groupCount*i, 
					0, 
					width/groupCount, 
					height);
			label.setForeground(darkBlue);
		}
		if (year.getCollisionsCount()==0)
			head.setBackground(colorHeadBlue);
		else
			head.setBackground(colorHeadRed);
		return head;
	}
	
	private void addAllowablePanel (MyCell cell, byte[] UDLR) {
		if (UDLR[2]!=UDLR[3]) {
			allowablePanel = new JPanel();
			allowablePanel.setLayout(null);
			cellPanel.add(allowablePanel);
			allowablePanel.setBackground(colorAllowable);
			if (GUI.chooser.getCurTeacher()!=null) {
				if (GUI.chooser.getCurTeacher().isBusy(new Time(cell.getTime().getDay(),cell.getTime().getLessonNumber(),FULL)))		// fixme
					allowablePanel.setBackground(colorBusyTeacher);
			}
			allowablePanel.setBounds(
					(int) (UDLR[2]*cell.getGridWidth()), 
					UDLR[0]*cell.getGridHeight(), 
					(int) ((UDLR[3]-UDLR[2])*cell.getGridWidth()), 
					(UDLR[1]-UDLR[0])*cell.getGridHeight()
					);
			addSelectionInAllowable(cell, UDLR, allowablePanel);
		}
	}
	
	private void addSelectionInAllowable(MyCell cell, byte[] UDLR, JPanel allowable) {
		if (cell.isSelected(UDLR[0],UDLR[1],UDLR[2],UDLR[3])) {
			if (GUI.tabbedPanel.selectedWeekType==FULL)
				selectPanel(allowablePanel);
			if (GUI.tabbedPanel.selectedWeekType==UPPER || GUI.tabbedPanel.selectedWeekType==LOWER){
				selectionPanel = new JPanel();
				allowablePanel.add(selectionPanel);
				selectionPanel.setBackground(allowable.getBackground());
				selectPanel(selectionPanel);
				if (GUI.tabbedPanel.selectedWeekType==LOWER)
					selectionPanel.setBounds(
							0, 
							cell.getGridHeight(), 
							allowablePanel.getWidth(), 
							cell.getGridHeight()
							);
				else
					selectionPanel.setBounds(
							0, 
							0, 
							allowablePanel.getWidth(), 
							cell.getGridHeight()
							);
			}
		}
	}
	
	private void addLessonPanel (MyCell cell, byte[] UDLR) {
		for (Lesson lesson : cell.getLessons()) {
			MyCell.writeToArrLessonCoordinates(lesson, UDLR);
			lessonPanel = new JPanel();
			cellPanel.add(lessonPanel);
			lessonPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			if (lesson.getCollisions()!=null && lesson.getCollisions().size()!=0)
				lessonPanel.setBackground(colorSkipedCollision);
			else
				lessonPanel.setBackground(colorNormalLesson);
			if (lesson == GUI.tabbedPanel.selectedLesson){
				int color;
				lessonPanel.setBorder(BorderFactory.createLineBorder(
						new Color(((color = lessonPanel.getBackground().getRed()-65)>0)?color:0,
								((color = lessonPanel.getBackground().getGreen()-65)>0)?color:0,
								((color = lessonPanel.getBackground().getBlue()-65)>0)?color:0), 2));
				lessonPanel.setBackground(
						new Color(((color = lessonPanel.getBackground().getRed()-13)<255)?color:255,
								((color = lessonPanel.getBackground().getGreen()-13)<255)?color:255,
								((color = lessonPanel.getBackground().getBlue()-13)<255)?color:255));
			//	selectPanel(lessonPanel);
			}
			lessonPanel.setBounds(
					(int) (UDLR[2]*cell.getGridWidth()), 
					UDLR[0]*cell.getGridHeight() + ((UDLR[0]==0&&(UDLR[1]-UDLR[0])==1)?-1:0), 
					(int) ((UDLR[3]-UDLR[2])*cell.getGridWidth()), 
					(UDLR[1]-UDLR[0])*cell.getGridHeight()+ ((UDLR[0]==0&&(UDLR[1]-UDLR[0])==1)?1:0)
					);
			lessonPanel.setLayout(null);
			JLabel label = new JLabel(lesson.toStringForTable());
			label.setVerticalAlignment(TOP);
			label.setHorizontalTextPosition(LEFT);
			lessonPanel.add(label);
			label.setBounds(2, 1, lessonPanel.getWidth()-4, lessonPanel.getHeight()-1);
		}
	}
	
	static private void selectPanel(JPanel selectionPanel) {
				int color;
				selectionPanel.setBorder(BorderFactory.createLineBorder(
						new Color(((color = selectionPanel.getBackground().getRed()-48)>0)?color:0,
								((color = selectionPanel.getBackground().getGreen()-48)>0)?color:0,
								((color = selectionPanel.getBackground().getBlue()-48)>0)?color:0), 2));
				selectionPanel.setBackground(
						new Color(((color = selectionPanel.getBackground().getRed()+13)<255)?color:255,
								((color = selectionPanel.getBackground().getGreen()+13)<255)?color:255,
								((color = selectionPanel.getBackground().getBlue()+13)<255)?color:255));
	}
	
	static byte[] getUpDownLeftRight(boolean[][] matrix) {
		int i;
		byte up=0, down=2, left=0, right=0;
		for (i=0; i<matrix[0].length && !matrix[0][i] && !matrix[1][i]; i++);
		if (i!=matrix[0].length) {			// allowable is not empty
			if (!matrix[1][i])
				up=1;
			if (!matrix[0][i])
				down=1;
		}
		left=(byte)i;
		for (; i<matrix[up].length && (matrix[0][i] || matrix[1][i]); i++);
		right=(byte)i;
		return new byte[]{up, down, left, right};
	}
}


