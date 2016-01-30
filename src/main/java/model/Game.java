package model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import utility.Observer;


/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Main class of the model that instantiates all big classes of the Game model.
 * Also instantiates the List of Observers that observe the model for the view.
 */
public class Game extends UnicastRemoteObject implements Serializable, GameInterface{
	private static final long serialVersionUID = 1L;
	
	private GameBoard gameBoard;
	private GameInfo gameInfo;
	private List<Observer> observers = new ArrayList<Observer>();
	
	/**
	 * Builder of the Game in case the number of Players is 2.
	 * @param nickname1 of the first Player.
	 * @param nickname2 of the second Player.
	 * @throws RemoteException if there are connection problems.
	 */
	public Game(String nickname1, String nickname2) throws RemoteException {
		super();
		gameBoard = new GameBoard(nickname1, nickname2);
		gameInfo = new GameInfo(2);	
	}
	
	/**
	 * Builder of the Game in case the number of Players is 3.
	 * @param nickname1 of the first Player.
	 * @param nickname2 of the second Player.
	 * @param nickname3 of the third Player.
	 * @throws RemoteException if there are connection problems.
	 */
	public Game(String nickname1, String nickname2, String nickname3) throws RemoteException {
		super();
		gameBoard = new GameBoard(nickname1, nickname2, nickname3);
		gameInfo = new GameInfo(3);	
	}
	
	/**
	 * Builder of the Game in case the number of players is 4.
	 * @param nickname1 of the first Player.
	 * @param nickname2 of the second Player.
	 * @param nickname3 of the third Player.
	 * @param nickname4 of the fourth Player.
	 * @throws RemoteException if there are connection problems. 
	 */
	public Game(String nickname1, String nickname2, String nickname3, String nickname4) throws RemoteException {
		super();
		gameBoard = new GameBoard(nickname1, nickname2, nickname3, nickname4);
		gameInfo = new GameInfo(4);	
	}
	
	/**
	 * @return the GameBoard of the Game.
	 *  @throws RemoteException if there are connection problems.
	 */
	public GameBoard getGameBoard() throws RemoteException {
		return gameBoard;
	}
	
	/**
	 * @param gameBoard to set to the Game.
	 */
	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	/**
	 * @return the GameInfo of the Game.
	 */
	public GameInfo getGameInfo() {
		return gameInfo;
	}
	
	/**
	 * @param gameInfo to set to the Game.
	 */
	public void setGameInfo(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
	}
	
	
	//*******************SaveChainMethods***********************	
	// This list of methods is used to make the code of the controller's classes more readable.
	
	/**
	 * Method used to get the Player that his playing in the current turn.
	 * @return the active Player of the Game.
	 * @throws RemoteException if there are connection problems.
	 */
	public Player getActivePlayer() throws RemoteException {
		return gameBoard.getPlayers().get(gameInfo.getIndexOfActivePlayer());
	}
	
	/**
	 * Method used to get the index of the List of the Players 
	 * of the Player that his playing in the current turn.
	 * @return the index of Active Player of the Game.
	 */
	public int getIndexOfActivePlayer() {
		return gameInfo.getIndexOfActivePlayer();
	}
	
	/**
	 * Method used to get the List of Cards that are actually on sales during the Game.
	 * @return the List of Cards on sales.
	 * @throws RemoteException if there are connection problems.
	 */
	public List<Card> getCardsOnSales() throws RemoteException {
		return gameBoard.getCards();
	}
	
	/**
	 * A method to obtain a random number from Dice.
	 * Sets a random number between 1 to number of face to the Dice result.
	 * @throws RemoteException if there are connection problems.
	 */
	public void rollDice() throws RemoteException {
		gameBoard.getDice().rollDice();
	}
	
	/**
	 * Method used to get the last number rolled with the Dice.
	 * @return result of the Dice.
	 * @throws RemoteException if there are connection problems.
	 */
	public int getResultOfDice() throws RemoteException {
		return gameBoard.getDice().getResult();
	}
	
	/**
	 * Method used to get the List of the Lands of the GameBoard.
	 * @return the List of the Lands of the GameBoard.
	 */
	public List<Land> getLands() {
		return gameBoard.getLands();
	}
	
