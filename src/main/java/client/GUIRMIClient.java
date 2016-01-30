/**
 * 
 */
package client;

import gui.GameGUI;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.SwingUtilities;

import server.RMIListenersInterface;
import utility.ServerLogger;
import view.GUIViewRMI;
import view.ViewGUIInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class GUIRMIClient {

	/**
	 * Private constructor for static methods.
	 */
	private GUIRMIClient(){
		
	}

	/**
	 * Static methods that the client run to connect in Socket and GUI.
	 * * First the client has to insert his nickname.
	 * Then he will connect to the server through the RMIListeners(Server).
	 */
	public static void start() {
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * 
			 */
			public void run(){
				try {
					GameGUI gameGUI = new GameGUI();
					ViewGUIInterface view = new GUIViewRMI();
					((GUIViewRMI) view).setGameGUI(gameGUI);
					String nickname  = gameGUI.popUpFromTheServer("Insert name: ");
					((GUIViewRMI) view).setNickname(nickname);
					gameGUI.setViewGUIInterface(view);
					Registry registry = LocateRegistry.getRegistry();
					registry.bind(view.getNickname(), (Remote) view);
					RMIListenersInterface rmInterface =  (RMIListenersInterface) registry.lookup("SheepLandRMI");
					rmInterface.addPlayer(view);
				} catch (RemoteException e) {
					ServerLogger.printOnLogger("GUIRMIClient", e);
				} catch (AlreadyBoundException e) {
					ServerLogger.printOnLogger("GUIRMIClient", e);
				} catch (NotBoundException e) {
					ServerLogger.printOnLogger("GUIRMIClient", e);
				}
			}
		});
	}
}
