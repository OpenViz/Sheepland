/**
 * 
 */
package model;

import java.io.Serializable;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Class for the Sheep. It's an Ovine.
 */
public class Sheep extends Ovine implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Builder of the Sheep.
	 */
	public Sheep() {
	}
	
	/**
	 * Builder of the Sheep that permits the manual settings of the id.
	 * The id of the Sheep is taken as parameter instead to be set by its superclasses.
	 * @param ovineId to set to the Sheep builded.
	 */
	public Sheep(int ovineId) {
		this.setId(ovineId);
	}
	
	/**
	 * Override of the method toString for the Sheep. 
	 * @return Sheep as String.
	 */
	@Override
	public String toString() {
		return "Sheep";
	}
}
