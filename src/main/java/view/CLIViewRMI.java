/**
 * 
 */
package view;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.NoSuchElementException;
import java.util.Scanner;

import utility.Observer;
import utility.ServerLogger;
import utility.SysoPrinter;
import model.BlackSheep;
import model.Card;
import model.EventType;
import model.GameInterface;
import model.Lamb;
import model.Land;
import model.Ovine;
import model.Player;
import model.Ram;
import model.Road;
import model.Sheep;
import model.Shepherd;

/**
 * @author manueltanzi
 *
 */
public class CLIViewRMI extends UnicastRemoteObject implements ViewInterface,Observer,Serializable{
	
	private static final long serialVersionUID = 1L;
	private String nickname;
	private Scanner scanner;
	private GameInterface game;
	private static final String CONNECTIONTYPE = "RMI";
	
	/**
	 * Create a CLIViewRMI.
	 * It receives input stream from input system stream and communicate with the Server.
	 * @throws RemoteException:Connectio issue.
	 */
	public CLIViewRMI() throws RemoteException {
		scanner = new Scanner(System.in);
		this.game = null;
		this.nickname = null;
	}
	
	/**
	 * Sets the GameInterface to the view.
	 */
	public void setGame(GameInterface game) throws RemoteException {
		this.game = game;
	}
	
	/**
	 * Sets the CLIViewRMI's nickname. 
	 * It indicates the owner of the view.
	 */
	public void setNickname(String nickname) throws RemoteException {
		this.nickname = nickname;
	}
	
	/**
	 * Gets the CLIViewRMI's nickname.
	 */
	public String getNickname(){
		return this.nickname;
	}
	
	
	/**
	 * @also
	 * 		Sends a string to the server.
	 */
	public String getString() throws RemoteException {
		try{
			return scanner.nextLine();
		}catch(NoSuchElementException e){
			ServerLogger.printOnLogger("CLIViewRMI", e);
			throw new RemoteException();
		}
	}
	
	
	/**
	 * @also
	 * 		Receive a string from the server.
	 */
	public void printString(String string) {
		if(string == null){
			return;
		}
		SysoPrinter.println(string);
	}
	
	
	/**
	 * @also
	 * 		Print the board on the CLI. 
	 * 		If we are at the end of the game  prints the list of winners players.
	 */
	public void update() throws RemoteException {
		if(game.getGameInfo().getActiveEvent()!= null
				&& game.getGameInfo().getActiveEvent().equals(EventType.WINNERTIME)){
			this.printPointAndWinner();
		}else{
			this.printBoard();
		}
	}
	
	
	/**
	 * Returns the string that rappresent the connection used.
	 * The string is "RMI".
	 */
	public String getConnectionType() {
		return CLIViewRMI.CONNECTIONTYPE;
	}
	
	
	/**
	 * Gets the gameInterface saved.
	 */
	public GameInterface getGameInterface() throws RemoteException{
		return this.game;
	}
	
	/**
	 * Method helper to update.
	 * Print on CLI the name of the winner players.
	 * @throws RemoteException
	 */
	private void printPointAndWinner() throws RemoteException{
		for(Player player: game.getGameBoard().getPlayers()){
			SysoPrinter.println(player.getNickname()+" scores "+player.getGold());
		}
		SysoPrinter.println("Winners:");
		for(Player player: game.getGameInfo().getWinnerPlayers()){
			SysoPrinter.println(player.getNickname());
		}
	}

	/**
	 * Prints the Board of viewInterface.
	 * @throws RemoteException:connectio Issue.
	 */
	private void printBoard() throws RemoteException {
		for(Land land: this.game.getGameBoard().getLands()){
			SysoPrinter.print("\n Land number:" + land.getId() + " - Type:" + land.getLandType() + " - Ovines Id: ");
			for(Ovine ovine: land.getOvines()) {
				if(ovine instanceof Sheep) {
					SysoPrinter.print("Sheep["+ ovine.getId() + "]");
				} else if(ovine instanceof Ram) {
					SysoPrinter.print("Ram["+ ovine.getId() + "]");
				} else if(ovine instanceof Lamb) {
					SysoPrinter.print("Lamb" + ((Lamb) ovine).getDayLife() + "DayLife[" + ovine.getId() + "]");
				} else if(ovine instanceof BlackSheep) {
					SysoPrinter.print("BlackSheep" + "[" + ovine.getId() + "]");
				}
			}
		}
		for(Road road : this.game.getGameBoard().getRoads()){
			SysoPrinter.print("\n Road number:"+ road.getId()+" - Near Lands:"+road.getNeighboringLands().get(0).getId()+" & "+
		road.getNeighboringLands().get(1).getId()+"-State:"+road.getRoadState()+" - Near Roads:");
			for(Road nearroad : road.getNeighboringRoads()){
				SysoPrinter.print(nearroad.getId()+" ");
			}
		}
		SysoPrinter.println("The wolf is on land number:"+this.game.getGameBoard().getWolf().getLandPosition().getId());
		for(Player player: this.game.getGameBoard().getPlayers()){
			SysoPrinter.println("The player: " + player.getNickname() + " has "+ player.getGold() +" golds");
			for(Shepherd shepherd: player.getShepherds()){
				if(shepherd.getRoad()==null){
					SysoPrinter.println("The shepherd of player: " + player.getNickname() + " has to be positioned");
				}else{
				SysoPrinter.println("The shepherd of player: " + player.getNickname() + " is on road: "+shepherd.getRoad().getId());
				}
			}
		}
		SysoPrinter.print("Buyable cards(Price/Type):-");
		for(Card card: this.game.getGameBoard().getCards()){
			if(card.getPrice()==5){
				SysoPrinter.print("Out of Stock/"+card.getLandType());
			}	else{
				SysoPrinter.print(card.getPrice()+"/"+card.getLandType()+"-");
			}
		}
		SysoPrinter.println("");
		SysoPrinter.print("My cards:-");
		for(Player player: this.game.getGameBoard().getPlayers()){
			if(player.getNickname().equals(this.nickname)){
				for(Card card: player.getCards()){
					SysoPrinter.print(card.getLandType()+"-");
				}
			}
		}
		SysoPrinter.println("");
	}
	
}
