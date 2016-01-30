package it.polimi.cg_17;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import utility.Observer;
import view.CLIViewRMI;
import view.ViewInterface;
import model.ActionType;
import model.EventType;
import model.Game;
import model.GameBoard;
import model.GameInfo;
import model.Player;
import model.Road;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class GameTest {
	private Game g1 = null;
	private Game g2 = null;
	private Game g3 = null;
	
	@Before
	public void setUp() throws RemoteException {
		this.g1 = new Game("1", "2");
		this.g2 = new Game("1", "2", "3");
		this.g3 = new Game("1", "2","3","4");
	}
	
	@Test
	public void setAndGetterControll() throws RemoteException {
		GameBoard gb = new GameBoard();
		GameInfo gi =  new GameInfo(3);
		
		this.g2.setGameInfo(gi);
		assertEquals(gi, g2.getGameInfo());
		
		
		this.g1.setGameBoard(gb);
		assertEquals(gb, g1.getGameBoard());
	}
	
	@Test
	public void observerControll() throws RemoteException {
		ViewInterface o1 = new CLIViewRMI();
		o1.setGame(g3);
		g3.addObserver((Observer) o1);
		g3.addObserver(null);
		g3.notifyObserver();
		assertEquals(o1, g3.getObservers().get(0));
		g3.removeObserver((Observer) o1);
		g3.removeObserver(null);
		assertEquals(true, g3.getObservers().isEmpty());
	}
	
	@Test
	public void saveChainMethodsControll() throws RemoteException {
		assertTrue(g1.getActivePlayer() instanceof Player);
		assertEquals(0, g1.getIndexOfActivePlayer());
		assertTrue(g1.getCardsOnSales() instanceof List<?>);
		assertTrue(g1.getLands() instanceof List<?>);
		
		g1.setActiveShepherdNumber(0);
		assertEquals(0, g1.getActiveShepherdNumber());
		
		Road r1 = g1.getGameBoard().getRoads().get(0);
		g1.getGameBoard().getPlayers().get(0).getShepherd(0).setRoad(r1);
		
		assertTrue(g1.getNeighboringLandsOfActivePlayerShepherd(0) instanceof List<?>);
		assertTrue(g1.getValidsLandsIds() instanceof List<?>);
		
		
		
		g1.rollDice();
		assertTrue(g1.getResultOfDice()>= 1 && g1.getResultOfDice() <= 6);
		
	}
	
	@Test
	public void actionAndEventControll() throws RemoteException {
		g2.setActionDone(true);
		assertEquals(true, g2.getGameInfo().isActionDone());
		
		g2.setActiveEvent(EventType.GROWTH);
		assertEquals(EventType.GROWTH, g2.getGameInfo().getActiveEvent());
		
		g2.setActiveAction(ActionType.BUYCARD);
		assertEquals(ActionType.BUYCARD, g2.getGameInfo().getActiveAction());
	}
	
	@Test
	public void ValidRoadAndValidLandControll() throws RemoteException {
		List<Integer> validRegionsNumber = new ArrayList<Integer>();
		Integer i1 = new Integer(11);
		Integer i2 = new Integer(12);
		validRegionsNumber.add(i1);
		validRegionsNumber.add(i2);
		g3.setValidLandsIds(validRegionsNumber);
		assertEquals(i1, g3.getGameInfo().getValidLandsIds().get(0));
		assertEquals(i2, g3.getGameInfo().getValidLandsIds().get(1));
		
		g3.clearValidLandsIds();
		assertTrue(g3.getGameInfo().getValidLandsIds().isEmpty());
	}
	
	

}
