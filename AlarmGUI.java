import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

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
	
	//jTextField to allow the user to input a label for the alarm
	public JTextField textField;

    @Override
    public void run() {
        JFrame frame = new JFrame("Alarm Menu");
        frame.setSize(650, 100);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Spinner for days of the week
        //How do you adjust the size of the text box? I did it a chicky way... Insert some spaces after Monday
        String[] list = {"Monday       ","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
        SpinnerModel model1 = new SpinnerListModel(list);
        JSpinner day = new JSpinner(model1);

        //Spinner for the time
        SpinnerModel model2 = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        time = new JSpinner(model2);

        JSpinner.DateEditor de = new JSpinner.DateEditor(time, "HH:mm");
        time.setEditor(de);

        JButton btn = new JButton("Save Alarm");
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

        cont.add(btn);
    }

    @SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
        String temp = e.getActionCommand();

        if(temp == "Save Alarm"){
        	//get the time selected in the spinner
            Date date = (Date)time.getModel().getValue();
            
            //create a new alarmClock object
            AlarmClock a = new AlarmClock(date);
            
            //set the alarm
            a.setInputHour(date.getHours());
            a.setInputMinute(date.getMinutes());
			if(textField.getText()==null || textField.getText().equals("")){
				a.setAlarmLabel("Alarm");
			}
			else{
				a.setAlarmLabel(textField.getText());
			}
            a.setAlarmSet(true);
            
            //start the alarm thread
            Gui.alarms.spawnNewThread(a);
            
            //add the new alarm to the list
            Gui.alarmList.addNewElement(a.getAlarmID());
            
            //notify the user an alarm has been set
			if(a.getInputMinute() < 10){
				JOptionPane.showMessageDialog(null, "Alarm set for " + a.getInputHour() + ":0" + a.getInputMinute());
			}
			else{
				JOptionPane.showMessageDialog(null, "Alarm set for " + a.getInputHour() + ":" + a.getInputMinute());
			}
        }
    }
}