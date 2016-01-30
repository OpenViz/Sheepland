package it.polimi.cg_17;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.ShepherdIdentifier;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 */
public class ShepherdIdentificatorTest {
	private ShepherdIdentifier si1 = null;
	private ShepherdIdentifier si2 = null;
	private ShepherdIdentifier si3 = null;

	@Before
	public void setup(){
		ShepherdIdentifier.setCounter();
		si1 = new ShepherdIdentifier();
		si2 = new ShepherdIdentifier();
		ShepherdIdentifier.setCounter();
		si3 = new ShepherdIdentifier();
	}
	
	@Test
	public void identificatorControll(){
		assertEquals(100, si1.getId());
		assertEquals(200, si2.getId());
		assertEquals(100, si3.getId());
	}
	
}
