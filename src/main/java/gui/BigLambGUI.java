/**
 * 
 */
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
 * JLabel that represents the Lambs with 1 day life in the GUI.
 * Inherits from OvineGUI that it's a Selectable and Movable Object.
 */
public class BigLambGUI extends OvineGUI {
	private static final long serialVersionUID = 1L;
	
	private static final int DAYLIFE = 1;
	
	private ImageIcon bigLambImage;
	
	/**
	 * Builder of the BigLambGUI. 
	 * Takes its ovineId as parameter.
	 * @param ovineId for the BigLambGUI builded.
	 */
	public BigLambGUI(int ovineId) {
		super(ovineId);
		
		try {
			bigLambImage = new ImageIcon(ImageIO.read(new File("./res/BigLamb.png")));
		} catch (IOException e) {
			ServerLogger.printOnLogger("BigLambGUI", e);
		}
		this.setIcon(bigLambImage);
		this.setBounds(0, 0, 38, 40);
	}

	/**
	 * @return dayLife of the BigLambGUI.
	 */
	public static int getDaylife() {
		return DAYLIFE;
	}
}
