import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class that creates threads and maps their IDs
 * Can also destroy a thread in the mapping given the ID
 * Version 1.1
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
	}
	
	/**
	 * Gets a list of IDs of all threads
	 * @return The list of IDs
	 */
	public ArrayList<Long> getThreadID(){
		return new ArrayList<Long>(threadID.keySet());
	}
}
