/**
 * 
 */
package it.polimi.cg_17;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.LandType;
import model.SellingCard;

/**
 * @author vittorioselo
 *
 */
public class SellingCardTest {
	private SellingCard sc1 = null;
	private SellingCard sc2 = null;
	private SellingCard sc3 = null;
	
	@Before
	public void setUp(){
		SellingCard.setCounterToZero();
		sc1 = new SellingCard(LandType.GREEN.toString(), 1, "1");
		sc2 = new SellingCard(LandType.FIELD.toString(), 1, "2");
		SellingCard.setCounterToZero();
		sc3 = new SellingCard(LandType.MOUNTAIN.toString(), 1, "0");
	}
	
	@Test
	public void getterControll(){
		assertEquals(0,sc1.getId());
		assertEquals(1,sc2.getId());
		assertEquals(LandType.GREEN.toString(),sc1.getCardType());
		assertEquals("2",sc2.getPlayerNickname());
		assertEquals(1, sc3.getSellingPrice());
		assertEquals(0, sc3.getId());
	}	
}
