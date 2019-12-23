package fr.raksrinana.utils.http.requestssenders;

import fr.raksrinana.utils.http.RequestHandler;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import lombok.NonNull;

public interface RequestSender<T>{
	/**
	 * Get a request handler linked to this sender.
	 *
	 * @return A request handler.
	 *
	 * @throws UnirestException If the request couldn't be made.
	 */
	@NonNull
	default RequestHandler<T> getRequestHandler() throws UnirestException{
		return new RequestHandler<>(this);
	}
	
	/**
	 * Sends the request and get the result.
	 *
	 * @return The result.
	 *
	 * @throws UnirestException If the request couldn't be made.
	 */
	@NonNull HttpResponse<? extends T> getRequestResult() throws UnirestException;
}
