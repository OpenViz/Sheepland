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
 * JLabel that represents the BlackSheep in the GUI.
 * Inherits from OvineGUI that it's a Selectable and Movable Object.
 */
public class BlackSheepGUI extends OvineGUI {
	private static final long serialVersionUID = 1L;
	
	private ImageIcon blackSheepImage;
	
	/**
	 * Builder of the BlackSheepGUI. 
	 * Takes its ovineId as parameter.
	 * @param ovineId for the BlackSheepGUI builded.
	 */
	public BlackSheepGUI(int ovineId) {
		super(ovineId);
		try {
			blackSheepImage = new ImageIcon(ImageIO.read(new File("./res/BlackSheep.png")));
		} catch (IOException e) {
			ServerLogger.printOnLogger("BlackSheepGUI", e);
		}

		this.setIcon(blackSheepImage);
		this.setBounds(0, 0, 30, 30);
	}

}
