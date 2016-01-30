package server;

import java.util.ArrayList;
import java.util.List;

import utility.ServerLogger;
import utility.SysoPrinter;
import view.ViewInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class GameInstantiator implements Runnable {
	private final static int MAXPLAYER = 4;
	private Integer uniqueGameName;
	private List<ViewInterface> views;
	private int numberOfPlayer;
	
	/**
	 * Create a new GameIstantiator. 
	 * The uniqueGameName starts from 0.
	 * The numberOfPlayer starts from 0.
	 * An empty list of viewsInterface.
	 */
	public GameInstantiator(){
		this.uniqueGameName = Integer.valueOf(0);
		this.views = new ArrayList<ViewInterface>();
		this.numberOfPlayer = 0;
	}
	
	/**
	 * Start the gameIstantiator.
	 * He will wait until there are 4 players or until there are almost 2 players
	 * and 15 second are passed.
	 * Then he will start the game with the first 4 player.
	 * Every second check if there are disconnected player.
	 */
	public void run() {
		SysoPrinter.println("Start gameIstanciator");
		while(true){
			int time = 0;
			while(numberOfPlayer < 4 && time < 15){
				try {
					if(numberOfPlayer >= 2){
						Thread.sleep(1000);
						time++;
						this.findDisconnectedPlayer();
					}
				} catch (InterruptedException e) {
					ServerLogger.printOnLogger("GameIstantiator", e);
				}
			}
			SysoPrinter.println("SheepLand starts");
			this.startSheepLand();
			}
		}
	
	/**
	 * Add the view(parameter) to the GameIstantiator's views.
	 * @param view: that has to be added.
	 */
	public synchronized void addView(ViewInterface view){
		this.views.add(view);
	}
	
	/**
	 * Add 1 to the numberOfPlayer.
	 */
	public void addNumberOfPlayer(){
		this.numberOfPlayer++;
	}
	
	/**
	 * Starts Sheepland.
	 * Get the first 4 player(view) and pass it to the GameStarter( that starts the game).
	 * The uniqueName is incremented by 1.
	 */
	public synchronized void startSheepLand(){
		List<ViewInterface> temporany = new ArrayList<ViewInterface>();
		int i = MAXPLAYER;
		if(this.numberOfPlayer < MAXPLAYER){
			i = this.numberOfPlayer;
		}
		for(int k = 0; k < i; k++){
			temporany.add(this.views.get(k));
		}
		for(int k = 0; k < i ; k++){
			this.views.remove(0);
		}
		
		
		Thread thread = new Thread(new GameStarter(this.uniqueGameName, temporany));
		this.uniqueGameName = this.uniqueGameName + 1 ;
		SysoPrinter.println("Starting Game...");
		this.numberOfPlayer = 0;
		thread.start();
	}
	
	/**
	 * This method find the disconnected Player and eliminate it from Views.
	 * Find a disconnected player sending to all views a "ping".
	 * If there are some connection issue the GameIstantiator will eliminate the view and
	 * will decrease the number of player by 1.
	 */
	private void findDisconnectedPlayer(){
		int i = 0;
		try{
			if(!views.isEmpty()){
				for( i = 0; i<views.size(); i++){
					if(this.views.get(i)!= null){
						this.views.get(i).printString(null);
					}
				}
			}
		}catch(Exception e){
			ServerLogger.printOnLogger("GameInstantiator", e);
			this.views.remove(i);
			this.numberOfPlayer--;
			
		}
		
	}

}
