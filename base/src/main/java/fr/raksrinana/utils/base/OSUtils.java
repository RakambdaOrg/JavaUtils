package fr.raksrinana.utils.base;

import com.sun.jna.Platform;
import fr.jcgay.notification.Application;
import fr.jcgay.notification.Notification;
import fr.jcgay.notification.Notifier;
import fr.jcgay.notification.SendNotification;
import lombok.NonNull;
import java.util.Objects;

public class OSUtils{
	private static Notifier NOTIFIER;
	
	public enum OS{
		WIN, OSX, LINUX
	}
	
	/**
	 * Sends an OS notification.
	 *
	 * @param application The application to send the notification from.
	 * @param title       The title of the notification.
	 * @param message     The content of the notification.
	 */
	public static void notify(Application application, String title, String message){
		final var notifier = getNotifier(application);
		try{
			notifier.send(Notification.builder().title(title).message(message).icon(application.icon()).build());
		}
		finally{
			notifier.close();
		}
	}
	
	/**
	 * Get a notifier for a given application.
	 *
	 * @param application The application associated to the notifier (see {@link SendNotification#setApplication(Application)}.
	 *
	 * @return The notifier.
	 */
	@NonNull
	private static Notifier getNotifier(Application application){
		if(Objects.isNull(NOTIFIER)){
			NOTIFIER = new SendNotification().setApplication(application).initNotifier();
		}
		return NOTIFIER;
	}
	
	/**
	 * Try to get the OS we're currently on.
	 *
	 * @return The os. If the OS is not Windows or MacOSX, we'll assume {@link OS#LINUX}.
	 */
	@NonNull
	public static OS getOs(){
		return Platform.isMac() ? OS.OSX : Platform.isWindows() ? OS.WIN : OS.LINUX;
	}
}
