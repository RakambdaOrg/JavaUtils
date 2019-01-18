package fr.mrcraftcod.utils.http;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
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
	private static HttpClient makeClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException{
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (chain, authType) -> true).build(), NoopHostnameVerifier.INSTANCE);
		RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
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
	public static GetRequest getRequest(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		URIBuilder uriBuilder = getBuilder(url, params);
		return Unirest.get(uriBuilder.build().toString()).headers(headers);
	}
	
	private static URIBuilder getBuilder(URL url, Map<String, String> params) throws URISyntaxException{
		URIBuilder uriBuilder = new URIBuilder(url.toURI());
		if(params != null){
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
		try{
			Unirest.shutdown();
		}
		catch(IOException ignored){
		}
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
	private static GetRequest headRequest(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
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
	public static RequestBodyEntity postRequest(URL url, Map<String, String> headers, Map<String, String> params, String body) throws URISyntaxException{
		URIBuilder uriBuilder = getBuilder(url, params);
		return Unirest.post(uriBuilder.build().toString()).headers(headers).body(body);
	}
	static{
		try{
			Unirest.setHttpClient(makeClient());
			//Unirest.setDefaultHeader(USER_AGENT_KEY, USER_AGENT);
			//Unirest.setDefaultHeader(LANGUAGE_TYPE_KEY, LANGUAGE_TYPE);
			//Unirest.setDefaultHeader(CONTENT_TYPE_KEY, CONTENT_TYPE);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
