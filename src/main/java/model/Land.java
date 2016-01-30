package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Class of the Lands of the GameBoard. It may contains Ovines and a Wolf.
 */
public class Land  extends Identifier implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private LandType landType;
	private List<Road> neighboringRoads;
	private List<Ovine> ovines;
	private Wolf wolf;
	
	// Color of the Land in the SheepLandColoredMapResized.jpg to identify the area of the Land in the GameBoardGUI.
	private int landColorGUI;
	// List of the Points that covers the Land area in the GameBoardGUI.
	private List<Point> landPoints;
	// The baricenter Point of the Land area of the Land in the GameBoardGUI.
	private Point baricenter;
	
	/**
	 * Builder of the Land. Sets the id of the Land calling 
	 * the builder of its superclass Identifier. 
	 * @param landType of the Land builded.
	 */
	public Land(LandType landType){
		super();
		this.landType = landType;
		this.wolf = null;
		this.neighboringRoads = new ArrayList<Road>();
		this.ovines= new ArrayList<Ovine>();
		this.landPoints = new ArrayList<Point>();
		this.landColorGUI = 0;
	}
	
	/**
	 * Method used to add a neighboring Road to the Land.
	 * Adds the neighboring Road inserted as parameter as the last element of the List
	 * of the neighboring Roads of the Land.
	 * @param road to add to the List of neighboring Roads.
	 */
	public void addNeighboringRoad(Road road) {
		this.neighboringRoads.add(road);
	}

	/**
	 * Method used to add an Ovine in the Land.
	 * Adds the Ovine inserted as parameter as the last element of the List
	 * of the Ovines in the Land.
	 * @param Road to add to the List of neighboring Road.
	 * @param Ovine to add to the List of Ovines that are in the Land.
	 */
	public void addOvine(Ovine ovine){
		this.ovines.add(ovine);
	}
	
	/**
	 * Method used to remove an Ovine in the Land.
	 * Removes the Ovine with the ovineId inserted as parameter to the list
	 * of the Ovines in the Land.
	 * @param ovineId of the Ovine to remove from the List of Ovines.
	 * @return the Ovine removed from the List.
	 */
	public Ovine removeOvineOnId(int ovineId){
		for(Ovine ovine:this.ovines){
			if(ovine.getId() == ovineId) {
				this.ovines.remove(ovine);
				return ovine;
			}
		}
		return null;
	}
	
	/**
	 * Method used to know the number of the ovines that are in the Land.
	 * The BlackSheep is counted as 2 Ovines.
	 * @return the number of Ovines in the Land, 
	 * if there is the BlackSheep it returns the number of Ovines increased by one.
	 */
	public int numberOfOvines(){
		if(this.contains(Ovinetype.BLACKSHEEP.toString())){
			return this.ovines.size()+1;
		}
		return this.ovines.size();
	}
	
	/**
	 * Method used to search a neighboring Road of the Land basing on the number.
	 * It search in the List of neighboring Roads of the Land the Road
	 * that has the number equal to the number inserted as parameter.
	 * @param number of the neighboring Road searched.
	 * @return the neighboring Road of the Land that has the number inserted as parameter,
	 * if it exists, else returns null.
	 */
	public Road searchRoadForMoveAnimal(int number){
		for(Road e: this.neighboringRoads){
			if(number==e.getNumber()){
				return e;
			}
		}
		return null;
	}
	
	//
	/**
	 * Method used to grow all Growable Ovines that are in the Land.
	 * It extracts all the Growable Ovines that are in the List of the Ovines of the
	 * Land and invokes from them their method grow() that returns a grown ovine. 
	 * Removes the old Ovines that are grow and adds to the List all the grown Ovines.
	 */
	public void growAll(){
		List<Ovine> temporanyRemoveList = new ArrayList<Ovine>();
		List<Ovine> temporanyAddList = new ArrayList<Ovine>();
		for(Ovine o: this.ovines ){
			if(o instanceof Growable){
				Ovine growedOvine = (Ovine) ((Growable) o).grow();
				if(growedOvine != null) {
					temporanyAddList.add(growedOvine);
					temporanyRemoveList.add(o);
				}	
			}
		}
		if(!temporanyRemoveList.isEmpty()){
			for(Ovine ovine : temporanyRemoveList){
				this.removeOvineOnId(ovine.getId());
			}
		}
		if(!temporanyAddList.isEmpty()){
			for(Ovine ovine : temporanyAddList){
				this.ovines.add(ovine);
			}
		}
	}
	
	/**
	 * Method used to check if all the neighboring Roads of the Lands are busy.
	 * @return true if all the Roads in the List of the neighboring Roads of the
	 * Land are busy, else returns false.
	 */
	public boolean isAllBusy(){
		boolean temp = true;
		for(Road r: this.neighboringRoads){
			temp = temp && r.isBusy();
		}
		return temp;
	}
	
	/**
	 * Method used to check if the Land contains a specific Type of Ovine.
	 * It takes as parameter an OvineType and check if in the List of the Ovine of the
	 * Land there is almost one Ovine of that OvineType.
	 * @param ovineType to check if it appears in the Land.
	 * @return true if there's an Ovine of the specified OvineType in the Land, else returns false.
	 */
	public boolean contains(String ovineType){
		for(Ovine ovine : this.ovines){
			if(ovine.toString().equalsIgnoreCase(ovineType)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Method used to check if the Land contains almost one Ovine.
	 * @return true if the List of the Ovines of the Land isn't empty, else returns false.
	 */
	public boolean containsAnOvine() {
		return !(ovines.isEmpty());
	}
	
	/**
	 * Method used to check if the Land contains almost two Sheeps.
	 * @return true if the List of the Ovines of the Land contains almost
	 * two Ovines that are Sheep, else returns false.
	 * The BlackSheep counts as a Sheep.
	 */
	public boolean containsTwoSheeps() {
		int counter = 0;
		for (Ovine o: ovines) {
			if((o instanceof Sheep) || (o instanceof BlackSheep)) {
				counter++;
			}
		}
		return counter >= 2;
	}
	
	/**
	 * Method used to check if the Land contains almost one Sheep and one Ram.
	 * @return true if the List of the Ovines of the Land contains almost
	 * one Ovine that is a Sheep and one that is a Ram, else returns false.
	 * The BlackSheep counts as a Sheep.
	 */
	public boolean containsSheepAndRam() {
		boolean sheep = false;
		boolean ram = false;
		for (Ovine o: ovines) {
			if((o instanceof Sheep) || (o instanceof BlackSheep)) {
				sheep = true;
			} else if (o instanceof Ram) {
				ram = true;
			}
		}
		return ram && sheep;
	}
	
	/**
	 * Method used for Testing.
	 * Method used to know if the Land contains the Wolf.
	 * @return true if the Land contains the Wolf, else false.
	 */
	public boolean containsWolf(){
		if(wolf == null){
			return false;
		}
		return true;
	}

	/**
	 * Method used to remove an Ovine of a specific Type from the Land.
	 * Check the List of the Ovines of the Land until it finds an Ovine that has
	 * the OvineType inserted as parameter and removes it from the List, then returns it.
	 * If there aren't Ovine of the specified Type in the List, it returns null
	 * @param ovineType of the Ovine to remove from the Land.
	 * @return the Ovine that we remove from the Land if exists, else returns null.
	 */
	public Ovine subOvine(String ovineType){
		for(Ovine ovine:this.ovines){
			if(ovine.toString().equalsIgnoreCase(ovineType)){
				this.ovines.remove(ovine);
				return ovine;
			}
		}
		return null;
	}

	/**
	 * @return the Wolf of the Land.
	 */
	public Wolf getWolf(){
		return this.wolf;
	}

	/**
	 * @param wolf to set to the Land.
	 */
	public void setWolf(Wolf wolf){
		this.wolf = wolf;
	}

	/**
	 * Method used to remove the Wolf from the Land.
	 * Sets the Wolf of the Land as null.
	 */
	public void removeWolf(){
		this.wolf= null;
	}

	/**
	 * @return the list of the Ovines of the Land.
	 */
	public List<Ovine> getOvines(){
		return this.ovines;
	}

	/**
	 * @return the List of the Points of the Land.
	 */
	public List<Point> getLandPoints() {
		return landPoints;
	}

	/**
	 * @param landPoints to set to the Land.
	 */
	public void setLandPoints(List<Point> landPoints) {
		this.landPoints = landPoints;
	}
	
	/**
	 * Method used to get a random Point in the Land.
	 * It extracts a random Point from the List of the Points of the
	 * Land and returns it.
	 * @return a random Point from the Land Points.
	 */
	public Point getRandomLandPoint() {
		Random rand = new Random();
		return landPoints.get(rand.nextInt(landPoints.size()));
	}

	/** 
	 * @return the LandType of the Land.
	 */
	public LandType getLandType (){
		return this.landType;
	}

	/**
	 * @return the baricenter Point of the Land.
	 */
	public Point getBaricenter() {
		return baricenter;
	}

	/**
	 * @param baricenter Point to set to the Land.
	 */
	public void setBaricenter(Point baricenter) {
		this.baricenter = baricenter;
	}

	/**
	 * @return the landColorGUI of the Land.
	 */
	public int getLandColorGUI() {
		return landColorGUI;
	}

	/**
	 * @param landColorGUI to set to the Land.
	 */
	public void setLandColorGUI(int landColorGUI) {
		this.landColorGUI = landColorGUI;
	}
	
}
	
	
	


