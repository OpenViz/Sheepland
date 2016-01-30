package it.polimi.cg_17;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import view.CLIViewRMI;
import view.ViewInterface;
import controller.GameController;

public class GameControllerTest {
	private GameController gc1 = null;
	private GameController gc2 = null;
	private GameController gc3 = null;
	private List<ViewInterface> views = null;
	private ViewInterface v1 = null;
	private ViewInterface v2 = null;
	private ViewInterface v3 = null;
	private ViewInterface v4 = null;
	
	@Before
	public void setUp() throws RemoteException{
		v1 = new CLIViewRMI();
		v1.setNickname("1");
		v2 = new CLIViewRMI();
		v2.setNickname("2");
		v3 = new CLIViewRMI();
		v3.setNickname("3");
		v4 = new CLIViewRMI();
		v4.setNickname("4");
		views = new ArrayList<ViewInterface>();
		views.add(v1);
		views.add(v2);
		gc1 = new GameController(views);
		views.add(v3);
		gc2 = new GameController(views);
		views.add(v4);
		gc3 = new GameController(views);
	}
	
	@Test
	public void GameControllerControll(){
		gc1.getGame();
		assertEquals(views,gc1.getViews());
		assertEquals(views,gc2.getViews());
		assertEquals(views,gc3.getViews());
	}
}
