package it.polimi.cg_17;

import static org.junit.Assert.*;
import guimodelsupport.GameGUISupporter;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.BlackSheep;
import model.GameBoard;
import model.Land;
import model.Road;
import model.RoadState;
import model.LandType;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class BlackSheepTest {
	private List<Land> lands = null;
	private Land l1 = null;
	private Land l2 = null;
	private Land l3 = null;
	private Road r1 = null;
	private Road r2 = null;
	private BlackSheep bs = null;
	
	/**
	 * Initialization of all
	 * @throws RemoteException 
	 */
	@Before
	public void setUp() throws RemoteException{
		lands = new ArrayList<Land>();
		l1 = new Land(LandType.FOREST);
		lands.add(l1);
		l2 = new Land(LandType.MOUNTAIN);
		lands.add(l2);
		l3 = new Land(LandType.FIELD);
		lands.add(l3);
		List<Point> forTest = new ArrayList<Point>();
		forTest.add(new Point(0, 0));
		l1.setLandPoints(forTest);
		l2.setLandPoints(forTest);
		l3.setLandPoints(forTest);
		
		
		r1 = new Road(1);
		r2 = new Road(2);
		l1.addNeighboringRoad(r1);
		l2.addNeighboringRoad(r1);
		l2.addNeighboringRoad(r2);
		l3.addNeighboringRoad(r2);
		r1.addNeighboringLand(l1);
		r1.addNeighboringLand(l2);
		r2.addNeighboringLand(l2);
		r2.addNeighboringLand(l3);
		bs = new BlackSheep(l1);
		l1.addOvine(bs);
	}
	/**
	 * The position of the Blacksheep should be l1.
	 */
	@Test
	public void builderControll(){
		assertEquals(l1, bs.getLandPosition());
	}
	
	/**
	 * I move the sheep in position l2.
	 */
	@Test
	public void moveControll(){
		bs.move(1);
		assertEquals(l2,bs.getLandPosition());
	}
	/**
	 * Move the blacksheep 2 time. It should be in l3.
	 */
	@Test
	public void doubleMoveControll(){
		bs.move(1);
		bs.move(2);
		assertEquals(l3,bs.getLandPosition());
		
	}
	
	/**
	 * The sheep would not move because there is not 3.
	 */
	@Test
	public void notMoveControll(){
		bs.move(3);
		assertEquals(l1, bs.getLandPosition());
	}
	
	/**
	 * The blacksheep should be in l2, cause to fence cannot move to l3.
	 */
	@Test
	public void notFencedMoveControll(){
		r2.setRoadState(RoadState.FENCED);
		bs.move(1);
		bs.move(2);
		assertEquals(l2, bs.getLandPosition());
	}
	
	/**
	 * Black sheep should be in l1, cause the Special Fenced cannot move.
	 */
	@Test 
	public void notSpecialFencedMoveControll(){
		r1.setRoadState(RoadState.SPECIALFENCED);
		bs.move(1);
		assertEquals(l1,bs.getLandPosition());
		
	}
	
	/**
	 * Black sheep should be in l1, cause the Shepherd cannot move.
	 */
	@Test
	public void notShepherdMoveControll(){
		r1.setRoadState(RoadState.SHEPHERD);
		bs.move(1);
		assertEquals(l1,bs.getLandPosition());
	}
	
	@Test
	public void setLandPositionControll(){
		bs.setLandPosition(l1);
		assertEquals(l1, bs.getLandPosition());
	}
}
