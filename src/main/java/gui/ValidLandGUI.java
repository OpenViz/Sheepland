package gui;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utility.ServerLogger;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class that represents the Lands of the GameBoard that actually have a valid id.
 * Their appears over the GameBoardGUI.
 */
public class ValidLandGUI extends JLabel {
	private static final long serialVersionUID = 1L;

	private Image validLandGUIImage;
	private int validLandGUIId;
	
	private boolean visible;
	
	/**
	 * Builder of the ValidLandGUI.
	 * @param landId of the Land related to the ValidLandGUI.
	 */
	public ValidLandGUI(int landId) {
		this.validLandGUIId = landId;
		try {
			this.loadResources();
		} catch (IOException e) {
			ServerLogger.printOnLogger("ValidLandGUI", e);
		}
		this.setIcon(new ImageIcon(validLandGUIImage));
	}
	
	/**
	 * @return the validLandGUIId.
	 */
	public int getValidLandGUIId() {
		return validLandGUIId;
	}
	
	/**
	 * @return true if the ValidLandGUI is visible, else false.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible to set as true or false to the ValidLandGUI.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * Loads an image of a Land basing on the id of the ValidLandGUI.
	 * @throws IOException if the image isn't found.
	 */
	private void loadResources() throws IOException {
		switch (validLandGUIId) {
			case 0:
				validLandGUIImage = ImageIO.read(new File("./res/Land0.png"));
				break;
			case 1:
				validLandGUIImage = ImageIO.read(new File("./res/Land1.png"));
				break;
			case 2:
				validLandGUIImage = ImageIO.read(new File("./res/Land2.png"));
				break;
			case 3:
				validLandGUIImage = ImageIO.read(new File("./res/Land3.png"));
				break;
			case 4:
				validLandGUIImage = ImageIO.read(new File("./res/Land4.png"));
				break;
			case 5:
				validLandGUIImage = ImageIO.read(new File("./res/Land5.png"));
				break;
			case 6:
				validLandGUIImage = ImageIO.read(new File("./res/Land6.png"));
				break;
			case 7:
				validLandGUIImage = ImageIO.read(new File("./res/Land7.png"));
				break;
			case 8:
				validLandGUIImage = ImageIO.read(new File("./res/Land8.png"));
				break;
			case 9:
				validLandGUIImage = ImageIO.read(new File("./res/Land9.png"));
				break;
			case 10:
				validLandGUIImage = ImageIO.read(new File("./res/Land10.png"));
				break;
			case 11:
				validLandGUIImage = ImageIO.read(new File("./res/Land11.png"));
				break;
			case 12:
				validLandGUIImage = ImageIO.read(new File("./res/Land12.png"));
				break;
			case 13:
				validLandGUIImage = ImageIO.read(new File("./res/Land13.png"));
				break;
			case 14:
				validLandGUIImage = ImageIO.read(new File("./res/Land14.png"));
				break;
			case 15:
				validLandGUIImage = ImageIO.read(new File("./res/Land15.png"));
				break;
			case 16:
				validLandGUIImage = ImageIO.read(new File("./res/Land16.png"));
				break;
			case 17:
				validLandGUIImage = ImageIO.read(new File("./res/Land17.png"));
				break;
			case 18:
				validLandGUIImage = ImageIO.read(new File("./res/Land18.png"));
				break;
			default:
				break;
		}
	}
	
}