	/**
	 * Method used to get the neighboring Lands of the active Shepherd of the active Player.
	 * It takes as parameter the number of the Shepherd that is currently being
	 * used by the active Player and returns the List of the neighboring Lands
	 * of the Road occupied by that Shepherd.
	 * @param shepherdNumber of the active Shepherd of the active Player.
	 * @return the List of the neighboring Lands of the Shepherd's Road. 
	 * @throws RemoteException if there are connection problems.
	 */
	public List<Land> getNeighboringLandsOfActivePlayerShepherd(int shepherdNumber) throws RemoteException {
		return this.getActivePlayer().getRoadOfShepherd(shepherdNumber).getNeighboringLands();
	}
	
	/**
	 * Method used to get the ids of the Land that are valid for some action.
	 * @return the List of the valid Lands ids of the GameInfo.
	 */
	public List<Integer> getValidsLandsIds() {
		return this.gameInfo.getValidLandsIds();
	}
	
	/**
	 * Method used to get the number of the of the Shepherd that is currently being
	 * used by the active Player. 
	 * @return the number of the active Shepherd.
	 */
	public int getActiveShepherdNumber() {
		return this.gameInfo.getActiveShepherdNumber();
	}
	
	/**
	 * Method used to set the number of the of the Shepherd that is currently being
	 * used by the active Player.
	 * @param activeShepherdNumber to set to the GameInfo.
	 */
	public void setActiveShepherdNumber(int activeShepherdNumber) {
		this.gameInfo.setActiveShepherdNumber(activeShepherdNumber);
	}
	//************************************************************
	
	
	/**
	 * @return the List of the Observers of the Game.
	 */
	public List<Observer> getObservers() {
		return this.observers;
	}
	
	/**
	 * Adds the observer to the List of the Observers that observe the Game.
	 * @param observer to add to the List of Observers.
	 */
	public void addObserver(Observer observer) {
		this.observers.add(observer);
	}
	
	/**
	 * Remove the observer from the List of the Observers that observe the Game.
	 * @param observer to remove to the List of the Observers.
	 */
	public void removeObserver(Observer observer) {
		this.observers.remove(observer);
	}
	
	/**
	 * Notify all the Observers that the observed object is changed.
	 * @throws RemoteException if there are connection problems.
	 */
	public void notifyObserver() throws RemoteException {
		for(Observer observer: this.observers){
			if(observer != null){
				observer.update();
			}
		}
	}
	
	/**
	 * Sets the active Action in the GameInfo.
	 * @param actionType to set as active Action in the GameInfo.
	 * @throws RemoteException if there are connection problems.
	 */
	public void setActiveAction(ActionType actionType) throws RemoteException{
		this.gameInfo.setActiveAction(actionType);
		this.notifyObserver();
	}
	
	/**
	 * Set the active Event in the GameInfo.
	 * @param eventType to set as active Event in the GameInfo.
	 * @throws RemoteException if there are connection problems.
	 */
	public void setActiveEvent(EventType eventType) throws RemoteException{
		this.gameInfo.setActiveEvent(eventType);
	}
	
	
	/**
	 * @param actionDone to set as true or false to the GameInfo.
	 * @throws RemoteException if there are connection problems.
	 */
	public void setActionDone(boolean actionDone) throws RemoteException{
		this.gameInfo.setActionDone(actionDone);
	}
	
	/**
	 * Method to set the List of valid Lands ids to the GameInfo.
	 * After the set notifies the observers.
	 * @param validLandsIds
	 * @throws RemoteException if there are connection problems.
	 */
	public void setValidLandsIds(List<Integer> validLandsIds) throws RemoteException {
		for(int i = 0; i < validLandsIds.size(); i++){
			this.gameInfo.getValidLandsIds().add(validLandsIds.get(i));
		}
		this.notifyObserver();
	}
	
	/**
	 * Method used to clear the List of valid Lands ids.
	 */
	public void clearValidLandsIds() {
		this.gameInfo.getValidLandsIds().clear();
	}
	
}
