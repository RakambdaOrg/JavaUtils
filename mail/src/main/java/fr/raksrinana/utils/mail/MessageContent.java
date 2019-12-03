package fr.raksrinana.utils.mail;

import lombok.Getter;
import lombok.NonNull;
import java.awt.Image;

@Getter
public class MessageContent{
	private boolean hasText;
	private boolean hasHTML;
	private boolean hasImage;
	private boolean hasVideo;
	private String textContent;
	private String htmlContent;
	
	public MessageContent(){
		this.hasText = false;
		this.hasHTML = false;
		this.hasImage = false;
		this.hasVideo = false;
		this.textContent = "";
		this.htmlContent = "";
	}
	
	@Override
	public String toString(){
		StringBuilder stringBuilder = new StringBuilder("MessageContent@").append(this.hashCode());
		stringBuilder.append("\nTextContent:").append("\n\t").append(this.getTextContent());
		stringBuilder.append("\nHTMLContent:").append("\n\t").append(this.getHtmlContent());
		return stringBuilder.toString();
	}
	
	@NonNull
	public MessageContent appendTextContent(@NonNull String textContent){
		this.hasText = true;
		this.textContent += textContent;
		return this;
	}
	
	@NonNull
	public MessageContent appendHTMLContent(@NonNull String htmlContent){
		this.hasHTML = true;
		this.htmlContent += htmlContent;
		return this;
	}
	
	@NonNull
	public MessageContent addImage(@NonNull Image image){
		this.hasImage = true;
		return this;
	}
	
	@NonNull
	public MessageContent addVideo(@NonNull Video video){
		this.hasVideo = true;
		return this;
	}
}
