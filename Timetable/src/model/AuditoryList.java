package model;

import java.util.List;
import java.util.Vector;

public class AuditoryList {
	
	static List<Auditory> auditoryList = new Vector<Auditory>();
	
	static public Auditory get(Auditory auditory){
		for (Auditory a : auditoryList)
			if (auditory.equalsTo(a))
				return a;
		auditoryList.add(auditory);
		return auditory;
	}
	
	static public void defaultList(){
		// implement me
	}
}
