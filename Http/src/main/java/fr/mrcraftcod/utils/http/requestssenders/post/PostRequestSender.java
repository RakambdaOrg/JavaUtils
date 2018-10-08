package fr.mrcraftcod.utils.http.requestssenders.post;

import com.mashape.unirest.request.body.RequestBodyEntity;
import fr.mrcraftcod.utils.http.URLHandler;
import fr.mrcraftcod.utils.http.requestssenders.RequestSender;
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
@SuppressWarnings("WeakerAccess")
public abstract class PostRequestSender<T> implements RequestSender<T>{
	private final RequestBodyEntity request;
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException    If the URL isn't valid.
	 * @throws MalformedURLException If the URL isn't valid.
	 */
	public PostRequestSender(String url) throws URISyntaxException, MalformedURLException{
		this(new URL(url));
	}
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public PostRequestSender(URL url) throws URISyntaxException{
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
	public PostRequestSender(URL url, Map<String, String> headers) throws URISyntaxException{
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
	public PostRequestSender(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		request = URLHandler.postRequest(url, headers, params, null);
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
	public PostRequestSender(URL url, Map<String, String> headers, Map<String, String> params, String body) throws URISyntaxException{
		request = URLHandler.postRequest(url, headers, params, body);
	}
	
	/**
	 * Get the request.
	 *
	 * @return The request.
	 */
	public RequestBodyEntity getRequest(){
		return this.request;
	}
}
