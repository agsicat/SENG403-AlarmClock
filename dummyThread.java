
public class dummyThread extends Thread{
	public static int globalID = 1;
	private int ID;
	
	public dummyThread(){
		ID = globalID;
	}
	
	public void start(){
		System.out.println("Dummy thread " + ID + " is starting");
	}
	
	public void run(){
		System.out.println("Dummy thread " + ID + " is running");
	}
}
