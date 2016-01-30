package model;

import java.io.Serializable;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class SellingCard implements Serializable {
	private static int counter = 0;
	
	private static final long serialVersionUID = 1L;
	private String cardType;
	private int sellingPrice;
	private String playerNickname;
	private int id;
	
	/**
	 * Take the card and decorate it with the sellingprice.
	 * We use this class if we want to sell the card.
	 * @param card i want to sell.
	 * @param sellingPrice the selling price of the card
	 */
	public SellingCard(String cardType, int sellingPrice,String playerNickname){
		this.cardType = cardType;
		this.sellingPrice = sellingPrice;
		this.playerNickname = playerNickname;
		this.id = counter;
		counter++;
	}
	
	/**
	 * Return the price of the card.
	 * @return the sellingprice of the card i want to sell.
	 */
	public int getSellingPrice(){
		return this.sellingPrice;
	}
	
	/**
	 * 
	 * @return the information of the card that i want to sell.
	 */
	public String getCardType(){
		return this.cardType;
	}
	
	/**
	 * @return the name of the owner of the card.
	 */
	public String getPlayerNickname(){
		return this.playerNickname;
	}
	
	/**
	 * Returns the id of the card.
	 * @return the identificator of the sellingCard.
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Set the static attribute counter = 0.
	 */
	public static void setCounterToZero(){
		counter = 0;
	}
	

}
