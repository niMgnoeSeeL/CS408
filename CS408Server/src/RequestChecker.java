import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;



public class RequestChecker extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
<<<<<<< HEAD:CS408Server/src/requestChecker.java
	private OutputStream sos = null;
	private ObjectOutputStream soos = null;
	private Connection con = null;
	private PreparedStatement psmt = null;
	private ResultSet rs = null;

=======
	private DatabaseHandler db;
>>>>>>> 88d84f955118ae8ea5e34a360effe8eeea063ac1:CS408Server/src/RequestChecker.java

	/**
	 * Constructor
	**/
	public RequestChecker(Socket socket) throws IOException {
		this.socket = socket;
		sos = socket.getOutputStream();
		out = new PrintWriter(sos, true);
		soos = new ObjectOutputStream(sos);
		in = new BufferedReader(new InputStreamReader(
			socket.getInputStream()));
		db = new DatabaseHandler();
	}

	public void run() {
		try {
			while(true) {
				int req = Integer.parseInt(in.readLine());
				switch(req) {
					case 0: 
						System.out.println("VISIT");
						// get user and booth from the client
						int user = 0;
						int booth = 0;
						db.addTimelog(user,booth);
						break;
					case 1: 
						System.out.println("REQUEST");
						(new Thread() {
							public void run() {
								try {
									// execute query in database + analyze received data 
									Thread.sleep(5000);
									out.println("OK");
									System.out.println("Sent ok");
								} catch(InterruptedException e) {
								}
							} 
						}).start();
						break;
					case 2:
						System.out.println("Connecting to the server...");
						con = DriverManager.getConnection("jdbc:mysql://localhost", "root", "bionic24");
						System.out.println("Connecting finished!");
						psmt = con.prepareStatement("SHOW DATABASES");
						rs = psmt.executeQuery();
						String result = null;
						while(rs.next()){
							result += rs.getString(1);
						}
						out.println(result);
						System.out.println("Sent ok");
						break;
				}
			}
			// out.close();
			// in.close();
			// socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException sqex) {
			// TODO Auto-generated catch block
			System.out.println("SQLException: " + sqex.getMessage());
			System.out.println("SQLState: " + sqex.getSQLState());
		}
	}
}
