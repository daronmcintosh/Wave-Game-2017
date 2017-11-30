package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import mainGame.HUD.HUDElement;
import mainGame.HUD.HUDRectangle;
import mainGame.HUD.HUDText;

/**
 * The main Heads Up Display of the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class SurvivalHUD extends HUD{

	public double health = 100;
	private double healthMax = 100;

	private double greenValue = 255;

	private int score = 00000000000;
	private int level = 0;
	//private double greenValue = 255;

	private boolean regen = false;
	private int timer = 60;
	private int healthBarWidth = 400;
	private int healthBarModifier = 2;
	private boolean doubleHealth = false;
	private String ability = "";
	private int abilityUses;
	public static String name = "Survival HUD";
	
	//private Font font;
	private int difficulty = 1;
	
	//private Color scoreColor = Color.white;
	
	//private HUDRectangle HUDBackground;
	//private HUDRectangle healthBarBackground;
	//private HUDRectangle healthBar;
	//private HUDText scoreText;
	//private HUDText levelText;
	//private ArrayList<HUDElement> HUDElementList;

	private Color scoreColor = Color.white;

	private int extraLives = 0;
	
	public SurvivalHUD() {
		
	}

	public SurvivalHUD() {
		font = new Font("Amoebic", 1, 30);
		HUDBackground = new HUDRectangle(0, 0, 
				Color.RED, 
				1920, 90);
		healthBarBackground = new HUDRectangle(10, 10, 
				Color.BLACK, 
				410, 44);
		healthBar = new HUDRectangle(15, 15, 
				new Color(75, (int) greenValue, 0), 
				400, 32);
		scoreText = new HUDText(700, 64, scoreColor, font);
		levelText = new HUDText(1100, 64, scoreColor, font);
		HUDElementList = new ArrayList<HUDElement>();
		HUDElementList.add(HUDBackground);
		HUDElementList.add(healthBarBackground);
		HUDElementList.add(healthBar);
		HUDElementList.add(scoreText);
		HUDElementList.add(levelText);
	}

	@Override
	public void tick() {
		health = Game.clamp(health, 0, health);
		greenValue = Game.clamp(greenValue, 0, 255);
		greenValue = health * healthBarModifier;
		//health--;

		if (regen) {// regenerates health if that ability has been unlocked
			timer--;
			if (timer == 0 && health < 100) {
				health += 1;
				timer = 60;
			}
		}
		greenValue = Game.clamp(greenValue, 0, 255);	
	}

	@Override
	public void render(Graphics g) {
		Font font = new Font("Amoebic", 1, 30);
		//Set a nice background color for the UI
		g.setColor(Color.RED);
		g.fillRect(0,	0,	Game.WIDTH,	(int) (Game.scaleY(90)));

		greenValue = Game.clamp(greenValue, 0, 255);
		g.setColor(Color.BLACK);
		g.fillRect(
				(int) (Game.scaleX(15)), 
				(int) (Game.scaleY(15)),
				(int) (Game.scaleX(healthBarWidth)), 
				(int) (Game.scaleY(32)));
		g.setColor(new Color(75, (int) greenValue, 0));
		g.fillRect(
				(int) (Game.scaleX(15)), 
				(int) (Game.scaleY(15)),
				(int) (Game.scaleX((health * 4))), 
				(int) (Game.scaleY(32)));
		g.setColor(scoreColor);
		g.drawRect(
				(int) (Game.scaleX(15)), 
				(int) (Game.scaleY(15)),
				(int) (Game.scaleX(healthBarWidth)), 
				(int) (Game.scaleY(32)));

		g.setFont(font);

		g.drawString(
				"Score: " + score, 
				(int) (Game.scaleX(500)), 
				(int) (Game.scaleY(64)));
/*		
		g.drawString(
				"Level: " + level, 
				(int) (Game.scaleX(900)), 
				(int) (Game.scaleY(64)));
		g.drawString(
				"Extra Lives: " + extraLives,
				(int) (Game.scaleX(1300)), 
				(int) (Game.scaleY(64)));
*/
		if (ability.equals("freezeTime")) {
			g.drawString(
					"Time Freezes: " + abilityUses,
					(int) (Game.scaleX(1600)), 
					(int) (Game.scaleY(64)));
		} else if (ability.equals("clearScreen")) {
			g.drawString(
					"Screen Clears: " + abilityUses,
					(int) (Game.scaleX(1600)), 
					(int) (Game.scaleY(64)));
		} else if (ability.equals("levelSkip")) {
			g.drawString(
					"Level Skips: " + abilityUses,
					(int) (Game.scaleX(1600)), 
					(int) (Game.scaleY(64)));
		for (HUDElement element : HUDElementList) {
			element.render(g);
		}
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}

	public int getAbilityUses() {
		return this.abilityUses;
	}

	public void setAbilityUses(int abilityUses) {
		this.abilityUses = abilityUses;
	}

	public void updateScoreColor(Color color) {
		this.scoreColor = color;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setRegen() {
		regen = true;
	}

	public void resetRegen() {
		regen = false;
	}

	public void setExtraLives(int lives) {
		this.extraLives = lives;
	}

	public int getExtraLives() {
		return this.extraLives;
	
	@Override
	public void tickScore() {
		//score++;
		updateScoreText(score);
		updateLevelText(difficulty);
	}

	public void healthIncrease() {
		doubleHealth = true;
		healthMax = 200;
		this.health = healthMax;
		healthBarModifier = 1;
		healthBarWidth = 800;
	public int getDifficulty() {
		return difficulty;
	}

	public void resetHealth() {
		doubleHealth = false;
		healthMax = 100;
		this.health = healthMax;
		healthBarModifier = 2;
		healthBarWidth = 400;
	}

	public void restoreHealth() {
		this.health = healthMax;
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
}
