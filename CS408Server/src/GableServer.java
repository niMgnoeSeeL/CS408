import java.net.*;
import java.io.*;


public class GableServer {
	private static int connectionPort = 4711;
    
	public static void main(String[] args) {  
		try {
			ServerSocket serverSocket = new ServerSocket(connectionPort); 
			System.out.println("Server started listening on port: " + connectionPort);
			while (true) {
				new RequestChecker(serverSocket.accept()).start();
			}
			//serverSocket.close();
		} catch (IOException e) {
      System.err.println("Could not listen on port: " + connectionPort);
      System.exit(1);
    }
  }
}
