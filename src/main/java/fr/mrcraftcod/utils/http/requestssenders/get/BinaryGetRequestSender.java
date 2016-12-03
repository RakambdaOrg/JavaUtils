package fr.mrcraftcod.utils.http.requestssenders.get;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/12/2016.
 *
 * @author Thomas Couchoud
 * @since 2016-12-03
 */
public class BinaryGetRequestSender extends GetRequestSender<InputStream>
{
	public BinaryGetRequestSender(URL url) throws URISyntaxException, UnirestException
	{
		this(url, null);
	}

	public BinaryGetRequestSender(URL url, Map<String, String> headers) throws URISyntaxException, UnirestException
	{
		this(url, headers, null);
	}

	public BinaryGetRequestSender(URL url, Map<String, String> headers, Map<String, String> params) throws URISyntaxException, UnirestException
	{
		super(url, headers, params);
	}

	@Override
	public HttpResponse<InputStream> getRequestResult() throws UnirestException
	{
		return this.getRequest().asBinary();
	}
}
