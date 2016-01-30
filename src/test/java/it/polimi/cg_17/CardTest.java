package it.polimi.cg_17;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Card;
import model.LandType;

/**
 * Testing the class Card
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class CardTest {
	private Card c1 = null;
	private int price;
	
	@Before
	public void setup(){
		price = 3; 
		c1 = new Card(LandType.RIVER, price );
		
	}
	
	/**
	 * True, the type should be River.
	 */
	@Test
	public void getLandTypeControll(){
		assertEquals(LandType.RIVER, c1.getLandType());
	}
	
	/**
	 * True, the price should be 3.
	 */
	@Test
	public void getPriceControll(){
		assertEquals(3, c1.getPrice());
	}

}
