open module fr.raksrinana.utils.config {
	requires fr.raksrinana.utils.base;
	requires java.sql;
	requires org.slf4j;
	requires static lombok;
	requires sqlite.jdbc;
	requires com.zaxxer.hikari;
	requires com.h2database;
	exports fr.raksrinana.utils.config;
}