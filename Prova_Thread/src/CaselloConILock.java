
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CaselloConILock {
	private final Lock[] porte;
	private double incasso = 0; // VARIABILE CONDIVISA
	private final double tariffa;
	
	private int pagamenti[] = {0,0,0,0,0};
	
	public CaselloConILock(int numeroPorte, double tariffa) {
		System.out.println("Creo il casello");
		this.tariffa = tariffa;
		this.porte = new ReentrantLock[numeroPorte];
		for(int i = 0; i<numeroPorte; i++) {
			this.porte[i]=new ReentrantLock();
		}
	}
	
	public void processaVeicolo(int chilometri) {
		int indicePorta = new Random().nextInt(porte.length);
		System.out.println("Veicolo si accoda a porta "+String.valueOf(indicePorta));
		
		try {
			porte[indicePorta].lock();
			synchronized(this) {
				pagamenti[indicePorta]=1;
				printPagamenti();
			}
			System.out.println("Veicolo prende la porta "+String.valueOf(indicePorta));
			double pedaggio = chilometri*tariffa;
			Thread.sleep(new Random().nextInt(4)+3);
			synchronized(this) {
				System.out.println("Veicolo a porta "+String.valueOf(indicePorta)+" paga");
				incasso+=pedaggio;
				pagamenti[indicePorta]=0;
				printPagamenti();
			}
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			System.out.println("Veicolo lascia la porta "+String.valueOf(indicePorta));
			porte[indicePorta].unlock();
		}
		System.out.println("Incasso ammonta a "+String.valueOf(incasso));
	}
	
	public synchronized double getIncasso() {
		return incasso;
	}
	
	public synchronized void printPagamenti() {
		System.out.println();
		for(int i = 0; i<porte.length; i++) {
			System.out.print(String.valueOf(pagamenti[i])+" ");
		}
		System.out.println();
		System.out.println();
	}
}