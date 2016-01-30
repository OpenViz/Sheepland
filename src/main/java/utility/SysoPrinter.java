package utility;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 */
public class SysoPrinter {
	
	/**
	 * Default constructor for utility class.
	 */
	private SysoPrinter(){
		
	}
	
	/**
	 * Print the string on console using System.out.println()
	 * @param string
	 */
	public static void println(String string){
		System.out.println(string);
	}
	
	/**
	 * Print the string using the method System.out.print()
	 * @param string
	 */
	public static void print(String string){
		System.out.print(string);
	}

}
