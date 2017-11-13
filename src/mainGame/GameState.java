package mainGame;

import mainGame.Game.STATE;

public class GameState {
	
	
	public Game game;
	
	
	private static STATE gameState = STATE.Game;

	public static STATE getState()
	{
	    return gameState;
	}

	public static void setState(STATE state)
	{
	    gameState = state;
	}
	
	

}
