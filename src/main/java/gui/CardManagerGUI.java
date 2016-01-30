package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.rmi.RemoteException;

import javax.swing.JPanel;

import view.ViewGUIInterface;
import model.LandType;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class CardManagerGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 240;
	private static final int WINDOW_HEIGHT = 450;
	private static final int WINDOW_TOP = 22;
	
	private CardGUI fieldCard;
	private CardGUI forestCard;
	private CardGUI hillCard;
	private CardGUI greenCard;
	private CardGUI riverCard;
	private CardGUI mountainCard;
	
	private boolean firstUpdate = true;
	
	/**
	 * Creates a JPanel that contains 6 {@link CardGUI}.
	 * Every card has a different LandType from the other.
	 */
	public CardManagerGUI() {
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_TOP));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_TOP));
		this.setBackground(new Color(35, 161, 246));
		this.setLayout(new FlowLayout());
		this.fieldCard = new CardGUI(LandType.FIELD);
		this.forestCard = new CardGUI(LandType.FOREST);
		this.hillCard =  new CardGUI(LandType.HILL);
		this.greenCard = new CardGUI(LandType.GREEN);
		this.riverCard = new CardGUI(LandType.RIVER);
		this.mountainCard = new CardGUI(LandType.MOUNTAIN);
		this.add(this.fieldCard);
		this.add(this.forestCard);
		this.add(this.hillCard);
		this.add(this.greenCard);
		this.add(this.riverCard);
		this.add(this.mountainCard);
		this.setVisible(false);
	}
	
	/**
	 * Sets the view of all six CardGUI.
	 * They use this viewGUIInterface to communicate with the server.
	 * @param viewGUI: used to set the different views.
	 */
	public void setViewForCards(ViewGUIInterface viewGUI) {
		this.fieldCard.setViewGUI(viewGUI); 
		this.forestCard.setViewGUI(viewGUI);  
		this.hillCard.setViewGUI(viewGUI);  
		this.greenCard.setViewGUI(viewGUI); 
		this.riverCard.setViewGUI(viewGUI); 
		this.mountainCard.setViewGUI(viewGUI); 
	}
	
	/**
	 * Updates all the CardGUI.
	 * At the first update it will make visible himself.
	 * From the second update it will update the cards.
	 * @throws RemoteException: connection issue.
	 */
	public void update() throws RemoteException {
		if(firstUpdate){
			this.setVisible(true);
			firstUpdate = false;
		}
		this.fieldCard.update(); 
		this.forestCard.update(); 
		this.hillCard.update(); 
		this.greenCard.update();  
		this.riverCard.update(); 
		this.mountainCard.update(); 
		this.revalidate();
		this.repaint();
	}
	
}
