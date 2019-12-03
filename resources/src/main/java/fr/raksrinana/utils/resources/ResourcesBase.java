package fr.raksrinana.utils.resources;

import fr.raksrinana.utils.base.ImageUtils;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Font;
import lombok.NonNull;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;

public class ResourcesBase{
	private static final HashMap<String, Properties> properties = new HashMap<>();
	private final Class<?> rootClass;
	
	public ResourcesBase(@NonNull Class<?> rootClass){
		this.rootClass = rootClass;
	}
	
	@NonNull
	public Optional<WritableImage> getImage(@NonNull ResourceElement resourceElement, @NonNull String path, int width, int height){
		try{
			return Optional.of(SwingFXUtils.toFXImage(ImageUtils.resizeBufferedImage(ImageIO.read(getResource(resourceElement, path)), width, height), null));
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	@NonNull
	public URL getResource(@NonNull ResourceElement resourceElement, @NonNull String path){
		return this.rootClass.getResource("/" + resourceElement.getRootPath() + (resourceElement.getRootPath().equals("") ? "" : "/") + path);
	}
	
	@NonNull
	public Optional<String> getPropertyString(@NonNull ResourceElement resourceElement, @NonNull String path, @NonNull String key){
		try{
			final var prop = getProperties(resourceElement, path);
			if(prop.containsKey(key)){
				return Optional.ofNullable(getProperties(resourceElement, path).getProperty(key));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	@NonNull
	public Properties getProperties(@NonNull ResourceElement resourceElement, @NonNull String path) throws IOException{
		if(properties.containsKey(path)){
			return properties.get(path);
		}
		Properties prop = new Properties();
		prop.load(new File("./", path + ".properties").exists() ? new InputStreamReader(new FileInputStream(new File("./", path + ".properties")), StandardCharsets.UTF_8) : new InputStreamReader(getResource(resourceElement, path + ".properties").openStream(), StandardCharsets.UTF_8));
		properties.put(path, prop);
		return prop;
	}
	
	@NonNull
	public Font getFont(@NonNull ResourceElement resourceElement, @NonNull String path, double size) throws IOException{
		return Font.loadFont(getResource(resourceElement, path).openConnection().getInputStream(), size);
	}
}
