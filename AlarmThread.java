/**
 * Thread which contains an alarm clock object and checks to see if it is time for the alarm to ring
 * once per second
 * 
 * @author Aaron Kobelsky
 * @version 1.3
 */
public class AlarmThread extends Thread{
	
	//boolean used to terminate the infinite loop inside the run method
	public boolean terminate = false;
	
	//the alarm clock the thread makes use of
	public AlarmClock alarm;
	
	/**
	 * Constructor for AlarmThread
	 * @param a Alarm clock for the thread to make use of
	 */
	public AlarmThread(AlarmClock a){
		alarm = a;
	}
	
	/**
	 * Run function checks every second if it is time for the alarm to "ring"
	 */
	public void run(){
		
		//while not terminated
		while(!terminate){
			//call function to check if the alarm should ring
			alarm.checkAlarm();
			
			//wait about 0.1 seconds (any faster than this is a waste of resources)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				//Ignore
			}
		}
	}
}
