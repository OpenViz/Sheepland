package client;


import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import utility.ServerLogger;
import utility.SysoPrinter;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class Client {
	static Scanner in;
	
	private Client() {
	}
	
	/**
	 * The method starts the client.
	 * The client can choose to use CLI or GUI.
	 * Then he will choose to connect in RMI or Socket to the server.
	 * @param args: parameter passed to the main.
	 * @throws ClassNotFoundException: connection issue.
	 * @throws IOException: connection issue.
	 * @throws AlreadyBoundException: name already in use in the registry.
	 * @throws NotBoundException: object non found in the registry.
	 */
	public static void main(String[] args) throws ClassNotFoundException, IOException, AlreadyBoundException, NotBoundException {
		in = new Scanner(System.in);
		SysoPrinter.println("Press CLI[1] or GUI[2]:" );
		int number = 0;
		int selection = 0;
		number = getNumberFromConsole();
		//If the client use CLI
		if(number == 1){
			SysoPrinter.println("Press Socket[1] or RMI{2]:" );
			selection = 0;
			selection = getNumberFromConsole();
			//Connection by Socket
			if(selection == 1){
				CLISocketClient.start();
			//Connection by RMI
			}else if(selection == 2){
				CLIRMIClient.start();
			} else{
				SysoPrinter.println("ERROR." );
			}	
		}
		//If the client use GUI
		if(number == 2){
			SysoPrinter.println("Press Socket[1] or RMI{2]:" );
			selection = 0;
			selection = getNumberFromConsole();
			//Connection by Socket
			if(selection == 1){
				GUISocketClient.start();
			//Connection by RMI
			}else if(selection == 2){
				GUIRMIClient.start();
			} else{
				SysoPrinter.println("ERROR." );
			}
		}
	}

	private static int getNumberFromConsole(){
		int number = 0;
		while(number != 1 && number != 2){
			try{
				SysoPrinter.println("Insert the service number selected: ");
				number = in.nextInt();
				return number;
			}catch(InputMismatchException e){
				in.nextLine();
				ServerLogger.printOnLogger(" StartingServer", e);				
			}catch(NoSuchElementException e){
				ServerLogger.printOnLogger(" StartingServer", e);
				in.nextLine();
			}
		}
		return 0;
	}
}
