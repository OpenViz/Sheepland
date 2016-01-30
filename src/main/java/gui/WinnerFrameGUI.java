package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Player;
import utility.ServerLogger;
import view.ViewGUIInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class WinnerFrameGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 500;
	private static final int WINDOW_TOP = 22;
	
	private ViewGUIInterface viewGUIInterface;
	private JLabel winners;
	private Font font;
	private JLabel winnerImage;
	
	/**
	 * Creates a JFrame that displays the winner/winners of the game.
	 * The winners are taken from the server through the ViewGUIInterface.
	 * @param viewGUIInterface: to communicate with the server.
	 * @throws RemoteException: connection issue.
	 */
	public WinnerFrameGUI(ViewGUIInterface viewGUIInterface) throws RemoteException {
		this.viewGUIInterface = viewGUIInterface;
		// Creates the JFrame
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_TOP);
		this.setTitle("Winner");
		this.setBackground(new Color(35, 161, 246));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout( new BorderLayout());
		
		// Set Font
		this.setFont();
		
		// Set the Image
		this.setWinnerImage();
		
		// Set Winner Name
		this.setNameOfWinners();
		
		this.setVisible(true);
		
	}
	
	/**
	 * Helper method to the builder.
	 * Sets the image based on number of the winners. 
	 * @throws RemoteException: connection issue.
	 */
	private void setWinnerImage() throws RemoteException {
		ImageIcon image;
		List<Player> winnerPlayer = this.getWinnerPlayersFromView();
		if(winnerPlayer.size()==1) {
			image = new ImageIcon("./res/winnerImage1.png");
		}else{
			image = new ImageIcon("./res/winnerImage2.png");
		}
		this.winnerImage = new JLabel();
		this.winnerImage.setBackground(new Color(35, 161, 246));
		this.winnerImage.setOpaque(true);
		this.winnerImage.setSize(new Dimension(500,400));
		this.winnerImage.setPreferredSize(new Dimension(500,400));
		this.winnerImage.setIcon(image);
		this.winnerImage.setVisible(true);
		this.add(winnerImage,BorderLayout.NORTH);
	}
	
	
	/**
	 * Helper method to the builder.
	 * Returns a list that contains the winners players.
	 * This list is taken from the Server. 
	 * @return List<Player>: that wins the game.
	 * @throws RemoteException: connection issue.
	 */
	private List<Player> getWinnerPlayersFromView() throws RemoteException {
		return this.viewGUIInterface.getGameInterface().getGameInfo().getWinnerPlayers();
	}
	
	/**
	 * Helper method to the builder.
	 * Sets the name of the winner in the JFrame.
	 * @throws RemoteException: connection issue.
	 */
	private void setNameOfWinners() throws RemoteException {
		List<Player> winnerPlayer = this.getWinnerPlayersFromView();
		String nameOfPlayer = null;
		for(Player player: winnerPlayer) {
			if(nameOfPlayer == null) {
				nameOfPlayer = player.getNickname();
			}else {
				nameOfPlayer = nameOfPlayer +" & "+ player.getNickname();
			}
		}
		this.winners = new JLabel();
		this.winners.setText(nameOfPlayer);
		this.winners.setFont(font);
		this.winners.setHorizontalAlignment(JLabel.CENTER);
		this.winners.setBackground(new Color(35, 161, 246));
		this.winners.setOpaque(true);
		this.winners.setSize(new Dimension(500,100));
		this.winners.setPreferredSize(new Dimension(500,100));
		this.add(winners,BorderLayout.SOUTH);
	}
	
	/**
	 * Helper method to the Builder.
	 * Sets a fixed font that is used by the JFrame.
	 */
	private void setFont(){
		try {
			File file = new File("./res/angrybirds-regular.ttf");
			font =  Font.createFont(Font.TRUETYPE_FONT, file);
			font =  font.deriveFont(Font.PLAIN,20);
		} catch (FontFormatException e) {
			ServerLogger.printOnLogger("GameMessageGUI", e);
		} catch (IOException e) {
			ServerLogger.printOnLogger("GameMessageGUI", e);
		}
	}

}
