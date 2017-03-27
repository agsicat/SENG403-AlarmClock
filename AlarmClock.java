import java.util.*;
import java.time.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * Object which acts as an alarm clock.
 * Contains a predetermined time at which it should ring.
 * Provides functionality for whether it is time to ring or not.
 * 
 * @author Jenny Le
 * @edit Aaron Kobelsky, Gave each alarm a unique ID number
 * @edit Aaron Kobelsky, Implemented alarms being given labels
 * @version 3.1
 */
public class AlarmClock{

	private LocalDate currentDate;
	private LocalTime currentTime;
	
	private DismissAlarm d;
	
	private Date alarmTime;
	
	private int inputHour;
	private int inputMinute;
	private int inputDay;
	
	private boolean alarmSet = false;
	private boolean checkRing = false;
	
	private boolean repeatDaily = false;
	private boolean repeatWeekly = false;
	
	//unique identification number for each alarm
	private long alarmID;
	
	//label to identify each alarm
	private String alarmLabel;

	// constructor
	public AlarmClock(Date d){
		alarmTime = d;
		currentDate = LocalDate.of(2017,1,1);
		currentTime = LocalTime.of(0, 0); 
		
		//Initialize the ID to be the unique HashCode of this object
		alarmID = System.identityHashCode(this);
		
		alarmLabel = "Alarm";
	}


	public LocalTime checkTime(){
		currentTime = LocalTime.now();
		System.out.println("Current time: " + currentTime);
		return currentTime;
	}

	// check the alarm
	// see if it rings or not

	public boolean checkAlarm(){
		if(!checkRing){
			if(alarmSet == false){
				//this should be replaced by an exception in later versions
				System.out.println("An alarm has not been set");
				return checkRing;
			} 
			
			currentTime = LocalTime.now(); // update and get current time 
			currentDate = LocalDate.now();
			int hour = currentTime.getHour();
			int minute = currentTime.getMinute();
			int day = 0;
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
			if ((hour == inputHour) && (minute == inputMinute) && (day == inputDay)){
				checkRing = true;
				//spawn the ringing alarm gui, in future will play ringtone as well
				d = new DismissAlarm(alarmID);
				
				//System.out.println("Ring ring ring! Alarm going off! It is " + hour + ":" + minute);
				return checkRing;
			} 
			//do nothing
			//System.out.println("It is not time to ring");
			return checkRing;
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
	
	public void setInputHour(int h){
		this.inputHour = h;
	}

	public int getInputHour(){
		return this.inputHour;
	}

	public int getInputMinute(){
		return this.inputMinute;
	}
	
	public void setInputMinute(int m){
		this.inputMinute = m;
	}
	
	public long getAlarmID(){
		return this.alarmID;
	}
	
	public void setAlarmLabel(String label){
		this.alarmLabel = label;
	}
	
	public String getAlarmLabel(){
		return this.alarmLabel;
	}
	
	public Date getAlarmDate(){
		return this.alarmTime;
	}
	
	public void setRepeatDaily(boolean D){
		repeatDaily = D;
	}
	
	public void setRepeatWeekly(boolean W){
		repeatWeekly = W;
	}
	
	public boolean getRepeatDaily(){
		return this.repeatDaily;
	}
	
	public boolean getRepeatWeekly(){
		return this.repeatWeekly;
	}
	
	public void setInputDay(int D){
		inputDay = D;
	}
	
	public int getInputDay(){
		return this.inputDay;
	}

}

