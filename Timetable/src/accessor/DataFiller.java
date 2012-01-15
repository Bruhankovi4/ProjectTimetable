package accessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataFiller {

	private Connection connection;
	private ResultSet resultSet;
	private Statement statement;


//= METHODS: ===================================
	
	public DataFiller() throws Exception{
		connection = Accessor.getConnection();
		statement = connection.createStatement();
	}
	
	public void addSubjects(String[] subjectTitles) throws SQLException {
		for(int i=0; i<subjectTitles.length; i++)
			statement.executeUpdate("INSERT INTO Subjects (title) VALUES ('"+subjectTitles[i]+"');");
	}
	
	public void addSpecialties(String[] specialtyTitles) throws SQLException {
		for(int i=0; i<specialtyTitles.length; i++)
			statement.executeUpdate("INSERT INTO Specialties (title) VALUES ('"+specialtyTitles[i]+"');");
	}
	
	public void addYears(String[] specialties, int[] count){
		Statement getterStatement=null;
		int idSpec;
		try{
			getterStatement = connection.createStatement();
			for (int i=0; i<specialties.length; i++){
				resultSet = getterStatement.executeQuery(
								"SELECT id_specialty FROM Specialties "+
								"WHERE title = '" + specialties[i] + "' ;");
				if (!resultSet.next()){
					resultSet.close();
					continue;
				}
				idSpec = resultSet.getInt(1);
				for (int year=1; year<=count[i]; year++)
					statement.executeUpdate(
							"INSERT INTO Years (ref_specialty, year) VALUES ('"+idSpec+"','"+year+"');");
			}
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
		}
	}
	
	public void addGroups(String specialty, int year, int groupCount) {
		Statement getterStatement=null;
		int idSpec, idYear;
		try{
			getterStatement = connection.createStatement();
				resultSet = getterStatement.executeQuery(
								"SELECT id_specialty FROM Specialties "+
								"WHERE title = '" + specialty + "' ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idSpec = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_year FROM Years "+
						"WHERE year = " + year + " AND ref_specialty = " + idSpec + " ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idYear = resultSet.getInt(1);
				for (int groupNum=1; groupNum<=groupCount; groupNum++)
					statement.executeUpdate(
							"INSERT INTO Groups (ref_year, group_number) VALUES ("+idYear+", "+groupNum+");");
					
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
		}
	}

	public void addSubjectYear(String specialty, int year, String subject) {
		Statement getterStatement=null;
		int idSpec, idYear, idSubj;
		try{
			getterStatement = connection.createStatement();
				resultSet = getterStatement.executeQuery(
								"SELECT id_specialty FROM Specialties "+
								"WHERE title = '" + specialty + "' ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idSpec = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_year FROM Years "+
						"WHERE year = " + year + " AND ref_specialty = " + idSpec + " ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idYear = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_subject FROM Subjects "+
						"WHERE title = '" + subject + "' ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idSubj = resultSet.getInt(1);
				resultSet.close();
				statement.executeUpdate(
						"INSERT INTO SubjectYear (ref_subject, ref_year) VALUES ("+idSubj+", "+idYear+");");
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
		}
	}

	public void addTeachers(String[] teacherNames) throws SQLException {
		for(int i=0; i<teacherNames.length; i++){
			String[] fio = teacherNames[i].split(" ");
			if (fio.length!=3)
				continue;
			statement.executeUpdate(
					"INSERT INTO Teachers (last_name, first_name, father_name) "+
					"VALUES ('"+fio[0]+"','"+fio[1]+"','"+fio[2]+"');");
		}
	}
	
	public void addCathedras(String[] cathedraTitles) throws SQLException {
		for(int i=0; i<cathedraTitles.length; i++){
			statement.executeUpdate(
					"INSERT INTO Cathedras (title) "+
					"VALUES ('"+cathedraTitles[i]+"');");
		}
	}

	public void addTeacherCathedra(String teacher, String cathedra) { 
		Statement getterStatement=null;
		int idTeach, idCath;
		try{
			getterStatement = connection.createStatement();
			String[] fio = teacher.split(" ");
			resultSet = getterStatement.executeQuery(
							"SELECT id_teacher FROM Teachers "+
							"WHERE last_name = '" + fio[0] + 
							"' AND first_name = '" + fio[1] + 
							"' AND father_name = '" + fio[2] + "' ;");
			if (!resultSet.next()){
				resultSet.close();
				return;
			}
			idTeach = resultSet.getInt(1);
			resultSet.close();
			resultSet = getterStatement.executeQuery(
					"SELECT id_cathedra FROM Cathedras "+
					"WHERE title = '" + cathedra + "' ;");
			if (!resultSet.next()){
				resultSet.close();
				return;
			}
			idCath = resultSet.getInt(1);
			resultSet.close();
			statement.executeUpdate(
					"INSERT INTO TeacherCathedra (ref_teacher, ref_cathedra) VALUES ("+idTeach+", "+idCath+");");
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
		}
	}

	public void addSubjectYearCathedra (
		String specialty, int year, String subject, String cathedra) {
		Statement getterStatement=null;
		int idSpec, idYear, idSubj, idSubjYear, idCathedra;
		try{
			getterStatement = connection.createStatement();
				resultSet = getterStatement.executeQuery(
								"SELECT id_specialty FROM Specialties "+
								"WHERE title = '" + specialty + "' ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idSpec = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_year FROM Years "+
						"WHERE year = " + year + " AND ref_specialty = " + idSpec + " ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idYear = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_subject FROM Subjects "+
						"WHERE title = '" + subject + "' ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idSubj = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_cathedra FROM Cathedras "+
						"WHERE title = '" + cathedra + "' ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idCathedra = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_sy FROM SubjectYear "+
						"WHERE ref_subject = " + idSubj + " AND ref_year = " + idYear + " ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idSubjYear = resultSet.getInt(1);
				resultSet.close();
				statement.executeUpdate(
						"INSERT INTO SubjectYearCathedra (ref_sy, ref_cathedra)" +
						"VALUES ("+idSubjYear+", "+idCathedra+");");
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
		}
	}
	
	public void addLessonTypes(String[] typeTitles) throws SQLException {
		for(int i=0; i<typeTitles.length; i++)
			statement.executeUpdate("INSERT INTO LessonType (title) VALUES ('"+typeTitles[i]+"');");
	}
	
	public void addAuditoryTypes(String[] audTitles) throws SQLException {
		for(int i=0; i<audTitles.length; i++)
			statement.executeUpdate("INSERT INTO AuditoryType (title) VALUES ('"+audTitles[i]+"');");
	}
	
	// be careful, it without auditoryType
	public void addSubjectYearSemesterType (
			String specialty, int year, String subject, int semester,
			int subgroupsCount, int hoursCount, String type) {
		Statement getterStatement=null;
		int idSpec, idYear, idSubj, idSubjYear, idType;
		try{
			getterStatement = connection.createStatement();
				resultSet = getterStatement.executeQuery(
								"SELECT id_specialty FROM Specialties "+
								"WHERE title = '" + specialty + "' ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idSpec = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_year FROM Years "+
						"WHERE year = " + year + " AND ref_specialty = " + idSpec + " ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idYear = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_subject FROM Subjects "+
						"WHERE title = '" + subject + "' ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idSubj = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_sy FROM SubjectYear "+
						"WHERE ref_subject = " + idSubj + " AND ref_year = " + idYear + " ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idSubjYear = resultSet.getInt(1);
				resultSet.close();
				resultSet = getterStatement.executeQuery(
						"SELECT id_type FROM LessonType "+
						"WHERE title = '" + type + "' ;");
				if (!resultSet.next()){
					resultSet.close();
					return;
				}
				idType = resultSet.getInt(1);
				resultSet.close();
				statement.executeUpdate(
						"INSERT INTO SubjectYearSemesterType (ref_sy, ref_type, subgroups_count, hours_count, semester)" +
						"VALUES ("+idSubjYear+", "+idType+", "+subgroupsCount+", "+hoursCount+", "+semester+");");
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
		}
	}
}
