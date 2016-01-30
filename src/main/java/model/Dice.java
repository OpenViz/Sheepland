package model;

import java.io.Serializable;
import java.util.Random;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Class of the Dice that can be rolled to obtain a random number based on its number of faces.
 */
public class Dice implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Says how many faces has the Dice.
	 */
	int numberOfFaces;
	int result;
	
	/**
	 * Builder of the Dice. Takes its number of faces as parameter.
	 * @param numberOfFaces of the dice builded
	 */
	public Dice( int numberOfFaces){
		this.numberOfFaces = numberOfFaces;
		this.result = 1; 
		
	}
	/**
	 * A method to obtain a random number from Dice.
	 * Sets a random number between 1 to number of face to the Dice result.
	 */
	public void rollDice(){
		Random rand = new Random();
		this.result =  rand.nextInt(numberOfFaces)+1;
	}
	
	/**
	 * A method to take the last value of Dice result.
	 * @return the current result of the dice.
	 */
	public int getResult(){
		return this.result;
	}
	
	// Need this function for Testing
	/**
	 * @param n set the result to n if it is between 0 and numberOfFace, else it doesn't change.
	 */
	public void setResult(int n){
		if(n <= this.numberOfFaces && n > 0){
			this.result = n;
			}
	}

}
