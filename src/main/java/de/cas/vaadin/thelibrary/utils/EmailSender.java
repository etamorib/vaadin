package de.cas.vaadin.thelibrary.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import de.cas.vaadin.thelibrary.model.bean.Reader;

public class EmailSender {
	private Reader r;
	private Properties prop;
	private static final String username = "014a73006ce6f4";
	private static final String password = "2f95a385eabd43";
	
	public EmailSender(Reader r) {
		this.r = r;
	}
	
	private Session configure(String username, String password) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.mailtrap.io");
		prop.put("mail.smtp.port", "25");
		prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
		
		Session session = Session.getInstance(prop, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, password);
		    }
		});
		
		return session;
	}
	public void send() throws MessagingException {
					
	
		Message message = new MimeMessage(configure(username, password));
		message.setFrom(new InternetAddress("admin@casthelibrary.hu"));
		message.setRecipients(
			Message.RecipientType.TO, InternetAddress.parse(r.getEmail()));
		message.setSubject("Mail Subject");
			 
		String msg = "Dear "+r.getName()+",<br>"
				+ "Your have uncompleted rentals in Cas the library!<br>"
				+ "Please, bring back the rented books!<br>"
				+ "<strong>NOTE: You will be charged!</strong><br>"
				+ "Regards, Cas the library team";
			 
		MimeBodyPart mimeBodyPart = new MimeBodyPart();
		mimeBodyPart.setContent(msg, "text/html");
			 
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(mimeBodyPart);
			 
		message.setContent(multipart);
			 
		Transport.send(message);
		
	}

}
