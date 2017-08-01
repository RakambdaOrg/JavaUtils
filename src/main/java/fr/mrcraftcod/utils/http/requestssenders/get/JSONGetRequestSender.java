package fr.mrcraftcod.utils.http.requestssenders.get;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/12/2016.
 *
 * @author Thomas Couchoud
 * @since 2016-12-03
 */
public class JSONGetRequestSender extends GetRequestSender<JsonNode>
{
	public JSONGetRequestSender(URL url) throws URISyntaxException
	{
		this(url, null);
	}
	
	public JSONGetRequestSender(URL url, Map<String, String> headers) throws URISyntaxException
	{
		this(url, headers, null);
	}
	
	public JSONGetRequestSender(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException
	{
		super(url, headers, params);
	}

	@Override
	public HttpResponse<JsonNode> getRequestResult() throws UnirestException
	{
		return this.getRequest().asJson();
	}
}
