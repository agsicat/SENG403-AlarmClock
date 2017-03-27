import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;


public class DismissAlarm {

	private JFrame frame;

	private long alarmID;

	public static int numOfWindows = 0;
	public int xOffset = 50;
	public int yOffset = 50;

	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DismissAlarm window = new DismissAlarm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

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
						btnSnooze.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {

							}
						});
						btnSnooze.setBounds(145, 166, 67, 23);
						frame.getContentPane().add(btnSnooze);

								JButton btnDismiss = new JButton("Dismiss");
								btnDismiss.setBounds(222, 166, 67, 23);
								btnDismiss.addActionListener(new RandomActionListener());
								frame.getContentPane().add(btnDismiss);
		frame.setAlwaysOnTop(true);

		int centreX = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int centreY = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation((centreX + (xOffset * numOfWindows)), (centreY + (yOffset * numOfWindows)));

		DismissAlarm.numOfWindows++;

		alarmID = ID;
	}

	public class RandomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			//Passes the alarmID of current alarm to stop the thread
			Gui.alarms.dismissAlarm(alarmID);

			//Closes window when Dismiss is selected
			frame.dispose();
		}
	}
}
