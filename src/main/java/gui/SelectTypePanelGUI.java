/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import model.LandType;
import view.ViewGUIInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class SelectTypePanelGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 350;
	
	private TypeMarketCardGUI field;
	private TypeMarketCardGUI forest;
	private TypeMarketCardGUI mountain;
	private TypeMarketCardGUI river;
	private TypeMarketCardGUI hill;
	private TypeMarketCardGUI green;
	
	/**
	 * Creates a new JPanel that contains 4 buttons.
	 * The buttons communicate with the server through the viewGuiINterface.
	 * This buttons sends a string to the server that has meaning of a Price.
	 * @param viewGuiInterface : to cmmunicate with the server.
	 * @param market : to close it after the communication with the server.
	 */
	public SelectTypePanelGUI(ViewGUIInterface viewGuiInterface, MarketGUI market) {
		//Create a new JPanel
		super.setBackground(new Color(35, 161, 246));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		//Create and add cards.
		this.setMarketCards(viewGuiInterface,market);
		
		this.setVisible(true);
	}
	
	
	/**
	 * Create and add all card for the SelectTypePanel.
	 * @param viewGuiInterface
	 * @param market
	 */
	private void setMarketCards(ViewGUIInterface viewGuiInterface, MarketGUI market) {
		this.field = new TypeMarketCardGUI(viewGuiInterface, market, LandType.FIELD);
		this.forest = new TypeMarketCardGUI(viewGuiInterface, market, LandType.FOREST);
		this.mountain = new TypeMarketCardGUI(viewGuiInterface, market, LandType.MOUNTAIN);
		this.river = new TypeMarketCardGUI(viewGuiInterface, market, LandType.RIVER);
		this.hill = new TypeMarketCardGUI(viewGuiInterface, market, LandType.HILL);
		this.green = new TypeMarketCardGUI(viewGuiInterface, market, LandType.GREEN);
		this.add(this.field);
		this.add(this.forest);
		this.add(this.mountain);
		this.add(this.river);
		this.add(this.hill);
		this.add(this.green);
	}

}
