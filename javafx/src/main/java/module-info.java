open module fr.raksrinana.utils.javafx {
	requires static javafx.base;
	requires static javafx.controls;
	requires static javafx.graphics;
	requires static javafx.swing;
	requires fr.raksrinana.utils.base;
	requires org.slf4j;
	requires static lombok;
	exports fr.raksrinana.utils.javafx;
	exports fr.raksrinana.utils.javafx.components;
}