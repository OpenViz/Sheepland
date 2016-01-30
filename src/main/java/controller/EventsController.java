package controller;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utility.ServerLogger;
import view.ViewInterface;
import model.EventType;
import model.Game;
import model.Land;
import model.LandType;
import model.MarketStep;
import model.Ovinetype;
import model.Player;
import model.SellingCard;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class that implements methods to execute events that occur in the game.
 */
public class EventsController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Game game;
	
	private List<ViewInterface> views;
	
	private InputController inputController;
	
	/**
	 * Builder of the EventsController.
	 * @param game where events occurs.
	 * @param views where ask and take input.
	 * @param inputController used to take input from views.
	 */
	public EventsController(Game game, List<ViewInterface> views, InputController inputController) {
		this.game = game;
		this.views = views;
		this.inputController = inputController;
	}
	
	/**
	 * Method to execute the BlackSheep move event.
	 * The black sheep is moved in a random direction if that is free of obstacles.
	 * @throws RemoteException if there are connection problems. 
	 */
	public void moveBlackSheep() throws RemoteException{
		game.setActiveEvent(model.EventType.MOVEBLACKSHEEP);
		if(game.getGameBoard().getBlackSheep().getLandPosition()!= null){
			game.rollDice();
			game.getGameBoard().getBlackSheep().move(game.getResultOfDice());
			for(Land land: game.getGameBoard().getLands()){
				if(land.contains(Ovinetype.BLACKSHEEP.toString())){
					land.subOvine(Ovinetype.BLACKSHEEP.toString());
				}
			}
			game.getGameBoard().getBlackSheep().getLandPosition().addOvine(game.getGameBoard().getBlackSheep());
		}
		game.notifyObserver();
		game.setActiveEvent(null);
	}

	/**
	 * Method to execute the grow event.
	 * Grows all the growing animal in all land.
	 * @throws RemoteException if there are connection problems. 
	 */
	public void growLambs() throws RemoteException{
		game.setActiveEvent(model.EventType.GROWTH);
		for(Land land: game.getGameBoard().getLands()){
			land.growAll();
		}
		game.notifyObserver();
		game.setActiveEvent(null);
	}

	/**
	 * Method to execute the chooseActiveShepherd event.
	 * Asks to the active PLayer to choose the Shepherd to use during his turn.
	 * @return the index number of the active Shepherd chose.
	 * @throws RemoteException if there are connection problems. 
	 */
	public int chooseActiveShepherd() throws RemoteException{
		game.setActiveEvent(model.EventType.CHOOSEACTIVESHEPHERD);
		game.notifyObserver();
		int temporany;
		if("SocketGUI".equals(views.get(game.getIndexOfActivePlayer()).getConnectionType()) || 
				"RMIGUI".equals(views.get(game.getIndexOfActivePlayer()).getConnectionType())) {
			while(true) {
				views.get(game.getIndexOfActivePlayer()).printString("Choose the shepherd you want to use(1/2): ");
				temporany = inputController.getIntFromView();
				if((temporany == 100 && "BLUE".equals(game.getActivePlayer().getColor().toString()))||(temporany == 300 && "RED".equals(game.getActivePlayer().getColor().toString()))) {
					game.setActiveEvent(null);
					return 0;
				} else if ((temporany == 200 && "BLUE".equals(game.getActivePlayer().getColor().toString()))||(temporany == 400 && "RED".equals(game.getActivePlayer().getColor().toString()))) {
					game.setActiveEvent(null);
					return 1;
				}
				views.get(game.getIndexOfActivePlayer()).printString("Retry!");
			}
		} else {
			while(true) {
				views.get(game.getIndexOfActivePlayer()).printString("Choose the shepherd you want to use(1/2): ");
				temporany = inputController.getIntFromView() -1 ;
				if(!((temporany == 0)||(temporany == 1))) {
					views.get(game.getIndexOfActivePlayer()).printString("Insert 1 or 2, retry!");
				} else {
					game.setActiveEvent(null);
					return temporany;
				}
			}
		}
	}

	/**
	 * Method to execute the Wolf move event.
	 * The Wolf is moved in a random direction if that is free of obstacles.
	 * Is also moved if every directions are busy, randomly jumping one of them.
	 * @throws RemoteException if there are connection problems. 
	 */
	public void moveWolf() throws RemoteException{
		game.setActiveEvent(model.EventType.MOVEWOLF);
		game.rollDice();
		game.getGameInfo().setEatedOvineId(game.getGameBoard().getWolf().move(game.getResultOfDice()));
		boolean temporany = true;
		for(Land land : game.getGameBoard().getLands()){
			temporany = temporany && !land.contains(Ovinetype.BLACKSHEEP.toString());
		}
		if(temporany){
			game.getGameBoard().getBlackSheep().setLandPosition(null);
		}
		game.notifyObserver();
		this.game.getGameInfo().setEatedOvineId(0);
		game.setActiveEvent(null);
	}
	
	/**
	 * Method to execute the Market event.
	 * The first player starts and can chose to sell or not some/all of his cards.
	 * Then a random players starts and he could chose to buy or not some of this selling cards.
	 * @throws RemoteException if there are connection problems.
	 */
	public void market() throws RemoteException{
		game.setActiveEvent(model.EventType.MARKET);
		List<SellingCard> cards = new ArrayList<SellingCard>();
		for(int i = 0; i <  this.game.getGameInfo().getNumberOfPlayers(); i++){
			this.game.getGameInfo().setIndexOfActivePlayer(i);
			//Controlla se il giocatore è connesso o no.
			if(views.get(game.getIndexOfActivePlayer()) != null){
				try{
					this.chooseCardToSell(cards);
				}catch(RemoteException e){
					ServerLogger.printOnLogger("EventsController", e);
					this.findDisconnectedPlayer();
				}
			}
		}
		if(cards.isEmpty()){
			this.game.getGameInfo().setIndexOfActivePlayer(0);
			return;
		}
		this.printSellingCard(cards);
		game.getGameInfo().setSelingCards(cards);
		Random random = new Random();
		int initialPlayer =  random.nextInt(game.getGameInfo().getNumberOfPlayers());
		this.game.getGameInfo().setIndexOfActivePlayer(initialPlayer);
		int activePlayer;
		while(true){
			// Checks if the Player is disconnected.
			if(views.get(game.getIndexOfActivePlayer()) != null){
				try{
					this.buyCard(cards);
				}catch(RemoteException e){
					ServerLogger.printOnLogger("EventsController", e);
					this.findDisconnectedPlayer();
				}
			}
			this.game.getGameInfo().setForNextPlayer();
			activePlayer = this.game.getIndexOfActivePlayer();
			if( initialPlayer == activePlayer ){
				break;
			}
		}
		this.game.getGameInfo().setIndexOfActivePlayer(0);
		game.setActiveEvent(null);
	}
	
	/**
	 * Method to execute the WinnerTime event.
	 * Finds the winner or winners of the game and sends it to all players.
	 * @throws RemoteException if there are connection problems.
	 */
	public void checkWinner() throws RemoteException{
		int winnerPoint = 0;
		List<Player> winnerPlayers = new ArrayList<Player>();
		for(Player player: game.getGameBoard().getPlayers()){
			player.addGolds(this.playerPoints(player));
			if(player.getGold()>winnerPoint){
				winnerPoint = player.getGold();
			}
		}
		for(Player player: game.getGameBoard().getPlayers()){
			if(player.getGold() == winnerPoint){
				winnerPlayers.add(player);
			}
		}
		this.game.getGameInfo().setWinnerPlayers(winnerPlayers);
		this.game.getGameInfo().setActiveEvent(EventType.WINNERTIME);
		this.game.notifyObserver();
		
	}

	// HELPER METHODS RELATIVE TO CHECKWINNER
	
	/**
	 * Helper method to the checkWinner.
	 * Returns the total points of the active player.
	 * @param player: where you have to count the point.
	 * @return point: that scores the active player.
	 * @throws RemoteException if there are connection problems. 
	 */
	private int playerPoints(Player player) throws RemoteException{
		int totalPoint = 0;
		for(Land land: game.getGameBoard().getLands()){
			totalPoint = totalPoint + land.numberOfOvines()*player.numberOfCard(land.getLandType());
		}
		return totalPoint;
	}

	//***HELPER METHODS RELATIVE TO MARKET***
	
	/**
	 * Helper method to the buyCard.
	 * Subtracts the specific card from the seller player and add it to the buyer player.
	 * @param nickname1: of the player who want to sell the card.
	 * @param nickname2: of the player who want to buy the card.
	 * @param type: of the selling card.
	 * @param sellingPrice: of the selling card.
	 * @throws RemoteException if there are connection problems.
	 */
	private void cardSelling(String nickname1, String nickname2, String landType, int sellingPrice) throws RemoteException {
		Player player1 = this.getPlayerOnName(nickname1);
		Player player2 = this.getPlayerOnName(nickname2);
		
		player2.subGolds(sellingPrice);
		player2.addCard(player1.subCard(landType));
		player1.addGolds(sellingPrice);
	}

	/**
	 * Helper method to the Market.
	 * The active player can buy a card from the list of selling cards.
	 * @param cards: list of cards that the player can buy.
	 * @throws RemoteException if there are connection problems.
	 */
	private void buyCard(List<SellingCard> cards) throws RemoteException {
		String answer;
		int id;
		SellingCard card;
		if(views.get(game.getIndexOfActivePlayer()) == null) {
			return;
		}
		while(!cards.isEmpty()) {
			game.getGameInfo().setMarketStep(MarketStep.YESORNOTSTEP);
			game.notifyObserver();
			views.get(game.getIndexOfActivePlayer()).printString("Do you want to buy a Card?(Y/N)");
			answer = views.get(game.getIndexOfActivePlayer()).getString();
			if("N".equalsIgnoreCase(answer)){
				break;
			}else if("Y".equalsIgnoreCase(answer)) {
				game.getGameInfo().setMarketStep(MarketStep.BUYCARDSTEP);
				game.notifyObserver();
				views.get(game.getIndexOfActivePlayer()).printString("Which card do u want to buy?");
				id = this.inputController.getIntFromView();
				card = this.getSellingCardFromList(id, cards);
				if(card != null && game.getActivePlayer().haveEnoughGolds(card.getSellingPrice())){
					if(this.contains(card.getId(), cards) 
							&& !this.getActivePlayerNickName().equals(card.getPlayerNickname())) {
						this.cardSelling(card.getPlayerNickname(), game.getActivePlayer().getNickname(), card.getCardType(), card.getSellingPrice());
						this.eliminateCard(card.getId(), cards);
						this.printSellingCard(cards);
					}else{
						if("RMI".equals(views.get(game.getIndexOfActivePlayer()).getConnectionType()) ||
								"SocketCLI".equals(views.get(game.getIndexOfActivePlayer()).getConnectionType())){
							views.get(game.getIndexOfActivePlayer()).printString("Error...");
						}	
					}
				}
			}
		}
	}

	/**
	 * Helper method to the Market.
	 * The active player can choose to sell or not one or more of his cards.
	 * He has to chose the type and the selling price( between 1 and 4).
	 * @param cards: list of selling card.
	 * @throws RemoteException if there are connection problems.
	 */
	private void chooseCardToSell(List<SellingCard> cards) throws RemoteException {
		String answer;
		int price;
		int numberOfField = this.getNumberOfCardofActivePlayer(LandType.FIELD);
		int numberOfForest = this.getNumberOfCardofActivePlayer(LandType.FOREST);
		int numberOfRiver = this.getNumberOfCardofActivePlayer(LandType.RIVER);
		int numberOfGreen = this.getNumberOfCardofActivePlayer(LandType.GREEN);
		int numberOfHill = this.getNumberOfCardofActivePlayer(LandType.HILL);
		int numberOfMountain = this.getNumberOfCardofActivePlayer(LandType.MOUNTAIN);
		int controllNumberOfCard = -1;
		if(views.get(game.getIndexOfActivePlayer())==null) {
			return;
		}
		while(true) {
			game.getGameInfo().setMarketStep(MarketStep.YESORNOTSTEP);
			game.notifyObserver();
			views.get(game.getIndexOfActivePlayer()).printString("Do you want to sell a Card?(Y/N)");
			answer = views.get(game.getIndexOfActivePlayer()).getString();
			if("N".equalsIgnoreCase(answer)) {
				break;
			}else if("Y".equalsIgnoreCase(answer)) {
				game.getGameInfo().setMarketStep(MarketStep.SELECTTYPESTEP);
				game.notifyObserver();
				views.get(game.getIndexOfActivePlayer()).printString("Which type of card do you want to sell?");
				answer = views.get(game.getIndexOfActivePlayer()).getString();
				controllNumberOfCard = -1;
				if(answer.equals(LandType.FIELD.toString())) {
					controllNumberOfCard = numberOfField;
				}else if(answer.equalsIgnoreCase(LandType.FOREST.toString())) {
					controllNumberOfCard = numberOfForest;
				}else if(answer.equalsIgnoreCase(LandType.MOUNTAIN.toString())) {
					controllNumberOfCard = numberOfMountain;
				}else	if(answer.equalsIgnoreCase(LandType.RIVER.toString())) {
					controllNumberOfCard = numberOfRiver;
				}else if(answer.equalsIgnoreCase(LandType.GREEN.toString())) {
					controllNumberOfCard = numberOfGreen;
				}else if(answer.equalsIgnoreCase(LandType.HILL.toString())) {
					controllNumberOfCard = numberOfHill;
				}
				if(this.game.getGameBoard().getPlayers().get(game.getIndexOfActivePlayer()).searchCard(answer) 
						&& (controllNumberOfCard>0)) {
					
					game.getGameInfo().setMarketStep(MarketStep.SELECTPRICESTEP);
					game.notifyObserver();
					views.get(game.getIndexOfActivePlayer()).printString("Which price?");
					price = inputController.getIntFromView();
					if(price >= 1 && price <= 4) {
						
						if(answer.equals(LandType.FIELD.toString())) {
							numberOfField--;
						}else if(answer.equalsIgnoreCase(LandType.FOREST.toString())) {
							numberOfForest--;
						}else if(answer.equalsIgnoreCase(LandType.MOUNTAIN.toString())) {
							numberOfMountain--;
						}else	if(answer.equalsIgnoreCase(LandType.RIVER.toString())) {
							numberOfRiver--;
						}else if(answer.equalsIgnoreCase(LandType.GREEN.toString())) {
							numberOfGreen--;
						}else if(answer.equalsIgnoreCase(LandType.HILL.toString())) {
							numberOfHill--;
						}
						
						cards.add(new SellingCard(answer, price,this.getActivePlayerNickName()));							
					}else{
						views.get(game.getIndexOfActivePlayer()).printString("Error...");
					}
				}else{
					if("RMI".equals(views.get(game.getIndexOfActivePlayer()).getConnectionType()) ||
							"SocketCLI".equals(views.get(game.getIndexOfActivePlayer()).getConnectionType())){
						views.get(game.getIndexOfActivePlayer()).printString("Error...");
					}
					}
				}
		}
	}
	
	/**
	 * Helper method to the Market.
	 * Prints the list of all SellingCard.
	 * It is showed only if the player is using the CLI.
	 * @param cards: that are printed.
	 * @throws RemoteException: connection issue.
	 */
	private void printSellingCard(List<SellingCard> cards) throws RemoteException {
		for(ViewInterface view: this.views) {
			if(view== null ||
					"RMIGUI".equals(view.getConnectionType()) ||
					"SocketGUI".equals(view.getConnectionType())) {
				return;
			}
			view.printString("Card on selling[Type/Price/Owner/ID]:");
			for(SellingCard card : cards){
				view.printString(card.getCardType()+" "+card.getSellingPrice() + " "+ card.getPlayerNickname()
						+ " " +card.getId() );
			}
		}
	}
	
	/**
	 * Tells if the list of cards( parameter) contains the card with specific id(parameter).
	 * @param id: of the card you want to search.
	 * @param cards: where you want to search the card.
	 * @return true if there is the card, else returns false.
	 */
	private boolean contains(int id, List<SellingCard> cards) {
		for(SellingCard card: cards) {
			if(card.getId()==id) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Eliminates from cards(parameter) the card with the same id(parameter) if it exists.
	 * @param id: of the card that has to be removed.
	 * @param cards: where search the card to remove.
	 */
	private void eliminateCard(int id, List<SellingCard> cards) {
		for(SellingCard card: cards) {
			if(card.getId() == id) {
				cards.remove(card);
				return;
			}
		}
		
	}
	
	/**
	 * Method helper that saves chain.
	 * Returns the nickname of the active player.
	 * @return nickname: of the active player.
	 * @throws RemoteException if there are connection problems.
	 */
	private String getActivePlayerNickName() throws RemoteException {
		return game.getActivePlayer().getNickname();
	}
	
	/**
	 * Gets the {@link Player} of the model by the nickname(parameter).
	 * @param nickname: of the player i want to returns.
	 * @return player: with this nickname if it exists, else returns null.
	 * @throws RemoteException if there are connection problems.
	 */
	private Player getPlayerOnName(String nickname) throws RemoteException {
		for(Player e: game.getGameBoard().getPlayers()){
			if(e.getNickname().equals(nickname)){
				return e;
			}	
		}
		return null;
	}

	/**
	 * Gets the selling card with specific id(parameter) from a list of cars(parameter).
	 * @param id: id of the card you want.
	 * @param cards: list of SellingCard where search the card.
	 * @return card: that you want, else returns null.
	 */
	private SellingCard getSellingCardFromList(int id,List<SellingCard> cards) {
		for(SellingCard card: cards){
			if(card.getId() == id){
				return card;
			}
		}
		return null;	
	}
	
	/**
	 * Returns the number of card of specific LandType(parameter) of the active player.
	 * @param landType: of the cardType
	 * @return the number of card that the ActivePlayer has.
	 * @throws RemoteException if there are connection problems.
	 */
	private int getNumberOfCardofActivePlayer(LandType landType) throws RemoteException {
		return game.getGameBoard().getPlayers().get(game.getIndexOfActivePlayer()).numberOfCard(landType);
		
	}
	
	/**
	 * Find a disconnected player and replace it with null.
	 * @throws RemoteException if there are connection problems. 
	 * @throws NotBoundException if there is no such element in the registry
	 */
	private void findDisconnectedPlayer() throws RemoteException {
		int i = 0;
		try{
			for( i = 0; i<views.size(); i++){
				if(this.views.get(i)!= null){
					this.views.get(i).printString(null);
				}
			}
		}catch(Exception e) {
			ServerLogger.printOnLogger("EventsController", e);
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
