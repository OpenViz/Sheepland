package it.polimi.cg_17;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Lamb;
import model.Ovine;
import model.Ram;
import model.Sheep;
/**
 * Test about the class lamb
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class LambTest {
	
	private Lamb l1 = null;
	private Lamb l2 = null;
	private Ovine o1 = null;
	
	@Before
	public void setup(){
		l1 = new Lamb();
		l2 =  new Lamb(14);
	}
	
	/**
	 * Test the builder
	 */
	@Test
	public void builderControll(){
		assertEquals(0, l1.getDayLife());
		
	}
	
	/**
	 * Test the growing method.
	 */
	@Test
	public void growControll(){
		l1.grow();
		o1 = l1.grow();
		boolean temp = (o1 instanceof Ram ) || (o1 instanceof Sheep);
		assertEquals ( true, temp );
		
	}
	
	@Test
	public void cycleControll(){
		for(int i = 0; i<20; i++){
			this.setup();
			this.growControll();
		}
	}
	
	@Test
	public void GrowDayAndLifeDayControll(){
		assertEquals(2, l2.getGROWDAY());
		l2.setDayLife(2);
		assertEquals(2, l2.getDayLife());
	}

}
