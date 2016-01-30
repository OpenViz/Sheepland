package model;

import java.io.Serializable;

/**
 * 
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * This class is used to assign id to the Objects that are extension of it.
 */
public class Identifier implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Static counter to assign new ids.
	private static int counter=0;
	// The id of the Object.
	private int id;
	
	/**
	 * Builder of the Identifier.
	 * Set the id of the Object to the current value of counter.
	 */
	public Identifier(){
		this.id=counter;
		counter++;
	}
	
	/**
	 * @return the id of the Object;
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * @param id to set to the Object.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Decrements the counter by 1.
	 */
	public static void decrementCounter() {
		counter--;
	}
	
	/**
	 * Method used for Testing.
	 * Resets the counter to 0.
	 */
	public static void setCounter() {
		counter = 0;
	}

}
