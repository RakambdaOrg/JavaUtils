/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 22/09/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-09-22
 */
module fr.mrcraftcod.utils.mail {
	requires transitive java.desktop;
	requires transitive fr.mrcraftcod.utils.base;
	
	requires javax.mail.api;
	requires imap;
	requires slf4j.api;
	
	exports fr.mrcraftcod.utils.mail;
}