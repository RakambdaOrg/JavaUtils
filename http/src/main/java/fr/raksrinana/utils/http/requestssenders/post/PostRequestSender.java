package fr.raksrinana.utils.http.requestssenders.post;

import fr.raksrinana.utils.http.requestssenders.RequestSender;
import kong.unirest.RequestBodyEntity;
import lombok.Getter;
import lombok.NonNull;

public abstract class PostRequestSender<T> implements RequestSender<T>{
	@Getter
	private final RequestBodyEntity request;
	
	/**
	 * Constructor.
	 *
	 * @param request The POST request.
	 */
	public PostRequestSender(@NonNull RequestBodyEntity request){
		this.request = request;
	}
}
