package fr.mrcraftcod.utils.http.requestssenders;

import fr.mrcraftcod.utils.http.RequestHandler;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/12/2016.
 *
 * @author Thomas Couchoud
 * @since 2016-12-03
 */
public interface RequestSender<T>{
	/**
	 * Get a request handler linked to this sender.
	 *
	 * @return A request handler.
	 *
	 * @throws UnirestException If the request couldn't be made.
	 */
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
	HttpResponse<T> getRequestResult() throws UnirestException;
}
