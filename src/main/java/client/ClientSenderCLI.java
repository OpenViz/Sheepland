package client;


import java.rmi.RemoteException;

import utility.ServerLogger;
import view.ViewInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class ClientSenderCLI implements Runnable {
	private ViewInterface view;
	
	/**
	 * Create a ClientSenderCLI. Send all message to the server that is specify the view.
	 * @param view:through which it sends the message.
	 */
	public ClientSenderCLI(ViewInterface view){
		this.view  = view;
	}

	/**
	 * @also
	 * 		Could send always a message to the Server.
	 */
	public void run() {
		while(true){
			try {
				view.getString();
			} catch (RemoteException e) {
				ServerLogger.printOnLogger("ClientSender", e);
			}
		}
		
	}

}
