package it.polimi.cg_17;

import static org.junit.Assert.*;
import guimodelsupport.GameGUISupporter;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.BlackSheep;
import model.GameBoard;
import model.Lamb;
import model.Land;
import model.Ovine;
import model.Ram;
import model.Road;
import model.Sheep;
import model.RoadState;
import model.LandType;
import model.Wolf;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Testing the class Wolf.
 */
public class WolfTest {
	private Wolf wolf = null;
	private Ovine o1 = null;
	private Ovine o2 = null;
	private Ovine o3 = null;
	private Ovine o4 = null;
	private List<Land> lands = null;
	private Land l1 = null;
	private Land l2 = null;
	private Land l3 = null;
	private Road r1 = null;
	private Road r2 = null;
	private Road r3 = null;	
	
	@Before
	public void setup() throws RemoteException {
		lands = new ArrayList<Land>();
		l1 = new Land(LandType.SHEEPSBURG);
		lands.add(l1);
		wolf = new Wolf(l1);
		l2 = new Land(LandType.FOREST);
		lands.add(l2);
		l3 = new Land ( LandType .MOUNTAIN);
		lands.add(l3);
		r1 = new Road(1);
		r2 = new Road(2);
		r3 = new Road(3);
		List<Point> forTest = new ArrayList<Point>();
		forTest.add(new Point(0, 0));
		l1.setLandPoints(forTest);
		l2.setLandPoints(forTest);
		l3.setLandPoints(forTest);
		
		l1.addNeighboringRoad(r1);
		l1.addNeighboringRoad(r2);
		l2.addNeighboringRoad(r1);
		l2.addNeighboringRoad(r3);
		l3.addNeighboringRoad(r2);
		l3.addNeighboringRoad(r3);
		r1.addNeighboringLand(l1);
		r1.addNeighboringLand(l2);
		r2.addNeighboringLand(l1);
		r2.addNeighboringLand(l3);
		r3.addNeighboringLand(l2);
		r3.addNeighboringLand(l3);
		o1 = new Sheep();
		o2 = new Ram();
		o3 = new Lamb();
		o4 = new BlackSheep(l2);
	}
	
	/**
	 * Test the builder of the wolf
	 */
	@Test
	public void getPositionControll(){
		assertEquals(l1 ,wolf.getLandPosition());
	}
	/**
	 * Test the ordinary controll of the wolf
	 */
	@Test
	public void ordinaryMoveWolfControll(){
		wolf.move(1);
		assertEquals(l2, wolf.getLandPosition());
		assertEquals(0, l2.numberOfOvines());
		
	}
	/**
	 * Test the jumpMove of the wolf.
	 */
	@Test
	public void notJumpMovefControll(){
		r1.setRoadState(RoadState.FENCED);
		wolf.move(1);
		assertEquals(l1, wolf.getLandPosition());
	}
	/**
	 * Test the special move of the wolf.
	 */
	@Test
	public void moveControll(){
		r1.setRoadState(RoadState.SPECIALFENCED);
		r2.setRoadState(RoadState.SHEPHERD);
		wolf.move(1);
		assertEquals(l2, wolf.getLandPosition());
		
	}
	/**
	 * Test the jumpMove of the wolf.
	 */
	@Test
	public void move2Controll(){
		r1.setRoadState(RoadState.FENCED);
		r2.setRoadState(RoadState.SPECIALFENCED);
		wolf.move(1);
		assertEquals(l2,wolf.getLandPosition());
	}
	/**
	 * Test about removing a random ovine
	 */
	@Test
	public void eatSheepControll(){
		l2.addOvine(o1);
		l2.addOvine(o2);
		l2.addOvine(o3);
		l2.addOvine(o4);
		wolf.move(1);
		boolean temp = ( l2.getOvines().size() == 3 );
		assertEquals(l2,wolf.getLandPosition());
		assertEquals(true, temp);
	}
	
	@Test
	public void eatOneSheep(){
		l2.addOvine(o1);
		wolf.move(1);
		assertEquals(false, l2.contains("Sheep"));
	}
	
	@Test
	public void setLandPositionControll(){
		Land land = new Land(LandType.FIELD);
		wolf.setLandPosition(land);
		assertEquals(land, wolf.getLandPosition());
	}
	
	@Test
	public void PositionWolfControll(){
		Point point =  new Point(13,14);
		wolf.setWolfPosition(point);
		assertEquals(point, wolf.getWolfPosition());
	}
	
	
	
	
	

}
