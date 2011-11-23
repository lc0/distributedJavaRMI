import java.rmi.RemoteException;

import data.ServerMonitor;


public class ServerNode implements Runnable {
	int port;
	String ipAddress;
	int coresNumber;	
	ServerMonitor monitor;

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

	public int getCoresNumber() throws RemoteException {
		coresNumber = serverLink.getCoresNumber();
		return coresNumber;
	}
	public int getCoresNumberLocal() {
		return coresNumber;
	}

	public void startComputation(ServerMonitor smonitor) throws RemoteException {
		monitor = smonitor;  
		new Thread(this).start();
		
	}

	@Override
	public void run() {
		try {
			monitor.setMax(serverLink.remoteComputations(monitor));
			monitor.finishCalculation();
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
