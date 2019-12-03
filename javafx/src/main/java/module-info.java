open module fr.raksrinana.utils.javafx {
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.swing;
	requires fr.raksrinana.utils.base;
	requires org.slf4j;
	requires static lombok;
	exports fr.raksrinana.utils.javafx;
	exports fr.raksrinana.utils.javafx.components;
}