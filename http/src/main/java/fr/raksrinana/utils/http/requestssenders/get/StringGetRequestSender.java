package fr.raksrinana.utils.http.requestssenders.get;

import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import lombok.NonNull;

public class StringGetRequestSender extends GetRequestSender<String>{
	/**
	 * Constructor.
	 *
	 * @param request The GET request.
	 */
	public StringGetRequestSender(@NonNull GetRequest request){
		super(request);
	}
	
	@Override
	@NonNull
	public HttpResponse<String> getRequestResult() throws UnirestException{
		return this.getRequest().asString();
	}
}
