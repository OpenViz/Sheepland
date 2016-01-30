package model;

import java.awt.Point;
import java.io.Serializable;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class for the Ovines. It extends the class Identifier that assigns an unique id
 * to every Ovine instantiated.
 * The Ovine has a Point position that is used to set his position in the GUI.
 */
public class Ovine extends Identifier implements Animal, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Point ovinePosition;
	
	/**
	 * Builder of the Ovine. Sets the id of the Ovine calling 
	 * the builder of its superclass Identifier.
	 */
	public Ovine() {
		super();
	}

	/**
	 * @return the Point position of the Ovine.
	 */
	public Point getOvinePosition() {
		return ovinePosition;
	}

	/**
	 * @param position Point to set for the Ovine.
	 */
	public void setOvinePosition(Point ovinePosition) {
		this.ovinePosition = ovinePosition;
	}
}