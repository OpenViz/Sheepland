package model;

import java.io.Serializable;
import java.util.Random;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Class for the Lamb. It's an Ovine and grow during the Game basing on its day life.
 */
public class Lamb extends Ovine implements Growable, Serializable {
	private static final long serialVersionUID = 1L;
	
	//The number of days that a Lamb needs to wait to grow.
	private static final int GROWDAY = 2;

	//The number of dayLife of the Lamb.
	private int dayLife;
	
	/**
	 * Builder of the Lamb. At the beginning it has 0 dayLife.
	 */
	public Lamb() {
		this.dayLife=0;
	}
	
	/**
	 * Builder of the Lamb that permits the manual settings of the id.
	 * The id of the Lamb is taken as parameter instead to be set by its superclasses.
	 * At the beginning it has 0 dayLife.
	 * @param ovineId that is assigned to the new Lamb.
	 */
	public Lamb(int ovineId) {
		this.dayLife=0;
		this.setId(ovineId);
	}
	
	/**
	 * This methods grow the Lamb in a Sheep or a Ram if its dayLife is equal to GROWDAY.
	 * Grow the Lamb randomly using the method growIn().
	 * @return ovine grown.
	 */
	public Ovine grow() {
		this.incrementDayLife();
		if(this.dayLife == Lamb.GROWDAY) {
			return this.growIn();
		}
		return null;
	}

	/**
	 * @return the GROWDAY of the Lamb.
	 */
	public int getGROWDAY() {
		return GROWDAY;
	}

	/**
	 * @return dayLife of the Lamb.
	 */
	public int getDayLife() {
		return this.dayLife;
	}

	/**
	 * @param dayLife to set to the Lamb.
	 */
	public void setDayLife(int dayLife) {
		this.dayLife = dayLife;
	}

	/**
	 * Override of the method toString for the Lamb. 
	 * @return Lamb as String.
	 */
	@Override
	public String toString() {
		return "lamb";
	}

	/**
	 * Increment the dayLife of the Lamb by 1
	 */
	private void incrementDayLife() {
		this.dayLife++;
	}

	/**
	 * Method Helper to grow().
	 * This method is used to grow a Lamb. The Lamb randomly grows in a Sheep or in a Ram.
	 *  @return a Sheep or a Ram that is the Lamb grown. Have the same Id that had when it was a Lamb.
	 */
	private Ovine growIn() {
		Random rand  = new Random();
		int n = rand.nextInt(101);
		Ovine ovine;
		if(n<50) {
			ovine = new Sheep();
			ovine.setId(this.getId());
			Identifier.decrementCounter();
			ovine.setOvinePosition(this.getOvinePosition());
			return ovine;
		} else {
			ovine = new Ram(this.getId());
			ovine.setId(this.getId());
			Identifier.decrementCounter();
			ovine.setOvinePosition(this.getOvinePosition());
			return ovine;
		}
	}

}
