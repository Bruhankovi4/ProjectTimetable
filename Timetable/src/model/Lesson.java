package model;

import gui.MyCell;

import java.util.List;
import java.util.Vector;


public class Lesson implements Constants, Finishable{
		
	private List<Collision> collisions;		// implement work with me

	private SubjectWithType parent;
	private byte subgroupNumber;
	private byte[] groupsList;		// determines place in Time Table
											// fucking English.. 
											// �������� ������, ����� ����������� ����� � ����/���� �����
											// � ���� �������� ���� ��� ���� ����� �������� � ����������...
	private boolean isFlasher;						// one lesson in two weeks
	
	private Time time=null;				// weekType must ~ isFlasher
	private Teacher teacher=null;		// from possibleTeachers
	private Auditory auditory=null;		// with type requiredAuditoryType
	private MyCell myCell=null;
	
	public Lesson (
			byte subgroupNum,
			byte[] groupList,
			boolean isFlasher){
		this.subgroupNumber = subgroupNum;
		this.groupsList = groupList;
		this.isFlasher = isFlasher;
	}
	
	void setParent(SubjectWithType parent) {
		this.parent = parent;
	}
	
	public void deleteFromTimeTable(){
		freeCell();
		if (auditory != null){
			auditory.deleteLesson(this);
			auditory = null;
		}
		if (teacher != null){
			teacher.deleteLesson(this);
			teacher = null;
		}
		if (collisions != null)
			collisions.clear();
		time = null;
	}

	public List<Collision> getCollisions(){
		return collisions;
	}

	public Time getTime() {
		return time;
	}

	// FIXME
	public void setTime(Time time) throws CollisionException{
		boolean teacherCollision = false,
				auditoryCollizion = false;
		//---------------| collision detecting:
		if (teacher != null)
			if (teacher.getLessonAt(time)!=this && teacher.isBusy(time))
				teacherCollision = true;
		if (auditory != null)
			if (teacher.getLessonAt(time)!=this && auditory.isBusy(time))
				auditoryCollizion = true;
		//---------------| exception generating:
		if (teacherCollision && auditoryCollizion)
			throw new CollisionException(
					teacher, auditory, this, CollisionException.TEACHER_AND_AUDITORY, time);
		if (teacherCollision)
			throw new CollisionException(
					teacher, this, time);
		if (auditoryCollizion)
			throw new CollisionException(
					auditory, this, time);
		//---------------| time setting:
		if (myCell != null) {
			freeCell();
		}
		this.time = time;
		updateCollisions();
	}
	
	public void setCell(MyCell cell) {
		myCell = cell;
	}
	
