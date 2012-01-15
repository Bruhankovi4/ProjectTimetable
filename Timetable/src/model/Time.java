package model;

public class Time implements Constants {
	
	private byte day;			// 1-7
	private byte lessonNumber;
	private byte weekType;

	public Time(byte day, byte lessonNumber, byte weekType) {
		this.day = day;
		this.lessonNumber = lessonNumber;
		this.weekType = weekType;
	}

	public byte getDay() {
		return day;
	}

	public byte getLessonNumber() {
		return lessonNumber;
	}

	public byte getWeekType() {
		return weekType;
	}
	
	public boolean equalsTo(Time t) {
		return t.day == day  &&  t.lessonNumber == lessonNumber  &&  t.weekType == weekType;
	}
	
	public boolean hasCollisionWith (Time t) {
		if (t.day == day  &&  t.lessonNumber == lessonNumber) {
			if (weekType==FULL || t.weekType==FULL)
				return true;
			if (weekType==LOWER && t.weekType==LOWER  ||  weekType==UPPER && t.weekType==UPPER)
				return true;
		}
		return false;
	}
	
	public String toString() {
		String string;
		if (weekType == UPPER)
			string = "В ";
		else if (weekType == LOWER)
			string = "Н ";
		else
			string = "";
		switch (day) {
		case 1:	string += "пн";	break;
		case 2:	string += "вт";	break;
		case 3:	string += "ср";	break;
		case 4:	string += "чт";	break;
		case 5:	string += "пт";	break;
		case 6:	string += "сб";	break;
		case 7:	string += "вс";	break;
		}
		return string + " " + lessonNumber;
	}
}
