module fr.mrcraftcod.utils.http
{
	requires transitive fr.mrcraftcod.utils.base;
	
	requires org.apache.commons.text;
	requires org.apache.httpcomponents.httpcore;
	requires org.apache.httpcomponents.httpclient;
	requires org.jsoup;
	requires unirest.java;
	requires slf4j.api;
	
	exports fr.mrcraftcod.utils.http;
	exports fr.mrcraftcod.utils.http.requestssenders;
	exports fr.mrcraftcod.utils.http.requestssenders.get;
}