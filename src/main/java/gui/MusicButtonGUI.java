package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import utility.ServerLogger;

/**
 * @author Vittorio Selo
 * @author Manuel Tanzi
 * 
 */
public class MusicButtonGUI extends JButton implements ActionListener {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WINDOW_WIDTH = 50;
	private static final int WINDOW_HEIGHT = 45;
	private boolean playing;
	private File audioFile;
	private ImageIcon imagePause;
	private ImageIcon imagePlay;
	private AudioInputStream audio;
	private Clip clip;
	
	
	/**
	 * Creates a JButton that is used to play one specific music.
	 * The {@link MusicButtonGUI} can be used to play or stop the music. 
	 */
	public MusicButtonGUI(){
		this.playing = true;
		super.setBackground(new Color(35, 161, 246));
		super.setForeground(new Color(35, 161, 246));
		this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		this.setOpaque(true);
		
		//Load image
		this.imagePause = new ImageIcon("./res/Pause.png");
		this.imagePlay = new ImageIcon("./res/Play.png");
		
		this.audioFile = new File("./res/23_Tavern_on_the_End_of_World.wav");
		try {
			this.audio = AudioSystem.getAudioInputStream(audioFile);
			this.clip = AudioSystem.getClip();
			this.clip.open(this.audio);
		} catch (UnsupportedAudioFileException e) {
			ServerLogger.printOnLogger("MusicButton", e);
		} catch (IOException e) {
			ServerLogger.printOnLogger("MusicButton", e);
		} catch (LineUnavailableException e) {
			ServerLogger.printOnLogger("MusicButton", e);
		}
		
		//Set Image
		this.setMusicImageButton();
		//Set play or stop
		this.setPlayOrStop();
		
		this.setVisible(true);
		this.addActionListener(this);
	}
	
	
	/**
	 * @also
	 * 		If the music is playing it will stop.
	 * 		If there is no music, it will starts.
	 */
	public void actionPerformed(ActionEvent e) {
		this.playing = !playing;
		this.setMusicImageButton();
		this.setPlayOrStop();
		
	}
	
	/**
	 * Helper method to the Builder.
	 * Sets the image of the button based if the music is playing or not.
	 */
	private void setMusicImageButton(){
		if(playing){
			this.setIcon(imagePause);
		}else if(!playing){
			this.setIcon(imagePlay);
		}
		this.revalidate();
		this.repaint();
	}
	
	private void setPlayOrStop(){
		if(this.playing){
			this.clip.loop(Clip.LOOP_CONTINUOUSLY);
		}else if(!this.playing){
			this.clip.stop();
		}
	}
	
	

}
