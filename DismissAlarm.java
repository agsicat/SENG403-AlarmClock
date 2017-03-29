import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.Color;
/**
 * Class that includes the window that pops-up when the 
 * Alarm is ringing.  This connects to the functionality of
 * the "snooze" and "dismiss" button.
 *
 * @author Ibrahim
 * @edit Angela Sicat - added functionality for dismiss button
 * @edit Ibrahim - added GUI for Snooze button
 * @edit Angela Sicat - v4.0 added functionality for Snooze button
 * @version 4.0
 **/
public class DismissAlarm {

	private JFrame frame;

	private long alarmID;

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { DismissAlarm window = new
	 * DismissAlarm(); window.frame.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	/**
	 * Create the application.
	 */
	public DismissAlarm(long ID) {
		initialize(ID);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(long ID) {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblAlarm = new JLabel("ALARM");
		lblAlarm.setBounds(90, 25, 250, 85);
		lblAlarm.setForeground(Color.RED);
		lblAlarm.setFont(new Font("Tahoma", Font.BOLD, 70));
		frame.getContentPane().add(lblAlarm);

		JLabel lblAlarmTime = new JLabel("ALARM TIME");
		lblAlarmTime.setBounds(184, 121, 61, 14);
		frame.getContentPane().add(lblAlarmTime);

		JButton btnSnooze = new JButton("Snooze");
		btnSnooze.setBounds(145, 166, 67, 23);
		btnSnooze.addActionListener(new RandomActionListener());
		frame.getContentPane().add(btnSnooze);

		JButton btnDismiss = new JButton("Dismiss");
		btnDismiss.setBounds(222, 166, 67, 23);
		btnDismiss.addActionListener(new RandomActionListener());
		frame.getContentPane().add(btnDismiss);
		frame.setAlwaysOnTop(true);

		alarmID = ID;
	}

	public class RandomActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String tempButtonName = e.getActionCommand();
			
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
