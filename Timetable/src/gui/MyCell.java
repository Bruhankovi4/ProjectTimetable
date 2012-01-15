package gui;
import java.util.List;
import java.util.Vector;

import model.CollisionException;
import model.Lesson;
import model.Specialty;
import model.Time;
import model.TimeList;


public class MyCell implements model.Constants{
	
	private int tableNum, row, col, selectedX, selectedY, oldWidth;
	private Time time;
	private List<Lesson> lessons;
	private boolean[][] allowable;
	private boolean[][] occupieSpace;
	private byte[] UDLR = new byte[4];		// Up Down Left Right - UDLR - define rectangle in cell
	
	public MyCell(int tabelNum, int r, int c, Time time){
		this.time = time;
		this.tableNum = tabelNum;
		this.col = c;
		this.row = r;
		lessons = new Vector<Lesson>();
	}
	
	public void init(Specialty specialty){
		allowable = new boolean[2][specialty.getYears().get(col-1).getMaxFragmentCount()];
		for (int i=0; i<allowable.length; i++)
			for (int j=0; j<allowable[i].length; j++)
				allowable[i][j] = false;
		occupieSpace = new boolean[2][allowable[0].length];
		for (int i=0; i<occupieSpace.length; i++)
			for (int j=0; j<occupieSpace[i].length; j++)
				allowable[i][j] = false;
	}

	public boolean addLesson(Lesson lesson){
		byte[] m = MyCellRenderer.getUpDownLeftRight(allowable);
		if (canBeAdded(lesson, GUI.tabbedPanel.selectedWeekType) && isSelected(m[0], m[1], m[2], m[3])){
			lessons.add(lesson);
			try{
				lesson.setTime(TimeList.get(new Time(time.getDay(), time.getLessonNumber(), GUI.tabbedPanel.selectedWeekType)));
			}
			catch (CollisionException e){
				e.skip();/*implement me*/
			}
			lesson.setCell(this);
			PlaceInCell place = lesson.getPlaceInCell();
			for (int i=place.getBegin(); i<place.getEnd(); i++) {
				if (place.getVertical()==LOWER || place.getVertical()==FULL)	occupieSpace[0][i] = true;
				if (place.getVertical()==UPPER || place.getVertical()==FULL)	occupieSpace[1][i] = true;
			}
			/*implement other*/
			return true;
		}
		return false;
	}
	
	public void freeOccupie (Lesson lesson) {
			PlaceInCell place = lesson.getPlaceInCell();
			for (int i=place.getBegin(); i<place.getEnd(); i++) {
				if (place.getVertical()==LOWER || place.getVertical()==FULL)	occupieSpace[0][i] = false;
				if (place.getVertical()==UPPER || place.getVertical()==FULL)	occupieSpace[1][i] = false;
			}
	}

	private boolean canBeAdded (Lesson lesson, byte selectedWeekType) {
		if (!lesson.getParent().getParent().getParent().getAbbreviation(1).
				equals(
			GUI.tabbedPanel.getSpecialties().get(tableNum).getYear((byte)(col)).getAbbreviation(1)))
			return false;
		if (lesson.isFlasher())
			for (Lesson curLess : lessons) {
				if (selectedWeekType!=LOWER && selectedWeekType!=UPPER)
					selectedWeekType=ANY_FLASHER;
				if (lesson.getPlaceInCell().hasHorizontalCollisionWith(curLess.getPlaceInCell())) {
					if (selectedWeekType!=ANY_FLASHER && curLess.getTime().getWeekType()==selectedWeekType)
						return false;
					else if (selectedWeekType==ANY_FLASHER)
						selectedWeekType=curLess.getTime().getWeekType();
				}
			}
		else
			for (Lesson curLess : lessons) 
				if (lesson.getPlaceInCell().hasHorizontalCollisionWith(curLess.getPlaceInCell()))
					return false;
		return true;
	}
	
	// return byte... (Constants.SELECTED_FOR_...)
	private boolean canSelected () {
		if (GUI.tabbedPanel.getSelectedTable().curActiveColumn==col || GUI.tabbedPanel.getSelectedTable().curActiveColumn==-1)
			if (GUI.chooser.getCurLesson()!=null && canBeAdded(GUI.chooser.getCurLesson(),(byte)0))
				return true;
		return false;
	}

	public void selectMyYear () {
		GUI.chooser.setSpetiality(tableNum);
		GUI.chooser.setYear(col);
	}
	
	public void updateAllowable() {
		for (int i=0; i<allowable.length; i++)
			for (int j=0; j<allowable[i].length; j++)
				allowable[i][j] = false;
		if (GUI.tabbedPanel.getSelectedTable().curActiveColumn==col || GUI.tabbedPanel.getSelectedTable().curActiveColumn==-1) {
			if (GUI.chooser.getCurLesson()!=null && canBeAdded(GUI.chooser.getCurLesson(),(byte)0)) {
				PlaceInCell place = GUI.chooser.getCurLesson().getPlaceInCell();
				for (int i=place.getBegin(); i<place.getEnd(); i++)
					for (int j=0; j<2; j++)
						if (!occupieSpace[j][i])	allowable[j][i] = true;
			}
		}
	}
	
