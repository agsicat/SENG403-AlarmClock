/**
 * This is a dummy thread that does nothing
 * Used for testing purposes only
 *
 */
public class dummyThread extends Thread{
	private Thread t;
	public boolean shutdown = false;
	
	public void start(){
		t = new Thread(this);
		t.start();
	}
	
	public void run(){
		System.out.println("Dummy thread " + this.getId() + " is running");
		while(!shutdown){
			//do stuff
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("Dummy thread " + this.getId() + " is shutting down");
	}
}
