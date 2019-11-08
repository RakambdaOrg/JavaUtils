/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 22/09/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-09-22
 */
open module fr.raksrinana.utils.mail {
	requires java.desktop;
	requires fr.raksrinana.utils.base;
	requires fr.raksrinana.utils.javafx;
	requires jsr305;
	requires org.slf4j;
	requires jakarta.mail;
	exports fr.raksrinana.utils.mail;
}