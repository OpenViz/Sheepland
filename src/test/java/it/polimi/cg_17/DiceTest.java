package it.polimi.cg_17;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Dice;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class DiceTest {
	private Dice d = null;
	private int numberOfFaces;
	
	@Before
	public void setup(){
		numberOfFaces = 6;
		d = new Dice(numberOfFaces);
	}
	
	@Test
	public void resultControll(){
		d.setResult(4);
		assertEquals(4,d.getResult());
	}
	
	@Test
	public void result2Controll(){
		d.setResult(4);
		d.setResult(100);
		d.setResult(-5);
		assertEquals(4, d.getResult());
	}
	
	/**
	 * Test  1 <= result <= numberOfFaces 
	 */
	@Test
	public void rollControll(){
		d.rollDice();
		boolean temp = false;
		for(int i = 1; i<= numberOfFaces; i++){
			temp = (temp || i == d.getResult()); 
		}
		assertEquals(true,temp);
		
	}

}
