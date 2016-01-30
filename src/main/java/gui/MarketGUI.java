package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import model.MarketStep;
import utility.ServerLogger;
import view.ViewGUIInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class MarketGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 450;
	private static final int WINDOW_TOP = 22;
	private ViewGUIInterface viewGUIInterface;
	private JLabel message;
	private Font font;
	private MarketStep marketStep;
	private GameGUI gameGUI;
	
	/**
	 * Creates a new JFrame used during the Market phase.
	 * It is composed by a JLabel that displays a message from the server and the second 
	 * part depends on {@link MarketStep}.
	 * @param viewGUIInterface: used to communicate with the server.
	 * @param request: the message received from the server.
	 * @param gameGUI: used to communicate with gameGUI.
	 * @throws RemoteException:connection issue.
	 */
	public MarketGUI(ViewGUIInterface viewGUIInterface, String request,GameGUI gameGUI) throws RemoteException{
		//Initialize the variables
		this.viewGUIInterface = viewGUIInterface;
		this.gameGUI = gameGUI;
		//Create a new Frame
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_TOP);
		this.setTitle("Market");
		this.setBackground(new Color(35, 161, 246));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBackground(new Color(35, 161, 246));
		this.setResizable(true);
		this.setLayout( new BorderLayout());
		
		//Create new  Message
		this.createMessage(request);
		this.createButtonPanel();
		
		this.setVisible(true);
	}
	
	
	/**
	 * Close the Market Frame.
	 * Enables the GameGUI and disposes the MarketGUI.
	 */
	public void closeMarketGUI(){
		this.setVisible(false);
		this.gameGUI.setEnabled(true);
		this.gameGUI.setOpenMarket(false);
		this.dispose();
	}

	/**
	 * Helper method to the Builder.
	 * Creates a JPanel and adds it to the MarketGUI.
	 * The JPanel depends on {@link MarketStep}.
	 * They could be: {@link YesOrNotPanelGUI}, {@link SelectPricePanelGUI}, {@link SelectTypePanelGUI}
	 * 	, {@link BuyCardMarketPanelGUI}.
	 * @throws RemoteException: connection issue.
	 */
	private void createButtonPanel() throws RemoteException{
		this.setMarketStep();
		switch (this.marketStep) {
		case YESORNOTSTEP:
			YesOrNotPanelGUI yesOrNotPanel = new YesOrNotPanelGUI(viewGUIInterface, this);
			this.add(yesOrNotPanel,BorderLayout.SOUTH);
			break;
		case SELECTTYPESTEP:
			SelectTypePanelGUI selectTypePanel =  new SelectTypePanelGUI(viewGUIInterface, this);
			this.add(selectTypePanel,BorderLayout.SOUTH);
			break;
		case SELECTPRICESTEP:
			SelectPricePanelGUI selectPricePanel = new SelectPricePanelGUI(viewGUIInterface, this);
			this.add(selectPricePanel,BorderLayout.SOUTH);
			break;
		case BUYCARDSTEP:
			BuyCardMarketPanelGUI buyCardMarketPanel = new BuyCardMarketPanelGUI(viewGUIInterface, this);
			this.add(buyCardMarketPanel,BorderLayout.SOUTH);
			break;

		default:
			break;
		}
	}
	
	/**
	 * Helper method to createButtonPanel
	 * Sets the active {@link MarketStep}. 
	 * Takes the marketStep from the Server through the viewGUIInterface.
	 * @throws RemoteException
	 */
	private void setMarketStep() throws RemoteException{
		this.marketStep =  this.viewGUIInterface.getGameInterface().getGameInfo().getMarketStep();
	}
	
	
	/**
	 * Helper method to Builder.
	 * Creates the JLabel with:fixed font,fixed dimension and dixed background.
	 * Displays the message (parameter).
	 * @param request: message that has to be showed.
	 */
	private void createMessage(String request){
		this.message = new JLabel();
		this.message.setBackground(new Color(35, 161, 246));
		this.message.setSize(new Dimension(500,100));
		this.message.setPreferredSize(new Dimension(500,100));
		try {
			File file = new File("./res/angrybirds-regular.ttf");
			font =  Font.createFont(Font.TRUETYPE_FONT, file);
			font =  font.deriveFont(Font.PLAIN,20);
		} catch (FontFormatException e) {
			ServerLogger.printOnLogger("GameMessageGUI", e);
		} catch (IOException e) {
			ServerLogger.printOnLogger("GameMessageGUI", e);
		}
		this.message.setOpaque(true);
		this.message.setFont(font);
		this.message.setText(request);
		this.message.setHorizontalAlignment(JLabel.CENTER);
		this.add(this.message,BorderLayout.CENTER);
		
	}
	
}
