package view;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import model.GameInterface;

/**
 * Interface of all view
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public interface ViewInterface extends Remote, Serializable {

	/**
	 * Gets a string from the ViewInterface.
	 * @return: a string from the ViewInterface
	 * @throws RemoteException: connectio issue.
	 */
	public String getString() throws RemoteException ;
	
	/**
	 * Prints the String(parameter)
	 * @param string: that have to be print.
	 * @throws RemoteException: connectio issue.
	 */
	public void printString(String string) throws RemoteException;
	
	/**
	 * Sets the GameInterface to the view.
	 * @param game : that have to be set.
	 * @throws RemoteException:connectio issue.
	 */
	public void setGame(GameInterface game) throws RemoteException ;
	
	/**
	 * Gets the ViewInterface's nickname.
	 * @return nickname: of the viewInterface.
	 * @throws RemoteException:connectio issue.
	 */
	public String getNickname() throws RemoteException;
		
	/**
	 * Gets a string that says which type of connection the ViewInterface is using.
	 * @return string: that represent the connection type.
	 * @throws RemoteException: connection issue.
	 */
	public String getConnectionType() throws RemoteException;

	/**
	 * Gets the gameInterface saved in the ViewINterface.
	 * @return GameInterface: that is saved in the ViewInterface.
	 * @throws RemoteException: connection issue.
	 */
	public GameInterface getGameInterface() throws RemoteException;
	
	/**
	 * Sets the ViewInterface's nickname. 
	 * It indicates the owner of the view.
	 * @param nickname : to set the ViewInterface's nickname.
	 * @throws RemoteException: connection Issue.
	 */
	public void setNickname(String nickname) throws RemoteException;
	
}

