package fr.raksrinana.utils.http.requestssenders.get;

import fr.raksrinana.utils.http.requestssenders.RequestSender;
import kong.unirest.GetRequest;
import lombok.Getter;
import lombok.NonNull;

public abstract class GetRequestSender<T> implements RequestSender<T>{
	@Getter
	private final GetRequest request;
	
	/**
	 * Constructor.
	 *
	 * @param request The GET request.
	 */
	public GetRequestSender(@NonNull GetRequest request){
		this.request = request;
	}
}
