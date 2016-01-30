package view;

import gui.GameGUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;

import model.GameInterface;
import utility.Observer;
import utility.ServerLogger;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class GUIViewClientSocket implements  Observer,Serializable,ViewGUIInterface {
	private static final long serialVersionUID = 1L;
	
	private Socket server;
	private GameInterface gameInterface;
	private String senderString;
	private ObjectOutputStream out;
	private String nickname;
	private ObjectInputStream objectInputStream;
	private GameGUI gameGUI;
	
	private boolean firstGUIUpdate = true;

	/**
	 * Create a GUIViewClientSocket. This view communicate with the specify server.
	 * Take the input and output stream from the socket server.
	 * @param server: with communicates to.
	 * @throws IOException
	 */
	public GUIViewClientSocket(Socket server) throws IOException {
		this.server = server;
		this.objectInputStream = new ObjectInputStream(this.server.getInputStream());
		this.out = new ObjectOutputStream(this.server.getOutputStream());
		this.gameInterface = null;
		this.nickname  = null;
		this.senderString = null;
		this.gameGUI = null;
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
	 * @also
	 * 		Send a string to the Server.
	 */
	public String getString() throws RemoteException {
		try {
			this.out.writeObject(senderString);
			this.out.flush();
		} catch (IOException e) {
			ServerLogger.printOnLogger("GUIViewClientSocket", e);
		}
		return senderString;
	}

	
	/**
	 * @also
	 * 		Receive a string from a server and print it.
	 * 		If this string is "UPDATE", the view does the update.
	 */
	public void printString(String string) throws RemoteException {
		if(string == null){
			return;
		}
		if("UPDATE".equals(string)){
			this.setGame(this.getGameFromServer());
			this.update();
			return;
		}
		this.gameGUI.printMessage(string);
	}
	
	/**
	 * Sets the GameInterface to the view.
	 */
	public void setGame(GameInterface game) throws RemoteException {
		this.gameInterface = game;
		
	}

	/**
	 * Gets the GUIViewClientSocket's nickname.
	 */
	public String getNickname() throws RemoteException {
		return this.nickname;
	}

	/**
	 * Returns a string that represent the type of the connection used.
	 * The string is "SocketGUI".
	 */
	public String getConnectionType() throws RemoteException {
		return "SocketGUI";
	}
	
	
	
	/**
	 * Gets the gameInterface saved.
	 */
	public GameInterface getGameInterface() throws RemoteException {
		return this.gameInterface;
	}
	
	/**
	 * Sets the GUIViewClietSocket's nickname. 
	 * It indicates the owner of the view.
	 */
	public void setNickname(String nickname) throws RemoteException {
		this.nickname = nickname;
	}
	
	
	/**
	 * Set the string that will be send to the server.
	 * Then sends it.
	 */
	public void setSenderString(String string) throws RemoteException {
		this.senderString = string;
		this.getString();
	}

	/**
	 * Receive the String from the server. After received  it will print them.
	 * @return string: received from the Server
	 * @throws ClassNotFoundException: connection issue.
	 * @throws IOException: connection issue.
	 */
	public String reciveFromServer() throws ClassNotFoundException, IOException {
		String temp = (String)this.objectInputStream.readObject();
		if(temp!= null){
			this.printString(temp);
		}
		return temp;
		
	}

	/**
	 * Set the gameGUI of the GUIViewClientSocket. 
	 * @param gameGUI: that is set.
	 */
	public void setGameGUI(GameGUI gameGUI) {
		this.gameGUI = gameGUI;
	}

	/**
	 * Return a boolean that means if the first update was done.
	 * @return firstGUIUpdate.
	 */
	public boolean isFirstGUIUpdate() {
		return firstGUIUpdate;
	}

	/**
	 * Sets the firstGUIUpdate equals to the passed parameter.
	 * @param firstGUIUpdate: to set firstGUIUpdate.
	 */
	public void setFirstGUIUpdate(boolean firstGUIUpdate) {
		this.firstGUIUpdate = firstGUIUpdate;
	}

	/**
	 * Helper method to the printString.
	 * Receive a GameInterface from the Server and return it.
	 * @return GameInterface: received from the server.
	 */
	private GameInterface getGameFromServer() {
		try {
			GameInterface temp = (GameInterface)this.objectInputStream.readObject();
			while(temp == null){
				temp = (GameInterface)this.objectInputStream.readObject();
			}
			this.gameInterface = temp;
			return temp;
			
		} catch (ClassNotFoundException e) {
			ServerLogger.printOnLogger("ClientViewSocket", e);
		} catch (IOException e) {
			ServerLogger.printOnLogger("ClientViewSocket", e);
		}
		return null;
	}
	

}
