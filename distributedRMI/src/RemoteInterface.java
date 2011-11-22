import java.rmi.*;

public interface RemoteInterface extends Remote {
	
	int remoteComputations ()  throws RemoteException;

}
