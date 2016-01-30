package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import utility.ServerLogger;
import model.GameInterface;
import model.Land;
import model.Road;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class of the GUI that initializes all the LandGUIs and the RoadGUIs of the GameBoardGUI
 * and relates them to the Roads and Lands of the Game through their id.
 */
public class GameBoardMappedIdGUI extends JLabel {
	private static final long serialVersionUID = 1L;

	private BufferedImage gameBoardColorImage;
	
	private ColorTrackedImageGUI colorTrackedGameBoard;
	
	private GameInterface gameInterface;
	
	private List<LandGUI> landGUIs;
	private List<RoadGUI> roadGUIs;
	
	/**
	 * Builder of the GameBoardMappedIdGUI.
	 * @param gameInterface used to get the Objects of the Game.
	 * @throws RemoteException if there are connection problems.
	 */
	public GameBoardMappedIdGUI(GameInterface gameInterface) throws RemoteException {
		this.loadResources();
		this.gameInterface = gameInterface;
		this.initComponents();
		this.setIcon(new ImageIcon(gameBoardColorImage));
	}
	
	/**
	 * @return the ColorTrackedImageGUI of the GameBoardMappedIdGUI.
	 */
	public ColorTrackedImageGUI getColorTrackedGameBoard() {
		return colorTrackedGameBoard;
	}
	
	/**
	 * Method used to get the Id of a LandGUI inserting the color of that LAndGUI as parameter.
	 * @param color of the LandGUI which get the id.
	 * @return the id of that LandGUI.
	 */
	public int getIdOnColor(int color) {
		for(LandGUI l: landGUIs) {
			if(l.getLandColor() == color) {
				return l.getLandId();
			}
		}
		return -1;	
	}

	/**
	 * @return the List of the RoadGUIs of the GameBoardMappedIdGUI.
	 */
	public List<RoadGUI> getRoadGUIs() {
		return roadGUIs;
	}

	/**
	 * @param roadGUIs to set to the GameBoardMappedIdGUI.
	 */
	public void setRoadGUIs(List<RoadGUI> roadGUIs) {
		this.roadGUIs = roadGUIs;
	}
	
	/**
	 * Method used to get a LandGUI inserting the id of that LAndGUI as parameter.
	 * @param id of the LandGUI to get.
	 * @return the LandGUI with that id if exists, else returns null.
	 */
	public LandGUI getLandGUIOnId(int id) {
		for(LandGUI l: landGUIs) {
			if(l.getLandId() == id) {
				return l;
			}
		}
		return null;
	}

	/**
	 * @return the gameInterface.
	 */
	public GameInterface getGameInterface() {
		return gameInterface;
	}

	/**
	 * @param gameInterface to set to the GameBOardMappedId.
	 */
	public void setGameInterface(GameInterface gameInterface) {
		this.gameInterface = gameInterface;
	}

	/**
	 * Loads an image of the GameBoard that uses a different color for every Lands
	 * of the GameBoard.
	 */
	private void loadResources() {
		try {
			gameBoardColorImage = ImageIO.read(new File("./res/SheeplandColoredMapResized.jpg"));
		} catch (IOException e) {
			ServerLogger.printOnLogger("GameBoardMappedIdGUI", e);
		}
	}

	/**
	 * Initializes every components of the GameBoardMappedIdGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initComponents() throws RemoteException {
		colorTrackedGameBoard = new ColorTrackedImageGUI(gameBoardColorImage);
		landGUIs = new ArrayList<LandGUI>();
		roadGUIs = new ArrayList<RoadGUI>();
		
		for (Land l: gameInterface.getGameBoard().getLands()) {
			landGUIs.add(new LandGUI(l.getId(), l.getLandColorGUI(), l.getLandPoints()));
		}
		
		for (Road r: gameInterface.getGameBoard().getRoads()) {
			roadGUIs.add(new RoadGUI(r.getId(), r.getRoadPosition()));
		}
	}
}
