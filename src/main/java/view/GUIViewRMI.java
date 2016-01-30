package view;

import gui.GameGUI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import utility.Observer;
import utility.ServerLogger;
import model.GameInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class GUIViewRMI extends UnicastRemoteObject implements Observer,ViewGUIInterface {
	private static final long serialVersionUID = 1L;
	private static final String CONNECTIONTYPE = "RMIGUI";
	
	private String nickname;
	private List<String> senderStrings;
	private GameInterface game;
	private GameGUI gameGUI;
	private boolean firstGUIUpdate = true;
	

	/**
	 * Creates a GUIViewRMI that is used to communicate with the Server.
	 * @throws RemoteException
	 */
	public GUIViewRMI() throws RemoteException {
		super();
		this.nickname = null;
		this.senderStrings = new ArrayList<String>();
		this.game = null;
		this.gameGUI = null;
	}

	/**
	 * @also
	 * 		The server gets a String from the specific client.
	 */
	public String getString() throws RemoteException {
		while(senderStrings.isEmpty()) {
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					ServerLogger.printOnLogger("GUIViewRMI", e);
				}
			}
		}
		return senderStrings.remove(0);
	}

	/**
	 * @also
	 * 		The server sends a String to the specific client.
	 */
	public void printString(String string) throws RemoteException {
		if(string == null) {
			return;
		}
		this.gameGUI.printMessage(string);
	}

	/**
	 * Sets the GameInterface to the view.
	 */
	public void setGame(GameInterface game) throws RemoteException {
		this.game = game;
		
	}

	/**
	 * Gets the GUIViewRMI's nickname.
	 */
	public String getNickname() throws RemoteException {
		return this.nickname;
	}

	/**
	 * Returns a string that represent the connection used.
	 * The string is "RMIGUI".
	 */
	public String getConnectionType() throws RemoteException {
		return GUIViewRMI.CONNECTIONTYPE;
	}

	/**
	 * Gets the gameInterface saved.
	 */
	public GameInterface getGameInterface() throws RemoteException {
		return this.game;
	}

	/**
	 * @also
	 * 		Makes two type of the update. The first update load all the image.
	 * 		The other updates modifies only the changes.
	 */
	public void update() throws RemoteException {
		if(firstGUIUpdate) {
			gameGUI.setInterfaces();
			gameGUI.getGameBoardGUI().initialize();
			gameGUI.update();
			firstGUIUpdate = false;
		} else {
			gameGUI.getGameBoardGUI().updateGameboardGUI();
			gameGUI.update();
		}
	}
	
	
	/**
	 * Sets the GUIViewRMI's nickname. It indicates the owner of the view.
	 */
	public void setNickname(String nickname) throws RemoteException{
		this.nickname = nickname;
	}
	
	/**
	 * Set the string that will be send to the server.
	 * Then notifies all that objects that are waiting.
	 */
	public void setSenderString(String senderString) throws RemoteException{
		synchronized (this) {
			this.senderStrings.add(senderString);
			notifyAll();
		}
	}
	
	/**
	 * Sets the GameGuI which the GUIViewRMI have to communicate with.
	 * @param gameGUI: to set the GUiViewRMI's GameGUI.
	 */
	public void setGameGUI(GameGUI gameGUI){
		this.gameGUI = gameGUI;
	}

}
