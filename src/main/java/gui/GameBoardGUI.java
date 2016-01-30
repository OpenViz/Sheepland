package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import utility.ServerLogger;
import view.ViewGUIInterface;
import model.ActionType;
import model.BlackSheep;
import model.EventType;
import model.GameInterface;
import model.Lamb;
import model.Land;
import model.MoveOvineStep;
import model.Ovine;
import model.Player;
import model.Ram;
import model.Road;
import model.RoadState;
import model.Sheep;
import model.Shepherd;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * JLayeredPane for the GameBoard of the Game. It cointains all the MovablObjects of the Game.
 */
public class GameBoardGUI extends JLayeredPane {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 700;
	private static final int WINDOW_TOP = 22;
	
	private static final int LAYER_HIDDEN_LABEL = 1;
	private static final int LAYER_GAMEBOARD_LABEL = 5;
	private static final int LAYER_FENCES_LABEL = 6;
	private static final int LAYER_VALIDLAND_LABEL = 7;
	private static final int LAYER_MOVABLE_OBJECTS = 10;
	private static final int LAYER_BLACKSHEEP_LABEL = 11;
	
	private BufferedImage gameBoardImage;
	private JLabel gameBoardLabel;
	private GameBoardMappedIdGUI gameBoardMappedId;
	
	private GameInterface gameInterface;
	private ViewGUIInterface viewGUIInterface;

	private List<OvineGUI> ovineGUIs;
	private List<ShepherdGUI> shepherdGUIs;
	private BlackSheepGUI blackSheepGUI;
	private WolfGUI wolfGUI;
	
	private List<ValidLandGUI> validLandGUIs;
	
	private MouseListenerGUI mouseListenerGUI;
	private UtilitiesForGUI utilitiesForGUI;
	
	/**
	 * Builder of the GameBoardGUI. It invokes a method to load the image of the GameBoard.
	 * It also invokes another method to initialize all the components that are in the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	public GameBoardGUI() throws RemoteException {
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_TOP));
		super.setBackground(new Color(35, 161, 246));
		
		this.loadResources();
		this.initComponents();
	}

	/**
	 * @param viewGUIInterface to set to the GameBoardGUI.
	 */
	public void setViewGUIInterface(ViewGUIInterface viewGUIInterface) {
		this.viewGUIInterface = viewGUIInterface;
	}

	/**
	 * @param utilitiesForGUI to set to the GameBoardGUI.
	 */
	public void setUtilitiesForGUI(UtilitiesForGUI utilitiesForGUI) {
		this.utilitiesForGUI = utilitiesForGUI;
	}

	/**
	 * @param gameInterface to set to the GameBoardGUI.
	 */
	public void setGameInterface(GameInterface gameInterface) {
		this.gameInterface = gameInterface;
	}

	/**
	 * @return the mouseListenerGUI of the GameBoardGUI.
	 */
	public MouseListenerGUI getMouseListenerGUI() {
		return mouseListenerGUI;
	}

	/**
	 * @param mouseListenerGUI to set to the GameBoardGUI.
	 */
	public void setMouseListenerGUI(MouseListenerGUI mouseListenerGUI) {
		this.mouseListenerGUI = mouseListenerGUI;
	}

