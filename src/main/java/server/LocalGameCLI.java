package server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.GameController;
import utility.Observer;
import utility.SysoPrinter;
import view.CLIViewRMI;
import view.ViewInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class LocalGameCLI {
	private Scanner input;
	private List<String> nicknames;
	private List<ViewInterface> views;
	private int numberOfPlayers;
	private GameController gameController;
	

	/**
	 * Create a new LocalGameByCLI. Use the input and output stream of the System.
	 */
	public LocalGameCLI(){
		this.input = new Scanner(System.in);
		this.views = new ArrayList<ViewInterface>();
		this.nicknames = new ArrayList<String>();
		this.numberOfPlayers = 0;
		this.gameController = null;
	}
	
	/**
	 * Start the local game by CLI. Ask name,create Views,subscribe views
	 * and finally will start the game.
	 * @throws RemoteException: connection issue.
	 */
	public void startLocalGame() throws RemoteException{
		this.askName();
		SysoPrinter.println("Starting game...");
		this.gameController = new GameController(this.views);
		this.subscribeViews();
		this.gameController.getGame().notifyObserver();
		gameController.startGame();
		
		
		
	}
	
	/**
	 *  * Helper Method:startLocalGame.
	 * Ask the name of the player. The asking time will end after 4 name or
	 * if the user insert the string "quit". 
	 * The number of player has to be >=2.
	 * Then creates new view and add it to the ViewsList.
	 * @throws RemoteException: connectio issue.
	 */
	private void askName() throws RemoteException{
		String in = "";
		while(true){
			SysoPrinter.println("Insert the name of the Player[write quit to finish]: ");
			in = input.nextLine();
			if("quit".equals(in)){
				if(this.numberOfPlayers >= 2){
					break;
				}else{
					SysoPrinter.println("Not enough players");
					continue;
				}
			}
			nicknames.add(in);
			views.add(new CLIViewRMI());
			this.numberOfPlayers++;
			if(this.numberOfPlayers == 4){
				break;
			}
		}
		for(int i = 0; i< numberOfPlayers; i++ ){
			((CLIViewRMI) this.views.get(i)).setNickname(this.nicknames.get(i));
		}
	}
	
	/**
	 * Helper Method:startLocalGame.
	 * All views that are saved in listviews subscribe to the gameController as Observer.
	 * @throws RemoteException: connection issue.
	 */
	private void subscribeViews() throws RemoteException{
		for(ViewInterface view: this.views){
			view.setGame(this.gameController.getGame());
			this.gameController.getGame().addObserver((Observer) view);
			}
	}

}
