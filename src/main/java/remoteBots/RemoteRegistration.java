package remoteBots;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.client.RestClient;

import tournament.Participant;

public class RemoteRegistration {
	RestClient client;
	
	String botName;
	InetAddress ip;
	int port;
	
	public record BotData(InetAddress IP, String botName, int port, String tournamentName) {};
	
	public RemoteRegistration(String name, InetAddress ip, int port, String baseURL) {
		this.botName = name;
		this.ip = ip;
		this.port = port;
		this.client = RestClient.builder()
				.baseUrl(baseURL)
				.build();
	}
	
	public void registerForTournament(String tournament){
		BotData data = new BotData(ip, this.botName, this.port, tournament);
		String message = client.put().uri("/register").body(data).retrieve().body(String.class);
	}
	
}
