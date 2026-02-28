package tournament;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TourneyPlayerTest {
	Participant p1;
	Participant p2;
	Participant p3;
	TourneyPlayer player1;
	TourneyPlayer player2;
	TourneyPlayer player3;
	
	ArrayList<TourneyPlayer> pastPlays;
	
	@BeforeEach
	void setUp() throws Exception {
		p1 = new AlternatingBot();
		player1 = new TourneyPlayer(p1);
		p2 = new AlternatingBot();
		player2 = new TourneyPlayer(p2);
		p3 = new AlternatingBot();
		player3 = new TourneyPlayer(p3);
		pastPlays = new ArrayList<TourneyPlayer>();
	}

	@Test
	void tourneyPlayerTestSetUp() {
		assertEquals(player1.getPlayer(), p1);
		assertEquals(player1.getTotalScore(), 0);
		assertEquals(player1.getPlayersPlayed(), pastPlays);
	}
	
	@Test
	void testScoring() {
		player1.setTotalScore(1);
		assertEquals(player1.getTotalScore(), 1);
		player1.increaseScore(2);
		assertEquals(player1.getTotalScore(), 3);
	}
	
	@Test
	void testMemory() {
		pastPlays.add(player2);
		player1.addPlayerPlayed(player2);
		assertEquals(player1.getPlayersPlayed(), pastPlays);
		
		pastPlays.add(player3);
		player1.addPlayerPlayed(player3);
		assertEquals(player1.getPlayersPlayed(), pastPlays);
	}
}