	/**
	 * Initializes all the Objects that are on the GameBoardGUI and
	 * adds the mouseListenerGUI to the Listeners of the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	public void initialize() throws RemoteException {
		gameBoardMappedId = new GameBoardMappedIdGUI(gameInterface);
		gameBoardMappedId.setBounds(0, 0, gameBoardMappedId.getPreferredSize().width, gameBoardMappedId.getPreferredSize().height);
		this.add(gameBoardMappedId);
		this.setLayer(gameBoardMappedId, LAYER_HIDDEN_LABEL);
		
		mouseListenerGUI = new MouseListenerGUI(gameBoardMappedId);
		this.addMouseListener(mouseListenerGUI);
		this.addMouseMotionListener(mouseListenerGUI);
		mouseListenerGUI.setViewGUIInterface(viewGUIInterface);
		mouseListenerGUI.setShepherdGUIs(shepherdGUIs);
		mouseListenerGUI.setUtilitiesForGUI(utilitiesForGUI);
		
		this.initShepherdsGUIs();
		this.initOvinesGUIs();
		this.initWolfGUI();
		this.initValidLandsGUIs();
	}

	/**
	 * Updates some Objects that are on the GameBoardGUI basing on which
	 * Event or Actions is active in the Game when this method is called.
	 * @throws RemoteException if there are connection problems.
	 */
	public void updateGameboardGUI() throws RemoteException {
		if(utilitiesForGUI.getActiveEvent()!=null) {
			if(utilitiesForGUI.getActiveEvent().equals(EventType.SETSHEPHERDSTARTINGPOSITIONS)) {
				this.updateShepherdsGUIsOnSetSheperdsStartingPositions();
			} else if(utilitiesForGUI.getActiveEvent().equals(EventType.GROWTH)) {
				this.updateOvinesGUIsOnGrowth();
			} else if(utilitiesForGUI.getActiveEvent().equals(EventType.MOVEBLACKSHEEP)) {
				this.updateBlackSheepGUI();
			} else if(utilitiesForGUI.getActiveEvent().equals(EventType.MOVEWOLF)) {
				this.updateWolfGUI();
			}
		}
		
		if(utilitiesForGUI.getActiveAction()!=null) {
			if (utilitiesForGUI.getActiveAction().equals(ActionType.MOVESHEPHERD)) {
				this.updateShepherdsGUIsOnMoveShepherd();
				this.updateRoadsGUI();
			} else if(utilitiesForGUI.getActiveAction().equals(ActionType.MOVEOVINE)) {
				this.updateValidLandsGUIs();
				if(utilitiesForGUI.getMoveOvineStep()!=null 
						&& utilitiesForGUI.getMoveOvineStep().equals(MoveOvineStep.END)) {
						this.updateOvinesGUIsOnMoveOvine();
				}
			} else if((utilitiesForGUI.getActiveAction().equals(ActionType.COUPLING1))||(utilitiesForGUI.getActiveAction().equals(ActionType.COUPLING2))) {
				this.updateValidLandsGUIs();
				this.updateOvinesGUIsOnCoupling();
			} else if (utilitiesForGUI.getActiveAction().equals(ActionType.KILLOVINE)) {
				this.updateValidLandsGUIs();
				this.updateOvinesGUIsOnKillOvine();
			}
		}
	}

	/**
	 * Loads the map of SheepLand to set to the GameBoardGUI.
	 */
	private void loadResources() {
		try {
			gameBoardImage = ImageIO.read(new File("./res/SheeplandMapResized.jpg"));
		} catch (IOException e) {
			ServerLogger.printOnLogger("GameBoardGUI", e);
		}
	}
	
