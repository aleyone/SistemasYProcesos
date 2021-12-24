package es.syp4.ae4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnect {

	public static void main(String[] args) {
		System.err.println("Running server...");
		ServerSocket socket=null;
		try {
			socket = new ServerSocket(1234);
		
		while(true) {
				Socket connection = socket.accept();
				System.err.println("SERVIDOR >> Retrieving connection....");
				Server server = new Server(connection);
				Thread h = new Thread (server);
				h.start();
			}
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}

}
