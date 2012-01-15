package gui;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.CollisionException;
import model.Lesson;
import model.Time;


public class MyCell {
	private TabbedPanel parentTabbedPanel;
	private int tableNum, row, col;
	private Time time;
	private List<Lesson> lessons;
	private List<JPanel> panels;
	private JPanel mainPanel;
	
	public MyCell(TabbedPanel p, int tabelNum, int r, int c, Time time){
		this.time = time;
		this.parentTabbedPanel = p;
		this.tableNum = tabelNum;
		this.col = c;
		this.row = r;
		lessons = new Vector<Lesson>();
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(122,44));
		mainPanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				parentTabbedPanel.setSelected(tableNum, row, col, 0);
			}
			public void mouseClicked(MouseEvent arg0) {
				parentTabbedPanel.setSelected(tableNum, row, col, 0);
			}
		});	// listener should to have every panel from panels 
	}

	public boolean addLesson(Lesson lesson){
		try{
			lesson.setTime(time);
		}
		catch (CollisionException exception){
			// catch... implement me
			return false;
		}
		boolean canAdd=true;
		int subgroupsCount = lesson.getSubgroupCount();
		if (subgroupsCount==1)
			return lessons.size()==0;
		int groupsCount = lesson.getGroupCount();
		if (groupsCount==subgroupsCount)
			for(Lesson curLesson : lessons)
				for (int i=0; i<curLesson.getGroupsList().length; i++)
					if (lesson.getSubgroupNumber() == curLesson.getGroupsList()[i])
						canAdd = false;
		if (subgroupsCount>groupsCount){
			int groupNum=lesson.getGroupsList()[0];
			for(Lesson curLesson : lessons){
				int fragmentation = curLesson.getSubgroupCount()/curLesson.getGroupCount(); 
				int newLesFragm = lesson.getSubgroupCount()/lesson.getGroupCount(); 
				if (fragmentation == 0)
					return false;
				if (fragmentation==newLesFragm){
					if (curLesson.getSubgroupNumber() == lesson.getSubgroupNumber())
						return false;
				}
				else{
					// i hope, that it will work.. Magic
					if (fragmentation>newLesFragm){
						int x = fragmentation/newLesFragm+fragmentation%newLesFragm;
						int y = x*(lesson.getSubgroupNumber()/newLesFragm+lesson.getSubgroupNumber()%newLesFragm);
						int numInGroupNewLess = (lesson.getSubgroupNumber()%newLesFragm==0)?newLesFragm:lesson.getSubgroupNumber()%newLesFragm;
						int beginInterval = y - (fragmentation-1-numInGroupNewLess*x);
						int endInterval = y - (fragmentation-1-numInGroupNewLess*x-x+1);
						if (curLesson.getSubgroupNumber()>beginInterval && curLesson.getSubgroupNumber()<endInterval)
							return false;
					}
					else{
						int x = newLesFragm/fragmentation+newLesFragm%fragmentation;
						int y = x*(curLesson.getSubgroupNumber()/fragmentation+curLesson.getSubgroupNumber()%fragmentation);
						int numInGroupCurLess = (curLesson.getSubgroupNumber()%fragmentation==0)?fragmentation:curLesson.getSubgroupNumber()%fragmentation;
						int beginInterval = y - (newLesFragm-1-numInGroupCurLess*x);
						int endInterval = y - (newLesFragm-1-numInGroupCurLess*x-x+1);
						if (lesson.getSubgroupNumber()>beginInterval && lesson.getSubgroupNumber()<endInterval)
							return false;
					}
				}
			}
		}
		lessons.add(lesson);
		updatePanels();
		return true;
	}
	
	public Component getPanel(){
		return mainPanel;
	}
	
	private void updatePanels(){
	//	if(lessons.size()==1)
			mainPanel.add(new JLabel(lessons.get(0).toStringForTable()));
		// implement other cases
	}
}
