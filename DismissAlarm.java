import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;


public class DismissAlarm {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the application.
	 */
	public DismissAlarm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 250, 100, 0};
		gridBagLayout.rowHeights = new int[]{85, 0, 25, 0, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblAlarm = new JLabel("ALARM");
		lblAlarm.setForeground(Color.RED);
		lblAlarm.setFont(new Font("Tahoma", Font.BOLD, 70));
		GridBagConstraints gbc_lblAlarm = new GridBagConstraints();
		gbc_lblAlarm.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblAlarm.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlarm.gridx = 1;
		gbc_lblAlarm.gridy = 1;
		frame.getContentPane().add(lblAlarm, gbc_lblAlarm);
		
		JLabel lblAlarmTime = new JLabel("ALARM TIME");
		GridBagConstraints gbc_lblAlarmTime = new GridBagConstraints();
		gbc_lblAlarmTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlarmTime.gridx = 1;
		gbc_lblAlarmTime.gridy = 2;
		frame.getContentPane().add(lblAlarmTime, gbc_lblAlarmTime);
		
		JButton btnDismiss = new JButton("Dismiss");
		GridBagConstraints gbc_btnDismiss = new GridBagConstraints();
		gbc_btnDismiss.insets = new Insets(0, 0, 5, 5);
		gbc_btnDismiss.gridx = 1;
		gbc_btnDismiss.gridy = 3;
		frame.getContentPane().add(btnDismiss, gbc_btnDismiss);
	}
}