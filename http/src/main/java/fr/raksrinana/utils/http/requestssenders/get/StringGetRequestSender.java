package fr.raksrinana.utils.http.requestssenders.get;

import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

public class StringGetRequestSender extends GetRequestSender<String>{
	public StringGetRequestSender(@Nonnull String url) throws URISyntaxException, MalformedURLException{
		super(url);
	}
	
	public StringGetRequestSender(@Nonnull URL url) throws URISyntaxException{
		super(url);
	}
	
	public StringGetRequestSender(@Nonnull URL url, @Nullable Map<String, String> headers) throws URISyntaxException{
		super(url, headers);
	}
	
	public StringGetRequestSender(@Nonnull URL url, @Nullable Map<String, String> headers, @Nullable Map<String, String> params) throws URISyntaxException{
		super(url, headers, params);
	}
	
	@Override
	@Nonnull
	public HttpResponse<String> getRequestResult() throws UnirestException{
		return this.getRequest().asString();
	}
}
