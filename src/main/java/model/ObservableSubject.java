package model;

import java.io.Serializable;
import java.rmi.RemoteException;

import utility.Observer;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Interface that can be observed.
 */
public interface ObservableSubject extends Serializable {
	
	/**
	 * Adds the observer to the list of objects that want to observe the ObservableSubject.
	 * @param observer that wants to observe the ObservableSubject.
	 */
	public void addObserver(Observer observer) throws RemoteException;
	
	/**
	 * Removes the observer from the list of objects that observe the ObservableSubject.
	 * @param observer that don't want to observe anymore the ObservableSubject.
	 */
	public void removeObserver(Observer observer) throws RemoteException;
	
	/**
	 * Notify all the observer that the object is changed.
	 * @throws RemoteException : connection issue.
	 */
	public void notifyObserver() throws RemoteException ;
}
