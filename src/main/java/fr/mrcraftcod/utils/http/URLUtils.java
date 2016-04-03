package fr.mrcraftcod.utils.http;

import org.apache.commons.lang3.StringEscapeUtils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class URLUtils
{
	public static List<URL> convertStringToURL(List<String> strings)
	{
		LinkedList<URL> urls = new LinkedList<>();
		for(String urlString : strings)
			try
			{
				urls.add(new URL(urlString));
			}
			catch(MalformedURLException e)
			{
				e.printStackTrace();
			}
		return urls;
	}

	public static List<String> pullLinks(URL url) throws Exception
	{
		return pullLinks(URLHandler.getJsoup(url).body().html());
	}

	public static List<String> pullLinks(String text)
	{
		LinkedList<String> links = new LinkedList<>();
		Matcher matcher = Pattern.compile("\\(?\\b(http://|https://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]").matcher(text);
		while(matcher.find())
		{
			String urlString = matcher.group();
			if(urlString.startsWith("(") && urlString.endsWith(")"))
				urlString = urlString.substring(1, urlString.length() - 1);
			links.add(StringEscapeUtils.unescapeHtml4(urlString));
		}
		HashSet<String> hs = new HashSet<>();
		hs.addAll(links);
		links.clear();
		links.addAll(hs);
		return links;
	}
}
