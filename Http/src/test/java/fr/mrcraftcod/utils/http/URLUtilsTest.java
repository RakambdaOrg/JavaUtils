package fr.mrcraftcod.utils.http;

import junit.framework.TestCase;
import java.net.URL;
import java.util.List;

public class URLUtilsTest extends TestCase
{
	private static final String TEST_HTML_LINK = "https://gist.githubusercontent.com/MrCraftCod/862ffbdda1e90faa79e4/raw/470d6f4b7ca9a80b91dddf0bfb450da2dec71542/test.html";
	private static final String URL_1 = "http://www.example.com/test.png";
	private static final String URL_2 = "http://www.example.com/test.png?size=big";

	public void testPullLinks() throws Exception
	{
		List<String> links = URLUtils.pullLinks(new URL(TEST_HTML_LINK));
		assertEquals(links.size(), 2);
		assertTrue(links.get(0).equals(URL_1));
		assertTrue(links.get(1).equals(URL_2));
	}
}
