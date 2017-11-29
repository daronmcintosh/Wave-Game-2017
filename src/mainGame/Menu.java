package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import mainGame.Game.STATE;

/**
 * The main menu
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Menu {

	private Game game;
	private Handler handler;
	private HUD hud;
	private Image img;
	private int timer;
	private Random r;
	private ArrayList<Color> colorPick = new ArrayList<Color>();
	private int colorIndex;
	private Spawn1to10 spawner;

	public Menu(Game game, Handler handler, HUD hud, Spawn1to10 spawner) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.spawner = spawner;
		timer = 10;
		r = new Random();
		addColors();
		//Sound.playSoundMenu();

		img = null;
		try {
			URL imageURL = Game.class.getResource("images/background.png");
			img = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}

		handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 500, 50, 50, 0, -2,
			colorPick.get(r.nextInt(6)), ID.Firework, this.handler));
	}

	public void addColors() {
		colorPick.add(Color.blue);
		colorPick.add(Color.white);
		colorPick.add(Color.green);
		colorPick.add(Color.red);
		colorPick.add(Color.cyan);
		colorPick.add(Color.magenta);
		colorPick.add(Color.yellow);
	}

	public void tick() {
		timer--;
		if (timer <= 0) {
			handler.object.clear();
			colorIndex = r.nextInt(6);
			handler.addObject(new MenuFireworks((r.nextInt(Game.WIDTH) - 25), 1080, 100, 100, 0, -4,
				colorPick.get(colorIndex), ID.Firework, this.handler));
			timer = 300;
		}
		handler.tick();
	}

	public void render(Graphics g) {
		if (game.gameState == STATE.Menu) {
			g.drawImage(img, 0, 0, Game.WIDTH, Game.HEIGHT, null);
			handler.render(g);
			Font font = new Font("Amoebic", 1, (int) (100 * (Game.HEIGHT / 1080f)));
			Font font2 = new Font("Amoebic", 1, (int) (60 * (Game.HEIGHT / 1080f)));

			/*
			 * Note to self:
			 * 
			 * Abstract each of these calls out to a window.
			 * 
			 * Each of these buttons should be drawn at a relative location.
			 * Since each was placed precisely at 1920x1080, every position
			 * should instead be multiplied by found width/height divided by
			 * 1920/1080 depending.
			 * 
			 * Really it should be a layout manager, but baby steps.

			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Game Modes", 1140, 100);
						
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Loehle's Sandbox", 75, 100);
			g.setColor(Color.white);
			g.drawRect(990, 135, 400, 400);
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Waves", 1110, 215);

			g.setColor(Color.white);
			g.drawRect(1440, 135, 400, 400);
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Bosses", 1550, 215);

			g.setColor(Color.white);
			g.drawRect(990, 585, 400, 400);
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Attack", 1095, 665);

			g.setColor(Color.white);
			g.drawRect(1440, 585, 400, 400);
			g.setFont(font2);
			g.setColor(Color.white);
			g.drawString("Hunger", 1550, 665);
			
				//Waves
			drawMenuItem(g, font2, Color.white, new int[] {805, 545, 300, 55}, 
				"", new int[] {1110, 215});
			//Survival
			drawMenuItem(g, font2, Color.white, new int[] {805, 610, 300, 55}, 
				"", new int[] {1560, 215});
			//Help
			drawMenuItem(g, font, Color.white, new int[] {805, 740, 300, 55}, 
				"", new int[] {400, 280});
			//Credits
			drawMenuItem(g, font, Color.white, new int[] {805, 805, 300, 55}, 
				"", new int[] {340, 600});
			//Quit
			drawMenuItem(g, font, Color.white, new int[] {805, 870, 300, 55}, 
				"", new int[] {400, 900});
			//Leaderboard
			drawMenuItem(g, font, Color.white, new int[] {805, 675, 300, 55}, 
				"", new int[] {1075, 900});
			
			g.setColor(Color.white);
			g.drawRect(80, 135, 850, 250);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Help", 400, 280);

			g.setColor(Color.white);
			g.drawRect(80, 435, 850, 250);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Credits", 340, 600);

			g.setColor(Color.white);
			g.drawRect(80, 735, 850, 250);
			g.setFont(font);
			g.setColor(Color.white);
			g.drawString("Quit", 400, 900);

			*/
//		} else if (game.gameState == STATE.Help) {// if the user clicks on "help"
//		Font font = new Font("impact", 1, 50);
//		Font font2 = new Font("impact", 1, 30);
//
//		g.setFont(font);
//		g.setColor(Color.white);
//		g.drawString("Help", 900, 70);

//		g.setFont(font2);
//		g.drawString("Waves: Simply use WASD to avoid enemies. Once you avoid" + " \n"
//			+ "them long enough, a new batch will spawn in! Defeat each boss to win!", 40, 200);
//
//		g.setFont(font2);
//		g.setColor(Color.white);
//		g.drawRect(850, 300, 200, 64);
//		g.drawString("Back", 920, 340);
		}
	}

	private void drawMenuItem(Graphics g, Font font, Color color, 
		int[] dimensions, String text, int[] textPos) {
		g.setFont(font);
		g.setColor(color);
		g.drawRect(
			(int) (dimensions[0] * (Game.WIDTH / 1920f)), 
			(int) (dimensions[1] * (Game.HEIGHT / 1080f)), 
			(int) (dimensions[2] * (Game.WIDTH / 1920f)), 
			(int) (dimensions[3] * (Game.HEIGHT / 1080f)));
		drawMenuString(g, font, color, text, textPos);
	}

	private void drawMenuString(Graphics g, Font font, Color color,
		String text, int[] textPos) {
		g.setFont(font);
		g.setColor(color);
		g.drawString(
			text, 
			(int) (textPos[0] * (Game.WIDTH / 1920f)), 
			(int) (textPos[1] * (Game.HEIGHT / 1080f)));
	}

			//else if (game.gameState == STATE.Help) {// if the user clicks on "help"
	//			Font font = new Font("impact", 1, 50);
	//			Font font2 = new Font("impact", 1, 30);
	//
	//			g.setFont(font);
	//			g.setColor(Color.white);
	//			g.drawString("Help", 900, 70);
	//
	//			g.setFont(font2);
	////			
	////			String stringToPrint = "How To Play: To play, Waves, you must first understand that you are playing" +" \n"
	////					 + " as the small white box in the center of the screen, with the purpose to try to " + " \n"
	////					 + " stay alive as long as possible while dodging enemies. To start avoiding enemies," +  " \n"
	////					 + " you simply use the arrow keys to navigate the page." + " \n"
	//// 					 + " To pause the game, press key "P." To resume the game press key "P."";
	////			
	////			g.drawString(stringToPrint, 40, 200);
	//			
	//			g.setFont(font2);
	//			g.setColor(Color.white);
	//			g.drawRect(850, 300, 200, 64);
	//			g.drawString("Back", 920, 340);
	//		}
	//
}