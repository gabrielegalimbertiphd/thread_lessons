

class MyRunnable implements Runnable {
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getId());
    }
}

public class Main {
	public static void main(String[] args) {
		System.out.println("prova");
		
		// espressione lambda 
		Runnable task = () -> {
            System.out.println("Thread running: " + Thread.currentThread().getId());
        };
		
        // thread da classe che estende Thread e prevede un metodo run
		MyThreads myThread = new MyThreads();
		myThread.start();  // Avvia il thread
        
		// thread da file main con classe MyRunnable
        Thread thread = new Thread(new MyRunnable());
        thread.start();  // Avvia il thread
        while(true) {
        	thread = new Thread(new MyRunnable());
        	thread.start();  // Avvia il thread
        }
	}
}

