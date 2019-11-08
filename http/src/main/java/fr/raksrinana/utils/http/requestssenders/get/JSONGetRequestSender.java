package fr.raksrinana.utils.http.requestssenders.get;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.UnirestException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class JSONGetRequestSender extends GetRequestSender<JsonNode>{
	public JSONGetRequestSender(@Nonnull String url) throws URISyntaxException, MalformedURLException{
		super(url);
	}
	
	public JSONGetRequestSender(@Nonnull URL url) throws URISyntaxException{
		super(url, null);
	}
	
	public JSONGetRequestSender(@Nonnull URL url, @Nullable Map<String, String> headers) throws URISyntaxException{
		super(url, headers, null);
	}
	
	public JSONGetRequestSender(@Nonnull URL url, @Nullable Map<String, String> headers, @Nullable Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
	}
	
	@Override
	@Nonnull
	public HttpResponse<JsonNode> getRequestResult() throws UnirestException{
		return this.getRequest().asJson();
	}
}
