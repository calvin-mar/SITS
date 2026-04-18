package tournament;

import java.net.InetAddress;

import org.springframework.web.client.RestClient;

public class MoveListener implements Observer {
	
	RestClient client;
	UserInfo serverData;
	
	
	public RestClient getClient() {
		return client;
	}

	public UserInfo getServerData() {
		return serverData;
	}

	public void setClient(RestClient client) {
		this.client = client;
	}

	public void setServerData(UserInfo serverData) {
		this.serverData = serverData;
	}

	public MoveListener(UserInfo serverData) {
		
		this.serverData = serverData;
		this.client = RestClient.builder()
				.baseUrl("http://" + serverData.getIp().getHostAddress() + ":" + String.valueOf(serverData.getPort()))
				.build();
	}
	
	public record Move(	String p1Name, int p1Score, int p1Action, String p2Name, int p2Score, int p2Action) {};
	
	@Override
	public void update(Subject subject) {
		Game game = (Game)subject;
		State state = game.getCurrState();
		Move moveData = new Move(state.getP1Name(), state.getP1Score(), state.getP1Action(), state.getP2Name(), state.getP2Score(), state.getP2Action());

		client.put().uri("/updateMoveList").body(moveData).retrieve().body(String.class);
		
	}
	
	public String toString() {
		return "IP: " + serverData.getIp().getHostAddress() + "\n" +
				"Port: " + serverData.getPort();
	}

}
