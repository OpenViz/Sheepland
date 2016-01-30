package gui;

import java.awt.Point;
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
 * Class used to represents the Roads in the GUI.
 * It can be free, fenced or specialFenced, and change the image basing on this.
 */
public class RoadGUI extends JLabel {
	private static final long serialVersionUID = 1L;
	private static final int LABEL_WIDTH = 23;
	private static final int LABEL_HEIGHT = 23;
	
	private int roadId;
	private ImageIcon fenceImage;
	
	private boolean fenced = false;
	private boolean specialFenced = false;
	
	/**
	 * Builder of the RoadGUI.
	 * @param roadId of the RoadGUI that relates it to the Road of the Game.
	 * @param roadPosition Point of the RoadGUI in the GameBoardGUI.
	 */
	public RoadGUI(int roadId, Point roadPosition) {
		this.roadId = roadId;
		this.setBounds(roadPosition.x, roadPosition.y, LABEL_WIDTH, LABEL_HEIGHT);
	}

	/**
	 * 
	 */
	public void setFencedGUI() {
		this.setBounds(this.getX() - 8, this.getY() - 10, 30, 31);
		try {
			fenceImage = new ImageIcon(ImageIO.read(new File("./res/Fence.png")));
		} catch (IOException e) {
			ServerLogger.printOnLogger("RoadGUI", e);
		}
		this.setIcon(fenceImage);
		this.fenced = true;
	}
	
	/**
	 * 
	 */
	public void setSpecialFencedGUI() {
		this.setBounds(this.getX() - 8, this.getY() - 10, 30, 31);
		try {
			fenceImage = new ImageIcon(ImageIO.read(new File("./res/specialFence.png")));
		} catch (IOException e) {
			ServerLogger.printOnLogger("RoadGUI", e);
		}
		this.setIcon(fenceImage);
		this.specialFenced = true;
	}
	
	/**
	 * @return the roadId
	 */
	public int getRoadId() {
		return roadId;
	}

	/**
	 * @param roadId the roadId to set
	 */
	public void setRoadId(int roadId) {
		this.roadId = roadId;
	}

	/**
	 * @return the fenced
	 */
	public boolean isFenced() {
		return fenced;
	}

	/**
	 * @param fenced the fenced to set
	 */
	public void setFenced(boolean fenced) {
		this.fenced = fenced;
	}

	/**
	 * @return the specialFenced
	 */
	public boolean isSpecialFenced() {
		return specialFenced;
	}

	/**
	 * @param specialFenced the specialFenced to set
	 */
	public void setSpecialFenced(boolean specialFenced) {
		this.specialFenced = specialFenced;
	}
	
}
