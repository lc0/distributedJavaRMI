import java.rmi.AccessException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class Server implements Runnable, RemoteInterface {
	int N;
	String serverIp;
	int port;
	String serverName;
	
	
	public Server(int n, int port) {
		this.N = n;		
		this.port = port;
		this.serverName = "RMIComputations";	
	}
	
	@Override
	public void run() {
		System.out.println(" [*] Inside server thread.");
		
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
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to distributing computing with Java.Sockets.");
		System.out.println("Student: Sergii Khomenko.\r\n");
		System.out.println("Server application with task: a=max(MB*MC+MO+ME+MR)\r\n");
		
		System.out.println("Please enter port of the server: ");
		int port = new Scanner(System.in).nextInt();		
		System.out.println("Please enter number of nodes(with server node): ");
		int nodes = new Scanner(System.in).nextInt();
		System.out.println("Please enter N: ");
		int N = new Scanner(System.in).nextInt();
		
		new Thread(new Server(N, port)).start();

	}

	@Override
	public int remoteComputations() throws RemoteException {
		System.out.println("inside computations");
		return 0;
	}
}
