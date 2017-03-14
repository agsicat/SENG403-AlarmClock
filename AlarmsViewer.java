
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.DefaultListModel;


public class AlarmsViewer extends JFrame implements Runnable, ActionListener{

	AlarmClock a;
    DefaultListModel<String> dlm;

	public AlarmsViewer(AlarmClock param1){
	    this.a = param1;
	}

	@Override
	public void run() {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setVisible(true);
		frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblAlarms = new JLabel("Alarms");
		lblAlarms.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAlarms.setBounds(182, 26, 68, 14);
		frame.getContentPane().add(lblAlarms);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(48, 51, 341, 115);
		frame.getContentPane().add(scrollPane);

        dlm = new DefaultListModel<>();
        if(a.getInputMinute() < 10){
            dlm.addElement(String.valueOf(a.getInputHour()) + ":0" + String.valueOf(a.getInputMinute()));
        }

        else{
            dlm.addElement(String.valueOf(a.getInputHour()) + ":" + String.valueOf(a.getInputMinute()));
        }

		//Update an element
		//dlm.set(1, "test3");

		JList<String> list = new JList<>(dlm);
		scrollPane.setViewportView(list);

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
	public void actionPerformed(ActionEvent e) {
		String temp = e.getActionCommand();

		if (temp == "Edit"){
            Gui.alarms.cancelAlarm(a.getAlarmID());
			dlm.set(0, "");

			AlarmGUI ag = new AlarmGUI(a);
			ag.run();

			/*
			if (ag.getReturnMinute() < 10){
				dlm.addElement(String.valueOf(ag.getReturnHour()) + ":0" + String.valueOf(ag.getReturnMinute()));
			}
			else{
				dlm.addElement(String.valueOf(ag.getReturnHour()) + ":" + String.valueOf(ag.getReturnMinute()));
			}
			*/

		}
        else if (temp == "Cancel") {
            Gui.alarms.cancelAlarm(a.getAlarmID());
            dlm.set(0, "");
        }
	}


}