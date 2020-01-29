package fr.raksrinana.utils.http;

import kong.unirest.Unirest;
import lombok.NonNull;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;

public class URLHandler{
	private static final int TIMEOUT = 30000;
	/**
	 * Setups Unirest.
	 */
	@NonNull
	private static void setupClient(){
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		Unirest.config().setObjectMapper(new JacksonObjectMapper()).connectTimeout(TIMEOUT).socketTimeout(TIMEOUT).enableCookieManagement(true).verifySsl(true);
	}
	static{
		setupClient();
	}
	
	/**
	 * Stops Unirest.
	 */
	public static void exit(){
		Unirest.shutDown();
	}
}
