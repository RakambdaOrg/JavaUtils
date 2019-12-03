package fr.raksrinana.utils.http.requestssenders.post;

import kong.unirest.HttpResponse;
import kong.unirest.RawResponse;
import kong.unirest.UnirestException;
import lombok.NonNull;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class BinaryPostRequestSender extends PostRequestSender<InputStream>{
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException    If the URL isn't valid.
	 * @throws MalformedURLException If the URL isn't valid.
	 */
	public BinaryPostRequestSender(@NonNull String url) throws URISyntaxException, MalformedURLException{
		super(url);
	}
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public BinaryPostRequestSender(@NonNull URL url) throws URISyntaxException{
		super(url, null);
	}
	
	/**
	 * Constructor.
	 *
	 * @param url     The URL.
	 * @param headers The headers.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public BinaryPostRequestSender(@NonNull URL url, Map<String, String> headers) throws URISyntaxException{
		super(url, headers, null);
	}
	
	/**
	 * Constructor.
	 *
	 * @param url     The URL.
	 * @param headers The headers.
	 * @param params  The params.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public BinaryPostRequestSender(@NonNull URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
	}
	
	public BinaryPostRequestSender(@NonNull URL url, Map<String, String> headers, Map<String, String> params, @NonNull String body) throws URISyntaxException{
		super(url, headers, params, body);
	}
	
	@Override
	@NonNull
	public HttpResponse<InputStream> getRequestResult() throws UnirestException{
		return this.getRequest().asObject(RawResponse::getContent);
	}
}
