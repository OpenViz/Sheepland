package it.polimi.cg_17;

import static org.junit.Assert.*;

import org.junit.Test;

import model.ActionType;

public class ActionTypeTest {

	private ActionType at = null;
	
	@Test
	public void controll(){
		assertEquals(null,at);
		at = ActionType.BUYCARD;
		assertEquals(ActionType.BUYCARD,at);
		at = ActionType.COUPLING1;
		assertEquals(ActionType.COUPLING1,at);
		at = ActionType.COUPLING2;
		assertEquals(ActionType.COUPLING2,at);
		at = ActionType.KILLOVINE;
		assertEquals(ActionType.KILLOVINE,at);
		at = ActionType.MOVEOVINE;
		assertEquals(ActionType.MOVEOVINE,at);
		at = ActionType.MOVESHEPHERD;
		assertEquals(ActionType.MOVESHEPHERD,at);
	}
}
