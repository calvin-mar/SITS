package tournamentServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

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
	
	Tournament tournament3;
	Bracket bracket3;
	Game game3;
	
	public record BotData(InetAddress IP, String botName, int port, String tournamentName) {};
	public record TournamentList(List<String> tournaments) {};
	
	@Autowired
	private TournamentServer server;
	
	@Autowired
	private RestTestClient client;
	
	@BeforeEach
	void setUp() throws Exception {
		bracket = new RoundRobinBracket();
		game = new IteratedPrisonerDilemna(3);
		game.setTimeDelay(0);
		tournament = new Tournament(game, bracket);
		
		bracket2 = new RoundRobinBracket();
		game2 = new IteratedPrisonerDilemna(3);
		game2.setTimeDelay(0);
		tournament2 = new Tournament(game2, bracket2);
		
		bracket3 = new RoundRobinBracket();
		game3 = new IteratedPrisonerDilemna(3);
		game3.setTimeDelay(0);
		tournament3 = new Tournament(game3, bracket3);
	}

	@Test
	void testAllTournamentServer() throws UnknownHostException {
		SelfishBot selfishParticipant = new SelfishBot();
		SelflessBot selflessParticipant = new SelflessBot();
		AlternatingBot alternatingParticipant = new AlternatingBot();
		
		tournament.addParticipant(selfishParticipant);
		tournament.addParticipant(selflessParticipant);
		tournament.addParticipant(alternatingParticipant);
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
		server.beginRegistration("RRPrisoners 2");
		
		tournament2.addParticipant(selfishParticipant);
		tournament2.addParticipant(selflessParticipant);
		tournament2.addParticipant(alternatingParticipant);
		
		server.beginTournament(tournament2);
		
		assertEquals(22, tournament2.getScoreboard().get(0).getTotalScore());
		assertEquals(6, tournament2.getScoreboard().get(1).getTotalScore());
		assertEquals(13, tournament2.getScoreboard().get(2).getTotalScore());
		
		ArrayList<String> registerableT = new ArrayList<String>();
		registerableT.add("RRPrisoners 2");
		
		
		tournament3.addParticipant(selfishParticipant);
		tournament3.addParticipant(selflessParticipant);
		tournament3.addParticipant(alternatingParticipant);
		server.addTournament("RRPrisoners 3", tournament3);
		server.beginRegistration("RRPrisoners 3");
		registerableT.add("RRPrisoners 3");
		TournamentList t = new TournamentList(registerableT);
		client.get().uri("/checkTournaments").exchange().expectBody(TournamentList.class).isEqualTo(t);
	
		// Testing spectateInfo
		record SpectateInfo(String tournamentName, InetAddress ip, int port) {};

		InetAddress testIP = InetAddress.getByName("10.14.5.64");
		SpectateInfo s = new SpectateInfo("RRPrisoners", testIP, 1);
		client.put().uri("/spectate").body(s).exchange().expectBody(String.class).isEqualTo("Successful register");
		SpectateInfo s2 = new SpectateInfo("RRPrisoners 2", testIP, 1);
		client.put().uri("/spectate").body(s2).exchange().expectBody(String.class).isEqualTo("Successful register");
		
		client.put().uri("/stopSpectate").body(s).exchange().expectBody(String.class).isEqualTo("Successful deregister");
		client.put().uri("/stopSpectate").body(s2).exchange().expectBody(String.class).isEqualTo("Successful deregister");


		
	}

}
