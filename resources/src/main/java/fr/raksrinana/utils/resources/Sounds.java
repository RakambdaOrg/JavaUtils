package fr.raksrinana.utils.resources;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class Sounds{
	private static final Logger LOGGER = LoggerFactory.getLogger(Sounds.class);
	private final ResourceElement resource;
	private final String path;
	
	public Sounds(@NonNull ResourceElement resource, @NonNull String name){
		this.resource = resource;
		this.path = name;
	}
	
	public void playSound(@NonNull ResourcesBase resourcesBase){
		new Thread(() -> {
			try{
				final Clip clip = AudioSystem.getClip();
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(resourcesBase.getResource(resource, Sounds.this.path));
				clip.open(inputStream);
				clip.start();
				clip.addLineListener(event -> {
					if(event.getType() == LineEvent.Type.STOP){
						clip.close();
					}
				});
			}
			catch(Exception e){
				LOGGER.warn("Couldn't play sound {}", Sounds.this.path, e);
			}
		}).start();
	}
}