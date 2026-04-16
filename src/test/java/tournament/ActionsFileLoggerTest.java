package tournament;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;   
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.io.FileWriter;

class ActionsFileLoggerTest {
	Game game;
	Participant p1;
	Participant p2;
	
	ActionsFileLogger actionObserver;
	
	@BeforeEach
	void setUp() throws Exception {
		game = new IteratedPrisonerDilemna(3);
		game.setTimeDelay(0);
		p1 = new SelfishBot();
		p2 = new SelflessBot();
		this.actionObserver = new ActionsFileLogger("actions.txt");
		try {
			FileWriter fileW = new FileWriter("actions.txt");
			fileW.write("");
		} catch(IOException e) {
			System.out.println("Error resetting file");
		}
	}

	@Test
	void observerTest() {
		this.game.registerActions(actionObserver);
		this.game.play(p1, p2);
		File file = new File("actions.txt");
		
		String fileText = "";
		try (Scanner myReader = new Scanner(file)) {
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        fileText += data + "\n";
		      }
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		assertEquals("Round\nSelfish Bot chose 0 scoring 5.\n"
				+ "Selfless Bot chose 1 scoring 0.\n"
				+ "Round\nSelfish Bot chose 0 scoring 10.\n"
				+ "Selfless Bot chose 1 scoring 0.\n"
				+ "Round\nSelfish Bot chose 0 scoring 15.\n"
				+ "Selfless Bot chose 1 scoring 0.\n", fileText);
		
	}

}
