package guimodelsupport;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Random;

import javax.imageio.ImageIO;

import utility.ServerLogger;
import model.GameBoard;
import model.Land;
import model.Ovine;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class that is needed by the model to sets some variables that are used by the GUI
 * like the positions Point of his Objects and the Lists of Points that define the area of
 * the Lands in the GUI.
 */
public class GameGUISupporter implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private GameBoard gameBoard;
	
	private transient BufferedImage gameBoardColorImage;
	
	private int width;
	private int height;
	//Matrix of the colors of the image's pixels indexed by pixels's coordinates.
	private int[][] coordinatesColor;
	
	/**
	 * Builder of the GameGUISupporter.
	 * Sets its variables width and height as the width and height of the image loaded.
	 * @param gameBoard of the Game.
	 * @throws RemoteException if there are connection problems.
	 */
	public GameGUISupporter(GameBoard gameBoard) throws RemoteException {
		this.gameBoard = gameBoard;
		this.loadResources();
		this.width = gameBoardColorImage.getWidth();
		this.height = gameBoardColorImage.getHeight();
		this.coordinatesColor = new int[width][height];
		this.generateCoordinatesColor();
		this.landsColorsGUISetter();
		this.mapPointsOfGUIOnLands();
		this.generateBaricentersOfGUIForLands();
		this.roadsPositionPointSetter();
		this.initObjectsPositions();
		}
	
	/**
	 * Loads an image of the GameBoard that uses a different color for every Lands
	 * of the GameBoard.
	 */
	private void loadResources() {
		try {
			gameBoardColorImage = ImageIO.read(new File("./res/SheeplandColoredMapResized.jpg"));
		} catch (IOException e) {
			ServerLogger.printOnLogger("LandsPointGUIGenerator", e);
		}
	}
	
	/**
	 * Class that takes the gameBoardColorImage loaded and sets every color of their pixels
	 * in a matrix indexed by the coordinates of the pixels.
	 */
	private void generateCoordinatesColor() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				coordinatesColor[x][y] = gameBoardColorImage.getRGB(x, y);
			}
		}
	}

	/**
	 * Method that initializes all the color of the Lands of the Game with
	 * some predefined values related to the color of the Land on the image loaded.
	 */
	private void landsColorsGUISetter() {
		gameBoard.getLands().get(0).setLandColorGUI(-11789160);
		gameBoard.getLands().get(1).setLandColorGUI(-179969);
		gameBoard.getLands().get(2).setLandColorGUI(-3066926);
		gameBoard.getLands().get(3).setLandColorGUI(-5494099);
		gameBoard.getLands().get(4).setLandColorGUI(-55295);
		gameBoard.getLands().get(5).setLandColorGUI(-3794175);
		gameBoard.getLands().get(6).setLandColorGUI(-6613760);
		gameBoard.getLands().get(7).setLandColorGUI(-5398784);
		gameBoard.getLands().get(8).setLandColorGUI(-2636031);
		gameBoard.getLands().get(9).setLandColorGUI(-5376);
		gameBoard.getLands().get(10).setLandColorGUI(-16624744);
		gameBoard.getLands().get(11).setLandColorGUI(-16617267);
		gameBoard.getLands().get(12).setLandColorGUI(-16676609);
		gameBoard.getLands().get(13).setLandColorGUI(-43520);
		gameBoard.getLands().get(14).setLandColorGUI(-1816063);
		gameBoard.getLands().get(15).setLandColorGUI(-4571904);
		gameBoard.getLands().get(16).setLandColorGUI(-16738030);
		gameBoard.getLands().get(17).setLandColorGUI(-16726504);
		gameBoard.getLands().get(18).setLandColorGUI(-16451042);
	}
	
	/**
	 * Method used to generate for every Lands of the GameBoard that List of the LandPoints that
	 * defines its area of Points in the GUI, using the coordinates color matrix generated before.
	 * For every color saved in the matrix sets its x and y which is indexed on the matrix 
	 * as a PointPosition of the Land with that color.
	 * @throws RemoteException if there are connection problems.
	 */
	private void mapPointsOfGUIOnLands() throws RemoteException {
		for(Land l: gameBoard.getLands()) {
			for (int y = 0; y < this.height; y++) {	
				for (int x = 0; x < this.width; x++) {
					if(this.coordinatesColor[x][y] == l.getLandColorGUI()) {
						l.getLandPoints().add(new Point(x, y)); 
					}
				}
			}
		}
	}

	/**
	 * Method used to generate the baricenter of all Lands of the GameBoard.
	 */
	private void generateBaricentersOfGUIForLands() {
		for(Land l: gameBoard.getLands()) {
			this.generateBaricenterOfLand(l);
		}
	}
	
	/**
	 * Method that takes a Land as input and generates its baricenter and set it to the Land.
	 * @param land which generate the baricenter Point.
	 */
	private void generateBaricenterOfLand(Land land) {
		int xBaricenter = 0;
		int yBaricenter = 0;
		for(Point p: land.getLandPoints()) {
			xBaricenter += p.getX();
			yBaricenter += p.getY();
		}
		xBaricenter/=land.getLandPoints().size();
		yBaricenter/=land.getLandPoints().size();
		land.setBaricenter(new Point(xBaricenter, yBaricenter));
	}
	
	/**
	 * Method that initializes all the position Points of the Road of the Game with
	 * some predefined values.
	 */
	private void roadsPositionPointSetter() {
		gameBoard.getRoads().get(0).setPointOfRoad(346, 118);
		gameBoard.getRoads().get(1).setPointOfRoad(79, 250);
		gameBoard.getRoads().get(9).setPointOfRoad(122, 173);
		gameBoard.getRoads().get(18).setPointOfRoad(253, 111);
		gameBoard.getRoads().get(11).setPointOfRoad(412, 169);
		gameBoard.getRoads().get(19).setPointOfRoad(152, 221);
		gameBoard.getRoads().get(25).setPointOfRoad(200, 201);
		gameBoard.getRoads().get(37).setPointOfRoad(248, 179);
		gameBoard.getRoads().get(10).setPointOfRoad(291, 165);
		gameBoard.getRoads().get(20).setPointOfRoad(324, 192);
		gameBoard.getRoads().get(32).setPointOfRoad(355, 216);
		gameBoard.getRoads().get(21).setPointOfRoad(394, 233);
		gameBoard.getRoads().get(3).setPointOfRoad(444, 249);
		gameBoard.getRoads().get(12).setPointOfRoad(150, 279);
		gameBoard.getRoads().get(31).setPointOfRoad(237, 230);
		gameBoard.getRoads().get(27).setPointOfRoad(113, 377);
		gameBoard.getRoads().get(22).setPointOfRoad(151, 345);
		gameBoard.getRoads().get(4).setPointOfRoad(185, 319);
		gameBoard.getRoads().get(38).setPointOfRoad(219, 290);
		gameBoard.getRoads().get(2).setPointOfRoad(259, 263);
		gameBoard.getRoads().get(26).setPointOfRoad(309, 240);
		gameBoard.getRoads().get(33).setPointOfRoad(221, 340);
		gameBoard.getRoads().get(13).setPointOfRoad(296, 288);
		gameBoard.getRoads().get(5).setPointOfRoad(332, 319);
		gameBoard.getRoads().get(39).setPointOfRoad(386, 285);
		gameBoard.getRoads().get(28).setPointOfRoad(264, 364);
		gameBoard.getRoads().get(23).setPointOfRoad(300, 340);
		gameBoard.getRoads().get(3).setPointOfRoad(444, 249);
		gameBoard.getRoads().get(15).setPointOfRoad(156, 412);
		gameBoard.getRoads().get(40).setPointOfRoad(228, 411);
		gameBoard.getRoads().get(34).setPointOfRoad(300, 398);
		gameBoard.getRoads().get(14).setPointOfRoad(386, 337);
		gameBoard.getRoads().get(6).setPointOfRoad(120, 519);
		gameBoard.getRoads().get(30).setPointOfRoad(188, 458);
		gameBoard.getRoads().get(24).setPointOfRoad(225, 479);
		gameBoard.getRoads().get(7).setPointOfRoad(262, 500);
		gameBoard.getRoads().get(16).setPointOfRoad(299, 472);
		gameBoard.getRoads().get(41).setPointOfRoad(328, 447);
		gameBoard.getRoads().get(29).setPointOfRoad(369, 404);
		gameBoard.getRoads().get(35).setPointOfRoad(431, 379);
		gameBoard.getRoads().get(17).setPointOfRoad(186, 575);
		gameBoard.getRoads().get(36).setPointOfRoad(297, 551);
		gameBoard.getRoads().get(8).setPointOfRoad(375, 491);
	}

	/**
	 * Method used to initialize the position Points of all
	 * the Ovines and the Wolf of the GameBoard for the GUI. 
	 * @throws RemoteException if there are connection problems.
	 */
	private void initObjectsPositions() throws RemoteException {
		this.initOvinesPositions();
		this.initWolfPosition();
	}

	/**
	 * Method used to initialize the position Points of all
	 * the Ovines of the GameBoard for the GUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initOvinesPositions() throws RemoteException {
		for(Land l: gameBoard.getLands()) {
			for(Ovine o: l.getOvines()) {
				initOvinePosition(o, l);
			}
		}
	}

	/**
	 * Method used to initialize the position Points
	 * of an Ovine of the GameBoard for the GUI.
	 * It generates random Points to set as Ovine position until finds one that isn't
	 * too far to the baricenter of the Land where the Ovine is in and set it as the Ovine
	 * position Point.
	 * @param ovine to generate the position Point.
	 * @param land where Ovine is in.
	 */
	private void initOvinePosition(Ovine ovine, Land land) {
		Point tempPosition = new Point();
		do {
			tempPosition.setLocation(this.getRandomPointInALand(land));
		} while(isTooFarFromLandBaricenter(tempPosition, land));
		ovine.setOvinePosition(tempPosition);
	}


	/**
	 * Extract a random Point from the landPoints area of the Land taken as parameter.
	 * @param land where extract a random point.
	 * @return the Point extracted.
	 */
	private Point getRandomPointInALand(Land land) {
		Random rand = new Random();
		return land.getLandPoints().get(rand.nextInt(land.getLandPoints().size()));
	}


	/**
	 * Method that checks if a specific Point is too far from the baricenter of a specific Land,
	 * inserted as parameter.
	 * @param position Point to check distance from baricenter.
	 * @param land to get the baricenter.
	 * @return true if the Point is too far from the baricenter of the Land.
	 */
	private boolean isTooFarFromLandBaricenter(Point position, Land land) {
		return 1 < ((int) Math.sqrt(Math.pow(position.getX() - land.getBaricenter().getX(),2.0) + Math.pow(position.getY() - land.getBaricenter().getY(),2.0)));
	}


	/**
	 * Method that initializes the Wolf position Point for the GUI.
	 */
	private void initWolfPosition() {
		gameBoard.getWolf().setWolfPosition(gameBoard.getWolf().getLandPosition().getRandomLandPoint());
	}
}
