import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is used for testing purposes only
 * The main method should not be called.
 * There should be a separate class/method to create/call a new threadSpawner class
 */
public class mainTestDriver {
	public static void main(String[] args){
		threadSpawner ts = new threadSpawner();
		
		for (int i = 0; i < 1; i++){
			ts.spawnNewThread(new AlarmClock());
		}
		
		ArrayList<Long> test = ts.getAllThreadID();
		
		Scanner kb = new Scanner(System.in);
		
		for (int i = 0; i < test.size(); i++){
			ts.getThreadByID(test.get(i)).alarm.checkAlarm();
			System.out.println("Want to set an alarm? [Y/N] ");
			String setAlarm = kb.nextLine();
			if(setAlarm.equals("Y")){
				ts.getThreadByID(test.get(i)).alarm.setAlarm();
			} 
			ts.getThreadByID(test.get(i)).alarm.checkAlarm();
		}
	}
}
