package tournament;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HumanBotTest {
	Participant ABot;
	Participant HBot;
	ByteArrayOutputStream outputStream;
	

	@BeforeEach
	void setUp() throws Exception {
		//Check Input and Output for makeChoice
		String userInput = String.format("0%s1%s1", System.lineSeparator(), System.lineSeparator());
		ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
		System.setIn(inputStream);
		
		this.outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		System.setOut(printStream);
		
		
		ABot = new AlternatingBot();
		HBot = new HumanBot();
	}

	@Test
	void test() {
		State state1 = new State("Alternating Bot", "Human Bot");
		State state2 = new State("Human Bot", "Alternating Bot");
		
		assertEquals("Human Bot", HBot.getName());
		
		
		assertEquals(0,HBot.makeChoice(2));
		assertEquals(1,HBot.makeChoice(2));
		assertEquals(1,HBot.makeChoice(2));
		
		state1.setP1Action(0);
		state1.setP2Action(1);
		state1.setP1Score(5);
		state1.setP2Score(0);
		HBot.updateMemory(state1);
		
		state2.setP1Action(0);
		state2.setP2Action(1);
		state2.setP1Score(5);
		state2.setP2Score(0);
		HBot.updateMemory(state2);
		
		String[] lines = this.outputStream.toString().split(System.lineSeparator());
	    
		assertEquals("Enter your choice from 2 actions. Your choice should be an integer 0-2: "
				+ "Enter your choice from 2 actions. Your choice should be an integer 0-2: "
				+ "Enter your choice from 2 actions. Your choice should be an integer 0-2: "
				+ "Your choice was 1 and Alternating Bot chose 0.", lines[lines.length-4]);
		assertEquals("You scored 0 and Alternating Bot's score is 5.", lines[lines.length-3]);
		assertEquals("Your choice was 0 and Alternating Bot chose 1.", lines[lines.length-2]);
		assertEquals("You scored 5 and Alternating Bot's score is 0.", lines[lines.length-1]);

		
	}

}
