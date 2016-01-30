package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import utility.ServerLogger;
import view.ViewGUIInterface;
import model.ActionType;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 */
public class ButtonActionGUI extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final int WINDOW_WIDTH = 105;
	private static final int WINDOW_HEIGHT = 70;
	private ActionType actionType;
	private ViewGUIInterface viewGUI;
	
	/**
	 * Creates a Button of specific actionType.
	 * It has an image and sends a string to the server based on the value of actionType(parameter).
	 * @param actionType: used to set the image and the string to send to the server.
	 */
	public ButtonActionGUI(ActionType actionType){
		this.actionType = actionType;
		this.viewGUI =null;
		this.setSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
		this.setImageForButton();
		
		this.addActionListener(this);
	}
	
	/**
	 * Sets the viewGUIInterface of the ButtonActionGUI.
	 * The button is this view to communicate with the Server.
	 * @param viewGUIInterface:used to set the ButtonActionGUI's view.
	 */
	public void setViewGUI(ViewGUIInterface viewGUIInterface){
		this.viewGUI = viewGUIInterface;
	}
	
	/**
	 * @also
	 * 		Sends a string to the server.
	 * 		The string is the actionType of the button.
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			this.viewGUI.setSenderString(this.actionType.toString());
		} catch (RemoteException e1) {
			ServerLogger.printOnLogger("ButtonActionGUI", e1);
		}
	}

	
	/**
	 * Helper method for the Builder.
	 * Sets the image of the ButtonActionGUI based on the ActionType set.
	 */
	private void setImageForButton() {
		ImageIcon buttonImage = null;
		switch (this.actionType) {
		case MOVESHEPHERD:
			buttonImage = new ImageIcon("./res/move.png");
			break;
		case MOVEOVINE:
			buttonImage =  new ImageIcon("./res/moveSheep.png");
			break;
		case BUYCARD:
			buttonImage = new ImageIcon("./res/buycard.png");
			break;
		case COUPLING1:
			buttonImage =  new ImageIcon("./res/coupling1.png");
			break;
		case COUPLING2:
			buttonImage =  new ImageIcon("./res/coupling2.png");
			break;
		case KILLOVINE:
			buttonImage = new ImageIcon("./res/killOvine.png");
			break;

		default:
			break;
		}
		this.setIcon(buttonImage);	
	}

}
