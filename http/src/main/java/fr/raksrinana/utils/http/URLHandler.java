package fr.raksrinana.utils.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import kong.unirest.*;
import lombok.NonNull;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.utils.URIBuilder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class URLHandler{
	private static final int TIMEOUT = 30000;
	private static final String USER_AGENT_KEY = "User-Agent";
	private static final String USER_AGENT = "MrCraftCod/Utils";
	private static final String CHARSET_TYPE_KEY = "charset";
	private static final String CHARSET_TYPE = "utf-8";
	private static final String CONTENT_TYPE_KEY = "Content-Type";
	private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
	private static final String LANGUAGE_TYPE_KEY = "Accept-Language";
	private static final String LANGUAGE_TYPE = Locale.getDefault().toString() + ";q=1,en;q=0.8";
	/**
	 * Setups Unirest.
	 */
	@NonNull
	private static void setupClient(){
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		Unirest.config().setObjectMapper(new ObjectMapper(){
			private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
			
			@Override
			public <T> T readValue(String value, Class<T> valueType){
				try{
					return jacksonObjectMapper.readValue(value, valueType);
				}
				catch(IOException e){
					throw new RuntimeException(e);
				}
			}
			
			@Override
			public <T> T readValue(String value, GenericType<T> genericType){
				try{
					return this.jacksonObjectMapper.readValue(value, new TypeReference<>(){});
				}
				catch(IOException var4){
					throw new RuntimeException(var4);
				}
			}
			
			@Override
			public String writeValue(Object value){
				try{
					return jacksonObjectMapper.writeValueAsString(value);
				}
				catch(JsonProcessingException e){
					throw new RuntimeException(e);
				}
			}
		});
	}
	
	static{
		setupClient();
	}
	/**
	 * Sends a get request.
	 *
	 * @param url     The URL to send to.
	 * @param headers The headers.
	 * @param params  The parameters of the url.
	 *
	 * @return The GetRequest.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	@NonNull
	public static GetRequest getRequest(@NonNull URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		URIBuilder uriBuilder = getBuilder(url, params);
		return Unirest.get(uriBuilder.build().toString()).headers(headers);
	}
	
	@NonNull
	private static URIBuilder getBuilder(@NonNull URL url, Map<String, String> params) throws URISyntaxException{
		URIBuilder uriBuilder = new URIBuilder(url.toURI());
		if(Objects.nonNull(params)){
			for(String key : params.keySet()){
				uriBuilder.addParameter(key, params.get(key));
			}
		}
		return uriBuilder;
	}
	
	/**
	 * Stops Unirest.
	 */
	public static void exit(){
		Unirest.shutDown();
	}
	
	/**
	 * Sends a head request.
	 *
	 * @param url     The URL to send to.
	 * @param headers The headers.
	 * @param params  The parameters of the url.
	 *
	 * @return The GetRequest.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	@NonNull
	private static GetRequest headRequest(@NonNull URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		URIBuilder uriBuilder = getBuilder(url, params);
		return Unirest.head(uriBuilder.build().toString()).headers(headers);
	}
	
	/**
	 * Sends a post request.
	 *
	 * @param url     The URL to send to.
	 * @param headers The headers.
	 * @param params  The parameters of the url.
	 * @param body    The body of the post.
	 *
	 * @return The request.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	@NonNull
	public static RequestBodyEntity postRequest(@NonNull URL url, Map<String, String> headers, Map<String, String> params, @NonNull String body) throws URISyntaxException{
		URIBuilder uriBuilder = getBuilder(url, params);
		return Unirest.post(uriBuilder.build().toString()).headers(headers).body(body);
	}
	
	static{
		Unirest.config().connectTimeout(TIMEOUT).socketTimeout(TIMEOUT).enableCookieManagement(true).verifySsl(true);
	}
}
