package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utility.ServerLogger;
import view.ViewGUIInterface;
import model.SellingCard;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */

public class BuyCardMarketPanelGUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 350;
	private List<SellingCard> cards;
	private JButton noButton;
	private MarketGUI marketGUI;
	private ViewGUIInterface viewGUIInterface;
	
	/**
	 * Creates a variable JPanel with  the cards that the players want to sell.
	 * There is a JButton to close this panel if the player(client) doesn't want to buy anything.
	 * @param viewGuiInterface: to communicate with the server.
	 * @param marketGUI: to close it after the ActionPerformed.
	 * @throws RemoteException: connection issues.
	 */
	public BuyCardMarketPanelGUI(ViewGUIInterface viewGuiInterface, MarketGUI marketGUI) throws RemoteException{
		this.cards = this.getSellingCardsFromTheServer(viewGuiInterface);
		this.viewGUIInterface = viewGuiInterface;
		this.marketGUI = marketGUI;
		
		super.setBackground(new Color(35, 161, 246));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		
		this.setSellingCard(viewGuiInterface, marketGUI);
		
		this.setVisible(true);
		
	}
	
	/**
	 * @also
	 * 		Exits from the Frame when the noButton is pressed.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(noButton)){
			try {
				this.viewGUIInterface.setSenderString(""+-1);
				this.marketGUI.closeMarketGUI();
			} catch (RemoteException e1) {
				ServerLogger.printOnLogger("BuyMarketPanel", e1);
			}
		}
		
	}

	/**
	 * Gets the list of SellingCard from the server through the ViewGUIIntergace(parameter).
	 * @param vireGUGuiInterface: used to take from the Server the list of selling cards.
	 * @return List<SellingCard>: a list of all the cards that the players want to sell.
	 * @throws RemoteException: connection issue.
	 */
	private List<SellingCard> getSellingCardsFromTheServer(ViewGUIInterface vireGUGuiInterface) throws RemoteException{
		return vireGUGuiInterface.getGameInterface().getGameInfo().getSellingCards();
	}
	
	/**
	 * Method helper to the Builder.
	 * Sets the {@link BuyableCardMarketGUI} based on the selling cards list. 
	 * Adds at the end a JButton for close the Panel.
	 * @param viewGUIInterface: used for builds the BuyableCardMarketGUI.
	 * @param marketGUI: used to close it after the actionPerformed.
	 */
	private void setSellingCard(ViewGUIInterface viewGUIInterface,MarketGUI marketGUI ){
		BuyableCardMarketGUI buyableCardMarketGUI;
		JPanel panel;
		for(SellingCard card: this.cards){
			panel = this.setPanelCard();
			buyableCardMarketGUI = new BuyableCardMarketGUI(viewGUIInterface, marketGUI, card);
			panel.add(buyableCardMarketGUI,BorderLayout.NORTH);
			panel.add(this.setSellerNickname(card),BorderLayout.SOUTH);
			this.add(panel);
			
		}
		ImageIcon noImage = new ImageIcon("./res/noButton.png");
		this.noButton = new JButton();
		this.noButton.setIcon(noImage);
		this.noButton.setSize(new Dimension(100,100));
		this.noButton.setPreferredSize(new Dimension(100,100));
		noButton.addActionListener(this);
		this.add(noButton);
		
		
	}
	
	
	/**
	 * Method helper to the Builder
	 * Creates a JPanel of fixed dimension and color.
	 * @return JPanel: that is created. 
	 */
	private JPanel setPanelCard(){
		JPanel panel;
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(new Color(35, 161, 246));
		panel.setSize(new Dimension(100,120));
		panel.setPreferredSize(new Dimension(100,120));
		panel.setVisible(true);
		return panel;
	}
	
	/**
	 * Method helper to SetSellingCard.
	 * Creates a JLabel with the owner's name of the card(parameter) and return it.
	 * @param card: used to take the name of the owner.
	 * @return JLabel: that the method creates.
	 */
	private JLabel setSellerNickname(SellingCard card){
		JLabel label = new JLabel(card.getPlayerNickname());
		label.setBackground(new Color(35, 161, 246));
		label.setOpaque(true);
		label.setSize(new Dimension(100,20));
		label.setPreferredSize(new Dimension(100,20));
		label.setVisible(true);
		return label;
		
		
	}

}
