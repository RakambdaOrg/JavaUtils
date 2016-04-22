package fr.mrcraftcod.utils.mail;

import com.sun.javaws.exceptions.InvalidArgumentException;
import javax.mail.*;
import javax.mail.event.MessageCountEvent;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class GMailUtils
{
	private static final String GMAIL_SMTP_HOST = "smtp.gmail.com";

	public static void sendGMail(String user, String password, String from, String to, String object, String body) throws MessagingException
	{
		MailUtils.sendMail(getGMailSession(user, password), from, to, object, body);
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

	public static GMailFetcher fetchGMailFolder(String user, String password, String folder, Consumer<MessageCountEvent> callback) throws MessagingException, InvalidArgumentException
	{
		return new GMailFetcher(getGMailStore(user, password), folder, callback);
	}

	public static GMailFetcher fetchGMailFolder(String user, String password, String folder, ExecutorService executor, Consumer<MessageCountEvent> callback) throws MessagingException, InvalidArgumentException
	{
		return new GMailFetcher(getGMailStore(user, password), folder, executor, callback);
	}
}
