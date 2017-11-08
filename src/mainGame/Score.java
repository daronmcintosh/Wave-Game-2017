package mainGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Score{
	private int [] scores;
	private File saveFile;
	
	public Score(){
		scores = new int[5];
		saveFile = new File("./scores.txt");
	}
	
	public int[] getScores(){
		return scores;
		
	}
	
	public void loadScores(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(saveFile));
			
			String line = reader.readLine();
			String[] readScores = line.split(",");
		
			for(int i = 0 ; i < scores.length; i++){
				scores[i] = Integer.parseInt(readScores[i]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("No save file found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addScore(int score){
		
		for(int i = 0 ; i<scores.length; i++){
			if(score > scores[i]){
				 for(int j = scores.length -1; j > i; j--){
					 scores[j] = scores[j-1];
				 }
				 scores[i]=score;
				 
				 break;
			}
		}
		
		saveScores();
	}
		//check to see if score is in top 5
	
	
	private void saveScores(){
		try {
			FileWriter fw = new FileWriter(saveFile);
			
			String [] scoresStringArr = new String[5];
		
			for(int i = 0 ; i < scores.length; i++){
				scoresStringArr[i] = Integer.toString(scores[i]);
			}
			
			String scoreString = String.join(",", scoresStringArr);
	
			fw.write(scoreString);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
