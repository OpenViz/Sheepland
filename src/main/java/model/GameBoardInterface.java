package model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Interface of the class GameBoard.
 */
public interface GameBoardInterface extends Serializable{
	 
	/**
	 * @return the BlackSheep of the GameBoard.
	 */
	public BlackSheep getBlackSheep() ;
	
	/**
	 * @return the List of Cards of the GameBoard.
	 */
	public List<Card> getCards() ;
	
	/**
	 * @return the Dice of the GameBoard.
	 */
	public Dice getDice() ;
	
	/**
	 * @return the List of the Lands of the GameBoard.
	 */
	public List<Land> getLands() ;
	
	/**
	 * @return the List of the Players of the GameBoard.
	 */
	public List<Player> getPlayers() ;
	
	/**
	 * @return the List of the Roads of the GameBoard.
	 */
	public List<Road> getRoads() ;
	
	/**
	 * @return the Wolf of the GameBoard.
	 */
	public Wolf getWolf() ;  

}
