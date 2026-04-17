package models;

import java.net.InetAddress;

import org.springframework.web.client.RestClient;

public class UserClient {
	InetAddress ip;
	int port;
	RestClient client;
	
	
	
	
	public UserClient(InetAddress ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
		
		this.client = RestClient.builder()
				.baseUrl("http://" + ip.getHostAddress() + ":" + String.valueOf(port)).build();
		
	}
	public InetAddress getIp() {
		return ip;
	}
	public int getPort() {
		return port;
	}
	public RestClient getClient() {
		return client;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setClient(RestClient client) {
		this.client = client;
	}
}
