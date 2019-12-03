package fr.raksrinana.utils.http.requestssenders.get;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.UnirestException;
import lombok.NonNull;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class JSONGetRequestSender extends GetRequestSender<JsonNode>{
	public JSONGetRequestSender(@NonNull String url) throws URISyntaxException, MalformedURLException{
		super(url);
	}
	
	public JSONGetRequestSender(@NonNull URL url) throws URISyntaxException{
		super(url, null);
	}
	
	public JSONGetRequestSender(@NonNull URL url, Map<String, String> headers) throws URISyntaxException{
		super(url, headers, null);
	}
	
	public JSONGetRequestSender(@NonNull URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
	}
	
	@Override
	@NonNull
	public HttpResponse<JsonNode> getRequestResult() throws UnirestException{
		return this.getRequest().asJson();
	}
}
