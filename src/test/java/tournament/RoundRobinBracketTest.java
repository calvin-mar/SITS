package tournament;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoundRobinBracketTest {
	ArrayList<TourneyPlayer> participantList;
	Participant selfishParticipant;
	TourneyPlayer selfishPlayer;
	Participant selflessParticipant;
	TourneyPlayer selflessPlayer;
	Participant alternatingParticipant;
	TourneyPlayer alternatingPlayer;
	Bracket bracket;

	@BeforeEach
	void setUp() throws Exception {
		participantList = new ArrayList<TourneyPlayer>();
		selfishParticipant = new SelfishBot();
		selfishPlayer = new TourneyPlayer(selfishParticipant);
		participantList.add(selfishPlayer);
		
		selflessParticipant = new SelflessBot();
		selflessPlayer = new TourneyPlayer(selflessParticipant);
		participantList.add(selflessPlayer);
		
		alternatingParticipant = new AlternatingBot();
		alternatingPlayer = new TourneyPlayer(alternatingParticipant);
		participantList.add(alternatingPlayer);
		
		bracket = new RoundRobinBracket(participantList);
	}

	@Test
	void nextPairTest() {
		// First Round
		State state = null;
		TourneyPlayer[] pair = bracket.nextPair(state);
		assertArrayEquals(pair, new TourneyPlayer[] {selfishPlayer, selflessPlayer});
		state = new State(selfishPlayer.getPlayer().getName(), selflessPlayer.getPlayer().getName());
		state.setP1Score(15);
		state.setP2Score(0);
		
		// Second Round
		pair = bracket.nextPair(state);
		assertArrayEquals(pair, new TourneyPlayer[] {selfishPlayer, alternatingPlayer});
		assertEquals(selfishPlayer.getTotalScore(), 15);
		assertEquals(selflessPlayer.getTotalScore(), 0);
		
		state = new State(selfishPlayer.getPlayer().getName(), alternatingPlayer.getPlayer().getName());
		state.setP1Score(7);
		state.setP2Score(2);
		
		// Third Round
		pair = bracket.nextPair(state);
		assertArrayEquals(pair, new TourneyPlayer[] {selflessPlayer, alternatingPlayer});
		assertEquals(selfishPlayer.getTotalScore(), 22);
		assertEquals(alternatingPlayer.getTotalScore(), 2);
		
		state = new State(selflessPlayer.getPlayer().getName(), alternatingPlayer.getPlayer().getName());
		state.setP1Score(6);
		state.setP2Score(11);
		
		// End Round
		pair = bracket.nextPair(state);
		assertArrayEquals(pair, new TourneyPlayer[] {null, null});
		assertEquals(selflessPlayer.getTotalScore(), 6);
		assertEquals(alternatingPlayer.getTotalScore(), 13);
	}

}
