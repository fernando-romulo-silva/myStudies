package murach.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtilYahoo {

    public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML) throws MessagingException {

	// 1 - get a mail session
	Properties props = new Properties();
	props.put("mail.transport.protocol", "smtps");
	props.put("mail.smtps.host", "smtp.mail.yahoo.com");
	props.put("mail.smtps.port", 465);
	props.put("mail.smtps.auth", "true");
	Session session = Session.getDefaultInstance(props);
	session.setDebug(true);

	// 2 - create a message
	Message message = new MimeMessage(session);
	message.setSubject(subject);
	if (bodyIsHTML) {
	    message.setContent(body, "text/html");
	} else {
	    message.setText(body);
	}

	// 3 - address the message
	Address fromAddress = new InternetAddress(from);
	Address toAddress = new InternetAddress(to);
	message.setFrom(fromAddress);
	message.setRecipient(Message.RecipientType.TO, toAddress);

	// 4 - send the message
	Transport transport = session.getTransport();
	transport.connect("johnsmith@yahoo.com", "sesame");
	transport.sendMessage(message, message.getAllRecipients());
	transport.close();
    }
}