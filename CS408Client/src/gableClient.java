import java.util.Scanner;
import java.io.*;
import java.net.*;

public class gableClient {
	private static int connectionPort = 4711;
	private	Socket socket;
	private	PrintWriter out;
	private	BufferedReader in;
	
	private static final int VISIT = 0;
	private static final int REQUEST = 1;
	private static final int DB = 2;

	public static void main(String[] args) {
		try {
			final gableClient c = new gableClient();
			Scanner scan = new Scanner(System.in);
			while(true) {
				System.out.println("0 or 1 or 2?");
				int nextAct = Integer.parseInt(scan.next());
				switch(nextAct) {
					case VISIT: 
						// when used by the beacon detecter
						c.send(VISIT);
						break;
					case REQUEST:
						// when used by the UI
						c.send(REQUEST);
						(new Thread() {
							public void run() {
								try {
									// update UI with the received data
									System.out.println(c.response());
								} catch(IOException e) {
								}
							} 
						}).start();
						break;
					case DB:
						c.send(DB);
						try{
							System.out.println(c.response());
						} catch (IOException e){
						}
				}
			}
		} catch(IOException e) {
		}
	}
	
	/**
	 * Constructor
	 **/
	public gableClient() {
		try {
			socket = new Socket("localhost", connectionPort);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Unknown host");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't open connection");
			System.exit(1);
		}		
	}
		
	/**
	 * @param int message, the request type
	 * @throws IOException if the message couldn't be sent over the stream
	 **/
	public void send(int message) throws IOException {
		out.println(message);
	}
	
	/**
	 * @return String response, the received response
	 * @throws IOException if the stream is closed
	 **/
	public String response() throws IOException {
		return in.readLine();
	}
	
	/**
	 * @throws IOException if the streams are closed
	 **/
	public void close() throws IOException {
		out.close();
		in.close();
		socket.close();
	}
}
