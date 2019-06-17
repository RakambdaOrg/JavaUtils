package fr.mrcraftcod.utils.http.requestssenders.post;

import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/12/2016.
 *
 * @author Thomas Couchoud
 * @since 2016-12-03
 */
@SuppressWarnings("unused")
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
	public ObjectPostRequestSender(Class<? extends T> clazz, String url) throws URISyntaxException, MalformedURLException{
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
	public ObjectPostRequestSender(Class<? extends T> clazz, URL url) throws URISyntaxException{
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
	public ObjectPostRequestSender(Class<? extends T> clazz, URL url, Map<String, String> headers) throws URISyntaxException{
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
	public ObjectPostRequestSender(Class<? extends T> clazz, URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
		this.clazz = clazz;
	}
	
	public ObjectPostRequestSender(Class<? extends T> clazz, URL url, Map<String, String> headers, Map<String, String> params, String body) throws URISyntaxException{
		super(url, headers, params, body);
		this.clazz = clazz;
	}
	
	@Override
	public HttpResponse<T> getRequestResult() throws UnirestException{
		return this.getRequest().asObject(this.clazz);
	}
}
