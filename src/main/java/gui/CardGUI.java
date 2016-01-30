package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import utility.ServerLogger;
import view.ViewGUIInterface;
import model.Card;
import model.LandType;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */

public class CardGUI extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private ImageIcon landTypeImage;
	private LandType landType;
	private ViewGUIInterface viewGUI;
	
	/**
	 * Creates a CardGUI with specific LandType.
	 * The dimension of this card are fixed.
	 * @param landType: used to set the LandType.
	 */
	public CardGUI(LandType landType) {
		super.setBackground(new Color(35, 161, 246));
		this.setSize(new Dimension(100,100));
		this.setPreferredSize(new Dimension(100,100));		
		this.landType = landType;
		this.viewGUI = null;
		this.addActionListener(this);
	
	}
	
	/**
	 * Sets the viewGUIInterface of the CardGUI.
	 * The cardGUI uses this viewGUIInterface to communicate with the server.
	 * @param viewGUI: used to set the viewGUI.
	 */
	public void setViewGUI(ViewGUIInterface viewGUI){
		this.viewGUI = viewGUI;
	}
	
	/**
	 * Update the image of the card that the CardGUI is representing.
	 * @throws RemoteException
	 */
	public void update() throws RemoteException{
		this.setCardImage();
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * @also
	 * 		Sends a string that represents the LandType of this CardGUI.
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			this.viewGUI.setSenderString(this.landType.toString());
		} catch (RemoteException e1) {
			ServerLogger.printOnLogger("CardGui", e1);
		}
	}
	
	/**
	 * Helper method to Update.
	 * Update the image of the CardGUI according to the model taken from the server.
	 * @throws RemoteException:connection issue.
	 */
	private void setCardImage() throws RemoteException{
		int price = getPriceOfLand();
		switch (landType) {
		case FIELD:
			switch (price) {
			case 0:
				this.landTypeImage = new ImageIcon("./res/field0.png");
				break;
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
			case 5:
				this.landTypeImage = new ImageIcon("./res/fieldnull.png");
				
				break;

			default:
				break;
			}
				
			break;
			
		case FOREST:
			switch (price) {
			case 0:
				this.landTypeImage = new ImageIcon("./res/forest0.png");
				break;
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
			case 5:
				this.landTypeImage = new ImageIcon("./res/forestnull.png");
				
				break;

			default:
				break;
			}
			
			break;
		
		case RIVER:
			switch (price) {
			case 0:
				this.landTypeImage = new ImageIcon("./res/river0.png");
				break;
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
			case 5:
				this.landTypeImage = new ImageIcon("./res/rivernull.png");
				
				break;

			default:
				break;
			}
			
			break;
			
		case GREEN:
			switch (price) {
			case 0:
				this.landTypeImage = new ImageIcon("./res/gree0.png");
				break;
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
			case 5:
				this.landTypeImage = new ImageIcon("./res/greennull.png");
				
				break;

			default:
				break;
			}
			
			break;
			
		case MOUNTAIN:
			switch (price) {
			case 0:
				this.landTypeImage = new ImageIcon("./res/mountain0.png");
				break;
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
			case 5:
				this.landTypeImage = new ImageIcon("./res/mountainnull.png");
				
				break;

			default:
				break;
			}
			
			break;
			
		case HILL:
			switch (price) {
			case 0:
				this.landTypeImage = new ImageIcon("./res/hill0.png");
				break;
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
			case 5:
				this.landTypeImage = new ImageIcon("./res/hillnull.png");
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
	
	/**
	 * Helper method to setCardImage.
	 * Takes the price of the card that the CardGUI is representing and returns it.
	 * @return price: of the card that the CardGUI represents.
	 * @throws RemoteException: connection issue.
	 */
	private int getPriceOfLand() throws RemoteException{
		for(Card card: this.viewGUI.getGameInterface().getGameBoard().getCards()){
			if(this.landType.equals(card.getLandType())){
				return card.getPrice();
			}
		}
		return -1;
	}
}
