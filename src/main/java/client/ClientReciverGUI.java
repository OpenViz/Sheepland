package client;


import java.io.IOException;

import utility.ServerLogger;
import view.GUIViewClientSocket;
import view.ViewInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class ClientReciverGUI implements Runnable {
private ViewInterface view;
	
	/**
	 * Create a ClientReciverGUI. Receives all message on the specify view.
	 * @param view: on which have to receive.
	 */
	public ClientReciverGUI(ViewInterface view){
		this.view = view;
	}

	/**
	 * @also
	 *  	Wait and receives all the message from the Server.
	 */
	public void run() {
		while(true){
			try {
				((GUIViewClientSocket)view).reciveFromServer();
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
