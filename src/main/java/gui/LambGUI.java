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
 * JLabel that represents the Lambs with 0 day life in the GUI.
 * Inherits from OvineGUI that it's a Selectable and Movable Object. 
 */
public class LambGUI extends OvineGUI{
	private static final long serialVersionUID = 1L;
	
	private static final int DAYLIFE = 0;
	
	private ImageIcon lambImage;
	
	/**
	 * Builder of the LambGUI. 
	 * Takes its ovineId as parameter.
	 * @param ovineId for the LambGUI builded.
	 */
	public LambGUI(int ovineId) {
		super(ovineId);
		try {
			lambImage = new ImageIcon(ImageIO.read(new File("./res/Lamb.png")));
		} catch (IOException e) {
			ServerLogger.printOnLogger("LambGUI", e);
		}

		setIcon(lambImage);
		this.setBounds(0, 0, 35, 37);
	}

	/**
	 * @return dayLife of the LambGUI.
	 */
	public static int getDaylife() {
		return DAYLIFE;
	}
	
}


