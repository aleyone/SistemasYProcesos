package es.syp4.ae4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		Scanner teclado = new Scanner (System.in);
		System.out.print("Introduce IP: ");
		String ip = teclado.nextLine();
		System.out.print("Introduce puerto: ");
		int port=Integer.parseInt(teclado.nextLine());
		System.out.print("CLIENTE >> Waiting server...");
		try {
			Socket cliente = new Socket (ip, port);
			ObjectInputStream inObject = new ObjectInputStream(cliente.getInputStream());
			Password pass = (Password) inObject.readObject();
			System.out.println("CLIENTE >> Receiving from server... >> "+pass.getTextPass());
			System.out.println("CLIENTE >> Updating object...");
			System.out.print("Introduce el password a encriptar: ");
			String password = teclado.nextLine();
			pass.setTextPass(password);			
			Thread.sleep(3000);
			System.out.println("CLIENTE >> Sending update object to server... >> "+pass.getTextPass());
			ObjectOutputStream outObject = new ObjectOutputStream(cliente.getOutputStream());
			outObject.writeObject(pass);
			Thread.sleep(2000);
			System.out.print("CLIENTE >> Wich option to convert? 1- Ascii Encryption - 2- MD5 Encryption");
			int opcion = Integer.parseInt(teclado.nextLine());
			PrintWriter pw = new PrintWriter(cliente.getOutputStream());
			pw.print(opcion+"\n");
			pw.flush();
			inObject = new ObjectInputStream(cliente.getInputStream());
			pass = (Password) inObject.readObject();
			System.out.println("CLIENTE >> Receiving encrypted password..."+pass.getTextPass()+" - "+pass.getEncryptedPass());
			inObject.close();
			outObject.close();
			cliente.close();
			teclado.close();			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


