open module fr.mrcraftcod.utils.config
{
	requires transitive fr.mrcraftcod.utils.base;
	requires transitive java.sql;
	
	requires jdeferred.core;
	requires sqlite.jdbc;
	requires org.slf4j;
	
	exports fr.mrcraftcod.utils.config;
}