package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

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
import utility.Observer;
import utility.ServerLogger;
import utility.SysoPrinter;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class CLIViewClientSocket implements ViewInterface, Observer, Serializable {
	private static final long serialVersionUID = 1L;
	
	private Socket server;
	private GameInterface game;
	private Scanner in;
	private ObjectOutputStream out;
	private String nickname;
	private ObjectInputStream objectInputStream;
	
	/**
	 * Create a new CLIViewClientSocket that communicate with specific server.
	 * Take the input and output stream from the server.
	 * Create a specific stream from the system input stream.
	 * @param server: with communicates to.
	 * @throws IOException:connection issue.
	 * @throws ClassNotFoundException: connection issue.
	 */
	public CLIViewClientSocket(Socket server) throws IOException, ClassNotFoundException{
		this.server = server;
		this.objectInputStream = new ObjectInputStream(this.server.getInputStream());
		this.out = new ObjectOutputStream(this.server.getOutputStream());
		this.in = new Scanner(System.in);
		this.game = null;
		this.nickname  = null;
		
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
	 * @also
	 * 		Send a string to the server.
	 */
	public String getString() throws RemoteException {
		String temporany = this.in.nextLine();
		try {
			this.out.writeObject(temporany);
			this.out.flush();
		} catch (IOException e) {
			ServerLogger.printOnLogger("ClientViewSocket", e);
		}
		return temporany;
	}

	/**
	 * @also
	 * 		Receive a String from the server.
	 */
	public void printString(String string) throws RemoteException {
		if(string == null){
			return;
		}else if("UPDATE".equals(string)){
			this.setGame(this.getGameFromServer());
			this.update();
		}else{
			SysoPrinter.println(string);
		}
		
	}

	/**
	 *Set the GameInterface to the view.
	 */
	public void setGame(GameInterface game)
			throws RemoteException {
		this.game = game;
		
	}

	/**
	 * Get the CLIViewClientSocket's nickname.
	 */
	public String getNickname() throws RemoteException {
		return this.nickname;
	}
	
	/**
	 * Set the CLIViewClientSocket's nickname. 
	 * It indicates the owner of the view.
	 */
	public void setNickname(String nickname) throws RemoteException{
		this.nickname = nickname;
	}
	
	
	/**
	 * Returns the type of connection is used. In this case sockets are used..
	 */
	public String getConnectionType() throws RemoteException {
		return "SocketCLI";
	}
	 
	
	/**
	 * Receive the String from the server. After received  it will print them.
	 * @throws ClassNotFoundException:connection issue.
	 * @throws IOException: connection issue.
	 */
	public void reciveFromServer() throws ClassNotFoundException, IOException{
		String temp = (String)this.objectInputStream.readObject();
		if(temp != null){
			this.printString(temp);
		}
		
	}
	
	
	/**
	 * Gets the gameInterface saved.
	 */
	public GameInterface getGameInterface() throws RemoteException{
		return this.game;
	}
	
	/**
	 * Send the connection type of the view to the Server.
	 * @throws RemoteException: connection issue.
	 * @throws IOException: connection issue.
	 */
	public void sendConnectionType() throws RemoteException, IOException{
		this.out.writeObject(this.getConnectionType());
		this.out.flush();
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

	/**
	 * Helper method to the update.
	 * Receive a GameInterface from the Server and return it.
	 * @return GameInterface: received from the server.
	 */
	private GameInterface getGameFromServer(){
		try {
			GameInterface temp = (GameInterface)this.objectInputStream.readObject();
			while(temp== null){
				temp = (GameInterface)this.objectInputStream.readObject();
			}
			this.game = temp;
			return temp;
			
		} catch (ClassNotFoundException e) {
			ServerLogger.printOnLogger("ClientViewSocket", e);
		} catch (IOException e) {
			ServerLogger.printOnLogger("ClientViewSocket", e);
		}
		return null;
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
	

}
