/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 22/09/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-09-22
 */
open module fr.raksrinana.utils.base {
	requires org.slf4j;
	requires org.apache.commons.io;
	requires java.validation;
	requires java.desktop;
	requires com.sun.jna;
	requires com.sun.jna.platform;
	requires send.notification;
	exports fr.raksrinana.utils.base;
}