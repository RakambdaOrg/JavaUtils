package fr.mrcraftcod.utils.http.requestssenders.post;

import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import org.json.JSONObject;
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
public class JSONPostRequestSender extends PostRequestSender<JSONObject>{
	public JSONPostRequestSender(String url) throws URISyntaxException, MalformedURLException{
		super(url);
	}
	
	public JSONPostRequestSender(URL url) throws URISyntaxException{
		super(url, null);
	}
	
	public JSONPostRequestSender(URL url, Map<String, String> headers) throws URISyntaxException{
		super(url, headers, null);
	}
	
	public JSONPostRequestSender(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
	}
	
	public JSONPostRequestSender(URL url, Map<String, String> headers, Map<String, String> params, String body) throws URISyntaxException{
		super(url, headers, params, body);
	}
	
	@Override
	public HttpResponse<JSONObject> getRequestResult() throws UnirestException{
		return this.getRequest().asObject(raw -> new JSONObject(raw.getContentAsString()));
	}
}
