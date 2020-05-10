open module fr.raksrinana.utils.config {
	requires fr.raksrinana.utils.base;
	requires java.sql;
	requires sqlite.jdbc;
	requires org.slf4j;
	requires static lombok;
	exports fr.raksrinana.utils.config;
	requires com.zaxxer.hikari;
}