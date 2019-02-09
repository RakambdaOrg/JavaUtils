/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 22/09/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-09-22
 */
open module fr.mrcraftcod.utils.mail {
	requires transitive java.desktop;
	requires transitive fr.mrcraftcod.utils.base;
	requires transitive fr.mrcraftcod.utils.javafx;
	
	requires org.slf4j;
	requires jakarta.mail;
	
	exports fr.mrcraftcod.utils.mail;
}