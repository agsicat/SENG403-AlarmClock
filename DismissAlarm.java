public class DismissAlarm {

		private Time dismissTime;

		public void dismiss() {
				while (checkAlarm() == true) {
						//dismisses 'ringing' alarm
						checkRing = false;
						
						//more to do here to turn on alarm again if it is a recurring one?
				}
		
		//To implement Snooze in the future - this will need to modified to what Jenny has
		//Will probably turn into something separate from Dismiss
		public void snooze() {
		/*			{alarm.off();
						int hour = currentTime.getHour();
						int minute = currentTime.getMinute();
						minute += SNOOZE_DURATION_IN_MINUTES;
								if (minute > 59) {
										minute -= 60;
										++hour;
						}
						snoozeTime = new Time(hour, minute);
		*/
		}
		
}
