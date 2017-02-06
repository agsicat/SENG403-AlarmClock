import java.util.ArrayList;

/**
 * This is used for testing purposes only
 * The main method should not be called.
 * There should be a separate class/method to create/call a new threadSpawner class
 */
public class mainTestDriver {
	public static void main(String[] args){
		threadSpawner ts = new threadSpawner();
		
		for (int i = 0; i < 4; i++){
			ts.spawnNewThread();
		}
		
		ArrayList<Long> test = ts.getThreadID();
		
		for (int i = 0; i < test.size(); i++){
			ts.stopThread(test.get(i));
		}
	}
}
