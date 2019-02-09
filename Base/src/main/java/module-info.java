/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 22/09/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-09-22
 */
open module fr.mrcraftcod.utils.base {
	requires transitive java.desktop;
	requires transitive java.logging;
	
	requires org.slf4j;
	requires org.apache.commons.io;
	
	exports fr.mrcraftcod.utils.base;
}