open module fr.raksrinana.utils.http {
	requires fr.raksrinana.utils.base;
	requires org.apache.commons.text;
	requires org.apache.httpcomponents.httpcore;
	requires org.apache.httpcomponents.httpclient;
	requires org.jsoup;
	requires unirest.java;
	requires org.slf4j;
	requires jsr305;
	requires com.fasterxml.jackson.core;
	requires com.fasterxml.jackson.databind;
	exports fr.raksrinana.utils.http;
	exports fr.raksrinana.utils.http.requestssenders;
	exports fr.raksrinana.utils.http.requestssenders.get;
	exports fr.raksrinana.utils.http.requestssenders.post;
}