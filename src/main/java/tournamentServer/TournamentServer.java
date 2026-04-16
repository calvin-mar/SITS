package tournamentServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Random;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

import tournament.*;

@SpringBootApplication
@RestController 
public class TournamentServer {
	HashMap<String, Tournament> availableTournaments;
	HashMap<String, Boolean> registerTournaments;
	ArrayList<MoveListener> spectators;
	
	@Autowired
	private ServletWebServerApplicationContext serverContext;
	
	public record BotData(InetAddress IP, String botName, int port, String tournamentName) {};
	public record TournamentList(List<String> tournaments) {};
	
	public TournamentServer() {
		this.availableTournaments = new HashMap<String, Tournament>();
		this.registerTournaments = new HashMap<String, Boolean>();
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
	public String register(@RequestBody BotData data){
		if(registerTournaments.containsKey(data.tournamentName()) && registerTournaments.get(data.tournamentName())) {
			ProxyBot proxy = new ProxyBot(data.IP(), data.botName(), data.port());
			availableTournaments.get(data.tournamentName()).addParticipant(proxy);
			return "Successful Register";
		}
		else{
			return "Unsuccessful Register";
		}
	}
	
	public void beginTournament(Tournament tournament) {
		tournament.playTournament();
	}
	
	public HashMap<String, Tournament> getAvailableTournaments(){
		return availableTournaments;
	}
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/checkTournaments")
	public TournamentList checkRegisterableTournaments(){
		ArrayList<String> tourneys = new ArrayList<String>();
		for(String key : availableTournaments.keySet()) {
			if(registerTournaments.get(key)) {
				tourneys.add(key);
			}
		}
		TournamentList t = new TournamentList(tourneys);
		return t;
	}
	
	public record SpectateInfo(String tournamentName, InetAddress ip, int port) {};
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/spectate")
	public void spectateTournment(@RequestBody SpectateInfo serverInfo) {
		UserInfo u = new UserInfo(serverInfo.ip, serverInfo.port);
		MoveListener newListener = new MoveListener(u);
		
		if(this.registerTournaments.containsKey(serverInfo.tournamentName )) {
			this.spectators.add(newListener);
			this.availableTournaments.get(serverInfo.tournamentName).getGame().registerActions(newListener);
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/stopSpectate")
	public void stopSpectateTournment(@RequestBody SpectateInfo serverInfo) {
		UserInfo u = new UserInfo(serverInfo.ip, serverInfo.port);
		MoveListener listener = null;
		
		for(MoveListener l: spectators) {
			if(l.getServerData() == u) {
				listener = l;
			}
		}
		if(!(listener == null)) {
			this.availableTournaments.get(serverInfo.tournamentName).getGame().deregisterActions(listener);
		}
	}
	
	public InetAddress getIP() {
		try {
			return InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public int getPort() {
		return this.serverContext.getWebServer().getPort();
	}
	
	public static void main(String[] args) {
        new SpringApplicationBuilder(TournamentServer.class).profiles("random").run(args);
    }

}
