package es.sypbl5.ae5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.sun.net.httpserver.HttpServer;

public class HTTPServer {

	public static void main(String[] args) {
		String host = "127.0.0.1";
		int puerto = 7777;
		InetSocketAddress direccionTCPIP = new InetSocketAddress(host, puerto);
		int backlog = 0;
		HTTPManager manager = new HTTPManager();
		String responsePath = "/estufa";
		HttpServer servidor;
		try {
			servidor = HttpServer.create(direccionTCPIP, backlog);
			servidor.createContext(responsePath, manager);
			ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
			servidor.setExecutor(threadPoolExecutor);
			servidor.start();
			System.out.println("Servidor HTTP arranca en el puerto " + puerto);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
