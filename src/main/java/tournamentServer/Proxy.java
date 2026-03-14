package tournamentServer;

import java.net.InetAddress;

import tournament.Participant;

public class Proxy extends Participant {

	InetAddress IP;
	String name;
	int port;
	
	@Override
	public int makeChoice(int Actions) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Proxy(InetAddress iP, String name, int port) {
		super();
		IP = iP;
		this.name = name;
		this.port = port;
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
