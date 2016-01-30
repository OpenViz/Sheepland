package it.polimi.cg_17;

import static org.junit.Assert.*;
import java.awt.Point;
import model.Identifier;
import model.Land;
import model.Road;
import model.RoadState;
import model.LandType;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Tests for class Road.
 */
public class RoadTest {
	
	private Road road = null;
	private Road r1 = null;
	private Road r2 = null;
	private Land l1 = null;
	private Land l2 = null;

	@Before
	public void setUp(){
		Identifier.setCounter();
		road = new Road(1);
		r1 = new Road (2);
		r2 = new Road(3);;
		l1 = new Land(LandType.GREEN);
		l2 = new Land(LandType.RIVER);
	}
	/**
	 * Test the method getId
	 */
	@Test
	public void idControll(){
		assertEquals(0,road.getId());
		assertEquals(1,r1.getId());
		assertEquals(2,r2.getId());
	}
	/**
	 * Test the method getNumber
	 */
	@Test
	public void numberControll(){
		assertEquals(1,road.getNumber());
	}
	/**
	 * Test the method searchRoad
	 */
	@Test
	public void searchRoadControll(){
		road.addNeighboringRoad(r1);
		assertEquals(true,road.isNeighboringRoad(r1));
		assertEquals(false, road.isNeighboringRoad(r2));
		
	}
	/**
	 * test the method searchLand
	 */
	@Test
	public void searchLandControll(){
		road.addNeighboringLand(l1);
		assertEquals(true,road.isNeighboringLand(l1));
		assertEquals(false, road.isNeighboringLand(l2));
	}
	/**
	 * Test the method isBusy and isShepherd
	 */
	@Test
	public void isShepherdControll(){
		road.setRoadState(RoadState.SHEPHERD);
		assertEquals(true, road.isShepherd());
		assertEquals(true, road.isBusy());
	}
	/**
	 * Test the method getState
	 */
	@Test
	public void getStateControll(){
		assertEquals(RoadState.FREE, road.getRoadState());
	}
	
	@Test
	public void setPointOfRoadControll(){
		Point point = new Point(13,14);
		r1.setPointOfRoad(13, 14);
		assertEquals(point, r1.getRoadPosition());
	}
	
}
