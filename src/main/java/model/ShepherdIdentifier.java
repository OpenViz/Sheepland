package model;

import java.io.Serializable;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * This class is used to assign id to the Shepherds that are extension of it.
 */
public class ShepherdIdentifier implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Static counter to assign new Shepherds ids.
	private static int counter = 100;

	// The id of the Shepherd.
	private int shepherdId;
	
	/**
	 * Builder of the ShepherdIdentifier.
	 * Set the id of the Shepherd to the current value of counter.
	 */
	public ShepherdIdentifier() {
		this.shepherdId = counter;
		counter += 100;
	}
	
	/**
	 * 
	 * @return the id value.
	 */
	public int getId() {
		return shepherdId;
	}
	
	/**
	 * Method used for testing.
	 * Restarts the counter to 100.
	 */
	public static void setCounter() {
		counter = 100;
	}
}
