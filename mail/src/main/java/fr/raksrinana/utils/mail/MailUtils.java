package fr.raksrinana.utils.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nonnull;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

public class MailUtils{
	private static final Logger LOGGER = LoggerFactory.getLogger(MailUtils.class);
	
	public static void sendMail(@Nonnull Session session, @Nonnull String emailFrom, @Nonnull String fromName, String to, @Nonnull String object, @Nonnull String body) throws MessagingException, UnsupportedEncodingException{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(emailFrom, fromName));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(object);
		message.setText(body);
		Transport.send(message);
	}
	
	@Nonnull
	public static Optional<MessageContent> getMessageContent(@Nonnull Message message){
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
								LOGGER.info("Unrecognized part {}", part.getContentType());
						}
					}
					catch(Exception e){
						LOGGER.warn("Error getting multipart {}: {}", i, part, e);
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
}
