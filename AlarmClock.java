import java.util.*;
import java.time.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;

public class AlarmClock{

	private LocalDate currentDate;
	private LocalTime currentTime;
	
	private int inputHour;
	private int inputMinute;
	
	private boolean alarmSet = false;
	private boolean checkRing = false;

	// constructor

	AlarmClock(){
		currentDate = LocalDate.of(2017,1,1);
		currentTime = LocalTime.of(0, 0); 
	}

	// get the time
	// this returns void, but will eventually return int

	public LocalTime checkTime(){
		currentTime = LocalTime.now();
		System.out.println("Current time: " + currentTime);
		return currentTime;
	}
	
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
		
		if(alarmSet == false){
			System.out.println("An alarm has not been set");
			return checkRing;
		} 
		
		int hour = currentTime.getHour();
		int minute = currentTime.getMinute();
		
		// check if hour and minute is equal to alarm
		if ((hour == inputHour) && (minute == inputMinute)){
			checkRing = true;
			System.out.println("Ring ring ring! Alarm going off! It is " + hour + ":" + minute);
			return checkRing;
		} 
		
		System.out.println("It is not time to ring");
		return checkRing;
	}
	
	// method called when an alarm is to be cancelled, sets alarmSet to false
	public void cancelAlarm() {
		if (alarmSet == false)
			System.out.println("No alarm is set to cancel");
		else
		{
			alarmSet = false;
			inputHour = 0;
			inputMinute = 0;
			System.out.println("The current alarm has been cancelled");
		}
	}



}