import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit; //This is what is used for the sleep call for the Snooze
/**
 * Class that creates threads and maps their IDs
 * Can also destroy a thread in the mapping given the ID
 * Properly dismisses and cancels alarm thread upon calling
 * respective "dismiss", "cancel" and "snooze" functions
 * 
 * @author Jeff
 * @edit Aaron Kobelsky - v4.0 Change alarm function
 * @edit Angela Sicat - v5.0 Snooze alarm function
 * @version 5.0
 **/
public class threadSpawner {

	//maps the thread ID with the actual thread
	public HashMap<Long, AlarmThread> threadID;

	//constructor
	public threadSpawner(){
		threadID = new HashMap<>();
	}

	/**
	 * Spawns a new thread to run one alarm clock object
	 *
	 * @edit Aaron Kobelsky - Changed HashCode for storage from ThreadID to AlarmID
	 * @param a		The alarm clock object
	 * @return		The thread ID of the spawned thread
	 */
	public Long spawnNewThread(AlarmClock a){
		AlarmThread newThread = new AlarmThread(a);
		newThread.start();
		//use the alarm's unique ID for storage
		threadID.put(a.getAlarmID(), newThread);

		return a.getAlarmID();
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
	
 	// Angela Sicat: Method called when an alarm is to be snoozed when 'ringing'
	public void snoozeAlarm(Long id) {
		if (getThreadByID(id).alarm.checkAlarm() == false)
			System.out.println("An alarm is not ringing!");
		else
		{

			// Get original alarm time
			int originalAlarmHour = getThreadByID(id).alarm.getInputHour();
			int originalAlarmMinute = getThreadByID(id).alarm.getInputMinute();
			int originalAlarmDay = getThreadByID(id).alarm.getInputDay();
			
			int snoozedAlarmHour = 0;
			int snoozedAlarmMinute = 0;
			
			// Add snooze duration to original alarm time
			if (originalAlarmHour <= 22 && originalAlarmMinute == 59){
				snoozedAlarmHour = originalAlarmHour + 1;
				snoozedAlarmMinute = 0;
				
			}else if (originalAlarmHour == 23 && originalAlarmMinute == 59){
				snoozedAlarmHour = 0;
				snoozedAlarmMinute = 0;
				
			}else if (originalAlarmMinute <= 58){
				snoozedAlarmHour = originalAlarmHour;
				snoozedAlarmMinute = originalAlarmMinute + 1;
				
			}
			
			
			// Make new thread with snoozed alarm time
        	//date variable for constructing AlarmClock Object
            Date date = new Date();
            
			//create a new AlarmClock object
            AlarmClock snoozedAlarm = new AlarmClock(date);
            //set the a new alarm thread with the snoozed time
            snoozedAlarm.setInputHour(snoozedAlarmHour);
            snoozedAlarm.setInputMinute(snoozedAlarmMinute);
            snoozedAlarm.setInputDay(originalAlarmDay);
            String snoozedAlarmLabel = "Snoozed " + getThreadByID(id).alarm.getAlarmLabel() + " ";
            snoozedAlarm.setAlarmLabel(snoozedAlarmLabel);
            
            snoozedAlarm.setAlarmSet(true);
            
            //start the snoozed alarm thread
            Gui.alarms.spawnNewThread(snoozedAlarm);
			
            System.out.println("threadID = " + getThreadByID(id).alarm.getAlarmID());
            System.out.println("The '" + getThreadByID(id).alarm.getAlarmLabel() + "' alarm has been snoozed until " + snoozedAlarmHour + ":" + snoozedAlarmMinute);

			// Stop snoozed alarm thread(s) (Clean up of the loop of snoozed alarms)
            if (getThreadByID(id).alarm.getAlarmLabel().contains("Snoozed")){
            	System.out.println("Dismissing " + getThreadByID(id).alarm.getAlarmLabel() + "to allow the next snooze thread to run");
            	getThreadByID(id).alarm.setAlarmSet(false);
    			this.stopThread(id);	
            }
            
		}
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
			System.out.println("The " + getThreadByID(id).alarm.getAlarmLabel() + " alarm has been dismissed");
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
			System.out.println("The " + getThreadByID(id).alarm.getAlarmLabel() + " alarm has been cancelled");
		}
	}

	/**
	 * Changes the time of an alarm
	 * @param id		ID of the alarm to be changed
	 * @param hour		New hour to be set (put 0 if not changing)
	 * @param minute	New minute to be set (put 0 if not changing)
	 */
	public void changeAlarm(Long id, int hour, int minute){
		boolean changed = false;
		if(hour > 0){
			this.changehour(id, hour);
			changed = true;
		}
		if(minute > 0){
			this.changeminute(id, minute);
			changed = true;
		}
		if(changed == false){
			//Used for testing purposes only
			System.out.println("Alarm has not changed or an error has occured");
		}
	}
	
	private void changehour(Long id, int hour){
		getThreadByID(id).alarm.setInputHour(hour);
	}
	
	private void changeminute(Long id, int minute){
		getThreadByID(id).alarm.setInputMinute(minute);
	}
}
