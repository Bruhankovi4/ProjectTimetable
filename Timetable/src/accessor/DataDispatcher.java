package accessor;

import java.sql.SQLException;
import java.util.List;

import model.Lesson;
import model.Specialty;
import model.Subject;
import model.Year;

public class DataDispatcher{

	static private List <Specialty> specialties=null;
	private DataLoader dataLoader;
	
	public DataDispatcher (byte semester) throws Exception{
		dataLoader = new DataLoader(semester);
	}
	
	public List <Specialty> getSpecialties () throws SQLException {
		return (specialties==null)?(specialties=dataLoader.getSpecialties()):specialties;
	}
	
	public void viewData() throws Exception{
		List<Specialty> specialties = this.getSpecialties();
		for (Specialty specialty : specialties){
			System.out.println(specialty.getName());
			for (Year year : specialty.getYears()){
				System.out.println("	" + year.getYear());
				for (Subject subject : year.getSubjects()){
					System.out.println("		" + subject.getName());
					for (Lesson lesson : subject.getLessons())
						System.out.println("			" + lesson.toString());
				}
			}
		}
	}

}
