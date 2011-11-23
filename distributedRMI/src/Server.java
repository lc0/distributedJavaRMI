import java.rmi.AccessException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import data.ClientMonitor;
import data.ServerMonitor;


public class Server implements Runnable, RemoteInterface {	
	int port;
	String serverName;
	int coresNumber;
	int nodeId;
	int coresOffset;
	
	
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
	public long remoteComputations(ServerMonitor smonitor) throws RemoteException {
		System.out.println(" [*] RMI method on the server node" + nodeId + " has been started");
		int H = smonitor.getH();
		
		System.out.println("   [*] Sharing data among cores");		
		ClientMonitor cmonitor = new ClientMonitor(coresNumber);
		cmonitor.setN(smonitor.getN());
		cmonitor.setH(smonitor.getH());
		cmonitor.setMBH(smonitor.getMBH(coresOffset, coresNumber*H));
		cmonitor.setMC(smonitor.getMC());
		cmonitor.setMOH(smonitor.getMOH(nodeId, coresNumber*H));
		cmonitor.setME(smonitor.getME());
		cmonitor.setMRH(smonitor.getMBH(nodeId, coresNumber*H));
		
		for (int i = 0; i < coresNumber; i++) {
			new Thread(new CoreCalculations(cmonitor, i)).start();
		}
				
		cmonitor.waitCalculationsResult();				
		
		System.out.println(" [*] RMI method on the server node" + nodeId + " has been finished");
		return cmonitor.getMax();
	}

	@Override
	public int getCoresNumber() throws RemoteException {
		coresNumber = Runtime.getRuntime().availableProcessors();
		System.out.println(" [*] Information about cores has been sended");
		return coresNumber;
	}

	@Override
	public void setNodeId(int id, int offset) throws RemoteException {
		this.nodeId = id;
		this.coresOffset = offset;
		
	}
}
