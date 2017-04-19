package alarmGUIs;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

import alarmClockThreads.AlarmClock;

import javax.swing.DefaultListModel;

/**
 * Displays a list of all currently set alarms in a list.
 * Also allows a selected alarm from the list to be cancelled or edited
 * @author Ibby
 * @Edit Francisco Garcia, Implemented Edit and Cancel functionality for a single alarm
 * @Edit Aaron Kobelsky, Refactored Edit and Cancel functionalities to support multiple alarms
 * @version 2.2
 */
public class AlarmsViewer implements Runnable, ActionListener{

	//main JFrame of this GUI
	public JFrame frame;
	
	//model in which list elements are stored
    public DefaultListModel<listElement> dlm;
    
    //list of alarms displayed by this GUI
    public JList<listElement> alarmsList;

    //constructs the alarmsViewer GUI, but does not yet display it
	public AlarmsViewer(){
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(165,233,220));
		panel.setLayout(null);

		JLabel lblAlarms = new JLabel("Alarms");
		lblAlarms.setFont(new Font("Serif", Font.PLAIN, 18));
		lblAlarms.setBounds(182, 26, 68, 18);
		panel.add(lblAlarms);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 51, 341, 115);
		panel.add(scrollPane);
		
		dlm = new DefaultListModel<>();
		alarmsList = new JList<>(dlm);
		scrollPane.setViewportView(alarmsList);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(279, 193, 89, 23);
		btnCancel.addActionListener(this);
		panel.add(btnCancel);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(66, 193, 89, 23);
        btnEdit.addActionListener(this);
        panel.add(btnEdit);
        
        frame.add(panel);
	}

	//there is only a single instance of this GUI so exiting hides it and this function makes it visible again
	@Override
	public void run() {
		frame.setVisible(true);
	}
	
	//add a new element to the list of alarms
	public void addNewElement(Long ID){
		dlm.addElement(new listElement(ID));
	}
	
	//allows an alarm to be removed from the list based on it's alarmID
	public void removeElementByID(Long ID){
		for(int i = 0; i < dlm.size(); i++){
			System.out.println(dlm.get(i).alarmID);
			if(dlm.get(i).alarmID.equals(ID)){
				dlm.remove(i);
				return;
			}
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String temp = e.getActionCommand();
		
		//if the edit button was selected
		if (temp == "Edit"){
			//if there are no currently set alarms, give the user a warning message
			if(dlm.isEmpty()){
				JOptionPane.showMessageDialog(null, "There are no alarms to edit");
				return;
			}
			//if there is no currently selected alarm, give the user a warning message
			if(alarmsList.getSelectedValue() == null){
        		JOptionPane.showMessageDialog(null, "Please select an alarm to edit");
        		return;
        	}
            
            //create a new edit gui to edit the selected alarm
			editGUI eg = new editGUI();
			eg.run();

		}
		//if the cancel button was selected
        else if (temp == "Cancel") {
        	//if there are no currently set alarms, give the user a warning message
        	if(dlm.isEmpty()){
        		JOptionPane.showMessageDialog(null, "There are no alarms to cancel");
				return;
			}
        	//if there is no currently selected alarm, give the user a warning message
        	if(alarmsList.getSelectedValue() == null){
        		JOptionPane.showMessageDialog(null, "Please select an alarm to cancel");
        		return;
        	}
        	//cancel the alarm and remove it from the list
            Gui.alarms.cancelAlarm(alarmsList.getSelectedValue().getAlarmID());
            dlm.remove(alarmsList.getSelectedIndex());
        }
	}
	
	/**
	 * Class used for displaying alarms in the alarm list
	 * @author Aaron Kobelsky
	 */
	public class listElement{
		
		//ID for the alarm this list element represents
		private Long alarmID;
		
		//minute, hour and label of the alarm
		private int alarmMinute;
		private int alarmHour;
		private String alarmLabel;
		private HashMap<Integer, Boolean> alarmDays;
		private boolean repeatDaily;
		private boolean repeatWeekly;
		
		//day the alarm is set for, in the case of daily repeating will be "EveryDay"
		//private String alarmDay;
		
		//flags for whether the alarm will repeat
		//private boolean repeatDaily;
		//private boolean repeatWeekly;
		
		public listElement(Long ID){
			AlarmClock temp = Gui.alarms.getThreadByID(ID).alarm;
			alarmMinute = temp.getAlarmMinute();
			alarmHour = temp.getAlarmHour();
			alarmLabel = temp.getAlarmLabel();
			alarmDays = temp.getAlarmDays();
			repeatDaily = temp.getRepeatDaily();
			repeatWeekly = temp.getRepeatWeekly();
			alarmID = ID;
		}
		
		public String toString(){
			String tempHour = Integer.toString(alarmHour);
			String tempMinute = Integer.toString(alarmMinute);
			if(alarmHour < 10){
	            tempHour = "0"+tempHour;
	        }
			if(alarmMinute < 10){
				tempMinute = "0"+tempMinute;
			}
			String tempDays = "";
			if(alarmDays.containsKey(1) && alarmDays.get(1)){
				tempDays += "M";
			}
			if(alarmDays.containsKey(2) && alarmDays.get(2)){
				tempDays += "T";
			}
			if(alarmDays.containsKey(3) && alarmDays.get(3)){
				tempDays += "W";
			}
			if(alarmDays.containsKey(4) && alarmDays.get(4)){
				tempDays += "R";
			}
			if(alarmDays.containsKey(5) && alarmDays.get(5)){
				tempDays += "F";
			}
			if(alarmDays.containsKey(6) && alarmDays.get(6)){
				tempDays += "S";
			}
			if(alarmDays.containsKey(7) && alarmDays.get(7)){
				tempDays += "U";
			}
			if(repeatDaily){
				return alarmLabel+" "+tempHour+":"+tempMinute+" Daily";
			}
			else if(repeatWeekly){
				return alarmLabel+" "+tempHour+":"+tempMinute+" "+tempDays+" Weekly";
			}
			return alarmLabel+" "+tempHour+":"+tempMinute+" "+tempDays;
		}
		
		public Long getAlarmID(){
			return alarmID;
		}
	}

	/**
	 * Class for displaying a GUI containing alarm edit options
	 * @author Aaron Kobelsky
	 */
	public class editGUI implements Runnable, ActionListener{
		
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
	        frame = new JFrame("Edit Alarm Menu");
	        frame.setSize(950, 120);
	        frame.setVisible(true);
	        frame.setResizable(false);
	        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	        //Spinner for the time
	        SpinnerModel model2 = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
	        time = new JSpinner(model2);

	        JSpinner.DateEditor de = new JSpinner.DateEditor(time, "HH:mm");
	        time.setEditor(de);

	        JButton btn = new JButton("Edit Alarm");
	        //btn.setBounds(120, 35);
	        btn.setSize(120, 35);
	        btn.addActionListener(this);

	        JPanel cont = new JPanel(); //frame.getContentPane();
	        cont.setLayout(new FlowLayout());
	        //cont.setLayout(null);
	        cont.setBackground(new Color(165,233,220));

	        //cont.add(new JLabel("Select Day:"));
	        //cont.add(day);

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
			//testList.addListSelectionListener(new mouseClickListener());
			//testList.addMouseListener(new rmouseClickListener());
			
			cont.add(weekly);
			cont.add(daily);

	        cont.add(btn);
	        frame.add(cont);
	    }
        
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            String temp = e.getActionCommand();

            //if edit alarm button was pressed
            if(temp == "Edit Alarm"){
            	
            	//check for erroneous button selections
            	if(daily.isSelected() && weekly.isSelected()){
            		JOptionPane.showMessageDialog(null, "An alarm cannot be both Daily and Weekly repeating!");
            		return;
            	}
            	
            	
            	//cancel the selected alarm
            	Gui.alarms.cancelAlarm(alarmsList.getSelectedValue().getAlarmID());
     
            	//create a new alarm with the new time
                Date date = (Date)time.getModel().getValue();
                
                boolean d = false;
                boolean w = false;
                //set daily/weekly repeating flags
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
                
                //set the new alarm's label to match the alarm being edited's label unless a new label has been given
    			String label = "";
    			if(textField.getText()==null || textField.getText().equals("")){
    				label = alarmsList.getSelectedValue().alarmLabel;
    			}
    			else{
    				label = textField.getText();
    			}
                
                AlarmClock a = new AlarmClock(date.getHours(), date.getMinutes(), alarmDays, d, w, label);
                //set the alarm
                a.setAlarmSet(true);
                
                //spawn a new thread for the alarm
                Gui.alarms.spawnNewThread(a);
                
                //remove the old alarm from the list and replace it with the new alarm
                dlm.remove(alarmsList.getSelectedIndex());
                Gui.alarmList.addNewElement(a.getAlarmID());
                
                //notify the user the alarm has been set
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
}