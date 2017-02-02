
public class threadSpawner {
	public void spawnNewDummyThread(){
		dummyThread dt = new dummyThread();
		dt.start();
		dt.run();
		dummyThread.globalID++;
	}
}
