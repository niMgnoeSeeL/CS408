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
	private OutputStream sos = null;		// Are sos and soos used?
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
				int req = Integer.parseInt(in.readLine());
				switch (req) {
				case 0:
					System.out.println("VISIT");
					// get user and booth from the client
					db.addTimelog(Integer.parseInt(in.readLine()),in.readLine(), in.readLine());
					break;
				case 3:
					System.out.println("KEEP");
					db.tempTimelog();
					break;
				default:
					static int op = Integer.parseInt(in.readLine());
					(new Thread() {
						public void run() {
							ResultSet rs;
							if (op == 0) {
								rs = db.getPopular();
							} else if (op == 1) {
								rs = db.getRecommended(in.readLine());
							}
							while(rs.next()) {
								out.println(rs.getString("booth.id")); 
							}
							// end of response
							out.println("-1");
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
		} 
	}
}
