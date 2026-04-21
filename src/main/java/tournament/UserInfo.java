package tournament;

import java.net.InetAddress;

public class UserInfo {
	String ip;
	int port;
	
	public UserInfo(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public String getIp() {
		return ip;
	}
	public int getPort() {
		return port;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) { return true; }
		if(!(o instanceof UserInfo)) { 
			return false; }
		
		UserInfo that = (UserInfo) o;
		boolean n = this.getIp().equals(that.getIp());
		if(this.getPort() == that.getPort() && this.getIp().equals(that.getIp())){
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return "IP: " + this.ip + "\n" +
				"Port: " + this.port;
	}
}
