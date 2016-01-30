package controller;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

import model.Game;
import model.Land;
import model.Road;
import utility.ServerLogger;
import view.ViewInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class that implements methods to get input from views.
 */
public class InputController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private List<ViewInterface> views;
	
	private ConverterController converterController;
	
	/**
	 * Builder of the InputController.
	 * @param game model.
	 * @param views where ask and take inputs.
	 * @param converterController used to convert ids in objects.
	 */
	public InputController(Game game, List<ViewInterface> views, ConverterController converterController) {
		this.game = game;
		this.views = views;
		this.converterController = converterController;
	}
	
	/**
	 * Method to take integer from view.
	 * @return an integer taken from the current view.
	 * @throws RemoteException if there are connection problems. 
	 */
	public int getIntFromView() throws RemoteException{
		while(true){
			try{
				return Integer.parseInt(views.get(game.getIndexOfActivePlayer()).getString());
			}catch(NumberFormatException e){
				ServerLogger.printOnLogger("ActionsController", e);
			}
		}
	}

	/**
	 * Method that takes an integer from view until it's a valid road's Id and return that road.
	 * @return the valid road that have the id inserted. 
	 * @throws RemoteException if there are connection problems. 
	 */
	public Road getValidRoad() throws RemoteException {
		int roadId;
		Road roadToGo;
		while(true) {
			roadId = this.getIntFromView();
			roadToGo = converterController.getRoadOnId(roadId);
			if(roadToGo == null) {
				views.get(game.getIndexOfActivePlayer()).printString("The road with that ID was not found, retry!");
			} else if(roadToGo.isBusy()) {
				views.get(game.getIndexOfActivePlayer()).printString("The road with that ID is busy, retry!");
			} else {
				return roadToGo;
			}
		}
	}

	/**
	 * Method that takes an integer from view until it's a valid ovine's Id and return that ovine.
	 * @return the valid ovine that have the id inserted. 
	 * @throws RemoteException if there are connection problems. 
	 */
	public int getValidOvineId() throws RemoteException {
		int ovineId;
		Land ovineLand;
		while(true) { 
			ovineId = this.getIntFromView();
			ovineLand = converterController.getLandOnOvineId(ovineId);
			if(ovineLand!=null && this.isValidLand(ovineLand.getId())) { 
				return ovineId;
			}
			views.get(game.getIndexOfActivePlayer()).printString("Choose a valid ovine, retry!");
		}
	}

	/**
	 * Method that takes an integer from view until it's a valid land's Id and return that land.
	 * @return the valid land that have the id inserted. 
	 * @throws RemoteException if there are connection problems. 
	 */
	public Land getValidLand() throws RemoteException {
		int landId;
		while(true) {
			landId = this.getIntFromView();
			if(this.isValidLand(landId)) {
				return converterController.getLandOnId(landId);
			}
			views.get(game.getIndexOfActivePlayer()).printString("Choose a valid land, retry!");
		}
	}
	
	/**
	 * Method that check if a landId it's an id of a valid land.
	 * @param landId to check.
	 * @return true if the land with landId inserted is valid.
	 */
	private boolean isValidLand(int landId) {
		for(int validId: game.getValidsLandsIds()) {
			if(landId == validId) {
				return true;
			}
		}
		return false;
	}
}