	public boolean[][] getAllowableMatrix() {
		updateAllowable();
		return allowable;
	}
	
	public void select(int x, int y) {
		updateAllowable();
		selectedX = x;	oldWidth = GUI.tabbedPanel.getTableAt(tableNum).getColumnWidth(col);
		selectedY = y;
		Lesson lesson;
		updateSelectedLesson();
		if (GUI.tabbedPanel.selectedLesson != null) {
			lesson = GUI.tabbedPanel.selectedLesson;
			GUI.chooser.setbut.setEnabled(false);
		}
		else {
			if (canSelected()) {
				lesson = GUI.chooser.getCurLesson();
				GUI.chooser.setbut.setEnabled(true);
			}
			else {
				GUI.chooser.setbut.setEnabled(false);
				GUI.tabbedPanel.setSelectedNull();
				return;
			}
		}
		if (lesson.isFlasher()) {
			if (y>getGridHeight())
				GUI.tabbedPanel.setSelected(GUI.tabbedPanel.tabbedPane.getSelectedIndex(), row, col, LOWER);
			else
				GUI.tabbedPanel.setSelected(GUI.tabbedPanel.tabbedPane.getSelectedIndex(), row, col, UPPER);
		}
		else
				GUI.tabbedPanel.setSelected(GUI.tabbedPanel.tabbedPane.getSelectedIndex(), row, col, FULL);
	}
	
	public double getGridWidth() {
		return ((double)GUI.tabbedPanel.getTableAt(tableNum).getColumnWidth(col))/allowable[0].length;
	}
	
	public int getGridHeight() {
		return GUI.tabbedPanel.getTableAt(tableNum).getRowHeight(3)/2;
	}
	
	public List<Lesson> getLessons() {
		return lessons;
	}

	public Time getTime() {
		return time;
	}
	
	public boolean isSelected() {
		if (GUI.tabbedPanel.selectTab==tableNum && GUI.tabbedPanel.selectCol==col && GUI.tabbedPanel.selectRow==row)
			return true;
		return false;
	}
	
	public boolean isSelected(byte up, byte down, byte left, byte right) {
		if (isSelected()){
			if (selectedY>up*getGridHeight()	&& 
				selectedY<down*getGridHeight()	&& 
				selectedX/((double)oldWidth/GUI.tabbedPanel.getTableAt(tableNum).getColumnWidth(col))>left*getGridWidth()	&& 
				selectedX/((double)oldWidth/GUI.tabbedPanel.getTableAt(tableNum).getColumnWidth(col))<right*getGridWidth())
				return true;
			GUI.tabbedPanel.setSelectedNull();
			GUI.chooser.setbut.setEnabled(false);			// bad code
		}
		return false;
	}
	
	static public void writeToArrLessonCoordinates(Lesson lesson, byte[] UDLR) {
		PlaceInCell place = lesson.getPlaceInCell();
		UDLR[0] = 0;
		UDLR[1] = 2;
		UDLR[2] = place.getBegin();
		UDLR[3] = place.getEnd();
		if (lesson.getTime().getWeekType() == UPPER )
			UDLR[1] = 1;
		if (lesson.getTime().getWeekType() == LOWER )
			UDLR[0] = 1;
	}
	
	private void updateSelectedLesson() {
		GUI.tabbedPanel.selectedLesson = null;
		int up, down, left, right;
		for (Lesson lesson : lessons) {
			writeToArrLessonCoordinates(lesson, UDLR);
			up = UDLR[0]*getGridHeight();
			down = UDLR[1]*getGridHeight();
			left = (int) (UDLR[2]*getGridWidth());
			right = (int) (UDLR[3]*getGridWidth());
			if (selectedY>up && selectedY<down && selectedX>left && selectedX<right) {
				GUI.tabbedPanel.selectedLesson = lesson;
				return;
			}
		}
	}
	
	
	// Coordinates
//========================================================
//------------- INNER CLASS 'PlaceInCell' ----------------

	static public class PlaceInCell {
		byte vertical;			// weekType
		byte horizontalBegin;	// begin of 
		byte horizontalEnd;
		public PlaceInCell (byte begin, byte end) {
			horizontalBegin = begin;
			horizontalEnd = end;
			vertical = FULL;
		}
		public PlaceInCell setVertical (byte weekType) {
			vertical = weekType;
			return this;
		}
		public byte getVertical() {
			return vertical;
		}
		public byte getBegin() {
			return horizontalBegin;
		}
		public byte getEnd() {
			return horizontalEnd;
		}
		public boolean hasHorizontalCollisionWith (PlaceInCell competitor) {
			if ((horizontalBegin>competitor.getBegin()	&& horizontalBegin<competitor.getEnd()) 
			  ||(horizontalEnd>competitor.getBegin()	&& horizontalEnd<competitor.getEnd())
			  ||(competitor.getBegin()>horizontalBegin	&& competitor.getBegin()<horizontalEnd)
			  ||(competitor.getEnd()>horizontalBegin	&& competitor.getEnd()<horizontalEnd))
				return true;
			return false;
		}
	}
}
