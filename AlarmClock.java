import java.util.*;
import java.time.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;

// Jenny Le: Implemented AlarmClock
// Edited By: Aaron Kobelsky
public class AlarmClock{

	private LocalDate currentDate;
	private LocalTime currentTime;
	
	private DismissAlarm d;
	
	private int inputHour;
	private int inputMinute;
	
	private boolean alarmSet = false;
	private boolean checkRing = false;

	// constructor

	AlarmClock(){
		currentDate = LocalDate.of(2017,1,1);
		currentTime = LocalTime.of(0, 0); 
	}


	public LocalTime checkTime(){
		currentTime = LocalTime.now();
		System.out.println("Current time: " + currentTime);
		return currentTime;
	}
	
	@Deprecated
	/**
	 * No longer needed after sprint 2
	 */
	public void setAlarm(){
		
		alarmSet = true;
	
		Scanner kb = new Scanner(System.in);
	
		System.out.print("Hour of the alarm (24h time please): ");
		inputHour = kb.nextInt();
		System.out.print("Minute of the alarm: ");
		inputMinute = kb.nextInt();
		
		System.out.println("Alarm has been set for " + inputHour + ":" + inputMinute);
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
			int hour = currentTime.getHour();
			int minute = currentTime.getMinute();
			
			// check if hour and minute is equal to alarm
			if ((hour == inputHour) && (minute == inputMinute)){
				checkRing = true;
				//spawn the ringing alarm gui, in future will play ringtone as well
				d = new DismissAlarm();
				
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
	
	public void setInputMinute(int m){
		this.inputMinute = m;
	}

}
