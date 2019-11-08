/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 22/09/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-09-22
 */
open module fr.raksrinana.utils.javafx {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.swing;
	requires fr.raksrinana.utils.base;
	requires org.slf4j;
	requires jsr305;
	exports fr.raksrinana.utils.javafx;
	exports fr.raksrinana.utils.javafx.components;
}