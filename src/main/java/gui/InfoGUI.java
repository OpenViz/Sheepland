package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.rmi.RemoteException;

import javax.swing.JPanel;

import view.ViewGUIInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class InfoGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 240;
	private static final int WINDOW_HEIGHT = 500;
	private static final int WINDOW_TOP = 22;
	private EnemyInfoGUI enemyInfoGUI;
	private CardManagerGUI cardManagerGUI;
	
	/**
	 * Creates a JPanel that contains the {@link EnemyInfoGUI} and the {@link CardManagerGUI}.
	 * EnemyInfoGUI displays the information on enemy players.
	 * CardManagerGUI displays the information about the cards that the player can buy.
	 */
	public InfoGUI(){
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_TOP));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_TOP));
		this.setBackground(new Color(35, 161, 246));
		this.setLayout(new FlowLayout());
		this.cardManagerGUI = new CardManagerGUI();
		this.enemyInfoGUI = new EnemyInfoGUI();
		this.add(this.enemyInfoGUI);
		this.add(this.cardManagerGUI);
	}
	
	/**
	 * Sets the viewGUIInterface of {@link EnemyInfoGUI} and {@link CardManagerGUI}.
	 * @param viewGUI: to set the views.
	 */
	public void setViewGUI(ViewGUIInterface viewGUI){
		this.enemyInfoGUI.setviewGUI(viewGUI);
		this.cardManagerGUI.setViewForCards(viewGUI);
	}
	
	/**
	 * Updates the {@link EnemyInfoGUI} and {@link CardManagerGUI}.
	 * @throws RemoteException: connection issue.
	 */
	public void update() throws RemoteException{
		this.enemyInfoGUI.update();
		this.cardManagerGUI.update();
	}
	
}
