package mainGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import sun.audio.*;
import java.awt.event.*;
import java.io.*;

public class Sound {

	// Instance Variables
	private static Clip clip;
	private static Clip clip2;

	//Sound Method for the game sound
	public static void playSound() {
		// Try catch audio
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sound.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}

		catch (Exception e) {
			System.out.println("Problem starting music");
			e.printStackTrace();
		}
	}

	// Stop method for game sound
	public static void stopSound() {
	//	clip.drain();
		clip.stop();
		clip.close();
	}

	// Method for the MenuSound
	public static void playSoundMenu() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("MenuTheme.wav"));
			clip2 = AudioSystem.getClip();
			clip2.open(audioInputStream);
			clip2.start();
			clip2.loop(Clip.LOOP_CONTINUOUSLY);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Stop method for menu sound
	public static void stopSoundMenu() {
		clip2.stop();
		clip2.close();
		System.out.println("clip closed");
	}
	

}
