package fr.raksrinana.utils.http.requestssenders.post;

import kong.unirest.HttpResponse;
import kong.unirest.RawResponse;
import kong.unirest.RequestBodyEntity;
import kong.unirest.UnirestException;
import lombok.NonNull;
import java.io.InputStream;

public class BinaryPostRequestSender extends PostRequestSender<InputStream>{
	/**
	 * Constructor.
	 *
	 * @param request The POST request.
	 */
	public BinaryPostRequestSender(@NonNull RequestBodyEntity request){
		super(request);
	}
	
	@Override
	@NonNull
	public HttpResponse<InputStream> getRequestResult() throws UnirestException{
		return this.getRequest().asObject(RawResponse::getContent);
	}
}
