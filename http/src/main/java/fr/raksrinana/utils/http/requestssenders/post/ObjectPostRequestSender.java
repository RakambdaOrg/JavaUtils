package fr.raksrinana.utils.http.requestssenders.post;

import kong.unirest.GenericType;
import kong.unirest.HttpResponse;
import kong.unirest.RequestBodyEntity;
import kong.unirest.UnirestException;
import lombok.NonNull;

public class ObjectPostRequestSender<T> extends PostRequestSender<T>{
	private final GenericType<? extends T> clazz;
	
	/**
	 * Constructor.
	 *
	 * @param request The POST request.
	 * @param clazz   The output class.
	 */
	public ObjectPostRequestSender(@NonNull GenericType<? extends T> clazz, @NonNull RequestBodyEntity request){
		super(request);
		this.clazz = clazz;
	}
	
	@Override
	@NonNull
	public HttpResponse<? extends T> getRequestResult() throws UnirestException{
		return this.getRequest().asObject(this.clazz);
	}
}
