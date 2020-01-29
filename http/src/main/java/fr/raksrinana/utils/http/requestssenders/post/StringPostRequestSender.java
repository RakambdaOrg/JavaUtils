package fr.raksrinana.utils.http.requestssenders.post;

import kong.unirest.HttpResponse;
import kong.unirest.RequestBodyEntity;
import kong.unirest.UnirestException;
import lombok.NonNull;

public class StringPostRequestSender extends PostRequestSender<String>{
	/**
	 * Constructor.
	 *
	 * @param request The POST request.
	 */
	public StringPostRequestSender(@NonNull RequestBodyEntity request){
		super(request);
	}
	
	@Override
	@NonNull
	public HttpResponse<String> getRequestResult() throws UnirestException{
		return this.getRequest().asString();
	}
}
