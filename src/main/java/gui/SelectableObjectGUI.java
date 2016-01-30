package gui;

import java.awt.Cursor;
import java.awt.Graphics;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class for the Selectable Object of the GameBoardGUI.
 */
public class SelectableObjectGUI extends MovableObjectGUI {
	private static final long serialVersionUID = 1L;

	private boolean isSelected;
	
	/**
	 * Builder of the SelectableObjectGUI.
	 * Sets isSelected as false.
	 */
	public SelectableObjectGUI() {
		this.isSelected = false;
	}
	
	/**
	 * Method that repaint the SelectableObjectGUI when this is selected.
	 */
	@Override
	public void paint(Graphics g) {
		if(this.isSelected){
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		super.paint(g);
	}

	/**
	 * @param s to set as true or false to the SelectableObjectGUI.
	 */
	public void isSelected(boolean s) {
		isSelected = s;
		repaint();
	}
	
	/**
	 * @return true if the SelectableObjectGUI is selected, else false.
	 */
	public boolean isSelected() {
		return isSelected;
	}
	
}

