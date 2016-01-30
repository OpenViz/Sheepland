package it.polimi.cg_17;

import static org.junit.Assert.*;

import org.junit.Test;

import model.EventType;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class EventTypeTest {
	private EventType et = null;
	
	@Test
	public void controll(){
		assertEquals(null,et);
		et = EventType.CHOOSEACTIVESHEPHERD;
		assertEquals(EventType.CHOOSEACTIVESHEPHERD, et);
		et = EventType.GROWTH;
		assertEquals(EventType.GROWTH, et);
		et = EventType.MARKET;
		assertEquals(EventType.MARKET, et);
		et = EventType.MOVEBLACKSHEEP;
		assertEquals(EventType.MOVEBLACKSHEEP, et);
		et = EventType.MOVEWOLF;
		assertEquals(EventType.MOVEWOLF, et);
		et = EventType.SETSHEPHERDSTARTINGPOSITIONS;
		assertEquals(EventType.SETSHEPHERDSTARTINGPOSITIONS, et);
		et = EventType.WINNERTIME;
		assertEquals(EventType.WINNERTIME, et);
	}
}
