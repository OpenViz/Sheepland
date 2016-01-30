package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import utility.ServerLogger;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class GameMessageGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int WINDOW_WIDTH = 350;
	private static final int WINDOW_HEIGHT = 200;
	private JTextArea message;
	private Font font;
	private MusicButtonGUI musicButton;
	
	/**
	 * Creates a JPanel that contains a TextArea and a {@link MusicButtonGUI}.
	 * The TextArea is used to print strings from the server.
	 * The MusicButtonGUI is used to stop or play the music.
	 */
	public GameMessageGUI() {
		super.setBackground(new Color(35, 161, 246));
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setLayout(new FlowLayout());
		
		this.message = new JTextArea();
		message.setSize(new Dimension(WINDOW_WIDTH, 145));
		message.setPreferredSize(new Dimension(WINDOW_WIDTH, 145));
		message.setBackground(new Color(35, 161, 246));
		message.setLineWrap(true);
		message.setWrapStyleWord(true);
		message.setEditable(false);
		try {
			File file = new File("./res/angrybirds-regular.ttf");
			font =  Font.createFont(Font.TRUETYPE_FONT, file);
			font =  font.deriveFont(Font.PLAIN,20);
		} catch (FontFormatException e) {
			ServerLogger.printOnLogger("GameMessageGUI", e);
		} catch (IOException e) {
			ServerLogger.printOnLogger("GameMessageGUI", e);
		}
		message.setFont(font);
		this.add(message);
		
		this.musicButton = new MusicButtonGUI();
		this.add(musicButton);
		
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Prints the specific string(parameter) on the TextArea.
	 * @param string: that has to be printed.
	 */
	public void printMessage(String string){
		message.setText(string);
		this.revalidate();
		this.repaint();
	}
}
