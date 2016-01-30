package model;

import java.io.Serializable;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 *  Class for the Ram. It's an Ovine. 
 */
public class Ram extends Ovine implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Builder of the Ram.
	 */
	public Ram() {
	}
	
	/**
	 * Builder of the Ram that permits the manual settings of the id.
	 * The id of the Ram is taken as parameter instead to be set by its superclasses.
	 * @param ovineId to set to the Ram builded.
	 */
	public Ram(int ovineId) {
		this.setId(ovineId);
	}

	/**
	 * Override of the method toString for the Ram. 
	 * @return Ram as String.
	 */
	@Override
	public String toString(){
		return "Ram";
	}
}
