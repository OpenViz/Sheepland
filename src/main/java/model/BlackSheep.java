package model;

import java.io.Serializable;

/** 
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class of the BlackSheep. It's an Ovine and grow during the Game basing on its day life.
 * It also implements the interface Movable and can randomly move.
 */
public class BlackSheep extends Ovine implements Movable, Serializable{
	private static final long serialVersionUID = 1L;
	
	private Land landPosition;
	
	/**
	 * Builder of the BlackSheep.
	 * Takes its starting Land as parameter.
	 * @param startingLand for the BlackSheep builded.
	 */
	public BlackSheep(Land startingLand) {
		this.landPosition = startingLand;
	}
	
	/**
	 * Method that moves the BlackSheep. 
	 * The BlackSheep is moved through the Road with the number inserted as parameter, only if that is not busy.
	 * @param number of the Road where the BlackSheep has to be moved through.
	 */
	public int move(int number) {
		Land startingLand = this.landPosition;
		Road temp1 = startingLand.searchRoadForMoveAnimal(number);
		if(temp1!= null && !temp1.isBusy()){
			for(Land destinationLand: temp1.getNeighboringLands()){
				if(destinationLand!= startingLand){
					this.landPosition = destinationLand;
					this.setOvinePosition(landPosition.getRandomLandPoint());
					return 1;
				}
			}
		}
		return 0;
	}
	
	/**
	 * @return landPosition of the BlackSheep.
	 */
	public Land getLandPosition() {
		return landPosition;
	}

	/**
	 * @param landPosition to set to the BlackSheep.
	 */
	public void setLandPosition(Land landPosition) {
		this.landPosition = landPosition;
	}

	/**
	 * Override of the method toString for the BlackSheep. 
	 * @return BlackSheep as String.
	 */
	@Override
	public String toString(){
		return "BlackSheep";
	}

}
