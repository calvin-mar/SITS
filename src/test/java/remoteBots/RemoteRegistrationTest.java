package remoteBots;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tournament.*;
import tournamentServer.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest(
		webEnvironment=WebEnvironment.RANDOM_PORT,
		classes = TournamentServer.class)
@AutoConfigureRestTestClient

class RemoteRegistrationTest {
	RemoteRegistration registrar;
	Tournament tournament;
	Bracket bracket;
	Game game;
	
	
	@Autowired
	private TournamentServer server;
	
	@BeforeEach
	void setUp() throws Exception {
		// Tested in TournamentServerTes
		
		bracket = new RoundRobinBracket();
		game = new IteratedPrisonerDilemna(3);
		tournament = new Tournament(game, bracket);
		server.addTournament("RRPrisoners", tournament);
		server.beginRegistration("RRPrisoners");
		
		// Init RemoteRegistration
		String baseURL = "http://" + server.getIP().getHostAddress() + ":" + String.valueOf(server.getPort()) + "/";
		registrar = new RemoteRegistration("Selfish Bot", InetAddress.getByName("127.0.0.1"), 100, baseURL);
	}

	@Test
	void test() throws UnknownHostException {
		// Check Successful Registration
		registrar.registerForTournament("RRPrisoners");
		assertEquals(1, tournament.getScoreboard().size());
		
		ProxyBot testProxy = new ProxyBot(InetAddress.getByName("127.0.0.1"),"Selfish Bot", 100);
		ProxyBot recProxy = (ProxyBot) tournament.getScoreboard().get(0).getPlayer();
		assertEquals(testProxy.getIP(),recProxy.getIP());
		assertEquals(testProxy.getName(), recProxy.getName());
		assertEquals(testProxy.getPort(), recProxy.getPort());
		}

}
