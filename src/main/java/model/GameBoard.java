package model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class GameBoard  implements  Serializable, GameBoardInterface {
	private static final long serialVersionUID = 1L;
	
	private static final int STARTINGGOLDSFOR2PLAYERS = 40;
	private static final int STARTINGGOLDS = 30;
	
	private List<Player> players;
	private List<Road> roads;
	private List<Land> lands;
	// List of the starting Cards that are randomly distributed to Players.
	private List<Card> startingCards;
	// List of Cards that Players can buy during the Game.
	private List<Card> cards;
	private BlackSheep blacksheep;
	private Wolf wolf;
	private Dice dice;
	private int numberFence;
	
	/**
	 * Builder of the GameBoard.
	 * It initialize every element that is on the GameBoard calling their constructors.
	 * It also set the neighboring relationships.
	 * @throws RemoteException if there are connection problems.
	 */
	public GameBoard() throws RemoteException {
		super();
		players = new ArrayList<Player>();
		lands  = new ArrayList<Land>();
		roads  = new ArrayList<Road>();
		cards  = new ArrayList<Card>();
		startingCards = new ArrayList<Card>();
		numberFence = 20;
		//Setto l'id a 0
		Identifier.setCounter();
		
		
		// STARTING CARDS GENERATOR
		startingCards.add(new Card(LandType.MOUNTAIN,-1));
		startingCards.add(new Card(LandType.FOREST,-1));
		startingCards.add(new Card(LandType.HILL,-1));
		startingCards.add(new Card(LandType.FIELD,-1));
		startingCards.add(new Card(LandType.GREEN,-1));
		startingCards.add(new Card(LandType.RIVER,-1));
		
		// CARDS GENERATOR
		cards.add(new Card(LandType.MOUNTAIN, 0));
		cards.add(new Card(LandType.FOREST, 0));
		cards.add(new Card(LandType.HILL, 0));
		cards.add(new Card(LandType.FIELD, 0));
		cards.add(new Card(LandType.GREEN, 0));
		cards.add(new Card(LandType.RIVER, 0));
		
		// LANDS GENERATOR
		// index of list = 0; ID = 0; Type = SHEEPSBURG
		lands.add(new Land(LandType.SHEEPSBURG));
		// index of list = 1-2-3; ID = 1-2-3; Type = MOUNTAIN
		for (int i = 0; i < 3; i++) {
			lands.add(new Land(LandType.MOUNTAIN)); 
		}
		// index of list = 4-5-6; ID = 4-5-6; Type = FOREST
		for (int i = 0; i < 3; i++) {
			lands.add(new Land(LandType.FOREST));	
		}
		// index of list = 7-8-9; ID = 7-8-9; Type = HILL
		for (int i = 0; i < 3; i++) {
			lands.add(new Land(LandType.HILL)); 	
		}
		// index of list = 10-11-12; ID = 10-11-12; Type = FIELD
		for (int i = 0; i < 3; i++) {
			lands.add(new Land(LandType.FIELD));	
		}
		// index of list = 13-14-15; ID = 13-14-15; Type = GREEN
		for (int i = 0; i < 3; i++) {
			lands.add(new Land(LandType.GREEN));	
		}
		// index of list = 16-17-18; ID = 16-17-18; Type = RIVER
		for (int i = 0; i < 3; i++) {
			lands.add(new Land(LandType.RIVER));	
		}
		
		// ROADS GENERATOR
		// index of list = 0/8; ID = 19/27; number = 1
		this.addRoadsOfNumber(1, 9);
		//road with ID == 19 have as neighbors lands with ID == 12 && ID == 2 
		this.linkRoadLand(0, 12);
		this.linkRoadLand(0, 2);
		// road with ID == 20 have as neighbors lands with ID == 13 && ID == 14
		this.linkRoadLand(1, 13);
		this.linkRoadLand(1, 14);
		// road with ID == 21 have as neighbors lands with ID == 1 && ID == 0
		this.linkRoadLand(2, 1);
		this.linkRoadLand(2, 0);
		// road with ID == 22 have as neighbors lands with ID == 3 && ID == 8
		this.linkRoadLand(3, 3);
		this.linkRoadLand(3, 8);
		// road with ID == 23 have as neighbors lands with ID == 10 && ID == 15
		this.linkRoadLand(4, 10);
		this.linkRoadLand(4, 15);
		// road with ID == 24 have as neighbors lands with ID == 7 && ID == 18
		this.linkRoadLand(5, 7);
		this.linkRoadLand(5, 18);
		//road with ID == 25 have as neighbors lands with ID == 4 && ID == 5
		this.linkRoadLand(6, 4);
		this.linkRoadLand(6, 5);
		// road with ID == 26 have as neighbors lands with ID == 6 && ID == 16
		this.linkRoadLand(7, 6);
		this.linkRoadLand(7, 16);
		// road with ID == 27 have as neighbors lands with ID == 9 && ID == 17
		this.linkRoadLand(8, 9);
		this.linkRoadLand(8, 17);
		
		// index of list = 9/17; ID = 28/36; number = 2
		this.addRoadsOfNumber(2, 9);
		// road with ID == 28 have as neighbors lands with ID == 11 && ID == 13
		this.linkRoadLand(9, 11);
		this.linkRoadLand(9, 13);
		// road with ID == 29 have as neighbors lands with ID == 12 && ID == 1
		this.linkRoadLand(10, 12);
		this.linkRoadLand(10, 1);
		// road with ID == 30 have as neighbors lands with ID == 2 && ID == 3
		this.linkRoadLand(11, 2);
		this.linkRoadLand(11, 3);
		// road with ID == 31 have as neighbors lands with ID == 10 && ID == 14
		this.linkRoadLand(12, 10);
		this.linkRoadLand(12, 14);
		// road with ID == 32 have as neighbors lands with ID == 7 && ID == 0
		this.linkRoadLand(13, 7);
		this.linkRoadLand(13, 0);
		// road with ID == 33 have as neighbors lands with ID == 8 && ID == 18
		this.linkRoadLand(14, 8);
		this.linkRoadLand(14, 18);
		// road with ID == 34 have as neighbors lands with ID == 15 && ID == 4
		this.linkRoadLand(15, 15);
		this.linkRoadLand(15, 4);
		// road with ID == 35 have as neighbors lands with ID == 6 && ID == 17
		this.linkRoadLand(16, 6);
		this.linkRoadLand(16, 17);
		// road with ID == 36 have as neighbors lands with ID == 5 && ID == 16
		this.linkRoadLand(17, 5);
		this.linkRoadLand(17, 16);
		
		// index of list = 18/24; ID = 37/43; number = 3
		this.addRoadsOfNumber(3, 7);
		// road with ID == 37 have as neighbors lands with ID == 12 && ID == 11
		this.linkRoadLand(18, 12);
		this.linkRoadLand(18, 11);
		// road with ID == 38 have as neighbors lands with ID == 13 && ID == 10
		this.linkRoadLand(19, 13);
		this.linkRoadLand(19, 10);
		// road with ID == 39 have as neighbors lands with ID == 2 && ID == 1
		this.linkRoadLand(20, 2);
		this.linkRoadLand(20, 1);
		// road with ID == 40 have as neighbors lands with ID == 3 && ID == 7
		this.linkRoadLand(21, 3);
		this.linkRoadLand(21, 7);
		// road with ID == 41 have as neighbors lands with ID == 14 && ID == 15
		this.linkRoadLand(22, 14);
		this.linkRoadLand(22, 15);
		// road with ID == 42 have as neighbors lands with ID == 0 && ID == 18
		this.linkRoadLand(23, 0);
		this.linkRoadLand(23, 18);
		// road with ID == 43 have as neighbors lands with ID == 6 && ID == 5
		this.linkRoadLand(24, 6);
		this.linkRoadLand(24, 5);
		
		// index of list = 25/30; ID = 44/49; number = 4
		this.addRoadsOfNumber(4, 6);
		// road with ID == 44 have as neighbors lands with ID == 11 && ID == 10
		this.linkRoadLand(25, 11);
		this.linkRoadLand(25, 10);
		// road with ID == 45 have as neighbors lands with ID == 1 && ID == 7
		this.linkRoadLand(26, 1);
		this.linkRoadLand(26, 7);
		// road with ID == 46 have as neighbors lands with ID == 14 && ID == 4
		this.linkRoadLand(27, 14);
		this.linkRoadLand(27, 4);
		// road with ID == 47 have as neighbors lands with ID == 0 && ID == 6
		this.linkRoadLand(28, 0);
		this.linkRoadLand(28, 6);
		// road with ID == 48 have as neighbors lands with ID == 18 && ID == 9
		this.linkRoadLand(29, 18);
		this.linkRoadLand(29, 9);
		// road with ID == 49 have as neighbors lands with ID == 15 && ID == 5
		this.linkRoadLand(30, 15);
		this.linkRoadLand(30, 5);
		
		// index of list = 31/36; ID = 50/55; number = 5
		this.addRoadsOfNumber(5, 6);
		// road with ID == 50 have as neighbors lands with ID == 1 && ID == 10
		this.linkRoadLand(31, 1);
		this.linkRoadLand(31, 10);
		// road with ID == 51 have as neighbors lands with ID == 2 && ID == 7
		this.linkRoadLand(32, 2);
		this.linkRoadLand(32, 7);
		// road with ID == 52 have as neighbors lands with ID == 0 && ID == 15
		this.linkRoadLand(33, 0);
		this.linkRoadLand(33, 15);
		// road with ID == 53 have as neighbors lands with ID == 18 && ID == 6
		this.linkRoadLand(34, 18);
		this.linkRoadLand(34, 6);
		// road with ID == 54 have as neighbors lands with ID == 8 && ID == 9
		this.linkRoadLand(35, 8);
		this.linkRoadLand(35, 9);
		// road with ID == 55 have as neighbors lands with ID == 16 && ID == 17
		this.linkRoadLand(36, 16);
		this.linkRoadLand(36, 17);
		
		// index of list = 37/41; ID = 56/60; number = 6
		this.addRoadsOfNumber(6, 5);
		// road with ID == 56 have as neighbors lands with ID == 11 && ID == 1
		this.linkRoadLand(37, 11);
		this.linkRoadLand(37, 1);
		// road with ID == 57 have as neighbors lands with ID == 10 && ID == 0
		this.linkRoadLand(38, 10);
		this.linkRoadLand(38, 0);
		// road with ID == 58 have as neighbors lands with ID == 7 && ID == 8
		this.linkRoadLand(39, 7);
		this.linkRoadLand(39, 8);
		// road with ID == 59 have as neighbors lands with ID == 15 && ID == 6
		this.linkRoadLand(40, 15);
		this.linkRoadLand(40, 6);
		// road with ID == 60 have as neighbors lands with ID == 18 && ID == 17
		this.linkRoadLand(41, 18);
		this.linkRoadLand(41, 17);
		
		// ROADS LINKATOR
		// Roads with number 1
		// Road with ID == 19
		this.linkTwoRoads(0, 10);
		this.linkTwoRoads(0, 20);
		// Road with ID == 20
		this.linkTwoRoads(1, 19);
		this.linkTwoRoads(1, 12);
		// Road with ID == 21
		this.linkTwoRoads(2, 31);
		this.linkTwoRoads(2, 26);
		this.linkTwoRoads(2, 38);
		this.linkTwoRoads(2, 13);
		// Road with ID == 22
		this.linkTwoRoads(3, 21);
		this.linkTwoRoads(3, 39);
		// Road with ID == 23
		this.linkTwoRoads(4, 12);
		this.linkTwoRoads(4, 38);
		this.linkTwoRoads(4, 22);
		this.linkTwoRoads(4, 33);
		// Road with ID == 24
		this.linkTwoRoads(5, 13);
		this.linkTwoRoads(5, 39);
		this.linkTwoRoads(5, 23);
		this.linkTwoRoads(5, 14);
		// Road with ID == 25
		this.linkTwoRoads(6, 15);
		this.linkTwoRoads(6, 30);
		// Road with ID == 26
		this.linkTwoRoads(7, 24);
		this.linkTwoRoads(7, 16);
		this.linkTwoRoads(7, 17);
		this.linkTwoRoads(7, 36);
		// Road with ID == 27
		this.linkTwoRoads(8, 29);
		this.linkTwoRoads(8, 41);
		// Roads with number 2
		// Road with ID == 28
		this.linkTwoRoads(9, 13);
		this.linkTwoRoads(9, 39);
		this.linkTwoRoads(9, 23);
		this.linkTwoRoads(9, 14);
		// Road with ID == 29
		this.linkTwoRoads(10, 18);
		this.linkTwoRoads(10, 0);
		this.linkTwoRoads(10, 37);
		this.linkTwoRoads(10, 20);
		// Road with ID == 30
		this.linkTwoRoads(11, 32);
		this.linkTwoRoads(11, 21);
		// Road with ID == 31
		this.linkTwoRoads(12, 1);
		this.linkTwoRoads(12, 19);
		this.linkTwoRoads(12, 4);
		this.linkTwoRoads(12, 22);
		// Road with ID == 32
		this.linkTwoRoads(13, 2);
		this.linkTwoRoads(13, 26);
		this.linkTwoRoads(13, 5);
		this.linkTwoRoads(13, 23);
		// Road with ID == 33
		this.linkTwoRoads(14, 5);
		this.linkTwoRoads(14, 39);
		this.linkTwoRoads(14, 35);
		this.linkTwoRoads(14, 29);
		// Road with ID == 34
		this.linkTwoRoads(15, 27);
		this.linkTwoRoads(15, 22);
		this.linkTwoRoads(15, 30);
		this.linkTwoRoads(15, 6);
		// Road with ID == 35
		this.linkTwoRoads(16, 34);
		this.linkTwoRoads(16, 41);
		this.linkTwoRoads(16, 36);
		this.linkTwoRoads(16, 7);
		// Road with ID == 36
		this.linkTwoRoads(17, 24);
		this.linkTwoRoads(17, 7);
		// Roads with number 3
		// Road with ID == 37
		this.linkTwoRoads(18, 37);
		this.linkTwoRoads(18, 10);
		// Road with ID == 38
		this.linkTwoRoads(19, 9);
		this.linkTwoRoads(19, 25);
		this.linkTwoRoads(19, 12);
		this.linkTwoRoads(19, 1);
		// Road with ID == 39
		this.linkTwoRoads(20, 10);
		this.linkTwoRoads(20, 0);
		this.linkTwoRoads(20, 32);
		this.linkTwoRoads(20, 36);
		// Road with ID == 40
		this.linkTwoRoads(21, 32);
		this.linkTwoRoads(21, 11);
		this.linkTwoRoads(21, 3);
		this.linkTwoRoads(21, 39);
		// Road with ID == 41
		this.linkTwoRoads(22, 12);
		this.linkTwoRoads(22, 4);
		this.linkTwoRoads(22, 15);
		this.linkTwoRoads(22, 27);
		// Road with ID == 42
		this.linkTwoRoads(23, 13);
		this.linkTwoRoads(23, 5);
		this.linkTwoRoads(23, 34);
		this.linkTwoRoads(23, 28);
		// Road with ID == 43
		this.linkTwoRoads(24, 30);
		this.linkTwoRoads(24, 40);
		this.linkTwoRoads(24, 7);
		this.linkTwoRoads(24, 17);
		// Roads with number 4
		// Road with ID == 44
		this.linkTwoRoads(25, 9);
		this.linkTwoRoads(25, 37);
		this.linkTwoRoads(25, 31);
		this.linkTwoRoads(25, 19);
		// Road with ID == 45
		this.linkTwoRoads(26, 20);
		this.linkTwoRoads(26, 32);
		this.linkTwoRoads(26, 13);
		this.linkTwoRoads(26, 2);
		// Road with ID == 46
		this.linkTwoRoads(27, 22);
		this.linkTwoRoads(27, 15);
		// Road with ID == 47
		this.linkTwoRoads(28, 33);
		this.linkTwoRoads(28, 23);
		this.linkTwoRoads(28, 34);
		this.linkTwoRoads(28, 40);
		// Road with ID == 48
		this.linkTwoRoads(29, 14);
		this.linkTwoRoads(29, 35);
		this.linkTwoRoads(29, 8);
		this.linkTwoRoads(29, 41);
		// Road with ID == 49
		this.linkTwoRoads(30, 15);
		this.linkTwoRoads(30, 40);
		this.linkTwoRoads(30, 24);
		this.linkTwoRoads(30, 6);
		// Roads with number 5
		// Road with ID == 50
		this.linkTwoRoads(31, 25);
		this.linkTwoRoads(31, 37);
		this.linkTwoRoads(31, 2);
		this.linkTwoRoads(31, 38);
		// Road with ID == 51
		this.linkTwoRoads(32, 20);
		this.linkTwoRoads(32, 11);
		this.linkTwoRoads(32, 21);
		this.linkTwoRoads(32, 26);
		// Road with ID == 52
		this.linkTwoRoads(33, 4);
		this.linkTwoRoads(33, 38);
		this.linkTwoRoads(33, 28);
		this.linkTwoRoads(33, 40);
		// Road with ID == 53
		this.linkTwoRoads(34, 28);
		this.linkTwoRoads(34, 23);
		this.linkTwoRoads(34, 41);
		this.linkTwoRoads(34, 16);
		// Road with ID == 54
		this.linkTwoRoads(35, 14);
		this.linkTwoRoads(35, 29);
		// Road with ID == 55
		this.linkTwoRoads(36, 7);
		this.linkTwoRoads(36, 16);
		// Roads with number 6
		// Road with ID == 56
		this.linkTwoRoads(37, 25);
		this.linkTwoRoads(37, 18);
		this.linkTwoRoads(37, 10);
		this.linkTwoRoads(37, 31);
		// Road with ID == 57
		this.linkTwoRoads(38, 31);
		this.linkTwoRoads(38, 2);
		this.linkTwoRoads(38, 33);
		this.linkTwoRoads(38, 4);
		// Road with ID == 58
		this.linkTwoRoads(39, 21);
		this.linkTwoRoads(39, 3);
		this.linkTwoRoads(39, 14);
		this.linkTwoRoads(39, 5);
		// Road with ID == 59
		this.linkTwoRoads(40, 33);
		this.linkTwoRoads(40, 28);
		this.linkTwoRoads(40, 24);
		this.linkTwoRoads(40, 30);
		// Road with ID == 60
		this.linkTwoRoads(41, 34);
		this.linkTwoRoads(41, 29);
		this.linkTwoRoads(41, 8);
		this.linkTwoRoads(41, 16);
		
		// BLACK SHEEP GENERATOR
		this.blacksheep = new BlackSheep(lands.get(0));
		
		// WOLF GENERATOR
		this.wolf = new Wolf(lands.get(0));
		
		//DICE GENERATOR
		this.dice = new Dice(6);
		
		//OVINE GENERATOR
		for(Land land: this.lands){
			if(!land.getLandType().equals(LandType.SHEEPSBURG)){
				land.addOvine(this.factoryRandomOvine());
			}else{
				land.addOvine((Ovine)blacksheep);
				land.setWolf(wolf);
			}
		}
			
	}
	
	/**
	 * Builder of the GameBoard in case the number of Players is 2.
	 * It invokes the builder of the parameterless GameBoard.
	 * @param nickname1 of the first Player.
	 * @param nickname2 of the second Player.
	 * @throws RemoteException if there are connection problems.
	 */
	public GameBoard(String nickname1, String nickname2) throws RemoteException{
		this();
		players.add(new Player(nickname1, STARTINGGOLDSFOR2PLAYERS, model.Color.BLUE, true));
		players.add(new Player(nickname2, STARTINGGOLDSFOR2PLAYERS, model.Color.RED, true));
		//add the initial card
		this.getPlayer(nickname1).addCard(this.getStartingCard(6));
		this.getPlayer(nickname2).addCard(this.getStartingCard(5));
	}
	
	/**
	 * Builder of the GameBoard in case the number of Players is 3.
	 * It invokes the builder of the parameterless GameBoard.
	 * @param nickname1 of the first Player.
	 * @param nickname2 of the second Player.
	 * @param nickname3 of the third Player.
	 * @throws RemoteException if there are connection problems.
	 */
	public GameBoard(String nickname1, String nickname2, String nickname3) throws RemoteException{
		this();
		players.add(new Player(nickname1, STARTINGGOLDS, model.Color.BLUE, false));
		players.add(new Player(nickname2, STARTINGGOLDS, model.Color.RED, false));
		players.add(new Player(nickname3, STARTINGGOLDS, model.Color.GREEN,false));
		//Add the initial card
		this.getPlayer(nickname1).addCard(this.getStartingCard(6));
		this.getPlayer(nickname2).addCard(this.getStartingCard(5));
		this.getPlayer(nickname3).addCard(this.getStartingCard(4));
	}
	
	/**
	 * Builder of the GameBoard in case the number of players is 4.
	 * It invokes the builder of the parameterless GameBoard.
	 * @param nickname1 of the first Player.
	 * @param nickname2 of the second Player.
	 * @param nickname3 of the third Player.
	 * @param nickname4 of the fourth Player.
	 * @throws RemoteException if there are connection problems. 
	 */
	public GameBoard(String nickname1, String nickname2, String nickname3, String nickname4) throws RemoteException{
		this();
		players.add(new Player(nickname1, STARTINGGOLDS, model.Color.BLUE, false));
		players.add(new Player(nickname2, STARTINGGOLDS, model.Color.RED, false));
		players.add(new Player(nickname3, STARTINGGOLDS, model.Color.GREEN, false));
		players.add(new Player(nickname4, STARTINGGOLDS, model.Color.YELLOW, false));
		//Add to the players the initial card
		this.getPlayer(nickname1).addCard(this.getStartingCard(6));
		this.getPlayer(nickname2).addCard(this.getStartingCard(5));
		this.getPlayer(nickname3).addCard(this.getStartingCard(4));
		this.getPlayer(nickname4).addCard(this.getStartingCard(3));
	}
	
	/**
	 * @return the List of the Players of the GameBoard.
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * @return the List of the Roads of the GameBoard.
	 */
	public List<Road> getRoads() {
		return roads;
	}


	/**
	 * @return the List of the Lands of the GameBoard.
	 */
	public List<Land> getLands() {
		return lands;
	}

	/**
	 * @return the List of the startingCards of the GameBoard. 
	 */
	public List<Card> getStartingCards() throws RemoteException {
		return startingCards;
	}

	/**
	 * @return the List of the Cards of the GameBoard.
	 */
	public List<Card> getCards() {
		return cards;
	}
	
	/**
	 * @return the Wolf of the GameBoard.
	 */
	public Wolf getWolf() {
		return wolf;
	}

	/**
	 * @return the Dice of the GameBoard.
	 */
	public Dice getDice() {
		return dice;
	}

	/**
	 * @return the number of Fences of the GameBoard.
	 */
	public int getNumberFence() throws RemoteException {
		return numberFence;
	}

	/**
	 * @return the startingGolds of the GameBoard in case the
	 * number of Players in the GameBoard isn't two.
	 */
	public static int getStartinggolds() throws RemoteException{
		return STARTINGGOLDS;
	}
	
	/**
	 * @return the startingGolds of the GameBoard  in case the 
	 * number of Players in the GameBoard is two.
	 */
	public static int getStartinggoldsForTwoPlayers()  throws RemoteException {
		return STARTINGGOLDSFOR2PLAYERS;
	}
	
	/**
	 * Method that subtracts one to the number of Fences of the GameBoard.
	 */
	public void subNumberOfFence() throws RemoteException {
		this.numberFence = this.numberFence -1;
	}
	
	/**
	 * @param blackSheep to set to the GameBoard.
	 * @throws RemoteException if there are connection problems.
	 */
	public void setBlackSheep(BlackSheep blackSheep) throws RemoteException{
		this.blacksheep = blackSheep;
	}
	
	/**
	 * @param lands List to set to the GameBoard.
	 */
	public void setLands(List<Land> lands) {
		this.lands = lands;
	}

	/**
	 * Return the BlackSheep of the GameBoard.
	 */
	public BlackSheep getBlackSheep() {
		return this.blacksheep;
	}

	/**
	 * Method that used to get an Ovine with a specific Ovine id. 
	 * @param ovineId of the Ovine to get.
	 * @return the Ovine with the Ovine id inserted as parameter if exists, else returns null.
	 */
	public Ovine getOvineOnId(int ovineId) {
		for(Land l: this.lands) {
			for(Ovine o: l.getOvines()) {
				if(o.getId() == ovineId) {
					return o;
				}
			}
		}
		return null;
	}

	/**
	 * Method used for testing.
	 * @return the total number of the Ovines in the GameBoard.
	 */
	public int getNumberOfOvines() {
		int numberOfOvines = 0;
		for(Land l: lands) {
			numberOfOvines += l.getOvines().size();
		}
		return numberOfOvines;
	}
	
	/**
	 * Method that add a Road with a specified number to the List of the Roads of the GameBoard. 
	 * @param number of the Road added.
	 * @param numberOfRoads of the GameBoard.
	 */
	private void addRoadsOfNumber(int number, int numberOfRoads){
		for (int i = 0; i < numberOfRoads; i++) {
			roads.add(new Road(number));
		}
	}

	/**
	 * Links a Road with a Land.
	 * @param roadIndex the index in the Roads List of the Road to link.
	 * @param landIndex the index in the Lands List of the Land to link.
	 */
	private void linkRoadLand(int roadIndex, int landIndex){
		roads.get(roadIndex).addNeighboringLand(lands.get(landIndex));
		lands.get(landIndex).addNeighboringRoad(roads.get(roadIndex));
	}

	/**
	 * Adds a neighboring Road to a Road.
	 * @param roadIndex1 of the Road.
	 * @param roadIndex2 of the Road to add as neighboring.
	 */
	private void linkTwoRoads(int roadIndex1, int roadIndex2){
		roads.get(roadIndex1).addNeighboringRoad(roads.get(roadIndex2));
	}

	/**
	 * Method used to get a random starting Card from the List of starting Cards.
	 * @param number of the starting Cards.
	 * @return a random starting Card.
	 */
	private Card getStartingCard(int number){
		Random random = new Random();
		int index = random.nextInt(number);
		Card card =  this.startingCards.get(index);
		this.startingCards.remove(index);
		return card;
		
	}

	/**
	 * Method used to get a Player by a nickname inserted as parameter.
	 * @param nickname of the Player to get.
	 * @return the Player with that nickname if exists, else returns null.
	 */
	private Player getPlayer(String nickname){
		for(Player e: this.players){
			if(e.getNickname().equals(nickname)){
				return e;
			}
			
		}
		return null;
	}

	/**
	 * Helper method for building the GameBoard.
	 * Generate an Ovine of a random Type.
	 * @return the Ovine randomly generated.
	 */
	private Ovine factoryRandomOvine(){
		Random random = new Random();
		int rand = random.nextInt(9);
		if(rand >= 0 && rand <= 2){
			return new Sheep();
		}else if(rand > 2 && rand <= 5){
			return new Ram();
		}else {
			return new Lamb();
		}	
	}
	
}

