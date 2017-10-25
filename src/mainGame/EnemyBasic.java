package mainGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * A type of enemy in the game
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class EnemyBasic extends GameObject {

	private Handler handler;

	public EnemyBasic(double x, double y, int velX, int velY, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
	}

	public void tick() {
		this.x += velX;
		this.y += velY;

		//if (this.y <= 0 || this.y >= Game.HEIGHT - 40){
		if (this.y <= 0 || this.y >= 1080 - 40){	
			velY *= -1;
			handler.removeObject(this); // removes the object when it goes off screen
		}
		//if (this.x <= 0 || this.x >= Game.WIDTH - 16){
		if (this.x <= 0 || this.x >= 1920 - 16){
			velX *= -1;
			handler.removeObject(this); // removes the object when it goes off screen
		}
		handler.addObject(new Trail(x, y, ID.Trail, Color.red, 16, 16, 0.025, this.handler));
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) x, (int) y, (int) Game.scaleX(16), (int) Game.scaleY(16));

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) this.x, (int) this.y, 16, 16);
	}

}
