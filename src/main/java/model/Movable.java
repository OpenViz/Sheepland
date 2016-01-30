package model;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *  
 * Interface for all Objects that can move.
 */
public interface Movable {

	/**
	 * Method used to move the Object that implements this interface.
	 * @param number related at the Object movement.
	 * @return an int related to the movement of the Object.
	 */
	public int move(int number);
		
}
