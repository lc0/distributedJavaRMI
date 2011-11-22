import java.rmi.RemoteException;


public class ServerNode {
	int port;
	String ipAddress;
	
	RemoteInterface serverLink;

	public ServerNode(int port, String ipAddress) {
		super();
		this.port = port;
		this.ipAddress = ipAddress;
	}

	public int getPort() {
		return port;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setServerLink(RemoteInterface serverLink) {
		this.serverLink = serverLink;
	}
	
	public void startComputation () throws RemoteException {
		this.serverLink.remoteComputations();
	
	}
}