	/**
	 * Sets the GameBoardLabel with the image loaded and initializes all the
	 * List of the Objects that are on the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initComponents() throws RemoteException {
		gameBoardLabel = new JLabel(new ImageIcon(gameBoardImage));
		gameBoardLabel.setBounds(0, 0, gameBoardLabel.getPreferredSize().width, gameBoardLabel.getPreferredSize().height);
		this.add(gameBoardLabel);
		this.setLayer(gameBoardLabel, LAYER_GAMEBOARD_LABEL);
		
		this.ovineGUIs = new ArrayList<OvineGUI>();
		this.shepherdGUIs = new ArrayList<ShepherdGUI>();
		this.validLandGUIs = new ArrayList<ValidLandGUI>();
		this.gameInterface = null;
		this.utilitiesForGUI = null;
	}
	
	/**
	 * Initializes all the ShepherdsGUI at the start of the Game.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initShepherdsGUIs() throws RemoteException {
		for(Player p: gameInterface.getGameBoard().getPlayers()) {
			for(Shepherd s: p.getShepherds()) {
				ShepherdGUI shepherdGUI = new ShepherdGUI(s.getId(), s.getColor());
				this.add(shepherdGUI);
				this.shepherdGUIs.add(shepherdGUI);
				this.mouseListenerGUI.getSelectableObjects().add(shepherdGUI);
			}
		}
	}
	
	/**
	 * Initializes all the OvinesGUI at the start of the Game.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initOvinesGUIs() throws RemoteException {
		for(Land l: gameInterface.getGameBoard().getLands()) {
			for(Ovine o: l.getOvines()) {
				initOvineGUI(o);
			}
		}
	}
	
	/**
	 * Initializes an OvineGUI basing on the type of the Ovine passed from the Game.
	 * @param ovine which initialize the relative OvineGUI for the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initOvineGUI(Ovine ovine) throws RemoteException {
		if(ovine instanceof Sheep) {
			this.initSheepGUI((Sheep) ovine);
		} else if(ovine instanceof Ram) {
			this.initRamGUI((Ram) ovine);
		} else if(ovine instanceof Lamb) {
			if(((Lamb) ovine).getDayLife() == 0) {
				this.initLambGUI((Lamb) ovine);
			} else if(((Lamb) ovine).getDayLife() == 1) {
				this.initBigLambGUI((Lamb) ovine);
			}
		} else if(ovine instanceof BlackSheep) {
			this.initBlackSheepGUI((BlackSheep) ovine);
		}
	}
	
	/**
	 * Initializes a SheepGUI.
	 * @param sheep which initialize the relative SheepGUI for the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initSheepGUI(Sheep sheep) throws RemoteException {
		SheepGUI sheepGUI = new SheepGUI(sheep.getId());
		this.add(sheepGUI);
		this.ovineGUIs.add(sheepGUI);
		this.mouseListenerGUI.getSelectableObjects().add(sheepGUI);
		this.setLayer(sheepGUI, LAYER_MOVABLE_OBJECTS);
		sheepGUI.setLocation(utilitiesForGUI.centerJLabelOnPoint(sheepGUI, sheep.getOvinePosition()));
	}
	
	/**
	 * Initializes a RamGUI.
	 * @param ram which initialize the relative RamGUI of the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initRamGUI(Ram ram) throws RemoteException {
		RamGUI ramGUI = new RamGUI(ram.getId());
		this.add(ramGUI);
		this.ovineGUIs.add(ramGUI);
		this.mouseListenerGUI.getSelectableObjects().add(ramGUI);
		this.setLayer(ramGUI, LAYER_MOVABLE_OBJECTS);
		ramGUI.setLocation(utilitiesForGUI.centerJLabelOnPoint(ramGUI, ram.getOvinePosition()));
	}
	
	/**
	 * Initializes a LambGUI.
	 * @param lamb with 0 dayLife which initialize the relative LambGUI of the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initLambGUI(Lamb lamb) throws RemoteException {
		LambGUI lambGUI = new LambGUI(lamb.getId());
		this.add(lambGUI);
		this.ovineGUIs.add(lambGUI);
		this.mouseListenerGUI.getSelectableObjects().add(lambGUI);
		this.setLayer(lambGUI, LAYER_MOVABLE_OBJECTS);
		lambGUI.setLocation(utilitiesForGUI.centerJLabelOnPoint(lambGUI, lamb.getOvinePosition()));
	}
	
	/**
	 * Initializes a BigLambGUI.
	 * @param bigLamb with 1 dayLife which initialize the relative OvineGUI of the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initBigLambGUI(Lamb bigLamb) throws RemoteException {
		BigLambGUI bigLambGUI = new BigLambGUI(bigLamb.getId());
		this.add(bigLambGUI);
		this.ovineGUIs.add(bigLambGUI);
		this.mouseListenerGUI.getSelectableObjects().add(bigLambGUI);
		this.setLayer(bigLambGUI, LAYER_MOVABLE_OBJECTS);
		bigLambGUI.setLocation(utilitiesForGUI.centerJLabelOnPoint(bigLambGUI, bigLamb.getOvinePosition()));
	}
	
	/**
	 * Initializes a BlckSheepGUI.
	 * @param blackSheep which initialize the relative BlackSheepGUI of the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initBlackSheepGUI(BlackSheep blackSheep) throws RemoteException {
		this.blackSheepGUI= new BlackSheepGUI(blackSheep.getId());
		this.add(blackSheepGUI);
		this.ovineGUIs.add(blackSheepGUI);
		this.mouseListenerGUI.getSelectableObjects().add(blackSheepGUI);
		this.setLayer(blackSheepGUI, LAYER_BLACKSHEEP_LABEL);
		blackSheepGUI.setLocation(utilitiesForGUI.centerJLabelOnPoint(blackSheepGUI, blackSheep.getOvinePosition()));
	}

	/**
	 * Initializes a WolfGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initWolfGUI() throws RemoteException {
		this.wolfGUI = new WolfGUI();
		this.add(wolfGUI);
		this.setLayer(wolfGUI, LAYER_MOVABLE_OBJECTS);
		wolfGUI.setLocation(utilitiesForGUI.centerJLabelOnPoint(wolfGUI, viewGUIInterface.getGameInterface().getGameBoard().getWolf().getWolfPosition()));
	}
	
	/**
	 * Initializes the List of ValidLandGUIs.
	 * @throws RemoteException if there are connection problems.
	 */
	private void initValidLandsGUIs() throws RemoteException {
		for(Land l: gameInterface.getGameBoard().getLands()) {
			this.initValidLandGUI(l.getId());
		}
	}

