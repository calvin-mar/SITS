package tournamentServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Random;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import tournament.*;

@SpringBootApplication
@RestController 
public class TournamentServer {
	HashMap<String, Tournament> availableTournaments;
	HashMap<String, Boolean> registerTournaments;
	
	public record BotData(InetAddress IP, String botName, int port, String tournamentName) {};
	
	public TournamentServer() {
		this.availableTournaments = new HashMap<String, Tournament>();
	}
	
	public void addTournament(String name, Tournament tournament) {
		availableTournaments.put(name, tournament);
		registerTournaments.put(name, false);
	}
	
	public void beginRegistration(String tournament) {
		registerTournaments.put(tournament, true);
	}
	
	public void endRegistration(String tournament) {
		registerTournaments.put(tournament, false);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/register")
	public void register(@RequestBody BotData data){
		Proxy proxy = new Proxy(data.IP(), data.botName(), data.port());
		availableTournaments.get(data.tournamentName()).addParticipant(proxy);
	}
	
	public void beginTournament(Tournament tournament) {
		tournament.playTournament();
	}
	
	public static void main(String[] args) {
        new SpringApplicationBuilder(TournamentServer.class).profiles("random").run(args);
    }

}
