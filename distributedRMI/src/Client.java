import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

import data.ClientMonitor;
import data.Matrix;
import data.Monitor;


public class Client implements Runnable {	
	int port;
	String serverName;
	ArrayList<ServerNode> nodes;
	
	int N, coresCount, nodesCount;
	Matrix MB, MC, MO, ME, MR;	
	long resultMax = Integer.MIN_VALUE;
		
	
	public Client(int n, ArrayList<ServerNode> dnodes) {		
		serverName = "RMIComputations";		
		nodes = dnodes;
		N = n;
	}

	@Override
	public void run() {
		System.out.println(" [*] Client thread has been started");
		
		coresCount = 0;		
		for (int i = 0; i < nodes.size(); i++) {			
			try {
				Registry registry = LocateRegistry.getRegistry(nodes.get(i).getIpAddress() , nodes.get(i).getPort());
				RemoteInterface serverInterface = (RemoteInterface)registry.lookup(serverName);
				nodes.get(i).setServerLink(serverInterface);
				System.out.println("  [*] A link to server node #" + i + " was established");
				
				int nodeCores = nodes.get(i).getCoresNumber();
				System.out.println("  [*] The server node #" + i + " has " + nodeCores + " cores");
				coresCount += nodeCores;
				
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
		nodesCount = nodes.size();
		
		System.out.println(" [*] System will work with " + nodesCount + " node(s) and " + coresCount + " cores.");
		
		System.out.println(" [+] Filling in MB, MC, MO, ME, MR");
		MB=new Matrix(N); MC=new Matrix(N); MO=new Matrix(N);
		ME=new Matrix(N); MR=new Matrix(N);						
		
		MB.fillWithOnes(); MC.fillWithOnes(); MO.fillWithOnes();
		ME.fillWithOnes(); MR.fillWithOnes();
		//MB.set(100, 1, 1);
		
		System.out.println(" [+] Moving data to distributed nodes");
		/*
		Monitor monitor = new Monitor(MB, MC, MO, ME, MR);
		
		int coresNumber = 4;
		int coresCount = 4;
		int H;
		H = coresCount<N?N/coresCount:1;
		
		for (int i=0; i<nodes.size() ; i++) {
			int nodeCoreSize = nodes.get(i).getCoresNumber();
			Matrix MBH = MB.getPartMatrix(i*H*nodeCoreSize, H*nodeCoreSize);
			Matrix MOH = MO.getPartMatrix(i*H*nodeCoreSize, H*nodeCoreSize);
			Matrix MRH = MR.getPartMatrix(i*H*nodeCoreSize, H*nodeCoreSize);
			
			ClientMonitor clientMonitor = new ClientMonitor(coresNumber); 
			clientMonitor.setN(N);
			clientMonitor.setH(H);
			clientMonitor.setMBH(MBH);
			clientMonitor.setMC(MC);
			clientMonitor.setMOH(MOH);
			clientMonitor.setME(ME);
			clientMonitor.setMRH(MRH);
		}
		*/
		
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
		
		new Thread(new Client(N, nodes)).start();
	}	

}
