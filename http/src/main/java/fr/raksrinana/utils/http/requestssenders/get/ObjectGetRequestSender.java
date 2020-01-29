package fr.raksrinana.utils.http.requestssenders.get;

import kong.unirest.GenericType;
import kong.unirest.GetRequest;
import kong.unirest.HttpResponse;
import kong.unirest.UnirestException;
import lombok.NonNull;

public class ObjectGetRequestSender<T> extends GetRequestSender<T>{
	private final GenericType<? extends T> clazz;
	
	/**
	 * Constructor.
	 *
	 * @param clazz   The output class.
	 * @param request The GET request.
	 */
	public ObjectGetRequestSender(@NonNull GenericType<? extends T> clazz, @NonNull GetRequest request){
		super(request);
		this.clazz = clazz;
	}
	
	@Override
	@NonNull
	public HttpResponse<? extends T> getRequestResult() throws UnirestException{
		return this.getRequest().asObject(this.clazz);
	}
}
