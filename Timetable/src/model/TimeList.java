package model;

import java.util.List;
import java.util.Vector;

public class TimeList {
	
	static private List<Time> timeList = new Vector<Time>();
	
	static public Time get(Time time){
		for (Time t : timeList)
			if (time.equalsTo(t))
				return t;
		timeList.add(time);
		return time;
	}
	
	static public Time get(byte day,byte lessonNumber, byte weekType){
		for (Time t : timeList)
			if (t.getDay()==day && t.getLessonNumber()==lessonNumber && t.getWeekType()==weekType)
				return t;
		Time time = new Time(day,lessonNumber,weekType);
		timeList.add(time);
		return new Time(day,lessonNumber,weekType);
	}
	
	static public void defaultList(){
		// implement me
	}

}
