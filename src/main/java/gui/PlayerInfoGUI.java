package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import utility.ServerLogger;
import view.ViewGUIInterface;
import model.LandType;
import model.Player;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class PlayerInfoGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 200;
	private static final int WINDOW_HEIGHT = 700;
	private boolean firstUpdate = true;
	private Font font ;
	private JLabel nickname;
	private JLabel gold;
	private JLabel cardField;
	private JLabel cardRiver;
	private JLabel cardHill;
	private JLabel cardMountain;
	private JLabel cardGreen;
	private JLabel cardForest;
	
	private  ViewGUIInterface viewGuiInterface;
	
	/**
	 * Creates a JPanel that contains the information about the player(client) of the view.
	 * Shows the nickname of the player.
	 * Contains the image of the player.
	 * Contains the information about the player's gold.
	 * Contains the information about the player's cards.
	 */
	public PlayerInfoGUI() {	
		this.viewGuiInterface = null;
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		super.setBackground(new Color(35, 161, 246));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,25,5));
		this.gold = new JLabel();
		this.cardField = new JLabel();
		this.cardRiver = new JLabel();
		this.cardHill = new JLabel();
		this.cardMountain = new JLabel();
		this.cardGreen = new JLabel();
		this.cardForest = new JLabel();
		try {
			File file = new File("./res/KGHAPPY.ttf");
			font =  Font.createFont(Font.TRUETYPE_FONT, file);
			font =  font.deriveFont(Font.PLAIN,30);
		} catch (FontFormatException e) {
			ServerLogger.printOnLogger("PlayerInfoGui", e);
		} catch (IOException e) {
			ServerLogger.printOnLogger("PlayerInfoGui", e);
		}
	}
	
	
	/**
	 * Sets the viewGUIInterface of the JPanel.
	 * This viewGUIInterface is used to communicate with the server.
	 * @param viewGuiInterface: to set the viewGUIInterface.
	 * @throws RemoteException: connection issue.
	 */
	public void setViewGuiInterface(ViewGUIInterface viewGuiInterface) throws RemoteException {
		this.viewGuiInterface = viewGuiInterface;
		nickname = new JLabel( this.viewGuiInterface.getNickname());
		nickname.setFont(font);
		nickname.setHorizontalAlignment(JLabel.CENTER);
		
		this.add(nickname);
		this.revalidate();
		this.repaint();	
	}
	
	/**
	 * Updates the PlayerInfoGUI.
	 * At the first update it will set all the informations and images.
	 * From the second update it will change only the number of player's gold and numbers of the cards 
	 * that the player owns.
	 * @throws RemoteException: connection issue.
	 */
	public void update() throws RemoteException {
		if(firstUpdate) {
			this.add(this.addIcon());
			this.setImageForGolds();
			this.setTextForGolds();
			this.setCardsImage();
			this.setNumberofCards();
			this.revalidate();
			this.repaint();
			this.firstUpdate = false;
			}
		this.setTextForGolds();
		setNumberofCards();
		this.revalidate();
		this.repaint();
	}


	/**
	 * Helper method to the update.
	 * Returns the number of the player's golds according to the information on the server.
	 * @return golds: that the players has.
	 * @throws RemoteException: connection issue.
	 */
	private String getMoneyOfthePlayer() throws RemoteException {
		String answer = null;
		for(Player player: this.viewGuiInterface.getGameInterface().getGameBoard().getPlayers()) {
			if(player.getNickname().equals(this.viewGuiInterface.getNickname())) {
				answer = ""+player.getGold();
			}
		}
		return answer;
	}
	
	
	/**
	 * Helper method to the update.
	 * Returns a JLabel with a icon correspondent to the color of the player.
	 * @return JLabel: that contains the image of the player.
	 * @throws RemoteException: connection issue.
	 */
	private JLabel addIcon() throws RemoteException {
		ImageIcon image;
		JLabel imageLabel;
		model.Color color = null;
		for(Player player: this.viewGuiInterface.getGameInterface().getGameBoard().getPlayers()) {
			if(player.getNickname().equals(this.viewGuiInterface.getNickname())) {
				color =  player.getColor();
			}
		}
		if(color.equals(model.Color.BLUE)) {
			image = new ImageIcon("./res/SmallStark.png");
			imageLabel = new JLabel(image);
			return imageLabel;
			
		} else if(color.equals(model.Color.RED)) {
			image = new ImageIcon("./res/SmallLeone.png");
			imageLabel = new JLabel(image);
			return imageLabel;
			
		} else if(color.equals(model.Color.GREEN)) {
			image = new ImageIcon("./res/Drake.png");
			imageLabel = new JLabel(image);
			return imageLabel;
			
		} else if(color.equals(model.Color.YELLOW)) {
			image = new ImageIcon("./res/SmallCervo.png");
			imageLabel = new JLabel(image);
			return imageLabel;
			
		} else {
			return null;
		}
	}
	
	
	/**
	 * Helper method to the update.
	 * Sets the images of all JLabels that represent the cards that the player has.
	 * The dimension is fixed.
	 */
	private void setCardsImage() {
		ImageIcon fieldImange = new ImageIcon("./res/field.png");
		ImageIcon riverImange = new ImageIcon("./res/river.png"); 
		ImageIcon hillImange= new ImageIcon("./res/hill.png"); 
		ImageIcon mountainImange= new ImageIcon("./res/mountain.png"); 
		ImageIcon greenImange= new ImageIcon("./res/green.png"); 
		ImageIcon forestImange = new ImageIcon("./res/forest.png");
		this.cardField.setIcon(fieldImange);
		this.cardRiver.setIcon(riverImange);
		this.cardHill.setIcon(hillImange);
		this.cardMountain.setIcon(mountainImange);
		this.cardGreen.setIcon(greenImange);
		this.cardForest.setIcon(forestImange);
		this.cardField.setOpaque(true);
		this.cardRiver.setOpaque(true);
		this.cardHill.setOpaque(true);
		this.cardMountain.setOpaque(true);
		this.cardGreen.setOpaque(true);
		this.cardForest.setOpaque(true);
		this.cardField.setBackground(new Color(251,191,0));
		this.cardRiver.setBackground(new Color(251,191,0));
		this.cardHill.setBackground(new Color(251,191,0));
		this.cardMountain.setBackground(new Color(251,191,0));
		this.cardGreen.setBackground(new Color(251,191,0));
		this.cardForest.setBackground(new Color(251,191,0));
		this.cardField.setSize(new Dimension(100,70));
		this.cardRiver.setSize(new Dimension(100,70));
		this.cardHill.setSize(new Dimension(100,70));
		this.cardMountain.setSize(new Dimension(100,70));
		this.cardGreen.setSize(new Dimension(100,70));
		this.cardForest.setSize(new Dimension(100,70));
		this.cardField.setPreferredSize(new Dimension(100,70));
		this.cardRiver.setPreferredSize(new Dimension(100,70));
		this.cardHill.setPreferredSize(new Dimension(100,70));
		this.cardMountain.setPreferredSize(new Dimension(100,70));
		this.cardGreen.setPreferredSize(new Dimension(100,70));
		this.cardForest.setPreferredSize(new Dimension(100,70));
		this.cardField.setFont(font);
		this.cardRiver.setFont(font);
		this.cardHill.setFont(font);
		this.cardMountain.setFont(font);
		this.cardGreen.setFont(font);
		this.cardForest.setFont(font);
		this.add(this.cardField);
		this.add(this.cardRiver);
		this.add(this.cardHill);
		this.add(this.cardMountain);
		this.add(this.cardGreen);
		this.add(this.cardForest);
	}
	
	/**
	 * Helper method to the update.
	 * Sets the number of all types of card that the player has.
	 * @throws RemoteException: connection issue.
	 */
	private void setNumberofCards() throws RemoteException {
		Player player = null;
		for(Player p: this.viewGuiInterface.getGameInterface().getGameBoard().getPlayers()) {
			if(p.getNickname().equals(this.viewGuiInterface.getNickname())) {
				player =  p;
			}
		}
		this.cardField.setText(""+player.numberOfCard(LandType.FIELD));
		this.cardRiver.setText(""+player.numberOfCard(LandType.RIVER));
		this.cardHill.setText(""+player.numberOfCard(LandType.HILL));
		this.cardMountain.setText(""+player.numberOfCard(LandType.MOUNTAIN));
		this.cardGreen.setText(""+player.numberOfCard(LandType.GREEN));
		this.cardForest.setText(""+player.numberOfCard(LandType.FOREST));
	}
	
	/**
	 * Helper method to the update.
	 * Sets the image for player's golds.
	 */
	private void setImageForGolds() {
		ImageIcon coinImange = new ImageIcon("./res/coins.png");
		this.gold.setIcon(coinImange);
		this.gold.setSize(new Dimension(110,90));
		this.gold.setPreferredSize(new Dimension(100,90));
		this.add(gold);
		
	}
	
	/**
	 * Helper method to the update.
	 * Sets the number of golds that the player has.
	 * @throws RemoteException: connection issue.
	 */
	private void setTextForGolds() throws RemoteException {
		this.gold.setText(getMoneyOfthePlayer());
		this.gold.setIconTextGap(-30);
	}
	
}

