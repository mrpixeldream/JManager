package net.dreamcode.jmanager;

import java.io.IOException;

import net.dreamcode.jmanager.connection.SSHCredentials;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;

public class JManager {

	private SSHClient client;
	private SSHCredentials credentials;
	private Session sshSession;
	
	public JManager(SSHCredentials credentials) {
		this.credentials = credentials;
		try {
			this.client = new SSHClient();
			
			this.client.loadKnownHosts();
			this.client.connect(this.credentials.getHost(), this.credentials.getPort());
			this.client.authPassword(this.credentials.getUser(), this.credentials.getPassword());
			
			this.sshSession = this.client.startSession();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public int updatesAvailable() {
		try {
			final Command updateCommand = this.sshSession.exec("apt-get upgrade -s");
			String result = IOUtils.readFully(updateCommand.getInputStream()).toString();
			String[] content = result.split(" ");
			
			for (int i = 0; i < content.length; i++) {
				if (content[i].equalsIgnoreCase("aktualisiert,") || content[i].equalsIgnoreCase("upgraded,")) {
					String strCount = content[i - 1];
					if (strCount.contains("\r\n")) {
						strCount = strCount.split("\r\n")[1];
					}
					if (strCount.contains("\n")) {
						strCount = strCount.split("\n")[1];
					}
					
					int updateCount = Integer.parseInt(strCount);
					return updateCount;
				}
			}
			return -1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void disconnect() {
		try {
			this.client.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SSHCredentials credentials = new SSHCredentials("213.136.79.133", "root", "Gummiball13!Pixel", 222);
		JManager srv1Manager = new JManager(credentials);
		System.out.println(srv1Manager.updatesAvailable());
		srv1Manager.disconnect();
	}
	
}
