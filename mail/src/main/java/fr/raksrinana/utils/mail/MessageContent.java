package fr.raksrinana.utils.mail;

import javax.annotation.Nonnull;
import java.awt.Image;

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
		stringBuilder.append("\nHTMLContent:").append("\n\t").append(this.getHTMLContent());
		return stringBuilder.toString();
	}
	
	@Nonnull
	public String getTextContent(){
		return this.textContent;
	}
	
	@Nonnull
	public String getHTMLContent(){
		return this.htmlContent;
	}
	
	@Nonnull
	public MessageContent appendTextContent(@Nonnull String textContent){
		this.hasText = true;
		this.textContent += textContent;
		return this;
	}
	
	public boolean hasText(){
		return this.hasText;
	}
	
	@Nonnull
	public MessageContent appendHTMLContent(@Nonnull String htmlContent){
		this.hasHTML = true;
		this.htmlContent += htmlContent;
		return this;
	}
	
	public boolean hasHTML(){
		return this.hasHTML;
	}
	
	@Nonnull
	public MessageContent addImage(@Nonnull Image image){
		this.hasImage = true;
		return this;
	}
	
	public boolean hasImage(){
		return this.hasImage;
	}
	
	@Nonnull
	public MessageContent addVideo(@Nonnull Video video){
		this.hasVideo = true;
		return this;
	}
	
	public boolean hasVideo(){
		return this.hasVideo;
	}
}
