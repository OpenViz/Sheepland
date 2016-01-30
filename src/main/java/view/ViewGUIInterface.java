package view;

import java.rmi.RemoteException;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public interface ViewGUIInterface extends ViewInterface{
	
	/**
	 * Set the string that will be send to the server.
	 * @param string: that will be send to the server.
	 * @throws RemoteException: connection issue.
	 */
	public void setSenderString(String string) throws RemoteException;

}
