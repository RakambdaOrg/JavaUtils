package fr.raksrinana.utils.http.requestssenders.get;

import fr.raksrinana.utils.http.URLHandler;
import fr.raksrinana.utils.http.requestssenders.RequestSender;
import kong.unirest.GetRequest;
import lombok.Getter;
import lombok.NonNull;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public abstract class GetRequestSender<T> implements RequestSender<T>{
	@Getter
	private final GetRequest request;
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException    If the URL isn't valid.
	 * @throws MalformedURLException If the URL isn't valid.
	 */
	public GetRequestSender(@NonNull String url) throws URISyntaxException, MalformedURLException{
		this(new URL(url));
	}
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public GetRequestSender(@NonNull URL url) throws URISyntaxException{
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
	public GetRequestSender(@NonNull URL url, Map<String, String> headers) throws URISyntaxException{
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
	public GetRequestSender(@NonNull URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		request = URLHandler.getRequest(url, headers, params);
	}
}
