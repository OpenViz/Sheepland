package gui;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import utility.ServerLogger;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * JLabel that represents the Wolf in the GUI.
 * It's a Movable Object.
 */
public class WolfGUI extends MovableObjectGUI {
	private static final long serialVersionUID = 1L;
	
	private ImageIcon wolfImage;
	
	/**
	 * Builder of the WolfGUI. 
	 */
	public WolfGUI() {
		try {
			wolfImage = new ImageIcon(ImageIO.read(new File("./res/Wolf.png")));
		} catch (IOException e) {
			ServerLogger.printOnLogger("WolfGUI", e);
		}

		this.setIcon(wolfImage);
		this.setBounds(0, 0, 60, 44);
	}

}
