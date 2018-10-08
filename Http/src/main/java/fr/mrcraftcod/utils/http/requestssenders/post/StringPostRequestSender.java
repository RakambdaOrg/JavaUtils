package fr.mrcraftcod.utils.http.requestssenders.post;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
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
public class StringPostRequestSender extends PostRequestSender<String>{
	public StringPostRequestSender(String url) throws URISyntaxException, MalformedURLException{
		super(url);
	}
	
	public StringPostRequestSender(URL url) throws URISyntaxException{
		super(url);
	}
	
	public StringPostRequestSender(URL url, Map<String, String> headers) throws URISyntaxException{
		super(url, headers);
	}
	
	public StringPostRequestSender(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
	}
	
	public StringPostRequestSender(URL url, Map<String, String> headers, Map<String, String> params, String body) throws URISyntaxException{
		super(url, headers, params, body);
	}
	
	@Override
	public HttpResponse<String> getRequestResult() throws UnirestException{
		return this.getRequest().asString();
	}
}
