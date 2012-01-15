package model;

import java.util.List;
import java.util.Vector;

public class Teacher {

	String firstName, lastName, fatherName;
	// Cathedra cathedra, phoneNums, email, degree;
	List <Lesson> lessons = new Vector<Lesson>();

	public Teacher(String firstName, String lastName, String fatherName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.fatherName = fatherName;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getFatherName() {
		return fatherName;
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
	
	public boolean addLesson(Lesson lesson){
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
	
	public void deleteLesson(Lesson lesson){
		if (lessons == null)
			return;
		lessons.remove(lesson);
	}
	
	public boolean isBusy(Time time){
		for (Lesson lesson : lessons){
			if (lesson.getTime()!=null && (time == lesson.getTime() || time.hasCollisionWith(lesson.getTime())))
				return true;
		}
		return false;
	}
	
	public String toString(){
		return lastName + " " + firstName.charAt(0) + "." + fatherName.charAt(0) + "."; 
	}
}
