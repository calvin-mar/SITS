package tournament;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ActionsFileLogger implements Observer{
	String filename;
	
	public ActionsFileLogger(String filename) {
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
			file.write("Round\n");
			file.write(state.getP1Name() + " chose " + state.getP1Action() + " scoring " + state.p1Score + ".\n");
			file.write(state.getP2Name() + " chose " + state.getP2Action() + " scoring " + state.p2Score + ".\n");
			file.close();
		} catch (IOException e) {
			System.out.println("File reading/writing went wrong for " + filename);
		}
	}
}
