import java.net.*;
import java.sql.*;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;

public class GableServer {
	private static int connectionPort = 4711;
	private static final String PATH = "path";
	private static final String CON = "jdbc:mysql://localhost/able";
	private static final String USER = "root";
	private static final String PASS = "bionic24";

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(connectionPort);
			Scanner scan = new Scanner(System.in);
			System.out.println("Server started listening on port: " + connectionPort);
			settingDB(0);
			
			Thread requestThread = new Thread(){
				public void run(){
					while(true){
						try {
							new RequestChecker(serverSocket.accept()).start();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
							System.out.println("Thread dead.");
						}
					}
				}
			};
			
			requestThread.start();
			
			while(true){
				System.out.print("Server request('1': rebuild, '0': stop): ");
				int sr = Integer.parseInt(scan.nextLine());
				switch(sr){
				case 1:
					settingDB(1);
					break;
				case 0:
					requestThread.interrupt();
					serverSocket.close();
					return;
				default :
					break;
				}
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + connectionPort);
			System.exit(1);
		}
	}
	
	public static void settingDB(int i){
		Connection conn;
		Statement statement;
		PrintWriter outWriter = new PrintWriter(System.out);
		try {
			conn = DriverManager.getConnection(CON, USER, PASS);
			if (i == 1){
				statement = conn.createStatement();
				statement.executeUpdate("DROP DATABASE able");
				// erase the database
			}
			ScriptRunner runner = new ScriptRunner(conn);
			Reader reader = Resources.getResourceAsReader("db.sql");
//			runner.setDelimiter("]");
			runner.setLogWriter(outWriter);
			runner.setErrorLogWriter(outWriter);
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
	
	public static void serverRequest(){
		
	}
}
