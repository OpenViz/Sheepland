package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.EventType;
import utility.ServerLogger;
import view.ViewGUIInterface;


/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Main class of the model that instantiates all big classes of the Game GUI.
 * It's a JFrame and uses the BorderLayout.
 *
 */
public class GameGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 1050;
	private static final int WINDOW_HEIGHT = 700;
	private static final int WINDOW_TOP = 22;
	
	private GameBoardGUI gameBoardGUI;
	private GameInfoGUI gameInfoGUI;
	private PlayerInfoGUI playerInfoGUI;
	private UtilitiesForGUI utilitiesForGUI;
	
	private ViewGUIInterface viewGUIInterface;
	private boolean openMarket = false;
	
	/**
	 * Builder of the GameGUI. Uses the Border Layout.
	 * Initialize the 3 main parts of the GUI that are:
	 * - GameBoardGUI in the CENTER of the BorderLayout.
	 * - PlayerInfoGUI in the WEST of the BorderLayout.
	 * - GameInfoGUI in the EAST of the BorderLayout.
	 * @throws RemoteException if there are connection problems.
	 */
	public GameGUI() throws RemoteException {
		this.viewGUIInterface = null;
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_TOP);
		this.setTitle("Sheepland");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(new Color(35, 161, 246));
		this.setResizable(false);
		
		this.setLayout(new BorderLayout());
	
		utilitiesForGUI = new UtilitiesForGUI(viewGUIInterface);
		
		gameBoardGUI = new GameBoardGUI();
		gameBoardGUI.setUtilitiesForGUI(utilitiesForGUI);
		this.add(gameBoardGUI, BorderLayout.CENTER);
		
		playerInfoGUI = new PlayerInfoGUI();
		this.add(playerInfoGUI, BorderLayout.WEST);
		
		
		gameInfoGUI = new GameInfoGUI( new ActionsGUI());
		this.add(gameInfoGUI, BorderLayout.EAST);
		
		this.setVisible(true);
	}

	/**
	 * @return the viewGUIInterface.
	 */
	public ViewGUIInterface getViewGUIInterface() {
		return viewGUIInterface;
	}

	/**
	 * Method used to set the ViewGUIInterface to the GameGUI.
	 * It is also set to the PlayerInfoGUI.
	 * ViewGUIInterface is used to get all the data of the Game that are needed
	 * by the GUI. 
	 * @param viewGUIInterface to set to the GameGUI and to the PlayerInfoGUI.
	 * @throws RemoteException if there are connection problems. 
	 */
	public void setViewGUIInterface(ViewGUIInterface viewGUIInterface) throws RemoteException {
		this.viewGUIInterface = viewGUIInterface;
		this.playerInfoGUI.setViewGuiInterface(viewGUIInterface);
	}
	
	/**
	 * @return the gameBoardGUI of the GameGUI.
	 */
	public GameBoardGUI getGameBoardGUI() {
		return gameBoardGUI;
	}
	
	/**
	 * @return the PlayerInfoGUI of the GameGUI.
	 */
	public PlayerInfoGUI getPlayerInfoGUI(){
		return this.playerInfoGUI;
	}
	
	/**
	 * Method used to set the ViewGUIInterface to all other parts of the GameGUI.
	 * ViewGUIInterface is used to get all the data of the Game that are needed
	 * by the GUI.
	 * @throws RemoteException if there are connection problems.
	 */
	public void setInterfaces() throws RemoteException {
		this.utilitiesForGUI.setViewGUIInterface(viewGUIInterface);
		this.getGameBoardGUI().setViewGUIInterface(viewGUIInterface);
		this.getGameBoardGUI().setGameInterface(viewGUIInterface.getGameInterface());
		this.gameInfoGUI.setViewGUIOfInfoGUI(viewGUIInterface);
		
	}

	/**
	 * Updates all the components of the JFrame GameGUI.
	 * If is active the WinnerTime event it shows the winners of the game.
	 * @throws RemoteException : connection issue.
	 */
	public void update() throws RemoteException{
		this.gameInfoGUI.update();
		this.playerInfoGUI.update();
		if(this.viewGUIInterface.getGameInterface().getGameInfo().getActiveEvent()!= null 
				&& this.viewGUIInterface.getGameInterface().getGameInfo().getActiveEvent().equals(EventType.WINNERTIME)){
			new WinnerFrameGUI(viewGUIInterface);
		}
		
	}
	
	/**
	 * Prints a message from the server in the JFrame GameGui.
	 * The message is showed in {@link GameMessageGUI}.
	 * @param message : that has to be printed.
	 */
	public void printMessage(String message) {
		try {
			if(this.viewGUIInterface!= null && utilitiesForGUI.getActiveEvent() != null 
					&& utilitiesForGUI.getActiveEvent().equals(EventType.MARKET) && !this.openMarket ){
					this.openMarket = true;
					this.setEnabled(false);
					new MarketGUI(viewGUIInterface, message, this);
			}
		} catch (RemoteException e) {
			ServerLogger.printOnLogger("GameGUI", e);
		}
		this.gameInfoGUI.printMessageOnGameMessageGui(message);
	}
	
	/**
	 * Shows a popup with a message from the server.
	 * The client has to write an answer.
	 * @param string : that has to be printed.
	 * @return the answer of the client.
	 */
	public String popUpFromTheServer(String string){
		String answer = null;
		while(answer == null){
		answer = JOptionPane.showInputDialog(string);
		}
		return answer;
	}
	
	/**
	 * Sets the OpenMarket variable equals to the parameter.
	 * @param openMarket : to set the variable OpenMarket.
	 */
	public void setOpenMarket(boolean openMarket){
		this.openMarket = openMarket;
	}
}


