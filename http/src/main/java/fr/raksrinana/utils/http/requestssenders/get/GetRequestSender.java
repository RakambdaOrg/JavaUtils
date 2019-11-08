package fr.raksrinana.utils.http.requestssenders.get;

import fr.raksrinana.utils.http.URLHandler;
import fr.raksrinana.utils.http.requestssenders.RequestSender;
import kong.unirest.GetRequest;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public abstract class GetRequestSender<T> implements RequestSender<T>{
	private final GetRequest request;
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException    If the URL isn't valid.
	 * @throws MalformedURLException If the URL isn't valid.
	 */
	public GetRequestSender(@Nonnull String url) throws URISyntaxException, MalformedURLException{
		this(new URL(url));
	}
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public GetRequestSender(@Nonnull URL url) throws URISyntaxException{
		this(url, null);
	}
	
	/**
	 * Constructor.
	 *
	 * @param url     The URL.
	 * @param headers The headers.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public GetRequestSender(@Nonnull URL url, @Nullable Map<String, String> headers) throws URISyntaxException{
		this(url, headers, null);
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
	public GetRequestSender(@Nonnull URL url, @Nullable Map<String, String> headers, @Nullable Map<String, String> params) throws URISyntaxException{
		request = URLHandler.getRequest(url, headers, params);
	}
	
	/**
	 * Get the request.
	 *
	 * @return The request.
	 */
	@Nonnull
	public GetRequest getRequest(){
		return this.request;
	}
}
