package it.polimi.cg_17;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Before;
import org.junit.Test;

import utility.ServerLogger;
import model.GameBoard;
import model.Road;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class GameBoardTest {
	private GameBoard gameBoard2Player  = null;
	private GameBoard gameBoard3Player = null;
	private GameBoard gameBoard4Player = null;
	
	@Before
	public void setup(){
		try {
			gameBoard2Player = new GameBoard("Vittorio","Selo");
			gameBoard3Player = new GameBoard("Vittorio", "Selo", "Manuel");
			gameBoard4Player = new GameBoard("Vittorio", "Selo", "Manuel", "Tanzi");
		} catch (RemoteException e) {
			ServerLogger.printOnLogger("ActionsControllerTest", e);
		}
	}
	/**
	 * Test that startinggolds are correct
	 */
	@Test
	public void getStartingGoldsControll(){
		try {
			assertEquals(30,GameBoard.getStartinggolds());
			assertEquals(40, GameBoard.getStartinggoldsForTwoPlayers());
		} catch (RemoteException e) {
			ServerLogger.printOnLogger("ActionsControllerTest", e);
		}
	}
	/**
	 * Test the number of fence for all game
	 */
	@Test
	public void getFenceControll(){
		try {
			assertEquals(20, gameBoard2Player.getNumberFence());
			assertEquals(20, gameBoard3Player.getNumberFence());
			assertEquals(20, gameBoard4Player.getNumberFence());
			gameBoard2Player.subNumberOfFence();;
			assertEquals(19, gameBoard2Player.getNumberFence());
		} catch (RemoteException e) {
			ServerLogger.printOnLogger("ActionsControllerTest", e);
		}
	}
	
	/**
	 * Test the initial position fo wolf and blacksheep
	 */
	@Test
	public void wolfAndSheepPositionControll(){
		assertEquals(gameBoard2Player.getLands().get(0), gameBoard2Player.getWolf().getLandPosition());
		assertEquals(gameBoard3Player.getLands().get(0), gameBoard3Player.getBlackSheep().getLandPosition());
	}
	/**
	 * test that every player has a starting card
	 */
	@Test
	public void getStartingCardsControll(){
		try {
			assertEquals(4, gameBoard2Player.getStartingCards().size());
			assertEquals(1, gameBoard3Player.getPlayers().get(0).getCards().size());
			assertEquals(6, gameBoard3Player.getCards().size());
		} catch (RemoteException e) {
			ServerLogger.printOnLogger("ActionsControllerTest", e);
		}
	}
	/**
	 * Test the creation of the dice.
	 */
	@Test
	public void diceControll(){
		assertEquals(1, gameBoard4Player.getDice().getResult());
	}
	
	@Test
	public void roadsControll(){
		boolean temp = true;
		for(Road road : gameBoard3Player.getRoads()){
			temp = temp && !road.isBusy();
		}
		assertEquals(true, temp);
		
		
	}
	
	/**
	 * Test the method getNumberOfOvines
	 */
	@Test
	public void getNumberOfOvineControll(){
		assertEquals(19, gameBoard2Player.getNumberOfOvines());
	}
	@Test
	public void getOvineOnIdControll(){
		assertEquals(gameBoard3Player.getBlackSheep(), gameBoard3Player.getOvineOnId(61));
	}
	
}
