package tournamentServer;

import java.net.InetAddress;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import tournament.*;

public class ProxyBot extends Participant {

	InetAddress IP;
	String name;
	int port;
	RestClient client;
	
	public record StateRecord(String p1Name, int p1Score, int p1Action, String p2Name, int p2Score, int p2Action) {};
	
	@Override
	public int makeChoice(int actions) {
		int choice = client.get().uri("/makeChoice/{actions}",actions).retrieve().body(Integer.class);
		return choice;
	}
	
	@Override
	public void updateMemory(State newState) {
		super.updateMemory(newState);
		StateRecord sendState = new StateRecord(newState.getP1Name(), newState.getP1Score(), newState.getP1Action(), newState.getP2Name(), newState.getP2Score(), newState.getP2Action());
		client.put().uri("/updateMemory").body(sendState).retrieve().body(String.class);

		}
	
	public ProxyBot(InetAddress iP, String name, int port) {
		super();
		IP = iP;
		this.name = name;
		this.port = port;
		String baseURL = "http://" + iP.getHostAddress() + ":" + String.valueOf(port) + "/";
		client = RestClient.builder().baseUrl(baseURL).build();
	}

	public InetAddress getIP() {
		return IP;
	}

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	public void setIP(InetAddress iP) {
		IP = iP;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
