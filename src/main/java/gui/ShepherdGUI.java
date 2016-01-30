package gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import utility.ServerLogger;
import model.Color;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * JLabel that represents the Shepherds in the GUI.
 * It's a Selectable and Movable Object.
 */
public class ShepherdGUI extends SelectableObjectGUI {
	private static final long serialVersionUID = 1L;

	private ImageIcon shepherdImage;

	private int shepherdId;
	private Color color;

	/**
	 * Builder of the ShepherdGUI. 
	 * Takes its shepherdId and color to identify its owner Player as parameters.
	 * @param shepherdId for the ShepherdGUI builded.
	 * @param color of the owner Player.
	 */
	public ShepherdGUI(int shepherdId, Color color) {
		this.color = color;
		this.shepherdId = shepherdId;
		
		this.loadResources();
		this.setBounds(250, -1, 30, 30);
	}

	/**
	 * @return shepherdId of the ShepherdGUI.
	 */
	public int getShepherdId() {
		return shepherdId;
	}

	/**
	 * @param shepherdId to set to the ShepherdGUI.
	 */
	public void setShepherdId(int shepherdId) {
		this.shepherdId = shepherdId;
	}

	/**
	 * Method helper to the ShepherdGUI builder.
	 * Loads a different image for the JLabel based on 
	 * the color passed as a parameter to the builder.
	 */
	private void loadResources() {
		switch(color) {
			case BLUE:
				try {
					shepherdImage = new ImageIcon(ImageIO.read(new File("./res/StarkResized.png")));
				} catch (IOException e) {
					ServerLogger.printOnLogger("ShepherdGUI", e);
				}
				break;
			case RED:
				try {
					shepherdImage = new ImageIcon(ImageIO.read(new File("./res/LannisterResized.png")));
				} catch (IOException e) {
					ServerLogger.printOnLogger("ShepherdGUI", e);
				}
				break;
			case GREEN:
				try {
					shepherdImage = new ImageIcon(ImageIO.read(new File("./res/TargaryenResized.png")));
				} catch (IOException e) {
					ServerLogger.printOnLogger("ShepherdGUI", e);
				}
				break;
			case YELLOW:
				try {
					shepherdImage = new ImageIcon(ImageIO.read(new File("./res/BaratheonResized.png")));
				} catch (IOException e) {
					ServerLogger.printOnLogger("ShepherdGUI", e);
				}
				break;
			default: 
				break;
		}
		this.setIcon(shepherdImage);
	}
}
