package fr.raksrinana.utils.http.requestssenders.post;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.RequestBodyEntity;
import kong.unirest.UnirestException;
import lombok.NonNull;

public class JSONPostRequestSender extends PostRequestSender<JsonNode>{
	/**
	 * Constructor.
	 *
	 * @param request The POST request.
	 */
	public JSONPostRequestSender(@NonNull RequestBodyEntity request){
		super(request);
	}
	
	@Override
	@NonNull
	public HttpResponse<JsonNode> getRequestResult() throws UnirestException{
		return this.getRequest().asJson();
	}
}
