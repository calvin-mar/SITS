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
		
		// ADD TESTING FOR UPDATE MEMORY
	}

}
