package alarmStartUpHelpers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import alarmClockThreads.AlarmClock;
import alarmGUIs.Gui;

/**
 * Reads in a saved alarms file and reconstructs alarms
 * @author Jeffery
 * @version 0.9.1
 */
public class AlarmsStartupReader {
	
	/**
	 * Nested class to store saved variables
	 * Don't touch the clone method
	 */
	private class SavedVariables implements Cloneable{
		private int hour = 0;
		private int minute = 0;
		private String label = "alarm";
		private HashMap<Integer, Boolean> dayOfWeek = new HashMap<Integer, Boolean>();
		private boolean repeatDaily = false;
		private boolean repeatWeekly = false;
		
		public SavedVariables clone() throws CloneNotSupportedException{
			return (SavedVariables) super.clone();
		}	
	}
	
	//Number of alarms
	private int numberOfAlarms = 0;
	
	//List of blocks of variables
	private ArrayList<SavedVariables> savedVars = new ArrayList<>();
	
	/**
	 * Reads in a file and parses for data members
	 * Calls a helper function to reconstruct alarms after reaching 
	 * the end of the file
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	public void readAndReconstructAlarms() throws IOException, CloneNotSupportedException{
		File f = new File("alarms.txt");
		if(!f.exists())
			return;
		BufferedReader in = new BufferedReader(new FileReader(f));
		int dataMember = 0;
		SavedVariables saved = new SavedVariables();
		while(true){
			String line = in.readLine();
			if(line.equals("EOF")){
				constructAlarms();
				in.close();
				break;
			}
			if(dataMember == 0){
				saved.hour = Integer.parseInt(line);
				//System.out.println(saved.hour);
				dataMember++;
			}
			else if(dataMember == 1){
				saved.minute = Integer.parseInt(line);
				//System.out.println(saved.minute);
				dataMember++;
			}
			else if(dataMember == 2){
				saved.label = line;
				//System.out.println(saved.label);
				dataMember++;
			}
			else if(dataMember == 3){
				if(Integer.parseInt(line) == 1){
					saved.repeatDaily = true;
				}
				//System.out.println(saved.repeatDaily);
				dataMember++;
			}
			else if(dataMember == 4){
				if(Integer.parseInt(line) == 1){
					saved.repeatWeekly = true;
				}
				//System.out.println(saved.repeatWeekly);
				dataMember++;
			}
			else if(dataMember > 4){
				if(line.equals("EOA")){
					SavedVariables temp = saved.clone();
					savedVars.add(temp);
					saved = new SavedVariables();
					numberOfAlarms++;
					dataMember = 0;
					continue;
				}
				saved.dayOfWeek.put(Integer.parseInt(line), true);
				//System.out.println(saved.dayOfWeek);
				dataMember++;
			}
		}
	}
	
	/**
	 * Helper function to reconstuct each alarm
	 */
	private void constructAlarms(){
		for(int i = 0; i < numberOfAlarms; i++){
			AlarmClock a = new AlarmClock(savedVars.get(i).hour, savedVars.get(i).minute, savedVars.get(i).dayOfWeek, savedVars.get(i).repeatDaily, savedVars.get(i).repeatWeekly, savedVars.get(i).label);
            a.setAlarmSet(true);
            Gui.alarms.spawnNewThread(a);
            Gui.alarmList.addNewElement(a.getAlarmID());
		}
	}
}
