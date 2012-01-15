package model;

import java.util.List;

public class Specialty implements Finishable{

	private String name;
	private List<Year> years;
	
	public Specialty(String name, List<Year> years) {
		this.name = name;
		this.years = years;
		for (Year year : years)
			year.setParent(this);
	}

	public String getName() {
		return name;
	}

	public List<Year> getYears() {
		return years;
	}
	
	public Year getYear(byte y) {
		for (Year year : years)
			if (year.getYear() == y)
				return year;
		return null;
	}

	public boolean isFinished(){
		boolean isFinished=true;
		for (Year year : years)
			if (year.isFinished())
				isFinished = false;
		return isFinished;
	}
	
	public String getAbbreviation () {
		// must be improved to DB
		if (name.equals("Информатика"))
			return "МФ";
		if (name.equals("Математика"))
			return "ММ";
		if (name.equals("Механика"))
			return "М";
		if (name.equals("Прикладная математика"))
			return "МП";
		return "##";
	}
	@Override
	public String toString () {
		return name;
	}
}
