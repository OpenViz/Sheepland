package gui;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JLabel;

import model.ActionType;
import model.MoveOvineStep;
import view.ViewGUIInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class that implements some utilietes and save chain methods used by other classes of the GUI.
 */
public class UtilitiesForGUI {
	private ViewGUIInterface viewGUIInterface;
	
	/** 
	 * Builder of the UtilitiesForGUI.
	 * It takes the ViewGUIInterface as parameter. 
	 * ViewGUIInterface is used to get all the data of the Game that are needed
	 * by the GUI. 
	 * @param viewGUIInterface to set to the UtilitiesForGUI.
	 */
	public UtilitiesForGUI(ViewGUIInterface viewGUIInterface) {
		this.viewGUIInterface = viewGUIInterface;
	}
	
	/**
	 * Method used to center the JLabels.
	 * @param jLabel to center.
	 * @param destinationPoint to put as the center of the JLabel.
	 * @return the new Leading Edge Point of the JLabel.
	 */
	public Point centerJLabelOnPoint(JLabel jLabel, Point destinationPoint) {
		Point newLeadingEdgePoint = new Point();
		newLeadingEdgePoint.setLocation(destinationPoint.getX() - jLabel.getWidth()/2, destinationPoint.getY() - jLabel.getHeight()/2);
		return newLeadingEdgePoint;
	}
	
	/**
	 * Save chain method.
	 * @return the List of the valid Lands ids of the GameInfo.
	 * @throws RemoteException if there are connection problems.
	 */
	public List<Integer> getValidLandsIds() throws RemoteException {
		return viewGUIInterface.getGameInterface().getGameInfo().getValidLandsIds();
	}
	
	/**
	 * Save chain method.
	 * @return the active Action of the GameInfo.
	 * @throws RemoteException if there are connection problems.
	 */
	public ActionType getActiveAction() throws RemoteException {
		return viewGUIInterface.getGameInterface().getGameInfo().getActiveAction();
	}
	
	/**
	 * Save chain method.
	 * @return the active Event of the GameInfo.
	 * @throws RemoteException if there are connection problems.
	 */
	public model.EventType getActiveEvent() throws RemoteException {
		return viewGUIInterface.getGameInterface().getGameInfo().getActiveEvent();
	}
	
	/**
	 * Save chain method.
	 * @return the MoveOvineStep of the GameInfo.
	 * @throws RemoteException if there are connection problems.
	 */
	public MoveOvineStep getMoveOvineStep() throws RemoteException {
		return viewGUIInterface.getGameInterface().getGameInfo().getMoveOvineStep();
	}

	/**
	 * @param viewGUIInterface to set to the UtilitiesForGUI.
	 */
	public void setViewGUIInterface(ViewGUIInterface viewGUIInterface) {
		this.viewGUIInterface = viewGUIInterface;
	}
}
