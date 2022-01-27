package es.sypbl5.ae5;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EMAILManager {

	public void sendMail(String user, String pass) throws UnsupportedEncodingException, MessagingException {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", user);
		props.put("mail.smtp.clave", pass);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", 587);
		
		Session session = Session.getDefaultInstance(props);

		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(user));

		message.addRecipients(Message.RecipientType.TO, "mantenimientoinvernalia@gmail.com");
		message.addRecipients(Message.RecipientType.CC, "megustaelfresquito@gmail.com");
		message.addRecipients(Message.RecipientType.CC, "david.gil.dam@gmail.com");

		message.setSubject("AVERIA 4U");
		Multipart multipart = new MimeMultipart();
		
		BodyPart messageBodyPart1 = new MimeBodyPart();
		messageBodyPart1.setText(
				"Hemos sufrido una avería en la Guardia de la Noche. Se nos ha estropeado la estufa y hace un frío que pela.\n\nFdo. David Gil");
		multipart.addBodyPart(messageBodyPart1);
		
		String[] adjuntos = { "./Averia.pdf", "./EstufaEmergencia.jpg" };
		
		for (int i=0; i<adjuntos.length;i++) {
			BodyPart messageBodyPart2= new MimeBodyPart();		
			DataSource src= new FileDataSource(adjuntos[i]);
			messageBodyPart2.setDataHandler(new DataHandler(src));
			messageBodyPart2.setFileName(adjuntos[i]);
			multipart.addBodyPart(messageBodyPart2);
		}
		
		message.setContent(multipart);
		
		Transport transport= session.getTransport("smtp");
		transport.connect(host, user, pass);
		transport.sendMessage(message, message.getAllRecipients());
		System.out.println("Mensaje enviado al técnico de guardia.");
		transport.close();

	}
}
