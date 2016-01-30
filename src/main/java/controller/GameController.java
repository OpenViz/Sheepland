package controller;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import utility.ServerLogger;
import view.ViewInterface;
import model.ActionType;
import model.Card;
import model.EventType;
import model.Game;
import model.Land;
import model.Ovinetype;
import model.Player;
import model.Road;
import model.Shepherd;


/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Main class of the controller that executes the entire progress of the Game.
 * It istantiates the other classes of the controller.
 */
public class GameController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private ActionsController actionsController;
	private EventsController eventsController;
	private InputController inputController;
	private ConverterController converterController;
	
	private int numberOfPlayers;
	private List<String> nicknames;
	private List<ViewInterface> views;
	
	/**
	 * Builder of the GameController
	 * @param numberOfPlayers that play the Game.
	 * @param nicknames List of the nicknames of Players.
	 * @param views List of the Players.
	 * @throws RemoteException if there are connection problems. 
	 */
	public GameController(List<ViewInterface> views) throws RemoteException {
		this.nicknames = new ArrayList<String>();
		this.views = new ArrayList<ViewInterface>();
		this.views = views;
		this.numberOfPlayers = this.views.size();
		
		for(ViewInterface view : views){
			this.nicknames.add(view.getNickname());
		}
		
		switch(this.numberOfPlayers) {
			case 2:
				this.game = new Game(this.nicknames.get(0), this.nicknames.get(1));
				break;
			case 3:
				this.game = new Game(this.nicknames.get(0), this.nicknames.get(1),this.nicknames.get(2));
				break;
			case 4:
				this.game = new Game(this.nicknames.get(0),this. nicknames.get(1), this.nicknames.get(2), this.nicknames.get(3));
				break;
			default:
				break;
		}
		
		converterController = new ConverterController(this.game);
		inputController = new InputController(this.game, this.views, this.converterController);
		eventsController = new EventsController(this.game, this.views, this.inputController);
		actionsController = new ActionsController(this.game,this.views, this.converterController, this.inputController);
	}
	
	/**
	 * This method starts the Game. 
	 * It invokes every main method that controll the execution of the Game.
	 * @throws RemoteException if there are connection problems.
	 */
	public void startGame() throws RemoteException {
	this.setSheperdsStartingPositions();
	this.findDisconnectedPlayer();
	game.setActiveShepherdNumber(0);
		while(!this.isEnded()) {
			if(game.getGameInfo().getNumberOfActionsForActivePlayer() == 3) {
				this.eventsController.moveBlackSheep();
				this.eventsController.growLambs();
			}
			if(!disconnectedPlayer()){
				try {
					if(game.getGameInfo().getNumberOfActionsForActivePlayer() == 3) {
						this.sendMessageToAllViews("Turn of: "+this.views.get(this.game.getIndexOfActivePlayer()).getNickname());
						if(game.getGameInfo().getNumberOfPlayers() == 2) {
							game.setActiveShepherdNumber(eventsController.chooseActiveShepherd());
						}
						this.generatePossibleActions();
					}
					while(game.getGameInfo().getNumberOfActionsForActivePlayer() > 0) {
						this.chooseAction();
						if(game.getGameInfo().isActionDone()) {
							game.getGameInfo().setForNextActionOfActivePlayer();
							this.generatePossibleActions();
						}
					}
				}catch(RemoteException e) {
					ServerLogger.printOnLogger("GameController", e);
					this.findDisconnectedPlayer();
				}
				if(game.getGameInfo().getNumberOfActionsForActivePlayer() == 0 || this.disconnectedPlayer()) {
					game.getGameInfo().setForNextPlayer();
					if(game.getIndexOfActivePlayer() == 0) {
						this.eventsController.market();
						this.eventsController.moveWolf();
					}
				} else {
					game.getGameInfo().setForNextActionOfActivePlayer();
					this.generatePossibleActions();
				}	
			} 
			
		}
		eventsController.checkWinner();
	}
	
	/**
	 * @return the Game of the GameController.
	 */
	public Game getGame(){
		return this.game;
	}

	/**
	 * @return the List of Views of the GameController.
	 */
	public List<ViewInterface> getViews(){
		return this.views;
	}	
	
	/**
	 * This method asks to the Player to choose the action that he wants perform.
	 * Invokes a method from the ActionsController basing on the choose of the active Player.
	 * @throws RemoteException if there are connection problems.
	 */
	private void chooseAction() throws RemoteException {
		ActionType actionType = null;
		if((game.getGameInfo().getNumberOfActionsForActivePlayer() == 1)&&(!game.getGameInfo().isMovedShepherd())) {
			game.setActiveAction(ActionType.MOVESHEPHERD);
			views.get(game.getIndexOfActivePlayer()).printString("You have to move your shepherd!");
			game.setActionDone(actionsController.moveShepherd());
			if(game.getGameInfo().isActionDone()) {
				game.getGameInfo().setMovedShepherd(true);
				game.setActiveAction(null);
			}
		} else {
			do {
				views.get(game.getIndexOfActivePlayer()).printString(game.getGameInfo().possibleActionsToString());
				views.get(game.getIndexOfActivePlayer()).printString("Choose the action you want to do: ");
				try {
					actionType = Enum.valueOf(ActionType.class, views.get(game.getIndexOfActivePlayer()).getString().toUpperCase());
				} catch(IllegalArgumentException e) {
					ServerLogger.printOnLogger("GameController", e);
				}
			} while (!isValidAction(actionType)||(actionType == game.getGameInfo().getLastActionDone())||(actionType == null));
			
			game.setActiveAction(actionType);
			
			switch(actionType) {
				case MOVESHEPHERD: 
					game.setActionDone(actionsController.moveShepherd());
					if(game.getGameInfo().isActionDone()) {
						game.getGameInfo().setMovedShepherd(true);
					}
					break;
				
				case MOVEOVINE: 
					game.setActionDone(actionsController.moveOvine());
					break;
				
				case BUYCARD: 
					game.setActionDone(actionsController.buyCard());
					break;
				
				case COUPLING1: 
					game.setActionDone(actionsController.coupling1());
					break;
				
				case COUPLING2: 
					game.setActionDone(actionsController.coupling2());
					break;
				
				case KILLOVINE: 
					game.setActionDone(actionsController.killOvine());
					break;
				
				default: 
					break;
			}
			game.setActiveAction(null);
			game.getGameInfo().setLastActionDone(actionType);
			if(actionType.equals(ActionType.MOVESHEPHERD)) {
				game.getGameInfo().setLastActionDone(null);
			}
		} 
	}

	/**
	 * This method asks to the Player to choose the starting position for his Shepherds.
	 * @throws RemoteException if there are connection problems. 
	 */
	private void setSheperdsStartingPositions() throws RemoteException {
		game.setActiveEvent(EventType.SETSHEPHERDSTARTINGPOSITIONS);
		game.notifyObserver();
		int numberSetSheperds = 1;
		if(numberOfPlayers == 2){
			numberSetSheperds = 2;
		}
		for(int i = 0; i < game.getGameInfo().getNumberOfPlayers(); i++) {
			for(int k = 0; k < numberSetSheperds; k++) {
				try {
					if(!this.disconnectedPlayer()) {
							game.setActiveShepherdNumber(k);
							actionsController.moveShepherd();
					}
				} catch (RemoteException e) {
					ServerLogger.printOnLogger("GameController", e);
					this.findDisconnectedPlayer();
				}
			}
			game.getGameInfo().setForNextPlayer();
		}
		game.getGameInfo().setIndexOfActivePlayer(0);
		game.setActiveEvent(null);
	}

	private boolean isEnded() throws RemoteException {
		return (game.getGameBoard().getNumberFence() <= 0 && game.getIndexOfActivePlayer() == 0);
	}

	private void generatePossibleActions() throws RemoteException {
		ActionType lastActionDone = game.getGameInfo().getLastActionDone();
		game.getGameInfo().getPossibleActions().add(ActionType.MOVESHEPHERD);
		if(lastActionDone != null){
			if(this.isMoveOvinePossible() && !lastActionDone.equals(ActionType.MOVEOVINE) ) {
				game.getGameInfo().getPossibleActions().add(ActionType.MOVEOVINE);
			}
			if(this.isBuyCardPossible() && !lastActionDone.equals(ActionType.BUYCARD)){
				game.getGameInfo().getPossibleActions().add(ActionType.BUYCARD);
			}
			if(this.isCoupling1Possible() && !lastActionDone.equals(ActionType.COUPLING1)) {
				game.getGameInfo().getPossibleActions().add(ActionType.COUPLING1);
			}
			if(this.isCoupling2Possible() && !lastActionDone.equals(ActionType.COUPLING2)) {
				game.getGameInfo().getPossibleActions().add(ActionType.COUPLING2);
			}
			if(this.isKillOvinePossible() && !lastActionDone.equals(ActionType.KILLOVINE)) {
				game.getGameInfo().getPossibleActions().add(ActionType.KILLOVINE);
			}
		}else{
			if(this.isMoveOvinePossible()) {
				game.getGameInfo().getPossibleActions().add(ActionType.MOVEOVINE);
			}
			if(this.isBuyCardPossible()) {
				game.getGameInfo().getPossibleActions().add(ActionType.BUYCARD);
			}
			if(this.isCoupling1Possible()) {
				game.getGameInfo().getPossibleActions().add(ActionType.COUPLING1);
			}
			if(this.isCoupling2Possible()) {
				game.getGameInfo().getPossibleActions().add(ActionType.COUPLING2);
			}
			if(this.isKillOvinePossible()) {
				game.getGameInfo().getPossibleActions().add(ActionType.KILLOVINE);
			}
		}
	}
	
	//***METHODS HELPER TO GENERATE POSSIBLE ACTIONS***

	/**
	 * @return true if the MoveOvine Action is possible, else false.
	 * @throws RemoteException if there are connection problems.
	 */
	private boolean isMoveOvinePossible() throws RemoteException {
		return this.thereIsALandWithOvineNearShepherd();
	}

	/**
	 * @return true if the Coupling1 Action is possible, else false.
	 * @throws RemoteException if there are connection problems.
	 */
	private boolean isCoupling1Possible() throws RemoteException {
		return this.thereIsALandWithTwoSheepsNearShepherd();
	}

	/**
	 * @return true if the Coupling2 Action is possible, else false.
	 * @throws RemoteException if there are connection problems.
	 */
	private boolean isCoupling2Possible() throws RemoteException {
		return this.thereIsALandWithSheepAndRamNearShepherd();
	}

	/**
	 * @return true if the KillOvine Action is possible, else false.
	 * @throws RemoteException if there are connection problems.
	 */
	private boolean isKillOvinePossible() throws RemoteException {
		return (this.thereIsALandWithOvineNearShepherd() && this.haveEnoughMoneyToPaySilence());
	}

	/**
	 * @return true if the BuyCard Action is possible, else false.
	 * @throws RemoteException if there are connection problems.
	 */
	private boolean isBuyCardPossible() throws RemoteException {
		for (Card c: game.getCardsOnSales()) {
			for (Land l: game.getActivePlayer().getRoadOfShepherd(game.getActiveShepherdNumber()).getNeighboringLands()) {
				if((c.getLandType() == l.getLandType()) && (c.getPrice()<5) && (game.getActivePlayer().haveEnoughGolds(c.getPrice()))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return true if there is a Land with an Ovine in 
	 * a neighboring Lands of the Shepherd's Road, else returns false.
	 * @throws RemoteException if there are connection problems.
	 */
	private boolean thereIsALandWithOvineNearShepherd() throws RemoteException {
		for (Land l: game.getActivePlayer().getRoadOfShepherd(game.getActiveShepherdNumber()).getNeighboringLands()) {
			if(l.containsAnOvine()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return true if there is a Land with an two Sheeps in 
	 * a neighboring Lands of the Shepherd's Road, else returns false.
	 * @throws RemoteException if there are connection problems.
	 */
	private boolean thereIsALandWithTwoSheepsNearShepherd() throws RemoteException {
		for (Land l: game.getActivePlayer().getRoadOfShepherd(game.getActiveShepherdNumber()).getNeighboringLands()) {
			if(l.containsTwoSheeps()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return true if there is a Land with a Sheep and a Ram in 
	 * a neighboring Lands of the Shepherd's Road, else returns false.
	 * @throws RemoteException if there are connection problems.
	 */
	private boolean thereIsALandWithSheepAndRamNearShepherd() throws RemoteException {
		for (Land l: game.getActivePlayer().getRoadOfShepherd(game.getActiveShepherdNumber()).getNeighboringLands()) {
			if(l.contains(Ovinetype.RAM.toString()) && (l.contains(Ovinetype.SHEEP.toString()) || l.contains(Ovinetype.BLACKSHEEP.toString()))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return true if the active Player have enough golds to pay the silence of
	 * other Players in case he kills an Ovine, else returns false.
	 * @throws RemoteException if there are connection problems.
	 */
	private boolean haveEnoughMoneyToPaySilence() throws RemoteException {
		Player activePlayer = game.getActivePlayer();
		int counterNearShepherds = 0;
		for(Player p: game.getGameBoard().getPlayers()) {
			if(p != activePlayer) {
				for(Shepherd s: p.getShepherds()) {
					if(s.getRoad() != null){
						for(Road r: s.getRoad().getNeighboringRoads()) {
							if(r == activePlayer.getShepherd(game.getActiveShepherdNumber()).getRoad()) {
								counterNearShepherds++;
							}
						}
					}
				}
			}
		}
		return (activePlayer.haveEnoughGolds(2*counterNearShepherds));
	}

	//**************************************************
	
	/**
	 * Method used to check if the Action chose by the Player is a possible Action.
	 * @param actionChose to check if it's possible.
	 * @return true if the Action chose is in the List of possible Actions, else returns false.
	 */
	private boolean isValidAction(ActionType actionChose) {
		for(ActionType a: game.getGameInfo().getPossibleActions()) {
			if(a == actionChose) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Send a message to all the views(client).
	 * @param message: send to all views.
	 * @throws RemoteException if there are connection problems.
	 */
	private void sendMessageToAllViews(String message) throws RemoteException{
		for(ViewInterface view: this.views){
			if(view!=null) {
				view.printString(message);
			}
		}
	}
	
	/**
	 * Says if the ActivePlayer is connected or disconnected.
	 * @return true if the player is disconnected. 
	 */
	private boolean disconnectedPlayer(){
		return this.views.get(this.game.getIndexOfActivePlayer()) == null;
	}
	
	/**
	 * Finds a disconnected player and replaces it with null.
	 * @throws RemoteException if there are connection problems. 
	 */
	private void findDisconnectedPlayer() throws RemoteException{
		int i = 0;
		try{
			for( i = 0; i<views.size(); i++){
				if(this.views.get(i)!= null){
					this.views.get(i).printString(null);
				}
			}
		}catch(Exception e){
			ServerLogger.printOnLogger("GameController", e);
			this.views.set(i, null);
			String nickname = this.game.getGameBoard().getPlayers().get(i).getNickname();
			this.game.getObservers().set(i, null);
			Registry registry = LocateRegistry.getRegistry();
			try {
				registry.unbind(nickname);
			} catch (NotBoundException e1) {
				ServerLogger.printOnLogger("GameController", e1);
			}	
		}
	}
	
}
