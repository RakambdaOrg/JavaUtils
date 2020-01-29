package fr.raksrinana.utils.http.requestssenders.get;

import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.UnirestException;
import lombok.NonNull;

public class JSONGetRequestSender extends GetRequestSender<JsonNode>{
	/**
	 * Constructor.
	 *
	 * @param request The GET request.
	 */
	public JSONGetRequestSender(@NonNull GetRequest request){
		super(request);
	}
	
	@Override
	@NonNull
	public HttpResponse<JsonNode> getRequestResult() throws UnirestException{
		return this.getRequest().asJson();
	}
}
