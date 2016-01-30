package it.polimi.cg_17;

import static org.junit.Assert.*;

import org.junit.Test;

import model.MoveOvineStep;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 */
public class MoveOvineStepTest {
	
	private MoveOvineStep mos = null;
	
	@Test
	public void controll(){
		assertEquals(null,mos);
		mos = MoveOvineStep.DRAG;
		assertEquals(MoveOvineStep.DRAG,mos);
		mos = MoveOvineStep.DROP;
		assertEquals(MoveOvineStep.DROP,mos);
		mos = MoveOvineStep.END;
		assertEquals(MoveOvineStep.END,mos);
	}

}

