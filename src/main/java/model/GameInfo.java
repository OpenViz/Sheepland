package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Class that stores all the information about the state of the Game.
 */
public class GameInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final int numberOfPlayers;
	private int indexOfActivePlayer;
	private int numberOfActionsForActivePlayer;
	private ActionType lastActionDone;
	private boolean actionDone;
	private boolean movedShepherd;
	
	//Selling Cards List of the Market.
	private List<SellingCard> sellingCards;
	
	//Steps of the Market.
	private MarketStep marketStep;
	
	private MoveOvineStep moveOvineStep;
 
	private List<ActionType> possibleActions;

	// Support variables to the GUI.
	private ActionType activeAction;
	private model.EventType activeEvent;
	private int activeShepherdNumber;
	private List<Integer> validLandsIds;
	private int movedOvineId;
	private int killedOvineId;
	private int newOvineId;
	private int eatedOvineId;
	
	//Information for who wins the game.
	private List<Player> winnerPlayers;
	
	/**
	 * Builder of the GameInfo.
	 * Initialize all the information about the state of the Game.
	 * @param numberOfPlayers that are playing the Game.
	 */
	public GameInfo(int numberOfPlayers){
		this.numberOfPlayers = numberOfPlayers;
		this.indexOfActivePlayer = 0;
		this.numberOfActionsForActivePlayer = 3;
		this.actionDone = false;
		this.lastActionDone = null;
		this.movedShepherd = false;
		this.possibleActions =  new ArrayList<ActionType>();

		this.activeAction = null;
		this.activeEvent = null;
		this.validLandsIds = new ArrayList<Integer>();
		this.eatedOvineId = 0;
		this.sellingCards = null;
		this.marketStep = null;
		this.moveOvineStep = null;
		
		this.winnerPlayers = null;
	}
	
	/**
	 * Method used to set all the variables relating to the conduct of the Game
	 * for the next Player's turn.
	 */
	public void setForNextPlayer() {
		this.indexOfActivePlayer++;
		if(this.indexOfActivePlayer == this.numberOfPlayers) {
			this.indexOfActivePlayer = 0;
		}
		this.numberOfActionsForActivePlayer = 3;
		this.movedShepherd = false;
		this.lastActionDone = null;
		this.possibleActions.clear();
	}
	
	/**
	 * Method used to set all the variables relating to the conduct of the Game
	 * for the next action of the active Player.
	 */
	public void setForNextActionOfActivePlayer() {
		this.numberOfActionsForActivePlayer--;
		this.actionDone = false;
		this.possibleActions.clear();
	}
	
	/**
	 * @return the indexOfActivePlayer of the GameInfo.
	 */
	public int getIndexOfActivePlayer() {
		return indexOfActivePlayer;
	}
	
	/**
	 * @param indexOfActivePlayer to set to the GameInfo.
	 */
	public void setIndexOfActivePlayer(int indexOfActivePlayer) {
		this.indexOfActivePlayer = indexOfActivePlayer;
	}
	
	/**
	 * @return the numberOfPlayers of the Game.
	 */
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	/**
	 * @return the numberOfActionsForActivePlayer of the GameInfo.
	 */
	public int getNumberOfActionsForActivePlayer() {
		return numberOfActionsForActivePlayer;
	}
	
	/**
	 * @param numberOfActionsForActivePlayer to set to the GameInfo.
	 */
	public void setNumberOfActionsForActivePlayer(
			int numberOfActionsForActivePlayer) {
		this.numberOfActionsForActivePlayer = numberOfActionsForActivePlayer;
	}
	
	/**
	 * @return true if the action is done, else false.
	 */
	public boolean isActionDone() {
		return actionDone;
	}
	
	/**
	 * @param actionDone to set true or false to the GameInfo.
	 */
	public void setActionDone(boolean actionDone) {
		this.actionDone = actionDone;
	}
	
	/**
	 * @return true if the Shepherd has moved during the Player's turn, else false. 
	 */
	public boolean isMovedShepherd() {
		return movedShepherd;
	}
	
	/**
	 * @param movedShepherd to set true or false to the GameInfo.
	 */
	public void setMovedShepherd(boolean movedShepherd) {
		this.movedShepherd = movedShepherd;
	}
	
	/**
	 * @return the lastActionDone of the GameInfo.
	 */
	public ActionType getLastActionDone() {
		return lastActionDone;
	}
	
	/**
	 * @param lastActionDone to set to the GameInfo.
	 */
	public void setLastActionDone(ActionType lastActionDone) {
		this.lastActionDone = lastActionDone;
	}
	
	/**
	 * @return the possibleActions List of the GameInfo.
	 */
	public List<ActionType> getPossibleActions() {
		return possibleActions;
	}
	
	/**
	 * @param possibleActions List to set to the GameInfo.
	 */
	public void setPossibleActions(List<ActionType> possibleActions) {
		this.possibleActions = possibleActions;
	}
	
	/**
	 * Method that converts the List of PossibleActions in a String to makes it
	 * printable on a CLI.
	 * @return the List of possibleActions as a String.
	 */
	public String possibleActionsToString() {
		String possibleActionsString = "[";
		for(ActionType a: this.possibleActions) {
			possibleActionsString += a.toString() + ",";
		}
		possibleActionsString += "]";
		return possibleActionsString;
	}
	
	/**
	 * @return the activeAction of the GameInfo.
	 */
	public ActionType getActiveAction() {
		return activeAction;
	}
	
	/**
	 * @param activeAction to set to the GameInfo.
	 */
	public void setActiveAction(ActionType activeAction) {
		this.activeAction = activeAction;
	}
	
	/**
	 * @return the activeShepherdNumber of the GameInfo.
	 */
	public int getActiveShepherdNumber() {
		return activeShepherdNumber;
	}
	
	/**
	 * @param activeShepherdNumber to set to the GameInfo.
	 */
	public void setActiveShepherdNumber(int activeShepherdNumber) {
		this.activeShepherdNumber = activeShepherdNumber;
	}
	
	/**
	 * @return the List of valid Lands ids of the GameInfo;
	 */
	public List<Integer> getValidLandsIds(){
		return this.validLandsIds;
	}

	/**
	 * @return the activeEvent of the GameInfo.
	 */
	public model.EventType getActiveEvent() {
		return activeEvent;
	}
	
	/**
	 * @param activeEvent to set to the GameInfo.
	 */
	public void setActiveEvent(model.EventType activeEvent) {
		this.activeEvent = activeEvent;
	}
	
	/**
	 * @return the movedOvineId of the GameInfo.
	 */
	public int getMovedOvineId() {
		return movedOvineId;
	}
	
	/**
	 * @param movedOvineId to set to the GameInfo.
	 */
	public void setMovedOvineId(int movedOvineId) {
		this.movedOvineId = movedOvineId;
	}
	
	/**
	 * @return the killedOvineId of the GameInfo.
	 */
	public int getKilledOvineId() {
		return killedOvineId;
	}
	/**
	 * @param killedOvineId to set to the GameInfo.
	 */
	public void setKilledOvineId(int killedOvineId) {
		this.killedOvineId = killedOvineId;
	}
	
	/**
	 * @return the newOvineId of the GameInfo.
	 */
	public int getNewOvineId() {
		return newOvineId;
	}
	
	/**
	 * @param newOvineId to set to the GameInfo.
	 */
	public void setNewOvineId(int newOvineId) {
		this.newOvineId = newOvineId;
	}
	
	/**
	 * @return the eatedOvineId of the GameInfo.
	 */
	public int getEatedOvineId() {
		return eatedOvineId;
	}
	
	/**
	 * @param eatedOvineId to set to the GameInfo.
	 */
	public void setEatedOvineId(int eatedOvineId) {
		this.eatedOvineId = eatedOvineId;
	}
	
	/**
	 * @return the List of SellingCards of the GameInfo.
	 */
	public List<SellingCard> getSellingCards(){
		return this.sellingCards;
		
	}
	
	/**
	 * @param sellingCards List to set to the GameInfo.
	 */
	public void setSelingCards(List<SellingCard> sellingCards){
		this.sellingCards = sellingCards;
	}
	
	/**
	 * @return the marketStep of the GameInfo.
	 */
	public MarketStep getMarketStep() {
		return marketStep;
	}
	
	/**
	 * @param marketStep to set to the GameInfo.
	 */
	public void setMarketStep(MarketStep marketStep) {
		this.marketStep = marketStep;
	}
	
	/**
	 * @return the moveOvineStep of the GameInfo.
	 */
	public MoveOvineStep getMoveOvineStep() {
		return moveOvineStep;
	}
	
	/**
	 * @param moveOvineStep to set to the GameInfo.
	 */
	public void setMoveOvineStep(MoveOvineStep moveOvineStep) {
		this.moveOvineStep = moveOvineStep;
	}
	
	/**
	 * @return the winnerPlayers List of the GameInfo.
	 */
	public List<Player> getWinnerPlayers() {
		return winnerPlayers;
	}
	
	/**
	 * @param winnerPlayers to set to the GameInfo.
	 */
	public void setWinnerPlayers(List<Player> winnerPlayers) {
		this.winnerPlayers = winnerPlayers;
	}
	
}
