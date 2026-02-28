package tournament;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParticipantTest {
	Participant ABot;
	Participant BBot;
	Participant CBot;
	
	@BeforeEach
	void setUp() throws Exception {
		ABot = new AlternatingBot();
		BBot = new SelfishBot();
		CBot = new SelflessBot();
	}

	@Test
	void alternatingBotTest() {
		State state1 = new State("ABot", "BBot");
		State state2 = new State("ABot", "BBot");

		State state3 = new State("ABot", "CBot");
		State state4 = new State("ABot", "CBot");

		
		ArrayList<State> testMemory = new ArrayList<State>();
		
		assertEquals(ABot.getName(), "Alternating Bot");
		
		// Check Alternating Behavior
		assertEquals(ABot.makeChoice(2), 0);
		assertEquals(ABot.makeChoice(2), 1);
		assertEquals(ABot.makeChoice(2), 0);
		assertEquals(ABot.makeChoice(2), 1);
		
		// Check Memory Abilities
		ABot.updateMemory(state1);
		testMemory.add(state1);
		assertEquals(ABot.memory, testMemory);
		
		ABot.updateMemory(state2);
		testMemory.add(state2);
		assertEquals(ABot.memory, testMemory);
		
		ABot.updateMemory(state3);
		testMemory.add(state3);
		assertEquals(ABot.memory, testMemory);
		
		ABot.updateMemory(state4);
		testMemory.add(state4);
		assertEquals(ABot.memory, testMemory);
		
		ABot.clearMemory();
		testMemory.clear();
		assertEquals(ABot.memory, testMemory);
		
		// Change the name
		ABot.setName("Alternating Bot 2");
		assertEquals(ABot.getName(), "Alternating Bot 2");
	}
	
	@Test
	void selfishBotTest() {
		State state1 = new State("ABot", "BBot");
		State state2 = new State("ABot", "BBot");

		State state3 = new State("BBot", "CBot");
		State state4 = new State("BBot", "CBot");

		
		ArrayList<State> testMemory = new ArrayList<State>();
		
		assertEquals(BBot.getName(), "Selfish Bot");
		
		// Check Alternating Behavior
		assertEquals(BBot.makeChoice(2), 0);
		assertEquals(BBot.makeChoice(2), 0);
		assertEquals(BBot.makeChoice(2), 0);
		assertEquals(BBot.makeChoice(2), 0);

		
		// Check Memory Abilities
		BBot.updateMemory(state1);
		testMemory.add(state1);
		assertEquals(BBot.memory, testMemory);
		
		BBot.updateMemory(state2);
		testMemory.add(state2);
		assertEquals(BBot.memory, testMemory);
		
		BBot.updateMemory(state3);
		testMemory.add(state3);
		assertEquals(BBot.memory, testMemory);
		
		BBot.updateMemory(state4);
		testMemory.add(state4);
		assertEquals(BBot.memory, testMemory);
		
		BBot.clearMemory();
		testMemory.clear();
		assertEquals(BBot.memory, testMemory);
		
		// Change the name
		BBot.setName("Selfish Bot 2");
		assertEquals(BBot.getName(), "Selfish Bot 2");
	}
	@Test
	void selflessBotTest() {
		State state1 = new State("CBot", "BBot");
		State state2 = new State("CBot", "BBot");

		State state3 = new State("ABot", "CBot");
		State state4 = new State("ABot", "CBot");

		
		ArrayList<State> testMemory = new ArrayList<State>();
		
		assertEquals(CBot.getName(), "Selfless Bot");
		
		// Check Alternating Behavior
		assertEquals(CBot.makeChoice(2), 1);
		assertEquals(CBot.makeChoice(2), 1);
		assertEquals(CBot.makeChoice(2), 1);
		assertEquals(CBot.makeChoice(2), 1);
		
		// Check Memory Abilities
		CBot.updateMemory(state1);
		testMemory.add(state1);
		assertEquals(CBot.memory, testMemory);
		
		CBot.updateMemory(state2);
		testMemory.add(state2);
		assertEquals(CBot.memory, testMemory);
		
		CBot.updateMemory(state3);
		testMemory.add(state3);
		assertEquals(CBot.memory, testMemory);
		
		CBot.updateMemory(state4);
		testMemory.add(state4);
		assertEquals(CBot.memory, testMemory);
		
		CBot.clearMemory();
		testMemory.clear();
		assertEquals(CBot.memory, testMemory);
		
		// Change the name
		CBot.setName("Selfless Bot 2");
		assertEquals(CBot.getName(), "Selfless Bot 2");
	}
}