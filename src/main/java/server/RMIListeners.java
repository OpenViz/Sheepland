package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import utility.ServerLogger;
import utility.SysoPrinter;
import view.ViewInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class RMIListeners extends UnicastRemoteObject implements RMIListenersInterface {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameInstantiator gameIstantiator;

	/**
	 * Create a  new RMIListeners. We have to specify the gameIstantiator with it works together.
	 * It is a remote object that is put in the Registry. When a Client wants to connect
	 * to the server with RMI have to invoke the remoteMethod addPlayer.
	 * @param gameIstantiator: references to GameIstantiator.
	 * @throws RemoteException: connection issue.
	 */
	public RMIListeners(GameInstantiator gameIstantiator) throws RemoteException{
		super();
		this.gameIstantiator = gameIstantiator;
	}
	/**
	 * @also
	 *		The client have to put his View as a parameter of the Method.This view have 
	 *		to implement the RMI. He adds the view to the gameIstantiator and increment
	 *		the number of players that are waiting a game.
	 */
	public void addPlayer(ViewInterface view) throws RemoteException{
		try {
			String name = view.getNickname();
			Registry registry = LocateRegistry.getRegistry();
			ViewInterface viewRMI;
			viewRMI = (ViewInterface) registry.lookup(name);
			this.gameIstantiator.addView(viewRMI);
			this.gameIstantiator.addNumberOfPlayer();
			SysoPrinter.println("Connection Received from " + view.getNickname());
		} catch (NotBoundException e) {
			ServerLogger.printOnLogger("RMIListeners", e);
			throw new RemoteException();
		}
		
		
	}

	
	
	
}
