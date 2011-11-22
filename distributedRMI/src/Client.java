import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;


public class Client implements Runnable {
	ArrayList<ServerNode> nodes;
	String ipAddress;
	int port;
	String serverName;
		
	
	public Client(ArrayList<ServerNode> dnodes) {		
		serverName = "RMIComputations";		
		nodes = dnodes;
	}

	@Override
	public void run() {
		System.out.println(" [*] Client thread has been started");
		
		for (int i = 0; i < nodes.size(); i++) {
			
			try {
				Registry registry = LocateRegistry.getRegistry(nodes.get(i).getIpAddress() , nodes.get(i).getPort());
				RemoteInterface serverInterface = (RemoteInterface)registry.lookup(serverName);
				nodes.get(i).setServerLink(serverInterface);
				System.out.println("  [*] A link to server node #" + i + " was established");
				
			} catch (RemoteException e) {
				System.out.println("  [-] Some troubles with an inital link to the server node #" + i);
				nodes.remove(i);				
				e.printStackTrace();
			} catch (NotBoundException e) {
				System.out.println("  [-] Some troubles with an inital link to the server node #" + i);
				nodes.remove(i);
				e.printStackTrace();
			}			
		}
		
		
		for (int i = 0; i < nodes.size(); i++) {
			try {
				nodes.get(i).startComputation();
				System.out.println("  [+] Remote computations on the server node #" + i + " has been started");
				
			} catch (RemoteException e) {
				System.out.println("  [-] Some troubles with starting computations on the server node #" + i);
				e.printStackTrace();
			}
		}
		
		System.out.println(" [*] Client thread has been finished");
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to distributing computing with Java.RMI.");
		System.out.println("Student: Sergii Khomenko.\r\n");
		System.out.println("Client application with task: a=max(MB*MC+MO+ME+MR)\r\n");
		
		System.out.println("Please enter number of nodes(with server node): ");
		int nodesNumber = new Scanner(System.in).nextInt();
		System.out.println("Please enter N: ");
		int N = new Scanner(System.in).nextInt();
		
		ArrayList<ServerNode> nodes = new ArrayList<ServerNode>();
		for (int i=0; i<nodesNumber; i++) {		
			System.out.println("Please enter IP address of the server node #" + (i+1) + ": ");
			String IP = new Scanner(System.in).nextLine();
			System.out.println("Please enter port of the server node #" + (i+1) + ": ");
			int port = new Scanner(System.in).nextInt();
			nodes.add(new ServerNode(port, IP));
		}
		
		new Thread(new Client(nodes)).start();
	}	

}
