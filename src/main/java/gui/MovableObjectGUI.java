//Copyright (C) 2014  Paduano Francesco
/**
 * 
 */
package gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;


/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 * Class for the Movable Objects of the GUI.
 * Permits to these Objects to move implementing the method moveTo().
 */

/*
 * This class is an extension of a JLabel
 * that add to the standard JLabel the possibility
 * to move smoothly from a place to another
 * Simply call the moveTo(..) method specifying
 * the destination in pixels and the time of 
 * the animation.
 */
public class MovableObjectGUI extends JLabel {
	private static final long serialVersionUID = 1L;
	
	private static final long DURATION_MUL = (long) 1E6;
	private static final int TIMER_DELAY = 10;

	private boolean isDraggable;
	private boolean isDroppable;
	
	private Point endPosition;
	private Point startPosition;
	private long startingTime;
	private long animationDuration;
	private boolean animating;

	/**
	 * Builder of the MovableObjectGUI.
	 * It sets isDraggeble and isDroppable for the MovableObjectGUI to false.
	 */
	public MovableObjectGUI() {
		super();
		this.isDraggable = false;
		this.isDroppable = false;
	}
	
	
	/**
	 * Starts an animation from the place where the component
	 * is placed to the position provided. 
	 * Moves the MovableObjectGUI to the Point inserted as parameter
	 * in a time also inserted as parameter in milliseconds.
	 * @param destination Point corresponding to the destination on the screen
	 * @param timeMillisec time required by the animation
	 */
	public void moveTo(Point destination, int timeMillisec) {
		startingTime = System.nanoTime();
		animationDuration = (long) (timeMillisec*DURATION_MUL);
		startPosition = getBounds().getLocation();
		this.endPosition = destination;
		animating = true;
		performAnimation();
	}
	 
	/**
	 * @return true if the MovableObjectGUI is animating, else false.
	 */
	public boolean isAnimating() {
		return animating;
	}

	/**
	 * @return true if the MovableObjectGUI is draggable, else false.
	 */
	public boolean isDraggable() {
		return isDraggable;
	}

	/**
	 * @param isDraggable to set as true or false to the MovableObjectGUI.
	 */
	public void setDraggable(boolean isDraggable) {
		this.isDraggable = isDraggable;
	}

	/**
	 * @return true if the MovableObjectGUI is droppable, else false.
	 */
	public boolean isDroppable() {
		return isDroppable;
	}

	/**
	 * @param isDroppable to set as true or false to the MovableObjectGUI.
	 */
	public void setDroppable(boolean isDroppable) {
		this.isDroppable = isDroppable;
	}

	/**
	 * Method that performs the animation of the movement of the MovableObjectGUI.
	 */
	private void performAnimation() {
		ActionListener animationTask = new ActionListener() {
	
			/**
			 * @also
			 * 		Performs the animation of the object.
			 */
			public void actionPerformed(ActionEvent evt) {
				//get the current time in nanoseconds
				long now = System.nanoTime();
				//progress is a number between 0 and 1 represents the progress of the animation
				//according to the passed time from the start of the animation
				double progress =  now > (startingTime + animationDuration) ?
						1 : (double)(now - startingTime) / (double)animationDuration;
				if(now < startingTime) {
					progress = 0;
				}
	
				//Compute the new position using a simple proportion 
				double newX = startPosition.x + (endPosition.x - startPosition.x)*progress;
				double newY = startPosition.y + (endPosition.y - startPosition.y)*progress;
				
				Point newPosition = new Point((int)newX,(int)newY);
				
				//check whether the animation must end
				if(progress == 1) {
					((Timer)evt.getSource()).stop();
					animating = false;
				}
			
				//Set the current location
				MovableObjectGUI.this.setLocation(newPosition);
			}
		};
	
		//Set up a timer that fire each 10ms, calling 
		//the method declare in the animationTask
		Timer timer = new Timer(TIMER_DELAY,animationTask);
		timer.start();
	}
}