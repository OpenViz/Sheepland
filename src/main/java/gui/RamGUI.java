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
 * JLabel that represents the Rams in the GUI.
 * Inherits from OvineGUI that it's a Selectable and Movable Object.
 */
public class RamGUI extends OvineGUI{
	private static final long serialVersionUID = 1L;
	
	private ImageIcon ramImage;
	
	/**
	 * Builder of the RamGUI. 
	 * Takes its ovineId as parameter.
	 * @param ovineId for the RamGUI builded.
	 */
	public RamGUI(int ovineId) {
		super(ovineId);
		try {
			ramImage = new ImageIcon(ImageIO.read(new File("./res/Ram.png")));
		} catch (IOException e) {
			ServerLogger.printOnLogger("RamGUI", e);
		}

		this.setIcon(ramImage);
		this.setBounds(0, 0, 60, 60);
	}

}
