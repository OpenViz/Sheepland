package gui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class used to represents the Lands in the GUI.
 * It has a List of Points that represents the area occupied by the Land in the GUI.
 */
public class LandGUI {
	private int landId;
	private int landColor;
	
	private List<Point> landPoints;
	private Point landCenter;
	
	/**
	 * Builder of LandGUI.
	 * The LandGUI is identified by an id that is related to its color.
	 * @param landId of the LandGUI  that relates it to the Lands of the Game.
	 * @param landColor of the LandGUI that is related to its id.
	 * @param landPoints of the LandGUI that represent the area
	 * of the GameBoardGUI that is occupied by the LandGUI.
	 */
	public LandGUI(int landId, int landColor, List<Point> landPoints) {
		this.landId = landId;
		this.landColor = landColor;
		this.landPoints = new ArrayList<Point>();
		this.landPoints = landPoints;
	}

	/**
	 * @return the landId of the LandGUI.
	 */
	public int getLandId() {
		return landId;
	}

	/**
	 * @param landId to set to the LandGUI.
	 */
	public void setLandId(int landId) {
		this.landId = landId;
	}

	/**
	 * @return the landColor of the LandGUI.
	 */
	public int getLandColor() {
		return landColor;
	}

	/**
	 * @param landColor to set to the LandGUI.
	 */
	public void setLandColor(int landColor) {
		this.landColor = landColor;
	}

	/**
	 * @return the landPoints List of the LandGUI.
	 */
	public List<Point> getLandPoints() {
		return landPoints;
	}

	/**
	 * @param landPoints List to set to the LandGUI.
	 */
	public void setLandPoints(List<Point> landPoints) {
		this.landPoints = landPoints;
	}

	/**
	 * @return the landCenter of the LandGUI.
	 */
	public Point getLandCenter() {
		return landCenter;
	}

	/**
	 * @param landCenter to set to the LandGUI.
	 */
	public void setLandCenter(Point landCenter) {
		this.landCenter = landCenter;
	}
	
}
