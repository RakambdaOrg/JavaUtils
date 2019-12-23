package fr.raksrinana.utils.http;

import fr.raksrinana.utils.http.requestssenders.RequestSender;
import kong.unirest.Headers;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import lombok.Getter;
import lombok.NonNull;
import java.util.Optional;

public class RequestHandler<T>{
	@Getter
	private final HttpResponse<T> result;
	
	/**
	 * Constructor.
	 *
	 * @param requestSender The sender to use.
	 *
	 * @throws UnirestException If the request couldn't be made.
	 */
	public RequestHandler(@NonNull RequestSender<T> requestSender) throws UnirestException{
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
	@NonNull
	public Optional<String> getSetCookies(){
		if(this.getHeaders().containsKey("Set-Cookie")){
			return Optional.of(this.getHeaders().get("Set-Cookie").get(0));
		}
		return Optional.empty();
	}
	
	/**
	 * Get the headers.
	 *
	 * @return The headers.
	 */
	@NonNull
	public Headers getHeaders(){
		return this.getResult().getHeaders();
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
	@NonNull
	public String getStatusText(){
		return this.getResult().getStatusText();
	}
}
