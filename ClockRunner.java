// Jenny's File

import java.util.*;
import java.time.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.Month;

public class ClockRunner{
	
	public static void main(String[] args){
		
		Scanner kb = new Scanner(System.in);
		
		// this area calls functions and stuff

		// get time
		// set an alarm
		// check ring 

		AlarmClock mainClock = new AlarmClock();
		mainClock.checkTime();
		
		System.out.println("Want to set an alarm? [Y/N] ");
		String setAlarm = kb.nextLine();
		
		if(setAlarm.equals("Y")){
			mainClock.setAlarm();
		} 
	
		mainClock.checkAlarm();
	}

}
