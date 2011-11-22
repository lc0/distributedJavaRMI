import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Client implements Runnable {
	String ipAddress;
	int port;
	String serverName;
	
	RemoteInterface serverInterface;
	Registry registry;
	
	
	public Client(String ip, int port) {
		this.port = port;
		this.ipAddress = ip;
		serverName = "RMIComputations";		
	}
	
	@Override
	public void run() {
		System.out.println("Inside a thread in client application");		
		try {
			registry = LocateRegistry.getRegistry(ipAddress, port);
			serverInterface = (RemoteInterface)registry.lookup(serverName);
			System.out.println(" [*] Client has started.");
			
			serverInterface.remoteComputations();
			System.out.println(" [*] Computation on client side have been started.");
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to distributing computing with Java.Sockets.");
		System.out.println("Student: Sergii Khomenko.\r\n");
		System.out.println("Client application with task: a=max(MB*MC+MO+ME+MR)\r\n");		
		
		System.out.println("Please enter ip address of the server: ");
		String IP = new Scanner(System.in).nextLine();
		System.out.println("Please enter port of the server: ");
		int port = new Scanner(System.in).nextInt();	
		
		new Thread(new Client(IP, port)).start();
	}	

}
