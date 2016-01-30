package it.polimi.cg_17;

import static org.junit.Assert.*;

import org.junit.Test;

import model.MarketStep;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 */
public class MarketStepTest {
	private MarketStep ms = null;
	
	@Test
	public void marketStepControll(){
		assertEquals(null,ms);
		ms = MarketStep.BUYCARDSTEP;
		assertEquals(MarketStep.BUYCARDSTEP,ms);
		ms = MarketStep.SELECTPRICESTEP;
		assertEquals(MarketStep.SELECTPRICESTEP,ms);
		ms = MarketStep.SELECTTYPESTEP;
		assertEquals(MarketStep.SELECTTYPESTEP,ms);
		ms = MarketStep.YESORNOTSTEP;
		assertEquals(MarketStep.YESORNOTSTEP,ms);
	}

}
