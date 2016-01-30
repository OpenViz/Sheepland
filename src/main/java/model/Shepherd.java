package model;

import java.awt.Point;
import java.io.Serializable;


/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class for the Shepherds. It extends the class ShepherdIdentifier that assigns an unique id
 * to every Shepherd instantiated.
 * The Shepherd has a Point position that is used to set his position in the GUI and a
 * placed boolean that indicates if the Shepherd is on the GUI or not. 
 */
public class Shepherd extends ShepherdIdentifier implements Serializable  {
	private static final long serialVersionUID = 1L;
	private Road road;
	private Color color;
	
	private Point shepherdPosition;
	private boolean placed;
	
	/**
	 * Builder of the Shepherd. 
	 * @param color that relates the Shepherd to a Player.
	 */
	public Shepherd(Color color){
		super();
		this.color = color;
		this.road = null;
		this.placed = false;
		this.shepherdPosition = new Point(0, 0);
	}
	
	/** 
	 * @return the Road of the Shepherd.
	 */
	public Road getRoad(){
		return  this.road;
	}
	
	/**
	 * @param road to set to the Shepherd.
	 */
	public void setRoad(Road road){
		this.road = road;
		
	}

	/**
	 * @return the color of the Shepherd.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color to set to the Shepherd.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the shepherdPosition Point of the Shepherd.
	 */
	public Point getShepherdPosition() {
		return shepherdPosition;
	}

	/**
	 * @param shepherdPosition to set to the Shepherd.
	 */
	public void setShepherdPosition(Point shepherdPosition) {
		this.shepherdPosition = new Point(shepherdPosition.x - 9, shepherdPosition.y - 9);
	}

	/**
	 * @return true if the Shepherd is placed, else false.
	 */
	public boolean isPlaced() {
		return placed;
	}

	/**
	 * @param placed to set as true or false to the Shepherd.
	 */
	public void setPlaced(boolean placed) {
		this.placed = placed;
	}

}
