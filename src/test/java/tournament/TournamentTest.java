package tournament;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TournamentTest {
	Tournament tournament;
	ArrayList<TourneyPlayer> scoreboard;
	Bracket bracket;
	Game game;
	Participant selfishParticipant;
	Participant selflessParticipant;
	Participant alternatingParticipant;
	@BeforeEach
	void setUp() throws Exception {
		bracket = new RoundRobinBracket();
		game = new IteratedPrisonerDilemna(3);
		tournament = new Tournament(game, bracket);
		
		selfishParticipant = new SelfishBot();
		selflessParticipant = new SelflessBot();
		alternatingParticipant = new AlternatingBot();
		
		tournament.addParticipant(selfishParticipant);
		tournament.addParticipant(selflessParticipant);
		tournament.addParticipant(alternatingParticipant);
	}

	@Test
	void tournamentTest() {
		assertEquals(selfishParticipant, tournament.getScoreboard().get(0).getPlayer());
		assertEquals(selflessParticipant, tournament.getScoreboard().get(1).getPlayer());
		assertEquals(alternatingParticipant, tournament.getScoreboard().get(2).getPlayer());
		
		tournament.playTournament();
		
		assertEquals(22, tournament.getScoreboard().get(0).getTotalScore());
		assertEquals(6, tournament.getScoreboard().get(1).getTotalScore());
		assertEquals(13, tournament.getScoreboard().get(2).getTotalScore());
	}
}
