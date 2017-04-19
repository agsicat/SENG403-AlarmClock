package alarmClockThreads;
import java.util.ArrayList;
import java.util.HashMap;

import alarmGUIs.Gui;
/**
 * Class that creates threads and maps their IDs
 * Can also destroy a thread in the mapping given the ID
 * Properly dismisses and cancels alarm thread upon calling
 * respective "dismiss", "cancel" and "snooze" functions
 * 
 * @Author Jeff
 * @Edit Aaron Kobelsky - v4.0 Change alarm function
 * @Edit Angela Sicat - v5.0 Snooze alarm function
 * @Edit Angela Sicat - v5.1 Cleaned up Snooze and Dismiss function
 * @Cersion 5.1
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
			// Stop original alarm as "ringing"
			getThreadByID(id).alarm.setCheckRing(false);
			
			// Initialize original alarm variables by getting original alarm information to use for the Snoozed Alarm
			long originalAlarmID = getThreadByID(id).alarm.getAlarmID();
			int originalAlarmHour = getThreadByID(id).alarm.getAlarmHour();
			int originalAlarmMinute = getThreadByID(id).alarm.getAlarmMinute();
			HashMap<Integer, Boolean> originalAlarmDay = getThreadByID(id).alarm.getAlarmDays();
			boolean daily = getThreadByID(id).alarm.getRepeatDaily();
			boolean weekly = getThreadByID(id).alarm.getRepeatWeekly();
			String originalAlarmLabel = getThreadByID(id).alarm.getAlarmLabel();
			
			// Initialize the variables for manipulating the Snoozed Alarm of an Original Alarm
			int snoozedAlarmHour = 0;
			int snoozedAlarmMinute = 0;
			String snoozedAlarmLabel = "Snoozed " + originalAlarmLabel;
			
			// Add snooze duration to original alarm time
			// This case handles when the time is one minute before a new hour
			if (originalAlarmHour <= 22 && originalAlarmMinute == 59){
				snoozedAlarmHour = originalAlarmHour + 1;
				snoozedAlarmMinute = 0;
			// This case handles when the time is exactly the minute before 12-Midnight
			}else if (originalAlarmHour == 23 && originalAlarmMinute == 59){
				snoozedAlarmHour = 0;
				snoozedAlarmMinute = 0;	
			// This case handles when the time is any other time that is not the case above
			}else if (originalAlarmMinute <= 58){
				snoozedAlarmHour = originalAlarmHour;
				snoozedAlarmMinute = originalAlarmMinute + 1;
			}
			
			// If the alarm is a Daily Repeating or Weekly Repeating from the boolean value of the variables
			//   Then Keep Original Alarm in the Alarms List
			// 	 And Create & Save Snooze Alarm(s) in the Alarms list
			if(daily || weekly){
				// Flag to ensure the repeating alarm is still accessible
				getThreadByID(id).alarm.setRepeatDismiss(true);
				
				// Make new thread with snoozed alarm time      
				// create a new AlarmClock object
	            AlarmClock snoozedAlarm = new AlarmClock(snoozedAlarmHour, snoozedAlarmMinute, originalAlarmDay, daily, weekly, snoozedAlarmLabel);
	            snoozedAlarm.setAlarmSet(true);
	            
	            // start the snoozed alarm thread
	            Gui.alarms.spawnNewThread(snoozedAlarm);
	            
	            System.out.println("original alarm threadID = " + originalAlarmID);
	            System.out.println("snoozed alarm threadID = " + snoozedAlarm.getAlarmID());
	            Gui.alarmList.addNewElement(snoozedAlarm.getAlarmID());
				
	            // System message to know what has been changed for Snooze Alarm
	            System.out.println("The '" + originalAlarmLabel + "' alarm has been snoozed until " + snoozedAlarmHour + ":" + snoozedAlarmMinute);

	        // Else - the alarm is not repeating
	        //  Then Create & Save Snooze Alarm(s) in the Alarms list
	        //  And Dismiss (remove) Original Alarm from the Alarms List
			}else {
				// Make new thread with snoozed alarm time      
				// create a new AlarmClock object
	            AlarmClock snoozedAlarm = new AlarmClock(snoozedAlarmHour, snoozedAlarmMinute, originalAlarmDay, daily, weekly, snoozedAlarmLabel);
	            snoozedAlarm.setAlarmSet(true);
	            
	            // start the snoozed alarm thread
	            Gui.alarms.spawnNewThread(snoozedAlarm);
	            
	            System.out.println("original alarm threadID = " + originalAlarmID);
	            System.out.println("snoozed alarm threadID = " + snoozedAlarm.getAlarmID());
	            Gui.alarmList.addNewElement(snoozedAlarm.getAlarmID());
				
	            // System message to know what has been changed for Snooze Alarm
	            System.out.println("The '" + originalAlarmLabel + "' alarm has been snoozed until " + snoozedAlarmHour + ":" + snoozedAlarmMinute);
				
				// Dismiss (remove) Original Alarm from the Alarms List
				Gui.alarmList.removeElementByID(originalAlarmID);	
			}
			
			// Stop snoozed alarm thread(s) (Clean up of the loop of snoozed alarms)
            if (getThreadByID(id).alarm.getAlarmLabel().contains("Snoozed")){
            	System.out.println("Dismissing '" + getThreadByID(id).alarm.getAlarmLabel() + "' to allow the next snooze thread to run");
            	getThreadByID(id).alarm.setAlarmSet(false);
				Gui.alarmList.removeElementByID(id);
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
			if(getThreadByID(id).alarm.getRepeatDaily() || getThreadByID(id).alarm.getRepeatWeekly()){
				getThreadByID(id).alarm.setRepeatDismiss(true);
				getThreadByID(id).alarm.setCheckRing(false);
				
	            if (getThreadByID(id).alarm.getAlarmLabel().contains("Snoozed")){
	            	getThreadByID(id).alarm.setAlarmSet(false);
					Gui.alarmList.removeElementByID(id);
	    			this.stopThread(id);
	            }
				
			}
			else{
				getThreadByID(id).alarm.setCheckRing(false);
				getThreadByID(id).alarm.setAlarmSet(false);
				Gui.alarmList.removeElementByID(id);
				this.stopThread(id);
			}
		}
	}

	// Matteo Molnar: Method called when an alarm is to be cancelled, sets alarmSet to false
	public void cancelAlarm(Long id) {
		if (getThreadByID(id).alarm.getAlarmSet() == false)
			System.out.println("No alarm is set to cancel");
		else
		{
			getThreadByID(id).alarm.setAlarmSet(false);
			this.stopThread(id);
		}
	}
}
