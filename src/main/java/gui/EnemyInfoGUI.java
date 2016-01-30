package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Player;
import view.ViewGUIInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class EnemyInfoGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private ViewGUIInterface viewGUI;
	private static final int WINDOW_WIDTH = 240;
	private static final int WINDOW_HEIGHT = 50;
	private static final int WINDOW_TOP = 22;
	private JLabel player1;
	private JLabel player2;
	private JLabel player3;
	private boolean firstUpdate = true;
	private Color goldColor;
	
	/**
	 * Creates a new JPanel that contains the information about the other players.
	 */
	public EnemyInfoGUI(){
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_TOP));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_TOP));
		this.setBackground(new Color(35, 161, 246));
		this.setLayout(new FlowLayout());
		this.goldColor = new Color(255,215,0);
		this.player1 = new JLabel();
		this.player1.setForeground(this.goldColor);
		this.player2 = new JLabel();
		this.player2.setForeground(this.goldColor);
		this.player3 = new JLabel();
		this.player3.setForeground(this.goldColor);
		this.viewGUI = null;
	}
	
	/**
	 * Set the viewGUIinterface that is used to communicate with the server.
	 * @param viewGUI: to set the viewGUIInterace.
	 */
	public void setviewGUI(ViewGUIInterface viewGUI){
		this.viewGUI = viewGUI;
		
	}
	
	/**
	 * Update the EnemyInfoGUI according to the information taken from the server.
	 * At the first update it will create the new JLabel from the enemy players.
	 * From the second update will update the gold that the players have.
	 * @throws RemoteException: connection issue.
	 */
	public void update() throws RemoteException{
		if(firstUpdate){
			this.setEnemyPlayerIcon();
			this.firstUpdate = false;
		}
		this.setEnemyGold();
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Helper method to update.
	 * Sets all the images for the enemy players.
	 * @throws RemoteException: connection issue.
	 */
	private void setEnemyPlayerIcon() throws RemoteException{
		int count = 0;
		for(Player p: this.viewGUI.getGameInterface().getGameBoard().getPlayers()){
			if(!p.getNickname().equals(this.viewGUI.getNickname()) && count == 0){
				this.setEnemyIcon(p,player1);
				this.add(player1);
				count++;
			} else if(!p.getNickname().equals(this.viewGUI.getNickname()) && count == 1){
				this.setEnemyIcon(p,player2);
				this.add(player2);
				count++;
			} else if(!p.getNickname().equals(this.viewGUI.getNickname()) && count == 2){
				this.setEnemyIcon(p,player3);
				this.add(player3);
				count++;
			}
		}
	}
	
	/**
	 * Helper method to setEnemyPlayerIcon.
	 * Sets the image for the specific player ( parameter) according to the information on the server
	 * @param player: used to choose the image to set.
	 * @param jLabel: where set the image chosen.
	 */
	private void setEnemyIcon(Player player, JLabel jLabel) {
		ImageIcon image = null;
		model.Color color = player.getColor();
		if(color.equals(model.Color.BLUE)){
			 image = new ImageIcon("./res/EnemyStark.png");
		} else if(color.equals(model.Color.RED)){
			image = new ImageIcon("./res/EnemyLeone.png");
		} else if(color.equals(model.Color.GREEN)){
			 image = new ImageIcon("./res/EnemyDrake.png");
		} else if(color.equals(model.Color.YELLOW)){
			image =  new ImageIcon("./res/EnemyCervo.png");
			
		}
		 jLabel.setIcon(image);
	}
	
	/**
	 * Helper method to update.
	 * Sets the golds that every enemy player has.
	 * @throws RemoteException: connection issue.
	 */
	private void setEnemyGold() throws RemoteException {
		int count = 0;
		for(Player p: this.viewGUI.getGameInterface().getGameBoard().getPlayers()){
			if(!p.getNickname().equals(this.viewGUI.getNickname()) && count == 0){
				this.player1.setText(""+p.getGold());
				count++;
			} else if(!p.getNickname().equals(this.viewGUI.getNickname()) && count == 1){
				this.player2.setText(""+p.getGold());
				count++;
			} else if(!p.getNickname().equals(this.viewGUI.getNickname()) && count == 2){
				this.player3.setText(""+p.getGold());
				count++;
			}
		}
	}
	
}
