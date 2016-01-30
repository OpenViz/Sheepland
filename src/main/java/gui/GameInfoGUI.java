package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.rmi.RemoteException;

import javax.swing.JPanel;

import view.ViewGUIInterface;


/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class GameInfoGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 350;
	private static final int WINDOW_HEIGHT = 700;
	private ActionsGUI actionsGUI;
	private InfoGUI infoGUI;
	private GameMessageGUI gameMessageGUI;
	
	/**
	 * Creates a JPanel that contains all the information about the game.
	 * It is composed by the {@link InfoGUI} and {@link GameMessageGUI}.
	 * @param actionsGUI
	 */
	public GameInfoGUI(ActionsGUI actionsGUI) {
		this.actionsGUI = actionsGUI;
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setBackground(new Color(35, 161, 246));
		this.setLayout(new BorderLayout());
		this.add(this.actionsGUI,BorderLayout.WEST);
		
		this.infoGUI = new InfoGUI();
		this.add(infoGUI,BorderLayout.EAST);
		
		this.gameMessageGUI = new GameMessageGUI();
		this.add(gameMessageGUI,BorderLayout.SOUTH);
		
	}
	
	/**
	 * Sets the viewGUIInterface of the {@link InfoGUI}.
	 * @param viewGUI: used to set the viewGUIInterface.
	 */
	public void setViewGUIOfInfoGUI(ViewGUIInterface viewGUI){
		this.infoGUI.setViewGUI(viewGUI);
		this.actionsGUI.setViewGUIInterface(viewGUI);
	}
	
	/**
	 * Updates the {@link InfoGUI}.
	 * @throws RemoteException: connection issue.
	 */
	public void update() throws RemoteException{
		this.infoGUI.update();
	}
	
	/**
	 * Prints the specify string (parameter) on the {@link GameMessageGUI}.
	 * @param string: that has to be printed.
	 */
	public void printMessageOnGameMessageGui(String string){
		this.gameMessageGUI.printMessage(string);
	}
}