	/**
	 * Initializes a ValidLandGUI basing the image for the JLabel on its id.
	 * @param landId to pass to the builder of the ValindLandGUI which will load a different
	 * image basing on that.
	 */
	private void initValidLandGUI(int landId) {
		ValidLandGUI validLandGUI = new ValidLandGUI(landId);
		validLandGUI.setBounds(0, 0, validLandGUI.getPreferredSize().width, validLandGUI.getPreferredSize().height);
		this.add(validLandGUI);
		this.validLandGUIs.add(validLandGUI);
		this.setLayer(validLandGUI, LAYER_HIDDEN_LABEL);	
	}

	/**
	 * Method used to update the ShepherdsGUI position Points after that are
	 * changed during the SETSHEPHERDSTARTINGPOSITIONS Event.
	 * @throws RemoteException if there are connection problems.
	 */
	private void updateShepherdsGUIsOnSetSheperdsStartingPositions() throws RemoteException {
		for(Player p: viewGUIInterface.getGameInterface().getGameBoard().getPlayers()) {
			for(Shepherd s: p.getShepherds()) {
				for(ShepherdGUI sGUI: this.shepherdGUIs) {
					if((sGUI.getShepherdId() == s.getId())&&(sGUI.getLocation()!=s.getShepherdPosition())&&(s.isPlaced())) {
						this.setLayer(sGUI, LAYER_MOVABLE_OBJECTS);
						sGUI.setLocation(s.getShepherdPosition());						
					}
				}
			}
		}
	}
	
	/**
	 * Method used to update the ShepherdsGUIs position Points after that are
	 * changed during the MOVESHEPHERD Action.
	 * @throws RemoteException if there are connection problems.
	 */
	private void updateShepherdsGUIsOnMoveShepherd() throws RemoteException {
		for(Player p: viewGUIInterface.getGameInterface().getGameBoard().getPlayers()) {
			for(Shepherd s: p.getShepherds()) {
				for(ShepherdGUI sGUI: this.shepherdGUIs) {
					if((sGUI.getShepherdId() == s.getId())&&(sGUI.getLocation()!=s.getShepherdPosition())&&(s.isPlaced())) {
						sGUI.moveTo(s.getShepherdPosition(), 2000);					
					}
				}
			}
		}
	}
	
	/**
	 * Method used to update the OvineGUIs position Points after that are
	 * changed during the MOVEOVINE Action.
	 * @throws RemoteException if there are connection problems.
	 */
	private void updateOvinesGUIsOnMoveOvine() throws RemoteException {
		for(OvineGUI oGUI: this.ovineGUIs) {
			if(oGUI.getOvineId() == viewGUIInterface.getGameInterface().getGameInfo().getMovedOvineId()&&(!oGUI.isSelected())) {
				this.moveOvineGUI(oGUI, viewGUIInterface.getGameInterface().getGameInfo().getMovedOvineId());
			}
		}
	}
	
	/**
	 * Method used to update the GameBoardGUI with a new OvineGUI after that is
	 * created during the COUPLING1 or COUPLING2 Action.
	 * @throws RemoteException if there are connection problems.
	 */
	private void updateOvinesGUIsOnCoupling() throws RemoteException {
		for(Land l: viewGUIInterface.getGameInterface().getGameBoard().getLands()) {
			for(Ovine o: l.getOvines()) {
				if(o!=null && this.isNewOvine(o)) {
						this.initOvineGUI(o);
				}
			}
		}	
	}
	
