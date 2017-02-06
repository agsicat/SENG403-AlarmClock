
public class AlarmThread extends Thread{
	
	public boolean terminate = false;
	//public JennyAlarm alarm;
	
	public void run(){
		while(!terminate){
			//call function to check if the alarm should ring
			
			//wait about 0.1 seconds
			try {
				currentThread().wait(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
