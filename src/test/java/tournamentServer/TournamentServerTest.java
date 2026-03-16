package tournamentServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tournament.*;

@SpringBootTest(
		webEnvironment=WebEnvironment.RANDOM_PORT,
		classes = TournamentServer.class) 
@AutoConfigureRestTestClient

class TournamentServerTest {
	Tournament tournament;
	Bracket bracket;
	Game game;
	
	public record BotData(InetAddress IP, String botName, int port, String tournamentName) {};

	
	@Autowired
	private TournamentServer server;
	
	@Autowired
	private RestTestClient client;
	
	@BeforeEach
	void setUp() throws Exception {
		bracket = new RoundRobinBracket();
		game = new IteratedPrisonerDilemna(3);
		tournament = new Tournament(game, bracket);
	}

	@Test
	void testAllTournamentServer() throws UnknownHostException {
		// Check addTournament
		server.addTournament("RRPrisoners", tournament);
		Set<String> keys = new HashSet<String>();
		keys.add("RRPrisoners");
		assertEquals(keys,server.getAvailableTournaments().keySet());
		assertEquals(tournament,server.getAvailableTournaments().get("RRPrisoners"));
		
		// Register with Tournament
		server.beginRegistration("RRPrisoners");
		BotData data = new BotData(InetAddress.getByName("127.0.0.1"), "Tester", 100, "RRPrisoners");
		client.put().uri("/register").body(data).exchange().expectBody(String.class).isEqualTo("Successful Register");
		
		// Try to register our of registration period
		server.endRegistration("RRPrisoners");
		BotData badData = new BotData(InetAddress.getByName("127.0.0.1"), "Tester", 100, "RRPrisoners");
		client.put().uri("/register").body(badData).exchange().expectBody(String.class).isEqualTo("Unsuccessful Register");
		
		// Try to run tournament through server
		Participant selflessParticipant = new SelflessBot();
		Participant alternatingParticipant = new AlternatingBot();
		
		tournament.addParticipant(selflessParticipant);
		tournament.addParticipant(alternatingParticipant);
		
		server.beginTournament(tournament);
		
		assertEquals(22, tournament.getScoreboard().get(0).getTotalScore());
		assertEquals(6, tournament.getScoreboard().get(1).getTotalScore());
		assertEquals(13, tournament.getScoreboard().get(2).getTotalScore());
	}

}
