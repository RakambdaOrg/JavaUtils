package fr.raksrinana.utils.http.requestssenders.get;

import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import lombok.NonNull;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class StringGetRequestSender extends GetRequestSender<String>{
	public StringGetRequestSender(@NonNull String url) throws URISyntaxException, MalformedURLException{
		super(url);
	}
	
	public StringGetRequestSender(@NonNull URL url) throws URISyntaxException{
		super(url);
	}
	
	public StringGetRequestSender(@NonNull URL url, Map<String, String> headers) throws URISyntaxException{
		super(url, headers);
	}
	
	public StringGetRequestSender(@NonNull URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
	}
	
	@Override
	@NonNull
	public HttpResponse<String> getRequestResult() throws UnirestException{
		return this.getRequest().asString();
	}
}
