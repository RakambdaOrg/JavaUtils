package fr.mrcraftcod.utils.http;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import fr.mrcraftcod.utils.MCCUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class URLHandler
{
	private static final int TIMEOUT = 30000;
	private static final String USER_AGENT_KEY = "User-Agent";
	private static final String USER_AGENT = "MrCraftCod/Utils/" + MCCUtils.VERSION;
	private static final String JSON_TYPE_KEY = "accept";
	private static final String JSON_TYPE = "application/json";
	private static final String CHARSET_TYPE_KEY = "charset";
	private static final String CHARSET_TYPE = "utf-8";
	private static final String CONTENT_TYPE_KEY = "Content-Type";
	private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
	private static final String LANGUAGE_TYPE_KEY = "Accept-Language";
	private static final String LANGUAGE_TYPE = Locale.getDefault().toString() + ";q=1,en;q=0.8";

	public static long getConnectionLinkLength(URL url) throws UnirestException, URISyntaxException
	{
		return getConnectionLinkLength(url, null);
	}

	public static long getConnectionLinkLength(URL url, Map<String, String> headers) throws UnirestException, URISyntaxException
	{
		return getConnectionLinkLength(url, headers, null);
	}

	public static long getConnectionLinkLength(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException, UnirestException
	{
		GetRequest req = headRequest(url, headers, params);
		HttpResponse<String> result = req.asString();
		long length = 0;
		Headers header = result.getHeaders();
		if(header.containsKey("content-length"))
			for(String value : header.get("content-length"))
				length += Long.parseLong(value);
		return length;
	}


	public static HttpClient makeClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException
	{
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(SSLContexts.custom().loadTrustMaterial(null, (chain, authType) -> true).build(), NoopHostnameVerifier.INSTANCE);
		return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).setConnectionTimeToLive(TIMEOUT, TimeUnit.MILLISECONDS).setUserAgent(USER_AGENT).build();
	}

	public static InputStream getAsBinary(URL url) throws UnirestException, URISyntaxException
	{
		return getAsBinary(url, null);
	}

	public static InputStream getAsBinary(URL url, Map<String, String> headers) throws URISyntaxException, UnirestException
	{
		return getAsBinary(url, headers, null);
	}

	public static InputStream getAsBinary(URL url, Map<String, String> headers, Map<String, String> params) throws UnirestException, URISyntaxException
	{
		GetRequest req = getRequest(url, headers, params);
		HttpResponse<InputStream> binaryResponse = req.asBinary();
		return binaryResponse.getBody();
	}

	public static JSONObject getAsJSON(URL url) throws UnirestException, URISyntaxException
	{
		Map<String, String> headers = new HashMap<>();
		headers.put(JSON_TYPE_KEY, JSON_TYPE);
		return getAsJSON(url, headers);
	}

	public static JSONObject getAsJSON(URL url, Map<String, String> headers) throws URISyntaxException, UnirestException
	{
		return getAsJSON(url, headers, null);
	}

	public static JSONObject getAsJSON(URL url, Map<String, String> headers, Map<String, String> params) throws UnirestException, URISyntaxException
	{
		headers.put(JSON_TYPE_KEY, JSON_TYPE);
		GetRequest req = getRequest(url, headers, params);
		HttpResponse<JsonNode> jsonResponse = req.asJson();
		return jsonResponse.getBody().getObject();
	}

	public static String getAsString(URL url) throws UnirestException, URISyntaxException
	{
		return getAsString(url, null);
	}

	public static String getAsString(URL url, Map<String, String> headers) throws UnirestException, URISyntaxException
	{
		return getAsString(url, headers, null);
	}

	public static String getAsString(URL url, Map<String, String> headers, Map<String, String> params) throws UnirestException, URISyntaxException
	{
		GetRequest req = getRequest(url, headers, params);
		HttpResponse<String> response = req.asString();
		return response.getBody();
	}

	public static String[] getAsStringArray(URL url) throws UnirestException, URISyntaxException
	{
		return getAsStringArray(url, null);
	}

	public static String[] getAsStringArray(URL url, Map<String, String> headers) throws UnirestException, URISyntaxException
	{
		return getAsString(url, headers).split("\n");
	}

	public static String getStatusText(URL url) throws UnirestException, URISyntaxException
	{
		return getStatusText(url, null);
	}

	public static String getStatusText(URL url, Map<String, String> headers) throws URISyntaxException, UnirestException
	{
		return getStatusText(url, headers, null);
	}

	public static String getStatusText(URL url, Map<String, String> headers, Map<String, String> params) throws UnirestException, URISyntaxException
	{
		GetRequest request = getRequest(url, headers, params);
		return request.asBinary().getStatusText();
	}

	public static GetRequest getRequest(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException
	{
		Unirest.clearDefaultHeaders();
		Unirest.setDefaultHeader(USER_AGENT_KEY, USER_AGENT);
		URIBuilder uriBuilder = new URIBuilder(new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef()));
		if(params != null)
			for(String key : params.keySet())
				uriBuilder.addParameter(key, params.get(key));
		return Unirest.get(uriBuilder.build().toString()).headers(headers).header(LANGUAGE_TYPE_KEY, LANGUAGE_TYPE).header(CONTENT_TYPE_KEY, CONTENT_TYPE).header(CHARSET_TYPE_KEY, CHARSET_TYPE).header(USER_AGENT_KEY, USER_AGENT);
	}

	private static GetRequest headRequest(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException
	{
		Unirest.clearDefaultHeaders();
		Unirest.setDefaultHeader(USER_AGENT_KEY, USER_AGENT);
		URIBuilder uriBuilder = new URIBuilder(new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef()));
		if(params != null)
			for(String key : params.keySet())
				uriBuilder.addParameter(key, params.get(key));
		return Unirest.head(uriBuilder.build().toString()).headers(headers).header(LANGUAGE_TYPE_KEY, LANGUAGE_TYPE).header(CONTENT_TYPE_KEY, CONTENT_TYPE).header(CHARSET_TYPE_KEY, CHARSET_TYPE).header(USER_AGENT_KEY, USER_AGENT);
	}

	public static int getStatus(URL url) throws UnirestException, URISyntaxException
	{
		return getStatus(url, null);
	}

	public static int getStatus(URL url, Map<String, String> headers) throws URISyntaxException, UnirestException
	{
		return getStatus(url, headers, null);
	}

	public static int getStatus(URL url, Map<String, String> headers, Map<String, String> params) throws UnirestException, URISyntaxException
	{
		GetRequest request = getRequest(url, headers, params);
		return request.asBinary().getStatus();
	}

	public static void exit()
	{
		try
		{
			Unirest.shutdown();
		}
		catch(IOException ignored)
		{
		}
	}

	public static Document getJsoup(String url) throws URISyntaxException, UnirestException, MalformedURLException
	{
		return Jsoup.parse(getAsString(new URL(url)));
	}

	public static Document getJsoup(URL url) throws URISyntaxException, UnirestException
	{
		return Jsoup.parse(getAsString(url));
	}

	static
	{
		try
		{
			Unirest.setHttpClient(makeClient());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
