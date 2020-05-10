open module fr.raksrinana.utils.config {
	requires fr.raksrinana.utils.base;
	requires java.sql;
	requires org.slf4j;
	requires static lombok;
	requires transitive sqlite.jdbc;
	requires transitive com.zaxxer.hikari;
	requires transitive com.h2database;
	exports fr.raksrinana.utils.config;
}