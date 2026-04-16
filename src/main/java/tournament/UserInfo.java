package tournament;

import java.net.InetAddress;

public class UserInfo {
	InetAddress ip;
	int port;
	
	public UserInfo(InetAddress ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public InetAddress getIp() {
		return ip;
	}
	public int getPort() {
		return port;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) { return true; }
		if(!(o instanceof UserInfo)) { return false; }
		
		UserInfo that = (UserInfo) o;
		
		if(this.getPort() == that.getPort() && this.getIp() == that.getIp()) {
			return true;
		}
		else {
			return false;
		}
	}
}
