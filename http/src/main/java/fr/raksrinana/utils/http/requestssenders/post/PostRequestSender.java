package fr.raksrinana.utils.http.requestssenders.post;

import fr.raksrinana.utils.http.URLHandler;
import fr.raksrinana.utils.http.requestssenders.RequestSender;
import kong.unirest.RequestBodyEntity;
import lombok.Getter;
import lombok.NonNull;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public abstract class PostRequestSender<T> implements RequestSender<T>{
	@Getter
	private final RequestBodyEntity request;
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException    If the URL isn't valid.
	 * @throws MalformedURLException If the URL isn't valid.
	 */
	public PostRequestSender(@NonNull String url) throws URISyntaxException, MalformedURLException{
		this(new URL(url));
	}
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public PostRequestSender(@NonNull URL url) throws URISyntaxException{
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
	public PostRequestSender(@NonNull URL url, Map<String, String> headers) throws URISyntaxException{
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
	public PostRequestSender(@NonNull URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		this(url, headers, params, "");
	}
	
	/**
	 * Constructor.
	 *
	 * @param url     The URL.
	 * @param headers The headers.
	 * @param params  The params.
	 * @param body    The body.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public PostRequestSender(@NonNull URL url, Map<String, String> headers, Map<String, String> params, @NonNull String body) throws URISyntaxException{
		request = URLHandler.postRequest(url, headers, params, body);
	}
}
