import java.util.*;
public class Time {
	public static void main(String[] args)
	{
		int second = 0;
		String AMPM,
			temp;
		for(;;)
		{
			Calendar time = new GregorianCalendar();
		
			if (second != time.get(Calendar.SECOND))
			{
				if(time.get(Calendar.AM_PM) == 1)
				{
					AMPM = "PM";
				}
				else{
					AMPM = "AM";
				}	
				
				
			temp = time.get(Calendar.HOUR) + ":" + time.get(Calendar.MINUTE) + ":" +time.get(Calendar.SECOND) + AMPM;
			printTime(temp);
			second = time.get(Calendar.SECOND);			
			}
		}
	}
	
	public static void printTime(String time)
	{
		System.out.println(time);
	}
}
