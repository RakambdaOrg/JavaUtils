package fr.mrcraftcod.utils.http;

import fr.mrcraftcod.utils.base.FileUtils;
import fr.mrcraftcod.utils.http.requestssenders.get.BinaryGetRequestSender;
import fr.mrcraftcod.utils.http.requestssenders.get.StringGetRequestSender;
import kong.unirest.UnirestException;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({
		"unused",
		"WeakerAccess"
})
public class URLUtils{
	private static final Logger LOGGER = LoggerFactory.getLogger(URLUtils.class);
	
	/**
	 * Pull all the links from an URL.
	 *
	 * @param url The URL to pull from.
	 *
	 * @return A list of string that are URLs.
	 *
	 * @throws Exception If something bad happened.
	 */
	public static List<String> pullLinks(URL url) throws Exception{
		return pullLinks(Jsoup.parse(new StringGetRequestSender(url).getRequestHandler().getRequestResult()).html());
	}
	
	/**
	 * Pull all the links from a text.
	 *
	 * @param text The text to scan.
	 *
	 * @return A list of string that are URLs.
	 */
	public static List<String> pullLinks(String text){
		LinkedList<String> links = new LinkedList<>();
		Matcher matcher = Pattern.compile("\\(?\\b(http://|https://|www[.])[-A-Za-z0-9+&@#/\\\\%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]").matcher(text);
		while(matcher.find()){
			String urlString = matcher.group();
			if(urlString.startsWith("(") && urlString.endsWith(")")){
				urlString = urlString.substring(1, urlString.length() - 1);
			}
			links.add(StringEscapeUtils.unescapeHtml4(urlString));
		}
		HashSet<String> hs = new HashSet<>(links);
		links.clear();
		links.addAll(hs);
		return links;
	}
	
	/**
	 * Save a URL as a file.
	 *
	 * @param url  The URL to get.
	 * @param file The file to save to.
	 *
	 * @return True if it succeeded, false otherwise.
	 */
	public static boolean saveAsFile(URL url, File file){
		FileUtils.createDirectories(file);
		try(InputStream is = new BinaryGetRequestSender(url).getRequestHandler().getRequestResult(); FileOutputStream fos = new FileOutputStream(file)){
			int i;
			while((i = is.read()) != -1){
				fos.write(i);
			}
		}
		catch(IOException | UnirestException | URISyntaxException e){
			LOGGER.warn("Couldn't download file {} to {}", url.toString(), file.toString(), e);
			return false;
		}
		return true;
	}
	
	/**
	 * The the final URL after all redirections.
	 *
	 * @param url The starting URL.
	 *
	 * @return The final URL.
	 *
	 * @throws IOException If the final URL couldn't be determined.
	 */
	public static URL getFinalURL(URL url) throws IOException{
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setInstanceFollowRedirects(false);
		HttpURLConnection.setFollowRedirects(false);
		
		if(conn.getResponseCode() == 301 || conn.getResponseCode() == 302){
			URL nextURL = new URL(conn.getHeaderField("Location"));
			conn.disconnect();
			return getFinalURL(nextURL);
		}
		return url;
	}
}
