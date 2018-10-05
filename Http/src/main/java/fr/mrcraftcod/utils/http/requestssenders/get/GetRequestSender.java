package fr.mrcraftcod.utils.http.requestssenders.get;

import com.mashape.unirest.request.GetRequest;
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
	public GetRequestSender(String url) throws URISyntaxException, MalformedURLException{
		this(new URL(url));
	}
	
	/**
	 * Constructor.
	 *
	 * @param url The URL.
	 *
	 * @throws URISyntaxException If the URL isn't valid.
	 */
	public GetRequestSender(URL url) throws URISyntaxException{
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
	public GetRequestSender(URL url, Map<String, String> headers) throws URISyntaxException{
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
	public GetRequestSender(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		request = URLHandler.getRequest(url, headers, params);
	}
	
	/**
	 * Get the request.
	 *
	 * @return The request.
	 */
	public GetRequest getRequest(){
		return this.request;
	}
}
