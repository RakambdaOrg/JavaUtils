package fr.raksrinana.utils.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import kong.unirest.GetRequest;
import kong.unirest.ObjectMapper;
import kong.unirest.RequestBodyEntity;
import kong.unirest.Unirest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
	 * Creates a new client.
	 *
	 * @return The client.
	 *
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	@Nonnull
	private static HttpClient makeClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException{
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (chain, authType) -> true).build(), NoopHostnameVerifier.INSTANCE);
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		Unirest.config().setObjectMapper(new ObjectMapper(){
			private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
			
			public <T> T readValue(String value, Class<T> valueType){
				try{
					return jacksonObjectMapper.readValue(value, valueType);
				}
				catch(IOException e){
					throw new RuntimeException(e);
				}
			}
			
			public String writeValue(Object value){
				try{
					return jacksonObjectMapper.writeValueAsString(value);
				}
				catch(JsonProcessingException e){
					throw new RuntimeException(e);
				}
			}
		});
		return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).setDefaultRequestConfig(globalConfig).setConnectionTimeToLive(TIMEOUT, TimeUnit.MILLISECONDS).build();
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
	@Nonnull
	public static GetRequest getRequest(@Nonnull URL url, @Nullable Map<String, String> headers, @Nullable Map<String, String> params) throws URISyntaxException{
		URIBuilder uriBuilder = getBuilder(url, params);
		return Unirest.get(uriBuilder.build().toString()).headers(headers);
	}
	
	@Nonnull
	private static URIBuilder getBuilder(@Nonnull URL url, @Nullable Map<String, String> params) throws URISyntaxException{
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
	@Nonnull
	private static GetRequest headRequest(@Nonnull URL url, @Nullable Map<String, String> headers, @Nullable Map<String, String> params) throws URISyntaxException{
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
	@Nonnull
	public static RequestBodyEntity postRequest(@Nonnull URL url, @Nullable Map<String, String> headers, @Nullable Map<String, String> params, @Nonnull String body) throws URISyntaxException{
		URIBuilder uriBuilder = getBuilder(url, params);
		return Unirest.post(uriBuilder.build().toString()).headers(headers).body(body);
	}
	
	static{
		Unirest.config().connectTimeout(TIMEOUT).socketTimeout(TIMEOUT).enableCookieManagement(true).verifySsl(true);
	}
}
