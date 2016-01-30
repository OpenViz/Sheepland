package it.polimi.cg_17;

import static org.junit.Assert.*;
import guimodelsupport.GameGUISupporter;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import utility.Observer;
import view.ViewInterface;
import mockView.MockEventView;
import model.BlackSheep;
import model.Game;
import model.Land;
import model.LandType;
import model.SellingCard;
import model.Wolf;
import controller.ActionsController;
import controller.ConverterController;
import controller.EventsController;
import controller.InputController;

public class EventsControllerTest {
	private EventsController ec = null;
	private InputController ic = null;
	private ConverterController cc = null;
	private ActionsController ac = null;
	private Game game = null;
	private List<ViewInterface> views = null;
	private ViewInterface view = null;
	private ViewInterface view2 = null;
	private GameGUISupporter gameGUISupporter = null;
	
	@Before
	public void setUp() throws RemoteException{
		game = new Game("1", "2");
		gameGUISupporter = new GameGUISupporter(game.getGameBoard());
		view = new MockEventView();
		view2 = new MockEventView();
		views = new ArrayList<ViewInterface>();
		views.add(view);
		views.add(view2);
		game.addObserver((Observer) view);
		
		cc = new ConverterController(game);
		ic = new InputController(game, views, cc);
		ec = new EventsController(game, views, ic);
		ac = new ActionsController(game, views, cc, ic);
		
		SellingCard.setCounterToZero();
	}
	
	@Test
	public void eventControll() throws RemoteException{
		game.getGameInfo().setIndexOfActivePlayer(0);
		ec.chooseActiveShepherd();
		assertEquals(0,game.getActiveShepherdNumber());
		
		ec.growLambs();
		ec.growLambs();
		
		ec.moveBlackSheep();
		BlackSheep bs = game.getGameBoard().getBlackSheep();
		int number = bs.getLandPosition().getId();
		boolean temp = this.landcontroll(number);
		assertTrue(temp);
		
		ec.moveWolf();
		Wolf w = game.getGameBoard().getWolf();
		number = w.getLandPosition().getId();
		temp = this.landcontroll(number);
		assertTrue(temp);
		
		game.getGameInfo().setIndexOfActivePlayer(0);
		game.getGameInfo().setActiveShepherdNumber(0);
		ac.moveShepherd();
		//buy forest
		ac.buyCard(); 
		

		game.getGameInfo().setIndexOfActivePlayer(1);
		game.getGameInfo().setActiveShepherdNumber(0);
		ac.moveShepherd();
		//buy filed
		ac.buyCard();
		ac.moveShepherd();
		ac.moveShepherd();
		ac.moveShepherd();
		ac.moveShepherd();
		
		
		ec.checkWinner();
		assertEquals(1, game.getGameInfo().getWinnerPlayers().size());
		assertEquals("1", game.getGameInfo().getWinnerPlayers().get(0).getNickname());
		
		ec.market();
		assertTrue(game.getGameBoard().getPlayers().get(0).searchCard(LandType.FIELD.toString()));
		
		
	}
	
	private boolean landcontroll(int number){
		if(number==1){
			return true;
		}else if(number==7){
			return true;
		}else if(number==18){
			return true;
		}else if(number==6){
			return true;
		}else if(number==15){
			return true;
		}else if(number==10){
			return true;
		}
		return false;
	}
	
	
}
