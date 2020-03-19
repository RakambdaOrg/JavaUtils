package fr.raksrinana.utils.mail;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;

@Slf4j
public class MailUtils{
	@NonNull
	public static Session getMailSession(@NonNull String host, int port, @NonNull String user, @NonNull String password){
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", Integer.toString(port));
		return Session.getInstance(properties, new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(user, password);
			}
		});
	}
	
	public static void sendMail(@NonNull Session session, @NonNull String emailFrom, @NonNull String fromName, String to, @NonNull String object, @NonNull String body) throws MessagingException, UnsupportedEncodingException{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(emailFrom, fromName));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(object);
		message.setText(body);
		Transport.send(message);
	}
	
	public static void sendMail(@NonNull Session session, @NonNull String emailFrom, @NonNull String fromName, Consumer<MimeMessage> messageFiller) throws MessagingException, UnsupportedEncodingException{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(emailFrom, fromName));
		messageFiller.accept(message);
		Transport.send(message);
	}
	
	@NonNull
	public static Optional<MessageContent> getMessageContent(@NonNull Message message){
		try{
			Object content = message.getContent();
			if(content instanceof String){
				return Optional.of(new MessageContent().appendTextContent(content.toString()));
			}
			else if(content instanceof Multipart){
				MessageContent messageContent = new MessageContent();
				Multipart parts = (Multipart) content;
				for(int i = 0; i < parts.getCount(); i++){
					BodyPart part = parts.getBodyPart(i);
					try{
						switch(part.getContentType().split(";")[0].toUpperCase()){
							case "TEXT/PLAIN":
								messageContent.appendTextContent(part.getContent().toString());
								break;
							case "TEXT/HTML":
								messageContent.appendHTMLContent(part.getContent().toString());
								break;
							case "IMAGE/JPEG":
								messageContent.addImage(ImageIO.read(part.getInputStream()));
								break;
							case "VIDEO/MP4":
								messageContent.addVideo(null);
								break;
							default:
								log.info("Unrecognized part {}", part.getContentType());
						}
					}
					catch(Exception e){
						log.warn("Error getting multipart {}: {}", i, part, e);
					}
				}
				return Optional.of(messageContent);
			}
		}
		catch(MessagingException | IOException e){
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	public static boolean forward(@NonNull Session session, @NonNull String user, @NonNull String fromName, @NonNull String to, @NonNull String toName, @NonNull Message message, @NonNull String subjectPrefix, @NonNull String header){
		try{
			Message forwardMessage = new MimeMessage(session);
			forwardMessage.setSubject(subjectPrefix + (message.getSubject() == null ? "" : message.getSubject()));
			forwardMessage.setFrom(new InternetAddress(user, fromName));
			forwardMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to, toName));
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(header);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			try{
				Object content = message.getContent();
				if(content instanceof String){
					BodyPart parts = new MimeBodyPart();
					parts.setText(String.valueOf(content));
					multipart.addBodyPart(parts);
				}
				else if(content instanceof Multipart){
					Multipart parts = (Multipart) content;
					for(int i = 0; i < parts.getCount(); i++){
						multipart.addBodyPart(parts.getBodyPart(i));
					}
				}
			}
			catch(MessagingException | IOException e){
				e.printStackTrace();
			}
			forwardMessage.setContent(multipart);
			Transport.send(forwardMessage);
			return true;
		}
		catch(Exception e){
			log.warn("Failed to forward message", e);
			return false;
		}
	}
}
