
public class mainTestDriver {
	public static void main(String[] args){
		threadSpawner ts = new threadSpawner();
		
		for (int i = 0; i < 4; i++){
			ts.spawnNewDummyThread();
		}
	}
}
