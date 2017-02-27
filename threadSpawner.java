import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class that creates threads and maps their IDs
 * Can also destroy a thread in the mapping given the ID
 * Properly dismisses and cancels alarm thread upon calling
 * respective "dismiss" and "cancel" functions
 *
 * Version 3.0
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
		AlarmThread at = new AlarmThread(a);
		at.start();
		threadID.put(at.getId(), at);
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
	 * @param ID 	ID of the thread to be retrieved
	 * @return		The AlarmThread with that ID
	 */
	public AlarmThread getThreadByID(Long ID){
		return threadID.get(ID);
	}

	// Angela Sicat: Method called when an alarm is to be dismissed when 'ringing', sets checkAlarm to false
	public void dismissAlarm(Long ID) {
		if (getThreadByID(ID).alarm.checkAlarm() == false)
			System.out.println("An alarm is not ringing!");
		else
		{
			getThreadByID(ID).alarm.setCheckRing(false);
			getThreadByID(ID).alarm.setAlarmSet(false);
			this.stopThread(ID);
			System.out.println("The current alarm has been dismissed");
		}
	}

	// Matteo Molnar: method called when an alarm is to be cancelled, sets alarmSet to false
	public void cancelAlarm(Long ID) {
		if (getThreadByID(ID).alarm.getAlarmSet() == false)
			System.out.println("No alarm is set to cancel");
		else
		{
			getThreadByID(ID).alarm.setAlarmSet(false);
			getThreadByID(ID).alarm.setInputHour(0);
			getThreadByID(ID).alarm.setInputMinute(0);
			this.stopThread(ID);
			System.out.println("The current alarm has been cancelled");
		}
	}
}
