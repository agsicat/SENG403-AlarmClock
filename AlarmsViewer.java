import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;


public class AlarmsViewer {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public void _Run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlarmsViewer window = new AlarmsViewer();
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
	public AlarmsViewer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblAlarms = new JLabel("Alarms");
		lblAlarms.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAlarms.setBounds(182, 26, 68, 14);
		frame.getContentPane().add(lblAlarms);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 51, 341, 115);
		frame.getContentPane().add(scrollPane);

		JList list = new JList();
		scrollPane.setViewportView(list);
		list.setValueIsAdjusting(true);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"ex 1", "ex 2", "ex 3", "ex 4", "ex 5", "ex 6", "ex 7", "ex 8", "ex 9", "ex 10"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(279, 193, 89, 23);
		frame.getContentPane().add(btnCancel);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(66, 193, 89, 23);
		frame.getContentPane().add(btnEdit);
	}
}