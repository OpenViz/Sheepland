package server;

import guimodelsupport.GameGUISupporter;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import model.GameInterface;
import utility.Observer;
import utility.ServerLogger;
import view.ViewInterface;
import controller.GameController;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class GameStarter implements Runnable {
	private GameController gameController;
	private List<ViewInterface> views;
	private Integer uniqueID;
	
	/**
	 * Create a new GameStarte. The new game starter has the uniqueID as identification.
	 * @param uniqueID: is the identification of the object.
	 * @param views: the list of the views that belongs to the players.
	 */
	public GameStarter(Integer uniqueID,List<ViewInterface> views){
		this.gameController = null;
		this.views = views;
		this.uniqueID = uniqueID;
	}
	/**
	 * Get the GameStarter's identification.
	 * @return uniqueID: the identification of the GameStarter.
	 */ 
	public Integer getUniqueID(){
		return this.uniqueID;
	}
	
	/**
	 * Starts SheepLand.
	 * Set the observer of all view and notify them.
	 */
	public void run() {
		try {
			this.gameController = new GameController(this.views);
			new GameGUISupporter(gameController.getGame().getGameBoard());
			this.setObserverForView();
			this.gameController.getGame().notifyObserver();
			this.gameController.startGame();
		} catch (RemoteException e) {
			ServerLogger.printOnLogger("GameStarter" + this.uniqueID, e);	
		} catch (AlreadyBoundException e) {
			ServerLogger.printOnLogger("Game starter" + this.uniqueID, e);
			return;
		} catch (NotBoundException e) {
			ServerLogger.printOnLogger("Game starter" + this.uniqueID, e);
			return;
		}
		
	}
	
	/**
	 * Set the all views as observer of the Game.
	 * Bind the game in the registry with name:GameStarter's identification.
	 * The RMI view are link with the Game in RMI.
	 * The socket view are connect directly.
	 * @throws RemoteException: connection issue.
	 * @throws AlreadyBoundException: there is already this name in the registry.
	 * @throws NotBoundException: there is no such element in the registry.
	 */
	private void setObserverForView() throws RemoteException, AlreadyBoundException, NotBoundException{
		String name = uniqueID.toString();
		Registry registry = LocateRegistry.getRegistry();
		registry.bind(name,this.gameController.getGame());
	for(ViewInterface view : views){
		if("RMI".equals(view.getConnectionType()) || "RMIGUI".equals(view.getConnectionType()) ){
			((ViewInterface) registry.lookup(view.getNickname())).setGame((GameInterface)registry.lookup(name));
			this.gameController.getGame().addObserver((Observer) registry.lookup(view.getNickname()));
		}else if("SocketCLI".equals(view.getConnectionType()) || "SocketGUI".equals(view.getConnectionType())){
			view.setGame(this.gameController.getGame());
			this.gameController.getGame().addObserver((Observer) view);
			
		}
	}
	}


}
