import java.net.*;
import java.sql.*;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;

public class GableServer {
	private static int connectionPort = 4711;
	private static final String PATH = "path";
	private static final String CON = "jdbc:mysql://localhost/CS408";
	private static final String USER = "root";
	private static final String PASS = "bionic24";

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(connectionPort);
			System.out.println("Server started listening on port: " + connectionPort);
			settingDB();
			while (true) {
				new RequestChecker(serverSocket.accept()).start();
			}
			// serverSocket.close();
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + connectionPort);
			System.exit(1);
		}
	}
	
	public static void settingDB(){
		Connection conn;
		try {
			conn = DriverManager.getConnection(CON, USER, PASS);
			ScriptRunner runner = new ScriptRunner(conn);
			Reader reader = Resources.getResourceAsReader("db.sql");
//			runner.setDelimiter("]");
			runner.setLogWriter(null);
			runner.setErrorLogWriter(null);
			runner.runScript(reader);
			conn.commit();
			reader.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
