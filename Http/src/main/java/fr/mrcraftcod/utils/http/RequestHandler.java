package fr.mrcraftcod.utils.http;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import fr.mrcraftcod.utils.http.requestssenders.RequestSender;
import java.util.Optional;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/12/2016.
 *
 * @author Thomas Couchoud
 * @since 2016-12-03
 */
@SuppressWarnings({
		"WeakerAccess",
		"unused"
})
public class RequestHandler<T>{
	private final HttpResponse<T> result;
	
	/**
	 * Constructor.
	 *
	 * @param requestSender The sender to use.
	 *
	 * @throws UnirestException If the request couldn't be made.
	 */
	public RequestHandler(RequestSender<T> requestSender) throws UnirestException{
		this.result = requestSender.getRequestResult();
	}
	
	/**
	 * Get the length of the result.
	 *
	 * @return The length.
	 */
	public long getLength(){
		long length = 0;
		if(this.getHeaders().containsKey("content-length")){
			for(String value : this.getHeaders().get("content-length")){
				length += Long.parseLong(value);
			}
		}
		else if(this.getHeaders().containsKey("Content-Length")){
			for(String value : this.getHeaders().get("Content-Length")){
				length += Long.parseLong(value);
			}
		}
		return length;
	}
	
	/**
	 * Get the headers.
	 *
	 * @return The headers.
	 */
	public Headers getHeaders(){
		return this.getResult().getHeaders();
	}
	
	/**
	 * Get the result.
	 *
	 * @return The result.
	 */
	public HttpResponse<T> getResult(){
		return this.result;
	}
	
	/**
	 * Get the request result.
	 *
	 * @return The request result.
	 */
	public T getRequestResult(){
		return this.getResult().getBody();
	}
	
	/**
	 * Get the set-cookie header.
	 *
	 * @return An optional of the value of the header.
	 */
	public Optional<String> getSetCookies(){
		if(this.getHeaders().containsKey("Set-Cookie")){
			return Optional.of(this.getHeaders().get("Set-Cookie").get(0));
		}
		return Optional.empty();
	}
	
	/**
	 * Get the status.
	 *
	 * @return The status.
	 */
	public int getStatus(){
		return this.getResult().getStatus();
	}
	
	/**
	 * Get the status text.
	 *
	 * @return The status text.
	 */
	public String getStatusText(){
		return this.getResult().getStatusText();
	}
}
