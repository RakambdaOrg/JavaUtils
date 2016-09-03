package fr.mrcraftcod.utils.mail;

import javax.mail.*;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class GMailUtils
{
	private static final String GMAIL_SMTP_HOST = "smtp.gmail.com";

	public static void sendGMail(String user, String password, String from, String to, String object, String body) throws MessagingException, UnsupportedEncodingException
	{
		MailUtils.sendMail(getGMailSession(user, password), user, from, to, object, body);
	}

	public static Session getGMailSession(String user, String password)
	{
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", GMAIL_SMTP_HOST);
		properties.put("mail.smtp.port", "587");
		return Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
	}

	public static Store getGMailStore(String user, String password) throws MessagingException
	{
		Store store = getGMailSession(user, password).getStore("imaps");
		store.connect(GMAIL_SMTP_HOST, user, password);
		return store;
	}

	public static GMailFetcher fetchGMailFolder(String user, String password, String folder, Consumer<MessageCountEvent> callback) throws Exception
	{
		return new GMailFetcher(getGMailStore(user, password), folder, callback);
	}

	public static GMailFetcher fetchGMailFolder(String user, String password, String folder, ExecutorService executor, Consumer<MessageCountEvent> callback) throws Exception
	{
		return new GMailFetcher(getGMailStore(user, password), folder, executor, callback);
	}

	public static boolean transfer(String user, String password, String from, String to, Message message)
	{
		return transfer(user, password, from, to, message, "");
	}

	public static boolean transfer(String user, String password, String from, String to, Message message, String header)
	{
		try
		{
			Message forwardMessage = new MimeMessage(getGMailSession(user, password));
			forwardMessage.setSubject("Fwd: " + message.getSubject());
			forwardMessage.setFrom(new InternetAddress(from));
			forwardMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(header);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(message.getDataHandler());
			multipart.addBodyPart(messageBodyPart);
			forwardMessage.setContent(multipart);
			Transport.send(forwardMessage);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
