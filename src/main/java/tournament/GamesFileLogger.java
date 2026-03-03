package tournament;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GamesFileLogger implements Observer{
	String filename;
	
	public GamesFileLogger(String filename) {
		this.filename = filename;
	}
	
	public void update(Subject subject) {
		FileWriter file;
		try {
			File myFile = new File(this.filename); // Create File object
		    myFile.createNewFile();  
		} catch (IOException e) {
			System.out.println("Problem Creating File");
		}
		try {
			Game game = (Game)subject;
			file = new FileWriter(filename, true);
			State state = (game.getCurrState());
			file.write("Game\n");
			file.write(state.getP1Name() + " played " + state.getP2Name() + " with a final score of " + state.getP1Score() + "-" + state.getP2Score() + ".\n");
			file.close();
		} catch (IOException e) {
			System.out.println("File reading/writing went wrong for " + filename);
		}
	}
}