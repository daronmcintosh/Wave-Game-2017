package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import mainGame.Game.STATE;

/**
 * The main player in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Player extends GameObject {

	Random r = new Random();
	Handler handler;
	private HUD hud;
	private Game game;
	private int damage;
	private int playerWidth, playerHeight;
	public static int playerSpeed = 10;

	public Player(double x, double y, ID id, Handler handler, HUD hud, Game game) {
		super(x, y, id);
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.damage = 2;
		playerWidth = 32;
		playerHeight = 32;
	}

	@Override
	public void tick() {
		this.x += velX;
		this.y += velY;
		x = Game.clamp(x, 0, Game.WIDTH - 38);
		y = Game.clamp(y, Game.scaleY(90), Game.HEIGHT - 60);

		// add the trail that follows it
		handler.addObject(new Trail(x, y, ID.Trail, Color.white, playerWidth, playerHeight, 0.05, this.handler));
		//System.out.println(hud);
		//System.out.println(game.survivalHud);
		//hud.health++;
		//game.survivalHud.health++;
		//System.out.println(game.gameState);
		collision();
		checkIfDead();

	}

	public void checkIfDead() {
		if (hud.health <= 0) {// player is dead, game over!

			if (hud.getExtraLives() == 0) {
				game.gameState = STATE.GameOver;
				Sound.stopSoundMenu();
				Sound.stopSound();
				Sound.playSoundOver();
			}

			else if (hud.getExtraLives() > 0) {// has an extra life, game continues
				hud.setExtraLives(hud.getExtraLives() - 1);
				hud.setHealth(100);
			}
		}
		if(hud.health == 30){
			//Add health is low sound
			Sound.playLowHealth();
		}
	}

	/**
	 * Checks for collisions with all of the enemies, and handles it accordingly
	 */
	public void collision() {

		hud.updateScoreColor(Color.white);
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.EnemyBasic 
					|| tempObject.getId() == ID.EnemyFast
					|| tempObject.getId() == ID.EnemySmart 
					|| tempObject.getId() == ID.EnemyBossBullet
					|| tempObject.getId() == ID.EnemySweep 
					|| tempObject.getId() == ID.EnemyShooterBullet
					|| tempObject.getId() == ID.EnemyBurst 
					|| tempObject.getId() == ID.EnemyShooter
					|| tempObject.getId() == ID.BossEye
					|| tempObject.getId() == ID.EnemyRaindrop
					|| tempObject.getId() == ID.EnemyShotgun
					|| tempObject.getId() == ID.EnemyShotgunBullet
					|| tempObject.getId() == ID.EnemyBossLazer) {// tempObject is an enemy

				// collision code
				if (getBounds().intersects(tempObject.getBounds())) { // player hit an enemy
					switch (game.gameState) {
						case Game:
							hud.health -= damage;
							break;
						case Survival:
							game.survivalHud.health -= damage;
							break;
						default:
							break;
					}
					//hud.health -= damage;
					//System.out.println(hud.health);
					//System.out.println(x);
					hud.updateScoreColor(Color.red);
				}

			}
			if (tempObject.getId() == ID.EnemyBoss) {
				// Allows player time to get out of upper area where they will get hurt once the
				// boss starts moving
				if (this.y <= 138 && tempObject.isMoving) {
					hud.health -= 2;
					hud.updateScoreColor(Color.red);
				}
			}

		}
	}

	@Override
	public void render(Graphics g) {

		g.setColor(Color.blue);
		g.fillRect((int) x, (int) y, (int) Game.scaleX(playerWidth), (int) Game.scaleY(playerHeight));

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, (int) Game.scaleX(playerWidth), (int) Game.scaleY(playerHeight));
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setPlayerSize(int size) {
		this.playerWidth = size;
		this.playerHeight = size;
	}
	
	public void setHUD(HUD newHud) {
		hud = newHud;
	}

}
