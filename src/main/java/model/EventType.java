package model;

import java.io.Serializable;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 * Enumeration of the events that occur during the Game.
 */
public enum EventType implements Serializable {
	SETSHEPHERDSTARTINGPOSITIONS, MOVEBLACKSHEEP,
	GROWTH, CHOOSEACTIVESHEPHERD, MOVEWOLF, MARKET, WINNERTIME;
}
