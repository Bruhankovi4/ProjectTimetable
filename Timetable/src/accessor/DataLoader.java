package accessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import model.Auditory;
import model.Lesson;
import model.Specialty;
import model.Subject;
import model.SubjectWithType;
import model.Teacher;
import model.Year;

public class DataLoader {
	/* LOADING ALGORITHM: 
	 	LOAD Specialty:
	 		- simply load name;
	 		-LOAD Year_s #1
	 			- load number;
	 			- load groupsCount;
	 				// we will use it in creating of Lessons
	 			-LOAD Subject_s #2
	 				- load name;
	 				- load (to TeacherTable) Teacher_s #3
	 					// if teacher was loaded before,
	 					// then use teacher from TeacherTable
	 				-LOAD SubjectWithType_s #3
	 					- load Type
	 						// may be we must implement
	 						// more flexible class Type
	 					- load subgroupsCount
	 					- create Lessons #4 (!!!IT IS DIFFICULT!!!)
	 				- new SubjectWithType(...);
	 				//...(in circle)...
	 			- new Year(...);
	 			//...(in circle)...
	 		- new Specialty(...);
	 		//...(in circle)...
	 		
	 	#with binding to Specialty#1/Year#2/Subject#3/SubjectWithType#4
	 	*/
	
//= VARS: ======================================
	private Map <Integer, Teacher> teacherTable = new HashMap<Integer, Teacher>();
	private Map <Integer, Auditory> auditoryTable = new HashMap<Integer, Auditory>();
//	private Map <Integer, LessonType> lessonTypeTable = new HashMap<Integer, Auditory>();
//	private Map <Integer, AuditoryType> auditoryTypeTable = new HashMap<Integer, AuditoryType>();
	
	private Connection connection;
//	private ResultSet resultSet;
//	private Statement statement;
	
	private byte semester;
	
	// fucking code (don't repeat this at home):
	private byte groupCountInCurYear;				// ***
	//private String plans_for_future = new String("Fuck this all!!");		// solved


//= METHODS: ===================================
	
	public DataLoader() throws Exception{
		connection = Accessor.getConnection();
	//	statement = connection.createStatement();
	}
	
	public DataLoader(byte semester) throws Exception{
		connection = Accessor.getConnection();
	//	statement = connection.createStatement();
		this.semester = semester;
	}
	