	/**
	 * Method used to update the GameBoardGUI removing an OvineGUI after that is
	 * killed during the KILLOVINE Action.
	 * @throws RemoteException if there are connection problems.
	 */
	private void updateOvinesGUIsOnKillOvine() throws RemoteException {
		OvineGUI ovineGUIKilled = null;
		for(OvineGUI oGUI: this.ovineGUIs) {
			if(oGUI.getOvineId() == viewGUIInterface.getGameInterface().getGameInfo().getKilledOvineId()) {
				ovineGUIKilled = oGUI;
				break;
			}
		}
		if(ovineGUIKilled!=null) {
			this.killOvineGUI(ovineGUIKilled);
		}
	}
	
	/**
	 * Method used to update the OvineGUIs positioned on the GameBOardGUI after that are
	 * grown during the GROWTH Event.
	 * @throws RemoteException if there are connection problems.
	 */
	private void updateOvinesGUIsOnGrowth() throws RemoteException {
		List<Ovine> growedOvinesTemp = new ArrayList<Ovine>();
		List<OvineGUI> ovineGUIsToRemoveTemp = new ArrayList<OvineGUI>();
		for(Land l: viewGUIInterface.getGameInterface().getGameBoard().getLands()) {
			for(Ovine o: l.getOvines()) {
				if(o!=null) {
					for(OvineGUI oGUI: this.ovineGUIs) {
						if((oGUI.getOvineId() == o.getId()) && this.isGrowedOvine(o, oGUI) ) {
								growedOvinesTemp.add(o);
								ovineGUIsToRemoveTemp.add(oGUI);
						}
					}	
				}
			}
		}
		for (int i = 0; i < ovineGUIsToRemoveTemp.size(); i++) {
			this.removeOvineGUI(ovineGUIsToRemoveTemp.get(i));
		}
		for(int i = 0; i < growedOvinesTemp.size(); i++) {
			this.growOvineGUI(growedOvinesTemp.get(i));
		}
	}
	
	/**
	 * Method used to update the BlackSheepGUI position Point after that is
	 * changed during the MOVEBLACKSHEEP Event.
	 * @throws RemoteException if there are connection problems.
	 */
	private void updateBlackSheepGUI() throws RemoteException {
		if(blackSheepGUI.getLocation()!=viewGUIInterface.getGameInterface().getGameBoard().getBlackSheep().getOvinePosition()) {
			blackSheepGUI.moveTo(utilitiesForGUI.centerJLabelOnPoint(blackSheepGUI, viewGUIInterface.getGameInterface().getGameBoard().getBlackSheep().getOvinePosition()), 3000);
		}
	}
	
	/**
	 * Method used to update the WolfGUI position Point after that is
	 * changed during the MOVEWOLF Event.
	 * It also updates the GameBoardGUI removing an OvineGUI after that is
	 * ate by the Wolf.
	 * @throws RemoteException if there are connection problems.
	 */
	private void updateWolfGUI() throws RemoteException {
		OvineGUI ovineGUIEated = null;
		if(wolfGUI.getLocation()!=viewGUIInterface.getGameInterface().getGameBoard().getWolf().getWolfPosition()) {
			wolfGUI.moveTo(utilitiesForGUI.centerJLabelOnPoint(wolfGUI, viewGUIInterface.getGameInterface().getGameBoard().getWolf().getWolfPosition()), 2000);
		}
		for(OvineGUI oGUI: this.ovineGUIs) {
			if(oGUI.getOvineId() == viewGUIInterface.getGameInterface().getGameInfo().getEatedOvineId()) {
				ovineGUIEated = oGUI;
				break;
			}
		}
		if(ovineGUIEated!=null) {
			this.removeOvineGUI(ovineGUIEated);
		}
	}
	
