/**
 * 
 */
package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import model.LandType;
import utility.ServerLogger;
import view.ViewGUIInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class TypeMarketCardGUI extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 90;
	private static final int WINDOW_HEIGHT = 90;
	
	private ViewGUIInterface viewGUIInterface;
	private MarketGUI marketGUI;
	private LandType landType;
	
	/**
	 * Creates a card that is used during the Market.
	 * Sets the image and the string to send to the server based on the {@link LandType}.
	 * @param viewGUIInterface: to communicate with the server.
	 * @param marketGUI: to close it after action performed.
	 * @param landType: to set image and string to send.
	 */
	public TypeMarketCardGUI(ViewGUIInterface viewGUIInterface,MarketGUI marketGUI,LandType landType){
		this.viewGUIInterface = viewGUIInterface;
		this.marketGUI = marketGUI;
		this.landType = landType;
		
		this.setSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
		this.addActionListener(this);
		this.setImageForButton();
		
		this.setVisible(true);	
	}
	
	/**
	 * @also
	 * 		Sends the landType converted to a string.
	 * 		Then close the MarketGUI frame.
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			this.viewGUIInterface.setSenderString(this.landType.toString());
		} catch (RemoteException e1) {
			ServerLogger.printOnLogger("MarketCardGUI", e1);
		}
		this.marketGUI.closeMarketGUI();
	}
	
	
	/**
	 * Helper method to the Builder.
	 * Sets the image of the button based on the landType.
	 */
	private void setImageForButton() {
		ImageIcon image = null;
		switch (this.landType) {
		case FIELD:
			image = new ImageIcon("./res/field.png");
			break;
		case FOREST:
			image = new ImageIcon("./res/forest.png");
			break;
		case RIVER:
			image = new ImageIcon("./res/river.png");
			break;
		case MOUNTAIN:
			image = new ImageIcon("./res/mountain.png");
			break;
		case HILL:
			image = new ImageIcon("./res/hill.png");
			break;
		case GREEN:
			image = new ImageIcon("./res/green.png");
			break;

		default:
			break;
		}
		this.setIcon(image);
	}

}
