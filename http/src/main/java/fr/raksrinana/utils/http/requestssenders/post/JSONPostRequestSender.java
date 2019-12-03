package fr.raksrinana.utils.http.requestssenders.post;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.UnirestException;
import lombok.NonNull;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class JSONPostRequestSender extends PostRequestSender<JsonNode>{
	public JSONPostRequestSender(@NonNull String url) throws URISyntaxException, MalformedURLException{
		super(url);
	}
	
	public JSONPostRequestSender(@NonNull URL url) throws URISyntaxException{
		super(url, null);
	}
	
	public JSONPostRequestSender(@NonNull URL url, Map<String, String> headers) throws URISyntaxException{
		super(url, headers, null);
	}
	
	public JSONPostRequestSender(@NonNull URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
	}
	
	public JSONPostRequestSender(@NonNull URL url, Map<String, String> headers, Map<String, String> params, @NonNull String body) throws URISyntaxException{
		super(url, headers, params, body);
	}
	
	@Override
	@NonNull
	public HttpResponse<JsonNode> getRequestResult() throws UnirestException{
		return this.getRequest().asJson();
	}
}
