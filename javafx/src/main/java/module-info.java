open module fr.raksrinana.utils.javafx {
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
	requires transitive javafx.swing;
	requires fr.raksrinana.utils.base;
	requires org.slf4j;
	requires static lombok;
	exports fr.raksrinana.utils.javafx;
	exports fr.raksrinana.utils.javafx.components;
}