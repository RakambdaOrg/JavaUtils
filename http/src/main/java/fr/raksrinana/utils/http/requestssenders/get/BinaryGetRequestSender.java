package fr.raksrinana.utils.http.requestssenders.get;

import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.RawResponse;
import kong.unirest.UnirestException;
import lombok.NonNull;
import java.io.InputStream;

public class BinaryGetRequestSender extends GetRequestSender<InputStream>{
	/**
	 * Constructor.
	 *
	 * @param request The GET request.
	 */
	public BinaryGetRequestSender(@NonNull GetRequest request){
		super(request);
	}
	
	@Override
	@NonNull
	public HttpResponse<InputStream> getRequestResult() throws UnirestException{
		return this.getRequest().asObject(RawResponse::getContent);
	}
}
