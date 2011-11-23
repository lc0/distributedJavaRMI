package data;

import java.io.Serializable;

/**
 * A monitor for sharing input data among distributed nodes.
 * 
 */
public class ServerMonitor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 774891327638898212L;
	Matrix MB, MC, MO, ME, MR;
	int N, H;
	long resultMax = Integer.MIN_VALUE;
	int nodesCount;
	int finishedNumber;
	
	public ServerMonitor(int h, int count, Matrix mB, Matrix mC, Matrix mO, Matrix mE,
			Matrix mR) {
		super();
		MB = mB;
		MC = mC;
		MO = mO;
		ME = mE;
		MR = mR;
		
		System.out.println("Result from servermonitor "+ MR.get(2, 2));
		
		nodesCount = count;
		
		N = MB.getLength();
		H = h;
		
		this.finishedNumber = nodesCount;
	}

	public synchronized long getMax() {
		return resultMax;
	}
	public synchronized Matrix getMBH(int number, int size) {
		return MB.getPartMatrix(number*H, size);
	}

	public synchronized Matrix getMC() {
		return MC;
	}

	public synchronized Matrix getMOH(int number, int size) {
		return MO.getPartMatrix(number*H, size);
	}

	public synchronized Matrix getME() {
		return ME;
	}

	public synchronized Matrix getMRH(int number, int size) {
		return MR.getPartMatrix(number*H, size);
	}

	public synchronized int getN() {
		return N;
	}

	public synchronized int getH() {
		return H;
	}
	
	public synchronized void waitCalculationsResult() {
		if (finishedNumber>=0)
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public synchronized void finishCalculation () {
		if (finishedNumber-1>0) {
			finishedNumber--;
			//System.out.println("finishedNumber="+finishedNumber);
		}
		else {
			notify();
			//System.out.println("wake up, Neo! - sm");
		}
	}
	
	public synchronized void setMax(long max) {
		if (max > resultMax)
			this.resultMax = max;
	}
	
	/**
	 * TODO: start calculations 
	 * TODO: calculations has been finished
	 */
	
	

}
