package gui;

import java.awt.image.BufferedImage;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class that takes an image as parameter of the builder and sets every color of their pixels
 * in a matrix indexed by the coordinates of the pixels.
 */
public class ColorTrackedImageGUI {

	//Matrix of the colors of the image's pixels indexed by pixels's coordinates.
	private int[][] coordinatesColor;
	private int width;
	private int height;
	
	/**
	 * Builder of the ColorTrackedImgeGUI.
	 * Sets its variables width and height as the width and height of the image inserted as parameter.
	 * @param image from which extract the colors of the pixels.
	 */
	public ColorTrackedImageGUI(BufferedImage image) {
		this.width = image.getWidth();
		this.height = image.getHeight();
		coordinatesColor = new int[width][height];
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				coordinatesColor[x][y] = image.getRGB(x, y);
			}
		}
	}

	/**
	 * @return the width of the ColorTrackedImageGUI.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height of the ColorTrackedImageGUI.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the coordinatesColor of the ColorTrackedImageGUI.
	 */
	public int[][] getCoordinatesColor() {
		return coordinatesColor;
	}

}
