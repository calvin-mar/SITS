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

import remoteBots.RemoteController;

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
	
	Tournament tournament2;
	Bracket bracket2;
	Game game2;
	
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
		
		bracket2 = new RoundRobinBracket();
		game2 = new IteratedPrisonerDilemna(3);
		tournament2 = new Tournament(game, bracket);
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
		server.addTournament("RRPrisoners 2", tournament2);
		Participant selfishParticipant = new SelfishBot();
		Participant selflessParticipant = new SelflessBot();
		Participant alternatingParticipant = new AlternatingBot();
		
		tournament2.addParticipant(selfishParticipant);
		tournament2.addParticipant(selflessParticipant);
		tournament2.addParticipant(alternatingParticipant);
		
		server.beginTournament(tournament2);
		
		assertEquals(22, tournament2.getScoreboard().get(0).getTotalScore());
		assertEquals(6, tournament2.getScoreboard().get(1).getTotalScore());
		assertEquals(13, tournament2.getScoreboard().get(2).getTotalScore());
	}

}
