/**
 * 
 */
package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;

import utility.Observer;
import utility.ServerLogger;
import model.GameInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class ServerSocketAdapter implements ViewInterface,Observer,Serializable {
	private static final long serialVersionUID = 1L;
	
	private transient ObjectInputStream in;
	private String nickname;
	private transient ObjectOutputStream objectOutputStream;
	private GameInterface game;
	private String connectionType;
	
	/**
	 * Create a new ServerSocketAdapeter.
	 * Take the input and output stream from the client(parameter).
	 * @param client: that connect to the server.
	 * @throws IOException:Connectio issue.
	 * @throws ClassNotFoundException: connection issue.
	 */
	public ServerSocketAdapter(Socket client) throws IOException{
		this.objectOutputStream = new ObjectOutputStream(client.getOutputStream());
		this.in= new ObjectInputStream(client.getInputStream());
		this.game =null;
		this.nickname = null;
		this.connectionType = null;
	}
	
	
	
	/**
	 * @also
	 * 		get a string from the specific Client of the view.
	 */
	public String getString() throws RemoteException {
		try {
			return (String) this.in.readObject();
		} catch (ClassNotFoundException e) {
			ServerLogger.printOnLogger("ServerSocketAdapter", e);
		} catch (IOException e) {
			ServerLogger.printOnLogger("ServerSocketAdapter", e);
			throw new RemoteException();
		}
		return null;
		
		
	}
	
	/**
	 * @also
	 * 		send a string to the specific Client of the view.
	 */
	public void printString(String string) throws RemoteException {
		try {
			this.objectOutputStream.writeObject(string);
			this.objectOutputStream.flush();
		} catch (IOException e) {
			ServerLogger.printOnLogger("ServerSocketAdapter", e);
			throw new RemoteException();
		}
		
	}
	
	/**
	 * Sets the GameInterface to the view.
	 */
	public void setGame(GameInterface game) 
			throws RemoteException {
		this.game = game;
		
	}
	
	/**
	 * Gets the nickname of the owner communicates with.
	 */
	public String getNickname() throws RemoteException {
		return this.nickname;
	}	

	/**
	 * Returns a string that represent the connection type used.
	 */
	public String getConnectionType() throws RemoteException {
		return this.connectionType;
	}

	
	/**
	 * @also
	 * 		send a string "UPDATE" to the client.
	 * 		The client knows that after this string he will receive a GameInterfaceObject.
	 * 		The view sends his gameInterface.
	 */
	public void update() throws RemoteException {
		try {
			this.printString("UPDATE");
			this.objectOutputStream.reset();
			this.objectOutputStream.writeObject((GameInterface)this.game);
			this.objectOutputStream.flush();
		} catch (IOException e) {
			ServerLogger.printOnLogger("ServerSocketAdapter", e);
			throw new RemoteException();
		}
		
	}
	
	/**
	 * Sets the owner's nickname communicates with. 
	 * It indicates the owner of the view.
	 */
	public void setNickname(String nickname){
		this.nickname = nickname;
	}


	/**
	 * Gets the gameInterface saved in.
	 */
	public GameInterface getGameInterface() throws RemoteException{
		return this.game;
	}
	
	/**
	 * Set the connection Type with the passed string(parameter).
	 * @param connectionType: is a string that indicates a connection type.
	 */
	public void setConnectionType(String connectionType){
		this.connectionType  = connectionType;
	}

}
