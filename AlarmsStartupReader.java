import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * Reads in a saved alarms file and reconstructs alarms
 * @author Jeffery
 * @version 0.9
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
		private int dayOfWeek = 0;
		private int repeatDaily = 0;
		private int repeatWeekly = 0;
		
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
	 */
	public void readAndReconstructAlarms() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader("alarms.txt"));
		int dataMember = 0;
		SavedVariables saved = new SavedVariables();
		boolean eof = false;
		while(!eof){
			String line = in.readLine();
			if(line.equals("EOF")){
				constructAlarms();
				in.close();
				break;
			}
			if(dataMember == 0){
				saved.hour = Integer.parseInt(line);
				System.out.println(saved.hour);
				dataMember++;
			}
			else if(dataMember == 1){
				saved.minute = Integer.parseInt(line);
				System.out.println(saved.minute);
				dataMember++;
			}
			else if(dataMember == 2){
				saved.label = line;
				System.out.println(saved.label);
				dataMember++;
			}
			else if(dataMember == 3){
				saved.dayOfWeek = Integer.parseInt(line);
				System.out.println(saved.dayOfWeek);
				dataMember++;
			}
			else if(dataMember == 4){
				saved.repeatDaily = Integer.parseInt(line);
				System.out.println(saved.repeatDaily);
				dataMember++;
			}
			else if(dataMember == 5){
				saved.repeatWeekly = Integer.parseInt(line);
				System.out.println(saved.repeatWeekly);
				dataMember++;
			}
			else if(dataMember == 6){
				if(line.equals("EOA")){
					try {
						SavedVariables temp = saved.clone();
						savedVars.add(temp);
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					numberOfAlarms++;
					dataMember = 0;
				}
			}
		}
	}
	
	/**
	 * Helper function to reconstuct each alarm
	 */
	private void constructAlarms(){
		for(int i = 0; i < numberOfAlarms; i++){
			AlarmClock a = new AlarmClock();
            a.setInputHour(savedVars.get(i).hour);
            a.setInputMinute(savedVars.get(i).minute);
            a.setAlarmLabel(savedVars.get(i).label);
            a.setAlarmSet(true);
            Gui.alarms.spawnNewThread(a);
            Gui.alarmList.addNewElement(a.getAlarmID());
		}
	}
}
