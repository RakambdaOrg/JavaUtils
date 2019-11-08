package fr.raksrinana.utils.http.requestssenders.post;

import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class ObjectPostRequestSender<T> extends PostRequestSender<T>{
	private final Class<? extends T> clazz;
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException    If the URL isn't valid.
	 * @throws MalformedURLException If the URL isn't valid.
	 */
	public ObjectPostRequestSender(@Nonnull Class<? extends T> clazz, @Nonnull String url) throws URISyntaxException, MalformedURLException{
		super(url);
		this.clazz = clazz;
	}
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public ObjectPostRequestSender(@Nonnull Class<? extends T> clazz, @Nonnull URL url) throws URISyntaxException{
		super(url, null);
		this.clazz = clazz;
	}
	
	/**
	 * Constructor.
	 *
	 * @param url     The URL.
	 * @param headers The headers.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public ObjectPostRequestSender(@Nonnull Class<? extends T> clazz, @Nonnull URL url, @Nullable Map<String, String> headers) throws URISyntaxException{
		super(url, headers, null);
		this.clazz = clazz;
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
	public ObjectPostRequestSender(@Nonnull Class<? extends T> clazz, @Nonnull URL url, @Nullable Map<String, String> headers, @Nullable Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
		this.clazz = clazz;
	}
	
	public ObjectPostRequestSender(@Nonnull Class<? extends T> clazz, @Nonnull URL url, @Nullable Map<String, String> headers, @Nullable Map<String, String> params, @Nonnull String body) throws URISyntaxException{
		super(url, headers, params, body);
		this.clazz = clazz;
	}
	
	@Override
	@Nonnull
	public HttpResponse<T> getRequestResult() throws UnirestException{
		return this.getRequest().asObject(this.clazz);
	}
}
