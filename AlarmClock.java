package alarmClockThreads;
import java.util.*;

import alarmGUIs.DismissAlarm;

import java.time.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Object which acts as an alarm clock.
 * Contains a predetermined time at which it should ring.
 * Provides functionality for whether it is time to ring or not.
 * 
 * @author Jenny Le
 * @edit Aaron Kobelsky, Gave each alarm a unique ID number
 * @edit Aaron Kobelsky, Implemented alarms being given labels
 * @Edit Aaron Kobelsky, Removed dead code and implemented weekly repeating alarms being set for multiple days
 * @version 3.2
 */
public class AlarmClock{

	//hour and minute at which the alarm is set to ring
	private int alarmHour;
	private int alarmMinute;
	//days for which a weekly repaeting alarm will ring
	private HashMap<Integer, Boolean> alarmDays;
	
	//booleans for error checking
	private boolean alarmSet = false;
	private boolean checkRing = false;
	
	//booleans to represent when an alarm should repeat daily or weekly
	private boolean repeatDaily = false;
	private boolean repeatWeekly = false;
	
	//unique identification number for each alarm
	private long alarmID;
	
	//label to identify each alarm
	private String alarmLabel;
	
	//flag used to manage dismissal of repeating alarms
	private boolean repeatDismiss = false;

	/**
	 * Constructor for the alarm class
	 * Any method calling this constructor must follow the following data constraints
	 * @param hour integer between 0-23 representing the hour at which the alarm will ring
	 * @param min integer between 0-59 representing the minute at which the alarm will ring
	 * @param days HashMap<Integer, Boolean> containing keys 1-7, the boolean associated with each indicating whether the alarm should ring that day of the week
	 * @param daily boolean to indicate whether the alarm should repeat daily
	 * @param weekly boolean to indicate whether the alarm should repeat weekly
	 * No alarm can be both daily and weekly repeating
	 * @param label String to serve as a label for the alarm
	 */
	public AlarmClock(int hour, int min, HashMap<Integer, Boolean> days, boolean daily, boolean weekly, String label){
		//Initialize the ID to be the unique HashCode of this object
		alarmID = System.identityHashCode(this);
		
		//initialize the alarm variables
		alarmLabel = label;
		alarmHour = hour;
		alarmMinute = min;
		alarmDays = days;
		repeatDaily = daily;
		repeatWeekly = weekly;
	}

	// check the alarm
	// see if it rings or not
	public boolean checkAlarm(){
		if(!repeatDismiss){
			if(!checkRing){
				if(alarmSet == false){
					//this should be replaced by an exception in later versions
					System.out.println("An alarm has not been set");
					return checkRing;
				} 
				
				LocalTime currentTime = LocalTime.now(); // update and get current time 
				LocalDate currentDate = LocalDate.now();
				int hour = currentTime.getHour();
				int minute = currentTime.getMinute();
				int day = 0;
				//get the current day as an int
				if(currentDate.getDayOfWeek() == DayOfWeek.MONDAY){
					day = 1;
				}
				else if(currentDate.getDayOfWeek() == DayOfWeek.TUESDAY){
					day = 2;
				}
				else if(currentDate.getDayOfWeek() == DayOfWeek.WEDNESDAY){
					day = 3;
				}
				else if(currentDate.getDayOfWeek() == DayOfWeek.THURSDAY){
					day = 4;
				}
				else if(currentDate.getDayOfWeek() == DayOfWeek.FRIDAY){
					day = 5;
				}
				else if(currentDate.getDayOfWeek() == DayOfWeek.SATURDAY){
					day = 6;
				}
				else if(currentDate.getDayOfWeek() == DayOfWeek.SUNDAY){
					day = 7;
				}
				//error check
				if(day == 0){
					return false;
				}
				
				// check if hour and minute is equal to alarm
				if(!repeatDaily){
					if ((hour == alarmHour) && (minute == alarmMinute) && (alarmDays.containsKey(day) && alarmDays.get(day))){
						checkRing = true;
						//spawn the ringing alarm gui
						new DismissAlarm(alarmID);
						
						return checkRing;
					} 
				}
				else{
					if ((hour == alarmHour) && (minute == alarmMinute)){
						checkRing = true;
						//spawn the ringing alarm gui
						new DismissAlarm(alarmID);
						
						return checkRing;
					} 
				}
			}
		}
		else{
			LocalTime currentTime = LocalTime.now(); // update and get current time 
			int hour = currentTime.getHour();
			int minute = currentTime.getMinute();
			if ((hour != alarmHour) || (minute != alarmMinute)){
				repeatDismiss = false;
			}
		}
		return checkRing;
	}
	
	//Getter and setter functions
	public boolean getAlarmSet(){
		return alarmSet;
	}
	
	public void setCheckRing(boolean b){
		this.checkRing = b;
	}
	
	public void setAlarmSet(boolean b){
		this.alarmSet = b;
	}

	public int getAlarmHour(){
		return this.alarmHour;
	}

	public int getAlarmMinute(){
		return this.alarmMinute;
	}
	
	public long getAlarmID(){
		return this.alarmID;
	}
	
	public String getAlarmLabel(){
		return this.alarmLabel;
	}
	
	public boolean getRepeatDaily(){
		return this.repeatDaily;
	}
	
	public boolean getRepeatWeekly(){
		return this.repeatWeekly;
	}
	
	public HashMap<Integer, Boolean> getAlarmDays(){
		return this.alarmDays;
	}
	
	public void setRepeatDismiss(boolean b){
		this.repeatDismiss = b;
	}

}

