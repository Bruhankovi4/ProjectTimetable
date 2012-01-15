package accessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Accessor {

	private static Connection connection=null;
	private static int[] psw={98, 145, 127, 135, 117, 139, 145, 100, 125, 127, 135, 129, 142, 76, 76 };
	
	static Connection getConnection() throws Exception {
		if (connection == null  ||  connection.isClosed()){
			String p=new String();
			for(int i=0; i<psw.length; i++)
				p = p + (char)(psw[i]-28);
			connection = DriverManager.getConnection("jdbc:postgresql://5.97.6.176:5432/TimeTable", "postgres", p);
		}
		return connection;
	}
	
	static void closeConnection() throws SQLException {
		if (connection!= null)
			connection.close();
	}
}
