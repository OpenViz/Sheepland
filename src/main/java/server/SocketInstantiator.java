package server;

import java.io.IOException;
import java.net.Socket;


import utility.ServerLogger;
import view.ServerSocketAdapter;
import view.ViewInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class SocketInstantiator implements Runnable {
	private GameInstantiator gameIstantiator;
	private Socket client;
	
	/**
	 * Create a new SocketIstatiator. We have to specify the gameIstantiator, with it works together,
	 *  and the Client from he has received the connection.
	 * @param gameIstantiator: references to GameIstantiator.
	 * @param client: the client who wants to connect to the server.
	 * @throws IOException: connection issue.
	 */
	public SocketInstantiator(GameInstantiator gameIstantiator, Socket client) throws IOException{
		this.gameIstantiator = gameIstantiator;
		this.client = client;
	}

	/**
	 * Received the connection he creates a new View(ServerSocketAdapter)
	 *  that have to speak with the client.
	 *  First thing he asks the nickname of the player.
	 * Then add the view to the gameIstantiator and increments the number of player of the
	 * gameIstantiator.
	 */
	public void run() {
		try {
				ViewInterface view;
				view = new ServerSocketAdapter(this.client);
				view.printString("Insert name:");
				((ServerSocketAdapter)view).setNickname(view.getString());
				((ServerSocketAdapter)view).setConnectionType(view.getString());
				this.gameIstantiator.addView(view);
				this.gameIstantiator.addNumberOfPlayer();
			} catch (IOException e) {
				ServerLogger.printOnLogger("SocketIstatiator", e);
			} 
	}

}
