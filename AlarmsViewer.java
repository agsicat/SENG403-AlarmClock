import java.awt.EventQueue;

import javax.swing.JFrame;


public class AlarmsViewer extends JFrame {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public AlarmsViewer() {
		frame = new JFrame("Alarms Viewer");
		frame.setBounds(100, 100, 450, 300);
        frame.setVisible(true);
        frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}