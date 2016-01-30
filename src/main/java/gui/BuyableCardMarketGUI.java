package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.LandType;
import model.SellingCard;
import utility.ServerLogger;
import view.ViewGUIInterface;

/**
 * @author manueltanzi
 * @author vittorioselo
 *
 */
public class BuyableCardMarketGUI extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private ViewGUIInterface viewGUIInterface;
	private MarketGUI marketGUI;
	private ImageIcon landTypeImage;
	private SellingCard card;
	
	/**
	 * Creates a "Card" that is used during the Market.
	 * Uses the viewGUIInterface(parameter) to communicate with the server.
	 * Uses the MarketGUI(parameter) for closing it after the click by the mouse.
	 * Uses the SellingCard(parameter) to set the image.
	 * This card can be bought by an user.
	 * @param viewGuiInterface: used to communicate with the server.
	 * @param marketGUI: to close it after the actionPerformed.
	 * @param card: used to set the image and the string that has to send to the server.
	 */
	public BuyableCardMarketGUI(ViewGUIInterface viewGuiInterface,MarketGUI marketGUI, SellingCard card){
		this.viewGUIInterface = viewGuiInterface;
		this.marketGUI = marketGUI;
		this.card = card;
		
		super.setBackground(new Color(35, 161, 246));
		this.setSize(new Dimension(100,100));
		this.setPreferredSize(new Dimension(100,100));
		
		this.setCardImageAndText();
		
		this.addActionListener(this);
		
		this.setVisible(true);
	}
	
	
	/**
	 * @also
	 * 		Sends a string to the server with the ID of the card chosen.
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			this.viewGUIInterface.setSenderString(""+this.card.getId());
		} catch (RemoteException e1) {
			ServerLogger.printOnLogger("BuyableCardMarket", e1);
		}
		this.marketGUI.closeMarketGUI();
	}
	
	/**
	 * Helper method to the Builder.
	 * Sets the image of the BuyableCardMarket based on the card(variable).
	 */
	private void setCardImageAndText(){
		int price = this.card.getSellingPrice();
		LandType landType = Enum.valueOf(LandType.class, this.card.getCardType());
		switch (landType) {
		case FIELD:
			switch (price) {
			case 1:
				this.landTypeImage = new ImageIcon("./res/field1.png");
				break;
			case 2:
				this.landTypeImage = new ImageIcon("./res/field2.png");
				break;
			case 3:
				this.landTypeImage = new ImageIcon("./res/field3.png");
				break;
			case 4:
				this.landTypeImage = new ImageIcon("./res/field4.png");
				break;
			default:
				break;
			}
				
			break;
			
		case FOREST:
			switch (price) {
			case 1:
				this.landTypeImage = new ImageIcon("./res/forest1.png");
				break;
			case 2:
				this.landTypeImage = new ImageIcon("./res/forest2.png");
				break;
			case 3:
				this.landTypeImage = new ImageIcon("./res/forest3.png");
				break;
			case 4:
				this.landTypeImage = new ImageIcon("./res/forest4.png");
				break;
			default:
				break;
			}
			
			break;
		
		case RIVER:
			switch (price) {
			case 1:
				this.landTypeImage = new ImageIcon("./res/river1.png");
				break;
			case 2:
				this.landTypeImage = new ImageIcon("./res/river2.png");
				break;
			case 3:
				this.landTypeImage = new ImageIcon("./res/river3.png");
				break;
			case 4:
				this.landTypeImage = new ImageIcon("./res/river4.png");
				break;
			default:
				break;
			}
			
			break;
			
		case GREEN:
			switch (price) {
			case 1:
				this.landTypeImage = new ImageIcon("./res/green1.png");
				break;
			case 2:
				this.landTypeImage = new ImageIcon("./res/green2.png");
				break;
			case 3:
				this.landTypeImage = new ImageIcon("./res/green3.png");
				break;
			case 4:
				this.landTypeImage = new ImageIcon("./res/green4.png");
				break;
			default:
				break;
			}
			
			break;
			
		case MOUNTAIN:
			switch (price) {
			case 1:
				this.landTypeImage = new ImageIcon("./res/mountain1.png");
				break;
			case 2:
				this.landTypeImage = new ImageIcon("./res/mountain2.png");
				break;
			case 3:
				this.landTypeImage = new ImageIcon("./res/mountain3.png");
				break;
			case 4:
				this.landTypeImage = new ImageIcon("./res/mountain4.png");
				break;
			default:
				break;
			}
			
			break;
			
		case HILL:
			switch (price) {
			case 1:
				this.landTypeImage = new ImageIcon("./res/hill1.png");
				break;
			case 2:
				this.landTypeImage = new ImageIcon("./res/hill2.png");
				break;
			case 3:
				this.landTypeImage = new ImageIcon("./res/hill3.png");
				break;
			case 4:
				this.landTypeImage = new ImageIcon("./res/hill4.png");
				break;
			default:
				break;
			}
			
			break;

		default:
			break;
		}
		this.setIcon(this.landTypeImage);	
	}
	
}
