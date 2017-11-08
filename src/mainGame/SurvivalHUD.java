package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * The main Heads Up Display of the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class SurvivalHUD {

	public double health = 100;
	private double healthMax = 100;

	private double greenValue = 255;

	private int score = 00000000000;
	private int level = 0;

	private boolean regen = false;
	private int timer = 60;
	private int healthBarWidth = 400;
	private int healthBarModifier = 2;
	private boolean doubleHealth = false;
	private String ability = "";
	private int abilityUses;

	private Color scoreColor = Color.white;

	private int extraLives = 0;

	public void tick() {
		health = Game.clamp(health, 0, health);

		greenValue = Game.clamp(greenValue, 0, 255);

		greenValue = health * healthBarModifier;


		if (regen) {// regenerates health if that ability has been unlocked
			timer--;
			if (timer == 0 && health < 100) {
				health += 1;
				timer = 60;
			}
		}
	}

	public void render(Graphics g) {
		Font font = new Font("Amoebic", 1, 30);
		//Set a nice background color for the UI

		g.setColor(Color.RED);
		g.fillRect(0,	0,	Game.WIDTH,	(int) (Game.scaleY(90)));
		
		g.setColor(Color.BLACK);
		g.fillRect(
				(int) (Game.scaleX(15)), 
				(int) (Game.scaleY(15)),
				(int) (Game.scaleX(healthBarWidth)), 
				(int) (Game.scaleY(64)));
		g.setColor(new Color(75, (int) greenValue, 0));
		g.fillRect(
				(int) (Game.scaleX(15)), 
				(int) (Game.scaleY(15)),
				(int) (Game.scaleX((health * 4))), 
				(int) (Game.scaleY(64)));
		g.setColor(scoreColor);
		g.drawRect(
				(int) (Game.scaleX(15)), 
				(int) (Game.scaleY(15)),
				(int) (Game.scaleX(healthBarWidth)), 
				(int) (Game.scaleY(64)));

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
	}

	public void healthIncrease() {
		doubleHealth = true;
		healthMax = 200;
		this.health = healthMax;
		healthBarModifier = 1;
		healthBarWidth = 800;
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
	}
}
