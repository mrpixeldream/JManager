package net.dreamcode.jmanager.connection;

public class SSHCredentials {
	
	private String host;
	private String user;
	private String password;
	private int port;
	
	public SSHCredentials(String host, String user, String password, int port) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
