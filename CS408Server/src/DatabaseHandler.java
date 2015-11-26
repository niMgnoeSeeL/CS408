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
	private static final String CON = "jdbc:mysql://localhost/able";
	private static final String USER = "root";
	private static final String PASS = "bionic24";

	private Connection myCon;
	private Statement statement;

	public DatabaseHandler() {
		try {
			myCon = DriverManager.getConnection(CON, USER, PASS);
			statement = myCon.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public void tempTimelog(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(new Date());
			String query = "insert into timelog values(0, 0, '" + currentTime + "', '" + currentTime + "', " + "null)";
			statement.executeUpdate(query);
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	public void addTimelog(int op, String user, String booth) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(new Date());
			String query;
			if(op == 0) {
				query = "UPDATE timelog SET endtime=" + currentTime + " WHERE endTime IS NULL AND user =" + user;		
			} else {
				query = "INSERT INTO timelog VALUES(" + user + "," + booth + ",'" + currentTime + "',NULL ,0)";				
			}
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The top 3 most popular booths
	 * 
	 * @returns ResultSet, the retrieved data from the database
	 */
	public ResultSet getPopular() {
		try {
			// The weight for currently visiting users is temporarily set to 60
			String query = "SELECT booth.id FROM booth, timelog WHERE booth.id = timelog.booth " +
					"GROUP BY booth.id ORDER BY SUM(TIMESTAMPDIFF(MINUTE,timelog.startTime," +
					"timelog.endTime))+(COUNT(CASE WHEN timelog.endTime IS NULL THEN 1 " +
					"ELSE NULL END)*60) DESC LIMIT 3";
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Simple recommendations for users based on popular booths which hasn't
	 * been visited yet
	 * 
	 * @param String
	 *            user, the user which will get the recommendation
	 * @returns ResultSet, the retrieved data from the database
	 */
	public ResultSet getRecommended(String user) {
		try {
			String query = "SELECT booth.id FROM booth, timelog WHERE booth.id = timelog.booth AND " +
					"booth.id NOT IN (SELECT booth FROM timelog WHERE userID=" + user + ") GROUP BY " +
					"booth.id ORDER BY SUM(TIMESTAMPDIFF(MINUTE,timelog.startTime," +
					"timelog.endTime))+(COUNT(CASE WHEN timelog.endTime IS NULL THEN 1 " +
					"ELSE NULL END)*60) DESC LIMIT 3";
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
