package alarmGUIs;
import javax.swing.*;

import alarmClockThreads.AlarmClock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Class AlarmGUI
 *
 *  Class creates a new object for the Alarm Menu
 *  @author Francisco Garcia
 *  @Edit Aaron Kobelsky, Multiple refactoring tasks to allow handling of multiple alarms
 *  @version 3.0
 */
public class AlarmGUI implements Runnable, ActionListener {

    //JSpinner for selecting a time for the new alarm to ring
    public JSpinner time;
    
    //JList for selecting a day for the new alarm
    public JList<String> day;
    
    //Radio button for selecting weekly repeating
    public JRadioButton weekly;
    
    //Radio button for selecting daily repeating
    public JRadioButton daily;
    
    //JFrame of this Gui
    public JFrame frame;
	
	//JTextField to allow the user to input a label for the alarm
	public JTextField textField;

    @Override
    public void run() {
        frame = new JFrame("Set New Alarm Menu");
        frame.setSize(950, 120);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Spinner for the time
        SpinnerModel model2 = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        time = new JSpinner(model2);

        JSpinner.DateEditor de = new JSpinner.DateEditor(time, "HH:mm");
        time.setEditor(de);

        JButton btn = new JButton("Save Alarm");
        btn.setSize(120, 35);
        btn.addActionListener(this);

        JPanel cont = new JPanel(); //frame.getContentPane();
        cont.setLayout(new FlowLayout());
        cont.setBackground(new Color(165,233,220));

        cont.add(new JLabel("Set Alarm Time:"));
        cont.add(time);
		
		textField = new JTextField(10);

		cont.add(new JLabel("Set Alarm Label:"));
		cont.add(textField);
		
		weekly = new JRadioButton();
		weekly.setOpaque(false);
		weekly.setText("Repeat Weekly");
		
		daily = new JRadioButton();
		daily.setOpaque(false);
		daily.setText("Repeat Daily");
		
		
		String[] days = {"Mon ","Tue ", "Wed ", "Thu ", "Fri ", "Sat ","Sun "};
		day = new JList<String>(days);
		day.setSelectedIndex((int)LocalDate.now().getDayOfWeek().getLong(ChronoField.DAY_OF_WEEK)-1);
		day.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		day.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		day.setVisibleRowCount(1);
		cont.add(new JLabel("Set Alarm Day(s):"));
		cont.add(day);
		
		cont.add(weekly);
		cont.add(daily);

        cont.add(btn);
        frame.add(cont);
    }

    @SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
        String temp = e.getActionCommand();

        if(temp == "Save Alarm"){
        	
        	//check for erroneous button selections
        	if(daily.isSelected() && weekly.isSelected()){
        		JOptionPane.showMessageDialog(null, "An alarm cannot be both Daily and Weekly repeating!");
        		return;
        	}
        	
        	//get the time selected in the spinner
            Date date = (Date)time.getModel().getValue();
            
            //set the alarm
            String label = "";
			if(textField.getText()==null || textField.getText().equals("")){
				label = "Alarm";
			}
			else{
				label = textField.getText();
			}
			
			//set daily/weekly repeating flags
			boolean d = false;
			boolean w = false;
			if(daily.isSelected()){
				d = true;
			}
			if(weekly.isSelected()){
				w = true;
			}
			
			int[] selectedDays = day.getSelectedIndices();
			HashMap<Integer, Boolean> alarmDays = new HashMap<Integer, Boolean>();
			for(int i = 0; i < selectedDays.length; i++){
				alarmDays.put(selectedDays[i]+1, true);
			}
			
			//create a new alarmClock object
            AlarmClock a = new AlarmClock(date.getHours(), date.getMinutes(), alarmDays, d, w, label);
            a.setAlarmSet(true);
            
            //start the alarm thread
            Gui.alarms.spawnNewThread(a);
            
            //add the new alarm to the list
            Gui.alarmList.addNewElement(a.getAlarmID());
            
            //notify the user an alarm has been set
			if(a.getAlarmMinute() < 10){
				JOptionPane.showMessageDialog(null, "Alarm set for " + a.getAlarmHour() + ":0" + a.getAlarmMinute());
			}
			else{
				JOptionPane.showMessageDialog(null, "Alarm set for " + a.getAlarmHour() + ":" + a.getAlarmMinute());
			}
			
			//close the gui
			frame.dispose();
        }
    }
}