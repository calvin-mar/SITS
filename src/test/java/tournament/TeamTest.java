package tournament;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamTest {
	Team MajorityTeam;
	Team CaptainTeam;
	
	Participant selfishBot;
	Participant selflessBot;
	Participant alternatingBot;
	
	Participant selfishBot2;
	Participant alternatingBot2;
	Participant alternatingBot3;

	@BeforeEach
	void setUp() throws Exception {
		selfishBot = new SelfishBot();
		selflessBot = new SelflessBot();
		alternatingBot = new AlternatingBot();
		
		MajorityTeam = new MajorityRules();
		MajorityTeam.joinTeam(selfishBot);
		MajorityTeam.joinTeam(selflessBot);
		MajorityTeam.joinTeam(alternatingBot);
		
		
		selfishBot2 = new SelfishBot();
		alternatingBot2 = new AlternatingBot();
		alternatingBot3 = new AlternatingBot();
		
		CaptainTeam = new RotatingCaptain();
		CaptainTeam.joinTeam(selfishBot2);
		CaptainTeam.joinTeam(alternatingBot2);
		CaptainTeam.joinTeam(alternatingBot3);
	}

	@Test
	void MajorityTest() {
		State state1 = new State("Majority Rules Bot", "Rotating Captain Bot");
		State state2 = new State("Majority Rules Bot", "Rotating Captain Bot");
		
		ArrayList<State> testMemory = new ArrayList<State>();
		
		assertEquals(MajorityTeam.getName(), "Majority Rules Bot");
		
		assertEquals(MajorityTeam.makeChoice(3), 0);
		assertEquals(MajorityTeam.makeChoice(3), 1);
		assertEquals(MajorityTeam.makeChoice(3), 0);
		assertEquals(MajorityTeam.makeChoice(3), 0);
		assertEquals(MajorityTeam.makeChoice(3), 1);
		assertEquals(MajorityTeam.makeChoice(3), 0);
		
		MajorityTeam.updateMemory(state1);
		testMemory.add(state1);
		assertEquals(selfishBot.memory, testMemory);
		assertEquals(selflessBot.memory, testMemory);
		assertEquals(alternatingBot.memory, testMemory);
		
		MajorityTeam.updateMemory(state2);
		testMemory.add(state2);
		assertEquals(selfishBot.memory, testMemory);
		assertEquals(selflessBot.memory, testMemory);
		assertEquals(alternatingBot.memory, testMemory);
	}
	
	@Test
	void CaptainTest() {
		State state1 = new State("Majority Rules Bot", "Rotating Captain Bot");
		State state2 = new State("Majority Rules Bot", "Rotating Captain Bot");
		
		ArrayList<State> testMemory = new ArrayList<State>();
		
		assertEquals(CaptainTeam.getName(), "Rotating Captain Bot");

		assertEquals(CaptainTeam.makeChoice(2), 0);
		assertEquals(CaptainTeam.makeChoice(2), 0);
		assertEquals(CaptainTeam.makeChoice(2), 0);
		assertEquals(CaptainTeam.makeChoice(2), 0);
		assertEquals(CaptainTeam.makeChoice(2), 1);
		assertEquals(CaptainTeam.makeChoice(2), 1);
		
		CaptainTeam.updateMemory(state1);
		testMemory.add(state1);
		assertEquals(selfishBot2.memory, testMemory);
		assertEquals(alternatingBot2.memory, testMemory);
		assertEquals(alternatingBot3.memory, testMemory);
	}

}
