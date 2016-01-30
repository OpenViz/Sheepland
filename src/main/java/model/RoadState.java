/**
 * 
 */
package model;

import java.io.Serializable;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Enumeration of the states that a Road can assume.
 */
public enum RoadState implements Serializable {
	FREE, FENCED, SPECIALFENCED, SHEPHERD;
}
