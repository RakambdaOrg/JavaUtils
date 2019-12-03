package fr.raksrinana.utils.base;

import lombok.NonNull;
import java.awt.image.BufferedImage;

public class ImageUtils{
	/**
	 * Resize an image to fit the given size while preserving its ratio.
	 *
	 * @param image  The image.
	 * @param width  The new width.
	 * @param height The new height.
	 *
	 * @return The resized image.
	 */
	@NonNull
	public static BufferedImage resizeBufferedImage(@NonNull BufferedImage image, float width, float height){
		final var baseWidth = image.getWidth();
		final var baseHeight = image.getHeight();
		final var ratio = baseWidth > baseHeight ? width / baseWidth : height / baseHeight;
		final var scaledImage = image.getScaledInstance((int) (ratio * baseWidth), (int) (ratio * baseHeight), BufferedImage.SCALE_SMOOTH);
		final var finalImage = new BufferedImage((int) (ratio * baseWidth), (int) (ratio * baseHeight), BufferedImage.TYPE_INT_ARGB);
		finalImage.getGraphics().drawImage(scaledImage, 0, 0, null);
		return finalImage;
	}
}
