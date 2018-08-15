module fr.mrcraftcod.utils.config
{
	requires transitive fr.mrcraftcod.utils.base;
	requires transitive java.sql;
	
	requires jdeferred.core;
	requires sqlite.jdbc;
	requires slf4j.api;
	
	exports fr.mrcraftcod.utils.config;
}