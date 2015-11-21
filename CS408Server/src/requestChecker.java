import java.io.*;
import java.net.*;


public class requestChecker extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	/**
	 * Constructor
	**/
	public requestChecker(Socket socket) throws IOException {
		this.socket = socket;
		out = new PrintWriter(socket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(
			socket.getInputStream()));
	}

	public void run() {
		try {
			while(true) {
				int req = Integer.parseInt(in.readLine());
				switch(req) {
					case 0: 
						System.out.println("VISIT");
						break;
					case 1: 
						System.out.println("REQUEST");
						(new Thread() {
							public void run() {
								try {
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