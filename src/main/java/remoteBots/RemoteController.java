package remoteBots;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.server.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Random;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import tournament.Participant;
import tournament.State;

@SpringBootApplication
@RestController 
public class RemoteController {
	String botName;
	Participant participantType;
	RemoteRegistration registrar;
	
	@Autowired
	private ServletWebServerApplicationContext serverContext;
	
	public record StateRecord(String p1Name, int p1Score, int p1Action, String p2Name, int p2Score, int p2Action) {};


	public void registerForTournament(String tournament) {
		this.registrar.registerForTournament(tournament);
	}
	
	public void setParticipantType(Participant participant) {
		this.participantType = participant;
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/makeChoice/{actions}")
	public int makeChoice(@PathVariable int actions) {
		return this.participantType.makeChoice(actions);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/updateMemory")
	public String updateMemory(@RequestBody StateRecord state) {
		State newState = new State(state.p1Name(), state.p2Name());
		newState.setP1Action(state.p1Action());
		newState.setP2Action(state.p2Action());
		newState.setP1Score(state.p1Score());
		newState.setP2Score(state.p2Score());
		System.out.println(newState);
		this.participantType.updateMemory(newState);
		return "Successful update";
	}
	
	public String getBotName() {
		return botName;
	}

	public Participant getParticipantType() {
		return participantType;
	}
	
	public void setBotName(String botName) {
		this.botName = botName;
	}
	
	public void setRegistrar(String baseURL) {
		try {
			this.registrar = new RemoteRegistration(this.participantType.getName(), InetAddress.getLocalHost(), this.serverContext.getWebServer().getPort(), baseURL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getPort() {
		return this.serverContext.getWebServer().getPort();
	}
	
	public InetAddress getIP() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
        new SpringApplicationBuilder(RemoteController.class).profiles("random").run(args);
    }
}
