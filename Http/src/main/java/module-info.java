open module fr.mrcraftcod.utils.http
{
	requires transitive fr.mrcraftcod.utils.base;
	
	requires org.apache.commons.text;
	requires org.apache.httpcomponents.httpcore;
	requires org.apache.httpcomponents.httpclient;
	requires org.jsoup;
	requires unirest.java;
	requires org.slf4j;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	
	exports fr.mrcraftcod.utils.http;
	exports fr.mrcraftcod.utils.http.requestssenders;
	exports fr.mrcraftcod.utils.http.requestssenders.get;
	exports fr.mrcraftcod.utils.http.requestssenders.post;
}