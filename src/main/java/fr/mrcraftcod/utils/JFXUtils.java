package fr.mrcraftcod.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class JFXUtils
{
	public WritableImage getImage(URL url, int width, int height)
	{
		try
		{
			return SwingFXUtils.toFXImage(ImageUtils.resizeBufferedImage(ImageIO.read(url), width, height), null);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
