package gui;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Superclass for all the JLabels that represents the Ovines in the GUI.
 * It's a Selectable and Movable Object.
 */
public class OvineGUI extends SelectableObjectGUI {
	private static final long serialVersionUID = 1L;
	int ovineId;

	/**
	 * Builder of the OvineGUI. 
	 * Takes its ovineId as parameter.
	 * @param ovineId for the OvineGUI builded.
	 */
	public OvineGUI(int ovineId) {
		this.ovineId = ovineId;
	}
	
	/**
	 * @return the ovineId of the OvineGUI.
	 */
	public int getOvineId() {
		return ovineId;
	}

	/**
	 * @param ovineId to set to the OvineGUI.
	 */
	public void setOvineId(int ovineId) {
		this.ovineId = ovineId;
	}
	
}