/**
 * 
 */
package model;

import java.awt.Point;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class of the Wolf.
 * It implements the interface Movable and can randomly move.
 */
public class Wolf implements  Movable, Animal, Serializable {
	private static final long serialVersionUID = 1L;

	private Land landPosition;
	private Point wolfPosition;
	
	/**
	 * Builder of the Wolf.
	 * Takes its starting land as parameter.
	 * @param startingLand for the Wolf builded.
	 */
	public Wolf(Land startingLand) {
		this.landPosition = startingLand;
	}

	/**
	 * Method that moves the Wolf. 
	 * The Wolf is moved through the street with the number inserted as parameter, only if that is not busy.
	 * But if all roads around the wolf are busy, the wolf jump in another land.
	 * @param number of the road where the Wolf have to be moved through.
	 * @return the ovineId of the ovine eated. If no ovine has been eated, return 0.
	 */
	public int move(int number) {
		if(this.landPosition.isAllBusy()){
			return this.jumpMove(number);
		}
		return this.ordinaryMove(number);		
	}
	
	/**
	 * @return the wolfPosition Point of the Wolf.
	 */
	public Point getWolfPosition() {
		return wolfPosition;
	}

	/**
	 * @param wolfPosition Point to set to the Wolf.
	 */
	public void setWolfPosition(Point wolfPosition) {
		this.wolfPosition = wolfPosition;
	}

	/**
	 * @return the landPosition of the Wolf.
	 */
	public Land getLandPosition() {
		return landPosition;
	}

	/**
	 * @param landPosition to set to the Wolf.
	 */
	public void setLandPosition(Land landPosition) {
		this.landPosition = landPosition;
	}

	/**
	 * Method helper to move().
	 * Move the wolf in the ordinary way.
	 * The Wolf is moved through the street with the number inserted as parameter, only if that is not busy.
	 * If there are some ovines in the land where the wolf is moved with this method it randomly eat one of them.
	 * @param number of the road where the Wolf have to be moved through.
	 * @return the ovineId of the ovine eated. If no ovine has been eated, return 0.
	 */
	private int ordinaryMove(int number){
		Road temp1 = this.landPosition.searchRoadForMoveAnimal(number);
		if(temp1!= null && !temp1.isBusy()){
			for(Land e: temp1.getNeighboringLands()){
				if(e!= this.landPosition){
					this.landPosition.removeWolf();
					e.setWolf(this);
					this.landPosition = e;
					this.wolfPosition = landPosition.getRandomLandPoint();
					return this.eatOvine();
				}
			}
		}
		return 0;
	}

	/**
	 * Method helper to move().
	 * Move the wolf in the jump way. This method is invoked if all the road around the wolf are busy or fenced.
	 * The Wolf is moved through the street with the number inserted as parameter, even if it's fenced.
	 * If there are some ovines in the land where the wolf is moved with this method it randomly eat one of them.
	 * @param number of the road where the Wolf have to be moved through.
	 * @return the ovineId of the ovine eated. If no ovine has been eated, return 0.
	 */
	private int jumpMove(int number){
		Road temp1 = this.landPosition.searchRoadForMoveAnimal(number);
		if(temp1!= null && !temp1.isShepherd()){
			for(Land e: temp1.getNeighboringLands()){
				if(e!= this.landPosition){
					this.landPosition.removeWolf();
					e.setWolf(this);
					this.landPosition = e;
					this.wolfPosition = landPosition.getRandomLandPoint();
					return this.eatOvine();
				}
			}
		}
		return 0;
	}

	/**
	 * Method helper to ordinaryMove() and jumpMove().
	 * The wolf eat a random ovine in his land position.
	 * @return the ovineId of the ovine eated. If no ovine has been eated, return 0.
	 */
	private int eatOvine() {
		List<Ovine> ovines = this.landPosition.getOvines();
		if(!ovines.isEmpty()){
			Random random = new Random();
			int rand =  random.nextInt(ovines.size());
			return ovines.remove(rand).getId();
		}
		return 0;
	}	
}
