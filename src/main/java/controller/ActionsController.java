package controller;

import java.awt.Point;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utility.*;
import view.ViewInterface;
import model.Card;
import model.Game;
import model.Lamb;
import model.Land;
import model.MoveOvineStep;
import model.Ovine;
import model.Player;
import model.Road;
import model.Sheep;
import model.Shepherd;
import model.RoadState;
import model.LandType;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Class that implements methods to execute the actions of the game.
 */
public class ActionsController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private List<ViewInterface> views;
	
	private ConverterController converterController;
	private InputController inputController;
	
	/**
	 * Builder of ActionsController.
	 * @param game where i perform action.
	 * @param views where ask and take input.
	 * @param converterController used to convert ids in objects.
	 * @param inputController used to take input from views.
	 */
	public ActionsController(Game game, List<ViewInterface> views, ConverterController converterController, InputController inputController){
		this.game = game;
		this.views = views;
		this.inputController = inputController;
		this.converterController = converterController;
	}
	
	/**
	 * Method used to execute the action moveShepherd() by the active Player.
	 * @return true if the action is done, instead false.
	 * @throws RemoteException if there are connection problems.
	 */
	public boolean moveShepherd() throws RemoteException{
		Player player = game.getActivePlayer();
		Road roadToGo = null ;
		int shepherdNumber = this.game.getActiveShepherdNumber();

		views.get(game.getIndexOfActivePlayer()).printString("Choose the road where you want to move your shepherd: ");		
		roadToGo = inputController.getValidRoad();
		
		if(player.getRoadOfShepherd(shepherdNumber) == null) {
			player.getShepherd(shepherdNumber).setRoad(roadToGo);
			player.getShepherd(shepherdNumber).setShepherdPosition(roadToGo.getRoadPosition());
			player.getRoadOfShepherd(shepherdNumber).setRoadState(RoadState.SHEPHERD);
			
			player.getShepherd(shepherdNumber).setPlaced(true);
			
			this.game.notifyObserver();
			return true;
		} else {
			if(!player.getRoadOfShepherd(shepherdNumber).isNeighboringRoad(roadToGo)) {
				player.subGolds(1);
			}
			if(game.getGameBoard().getNumberFence() > 0) {
				game.getGameBoard().subNumberOfFence();
				player.getRoadOfShepherd(shepherdNumber).setRoadState(RoadState.FENCED);
			} else {
				player.getRoadOfShepherd(shepherdNumber).setRoadState(RoadState.SPECIALFENCED);
			}
		
			player.getShepherd(shepherdNumber).setRoad(roadToGo);
			player.getShepherd(shepherdNumber).setShepherdPosition(roadToGo.getRoadPosition());
			player.getRoadOfShepherd(shepherdNumber).setRoadState(RoadState.SHEPHERD);
	
			this.game.notifyObserver();
			return true;
		}
	}
	
	/**
	 * Method used to execute the action moveOvine() by the active Player.
	 * @return true if the action is done, instead false.
	 * @throws RemoteException if there are connection problems.
	 */	
	public boolean moveOvine() throws RemoteException {
		int shepherdNumber = game.getActiveShepherdNumber();
		int ovineToMoveId;
		Land startingLand = null;
		Land finalLand = null;
		
		game.getGameInfo().setMoveOvineStep(MoveOvineStep.DRAG);
		this.setValidLandsIdsWithOvine(shepherdNumber);

		views.get(game.getIndexOfActivePlayer()).printString("Choose the ovine that you want to move: ");
		ovineToMoveId = inputController.getValidOvineId();
		startingLand = converterController.getLandOnOvineId(ovineToMoveId);
		
		game.getGameInfo().setMovedOvineId(ovineToMoveId);
		this.game.clearValidLandsIds();
		game.getGameInfo().setMoveOvineStep(MoveOvineStep.DROP);
		this.setValidLandsIdsMoveOvine(shepherdNumber, startingLand.getId());
		
		views.get(game.getIndexOfActivePlayer()).printString("Choose the land where you want to move the ovine: ");
		finalLand = inputController.getValidLand();
		
		if("SocketGUI".equals(views.get(game.getIndexOfActivePlayer()).getConnectionType()) || 
				"RMIGUI".equals(views.get(game.getIndexOfActivePlayer()).getConnectionType())) {
			this.performMoveOvineGUI(ovineToMoveId, startingLand, finalLand);
		} else {
			this.performMoveOvineCLI(ovineToMoveId, startingLand, finalLand);
		}
		
		game.clearValidLandsIds();
		game.getGameInfo().setMoveOvineStep(MoveOvineStep.END);
		game.notifyObserver();
		game.getGameInfo().setMovedOvineId(0);
		game.getGameInfo().setMoveOvineStep(null);
		return true;
	}

	/**
	 * Method used to execute the action coupling1() by the active Player.
	 * @return true if the action is done, instead false.
	 * @throws RemoteException if there are connection problems.
	 */
	public boolean coupling1() throws RemoteException {
		int shepherdNumber = game.getActiveShepherdNumber();
		Land landToCoupling1;
		this.setValidLandsIdsCoupling1(shepherdNumber);
		
		views.get(game.getIndexOfActivePlayer()).printString("Choose the land where you want to coupling1: ");
		landToCoupling1 = inputController.getValidLand();
		
		this.performCoupling1(landToCoupling1, shepherdNumber);
		
		this.game.clearValidLandsIds();
		this.game.notifyObserver();
		return true;
	}
	
	/**
	 * Method used to execute the action coupling2() by the active Player.
	 * @return true if the action is done, instead false.
	 * @throws RemoteException if there are connection problems.
	 */
	public boolean coupling2() throws RemoteException {
		int shepherdNumber = game.getActiveShepherdNumber();
		Land landToCoupling2;
		this.setValidLandsIdsCoupling2(shepherdNumber);
		
		views.get(game.getIndexOfActivePlayer()).printString("Choose the land where you want to coupling2: ");
		landToCoupling2 = inputController.getValidLand();
		
		this.performCoupling2(landToCoupling2);
		
		this.game.clearValidLandsIds();
		this.game.notifyObserver();
		return true;
	}
	
	
	/**
	 * Method used to execute the action killOvine() by the active Player.
	 * @return true if the action is done, instead false.
	 * @throws RemoteException if there are connection problems.
	 */
	public boolean killOvine() throws RemoteException {
			int shepherdNumber = game.getActiveShepherdNumber();
			int ovineToKillId;
			Land ovineToKillLand = null;
			
			this.setValidLandsIdsWithOvine(shepherdNumber);
		
			views.get(game.getIndexOfActivePlayer()).printString("Choose the ovine that you want to kill: ");
			ovineToKillId = inputController.getValidOvineId();
			ovineToKillLand = converterController.getLandOnOvineId(ovineToKillId);
			
			this.performKillOvine(ovineToKillId, ovineToKillLand, shepherdNumber);
			
			this.game.clearValidLandsIds();
			this.game.notifyObserver();
			this.game.getGameInfo().setKilledOvineId(0);
			return true;
	}
	
	/**
	 * Method used to execute the action buyCard() by the active Player. 
	 * @return true if the action is done, instead false.
	 * @throws RemoteException if there are connection problems.
	 */
	public boolean buyCard() throws RemoteException {
		Player player = game.getActivePlayer();
		int shepherdNumber = game.getActiveShepherdNumber();
		LandType landType;
		Card cardToBuy;
		
		totalInput: while(true) {
			while(true) {
				try{
					views.get(game.getIndexOfActivePlayer()).printString("Choose the card you want to buy: ");
					landType = Enum.valueOf(LandType.class, views.get(game.getIndexOfActivePlayer()).getString().toUpperCase());
					break;
				}catch(IllegalArgumentException e) {
					ServerLogger.printOnLogger("ActionsController", e);
				}
			}
			for(Land l: player.getRoadOfShepherd(shepherdNumber).getNeighboringLands()) {
				if(l.getLandType().equals(landType)) {
					cardToBuy = this.searchCardOnLandType(landType);
					if(cardToBuy.getPrice()>4){
						views.get(game.getIndexOfActivePlayer()).printString("SOLD OUT!");
					}else if(player.haveEnoughGolds(cardToBuy.getPrice())) {
						break totalInput;
					} else {
						views.get(game.getIndexOfActivePlayer()).printString("You haven't enough moneys, retry!");
					}
				}
			}
			views.get(game.getIndexOfActivePlayer()).printString("Your shepherd isn't near to a land with the type of the card you want to buy, retry!");
		}
		
		player.subGolds(cardToBuy.getPrice());
		player.addCard(cardToBuy);
		this.cardUpOnLandType(landType);
		this.game.notifyObserver();
		return true;
	}

	/**
	 * Method helper to buyCard().
	 * Return the Card of the list of cards on sales
	 * with the same landType as the landTpe taken as parameter.
	 * @param type of the card to search in the list of cards.
	 * @return the card searched on type,
	 * @throws RemoteException if there are connection problems.
	 */
	private Card searchCardOnLandType(LandType landType) throws RemoteException {
		for(Card c: game.getCardsOnSales()) {
			if(c.getLandType().equals(landType)) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Method helper to buyCard().
	 * Takes the card of landType inserted as parameter and substitutes it in the List of the cards on sales
	 * with new Card with the same type and the price increased by 1.
	 * @param type of the card to which increase the price.
	 * @throws RemoteException if there are connection problems.
	 */
	private void cardUpOnLandType(LandType landType) throws RemoteException {
		for (int i = 0; i < game.getCardsOnSales().size(); i++) {
			if(game.getCardsOnSales().get(i).getLandType().equals(landType)) {
				Card temp = new Card(game.getCardsOnSales().get(i).getLandType(), game.getCardsOnSales().get(i).getPrice() + 1);
				game.getCardsOnSales().set(i, temp);
			}
		}
	}
	
	/**
	 * Method helper to moveOvine() and killOvine().
	 * Checks the land that are valid to perform a move ovine drag or a kill ovine for the active shepherd of the active player
	 * and set their ids in the validLandsIds list.
	 * @param shepherdNumber of the active shepherd.
	 * @throws RemoteException if there are connection problems.
	 */
	private void setValidLandsIdsWithOvine(int shepherdNumber) throws RemoteException {
		List<Integer> validLandsIds = new ArrayList<Integer>();
		for(int i = 0; i < game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).size(); i++){
			if(game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).get(i).containsAnOvine()){
				validLandsIds.add(game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).get(i).getId());
			}
		}
		game.setValidLandsIds(validLandsIds);
	}
	
	/**
	 * Method helper to moveOvine().
	 * Checks the land that are valid to perform a move ovine drop for the active shepherd of the active player
	 * and set their ids in the validLandsIds list.
	 * @param shepherdNumber of the active shepherd.
	 * @throws RemoteException if there are connection problems.
	 */
	private void setValidLandsIdsMoveOvine(int shepherdNumber, int startingLandId) throws RemoteException {
		List<Integer> validLandsIds = new ArrayList<Integer>();
		for(int i = 0; i < game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).size(); i++){
			if(game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).get(i).getId() != startingLandId){
				validLandsIds.add(game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).get(i).getId());
			}
		}
		game.setValidLandsIds(validLandsIds);
	}
	
	/**
	 * Method helper to coupling1().
	 * Checks the land that are valid to perform a coupling1 for the active shpherd of the active player
	 * and set their ids in the validLandsIds list.
	 * @param shepherdNumber of the active shepherd.
	 * @throws RemoteException if there are connection problems.
	 */
	private void setValidLandsIdsCoupling1(int shepherdNumber) throws RemoteException {
		List<Integer> validLandsIds = new ArrayList<Integer>();
		for(int i = 0; i < game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).size(); i++){
			if(game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).get(i).containsTwoSheeps()){
				validLandsIds.add(game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).get(i).getId());
			}
		}
		game.setValidLandsIds(validLandsIds);
	}
	
	/**
	 * Method helper to coupling2().
	 * Checks the land that are valid to perform a coupling2 for the active shpherd of the active player
	 * and set their ids in the validLandsIds list.
	 * @param shepherdNumber of the active shepherd.
	 * @throws RemoteException if there are connection problems.
	 */
	private void setValidLandsIdsCoupling2(int shepherdNumber) throws RemoteException {
		List<Integer> validLandsIds = new ArrayList<Integer>();
		for(int i = 0; i < game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).size(); i++){
			if(game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).get(i).containsSheepAndRam()){
				validLandsIds.add(game.getNeighboringLandsOfActivePlayerShepherd(shepherdNumber).get(i).getId());
			}
		}
		game.setValidLandsIds(validLandsIds);
	}
	
	/**
	 * Methods helper to moveOvine().
	 * Perform the move ovine steps that modifies the model.
	 * Takes the ovine with the ovineId inserted as parameter from the startingLand and put it in the finalLand.
	 * Invokes a method to generate a random point in the finalLand and set that as the new position Point of the ovine moved.  
	 * @param ovineId of the moved ovine.
	 * @param startingLand of the moved ovine.
	 * @param finalLand of the moved ovine.
	 * @throws RemoteException if there are connection problems. 
	 */
	private void performMoveOvineCLI(int ovineId, Land startingLand, Land finalLand) {
		Ovine movedOvine = startingLand.removeOvineOnId(ovineId);
		finalLand.addOvine(movedOvine);
		movedOvine.setOvinePosition(this.getRandomPointInALand(finalLand));
	}
	
	/**
	 * Method helper for performMoveOvineCLI().
	 * Extract a random Point from the landPoints area of the land taken as parameter.
	 * @param land where extract a random point.
	 * @return the Point extracted.
	 */
	private Point getRandomPointInALand(Land land) {
		Random rand = new Random();
		return land.getLandPoints().get(rand.nextInt(land.getLandPoints().size()));
	}
	
	/**
	 * Methods helper to moveOvine().
	 * Perform the move ovine steps that modifies the model.
	 * Takes the ovine with the ovineId inserted as parameter from the startingLand and put it in the finalLand.
	 * Takes the Point coordinates from the view of the active player and set that as the new position Point of the ovine moved.  
	 * @param ovineId of the moved ovine.
	 * @param startingLand of the moved ovine.
	 * @param finalLand of the moved ovine.
	 * @throws RemoteException if there are connection problems. 
	 */
	private void performMoveOvineGUI(int ovineId, Land startingLand, Land finalLand) throws RemoteException { // potrei metterne 2 di default per togliere l'if, tanto la cli se ne frega(farlo solo se riusciamo a togliere tutti gli if)
		Ovine movedOvine = startingLand.removeOvineOnId(ovineId);
		finalLand.addOvine(movedOvine);
		int x = inputController.getIntFromView();
		int y = inputController.getIntFromView();
		movedOvine.setOvinePosition(new Point(x, y));
	}
	
	/**
	 * Methods helper to coupling1().
	 * Perform the coupling1 steps that modifies the model.
	 * Roll a dice and if the number is equal to the number of road on active shepherd's road 
	 * in the land inserted as parameter is randomly generated another ovine.
	 * @param coupling1Land choosed for coupling1.
	 * @param shepherdNumber of the active Shepherd.
	 * @throws RemoteException if there are connection problems.
	 */
	private void performCoupling1(Land coupling1Land, int shepherdNumber) throws RemoteException{
		game.rollDice();
		views.get(game.getIndexOfActivePlayer()).printString("Dice result: " + game.getResultOfDice());
		if(game.getResultOfDice() == game.getActivePlayer().getRoadOfShepherd(shepherdNumber).getNumber()) {
			Ovine o = new Sheep();
			coupling1Land.addOvine(o);
			o.setOvinePosition(coupling1Land.getRandomLandPoint());
		}
	}
	
	/**
	 * Methods helper to coupling2().
	 * Perform the coupling2 steps that modifies the model.
	 * Roll a dice and if the number is equal to the number of road on active shepherd's road 
	 * in the land inserted as parameter is randomly generated another ovine.
	 * @param coupling2Land choosed for coupling2.
	 * @param shepherdNumber of the active Shepherd.
	 * @throws RemoteException if there are connection problems.
	 */
	private void performCoupling2(Land coupling2Land) throws RemoteException {
		game.rollDice();
		Ovine o = new Lamb();
		coupling2Land.addOvine(o);
		o.setOvinePosition(coupling2Land.getRandomLandPoint());
	}
	
	/**
	 * Methods helper to killOvine().
	 * Perform the kill ovine steps that modifies the model.
	 * Roll a dice and if the number is equal to the number of road on active shepherd's road 
	 * the ovine with the ovineId taken as parameter is removed, then invoke the method paySilence().
	 * @param ovineId of the ovine that player want to kill.
	 * @param shootedOvineLand of the ovine that player want to kill. 
	 * @param shepherdNumber of the active Shepherd.
	 * @throws RemoteException if there are connection problems.
	 */
	private void performKillOvine(int ovineId, Land shootedOvineLand, int shepherdNumber) throws RemoteException {
		game.rollDice();
		views.get(game.getIndexOfActivePlayer()).printString("Dice result: " + game.getResultOfDice());
		if(game.getResultOfDice() == game.getActivePlayer().getRoadOfShepherd(shepherdNumber).getNumber()) {
			game.getGameInfo().setKilledOvineId(ovineId);
			shootedOvineLand.removeOvineOnId(ovineId);
			this.paySilence(shepherdNumber);
		}
	}	
	

	/**
	 * Method helper to killOvine().
	 * Every other player that have a shepherd in a neighboring road of the active player's active shepherd roll a dice.
	 * The active player gives 2 golds to every player that rolls a result >=5.
	 * @param shepherdNumber of the active shepherd of the active player.
	 * @throws RemoteException if there are connection problems.
	 */
	private void paySilence(int shepherdNumber) throws RemoteException {
		Player activePlayer = game.getActivePlayer();
		for(Player p: game.getGameBoard().getPlayers()){
			if(p != activePlayer) {
				for(Shepherd s: p.getShepherds()) {
					if(s.getRoad()!= null){
						for(Road r: s.getRoad().getNeighboringRoads()) {
							if(r == activePlayer.getShepherd(shepherdNumber).getRoad()) {
								game.rollDice();
								if(game.getResultOfDice() >= 5) {
									activePlayer.subGolds(2);
									p.addGolds(2);
								}
							}
						}
					}
				}
			}			
		}
	}
}
	
	
