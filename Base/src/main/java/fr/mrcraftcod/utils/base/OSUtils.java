package fr.mrcraftcod.utils.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/09/2016.
 *
 * @author Thomas Couchoud
 * @since 2016-09-25
 */
public class OSUtils{
	private final static Logger LOGGER = LoggerFactory.getLogger(OSUtils.class);
	
	/**
	 * Sends an OS notification.
	 *
	 * @param title   The title of the notification.
	 * @param message The content of the notification.
	 */
	public static void notify(String title, String message){
		if(isMac()){
			try{
				Runtime.getRuntime().exec(new String[]{
						"osascript",
						"-e",
						"display notification \"" + message + "\" with title \"" + title + "\""
				});
			}
			catch(IOException e){
				LOGGER.warn("Error while sending notification", e);
			}
		}
		else{
			LOGGER.info("OS not supported for notifications");
		}
	}
	
	/**
	 * Tells if the system is a mac.
	 *
	 * @return True if a mac, false otherwise.
	 */
	public static boolean isMac(){
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
}
