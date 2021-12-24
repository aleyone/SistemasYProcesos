package es.syp4.ae4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

	Socket socket;
	public Server(Socket socket) {
		this.socket=socket;
	}
	
	public void run() {
		try {
			
			Thread.sleep(3000);
			ObjectOutputStream outObject = new ObjectOutputStream(socket.getOutputStream());
			Password pass = new Password("Texto Plano", "Contraseña Encriptada");
			outObject.writeObject(pass);
			System.err.println("SERVIDOR >>> Sending empty object: "+pass.getTextPass());
			ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
			Password modPass = (Password) inObject.readObject();
			Thread.sleep(3000);
			System.err.println("SERVIDOR >>> Receiving data from user: "+modPass.getTextPass());
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bf = new BufferedReader(isr);
			System.err.println("SERVIDOR >>> Encryption mode...");
			int opcion = Integer.parseInt(bf.readLine());
			if (opcion == 1) {
				modPass.AsciiEnc(modPass.getTextPass());
			} else if (opcion == 2) {
				modPass.MD5Enc("myKey", modPass.getTextPass());
			}
			outObject = new ObjectOutputStream(socket.getOutputStream());
			outObject.writeObject(modPass);
			System.err.println("SERVIDOR >>> Sending complete object... > "+modPass.getTextPass()+" - "+modPass.getEncryptedPass());
			outObject.close();
			inObject.close();
			socket.close();
		//	servidor.close();
			
			
		} catch (InterruptedException e) {
		} catch (ClassNotFoundException e) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}


