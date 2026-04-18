package views;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import tournamentServer.TournamentServer;


@SpringBootTest(
		webEnvironment=WebEnvironment.RANDOM_PORT,
		classes = TournamentServer.class) 
class ConnectToServerControllerTest {

	@Autowired
	private TournamentServer server;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
