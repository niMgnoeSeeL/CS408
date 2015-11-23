import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * DatabaseHandler.java
 * Connects to the server and executes all necessary queries
 * 
 * @author Filiz Boyraz
 * @version 22.11.2015
 */
public class DatabaseHandler {
	private static final String PATH = "path";
	private static final String CON = "connection";
	private static final String USER = "username";
	private static final String PASS = "password";
	
	private Connection myCon;
	private Statement statement;

	public DatabaseHandler() {
		try {
			Class.forName(PATH);
			myCon = DriverManager.getConnection(CON, USER, PASS);
			statement = myCon.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addTimelog(int user, int booth) {
		try {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String query = "UPDATE timelog SET endtime="+sdf.format(new Date())+" WHERE endTime IS NULL AND user ="+user;
			statement.executeUpdate(query);
			query = "INSERT INTO timelog VALUES("+user+","+booth+","+currentTime+",NULL)";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
