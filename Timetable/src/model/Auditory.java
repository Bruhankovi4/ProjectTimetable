package model;

import java.util.List;
import java.util.Vector;

public class Auditory implements Constants {
	
	private byte type;
	private String title;
	private List<Lesson> lessons;
	
	public Auditory (byte type, String title){
		this.type  = type;
		this.title = title;
	}

	public byte getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}
	
	public List<Lesson> getLessons() {
		return lessons;
	}
	
	public Lesson getLessonAt(Time time){
		for (Lesson lesson : lessons)
			if (lesson.getTime() == time)
				return lesson;
		return null;
	}
	
	public boolean addLesson (Lesson lesson){
		if (lessons == null){
			lessons = new Vector<Lesson>();
			lessons.add(lesson);
			return true;
		}
		if (lessons.contains(lesson))
			return true;
		lessons.add(lesson);
		return true;
	}
	
	public void deleteLesson (Lesson lesson){
		if (lessons == null)
			return;
		lessons.remove(lesson);
	}
	
	public boolean isBusy(Time time){
		for (Lesson lesson : lessons)
			if (time == lesson.getTime() || time.hasCollisionWith(lesson.getTime()))
				return true;
		return false;
	}

	public boolean equalsTo(Auditory a) {
		return a.lessons == lessons  &&  a.title.equals(title)  &&  a.type == type;
	}
}
