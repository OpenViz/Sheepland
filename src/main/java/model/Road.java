package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 *  Class of the Roads of the GameBoard. It has a number from 1 to 6 on it.
 *  The Road can be FREE, SHEPHERD (occupied by a shepherd),
 *  FENCED or SPECIALFENCED; according to its RoadState.
 */
public class Road extends Identifier implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private RoadState roadState;
	private int number;
	private List<Land> neighboringLands;
	private List<Road> neighboringRoads;
	
	// Point that indicates where the Land is placed on the GUI.
	private Point roadPosition;
	
	/**
	 * Builder of Road. Set the id of the Road calling 
	 * the builder of its superclass Identifier.
	 * Its starting RoadState is set to FREE and its number is taken as parameter.
	 * @param number on the Road builded.
	 */
	public Road(int number) {
		super();
		this.roadState = RoadState.FREE;
		this.number = number;
		this.neighboringLands = new ArrayList<Land>();
		this.neighboringRoads = new ArrayList<Road>();
	}
	
	/**
	 * @return the number on the Road.
	 */
	public int getNumber() {
		return this.number;
	}
	
	/**
	 * Method used to know if the Road is occupied by something.
	 * @return true if the RoadState of the Road isn't FREE, else false.
	 */
	public boolean isBusy(){
		return !(this.roadState == RoadState.FREE);
	}
	
	/**
	 * Method used to know if the Road is occupied by a Shepherd.
	 * @return true if the RoadState of the Road is SHEPHERD, else false.
	 */
	public boolean isShepherd(){
		return this.roadState == RoadState.SHEPHERD;
	}
	
	/**
	 * 
	 * @return the RoadState of the Road.
	 */
	public RoadState getRoadState(){
		return this.roadState;
	}
	
	/**
	 * @param roadState to set to the Road. 
	 */
	public void setRoadState(RoadState roadState){
		this.roadState = roadState;
	}

	/**
	 * Method used to add a neighboring Road to the Road.
	 * Adds the neighboring Road inserted as parameter as the last element of the List
	 * of the neighboring Roads of the Road.
	 * @param road to add to the List of neighboring Roads.
	 */
	public void addNeighboringRoad(Road road){
		this.neighboringRoads.add(road);
	}
	
	/**
	 * Method used to add a neighboring Land to the Road.
	 * Adds the neighboring Land inserted as parameter as the last element of the List
	 * of the neighboring Land of the Road. 
	 * @param land to add to the List of neighboring Lands.
	 */
	public void addNeighboringLand(Land land){
		this.neighboringLands.add(land);
	}
	
	/**
	 * Method used to check if a specific Road is a neighboring Road to the Road.
	 * Takes a Road as parameter and check if that is in the List of neighboring Road
	 * of the Road.
	 * @param road to check if is in the List of neighboring Roads.
	 * @return true if the Road inserted as parameter is in the List of neighboring Road, else false.
	 */
	public boolean isNeighboringRoad(Road road) {
		for(Road r:this.neighboringRoads){
			if(r == road){
				return true;
			}					
		}
		return false;
	}
	
	/**
	 * Method used to check if a specific Land is a neighboring Land to the Road.
	 * Takes a Land as parameter and check if that is in the List of neighboring Land
	 * of the Road.
	 * @param land to check if is in the List of neighboring Lands.
	 * @return true if the Land inserted as parameter is in the List of neighboring Land, else false.
	 */
	public boolean isNeighboringLand(Land land) {
		for(Land l: this.neighboringLands){
			if(l == land){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return the List of neighboring Roads of the Road.
	 */
	public List<Road> getNeighboringRoads(){
		return neighboringRoads;
	}
	
	/**
	 * 
	 * @return the List of neighboring Lands of the Road.
	 */
	public List<Land> getNeighboringLands(){
		return neighboringLands;
	}
	
	/**
	 * @return the roadPosition Point of the Road.
	 */
	public Point getRoadPosition() {
		return roadPosition;
	}

	/**
	 * @param the roadPosition Point to set to the Road.
	 */
	public void setRoadPosition(Point roadPosition) {
		this.roadPosition = roadPosition;
	}

	/**
	 * Method used to set the position Point of the Road taking as parameter 2 coordinates x and y
	 * instead that a Point.
	 * Takes as parameter 2 int and generates a new Point using these 2 int as coordinates x and y
	 * of the Point and sets it as the Road position.
	 * @param xPositionOfRoad coordinate x of the Point to set as the position of the Road.
	 * @param yPositionOfRoad coordinate y of the Point to set as the position of the Road.
	 */
	public void setPointOfRoad(int xPositionOfRoad, int yPositionOfRoad) { 
		this.setRoadPosition(new Point(xPositionOfRoad, yPositionOfRoad));
	}
	
}

