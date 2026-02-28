package tournament;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IteratedPrisonerDilemnaTest {
	Game ipd;
	Participant participant1;
	Participant participant2;
	Participant participant3;
	
	@BeforeEach
	void setUp() throws Exception {
		this.ipd = new IteratedPrisonerDilemna(3);
		participant1 = new SelfishBot();
		participant2 = new SelflessBot();
		participant3 = new AlternatingBot();
	}

	@Test
	void scoreActionsTest() {
		int[] testReturn = new int[] {1,1};
		assertArrayEquals(testReturn, this.ipd.scoreActions(0,0));
		
		testReturn[0] = 5;
		testReturn[1] = 0;
		assertArrayEquals(testReturn, this.ipd.scoreActions(0,1));
		
		testReturn[0] = 0;
		testReturn[1] = 5;
		assertArrayEquals(testReturn, this.ipd.scoreActions(1,0));
		
		testReturn[0] = 3;
		testReturn[1] = 3;
		assertArrayEquals(testReturn, this.ipd.scoreActions(1,1));
	}

	@Test
	void endGameTest() {
		boolean endBool = false;
		assertEquals(endBool, this.ipd.endGame());
		assertEquals(endBool, this.ipd.endGame());
		assertEquals(endBool, this.ipd.endGame());
		endBool = true;
		assertEquals(endBool, this.ipd.endGame());
	}
	
	@Test
	void playTest() {
		State testState1 = new State(participant1.getName(), participant2.getName());
		testState1.setP1Action(0);
		testState1.setP2Action(1);
		testState1.setP1Score(15);
		testState1.setP2Score(0);
		assertEquals(testState1, this.ipd.play(participant1, participant2));

		State testState2 = new State(participant1.getName(), participant3.getName());
		testState2.setP1Action(0);
		testState2.setP2Action(0);
		testState2.setP1Score(7);
		testState2.setP2Score(2);
		assertEquals(testState2, this.ipd.play(participant1, participant3));

		State testState3 = new State(participant2.getName(), participant3.getName());
		testState3.setP1Action(1);
		testState3.setP2Action(1);
		testState3.setP1Score(6);
		testState3.setP2Score(11);
		assertEquals(testState3, this.ipd.play(participant2, participant3));
	}
}
