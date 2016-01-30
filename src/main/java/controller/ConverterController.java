package controller;

import java.io.Serializable;
import java.rmi.RemoteException;

import model.Game;
import model.Land;
import model.Ovine;
import model.Road;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Class that implements methods to convert Id taken as input in object relative to the game.
 */
public class ConverterController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	/**
	 * Builder of the ConverterController.
	 * @param game model.
	 */
	public ConverterController(Game game) {
		this.game = game;
	}

	/**
	 * Method that takes as parameter the id of a road and return the Road with that id.
	 * @param roadId of the road.
	 * @return the road that has that id.
	 * @throws RemoteException if there are connection problems. 
	 */
	public Road getRoadOnId(int roadId) throws RemoteException{
		for(Road r: game.getGameBoard().getRoads()){
			if(r.getId() == roadId){
				return r;
			}
		}
		return null;
	}

	/**
	 * Method that takes as parameter the id of a land and return the Land with that id.
	 * @param landId of the land.
	 * @return the land that has that id.
	 */
	public Land getLandOnId(int landId) {
		for(Land l: game.getLands()) {
			if(l.getId() == landId) {
				return l;
			}
		}
		return null;
	}

	/**
	 * Method that takes as parameter the id of an ovine and return the Land that contains the ovine with that id.
	 * @param ovineId of the ovine.
	 * @return the land that contains the ovine with that id.
	 */
	public Land getLandOnOvineId(int ovineId) { 
		for(Land l: game.getLands()) {
			for(Ovine o: l.getOvines()) {
				if(o.getId() == ovineId) {
					return l;
				}
			}
		}
		return null;
	}
}
