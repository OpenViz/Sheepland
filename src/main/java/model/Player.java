package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Class of the Players of the Game.
 * A Player has a nickname, one or more Shepherds, a color to identify its Shepherds,
 * an amount of golds and some Cards that represent their Lands properties. 
 */
public class Player implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nickname; 
	private int golds;
	private List<Card> cards ;
	private List<Shepherd> shepherds;
	private Color color;
	
	/**
	 * Builder of the Player. 
	 * If there are only 2 Players, the builder assigns 2 Shepherds to
	 * the Player builded instead of only one.
	 * @param nickname of the Player. The nickname will be choosed before that the Game starts.
	 * @param golds which Player builded starts with.
	 * @param color to identify the Player's Shepherds.
	 * @param twoPlayers that notifies if there are only two Players in the Game.
	 */
	public Player(String nickname, int golds, Color color, boolean twoPlayers) {
		this.nickname = nickname;
		this.golds = golds;
		this.color = color;
		this.cards = new ArrayList<Card>();
		this.shepherds = new ArrayList<Shepherd>();
		this.shepherds.add(new Shepherd(color));
		// If there are only two Players each of them has two Shepherds instead that only one.
		if(twoPlayers){
			this.shepherds.add(new Shepherd(color));
		}
	}
	
	/**
	 * Method used to increment the number of golds that the Player owns.
	 * Add n to the Player golds.
	 * @param n value to add to golds of the Player.
	 */
	public void addGolds(int n) {
		this.golds = this.golds + n;
	}

	/**
	 * Method used to decrement the number of golds that the Player owns.
	 * Subtracts n to the Player golds.
	 * @param n value to subtract to golds of the Player.
	 */
	public void subGolds(int n) {
		this.golds = this.golds - n;
	}
	
	/**
	 * Method used to verify if the Player have enough money to do something.
	 * Returns true if the player have almost n golds, else returns false.
	 * @param n golds to check if the Player owns.
	 * @return true if golds of the Player are major or equals to n, else false.
	 */
	public boolean haveEnoughGolds(int n) {
		return golds>=n;
	}
	
	/**
	 * @return the golds of the Player.
	 */
	public int getGold() {
		return this.golds;
	}
	
	/**
	 * Method used to add a Card to the List of Cards that the Player owns.
	 * @param card to add to the List of Cards of the Player.
	 */
	public void addCard(Card card) {
		this.cards.add(card);
	}
	
	/**
	 * Method used to remove a Card to the List of Cards that the Player owns.
	 * Remove the card from the List of Player Cards basing on the LandType of the Card.
	 * @param stringLandType String of the LandType of the Card to Remove.
	 * @return the removed Card if it's in the List, else null.
	 */
	public Card subCard(String stringLandType) {
		for(Card e:this.cards) {
			if(stringLandType.equalsIgnoreCase(e.getLandType().toString())) {
					this.cards.remove(e);
					return e;
			}	
		}
		return null;
	}
	
	/**
	 * Method used for Testing.
	 * Method used to check if a Card of a specific LandType
	 * is in the List of Cards that the Player owns.
	 * @param stringLandType String of the LandType of the Card to search.
	 * @return true if the Card searched is in the List, else false.
	 */
	public boolean searchCard(String stringLandType) {
		for(Card e: this.cards) {
			if( stringLandType.equalsIgnoreCase(e.getLandType().toString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method used to know how much Cards of a specific LandType
	 * the Player has in his List of Cards.
	 * @param landType of the Cards in the List that have to be counted.
	 * @return the number of Cards of the inserted LandType that the Player has in his List of Cards.
	 */
	public int numberOfCard(LandType landType) {
		int counter = 0;
		for(Card card:this.cards) {
			if(card.getLandType().equals(landType)) {
				counter ++;
			}
		}
		return counter;
	}

	/**
	 * @return the nickname of the Player.
	 */
	public String getNickname() {
		return this.nickname;
	}
	
	/**
	 * @return the List of Shepherds of the Player.
	 */
	public List<Shepherd> getShepherds() {
		return this.shepherds;
	}

	/**
	 * Method used to get a specific Shepherd from the List of Shepherds
	 * that the Player owns.
	 * @param number that has the index where is the Shepherd that has to be got.
	 * @return the Shepherd that occupies the inserted number position of the List.
	 */
	public Shepherd getShepherd(int number) {
		return this.shepherds.get(number);
	}
	
	/**
	 * @return the List of the Cards of the Player.
	 */
	public List<Card> getCards() {
		return this.cards;
	}
	
	/**
	 * @return the color of the Player that relates him to his Shepherds.
	 */
	public Color getColor() {
		return color;
	}

	// Save chain method
	/**
	 * Method used to get the Road where is placed
	 * a specific Shepherd from the List of Shepherds that the Player owns.
	 * @param shepherdNumber that has the index where is the Shepherd which its Road has to be got.
	 * @return the Road of the Shepherd that occupies the inserted number position of the List.
	 */
	public Road getRoadOfShepherd(int shepherdNumber) {
		return shepherds.get(shepherdNumber).getRoad();
	}
	
}
