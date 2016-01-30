package client;


import java.io.IOException;
import java.net.Socket;

import utility.SysoPrinter;
import view.CLIViewClientSocket;
import view.ViewInterface;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class CLISocketClient {
	private static final int PORT = 4000;
	private static final String IP = "127.0.0.1";
	
	/**
	 * Private constructor for static methods.
	 */
	private CLISocketClient() {
		
	}
	
	/**
	 * Static methods that the client run to connect in Socket and CLI.
	 * The client have to insert his name. Then send the type of connection he is using.
	 * After this it start 2 threads: 1 for receive and 1 for send.
	 * @throws IOException:connection issue.
	 * @throws ClassNotFoundException: connection issue.
	 */
	public static void start() throws IOException, ClassNotFoundException {
		Socket server = new Socket(IP, PORT);
		ViewInterface view = new CLIViewClientSocket(server);
		((CLIViewClientSocket) view).reciveFromServer();
		String nickname =  view.getString();
		((CLIViewClientSocket) view).setNickname(nickname);
		((CLIViewClientSocket) view).sendConnectionType();
		SysoPrinter.println("ConnectionDone");
		Thread t1 = new Thread(new ClientReciverCLI(view));
		t1.start();
		Thread t2 = new Thread(new ClientSenderCLI(view));
		t2.start();
	}

}
