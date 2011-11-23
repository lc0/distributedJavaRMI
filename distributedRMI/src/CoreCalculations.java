import data.ClientMonitor;
import data.Matrix;



public class CoreCalculations implements Runnable {
	
	private ClientMonitor clientMonitor;
	private int coreId;
	private int coreH;
	private int N;

	public CoreCalculations(ClientMonitor clientMonitor, int coreId) {
		super();
		this.clientMonitor = clientMonitor;
		this.coreId = coreId;
	}

	@Override
	public void run() {
		System.out.println("   [*] Preparing data on core #" + coreId + ".");		
		Matrix MBH = clientMonitor.getMBH(coreId);
		Matrix MC = clientMonitor.getMC();
		Matrix MOH = clientMonitor.getMOH(coreId);
		Matrix ME = clientMonitor.getME();
		Matrix MRH = clientMonitor.getMRH(coreId);
		N = clientMonitor.getN();
		coreH = clientMonitor.getH();
		
		int coreMax = Integer.MIN_VALUE;
		int tval;
				
		System.out.println("   [*] Starting calculations on core #" + coreId + ".");
		for (int i=0; i<coreH; i++) {
			for (int j=0; j<N; j++) {
				tval=0;
				for (int v=0; v<N; v++) {
					tval += MBH.get(i, v)*MC.get(j, v) + MOH.get(i, v) * ME.get(j, v);
				}
				tval+=MRH.get(i, j); //TODO:bug
				//if (i==2 && j==2)
					//System.out.println("tval="+tval+"MRH="+MRH.get(i, j));
				if (tval>coreMax) {
					coreMax = tval;
					//System.out.println("coreMax="+coreMax);
				}
			}
		}
		
		if (coreMax>clientMonitor.getMax()) 
			clientMonitor.setMax(coreMax);		
		clientMonitor.finishCalculation();
		System.out.println("   [*] Calculations have been finished on core #" + coreId + ".");		
		
	}

}
