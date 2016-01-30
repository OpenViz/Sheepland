package utility;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public interface Observer extends Remote,Serializable{
	
	
	/**
	 * The observer update his state knowing that observable object has change.
	 * @throws RemoteException: connection issue.
	 */
	public void update() throws RemoteException;

}
