package mainGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Survival {
	private Handler handler;
	private SurvivalHUD hud;
	private Game game;
	private Player player;
	private int levelTimer;
	private int spawnTimer;
	private Random r = new Random();
	private EnemyFactory factory;
	private double difficulty = 0;
	private LinkedList<GameObject> createdEnemies;
	
	public Survival(Handler handler, SurvivalHUD hud, Game game, Player player) {
		this.handler = handler;
		this.hud = hud;
		this.game = game;
		this.player = player;
		handler.object.clear();
		createdEnemies = new LinkedList<GameObject>();
	}
	
	public void initialize() {
		player.setHUD(hud);	
		System.out.println(hud);
		hud.health = 100;
		spawnTimer = 600;
		levelTimer = 580;
		factory = new EnemyFactory(handler);
		logToConsole();
	}

	public void tick() {
		incrementTimer();
		//logToConsole();
	}
	
	private void incrementTimer() {
		levelTimer++;
		if (levelTimer >= spawnTimer) {
			levelTimer = 0;
			createdEnemies.add(factory.generateEnemy());
			if (createdEnemies.size() > (difficulty + 3)) {
				removeEnemy();
			}
			spawnTimer -= 50;
			if (spawnTimer <= 400) {
				factory.increaseDifficulty();
				spawnTimer = 600;
			}
		}
	}
	
	private void logToConsole() {
		System.out.println("Level Timer: " + levelTimer);
		System.out.println("Spawn Timer: " + spawnTimer);
	}
	
	private void removeEnemy() {
		handler.removeObject(createdEnemies.removeFirst());
		hud.setScore((int) (hud.getScore() + (10 + (10 * difficulty))));
	}
	
	private class EnemyFactory {
		private Handler handler;
		private LinkedList<Integer> recentSpawns;
		private ArrayList<ID> enemyIDs;
		private int spawnSafetyRadius = 60;
		private int[] playerPosition = new int[2];
		private int[] spawnPosition = new int[2];
		private String[] side = { "left", "right", "top", "bottom" };
		private int enemyType;
		
		private int spawnDistance = 0;
		
		public EnemyFactory(Handler handler) {
			this.handler = handler;
			recentSpawns = new LinkedList<Integer>();
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
			enemyType = r.nextInt(5);
			while (recentSpawns.contains(enemyType)) {
				enemyType = r.nextInt(5);
			}
			addToRecent(enemyType);
			ID enemyID = enemyIDs.get(enemyType);
			setSpawnPosition();
			
			switch (enemyType) {
				case 0:
					return new EnemyBasic(
							spawnPosition[0], 
							spawnPosition[1], 
							(int) difficulty + 5, 
							(int) difficulty + 5, 
							enemyID, 
							handler);
				case 1:
					return new EnemySweep(
							spawnPosition[0], 
							spawnPosition[1],
							(int) difficulty + 12, 
							(int) difficulty + 1, 
							enemyID, 
							handler);
				case 2: 
					return new EnemySmart(
							spawnPosition[0], 
							spawnPosition[1],
							(int) ((difficulty + 5) * -1), 
							enemyID, 
							handler);
				case 3:
					return new EnemyShooter(
							spawnPosition[0], 
							spawnPosition[1],
							(int) (5 * difficulty) + 60, 
							(int) (5 * difficulty) + 60, 
							(int) ((difficulty + 12) * -1), 
							enemyID, 
							handler);
				case 4:
					return new EnemyBurst(
							(double) spawnPosition[0], 
							(double) spawnPosition[1],
							(int) 35 + difficulty, 
							(int) 35 + difficulty, 
							(int) ((int) 150 + (3 * difficulty)), 
							side[r.nextInt(4)], 
							enemyID, 
							handler);
				default:
					return new EnemyBasic(
							spawnPosition[0], 
							spawnPosition[1], 
							(int) difficulty + 5, 
							(int) difficulty + 5, 
							enemyID, 
							handler);
			}
		}
		
		public GameObject generateEnemy() {
			GameObject spawnedEnemy = selectEnemy();
			handler.addObject(spawnedEnemy);
			return spawnedEnemy;
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
			spawnPosition[1] = r.nextInt(Game.HEIGHT - 80) + 60;
			spawnDistance = (int) Math.sqrt(
					Math.pow((playerPosition[0] - spawnPosition[0]), 2) + 
					Math.pow((playerPosition[1] - spawnPosition[1]), 2));
			//System.out.println(playerPosition[0]);
			//System.out.println(playerPosition[1]);
			//System.out.println(spawnPosition[0]);
			//System.out.println(spawnPosition[1]);
			while (spawnDistance < spawnSafetyRadius) {
				spawnPosition[0] = r.nextInt(Game.WIDTH);
				spawnPosition[1] = r.nextInt(Game.HEIGHT - 80) + 60;
				spawnDistance = (int) Math.sqrt(
						Math.pow((playerPosition[0] - spawnPosition[0]), 2) + 
						Math.pow((playerPosition[1] - spawnPosition[1]), 2));
			}
		}
		
		public void increaseDifficulty() {
			difficulty += 0.5;
		}
		
		private void addToRecent(int spawnedEnemy) {
			recentSpawns.add(spawnedEnemy);
			recentSpawns.removeLast();
		}
	}
}