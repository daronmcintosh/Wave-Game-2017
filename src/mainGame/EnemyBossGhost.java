package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Mark Russo 
 * New enemy ghost to be implemented in level 5
 * November 28, 2017
 */

public class EnemyBossGhost extends GameObject {

	private Handler handler;
	private int timer;
	private int size;
	private String side;
	private Random r = new Random();
	private Image img;
	private int speed;
	private GameObject player;
	private int sizeX;
	private int sizeY;
	private double bulletVelX;
	private double bulletVelY;
	private int bulletSpeed;


		public EnemyBossGhost(double x, double y, int speed, ID id, Handler handler) {
			super(x, y, id);
			this.handler = handler;
			this.speed = speed;
			this.velX = 0;
			this.velY = 0;
			this.sizeX = sizeX;
			this.sizeY = sizeY;
			this.timer = 60;
			this.bulletSpeed = bulletSpeed;
			
			
			
			img = getImage("images/EnemyGhost.png");

			for (int i = 0; i < handler.object.size(); i++) {
				if (handler.object.get(i).getId() == ID.Player)
					player = handler.object.get(i);
			}

		}

		public void tick() {
			this.x += velX;
			this.y += velY;
			////////////////////////////// pythagorean theorem
			////////////////////////////// below//////////////////////////////////////////////////
			double diffX = this.x - player.getX() - 16;
			double diffY = this.y - player.getY() - 16;
			double distance = Math.sqrt(((this.x - player.getX()) * (this.x - player.getX()))
					+ ((this.y - player.getY()) * (this.y - player.getY())));
			////////////////////////////// pythagorean theorem
			////////////////////////////// above//////////////////////////////////////////////////
			velX = ((this.speed / distance) * diffX); // numerator affects speed of enemy
			velY = ((this.speed / distance) * diffY);// numerator affects speed of enemy

			timer--;
			if (timer <= 0) {
				shoot();
				timer = 10;
			
			}

		}
		
		public void shoot() {
			double diffX = this.x - player.getX() - Game.scaleX(16);
			double diffY = this.y - player.getY() - Game.scaleX(16);
			double distance = Math.sqrt(((this.x - player.getX()) * (this.x - player.getX()))
					+ ((this.y - player.getY()) * (this.y - player.getY())));
			////////////////////////////// pythagorean theorem
			////////////////////////////// above//////////////////////////////////////////////////
			bulletVelX = ((this.bulletSpeed / distance) * diffX); // numerator affects speed of enemy
			bulletVelY = ((this.bulletSpeed / distance) * diffY);// numerator affects speed of enemy

			handler.addObject(
					new EnemyBossGhostTrail(this.x, this.y, bulletVelX, bulletVelY, ID.EnemyShooterBullet, this.handler));
		}
		
		

		public void render(Graphics g) {
			g.drawImage(img, (int) this.x, (int) this.y, 96, 96, null);


		}

		@Override
		public Rectangle getBounds() {
			return new Rectangle((int) this.x, (int) this.y, (int) Game.scaleX(16), (int) Game.scaleY(16));
		}
		
		
		public Image getImage(String path) {
			Image image = null;
			try {
				URL imageURL = Game.class.getResource(path);
				image = Toolkit.getDefaultToolkit().getImage(imageURL);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			return image;
		}
		

	}
