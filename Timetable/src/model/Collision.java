package model;

import java.util.List;
import java.util.Vector;


public class Collision implements Constants {

	private Teacher teacher=null;
	private Auditory auditory=null;
	private List<Lesson> lessons;
	private byte mark;
	
	public Collision (
			Teacher teacher, 
			Lesson lesson1, 
			Lesson lesson2, 
			byte mark) {
		this.teacher = teacher;
		lessons = new Vector<Lesson>();
		lessons.add(lesson1);
		lessons.add(lesson2);
		this.mark = mark;
	}

	public Collision (
			Auditory auditory, 
			Lesson lesson1, 
			Lesson lesson2, 
			byte mark) {
		this.auditory = auditory;
		lessons = new Vector<Lesson>();
		lessons.add(lesson1);
		lessons.add(lesson2);
		this.mark = mark;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public Auditory getAuditory() {
		return auditory;
	}
	
	public void addLesson(Lesson lesson) {
		lessons.add(lesson);
	}
	
	public Time getTime() {
		return lessons.get(0).getTime();
	}
	
	public List<Lesson> getLessons() {
		return lessons;
	}
	
	public byte getMark() {
		return mark;
	}
	
	public void update() {
		boolean isActual;
		for (Lesson lesson1 : lessons){
			isActual = false;
			for (Lesson lesson2 : lessons)
				if (lesson1.getTime()!=null && lesson1!=lesson2 && lesson1.getTime()==lesson2.getTime())
					isActual=true;
			if(!isActual){
				lessons.remove(lesson1);				// !!! UNSAFE CODE
				lesson1.removeCollision(this);
			}
		}
	}
	
	public boolean isFullyFixed() {
		return lessons.size() < 2;
	}
	
	public void removeMe() {
		for (Lesson lesson : lessons)
			lesson.removeCollision(this);
	}
}
