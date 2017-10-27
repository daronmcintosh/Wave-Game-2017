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

public class HUD {

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

		score++;

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

		g.setColor(Color.GRAY);
		g.fillRect(
				(int) (15 * (Game.WIDTH / 1920f)), 
				(int) (15 * (Game.HEIGHT / 1080f)),
				(int)	(healthBarWidth * (Game.WIDTH / 1920f)), 
				(int) (64 * (Game.HEIGHT / 1800f)));
		g.setColor(new Color(75, (int) greenValue, 0));
		g.fillRect(
				(int) (15 * (Game.WIDTH / 1920f)), 
				(int) (15 * (Game.HEIGHT / 1080f)),
				(int)	((health * 4) * (Game.WIDTH / 1920f)), 
				(int) (64 * (Game.HEIGHT / 1080f)));
		g.setColor(scoreColor);
		g.drawRect(
				(int) (15 * (Game.WIDTH / 1920f)), 
				(int) (15 * (Game.HEIGHT / 1080f)),
				(int)	(healthBarWidth * (Game.WIDTH / 1920f)), 
				(int) (64 * (Game.HEIGHT / 1080f)));

		g.setFont(font);

		g.drawString(
				"Score: " + score, 
				(int) (15 * (Game.WIDTH / 1920f)), 
				(int) (115 * (Game.HEIGHT / 1080f)));
		g.drawString(
				"Level: " + level, 
				(int) (15 * (Game.WIDTH / 1920f)), 
				(int) (150 * (Game.HEIGHT / 1080f)));
		g.drawString(
				"Extra Lives: " + extraLives, 
				(int) (15 * (Game.WIDTH / 1920f)), 
				(int) (185 * (Game.HEIGHT / 1080f)));

		if (ability.equals("freezeTime")) {
			g.drawString(
					"Time Freezes: " + abilityUses, 
					Game.WIDTH - ( (int) (300 * (Game.WIDTH / 1920f))), 
					(int) (185 * (Game.HEIGHT / 1080f)));
		} else if (ability.equals("clearScreen")) {
			g.drawString(
					"Screen Clears: " + abilityUses, 
					Game.WIDTH - ( (int) (300 * (Game.WIDTH / 1920f))), 
					(int) (185 * (Game.HEIGHT / 1920f)));
		} else if (ability.equals("levelSkip")) {
			g.drawString(
					"Level Skips: " + abilityUses, 
					Game.WIDTH - ( (int) (300 * (Game.WIDTH / 1920f))), 
					(int) (185 * (Game.HEIGHT / 1920f)));
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
