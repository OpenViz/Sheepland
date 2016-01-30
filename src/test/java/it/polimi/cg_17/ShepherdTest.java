package it.polimi.cg_17;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import model.Color;
import model.Road;
import model.Shepherd;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Testing the class Shepherd.
 */
public class ShepherdTest {
	private Shepherd shepherd = null;
	private Road road = null;
	private Road road2 = null;
	
	@Before
	public void setup(){
		shepherd = new Shepherd(Color.RED);
		road = new Road(1);
		road2 = new Road(3);
		
	}
	/**
	 * Test the method getColor
	 */
	@Test
	public void getColorControll(){
		assertEquals(Color.RED, shepherd.getColor());
	}
	/**
	 * Test the method getRoad
	 */
	@Test
	public void getRoadControll(){
		assertEquals(null, shepherd.getRoad());
	}
	/**
	 * Test the method setRoad
	 */
	@Test
	public void setRoadControll(){
		shepherd.setRoad(road);
		assertEquals(road, shepherd.getRoad());
		shepherd.setRoad(road2);
		assertEquals(road2, shepherd.getRoad());
	}
	
	@Test
	public void placedControll(){
		assertEquals(false, shepherd.isPlaced());
		shepherd.setPlaced(true);
		assertEquals(true, shepherd.isPlaced());
	}
	
	@Test
	public void colorControll(){
		assertEquals(Color.RED, shepherd.getColor());
		shepherd.setColor(Color.BLUE);
		assertEquals(Color.BLUE, shepherd.getColor());
	}
	
	@Test
	public void positionControll(){
		Point p1 = new Point(19,19);
		Point p2 = new Point(10,10);
		shepherd.setShepherdPosition(p1);
		assertEquals(p2, shepherd.getShepherdPosition());
	}

}
