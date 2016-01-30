package model;

import java.io.Serializable;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class of the Cards.
 * If the price is -1 is the initial Cards.
 * If the price is 5 the Cards of that LandType are over.
 */
public class Card  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private LandType landType;
	private int price;
	
	/**
	 * Card builder. Takes its type and its price as parameter.
	 * @param type of the Cards.
	 * @param price of the Cards.
	 */
	public Card(LandType landType, int price){
		this.landType = landType;
		this.price = price;
	}
	
	/**
	 * @return landType of the Card.
	 */
	public LandType getLandType(){
		return landType;
	}

	/**
	 * @return price of the Card.
	 */
	public int getPrice(){
		return price;
	}
}
