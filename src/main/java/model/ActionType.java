package model;

import java.io.Serializable;


/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Enumeration of the actions that a Player can do in his turn.
 */
public enum ActionType implements Serializable {
	MOVESHEPHERD,MOVEOVINE,BUYCARD,COUPLING1,
	COUPLING2,KILLOVINE;
}
