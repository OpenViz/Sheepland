/**
 * 
 */
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
public class SelectPricePanelGUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 250;
	
	private ViewGUIInterface viewGUIInterface;
	private MarketGUI market;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private ImageIcon imageButton1;
	private ImageIcon imageButton2;
	private ImageIcon imageButton3;
	private ImageIcon imageButton4;
	
	/**
	 * Create a SelectPricePanel with specific ViewGUIInterface and MarketGUI.
	 * @param viewGuiInterface: that use to communicate with.
	 * @param marketGUI: where is in.
	 */
	public SelectPricePanelGUI(ViewGUIInterface viewGuiInterface, MarketGUI marketGUI){
		this.viewGUIInterface = viewGuiInterface;
		this.market = marketGUI;
		//Create  a new Panel
		super.setBackground(new Color(35, 161, 246));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
		
		this.createAndAddButtons();
		
		this.setVisible(true);
	}
	
	
	/**
	 * @also
	 * 		Sends a string to the server with the price of the button pressed.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(button1)) {
			try {
				this.viewGUIInterface.setSenderString("1");
				this.market.closeMarketGUI();
			} catch (RemoteException e1) {
				ServerLogger.printOnLogger("SelectPricePanel", e1);
			}
		} else if(e.getSource().equals(button2)) {
			try {
				this.viewGUIInterface.setSenderString("2");
				this.market.closeMarketGUI();
			} catch (RemoteException e1) {
				ServerLogger.printOnLogger("SelectPricePanel", e1);
			}
		} else if(e.getSource().equals(button3)) {
			try {
				this.viewGUIInterface.setSenderString("3");
				this.market.closeMarketGUI();
			} catch (RemoteException e1) {
				ServerLogger.printOnLogger("SelectPricePanel", e1);
			}
		}else if(e.getSource().equals(button4)) {
			try {
				this.viewGUIInterface.setSenderString("4");
				this.market.closeMarketGUI();
			} catch (RemoteException e1) {
				ServerLogger.printOnLogger("SelectPricePanel", e1);
			}
		}
	}
	
	/**
	 * Create and add the button to the SelectPricePanel.
	 */
	private void createAndAddButtons() {
		this.imageButton1 = new ImageIcon("./res/gold1.png");
		this.imageButton2 = new ImageIcon("./res/gold2.png");
		this.imageButton3 = new ImageIcon("./res/gold3.png");
		this.imageButton4 = new ImageIcon("./res/gold4.png");
		this.button1 = new JButton();
		this.button2 = new JButton();
		this.button3 = new JButton();
		this.button4 = new JButton();
		this.button1.setIcon(imageButton1);
		this.button2.setIcon(imageButton2);
		this.button3.setIcon(imageButton3);
		this.button4.setIcon(imageButton4);
		this.button1.setSize(new Dimension(90,90));
		this.button2.setSize(new Dimension(90,90));
		this.button3.setSize(new Dimension(90,90));
		this.button4.setSize(new Dimension(90,90));
		this.button1.setPreferredSize(new Dimension(90,90));
		this.button2.setPreferredSize(new Dimension(90,90));
		this.button3.setPreferredSize(new Dimension(90,90));
		this.button4.setPreferredSize(new Dimension(90,90));
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		this.add(button1);
		this.add(button2);
		this.add(button3);
		this.add(button4);
	}

}
