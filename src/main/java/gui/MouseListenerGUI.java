package gui;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import model.ActionType;
import model.EventType;
import model.MoveOvineStep;
import model.Ovine;
import utility.ServerLogger;
import view.ViewGUIInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * This class is a Listener of the Mouse and executes some methods in relation
 * to the current event about the mouse and to the current action or event that is 
 * happening in the Game.
 */
public class MouseListenerGUI implements MouseListener, MouseMotionListener {
	
	private SelectableObjectGUI selectedObject;

	private List<SelectableObjectGUI> selectableObjects;
	
	private GameBoardMappedIdGUI gameBoardMappedId;
	
	private ViewGUIInterface viewGUIInterface;
	
	private List<ShepherdGUI> shepherdGUIs;
	
	private UtilitiesForGUI utilitiesForGUI;
	
	/**
	 * Builder of the MouseListenerGUI. Initialize the selected Object of the mouse to null
	 * and takes the gameBoardMappedId as parameter.
	 * @param gameBoardMappedId used to relate a color that identify an id to an area of the GameBoard image.
	 */
	public MouseListenerGUI(GameBoardMappedIdGUI gameBoardMappedId) {
		selectedObject = null;
		this.selectableObjects = new ArrayList<SelectableObjectGUI>();
		this.gameBoardMappedId = gameBoardMappedId;
		this.viewGUIInterface = null;
		this.utilitiesForGUI = null;
	}

	/**
	 * Method that is executed every time the mouse is clicked.
	 * It invokes other methods according to the active Event or Action of the Game and
	 * pass to it the MouseEvent e useful to get the mouse position at the moment of the click
	 * on the GameBoardGUI.
	 */
	public void mouseClicked(MouseEvent e) {
		try {
			if(utilitiesForGUI.getActiveAction() != null) {
				if(utilitiesForGUI.getActiveAction().equals(ActionType.MOVESHEPHERD)) {
					this.mouseClickedForMoveShepherd(e);
				} else if(utilitiesForGUI.getActiveAction().equals(ActionType.MOVEOVINE)) {
					this.mouseClickedForMoveOvine(e);
				} else if((utilitiesForGUI.getActiveAction().equals(ActionType.COUPLING1))||(utilitiesForGUI.getActiveAction().equals(ActionType.COUPLING2))) {
					this.mouseClickedForCoupling(e);
				} else if(utilitiesForGUI.getActiveAction().equals(ActionType.KILLOVINE)) {
					this.mouseClickedForKillOvine(e);
				} 
			}
			if(utilitiesForGUI.getActiveEvent() != null) {
				if(utilitiesForGUI.getActiveEvent().equals(EventType.SETSHEPHERDSTARTINGPOSITIONS)) {
					this.mouseClickedForMoveShepherd(e);
				} else if(utilitiesForGUI.getActiveEvent().equals(EventType.CHOOSEACTIVESHEPHERD)) {
					this.mouseClickedForChooseShepherd(e);
				}
			}
		} catch (RemoteException e3) {
			ServerLogger.printOnLogger("MouseLIstenerGUI", e3);
		}		
	}
	
	/**
	 * Method that is executed every time the mouse is moved.
	 * If any Selectable Object is selected, that will follow the mouse invoking the method moveTo.
	 */
	public void mouseMoved(MouseEvent e) {
		if(selectedObject != null) {
			selectedObject.moveTo(utilitiesForGUI.centerJLabelOnPoint(selectedObject, e.getPoint()), 1);
		}
	}

	/**
	 * @param utilitiesForGUI to set to the MouseListenerGUI.
	 */
	public void setUtilitiesForGUI(UtilitiesForGUI utilitiesForGUI) {
		this.utilitiesForGUI = utilitiesForGUI;
	}

	/**
	 * @return the selectableObjects of the MouseListenerGUI.
	 */
	public List<SelectableObjectGUI> getSelectableObjects() {
		return selectableObjects;
	}

	/**
	 * @param selectableObjects to set to the MouseListenerGUI.
	 */
	public void setSelectableObjects(List<SelectableObjectGUI> selectableObjects) {
		this.selectableObjects = selectableObjects;
	}
	
	/**
	 * @return the selectedObject of the MouseListenerGUI.
	 */
	public SelectableObjectGUI getSelectedObject() {
		return selectedObject;
	}

	/**
	 * @param selectedObject to set to the MouseListenerGUI.
	 */
	public void setSelectedObject(SelectableObjectGUI selectedObject) {
		this.selectedObject = selectedObject;
	}

	/**
	 * @param viewGUIInterface to set to the MouseListenerGUI.
	 */
	public void setViewGUIInterface(ViewGUIInterface viewGUIInterface) {
		this.viewGUIInterface = viewGUIInterface;
	}

	/**
	 * @param shepherdGUIs to set to the MouseListenerGUI.
	 */
	public void setShepherdGUIs(List<ShepherdGUI> shepherdGUIs) {
		this.shepherdGUIs = shepherdGUIs;
	}

	/**
	 *  Method that is executed every time the mouse is dragging.
	 */
	public void mouseDragged(MouseEvent e) {
		return ;
	}

