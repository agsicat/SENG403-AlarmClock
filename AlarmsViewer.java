import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
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
		frame.getContentPane().setLayout(null);

		JLabel lblAlarms = new JLabel("Alarms");
		lblAlarms.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAlarms.setBounds(182, 26, 68, 14);
		frame.getContentPane().add(lblAlarms);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 51, 341, 115);
		frame.getContentPane().add(scrollPane);
		
		dlm = new DefaultListModel<>();
		alarmsList = new JList<>(dlm);
		scrollPane.setViewportView(alarmsList);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(279, 193, 89, 23);
		btnCancel.addActionListener(this);
		frame.getContentPane().add(btnCancel);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(66, 193, 89, 23);
        btnEdit.addActionListener(this);
		frame.getContentPane().add(btnEdit);
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
		
		//day the alarm is set for, in the case of daily repeating will be "EveryDay"
		//private String alarmDay;
		
		//flags for whether the alarm will repeat
		//private boolean repeatDaily;
		//private boolean repeatWeekly;
		
		public listElement(Long ID){
			AlarmClock temp = Gui.alarms.getThreadByID(ID).alarm;
			alarmMinute = temp.getInputMinute();
			alarmHour = temp.getInputHour();
			alarmLabel = temp.getAlarmLabel();
			//TODO add day support to AlarmClock
			//alarmDay = temp.getDay();
			//TODO add repeat functionality to AlarmClock
			//repeatDaily = temp.getDaily();
			//repeatWeekly = temp.getWeekly();
			alarmID = ID;
		}
		
		public String toString(){
			//TODO add display for repeating functionality and day
			String tempHour = Integer.toString(alarmHour);
			String tempMinute = Integer.toString(alarmMinute);
			if(alarmHour < 10){
	            tempHour = "0"+tempHour;
	        }
			if(alarmMinute < 10){
				tempMinute = "0"+tempMinute;
			}
			return alarmLabel+" "+tempHour+":"+tempMinute;
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
		
		//JSpinner to indicate the time to change the selected alarm to
        public JSpinner time;
        
        //JFrame of this Gui
        public JFrame frame;
        
      //JSpinner for selecting a day for the new alarm
        public JSpinner day;
        
        //Radio button for selecting weekly repeating
        public JRadioButton weekly;
        
        //Radio button for selecting daily repeating
        public JRadioButton daily;
        
        //JTextField to allow the user to input a label for the alarm
    	public JTextField textField;

        @Override
        public void run() {
            frame = new JFrame("Alarm Menu");
            frame.setSize(650, 120);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            //Spinner for days of the week
            //How do you adjust the size of the text box? I did it a chicky way... Insert some spaces after Monday
            String[] list = {"Mon","Tue", "Wed", "Thu", "Fri", "Sat","Sun"};
            SpinnerModel model1 = new SpinnerListModel(list);
            day = new JSpinner(model1);

            //Spinner for the time
            SpinnerModel model2 = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
            time = new JSpinner(model2);

            JSpinner.DateEditor de = new JSpinner.DateEditor(time, "HH:mm");
            time.setEditor(de);

            JButton btn = new JButton("Edit Alarm");
            btn.addActionListener(this);

            Container cont = frame.getContentPane();
            cont.setLayout(new FlowLayout());

            cont.add(new JLabel("Select Day:"));
            cont.add(day);

            cont.add(new JLabel("Select Time:"));
            cont.add(time);
            
            textField = new JTextField(10);

    		cont.add(new JLabel("Set Label:"));
    		cont.add(textField);
    		
    		weekly = new JRadioButton();
    		weekly.setText("Weekly");
    		
    		daily = new JRadioButton();
    		daily.setText("Daily");
    		
    		cont.add(weekly);
    		cont.add(daily);

            cont.add(btn);
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
                AlarmClock a = new AlarmClock(date);
                a.setInputHour(date.getHours());
                a.setInputMinute(date.getMinutes());
                //set daily/weekly repeating flags
    			if(daily.isSelected()){
    				a.setRepeatDaily(true);
    			}
    			if(weekly.isSelected()){
    				a.setRepeatWeekly(true);
    			}
    			
    			if(day.getModel().getValue().equals("Mon")){
    				a.setInputDay(1);
    			}
    			else if(day.getModel().getValue().equals("Tue")){
    				a.setInputDay(2);
    			}
    			else if(day.getModel().getValue().equals("Wed")){
    				a.setInputDay(3);
    			}
    			else if(day.getModel().getValue().equals("Thu")){
    				a.setInputDay(4);
    			}
    			else if(day.getModel().getValue().equals("Fri")){
    				a.setInputDay(5);
    			}
    			else if(day.getModel().getValue().equals("Sat")){
    				a.setInputDay(6);
    			}
    			else if(day.getModel().getValue().equals("Sun")){
    				a.setInputDay(7);
    			}
                
                //set the new alarm's label to match the alarm being edited's label unless a new label has been given
                if(textField.getText()==null || textField.getText().equals("")){
    				a.setAlarmLabel(dlm.getElementAt(alarmsList.getSelectedIndex()).alarmLabel);
    			}
    			else{
    				a.setAlarmLabel(textField.getText());
    			}
                
                //set the alarm
                a.setAlarmSet(true);
                
                //spawn a new thread for the alarm
                Gui.alarms.spawnNewThread(a);
                
                //remove the old alarm from the list and replace it with the new alarm
                dlm.remove(alarmsList.getSelectedIndex());
                Gui.alarmList.addNewElement(a.getAlarmID());
                
                //notify the user the alarm has been set
				if(a.getInputMinute() < 10){
					JOptionPane.showMessageDialog(null, "Alarm set for " + a.getInputHour() + ":0" + a.getInputMinute());
				}
				else{
					JOptionPane.showMessageDialog(null, "Alarm set for " + a.getInputHour() + ":" + a.getInputMinute());
				}
         
				//close the gui
				frame.dispose();
            }
        }
    }
}