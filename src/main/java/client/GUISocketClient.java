/**
 * 
 */
package client;


import gui.GameGUI;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;

import utility.ServerLogger;
import utility.SysoPrinter;
import view.GUIViewClientSocket;
import view.ViewGUIInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class GUISocketClient {
	private static final int PORT = 4000;
	private static final String IP = "127.0.0.1";
	
	/**
	 * Private constructor for static methods.
	 */
	private GUISocketClient(){
		
	}
	
	
	/**
	 * Static methods that the client run to connect in Socket and GUI.
	 * The client have to insert his name. Then send the type of connection he is using.
	 * After starts one thread that receive all the message from the server.
	 */
	public static void start() {
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * 
			 */
			public void run(){
				try {
				Socket server;
				server = new Socket(IP, PORT);
				ViewGUIInterface view = new GUIViewClientSocket(server);
				GameGUI gameGUI = new GameGUI();
				((GUIViewClientSocket) view).setGameGUI(gameGUI);
				String nickname  = gameGUI.popUpFromTheServer(((GUIViewClientSocket) view).reciveFromServer());
				((GUIViewClientSocket) view).setSenderString(nickname);
				((GUIViewClientSocket) view).setNickname(nickname);
				view.setSenderString(view.getConnectionType());
				gameGUI.setViewGUIInterface(view);
				Thread t1 = new Thread( new ClientReciverGUI(view));
				t1.start();
				SysoPrinter.println("ConectionDone");
				} catch (UnknownHostException e) {
					ServerLogger.printOnLogger("GUISocketClient", e);
				} catch (IOException e) {
					ServerLogger.printOnLogger("GUISocketClient", e);
				} catch (ClassNotFoundException e) {
					ServerLogger.printOnLogger("GUISocketClient", e);
				}
			}
		});

	}

}
