package it.polimi.cg_17;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Ovine;
import model.Sheep;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 */
public class SheepTest {
 	private Ovine s2 =  null;
	
	@Before
	public void setup(){
		s2 =  new Sheep(12);
	}
	
	@Test
	public void idControll(){
		assertEquals(12,s2.getId());
	}
}
