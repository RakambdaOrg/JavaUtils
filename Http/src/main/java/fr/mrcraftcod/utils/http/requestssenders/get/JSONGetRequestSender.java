package fr.mrcraftcod.utils.http.requestssenders.get;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
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
public class JSONGetRequestSender extends GetRequestSender<JsonNode>
{
	public JSONGetRequestSender(String url) throws URISyntaxException, MalformedURLException
	{
		super(url);
	}
	
	public JSONGetRequestSender(URL url) throws URISyntaxException
	{
		super(url, null);
	}
	
	public JSONGetRequestSender(URL url, Map<String, String> headers) throws URISyntaxException
	{
		super(url, headers, null);
	}
	
	public JSONGetRequestSender(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException
	{
		super(url, headers, params);
	}

	@Override
	public HttpResponse<JsonNode> getRequestResult() throws UnirestException{
		return this.getRequest().asJson();
	}
}
