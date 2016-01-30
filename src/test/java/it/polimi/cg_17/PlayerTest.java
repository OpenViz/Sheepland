package it.polimi.cg_17;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Card;
import model.Color;
import model.Player;
import model.Road;
import model.Shepherd;
import model.LandType;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Testing the class Player
 */
public class PlayerTest {
	private Player player = null;
	private Player player2 = null;
	private Card card = null;
	
	@Before
	public void setup(){
		player = new Player("Cloud", 30, Color.GREEN, false);
		player2 = new Player("Vikisix", 30, Color.RED, true);
		card = new Card(LandType.FIELD, 1);
	}
	/**
	 * Test method getGold
	 */
	@Test
	public void getGoldControll(){
		assertEquals(30,player.getGold());
	}
	/**
	 * Test method getNickname
	 */
	@Test
	public void getNicknameControll(){
		assertEquals("Vikisix", player2.getNickname());
	}
	/**
	 * Test method getColor
	 */
	@Test
	public void getColorControll(){
		assertEquals(Color.GREEN, player.getColor());
		assertEquals(Color.RED, player2.getColor());
	}
	/**
	 * Test method addGold
	 */
	@Test
	public void addGoldControll(){
		player.addGolds(10);
		assertEquals(40, player.getGold());
	}
	/**
	 * Test method subGold
	 */
	@Test
	public void subGoldControll(){
		player.subGolds(10);
		assertEquals(20, player.getGold());
	}
	
	/**
	 * Test the method addCard
	 */
	@Test
	public void addCardControll(){
		assertEquals(false, player.searchCard(LandType.FIELD.toString()));
		player.addCard(card);
		assertEquals(true, player.searchCard(LandType.FIELD.toString()));
		assertEquals(false, player.searchCard(LandType.SHEEPSBURG.toString()));
	}
	/**
	 * Test the method subCard
	 */
	@Test
	public void subCardControll(){
		Card card1 = new Card(LandType.FIELD, 2);
		player.addCard(card);
		player.addCard(card1);
		assertEquals(true, player.searchCard(LandType.FIELD.toString()));
		player.subCard(LandType.FIELD.toString());
		assertEquals(true, player.searchCard(LandType.FIELD.toString()));
		assertEquals(null, player.subCard(LandType.MOUNTAIN.toString()));
	}
	/**
	 * Test method haveEnough
	 */
	@Test
	public void haveEnoughControll(){
		assertEquals(true, player.haveEnoughGolds(15));
		assertEquals(false, player2.haveEnoughGolds(400));
	}
	
	@Test
	public void shepHerdControll(){
		List<Shepherd> shepherds = player2.getShepherds();
		assertEquals(Color.RED, shepherds.get(0).getColor());
		assertEquals(Color.RED, player2.getShepherd(0).getColor());
		assertEquals(Color.RED, player2.getShepherd(1).getColor());
		assertEquals(Color.GREEN, player.getShepherd(0).getColor());
	}
	
	@Test
	public void numberOfCardControll(){
		assertEquals(0, player.numberOfCard(LandType.FOREST));
		player.addCard(card);
		assertEquals(1, player.numberOfCard(LandType.FIELD));
	}
	
	@Test
	public void getRoadofShepherdControll(){
		Road r1 = new Road(32);
		player2.getShepherd(0).setRoad(r1);
		assertEquals(r1	, player2.getRoadOfShepherd(0));
	}
	
}