	private void freeCell() {
		myCell.getLessons().remove(this);
		myCell.freeOccupie(this);
		myCell = null;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	// FIXME
	public void setTeacher(Teacher teacher) throws CollisionException {
		if (time != null)
			if (teacher.getLessonAt(time)!=this && teacher.isBusy(time))
				throw new CollisionException(teacher, this, time);
		teacher.addLesson(this);
		Teacher prevTeacher = this.teacher;
		this.teacher = teacher;
		if (prevTeacher != null && prevTeacher!=this.teacher){
			prevTeacher.deleteLesson(this);
			updateCollisions();
		}
	}

	public Auditory getAuditory() {
		return auditory;
	}

	// FIXME
	public void setAuditory(Auditory auditory) throws CollisionException {
		if (time != null)
			if (auditory.getLessonAt(time)!=this && auditory.isBusy(time))
				throw new CollisionException(auditory, this, time);
		auditory.addLesson(this);
		Auditory prevAuditory = this.auditory;
		this.auditory = auditory;
		if (prevAuditory != null){
			prevAuditory.deleteLesson(this);
			updateCollisions();
		}
	}

	public SubjectWithType getParent() {
		return parent;
	}
	
	public byte getSubgroupNumber(){
		return subgroupNumber;
	}

	public byte[] getGroupsList() {
		return groupsList;
	}

	public List<Teacher> getPossibleTeachers() {
		return parent.possibleTeachers;
	}
	
	public boolean isFlasher(){
		return isFlasher;
	}
	
	private void updateCollisions(){
		if (collisions != null)
			for(Collision collision : collisions)
				collision.update();
	}
	
	public boolean isFinished() {
		return time!=null && teacher!=null && (auditory!=null || parent.getRequiredAuditoryType()==USUAL);
	}
	
	public MyCell.PlaceInCell getPlaceInCell () {
		byte fragmentCount = parent.getParent().getParent().getMaxFragmentCount(); 
		byte width = (byte) (fragmentCount/parent.getSubgroupsCount());
		byte begin = (byte) (width*(subgroupNumber-1));
		MyCell.PlaceInCell place = new MyCell.PlaceInCell(begin, (byte) (begin+width));
		if (isFlasher)
			if (time != null)
				place.setVertical(time.getWeekType());
		return place;
	}
	
	public String toString() {
		String type, groups;
		if (getParent().getType()==Constants.LACTION)
			type = "���";
		else
			type = "��";
		groups = "(" + getGroupsList()[0];
		for (int i=1; i<getGroupsList().length; i++)
			groups += "," + getGroupsList()[i];
		groups += ")";
		return ("" + type + " ��" + groups);
	}
	
	// test me:
	public String toStringForTable() {
		/*String type;
		if (getParent().getType()==Constants.LACTION)
			type = "���";
		else
			type = "��";*/
		return (""/* + type + " " */+ 
				//((auditory==null)?"":("\r"+auditory.getTitle())) + " " + 
				"<html><nobr>"+parent.getParent().getName()+"<br>"+
				((teacher==null)?"":(teacher.toString())) + "</nobr></html>");
	}
	
	// test me:
	public String toStringForException() {
		return "" + time.toString() + " " + time.toString();
	}
	
	public int getGroupCount(){
		return getParent().getParent().getParent().getGroupCount();
	}
		 
	 public int getSubgroupCount(){
		return getParent().getSubgroupsCount();
	 }
	
	//-- coercive methods (for CollisionException)------------------------
	//            it visible only in package model:
	
	void addCollision(Collision collision){
		if (collisions == null)
			collisions = new Vector<Collision>();
		collisions.add(collision);
		parent.getParent().getParent().addCollision();
	}
	
	void removeCollision(Collision collision){
		collisions.remove(collision);
		parent.getParent().getParent().deleteCollision();
	}
	
	void coerciveSetTeacher (Teacher teacher){
		if (this.teacher != null)
			this.teacher.deleteLesson(this);
		teacher.addLesson(this);
		this.teacher = teacher; 
	}
	
	void coerciveSetAuditory (Auditory auditory){
		if (this.auditory != null)
			this.auditory.deleteLesson(this);
		auditory.addLesson(this);
		this.auditory = auditory;
	}
	
	void coerciveSetTime (Time time){
		this.time = time;
	}
	
}
	// ��� ��� ���������� ����
	// ��������: 1. (����������, ������� ������������ �� ��)
	//			 ������ (�� ����, ������� ���������� � ������� ������)
	//           ������ �� ���������������� � ������������ ������� �����.
	//			 ������ ������� ������ � ������ ������.
	//			 ��������� ������� ���������� ���������.
	//			 ���� ����� ��������, ���������, ������, ...
	//			 2. (����������, ������� ��������� � �������� ������ ����������)
	//			 ����� ��������, � ��������� 6-52, ������� (������� � ������ ������), 3-� ����
	
	//	���		 1. ������ (�� ����, ������� ���������� � ������� ������)
	//			 �������� �� ���-��� � ������������ ������� �����.
	//			 �������� "�������" ������ ������ ...
	//		// ���� � parent (SubjectWithType) ���� !subgroupsCount! ������
	//		// ��� !groupCount! � ���������������� Stream (�� ��������� �������� 
	//		// ���� ���������� �������������),
	//		// �� Lesson_��� � � ����� � ��� �� �������������� ������� �����,
	//		// � ����� � ��� �� �������� ����� ���� ��������� (��������� ��� � ����� ������ ����������)
	
	// ���� ��� ^ �� �� ����� ����������� 