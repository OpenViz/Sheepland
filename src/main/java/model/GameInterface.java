package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Interface of the class Game, is used by views.
 */
public interface GameInterface extends ObservableSubject, Remote {
	
	/**
	 * @return the GameBoard of the Game.
	 * @throws RemoteException if there are connection problems.
	 */
	public GameBoard getGameBoard() throws RemoteException;
	
	/**
	 * @return the GameInfo of the Game.
	 * @throws RemoteException if there are connection problems.
	 */
	public GameInfo getGameInfo() throws RemoteException;
	
	/** 
	 * @return the active Player of the Game.
	 * @throws RemoteException if there are connection problems.
	 */
	public Player getActivePlayer() throws RemoteException;
}
