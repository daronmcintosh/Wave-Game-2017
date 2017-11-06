package mainGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Survival {
	private Handler handler;
	private HUD hud;
	private Game game;
	private Player player;
	private int levelTimer;
	private int spawnTimer;
	private Random r = new Random();
	private EnemyFactory factory;
	
	
	public Survival(Handler handler, HUD hud, Game game, Player player) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.player = player;
		handler.object.clear();
		
		spawnTimer = 600;
		levelTimer = 520;
		factory = new EnemyFactory(handler);
	}

	public void tick() {
		incrementTimer();
		logToConsole();
	}
	
	private void incrementTimer() {
		levelTimer++;
		if (levelTimer == spawnTimer) {
			levelTimer = 0;
			factory.generateEnemy();
		}
	}
	
	private void logToConsole() {
		System.out.println("Level Timer: " + levelTimer);
		System.out.println("Spawn Timer: " + spawnTimer);
	}
	
	private class EnemyFactory {
		private Handler handler;
		private LinkedList<GameObject> recentSpawns;
		private ArrayList<ID> enemyIDs;
		private int spawnSafetyRadius = 60;
		private int[] playerPosition = new int[2];
		private int[] spawnPosition = new int[2];
		private int spawnDistance = 0;
		
		public EnemyFactory(Handler handler) {
			this.handler = handler;
			recentSpawns = new LinkedList<GameObject>();
			enemyIDs = new ArrayList<ID>();
			populateIDList();
		}
		
		public GameObject selectEnemy() {
			/*
			 * 0 = EnemyBasic
			 * 1 = EnemySweep
			 * 2 = EnemySmart
			 * 3 = EnemyShooter
			 * 4 = EnemyBurst
			 * 5 = EnemyFast <--Is this implemented yet?
			 */
			int enemyType = r.nextInt(5);
			ID enemyID = enemyIDs.get(enemyType);
			setSpawnPosition();
			
			return new EnemyBasic(
					spawnPosition[0], 
					spawnPosition[1], 
					9, 
					9, 
					ID.EnemyBasic, 
					handler);
		}
		
		public void generateEnemy() {
			handler.addObject(selectEnemy());
		}
		
		private void populateIDList() {
			enemyIDs.add(ID.EnemyBasic);
			enemyIDs.add(ID.EnemySmart);
			enemyIDs.add(ID.EnemySweep);
			enemyIDs.add(ID.EnemyShooter);
			enemyIDs.add(ID.EnemyBurst);
			enemyIDs.add(ID.EnemyFast);
		}
		
		private void setSpawnPosition() {
			playerPosition[0] = (int) player.getX(); 
			playerPosition[1] = (int) player.getY();
			spawnPosition[0] = r.nextInt(Game.WIDTH);
			spawnPosition[1] = r.nextInt(Game.HEIGHT);
			spawnDistance = (int) Math.sqrt(
					Math.pow((playerPosition[0] - spawnPosition[0]), 2) + 
					Math.pow((playerPosition[1] - spawnPosition[1]), 2));
			System.out.println(playerPosition[0]);
			System.out.println(playerPosition[1]);
			System.out.println(spawnPosition[0]);
			System.out.println(spawnPosition[1]);
			while (spawnDistance < spawnSafetyRadius) {
				
				spawnPosition[0] = r.nextInt(Game.WIDTH);
				spawnPosition[1] = r.nextInt(Game.HEIGHT);
				spawnDistance = (int) Math.sqrt(
						Math.pow((playerPosition[0] - spawnPosition[0]), 2) + 
						Math.pow((playerPosition[1] - spawnPosition[1]), 2));
			}
		}
	}
}