	public List <Specialty> getSpecialties () throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet;
		// smart loading from DB // implement me
		Vector<Specialty> specialties = new Vector<Specialty>();
		resultSet = statement.executeQuery(
				"SELECT id_specialty as id, title FROM Specialties; ");
		while (resultSet.next()){
			List<Year> years;
			int idSpec = resultSet.getInt("id");
			years = getYears(idSpec);
			specialties.add(new Specialty(resultSet.getString("title"), years));
		}
		resultSet.close();
		statement.close();
		return specialties;
	}
	
	private List <Year> getYears(int idSpec) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet resultSet;
		Vector<Year> years = new Vector<Year>();
		resultSet = statement.executeQuery(
				"SELECT id_year as id, year FROM Years WHERE ref_specialty="+idSpec+"; ");
		while (resultSet.next()){
			List<Subject> subjects;
			int idYear = resultSet.getInt("id");
			byte groupCount = getGroupCount(idYear);
			groupCountInCurYear = groupCount;			// ***^
			subjects = getSubjects(idYear);
			if (subjects != null)
				years.add(new Year((byte)resultSet.getInt("year"), groupCount, subjects));
		}
		resultSet.close();
		statement.close();
		return years;
	}
	
	private List <Subject> getSubjects(int idYear) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet resultSet;
		Vector<Subject> subjects = new Vector<Subject>();
		resultSet = statement.executeQuery(
				"SELECT id_sy as id, title "+
				"FROM Subjects  INNER JOIN SubjectYear" +
				"				ON ref_subject=id_subject AND ref_year="+idYear+"; ");
		while (resultSet.next()){
			List<SubjectWithType> subjectsWithType;
			int idSubjYear = resultSet.getInt("id");
			subjectsWithType = getSubjectsWithType(idSubjYear);
			if (subjectsWithType.size() != 0)
				subjects.add(new Subject(resultSet.getString("title"), subjectsWithType));
		}
		resultSet.close();
		statement.close();
		return subjects;
	}
	
	private List <SubjectWithType> getSubjectsWithType(int idSubjYear) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet resultSet;
		Vector<SubjectWithType> subjectsWithType = new Vector<SubjectWithType>();
		resultSet = statement.executeQuery(
				"SELECT DISTINCT id_syst as id," +
				"		 subgroups_count," +
				"		 hours_count," +
				"		 LessonType.title as lessonType," +
				"		 AuditoryType.title as audType "+
				"FROM AuditoryType" +
				"	   RIGHT OUTER JOIN (LessonType" +
				"	   			  INNER JOIN (SubjectYearSemesterType" +
				"	  			   			  INNER JOIN SubjectYear" +
				"	  			   			  ON ref_sy="+idSubjYear+" AND semester="+semester+")" +
				"	   			  ON ref_type = LessonType.id_type)" +
				"	  ON ref_audtype=AuditoryType.id_type ; ");
		while (resultSet.next()){
			List<Lesson> lessons;
			List<Teacher> teachers;
			// fixme!!! >>>---------------
			byte audType, lessonType;
			if (resultSet.getString("audType")!=null &&
					resultSet.getString("audType").equals("Компьютерный класс"))
				audType = model.Constants.COMPUTER;
			else
				audType = model.Constants.USUAL;
			if (resultSet.getString("lessonType").equals("ЛЕК"))
				lessonType = model.Constants.LACTION;
			else
				lessonType = model.Constants.PRACTICAL;
			teachers = getTeachers(idSubjYear);
			lessons = getLessons(
					(byte)resultSet.getInt("subgroups_count"),
					(byte)resultSet.getInt("hours_count"));
			//--------------------------<<<<<<<<<
			if (lessons.size()!=0)
				subjectsWithType.add(new SubjectWithType(
											lessonType,
											(byte)resultSet.getInt("subgroups_count"),
											(byte)resultSet.getInt("hours_count"),
											audType,
											teachers,
											lessons));
		}
		resultSet.close();
		statement.close();
		return subjectsWithType;
	}
	
	private List <Lesson> getLessons(byte subgroupsCount, byte hoursCount) throws SQLException{
		Vector<Lesson> lessons = new Vector<Lesson>();
		// be careful
		if (subgroupsCount > groupCountInCurYear && subgroupsCount%groupCountInCurYear!=0){
			System.out.println("!!!ERR!!! wrong partition into subgroups");
			return lessons;
		}
		// be careful
		if(hoursCount%2!=0 && hoursCount!=1){
			System.out.println("!!!ERR!!! wrong hours count in two weeks");
			return lessons;
		}
		for (byte i=0; i<subgroupsCount; i++)
			for (byte j=0; j<hoursCount; j+=2){
				int size = groupCountInCurYear/subgroupsCount;
				byte[] groupList = new byte[(size==0)?1:size];
				for(byte k=0; k<groupList.length; k++)
					groupList[k]=(byte) (((size==0)?1:size)*i+k+1);		// be careful
				Lesson les = new Lesson((byte)(i+1), groupList, ((hoursCount==1)?true:false));
				lessons.add(les);
			}
		return lessons;
	}
	
	
	// be careful, it without email, phone numbers and degree
	private List <Teacher> getTeachers(int idSubjYear) throws SQLException{
		Statement statement = connection.createStatement();
		ResultSet resultSet;
		Vector<Teacher> teachers = new Vector<Teacher>();
		resultSet = statement.executeQuery(
				"SELECT DISTINCT id_teacher as id," +
				"					last_name," +
				"					first_name," +
				"					father_name " +
				"FROM Teachers " +
				"	  INNER JOIN (TeacherCathedra " +
				"				  INNER JOIN (Cathedras " +
				"							  INNER JOIN SubjectYearCathedra " +
				"							  ON SubjectYearCathedra.ref_cathedra=id_cathedra AND ref_sy="+idSubjYear+") " +
				"				  ON TeacherCathedra.ref_cathedra=id_cathedra)" +
				"	  ON ref_teacher=id_teacher; ");
		while (resultSet.next()){
			int idTeacher = resultSet.getInt("id");
			Teacher teacher;
			if ((teacher=teacherTable.get(idTeacher)) == null)
				teacherTable.put(idTeacher, teacher=new Teacher(
												resultSet.getString("first_name"),
												resultSet.getString("last_name"),
												resultSet.getString("father_name")));
			teachers.add(teacher);
		}
		resultSet.close();
		statement.close();
		return teachers;
	}
	
	private HashMap<Integer,Byte> getAuditoryTypeTable(){
		HashMap<Integer, Byte> auditoryTypeTable = new HashMap<Integer,Byte>();
		// loading from DB // implement me
		return auditoryTypeTable;
	}
	
	private HashMap<Integer,Auditory> getAuditoryTable(){
		HashMap<Integer, Auditory> auditoryTable = new HashMap<Integer,Auditory>();
		// loading from DB // implement me
		return auditoryTable;
	}
	
	
	

	public int getGroupCount(String specialty, int year) {
		Statement getterStatement=null;
		ResultSet resultSet;
		int idSpec, idYear, count=-1;
		try{
			getterStatement = connection.createStatement();
				resultSet = getterStatement.executeQuery(
								"SELECT id_specialty FROM Specialties "+
								"WHERE title = '" + specialty + "' ;");
				if (!resultSet.next()){
					resultSet.close();
					return -1;
				}
				idSpec = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_year FROM Years "+
						"WHERE year = " + year + " AND ref_specialty = " + idSpec + " ;");
				if (!resultSet.next()){
					resultSet.close();
					return -1;
				}
				idYear = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT count(id_group) FROM Groups "+
						"WHERE ref_year = " + idYear + " ;");
				if (!resultSet.next()){
					resultSet.close();
					return -1;
				}
				count = resultSet.getInt(1);
				resultSet.close();
		}
		finally{
			if(getterStatement!=null)
				try {
					getterStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			return count;
		}
	}
		
	public byte getGroupCount(int idYear) {
		ResultSet resultSet;
		Statement getterStatement=null;
		byte count=-1;
		try{
			getterStatement = connection.createStatement();
				resultSet = getterStatement.executeQuery(
						"SELECT count(id_group) FROM Groups "+
						"WHERE ref_year = " + idYear + " ;");
				if (!resultSet.next()){
					resultSet.close();
					return -1;
				}
				count = (byte)resultSet.getInt(1);
				resultSet.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(getterStatement!=null)
				try {
					getterStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			return count;
		}
	}
}
