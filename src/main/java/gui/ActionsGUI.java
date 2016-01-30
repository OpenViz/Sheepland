/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ActionType;
import view.ViewGUIInterface;

/**
 * @author manueltanzi
 * @author vittorioselo
 */
public class ActionsGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int WINDOW_WIDTH = 110;
	private static final int WINDOW_HEIGHT = 500;
	private static final int WINDOW_TOP = 22;
	
	private ButtonActionGUI moveShepherdButton;
	private ButtonActionGUI moveOvineButton;
	private ButtonActionGUI buyCardButton;
	private ButtonActionGUI coupling1Button;
	private ButtonActionGUI coupling2Button;
	private ButtonActionGUI killOvineButton;
	
	private ViewGUIInterface viewGUIInterface;
	
	/**
	 * Creates a JPanel with 6 buttons. One for each actions.
	 * For mare details see {@link ButtonActionGUI}.
	 */
	public ActionsGUI() {
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + WINDOW_TOP));
		super.setBackground(new Color(35, 161, 246));
		this.setLayout(new FlowLayout(JLabel.CENTER,5,10));
		
		moveShepherdButton = new ButtonActionGUI(ActionType.MOVESHEPHERD);
		moveOvineButton = new ButtonActionGUI(ActionType.MOVEOVINE);
		buyCardButton = new ButtonActionGUI(ActionType.BUYCARD);
		coupling1Button = new ButtonActionGUI(ActionType.COUPLING1);
		coupling2Button = new ButtonActionGUI(ActionType.COUPLING2);
		killOvineButton = new ButtonActionGUI(ActionType.KILLOVINE);
		this.add(moveShepherdButton);
		this.add(moveOvineButton);
		this.add(buyCardButton);
		this.add(coupling1Button);
		this.add(coupling2Button);
		this.add(killOvineButton);
		
		this.setVisible(false);
	}

	
	/**
	 * Gets the specify view that the panel uses to communicate with the Server.
	 * @return ViewGUIInterface: that the ActionsGUI uses to communicate.
	 */
	public ViewGUIInterface getViewGUIInterface() {
		return viewGUIInterface;
	}

	/**
	 * Sets the ViewGUIINterface of the panel and of all buttons. 
	 * @param viewGUIInterface: that is set.
	 */
	public void setViewGUIInterface(ViewGUIInterface viewGUIInterface) {
		this.viewGUIInterface = viewGUIInterface;
		this.moveShepherdButton.setViewGUI(viewGUIInterface);
		this.moveOvineButton.setViewGUI(viewGUIInterface);
		this.buyCardButton.setViewGUI(viewGUIInterface);
		this.coupling1Button.setViewGUI(viewGUIInterface);
		this.coupling2Button.setViewGUI(viewGUIInterface);
		this.killOvineButton.setViewGUI(viewGUIInterface);
		this.setVisible(true);
	}
}
