open module fr.raksrinana.utils.mail {
	requires java.desktop;
	requires fr.raksrinana.utils.base;
	requires fr.raksrinana.utils.javafx;
	requires static lombok;
	requires org.slf4j;
	requires jakarta.mail;
	exports fr.raksrinana.utils.mail;
}