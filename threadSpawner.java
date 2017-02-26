import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class that creates threads and maps their IDs
 * Can also destroy a thread in the mapping given the ID
 * Properly dismisses and cancels alarm thread upon calling 
 * respective "dismiss" and "cancel" functions
 * 
 * Version 3.1
 */
public class threadSpawner {
	
	//maps the thread ID with the actual thread
	public HashMap<Long, AlarmThread> threadID;
	
	//constructor
	public threadSpawner(){
		threadID = new HashMap<>();
	}
	
	/**
	 * Spawns a new thread and puts its ID in the hashmap
	 */
	public void spawnNewThread(AlarmClock a){
		AlarmThread newThread = new AlarmThread(a);
		newThread.start();
		threadID.put(newThread.getId(), newThread);
	}
	
	/**
	 * Stops a thread
	 * @param id	The ID of the thread to be stopped
	 */
	public void stopThread(Long id){
		threadID.get(id).terminate = true;
		threadID.remove(id);
	}
	
	/**
	 * Gets a list of IDs of all threads
	 * @return The list of IDs
	 */
	public ArrayList<Long> getAllThreadID(){
		return new ArrayList<Long>(threadID.keySet());
	}
	
	/**
	 * Retrieves a thread
	 * @param id 	ID of the thread to be retrieved
	 * @return		The AlarmThread with that ID
	 */
	public AlarmThread getThreadByID(Long id){
		return threadID.get(id);
	}
	
	// Angela Sicat: Method called when an alarm is to be dismissed when 'ringing', sets checkAlarm to false
		public void dismissAlarm(Long id) {
			if (getThreadByID(id).alarm.checkAlarm() == false)
				System.out.println("An alarm is not ringing!");
			else
			{
				getThreadByID(id).alarm.setCheckRing(false);
				getThreadByID(id).alarm.setAlarmSet(false);
				this.stopThread(id);
				System.out.println("The current alarm has been dismissed");
			}
		}
	
	// Matteo Molnar: method called when an alarm is to be cancelled, sets alarmSet to false
		public void cancelAlarm(Long id) {
			if (getThreadByID(id).alarm.getAlarmSet() == false)
				System.out.println("No alarm is set to cancel");
			else
			{
				getThreadByID(id).alarm.setAlarmSet(false);
				getThreadByID(id).alarm.setInputHour(0);
				getThreadByID(id).alarm.setInputMinute(0);
				this.stopThread(id);
				System.out.println("The current alarm has been cancelled");
			}
		}
}
