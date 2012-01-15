package model;

import java.util.List;

public class SubjectWithType implements Constants {
	
	private Subject parent;
	private byte type;					// replace it to new entity
	private byte subgroupsCount;
	private byte lessonsInTwoWeeks;
	private byte requiredAuditoryType;	// replace it to new entity
	List <Teacher> possibleTeachers;
	private List <Lesson> lessons;			// lesson.size = subgroupCount*lessonsInTwoWeeks
	
	public SubjectWithType (
			byte type, 
			byte subgroupCount,
			byte lessonsInTwoWeeks,
			byte requiredAuditoryType,
			List <Teacher> possibleTeachers, 
			List<Lesson> lessons) {
		this.type = type;
		this.subgroupsCount = subgroupCount;
		this.lessonsInTwoWeeks = lessonsInTwoWeeks;
		this.requiredAuditoryType = requiredAuditoryType;
		this.possibleTeachers = possibleTeachers;
		this.lessons = lessons;
		for (Lesson lesson : this.lessons)
			lesson.setParent(this);
	}
	
	void setParent(Subject parent) {
		this.parent = parent;
	}
	
	public Subject getParent() {
		return parent;
	}

	public byte getType() {
		return type;
	}

	public byte getSubgroupsCount() {
		return subgroupsCount;
	}

	public byte getLessonsInTwoWeeks() {
		return lessonsInTwoWeeks;
	}

	public byte getRequiredAuditoryType() {
		return requiredAuditoryType;
	}

	public List<Teacher> getPossibleTeachers() {
		return possibleTeachers;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}
	
	public boolean isFinished(){
		boolean isFinished=true;
		for (Lesson lesson : lessons)
			if (lesson.isFinished())
				isFinished = false;
		return isFinished;
	}
}
