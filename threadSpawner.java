import java.util.ArrayList;
import java.util.HashMap;
/**
 * Class that creates threads and maps their IDs
 * Can also destroy a thread in the mapping given the ID
 * Version 1.0
 */
public class threadSpawner {
	
	//maps the thread ID with the actual thread
	private HashMap<Long, dummyThread> threadID;
	
	//constructor
	public threadSpawner(){
		threadID = new HashMap<>();
	}
	
	/**
	 * Spawns a new thread and puts its ID in the hashmap
	 */
	//TODO: Change the name to spawn actual thread
	public void spawnNewThread(){
		dummyThread dt = new dummyThread();
		dt.start();
		threadID.put(dt.getId(), dt);
	}
	
	/**
	 * Stops a thread
	 * @param id	The ID of the thread to be stopped
	 */
	public void stopThread(Long id){
		threadID.get(id).shutdown = true;
	}
	
	/**
	 * Gets a list of IDs of all threads
	 * @return The list of IDs
	 */
	public ArrayList<Long> getThreadID(){
		return new ArrayList<Long>(threadID.keySet());
	}
}
