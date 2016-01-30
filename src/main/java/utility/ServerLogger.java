package utility;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 *
 */
public class ServerLogger implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static FileHandler myHandler;
	private static final Logger LOGGER= Logger.getLogger(ServerLogger.class.getName());

	/**
	 * Default constructor for utility class.
	 * Create a ServerLogger for exception handler.
	 * Has a FileHandler "ServerLog.log" with append true.
	 */
	private  ServerLogger(){
		try {
			ServerLogger.myHandler = new FileHandler("ServerLog.log",true);
		} catch (IOException e) {
			ServerLogger.printOnLogger("ServerLogger", e);
		}
		ServerLogger.LOGGER.addHandler(myHandler);
	}
	
	/**
	 * Print on the logger the string and the exception received
	 * @param string: the message write on the Logger.
	 * @param e: type of exception that happens.
	 */
	public static void printOnLogger(String string, Exception e){
		ServerLogger.LOGGER.info(string+"."+e);
	}

}
