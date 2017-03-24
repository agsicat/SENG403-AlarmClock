<<<<<<< HEAD
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
	public static void main(String[] args) {
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
=======
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;

/**
 * Displays a list of all currently set alarms in a list.
 * Also allows a selected alarm from the list to be cancelled or edited
 * @author Ibby
 * @Edit Francisco Garcia, Implemented Edit and Cancel functionality for a single alarm
 * @Edit Aaron Kobelsky, Refactored Edit and Cancel functionalities to support multiple alarms
 * @version 2.0
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

	@Override
	public void run() {
		frame.setVisible(true);
	}
	
	public void addNewElement(Long ID){
		dlm.addElement(new listElement(ID));
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String temp = e.getActionCommand();
		int index = dlm.getSize();
		ArrayList<listElement> elements = new ArrayList<listElement>();
		for(int i = 0; i < index; i++){
			elements.add(dlm.get(i));
		}
		
		
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
			
			//TODO refactor this to use Jeff's change time function
			//cancel the alarm
            Gui.alarms.cancelAlarm(alarmsList.getSelectedValue().getAlarmID());
            dlm.remove(alarmsList.getSelectedIndex());
            
            //create a new alarm to replace it
			AlarmGUI ag = new AlarmGUI();
			ag.run();

		}
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
			String tempHour = Integer.toBinaryString(alarmHour);
			String tempMinute = Integer.toBinaryString(alarmMinute);
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


}
>>>>>>> refs/remotes/origin/master
