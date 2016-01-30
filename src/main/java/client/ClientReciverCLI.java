package client;


import java.io.IOException;

import utility.ServerLogger;
import view.CLIViewClientSocket;
import view.ViewInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class ClientReciverCLI implements Runnable {
	private ViewInterface view;
	
	
	/**
	 * Create a ClientReciverCLI. Receives all message on the specify view.
	 * @param view: on which have to receive.
	 */
	public ClientReciverCLI(ViewInterface view){
		this.view = view;
	}

	/**
	 * @also
	 * 		Wait and receives all the message from the Server.
	 */
	public void run() {
		while(true){
			try {
				((CLIViewClientSocket)view).reciveFromServer();
			} catch (ClassNotFoundException e) {
				ServerLogger.printOnLogger("ClientReciver", e);
				break;
			} catch (IOException e) {
				ServerLogger.printOnLogger("ClientReciver", e);
				break;
			}
		}
	}
	
}
