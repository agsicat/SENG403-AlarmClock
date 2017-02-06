
public class AlarmThread extends Thread{
	
	public boolean terminate = false;
	public AlarmClock alarm;
	
	public AlarmThread(AlarmClock a){
		alarm = a;
	}
	
	public void run(){
		while(!terminate){
			//call function to check if the alarm should ring
			alarm.checkAlarm();
			
			//wait about 0.1 seconds
			try {
				currentThread().wait(10);
			} catch (InterruptedException e) {
				//Ignore
			}
		}
	}
}
