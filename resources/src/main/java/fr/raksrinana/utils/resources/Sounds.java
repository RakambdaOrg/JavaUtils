package fr.raksrinana.utils.resources;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

@Slf4j
public class Sounds{
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
				log.warn("Couldn't play sound {}", Sounds.this.path, e);
			}
		}).start();
	}
}