	/**
	 * Method used to update the RoadsGUIs state image after that are
	 * changed during the MOVESHEPHERD Action.
	 * @throws RemoteException if there are connection problems.
	 */
	private void updateRoadsGUI() throws RemoteException {
		for(RoadGUI rGUI: this.gameBoardMappedId.getRoadGUIs()) {
			for(Road r: viewGUIInterface.getGameInterface().getGameBoard().getRoads()) {
				if((rGUI.getRoadId() == r.getId())&&(r.getRoadState().equals(RoadState.FENCED))&&(!rGUI.isFenced())) {
					rGUI.setFencedGUI();
					this.add(rGUI);
					this.setLayer(rGUI, LAYER_FENCES_LABEL);
				} else if ((rGUI.getRoadId() == r.getId())&&(r.getRoadState().equals(RoadState.SPECIALFENCED))&&(!rGUI.isSpecialFenced())) {
					rGUI.setSpecialFencedGUI();
					this.add(rGUI);
					this.setLayer(rGUI, LAYER_FENCES_LABEL);
				}
			}
		}
	}
	
	/**
	 * Method used to update the ValidLandsGUIs that are visible on the screen
	 * after the List of validLandIds is changed during some Actions.
	 * @throws RemoteException if there are connection problems.
	 */
	private void updateValidLandsGUIs() throws RemoteException {
		for(ValidLandGUI validLandGUI: this.validLandGUIs) {
			if(validLandGUI.isVisible()) {
				this.setLayer(validLandGUI, LAYER_HIDDEN_LABEL);
			}
			for(int validLandId: utilitiesForGUI.getValidLandsIds()) {
				if(validLandGUI.getValidLandGUIId() == validLandId) {
					this.setLayer(validLandGUI, LAYER_VALIDLAND_LABEL);
					validLandGUI.setVisible(true);
					break;
				} 
			}
		}
	}
	
	/**
	 * Method used to check if an ovine of the Game is new for the GameBoardGUI.
	 * @param ovine to check if it's new.
	 * @return true if doesn't yet exists an OvineGUI of that Ovine on the GameBoardGUI,
	 * else returns false.
	 */
	private boolean isNewOvine(Ovine ovine) {
		for (OvineGUI o: ovineGUIs) {
			if(o.getOvineId() == ovine.getId()) {
				return false;
			}
		}
		if(ovine instanceof BlackSheep) {
			return false;
		}
		return true;
	}

	/**
	 * Method used to check if a grown ovine of the Game has an OvineGUI
	 * that isn't yet grown.
	 * @param ovine to check if it's grown.
	 * @param ovineGUI to compare to the Ovine grown.
	 * @return true if the OvineGUI isn't yet grown, else returns false.
	 */
	private boolean isGrowedOvine(Ovine ovine, OvineGUI ovineGUI) {
		if(ovineGUI instanceof LambGUI) {
			if(((Lamb) ovine).getDayLife() == 1) {
				return true;
			}
		} else if(ovineGUI instanceof BigLambGUI 
				&& (ovine instanceof Sheep)||(ovine instanceof Ram)) {
				return true;
		}
		return false;
	}
	
	/**
	 * Method used to move an OvineGUI in another Point.
	 * @param ovineGUIToMove in another Point.
	 * @param movedOvineId of the Ovine which OvineGUI has to be moved.
	 * @throws RemoteException if there are connection problems.
	 */
	private void moveOvineGUI(OvineGUI ovineGUIToMove, int movedOvineId) throws RemoteException {
		ovineGUIToMove.moveTo(utilitiesForGUI.centerJLabelOnPoint(ovineGUIToMove, viewGUIInterface.getGameInterface().getGameBoard().getOvineOnId(movedOvineId).getOvinePosition()), 2000);
	}

	/**
	 * Method used to kill an OvineGUI.
	 * @param ovineGUIToRemove that is death.
	 */
	private void killOvineGUI(OvineGUI ovineGUIToRemove) {
		this.removeOvineGUI(ovineGUIToRemove);
	}
	
	/**
	 * Method used to definitively remove an OvineGUI from the GameBoardGUI.
	 * @param ovineGUI to remove to the GameBoardGUI.
	 */
	private void removeOvineGUI(OvineGUI ovineGUI) {
		this.remove(ovineGUI);
		this.ovineGUIs.remove(ovineGUI);
		this.mouseListenerGUI.getSelectableObjects().remove(ovineGUI);
		this.revalidate();
		this.repaint();
	}

	/**
	 * Method used to grown an OvineGUI.
	 * @param ovine which OvineGUI has to be grown.
	 * @throws RemoteException if there are connection problems.
	 */
	private void growOvineGUI(Ovine ovine) throws RemoteException {
		this.initOvineGUI(ovine);
	}
}