	/**
	 *  Method that is executed every time the mouse is pressed.
	 */
	public void mousePressed(MouseEvent e) {
		return ;
	}

	/**
	 *  Method that is executed every time the mouse is released.
	 */
	public void mouseReleased(MouseEvent e) {
		return ;
	}

	/**
	 *  Method that is executed every time the mouse enters.
	 */
	public void mouseEntered(MouseEvent e) {
		return ;
	}

	/**
	 *  Method that is executed every time the mouse exits.
	 */
	public void mouseExited(MouseEvent e) {
		return ;
	}

	/**
	 * Method that is executed every time the mouse is clicked and the active Event
	 * of the Game is CHOOSESHEPHERD.
	 * It sends to the Server a String of the Id of the clicked ShepherdGUI.
	 * @param e MouseEvent used to get the mouse position at the moment of the click
	 * on the GameBoardGUI.
	 */
	private void mouseClickedForChooseShepherd(MouseEvent e) {
		for(ShepherdGUI s : shepherdGUIs) {
			if(s.getBounds().contains(e.getPoint())){
				int shepherdId = s.getShepherdId();
				try {
					viewGUIInterface.setSenderString(""+shepherdId);
				} catch (RemoteException e2) {
					ServerLogger.printOnLogger("MouseLIstenerGUI", e2);
				}
			}
		}
	}

	/**
	 * Method that is executed every time the mouse is clicked and the active Event
	 * of the Game is SETSHPHERDSTARTINGPOSITION or if the active Action is MOVESHEPHERD.
	 * It sends to the Server a String of the Id of the clicked RoadGUI.
	 * @param e MouseEvent used to get the mouse position at the moment of the click
	 * on the GameBoardGUI.
	 */
	private void mouseClickedForMoveShepherd(MouseEvent e) {
		for(RoadGUI r : gameBoardMappedId.getRoadGUIs()) {
			if(r.getBounds().contains(e.getPoint())){
				int roadId = r.getRoadId();
				try {
					viewGUIInterface.setSenderString(""+roadId);
				} catch (RemoteException e2) {
					ServerLogger.printOnLogger("MouseLIstenerGUI", e2);
				}
			}
		}
	}

	/**
	 * Method that is executed every time the mouse is clicked and the active Action
	 * of the Game is MOVEOVINE.
	 * If the active step of the moveOvine is DRAG and there isn't a selected Object
	 * it invokes the method dragAction(e), instead if the active step of the moveOvine is DROP
	 * and the mouse is clicked on a LandGUI that has a valid Land id, it invokes the method DROP.
	 * @param e MouseEvent used to get the mouse position at the moment of the click
	 * on the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void mouseClickedForMoveOvine(MouseEvent e) throws RemoteException {
		if(utilitiesForGUI.getMoveOvineStep()!=null) {
			if((selectedObject == null)&&(utilitiesForGUI.getMoveOvineStep().equals(MoveOvineStep.DRAG))) {
				try {
					dragAction(e);
				} catch (RemoteException e1) {
					ServerLogger.printOnLogger("MouseLIstenerGUI", e1);
				}
			} else {
				try {
					if(utilitiesForGUI.getMoveOvineStep().equals(MoveOvineStep.DROP) 
							&& isValidLand(this.getClickedLandId(e.getPoint())) ){
						dropAction(e);
					}
				} catch (RemoteException e1) {
					ServerLogger.printOnLogger("MouseLIstenerGUI", e1);
				}
			}
		}
	}

	/**
	 * Method used to drag a Selectable Object with the Mouse.
	 * If the Point where the Mouse is clicked, taken by Mouse Event e that is passed as parameter,
	 * is on an OvineGUI with a valid Ovine id, that is dragged by the mouse and its OvineGUI id 
	 * is send to the Server.
	 * @param e MouseEvent used to get the mouse position at the moment of the click
	 * on the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void dragAction(MouseEvent e) throws RemoteException {
		Point mousePosition = e.getPoint();
		for(SelectableObjectGUI s : selectableObjects) {
			if(s.getBounds().contains(mousePosition) 
					&& s instanceof OvineGUI
					&& this.isValidOvine(((OvineGUI) s).getOvineId())){
						selectedObject = s;
						selectedObject.isSelected(true);
						this.sendOvineGUIId((OvineGUI) s);
						break;
			}
		}
	}

	/**
	 * Method used to drop a Selectable Object from the Mouse.
	 * If the Point where the Mouse is clicked, taken by Mouse Event e that is passed as parameter,
	 * is on a LandGUI with a valid Land id, the Selected Object is dropped from the Mouse and the
	 * id of the LandGUI and the new positionPoint are send to the Server.
	 * @param e MouseEvent used to get the mouse position at the moment of the click
	 * on the GameBoardGUI.
	 * @throws RemoteException if there are connection problems.
	 */
	private void dropAction(MouseEvent e) throws RemoteException {
			selectedObject.setLocation(utilitiesForGUI.centerJLabelOnPoint(selectedObject, e.getPoint()));
			selectedObject.isSelected(false);
			this.setSelectedObject(null);
			this.sendClickedLandId(e.getPoint());
			this.sendClickPosition(e.getPoint());
	}

