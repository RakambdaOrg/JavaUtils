package fr.raksrinana.utils.http.requestssenders.get;

import kong.unirest.HttpResponse;
import kong.unirest.RawResponse;
import kong.unirest.UnirestException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class BinaryGetRequestSender extends GetRequestSender<InputStream>{
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException    If the URL isn't valid.
	 * @throws MalformedURLException If the URL isn't valid.
	 */
	public BinaryGetRequestSender(@Nonnull String url) throws URISyntaxException, MalformedURLException{
		super(url);
	}
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public BinaryGetRequestSender(@Nonnull URL url) throws URISyntaxException{
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
	public BinaryGetRequestSender(@Nonnull URL url, @Nullable Map<String, String> headers) throws URISyntaxException{
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
	public BinaryGetRequestSender(@Nonnull URL url, @Nullable Map<String, String> headers, @Nullable Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
	}
	
	@Override
	@Nonnull
	public HttpResponse<InputStream> getRequestResult() throws UnirestException{
		return this.getRequest().asObject(RawResponse::getContent);
	}
}
