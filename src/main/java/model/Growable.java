package model;

import java.io.Serializable;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Interface for all Objects that can grow.
 */

public interface Growable extends Serializable {

	/**
	 * Grow the Object in something other and return it.
	 * @return the Object grown.
	 */
	public Object grow();
}
