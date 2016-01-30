package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import utility.ServerLogger;
import utility.SysoPrinter;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class SocketListener implements Runnable {

	private ServerSocket server;
	private static final int PORT = 4000;
	private GameInstantiator gameIstantiator;
	
	/**
	 * Create a new SocketListeners. We have to specified the gameIstantiator with it works together.
	 * @param gameIstantiator: references to GameIstantiator
	 * @throws IOException: connection issue.
	 */
	public SocketListener(GameInstantiator gameIstantiator) throws IOException{
		this.server = new ServerSocket(PORT);
		this.gameIstantiator = gameIstantiator;
		SysoPrinter.println("Server ready on port: "+ PORT);
	}
	/**
	 * Run the socketListener. This Thread waits for a connection. 
	 * When a connection is received a new Thread starts. It will run the SocketIstantiator.
	 * Then it will wait,again , a new connection.
	 */
	public void run() {
		while(true) {
			try {
				SysoPrinter.println("Waiting for connection");
				Socket client = this.server.accept();
				SysoPrinter.println("Connection recived:" + client.getInetAddress());
				SocketInstantiator connection = new SocketInstantiator(this.gameIstantiator, client);
				Thread t1 = new Thread(connection);
				t1.start();
			} catch (IOException e) {
				ServerLogger.printOnLogger("SocketServer", e);
			}	
		}	
	}

}
