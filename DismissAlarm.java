import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Dimension;
/**
 * Class that includes the window that pops-up when the
 * Alarm is ringing.  This connects to the functionality of
 * the "snooze" and "dismiss" button.
 *
 * @author Ibrahim
 * @edit Angela Sicat - added functionality for dismiss button
 * @edit Ibrahim - added GUI for Snooze button
 * @edit Angela Sicat - v4.0 added functionality for Snooze button
 * @Edit Aaron Added support for displaying some of the alarm's information on the GUI
 * @Edit Aaron Added comments
 * @version 4.2
 **/
public class DismissAlarm {

	//frame of the pop up GUI
	private JFrame frame;

	//alarmID of the alarm this GUI represents
	private long alarmID;
	
	//variables to allow for "graceful" handling of multiple alarms
	public static int numOfWindows = 0;
	public int xOffset = 50;
	public int yOffset = 50;

	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * Create the GUI.
	 */
	public DismissAlarm(long ID) {
		initialize(ID);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(long ID) {
		
		//save the alarmID
		alarmID = ID;
		
		//initialize the main GUI frame
		frame = new JFrame();
		Color background = new Color(165,233,220);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				DismissAlarm.numOfWindows--;
				super.windowClosing(e);
			}
		});
		
		//initialize a panel to diaplay in the frame
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(background);
		
		//label of the ringing alarm to be displayed on the GUI
		JLabel lblAlarm = new JLabel(" " + Gui.alarms.getThreadByID(alarmID).alarm.getAlarmLabel());
		lblAlarm.setBounds(90, 25, 250, 65);
		lblAlarm.setBackground(Color.WHITE);
		lblAlarm.setOpaque(true);
		lblAlarm.setFont(new Font("Serif", Font.PLAIN, 50));
		panel.add(lblAlarm);
						
		//display the time the alarm was set for on the GUI
		int hour = Gui.alarms.getThreadByID(alarmID).alarm.getInputHour();
		int min = Gui.alarms.getThreadByID(alarmID).alarm.getInputMinute();
		String h = "";
		String m = "";
		String ampm = "";
		if(hour >= 12){
			if(hour!=12){
				hour = hour - 12;
			}
			ampm = "PM";
		}
		else if(hour == 0){
			hour = 12;
			ampm = "AM";
		}
		else{
			ampm = "AM";
		}
		h = Integer.toString(hour);
		if(min < 10){
			m = "0"+Integer.toString(min);
		}
		else{
			m = Integer.toString(min);
		}
		JLabel lblAlarmTime = new JLabel(h + ":" + m + " " + ampm);
		lblAlarmTime.setBounds(170, 121, 100, 25);
		lblAlarmTime.setFont(new Font("Serif", Font.PLAIN, 25));
		panel.add(lblAlarmTime);
					
		//button to snooze the current alarm for 5 minutes
		JButton btnSnooze = new JButton("Snooze");
		btnSnooze.addActionListener(new DismissActionListener());
		btnSnooze.setBounds(112, 166, 100, 23);
		panel.add(btnSnooze);
						
		//button to dismiss the current alarm
		JButton btnDismiss = new JButton("Dismiss");
		btnDismiss.setBounds(222, 166, 100, 23);
		btnDismiss.addActionListener(new DismissActionListener());
		panel.add(btnDismiss);
		
		//add the panel to the frame
		frame.add(panel);
		
		//make sure this GUI does not appear directly on top of another dismiss GUI
		int centreX = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int centreY = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation((centreX + (xOffset * numOfWindows)), (centreY + (yOffset * numOfWindows)));

		//increment the number of DismissGUIs currently open
		DismissAlarm.numOfWindows++;
	}

	public class DismissActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String tempButtonName = e.getActionCommand();
			
			// Passes the alarmID of current alarm to stop the thread
			if (tempButtonName == "Snooze") {
				// Closes window when Snooze is selected
				frame.dispose();

				// Then runs the Snooze function by passing the alarmID of current alarm to snooze the thread
				Gui.alarms.snoozeAlarm(alarmID);
				
			}else if (tempButtonName == "Dismiss") {
				// Passes the alarmID of current alarm to stop the thread
				Gui.alarms.dismissAlarm(alarmID);
				
				// Then closes window when Dismiss is selected
				frame.dispose();
			}

		}
	}
}
