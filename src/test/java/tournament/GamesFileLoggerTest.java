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

class GamesFileLoggerTest {
	Game game;
	Participant p1;
	Participant p2;
	Participant p3;
	
	GamesFileLogger gameObserver;
	
	@BeforeEach
	void setUp() throws Exception {
		game = new IteratedPrisonerDilemna(3);
		p1 = new SelfishBot();
		p2 = new SelflessBot();
		p3 = new SelflessBot();
		this.gameObserver = new GamesFileLogger("games.txt");
		
		try {
			FileWriter fileW = new FileWriter("games.txt");
			fileW.write("");
		} catch(IOException e) {
			System.out.println("Error resetting file");
		}
	}

	@Test
	void observerTest() {
		this.game.registerGames(gameObserver);
		this.game.play(p1, p2);
		File file = new File("games.txt");
		
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
		
		assertEquals("Game\nSelfish Bot played Selfless Bot with a final score of 15-0.\n", fileText);
		

		this.game.play(p2, p3);
		file = new File("games.txt");

		fileText = "";
		try (Scanner myReader = new Scanner(file)) {
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        fileText += data + "\n";
		      }
		} catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		}
		
		assertEquals("Game\nSelfish Bot played Selfless Bot with a final score of 15-0.\n" +
		"Game\nSelfless Bot played Selfless Bot with a final score of 9-9.\n", fileText);
	}
}		
