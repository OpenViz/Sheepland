package it.polimi.cg_17;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.ActionType;
import model.EventType;
import model.GameInfo;
import model.MarketStep;
import model.MoveOvineStep;
import model.Player;
import model.SellingCard;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class GameInfoTest {
	private GameInfo gi = null;
	
	@Before
	public void setup(){
		gi = new GameInfo(2);
	}
	
	@Test
	public void setterAndGetterControll(){
		List<SellingCard> cards = new ArrayList<SellingCard>();
		List<Integer> numbers = gi.getValidLandsIds();
		List<ActionType> actions = new ArrayList<ActionType>();
		List<Player> winnerPlayer = new ArrayList<Player>();
		
		gi.setActionDone(true);
		assertEquals(true,gi.isActionDone());
		
		gi.setMovedShepherd(false);
		assertEquals(false, gi.isMovedShepherd());
		
		gi.setActiveAction(ActionType.BUYCARD);
		assertEquals(ActionType.BUYCARD,gi.getActiveAction());
		
		gi.setActiveEvent(EventType.CHOOSEACTIVESHEPHERD);
		assertEquals(EventType.CHOOSEACTIVESHEPHERD, gi.getActiveEvent());
		
		gi.setActiveShepherdNumber(0);
		assertEquals(0, gi.getActiveShepherdNumber());
		
		gi.setEatedOvineId(12);
		assertEquals(12, gi.getEatedOvineId());
		
		gi.setKilledOvineId(14);
		assertEquals(14, gi.getKilledOvineId());
		
		gi.setNewOvineId(69);
		assertEquals(69, gi.getNewOvineId());
		
		gi.setMarketStep(MarketStep.BUYCARDSTEP);
		assertEquals(MarketStep.BUYCARDSTEP, gi.getMarketStep());
		
		gi.setSelingCards(cards);
		assertEquals(cards, gi.getSellingCards());
		
		gi.setIndexOfActivePlayer(0);
		assertEquals(0, gi.getIndexOfActivePlayer());
		
		assertEquals(2, gi.getNumberOfPlayers());
		
		gi.setLastActionDone(ActionType.COUPLING1);
		assertEquals(ActionType.COUPLING1, gi.getLastActionDone());
		
		gi.setPossibleActions(actions);
		assertEquals(actions, gi.getPossibleActions());
		
		gi.possibleActionsToString();
		
		gi.setWinnerPlayers(winnerPlayer);
		assertEquals(winnerPlayer, gi.getWinnerPlayers());
		
		gi.setMoveOvineStep(MoveOvineStep.DRAG);
		assertEquals(MoveOvineStep.DRAG, gi.getMoveOvineStep());
		
		gi.setForNextActionOfActivePlayer();
		assertEquals(2, gi.getNumberOfActionsForActivePlayer());
		assertEquals(false, gi.isActionDone());
		
		gi.setForNextPlayer();
		gi.setForNextPlayer();
		assertEquals(3, gi.getNumberOfActionsForActivePlayer());
		assertEquals(null, gi.getLastActionDone());
		
		assertEquals(numbers, gi.getValidLandsIds());
		
		
		
	}
	

}
