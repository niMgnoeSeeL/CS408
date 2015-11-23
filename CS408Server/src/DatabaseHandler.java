import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * DatabaseHandler.java Connects to the server and executes all necessary
 * queries
 * 
 * @author Filiz Boyraz
 * @version 22.11.2015
 */
public class DatabaseHandler {
	private static final String PATH = "path";
	private static final String CON = "jdbc:mysql://localhost";
	private static final String USER = "root";
	private static final String PASS = "bionic24";

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
			String currentTime = sdf.format(new Date());
			String query = "UPDATE timelog SET endtime=" + currentTime
					+ " WHERE endTime IS NULL AND user =" + user;
			statement.executeUpdate(query);
			query = "INSERT INTO timelog VALUES(" + user + "," + booth + ","
					+ currentTime + ",NULL)";
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet getPopular() {
		try {
			String query = "SELECT booth.name FROM booth, timelog WHERE booth.id = timelog.booth GROUP BY "
					+ "booth.id ORDER BY SUM(TIMESTAMPDIFF(MINUTE,timelog.startTime,timelog.endTime)) "
					+ "DESC LIMIT 3";
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
