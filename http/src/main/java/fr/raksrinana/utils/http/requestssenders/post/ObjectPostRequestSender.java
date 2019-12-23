package fr.raksrinana.utils.http.requestssenders.post;

import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import lombok.NonNull;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class ObjectPostRequestSender<T> extends PostRequestSender<T>{
	private final GenericType<? extends T> clazz;
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException    If the URL isn't valid.
	 * @throws MalformedURLException If the URL isn't valid.
	 */
	public ObjectPostRequestSender(@NonNull GenericType<? extends T> clazz, @NonNull String url) throws URISyntaxException, MalformedURLException{
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
	public ObjectPostRequestSender(@NonNull GenericType<? extends T> clazz, @NonNull URL url) throws URISyntaxException{
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
	public ObjectPostRequestSender(@NonNull GenericType<? extends T> clazz, @NonNull URL url, Map<String, String> headers) throws URISyntaxException{
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
	public ObjectPostRequestSender(@NonNull GenericType<? extends T> clazz, @NonNull URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
		this.clazz = clazz;
	}
	
	public ObjectPostRequestSender(@NonNull GenericType<? extends T> clazz, @NonNull URL url, Map<String, String> headers, Map<String, String> params, @NonNull String body) throws URISyntaxException{
		super(url, headers, params, body);
		this.clazz = clazz;
	}
	
	@Override
	@NonNull
	public HttpResponse<? extends T> getRequestResult() throws UnirestException{
		return this.getRequest().asObject(this.clazz);
	}
}