	/**
	 * Method that is executed every time the mouse is clicked and the active Action
	 * of the Game is COUPLING1 or COUPLING2.
	 * It sends the id of the LandGUI clicked to the Server.
	 * @param e MouseEvent used to get the mouse position at the moment of the click
	 * on the GameBoardGUI.
	 */
	private void mouseClickedForCoupling(MouseEvent e) {
		this.sendClickedLandId(e.getPoint());
	}

	/**
	 * Method that is executed every time the mouse is clicked and the active Action
	 * of the Game is KILLOVINE.
	 * If the Point where the Mouse is clicked, taken by Mouse Event e that is passed as parameter,
	 * is on an OvineGUI, it sends the id of the OvineGUI clicked to the Server.
	 * @param e MouseEvent used to get the mouse position at the moment of the click
	 * on the GameBoardGUI.
	 */
	private void mouseClickedForKillOvine(MouseEvent e) {
		for(SelectableObjectGUI s : selectableObjects) { 
			if(s.getBounds().contains(e.getPoint()) && s instanceof OvineGUI) {
				this.sendOvineGUIId((OvineGUI) s);
				break;
			}
		}
	}

	/**
	 * Method used to get the id of the LandGUI clicked.
	 * It takes as parameter a Point and extracts the color of the LandGUI clicked from a matrix that
	 * associates Points of the LandGUI of the GameBoardGUI to colors and then invoking another method
	 * that converts the color in a LandGUI id and returns it.
	 * @param mouseClickPoint where to get the LandGUI which to take the id.
	 * @return the id of the LandGUI that contains the Point inserted as parameter.
	 */
	private int getClickedLandId(Point mouseClickPoint) {
		return gameBoardMappedId.getIdOnColor(gameBoardMappedId.getColorTrackedGameBoard().getCoordinatesColor()[(int) mouseClickPoint.getX()][ (int) mouseClickPoint.getY()]);
	}

	/**
	 * Method that sends the clicked LandGUI id to the server.
	 * @param mouseClickPoint where to get the LandGUI which to take the id to send to the server.
	 */
	private void sendClickedLandId(Point mouseClickPoint) {
		int landId = gameBoardMappedId.getIdOnColor(gameBoardMappedId.getColorTrackedGameBoard().getCoordinatesColor()[(int) mouseClickPoint.getX()][ (int) mouseClickPoint.getY()]);
		if(landId!=-1){
			try {
				viewGUIInterface.setSenderString(""+landId);
			} catch (RemoteException e2) {
				ServerLogger.printOnLogger("MouseLIstenerGUI", e2);
			}
		}
	}

	/**
	 * Method that sends the Mouse position Point at the moment of the click to the server.
	 * Sends the Point in the form of coordinates, before a String of the x of the Point and
	 * then a String of the y of the Point.
	 * @param mouseClickPoint at the moment of the click.
	 */
	private void sendClickPosition(Point mouseClickPoint) {
		try {
			viewGUIInterface.setSenderString("" + mouseClickPoint.x);
			viewGUIInterface.setSenderString("" + mouseClickPoint.y);
		} catch (RemoteException e2) {
			ServerLogger.printOnLogger("MouseLIstenerGUI", e2);
		}
	}

	/**
	 * Method that sends the clicked OvineGUI id to the server.
	 * @param ovineGUI that have the id to send to the server.
	 */
	private void sendOvineGUIId(OvineGUI ovineGUI) {
		try {
			viewGUIInterface.setSenderString("" + ovineGUI.getOvineId());
		} catch (RemoteException e2) {
			ServerLogger.printOnLogger("MouseLIstenerGUI", e2);
		}
	}

	/**
	 * Method used to check if a LandGUI it's a valid Land.
	 * It takes as parameter the LandGUI id and check if it's in the List of the valid Land ids.
	 * @param landId of the LandGUI to check if it's valid.
	 * @return true if the LandGUI id is valid, else false.
	 * @throws RemoteException if there are connection problems.
	 */
	private boolean isValidLand(int landId) throws RemoteException {
		for(int validLandId: utilitiesForGUI.getValidLandsIds()) {
			if(landId == validLandId) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method used to check if an OvineGUI it's a valid Ovine.
	 * It takes as parameter the OvineGUI id and check if it's in a Land with an id
	 * that is in the List of the valid Land ids. 
	 * @param ovineId of the OvineGUI to check if it's valid.
	 * @return true if the OvineGUI id is valid, else false.
	 * @throws RemoteException if there are connection problems.
	 */
	private boolean isValidOvine(int ovineId) throws RemoteException{
		for(int validLandId: utilitiesForGUI.getValidLandsIds()) {
			for(Ovine o: viewGUIInterface.getGameInterface().getGameBoard().getLands().get(validLandId).getOvines()) {
				if(ovineId == o.getId()) {
					return true;
				}
			}
		}
		return false;
	}
	
}	
