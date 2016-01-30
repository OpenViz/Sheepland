package server;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import view.ViewInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public interface RMIListenersInterface extends Remote {
	
	
	/**
	 * Add the view(Client) to the server.
	 * @param view: client view.
	 * @throws RemoteException: Connection issue.
	 * @throws NotBoundException: Connection issue.
	 */
	public void addPlayer(ViewInterface view) throws RemoteException;

}
