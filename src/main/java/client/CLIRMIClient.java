package client;


import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import server.RMIListenersInterface;
import utility.SysoPrinter;
import view.CLIViewRMI;
import view.ViewInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class CLIRMIClient {
	
	/**
	 * Private constructor for static methods.
	 */
	private CLIRMIClient(){
	}

	/**
	 * Static methods that runs the Client in RMI and CLI.
	 * First the client has to insert his nickname.
	 * Then he will connect to the server through the RMIListeners(Server).
	 * @throws RemoteException:connection issue.
	 * @throws AlreadyBoundException:the name for the bind is already use.
	 * @throws NotBoundException: the server was not found.
	 */
	public static void start() throws RemoteException, AlreadyBoundException, NotBoundException {
		ViewInterface view = new CLIViewRMI();
		String nickname =  view.getString();
		((CLIViewRMI) view).setNickname(nickname);
		Registry registry = LocateRegistry.getRegistry();
		registry.bind(view.getNickname(), (Remote) view);
		RMIListenersInterface rmInterface =  (RMIListenersInterface) registry.lookup("SheepLandRMI");
		rmInterface.addPlayer(view);
		SysoPrinter.println("ConnectionDone");
	}

}
