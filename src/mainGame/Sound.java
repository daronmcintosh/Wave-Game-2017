package mainGame;

/**
 * @author Mark Russo November 4, 2017
 * This class creates sound for the game
 * Implemented in the Game class
 */

import javax.sound.sampled.AudioInputStream;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import mainGame.Game.STATE;
import sun.audio.*;

import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;
import mainGame.Game.STATE;

public class Sound{
	// Instance Variables
	private static Clip clip;
	private static Clip clip2;
	private Game game;
	private Handler handler;
	public static STATE gameState;
	
	
	public Sound(Game game, Handler handler) {
		this.game = game;
		this.handler = handler;
		GameState.getState();
	}

	//Sound Method for the game sound
	//public void playSound() {
		
		
		// Try catch audio
		public void getSound() {
			if (game.gameState == STATE.Menu) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sound.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			System.out.println("Music has Started");
		}

		catch (Exception e) {
			System.out.println("Problem starting music");
			e.printStackTrace();
		}
		
	}
		else if(game.gameState == STATE.Game){
			clip.stop();
			clip.close();
			System.out.println("Music has stopped");
		}
			getSound();
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
