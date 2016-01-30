package server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import utility.ServerLogger;
import utility.SysoPrinter;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class StartingServer {
	
	private static Scanner input;

	/**
	 * Create a StartingServer.
	 */
	private StartingServer(){
		
	}

	/**
	 * Run the startingServer main.You could choose to use it in LocalHost or to use RMI 
	 * on port 1099( create a new registry with bind name SheepLand) and Socket on port 4000.
	 * @param args
	 */
	public static void main(String[] args) {
		int number = 0;
		input = new Scanner(System.in);
		SysoPrinter.println("What kind of service do you want?");
		SysoPrinter.println("1.Local Game");
		SysoPrinter.println("2.RMI[implemented] & Socket [implemented]");
		//chose the service.
		while(number != 1 && number != 2){
			try{
				SysoPrinter.println("Insert the service number selceted");
				number = input.nextInt();
			}catch(InputMismatchException e){
				input.nextLine();
				ServerLogger.printOnLogger(" StartingServer", e);				
			}catch(NoSuchElementException e){
				ServerLogger.printOnLogger(" StartingServer", e);
				input.nextLine();
			}
		}
		//Local Host
		if(number == 1){
			LocalGameCLI local = new LocalGameCLI();
				try {
					local.startLocalGame();
				} catch (RemoteException e) {
					ServerLogger.printOnLogger("StartingServer", e);
				}
		//RMI and Socket
		}else if(number ==2){
			try {
				//Create the Lobby of the game and start it
				GameInstantiator gameIstantiator = new GameInstantiator();
				Thread gameIstantiatorThread = new Thread(gameIstantiator);
				gameIstantiatorThread.start();
				//Create registry.
				Registry registry = LocateRegistry.createRegistry(1099);
				String name = "SheepLandRMI";
				//Create a new RMIListeners
				RMIListenersInterface rMIListeners = new RMIListeners( gameIstantiator);
				//Create and start the socketListener.
				Thread socketListener = new Thread(new SocketListener(gameIstantiator));
				socketListener.start();
				try {
					registry.bind(name, rMIListeners);
					SysoPrinter.println("Start Server in RMI connection");
				} catch (AlreadyBoundException e) {
					ServerLogger.printOnLogger("StartingServer", e);
				}
				
			} catch (RemoteException e) {
				ServerLogger.printOnLogger("StartingServer", e);
			} catch (IOException e1) {
				ServerLogger.printOnLogger("StartingServer", e1);
			}	
		}else{
			SysoPrinter.println("Some error occurs");
		}
	}
	
}
