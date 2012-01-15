package model;

import java.util.List;

// example: first year of mathematicians 
public class Year implements Finishable{
	
	private int collisionsCount;
	private Specialty parent;
	private byte year;
	private byte groupCount;
	private List <Subject> subjects;
	
	public Year(byte year, byte groupCount, List <Subject> subjects){
		collisionsCount=0;
		this.year 	  = year;
		this.groupCount = groupCount;
		this.subjects = subjects;
		for (Subject subject : subjects)
			subject.setParent(this);
	}
	
	void setParent(Specialty parent) {
		this.parent = parent;
	}

	public Specialty getSpecialty(){
		return parent;
	}
	
	public byte getYear(){
		return year;
	}
	
	public byte getGroupCount() {
		return groupCount;
	}
	
	public List <Subject> getSubjects (){
		return subjects;
	}

	public boolean isFinished(){
		boolean isFinished=true;
		for (Subject subject : subjects)
			if (subject.isFinished())
				isFinished = false;
		return isFinished;
	}
	
	public byte getMaxFragmentCount () {			// be careful: work only for 1,2,3 subgroups in group
		byte flag=1;
		for (Subject subj : subjects){
			for (SubjectWithType subjWithType : subj.getSubjectsWithType()){
				if (subjWithType.getSubgroupsCount()/groupCount == 2){
					if (flag==1)
						flag=2;
					else if (flag==3)
						flag=6;
				}
				if (subjWithType.getSubgroupsCount()/groupCount == 3){
					if (flag==1)
						flag=3;
					else if (flag==2)
						flag=6;
				}
			}
		}
		return (byte) (flag*groupCount);
	}

	
	public String getAbbreviation (int groupNumber) {
		return parent.getAbbreviation() + "-" + year + groupNumber;
	}
	
	public void addCollision(){
		collisionsCount++;
	}
	
	public void deleteCollision(){
		collisionsCount--;
	}

	public int getCollisionsCount() {
		return collisionsCount;
	}
}
