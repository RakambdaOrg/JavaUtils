package fr.raksrinana.utils.http.requestssenders.post;

import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import lombok.NonNull;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class StringPostRequestSender extends PostRequestSender<String>{
	public StringPostRequestSender(@NonNull String url) throws URISyntaxException, MalformedURLException{
		super(url);
	}
	
	public StringPostRequestSender(@NonNull URL url) throws URISyntaxException{
		super(url);
	}
	
	public StringPostRequestSender(@NonNull URL url, Map<String, String> headers) throws URISyntaxException{
		super(url, headers);
	}
	
	public StringPostRequestSender(@NonNull URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
	}
	
	public StringPostRequestSender(@NonNull URL url, Map<String, String> headers, Map<String, String> params, @NonNull String body) throws URISyntaxException{
		super(url, headers, params, body);
	}
	
	@Override
	@NonNull
	public HttpResponse<String> getRequestResult() throws UnirestException{
		return this.getRequest().asString();
	}
}
