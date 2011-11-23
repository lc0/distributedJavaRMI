import java.rmi.AccessException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class Server implements Runnable, RemoteInterface {	
	int port;
	String serverName;
	int coresNumber;
	
	
	public Server(int port) {				
		this.port = port;
		this.serverName = "RMIComputations";	
	}
	
	@Override
	public void run() {
		System.out.println(" [*] Client thread has been started");
		
		try {
			RemoteInterface serverImp = (RemoteInterface)UnicastRemoteObject.exportObject(this,0);
			//RemoteInterface  serverImp = new RemoteInterface();
			Registry registry = LocateRegistry.createRegistry(port);
			
			registry.rebind(serverName, serverImp);			
			System.out.println(" [*] Server with RMIRegistry has started.");
			
			
			
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(" [*] Client thread has been finished");
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to distributing computing with Java.RMI.");
		System.out.println("Student: Sergii Khomenko.\r\n");
		System.out.println("Server application with task: a=max(MB*MC+MO*ME+MR)\r\n");
		
		System.out.println("Please enter port of the server: ");
		int port = new Scanner(System.in).nextInt();		
		
		
		new Thread(new Server(port)).start();

	}

	@Override
	public int remoteComputations() throws RemoteException {
		System.out.println("  [~] Stub for remote computations on the server node");
		return 0;
	}

	@Override
	public int getCoresNumber() throws RemoteException {
		coresNumber = Runtime.getRuntime().availableProcessors();
		System.out.println(" [*] Information about cores has been sended");
		return coresNumber;
	}
}
