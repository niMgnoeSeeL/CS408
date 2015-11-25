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
	private OutputStream sos = null;
	private ObjectOutputStream soos = null;
	private Connection con = null;
	private PreparedStatement psmt = null;
	private ResultSet rs = null;
	private static DatabaseHandler db;

	/**
	 * Constructor
	 **/
	public RequestChecker(Socket socket) throws IOException {
		this.socket = socket;
		sos = socket.getOutputStream();
		out = new PrintWriter(sos, true);
		soos = new ObjectOutputStream(sos);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		db = new DatabaseHandler();
	}

	public void run() {
		try {
			while (true) {
				final int req = Integer.parseInt(in.readLine());
				switch (req) {
				case 0:
					System.out.println("VISIT");
					// get user and booth from the client
					int user = 0;
					int booth = 0;
					db.addTimelog("123", "0");
					break;
				case 3:
					System.out.println("KEEP");
					db.tempTimelog();
					break;
				default:
					(new Thread() {
						public void run() {
							ResultSet rs;
							if (req == 1) {
								rs = db.getPopular();
							} else if (req == 2) {
								rs = db.getRecommended("0");
							}
							/*
							 * while(rs.next()) {
							 * out.println(rs.getString("booth.name")); }
							 */
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
		} /*
			 * catch (SQLException sqex) { // TODO Auto-generated catch block
			 * System.out.println("SQLException: " + sqex.getMessage());
			 * System.out.println("SQLState: " + sqex.getSQLState()); }
			 */
	}
}
