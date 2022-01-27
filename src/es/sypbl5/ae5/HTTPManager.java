package es.sypbl5.ae5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HTTPManager implements HttpHandler {
	int temperaturaActual = 15;
	int temperaturaTermostato = 15;
	EMAILManager gestorCorreo = new EMAILManager();

	@Override
	/**
	 * se decide si la petición se trata de un GET o un POST
	 */
	public void handle(HttpExchange httpExchange) throws IOException {
		String requestParamValue = null;
		if ("GET".equals(httpExchange.getRequestMethod())) {
			requestParamValue = handleGetRequest(httpExchange);
			handleGETResponse(httpExchange, requestParamValue);
		} else if ("POST".equals(httpExchange.getRequestMethod())) {
			requestParamValue = handlePostRequest(httpExchange);
			handlePOSTResponse(httpExchange, requestParamValue);
		}

	}

	/**
	 * gestión de un getRequest asignamos word a la primera palabra después del ?
	 * 
	 * @param httpExchange se recupera el tipo de petición
	 * @return retornamos el string esperado en función de la palabra enviada
	 */
	private String handleGetRequest(HttpExchange httpExchange) {
		String response = "";
		String word = httpExchange.getRequestURI().toString().split("\\?")[1];
		if (word.equals("temperaturaActual")) {
			response = "Temperatura Actual: " + String.valueOf(temperaturaActual) + "\n" + "Temperatura Termostato: "
					+ String.valueOf(temperaturaTermostato);
		} else
			response = "No es el parámetro adecuado";

		return response;
	}

	/**
	 * gestionamos la respuesta por pantalla
	 * 
	 * @param httpExchange      se recoge el tipo de petición
	 * @param requestParamValue se recoge la respuesta de handleGetRequest
	 */
	private void handleGETResponse(HttpExchange httpExchange, String requestParamValue) {
		OutputStream outputStream = httpExchange.getResponseBody();
		String htmlResponse = "<html><body>" + requestParamValue + "</body></html>";
		try {
			httpExchange.sendResponseHeaders(200, htmlResponse.length());
			outputStream.write(htmlResponse.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * gestionamos la petición POST, detectamos la primera palabra tras el ? para
	 * que sea la adecuada averiguamos los grados en la variable int
	 * 
	 * @param httpExchange se le pasa el tipo de petición
	 * @return devuelve el mensaje adecuado según el caso
	 */
	private String handlePostRequest(HttpExchange httpExchange) {
		String response = "";
		InputStream body = httpExchange.getRequestBody();
		InputStreamReader isr = new InputStreamReader (body);
		BufferedReader br = new BufferedReader (isr);
		String texto="";
		try {
			texto = br.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//System.out.println(texto);
		
		//Gestionamos el setTemperatura, si contiene la palabra aislamos la palabra y el parámetro
		if (texto.contains("setTemperatura")) {
			String word = texto.split("=")[0];
			int temp = 0;
			try {
				temp = Integer.parseInt(texto.split("=")[1]);
			} catch (NumberFormatException e) {
				response = "¿Seguro que has pasado un número como parámetro?";
			}
			
			if (temp < 5 || temp > 35) {
				response="Temperatura incorrecta: >=5 y <=35 (la estufa es un poco vieja aunque tenga gestor de correo";
				
			} else {
				temperaturaTermostato = temp;
				regularTemperatura(temperaturaActual, temp);				
				response = "Se ha actualizado la temperatura a: "+temperaturaActual;
			}
			
		//Gestionamos el post en caso de que se notifique la avería para aislar sus componentes
		} else if (texto.contains("notificarAveria")) {
			String user="";
			String pass="";
			String expectedMailTag = texto.split(":")[1].toString().split("=")[0];
			String expectedPassTag = texto.split(";")[1].toString().split("=")[0];
						
			if (expectedMailTag.contains("email_remitente")) {
				user = texto.split(":")[1].toString().split("=")[1].split(";")[0];
			} else {
				user = texto.split(":")[1].toString().split("=")[1].split(";")[0];
			}
			if (expectedPassTag.contains("pass_remitente")) {
				pass = texto.split(";")[1].toString().split("=")[1];
			} else {
				pass = texto.split(";")[1].toString().split("=")[1];
			}
			response = "Hemos recogido correctamente tu correo y password. Vamos a enviar un correo al Lord Stark";
			 
			//Solo en caso de tener un usuario y un pass se hace uso del envío del correo
			if (user!="" && pass!="") {
				notification(user,pass);
				response = "Hemos enviado tu correo a Lord Stark.";
				
			}

		} else {
			response = "No me has dicho el método correctamente, recuerda: -setTemperatura=grados- || -notificarAveria:email_remitente=EMAIL;pass_remitente=PASS";
		}

		return response;
	}

	/**
	 * gestionamos la respuesta del post
	 * 
	 * @param httpExchange      se pasa el tipo de petición
	 * @param requestParamValue se pasa la respuesta de handlePostRequest
	 */
	private void handlePOSTResponse(HttpExchange httpExchange, String requestParamValue) {
		OutputStream outputStream = httpExchange.getResponseBody();
		String htmlResponse = requestParamValue ;
		try {
			httpExchange.sendResponseHeaders(200, htmlResponse.length());
			outputStream.write(htmlResponse.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * regulador de temperatura, incrementa o decrementa en un grado cada 5 segundos
	 * la temperaturaActual hasta llegar a la temperaturaTermostato
	 * 
	 * @param temperaturaActual     recibimos temperaturaActual
	 * @param temperaturaTermostato recibimos temperaturaTermostato
	 */
	private void regularTemperatura(int ta, int tt) {
		System.out.println("Vamos a ajustar la temperatura tal y como has pedido.");
		System.out.println("Temperatura actual: "+temperaturaActual);
		int delay = 5000;
		if (ta < tt) {
			for (int i = ta; i < tt; i++) {
				temperaturaActual++;
				System.out.println("Temperatura actual: "+temperaturaActual);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (ta > tt) {
			for (int i = ta; i > tt; i--) {
				temperaturaActual--;
				System.out.println("Temperatura actual: "+temperaturaActual);

				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Se ha actualizado la temperatura correctamente.");
		System.out.println("Temperatura Actual: "+temperaturaActual+" || Temperatura Termostato: "+temperaturaTermostato);

	}
	
	/**
	 * gestionamos el envío del correo de alerta
	 * @param user
	 * @param pass
	 */
	private void notification (String user, String pass) {
		try {
			gestorCorreo.sendMail(user, pass);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
