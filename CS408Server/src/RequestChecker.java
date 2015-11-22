import java.io.*;
import java.net.*;


public class RequestChecker extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private DatabaseHandler db;

	/**
	 * Constructor
	**/
	public RequestChecker(Socket socket) throws IOException {
		this.socket = socket;
		out = new PrintWriter(socket.getOutputStream(), true);
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
