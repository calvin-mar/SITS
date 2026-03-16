package tournamentServer;

import static org.junit.jupiter.api.Assertions.*;

import java.net.InetAddress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import remoteBots.*;
import tournament.*;

@SpringBootTest(
		webEnvironment=WebEnvironment.RANDOM_PORT,
		classes = RemoteController.class)
class ProxyBotTest {
	ProxyBot testProxy;
	
	@Autowired
	RemoteController server;

	@BeforeEach
	void setUp() throws Exception {
		server.setParticipantType(new AlternatingBot());
		InetAddress serverIP = server.getIP();
		int serverPort = server.getPort();
		testProxy = new ProxyBot(serverIP,"Test Proxy", serverPort);
	}

	@Test
	void test() {
		assertEquals(0, testProxy.makeChoice(2));
		assertEquals(1, testProxy.makeChoice(2));
		assertEquals(0, testProxy.makeChoice(2));
		

		State state = new State("p1", "p2");
		state.setP1Score(1);
		state.setP2Score(1);
		state.setP1Action(0);
		state.setP2Action(0);
		
		testProxy.updateMemory(state);
		
		assertEquals("p1", server.getParticipantType().getMemory().get(0).getP1Name());
		assertEquals(1, server.getParticipantType().getMemory().get(0).getP1Score());
		assertEquals(0, server.getParticipantType().getMemory().get(0).getP1Action());
		assertEquals("p2", server.getParticipantType().getMemory().get(0).getP2Name());
		assertEquals(1, server.getParticipantType().getMemory().get(0).getP2Score());
		assertEquals(0, server.getParticipantType().getMemory().get(0).getP2Action());
	}

}
