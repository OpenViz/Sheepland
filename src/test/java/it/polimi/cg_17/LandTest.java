package it.polimi.cg_17;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.BlackSheep;
import model.Identifier;
import model.Lamb;
import model.Land;
import model.Ovine;
import model.Ovinetype;
import model.Ram;
import model.Road;
import model.Sheep;
import model.RoadState;
import model.LandType;
import model.Wolf;

import org.junit.Before;
import org.junit.Test;


/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * Tests for class Land.
 */
public class LandTest {
	private Land land = null;
	
	@Before
	public void setup(){
		Identifier.setCounter();
		land = new Land(LandType.FOREST);
	}
	
	/**
	 * Test the id controll.
	 */
	@Test
	public void idControll() {
		Land land1 = new Land(LandType.GREEN);
		assertEquals(1,land1.getId());
	}
	
	/**
	 * Test the type of creation.
	 */
	@Test
	public void landTypeControll() {
		assertEquals("The type of the land is ", LandType.FOREST, land.getLandType());
		
	}
	
	/**
	 * Test the method addOvine
	 */
	@Test
	public void addOvineControll() {
		Ovine o1 = new Sheep();
		land.addOvine(o1);
		assertEquals(1,land.numberOfOvines());
	}
	
	/**
	 * Test the method contain
	 */
	@Test
	public void containsControll() {
		Ovine o1 = new Sheep();
		Ovine o2 = new Ram();
		land.addOvine(o1);
		land.addOvine(o2);
		assertEquals(true,land.contains("Ram"));
		assertEquals(true, land.contains("Sheep"));
		assertEquals(false, land.contains("Lamb"));
		assertEquals(false, land.contains("asd"));
		land.subOvine("sheep");
		assertEquals(false, land.contains("sheep"));
	}
	
	/**
	 * Test the method subOvine
	 */
	@Test
	public void subOvineControll() {
		assertEquals(null, land.subOvine(Ovinetype.SHEEP.toString()));
		Ovine o1 = new Sheep();
		Ovine o2 = new Sheep();
		Ovine o3 = new Ram();
		land.addOvine(o1);
		land.addOvine(o2);
		land.addOvine(o3);
		land.subOvine("Ram");
		assertEquals(false,land.contains("Ram") );
		assertEquals(null, land.subOvine("ram"));
		assertEquals(null, land.subOvine("asd"));
	}
	
	@Test
	public void subOvineControll2() {
		Ovine o1 = new Sheep();
		Ovine o2 = new BlackSheep(land);
		Ovine o4 = new Sheep();
		Ovine o3 = new Ram();
		land.addOvine(o1);
		land.addOvine(o2);
		land.addOvine(o3);
		land.addOvine(o4);
		land.subOvine("Sheep");
		assertEquals(true,land.contains("Sheep") );
		land.subOvine("Blacksheep");
		assertEquals(null, land.subOvine("Blacksheep"));
	}
	
	/**
	 * Testing the function of add and sub wolf.
	 */
	@Test
	public void WolfControll() {
		Land l1 = new Land(LandType.SHEEPSBURG);
		Wolf wolf = new Wolf(l1);
		land.setWolf(wolf);
		l1.removeWolf();
		assertEquals(null, l1.getWolf());
		assertEquals(wolf, land.getWolf());
		
	}
	
	/**
	 * Test method growAll
	 */
	@Test
	public void growAllControll() {
		Ovine o1 = new Lamb();
		land.addOvine(o1);
		land.growAll();
		land.growAll();
		boolean temp = (land.contains("Sheep") || land.contains("Ram"));
		assertEquals(true, temp);
	}
	
	/**
	 * Test the method ovineNumber
	 */
	@Test 
	public void ovineNumberControll() {
		Ovine o1 = new Sheep();
		Ovine o2 = new Ram();
		Ovine o3 = new BlackSheep(land);
		land.addOvine(o1);
		land.addOvine(o2);
		assertEquals(2, land.numberOfOvines());
		land.addOvine(o3);
		assertEquals(4, land.numberOfOvines());
	}
	/**
	 * Test the method searchRoadforMoveAnimal
	 */
	@Test
	public void searchRoadControll(){
		Road r1 = new Road(1);
		land.addNeighboringRoad(r1);
		assertEquals(r1, land.searchRoadForMoveAnimal(1));
	}
	/**
	 * Test the method is allbusy
	 */
	@Test
	public void isAllBusyControll(){
		Road r1 = new Road(1);
		Road r2 = new Road(2);
		r1.setRoadState(RoadState.FENCED);
		r2.setRoadState(RoadState.SPECIALFENCED);
		assertEquals(true, land.isAllBusy());

	}
	/**
	 * Test the method checkTwoSheeps
	 */
	@Test
	public void containsTwoSheepsControll() {
		Ovine o1 = new Sheep();
		land.addOvine(o1);
		assertEquals(false, land.containsTwoSheeps());
		Ovine o2 = new Sheep();
		land.addOvine(o2);
		assertEquals(true, land.containsTwoSheeps());
	}
	
	@Test
	public void animalControll() {
		Ovine s1 = new Sheep();
		Ovine r1 = new Ram();
		Ovine l1 = new Lamb();
		Wolf w = new Wolf(land);
		land.addOvine(s1);
		assertEquals(false, land.containsSheepAndRam());
		land.addOvine(l1);
		land.addOvine(r1);
		assertEquals(false, land.containsWolf());
		land.setWolf(w);
		assertEquals(true, land.containsSheepAndRam());
		assertEquals(true, land.containsWolf());
		
	}
	
	@Test
	public void positionControll() {
		List<Point> points = new ArrayList<Point>();
		Point point =  new Point(12,13);
		land.setLandPoints(points);
		land.setBaricenter(point);
		assertEquals(point, land.getBaricenter());
		assertEquals(points, land.getLandPoints());
	}

}
