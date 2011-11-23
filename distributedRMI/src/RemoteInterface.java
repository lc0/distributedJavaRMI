import java.rmi.*;

import data.ServerMonitor;

public interface RemoteInterface extends Remote {
	
	long remoteComputations (ServerMonitor monitor) throws RemoteException;
	int getCoresNumber () throws RemoteException;
	void setNodeId (int id, int offset) throws RemoteException;

}
