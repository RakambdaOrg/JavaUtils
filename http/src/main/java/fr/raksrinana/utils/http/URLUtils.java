package fr.raksrinana.utils.http;

import fr.raksrinana.utils.http.requestssenders.get.BinaryGetRequestSender;
import fr.raksrinana.utils.http.requestssenders.get.StringGetRequestSender;
import kong.unirest.UnirestException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class URLUtils{
	/**
	 * Pull all the links from an URL.
	 *
	 * @param url The URL to pull from.
	 *
	 * @return A list of string that are URLs.
	 *
	 * @throws Exception If something bad happened.
	 */
	@NonNull
	public static List<String> pullLinks(@NonNull URL url) throws Exception{
		return pullLinks(Jsoup.parse(new StringGetRequestSender(url).getRequestHandler().getRequestResult()).html());
	}
	
	/**
	 * Pull all the links from a text.
	 *
	 * @param text The text to scan.
	 *
	 * @return A list of string that are URLs.
	 */
	@NonNull
	public static List<String> pullLinks(@NonNull String text){
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
	public static boolean saveAsFile(@NonNull URL url, Path file){
		file.getParent().toFile().mkdirs();
		try(InputStream inputStream = new BinaryGetRequestSender(url).getRequestHandler().getRequestResult(); OutputStream outputStream = Files.newOutputStream(file, StandardOpenOption.WRITE)){
			int i;
			while((i = inputStream.read()) != -1){
				outputStream.write(i);
			}
		}
		catch(IOException | UnirestException | URISyntaxException e){
			log.warn("Couldn't download file {} to {}", url.toString(), file.toString(), e);
			return false;
		}
		return true;
	}
	
	/**
	 * The the final URL after all redirection.
	 *
	 * @param url The starting URL.
	 *
	 * @return The final URL.
	 *
	 * @throws IOException If the final URL couldn't be determined.
	 */
	@NonNull
	public static URL getFinalURL(@NonNull URL url) throws IOException{
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
