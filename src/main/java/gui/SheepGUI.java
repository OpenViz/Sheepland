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
 * JLabel that represents the Sheeps in the GUI.
 * Inherits from OvineGUI that it's a Selectable and Movable Object.
 */
public class SheepGUI extends OvineGUI {
	private static final long serialVersionUID = 1L;
	
	private ImageIcon sheepImage;
	
	/**
	 * Builder of the SheepGUI. 
	 * Takes its ovineId as parameter.
	 * @param ovineId for the SheepGUI builded.
	 */
	public SheepGUI(int ovineId) {
		super(ovineId);
		try {
			sheepImage = new ImageIcon(ImageIO.read(new File("./res/Sheep.png")));
		} catch (IOException e) {
			ServerLogger.printOnLogger("SheepGUI", e);
		}

		this.setIcon(sheepImage);
		this.setBounds(0, 0, 40, 40);
	}

}
