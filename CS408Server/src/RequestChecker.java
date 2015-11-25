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
	private static PrintWriter out;
<<<<<<< HEAD:CS408Server/src/requestChecker.java
	private OutputStream sos = null;
	private ObjectOutputStream soos = null;
	private Connection con = null;
	private PreparedStatement psmt = null;
	private ResultSet rs = null;

=======
	private static DatabaseHandler db;
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
				final int req = Integer.parseInt(in.readLine());
				switch(req) {
					case 0: 
						System.out.println("VISIT");
						// get user and booth from the client
						int user = 0;
						int booth = 0;
						db.addTimelog(in.readLine(),in.readLine());
						break;
					default:
						(new Thread() {
							public void run() {
								try {
									ResultSet rs;
									if(req == 1) {
										rs = db.getPopular();
									} else if(req == 2) {
										rs = db.getRecommended(in.readLine());
									}
									while(rs.next()) {
										out.println(rs.getString("booth.name"));
									}
								} catch(InterruptedException e) {
								}
							} 
						}).start();
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
