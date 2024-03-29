package data;

public class ClientMonitor {
	public ClientMonitor(int coresNumber) {
		super();
		this.finishedNumber = coresNumber;
	}
	private int N;
	private int H;
	private Matrix MBH, MC, MOH, ME, MRH;
	private long resultMax = Integer.MIN_VALUE;
	private int finishedNumber;
	
	public synchronized void waitCalculationsResult() {
		if (finishedNumber>0)
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
		}
		else {
			notify();
			//System.out.println("wake up, Neo! - cm");
		}
	}		
	
	public synchronized int getN() {
		return N;
	}
	public synchronized void setN(int n) {
		N = n;
	}
	public synchronized int getH() {
		return H;
	}
	public synchronized void setH(int h) {
		H = h;
	}
	public synchronized Matrix getMBH(int coreId) {
		return MBH.getPartMatrix(coreId*H, H);
	}
	public synchronized void setMBH(Matrix mBH) {
		MBH = mBH;
	}
	public synchronized Matrix getMC() {
		return MC;
	}
	public synchronized void setMC(Matrix mC) {
		MC = mC;
	}
	public synchronized Matrix getMOH(int coreId) {
		return MOH.getPartMatrix(coreId*H, H);
	}
	public synchronized void setMOH(Matrix mOH) {
		MOH = mOH;
	}
	public synchronized Matrix getME() {
		return ME;
	}
	public synchronized void setME(Matrix mE) {
		ME = mE;
	}
	public synchronized Matrix getMRH(int coreId) {
		return MRH.getPartMatrix(coreId*H, H);
	}
	public synchronized void setMRH(Matrix mRH) {
		MRH = mRH;
	}
	public synchronized long getMax() {
		return resultMax;
	}
	public synchronized void setMax(long max) {
		if (max > resultMax)
			this.resultMax = max;
	}	
}
