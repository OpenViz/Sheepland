package it.polimi.cg_17;

import static org.junit.Assert.*;
import guimodelsupport.GameGUISupporter;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import mockView.MockView;
import model.Game;
import model.LandType;
import model.Ovine;
import model.Ovinetype;
import model.Ram;
import model.Sheep;

import org.junit.Before;
import org.junit.Test;

import utility.Observer;
import view.ViewInterface;
import controller.ActionsController;
import controller.ConverterController;
import controller.InputController;

public class ActionsControllerTest {
	private ActionsController ac = null;
	private ConverterController cc = null;
	private InputController ic = null;
	private List<ViewInterface> views = null;
	private ViewInterface vikkiView = null;
	private ViewInterface manuelView = null;
	private Game game = null;
	private GameGUISupporter gameGUISupporter;
	
	@Before
	public void setUp() throws RemoteException{
		game = new Game("Vikki", "Manuel");
		gameGUISupporter = new GameGUISupporter(game.getGameBoard());
		vikkiView = new MockView();
		manuelView = new MockView();
		views = new ArrayList<ViewInterface>();
		views.add(vikkiView);
		views.add(manuelView);
		game.addObserver((Observer) vikkiView);
		cc = new ConverterController(game);
		ic = new InputController(game, views, cc);
		ac = new ActionsController(game, views, cc, ic);
		
		game.getGameInfo().setIndexOfActivePlayer(0);
		game.getGameInfo().setActiveShepherdNumber(0);
		
		
	}
	
	@Test
	public void actionsControll() throws RemoteException{
		ac.moveShepherd();
		ac.moveShepherd();
		ac.moveShepherd();
		assertEquals(cc.getRoadOnId(47),game.getActivePlayer().getShepherd(0).getRoad());
		game.getGameInfo().setActiveShepherdNumber(1);
		ac.moveShepherd();
		assertEquals(cc.getRoadOnId(52),game.getActivePlayer().getShepherd(1).getRoad());
		
		game.getGameInfo().setIndexOfActivePlayer(1);
		game.getGameInfo().setActiveShepherdNumber(0);
		ac.moveShepherd();
		assertEquals(cc.getRoadOnId(42),game.getActivePlayer().getShepherd(0).getRoad());
		
		game.getGameInfo().setIndexOfActivePlayer(0);
		
		ac.moveOvine();
		assertEquals(true, cc.getLandOnId(6).contains(Ovinetype.BLACKSHEEP.toString()));
		
		ac.buyCard();
		assertEquals(true, game.getActivePlayer().searchCard(LandType.FOREST.toString()));
		
		ac.moveShepherd();
		ac.buyCard();
		ac.buyCard();
		ac.buyCard();
		ac.buyCard();
		ac.buyCard();
		assertTrue(game.getActivePlayer().searchCard(LandType.RIVER.toString()));
		
		Ovine o1 = new Sheep();
		Ovine o2 = new Ram();
		cc.getLandOnId(6).addOvine(o1);
		cc.getLandOnId(6).addOvine(o2);
		ac.coupling1();
		while(!(game.getGameBoard().getDice().getResult()==1)){
			MockView.setActionNumber(21);
			ac.coupling1();
		}
		assertTrue(cc.getLandOnId(6).getOvines().size()==5);
		ac.coupling2();
		assertTrue(cc.getLandOnId(6).getOvines().size()==6);
		
		game.getGameInfo().setIndexOfActivePlayer(1);
		game.getGameInfo().setActiveShepherdNumber(1);
		ac.moveShepherd();
		
		game.getGameInfo().setIndexOfActivePlayer(0);
		game.getGameInfo().setActiveShepherdNumber(0);
		
		ac.moveShepherd();
		game.getGameBoard().getDice().setResult(1);
		ac.killOvine();
		while(cc.getLandOnId(6).contains(Ovinetype.BLACKSHEEP.toString())){
			MockView.setActionNumber(25);
			ac.killOvine();
		}
		assertTrue(cc.getLandOnId(6).getOvines().size()==5);
		
	}

}
