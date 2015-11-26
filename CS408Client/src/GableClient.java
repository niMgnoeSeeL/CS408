import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.net.*;

public class GableClient {
	private static int connectionPort = 4711;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;

	private static final int VISIT = 0;
	private static final int REQUEST = 1;
	private static final int DB = 2;
	private static final int KEEP = 3;

	public static void main(String[] args) {
		try {
			final GableClient c = new GableClient();
			Scanner scan = new Scanner(System.in);
			String[] queryVal = {};
			
			Thread timelogThread = new Thread(){
				public void run(){
					while(true){
						try {
							Thread.sleep(5000);
							queryVal = {};
							c.send(KEEP, queryVal);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};
			
			timelogThread.start();
			
			while (true) {
				System.out.println("0 or 1 or 2?");
				int nextAct = Integer.parseInt(scan.next());
				switch (nextAct) {
				case VISIT:
					// when used by the beacon detecter
					queryVal = {"0","0","0"};
					c.send(VISIT, queryVal);
					break;
				case REQUEST:
					// when used by the UI
					queryVal = {"0"};
					c.send(REQUEST, queryVal);
					(new Thread() {
						public void run() {
							try {
								// update UI with the received data
								System.out.println(c.response().toString());
							} catch (IOException e) {
							}
						}
					}).start();
					break;
				case DB:
					queryVal = {"1","0"};
					c.send(DB, queryVal);
					try {
						System.out.println(c.response().toString());
					} catch (IOException e) {
					}
				}
			}
		} catch (IOException e) {
		}
	}

	/**
	 * Constructor
	 **/
	public GableClient() {
		try {
			socket = new Socket("143.248.196.119", connectionPort);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Unknown host");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't open connection");
			System.exit(1);
		}
	}

	/**
	 * @param int
	 *            message, the request type
	 * @throws IOException
	 *             if the message couldn't be sent over the stream
	 **/
	public void send(int message, String[] args) throws IOException {
		out.println(message);
		for (String s : args) {
			out.println(s);
		}
	}

	/**
	 * @return ArrayList<String> response, the received response
	 * @throws IOException
	 *             if the stream is closed
	 **/
	public ArrayList<String> response() throws IOException {
		ArrayList<String> response = new ArrayList<String>();
		String s;
		while (!(s = in.readLine()).equals("-1")) {
			response.add(s);
		}
		return response;
	}

	/**
	 * @throws IOException
	 *             if the streams are closed
	 **/
	public void close() throws IOException {
		out.close();
		in.close();
		socket.close();
	}
}
