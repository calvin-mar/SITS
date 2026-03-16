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
		classes = RemoteController.class)
@AutoConfigureRestTestClient
class RemoteControllerTest {
	
	Participant participant;

	@Autowired
	RemoteController remote;
	
	@Autowired
	private RestTestClient client;
	
	
	@BeforeEach
	void setUp() throws Exception {
		participant = new SelfishBot();
		remote.setParticipantType(participant);
		remote.setBotName("Selfish Bot");
	}

	@Test
	void test() {
		client.get().uri("/makeChoice/2").exchange().expectBody(Integer.class).isEqualTo(0);
		client.get().uri("/makeChoice/2").exchange().expectBody(Integer.class).isEqualTo(0);
		client.get().uri("/makeChoice/2").exchange().expectBody(Integer.class).isEqualTo(0);
		participant = new SelflessBot();
		remote.setParticipantType(participant);
		client.get().uri("/makeChoice/2").exchange().expectBody(Integer.class).isEqualTo(1);
		client.get().uri("/makeChoice/2").exchange().expectBody(Integer.class).isEqualTo(1);
		client.get().uri("/makeChoice/2").exchange().expectBody(Integer.class).isEqualTo(1);
		
		// ADD TESTING FOR UPDATE MEMORY
	}

}
