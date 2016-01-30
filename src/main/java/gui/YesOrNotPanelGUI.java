package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import utility.ServerLogger;
import view.ViewGUIInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class YesOrNotPanelGUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 250;
	
	private ViewGUIInterface viewGUIInterface;
	private JButton yesButton;
	private JButton noButton;
	private ImageIcon yesImage;
	private ImageIcon noImage;
	private MarketGUI market;
	
	/**
	 * Creates a JPanel that is used during Market phase.
	 * Contains 2 fixed buttons for sending a string to the server.
	 * Uses the viewGUIInterface to communicate to the server.
	 * @param viewGuiInterface: to communicate to the server.
	 * @param market: to close it after performed action.
	 */
	public YesOrNotPanelGUI(ViewGUIInterface viewGuiInterface, MarketGUI market){
		this.viewGUIInterface = viewGuiInterface;
		this.market = market;
		super.setBackground(new Color(35, 161, 246));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,100,10));
		//Add Button
		this.addButton();
		this.setVisible(true);
	}

	/**
	 * @also
	 * 		Sends a string to the server based on which button is clicked.
	 * 		YesButton sends a positive string.
	 * 		NoButtons sends a negative string.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(yesButton)) {
			try {
				this.viewGUIInterface.setSenderString("Y");
			} catch (RemoteException e1) {
				ServerLogger.printOnLogger("YesOrNotPanel", e1);
			}
			this.market.closeMarketGUI();
		}else if( e.getSource().equals(noButton)) {
			try {
				this.viewGUIInterface.setSenderString("N");
			} catch (RemoteException e1) {
				ServerLogger.printOnLogger("YesOrNotPanel", e1);
			}
			this.market.closeMarketGUI();
		}
			
	}
		
	
	/**
	 * Method helper to the Builder.
	 * Creates the two buttons of fixed dimension and fixed image.
	 * Adds them to the JPanel.
	 */
	private void addButton() {
		this.yesImage = new ImageIcon("./res/yesButton.png");
		this.noImage = new ImageIcon("./res/noButton.png");
		this.yesButton = new JButton();
		this.yesButton.setIcon(yesImage);
		this.noButton = new JButton();
		this.noButton.setIcon(noImage);
		this.yesButton.setSize(new Dimension(100,100));
		this.yesButton.setPreferredSize(new Dimension(100,100));
		this.noButton.setSize(new Dimension(100,100));
		this.noButton.setPreferredSize(new Dimension(100,100));
		yesButton.addActionListener(this);
		noButton.addActionListener(this);
		this.add(yesButton);
		this.add(noButton);
	}
	
}
