package model;

import java.util.List;
import java.util.Vector;

public class Subject implements Finishable{
	
	private Year parent;
	private String name;
	private List<SubjectWithType> subjectsWithType;		// improve it to subject
	
	public Subject(String name, List<SubjectWithType> subjectsWithType){
		this.name 	 = name;
		this.subjectsWithType = subjectsWithType;
		for (SubjectWithType subjectWithType : this.subjectsWithType)
			subjectWithType.setParent(this);
	}
	
	void setParent(Year parent) {
		this.parent = parent;
	}

	public Year getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public List<SubjectWithType> getSubjectsWithType() {
		return subjectsWithType;
	}
	
	public List<Lesson> getLessons(){
		Vector<Lesson> lessons = new Vector<Lesson>();
		for (SubjectWithType subjWithType : subjectsWithType)
			for (Lesson lesson : subjWithType.getLessons())
				lessons.add(lesson);
		return lessons;
	}

	public boolean isFinished() {
		boolean isFinished=true;
		for (SubjectWithType subjWithType : subjectsWithType)
			if (subjWithType.isFinished())
				isFinished = false;
		return isFinished;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
