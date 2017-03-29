/**
 * Class Gui
 *
 * It contains the Graphic User Interface for the Alarm Clock
 *
 * @author Francisco Garcia
 * @version 0.8
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Gui extends JFrame implements ActionListener, Runnable{

    //Variables for GUI Component
    private JFrame frame;
    private JPanel panel;
    private JButton btnSwitch, btnAlarm;
    private JLabel label;
    
    //storage for the alarms in the system
    private threadSpawner alarms;
    
    public class AlarmGUI extends JFrame implements Runnable{

    	private JFrame frame2;
    	private JPanel panel2;
    	private JButton btnCancel, btnDismiss;
    	
		@Override
		public void run() {
			//Initialize JFrame of the GUI
            frame2 = new JFrame("Alarm Clock");
            frame2.setVisible(true);
            frame2.setSize(700, 500);
            frame2.setResizable(false);
            frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Initialize JPanel of the GUI
            panel2 = new JPanel();
            panel2.setLayout(null);;

            //Initialize the first JButton of the GUI
            btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(new AlarmListener());
            btnCancel.setBounds(175, 375, 120, 35);
            panel2.add(btnCancel);

            //Initialize the second JButton of the GUI
            btnDismiss = new JButton("New");
            btnDismiss.addActionListener(new AlarmListener());
            btnDismiss.setBounds(425, 375, 120, 35);
            panel2.add(btnDismiss);
            
            //Add panel to frame
            frame2.add(panel2);
            
            AlarmClock mainClock = new AlarmClock();
            mainClock.checkTime();
            mainClock.setAlarm();
            threadSpawner t = new threadSpawner();
            t.spawnNewThread(mainClock);
		}
    	
    }
    public class AlarmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String temp = e.getActionCommand();

	        if(temp == "Alarm"){
	            //JOptionPane.showMessageDialog(null, "Analog/Digital");
	            AlarmGUI g = new AlarmGUI();
	            g.run();
	        }
	        
	        if(temp == "New"){
	        	JOptionPane.showMessageDialog(null, "Enter the time the alarm should ring");
	        }
	        
	        if(temp == "Cancel"){
	        	
	        }
	        
		}
    	
    }

    /**
     * Constructor
     *
     * It creates an instance of the Graphic User Interface
     */
    public Gui(){

        //Initialize JFrame of the GUI
        frame = new JFrame("Alarm Clock");
        frame.setVisible(true);
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initialize JPanel of the GUI
        panel = new JPanel();
        panel.setLayout(null);;

        //Initialize the first JButton of the GUI
        btnSwitch = new JButton("Switch");
        btnSwitch.addActionListener(this);
        btnSwitch.setBounds(175, 375, 120, 35);
        panel.add(btnSwitch);

        //Initialize the second JButton of the GUI
        btnAlarm = new JButton("Alarm");
        btnAlarm.addActionListener(new AlarmListener());
        btnAlarm.setBounds(425, 375, 120, 35);
        panel.add(btnAlarm);


        //Initialize the label, it contains the time
        String date = getTime();
        label = new JLabel(date);
        label.setBounds(250, 100, 300, 200);
        label.setFont(new Font("Serif", Font.PLAIN, 54));
        panel.add(label);

        //Add panel to frame
        frame.add(panel);

        //initialize the thread manager for the alarms
        alarms = new threadSpawner();
    }

    /**
     *  Function returns the time of the users machine
     *
     *  @return   temp      String containing the current time
     */

    public String getTime(){

        int second = 0;
        String AMPM = "";
        Calendar time = new GregorianCalendar();

        if (second != time.get(Calendar.SECOND)) {
            if (time.get(Calendar.AM_PM) == 1) {
                AMPM = "PM";
            } else {
                AMPM = "AM";
            }
        }

        String temp =  time.get(Calendar.HOUR) + ":" + time.get(Calendar.MINUTE) + ":" +time.get(Calendar.SECOND) + " " + AMPM;

        return temp;
    }

    @Override
    /**
     * Function allows the GUI to respond to an action performed
     *
     * @param e     The action that will trigger a respone in the GUI
     */
    public void actionPerformed(ActionEvent e) {
        String temp = e.getActionCommand();

        if(temp == "Switch"){
            JOptionPane.showMessageDialog(null, "Analog/Digital");
        }

        else if(temp == "Alarm" ){
            JOptionPane.showMessageDialog(null, "Alarm Menu");
        }
    }

    /**
     * Main function of the class
     *
     * @param args      Command Line Arguments
     */
    public static void main (String[] args){
            Gui g = new Gui();
            g.run();
    }

	@Override
	public void run() {
		 while(true){
         	String date = this.getTime();
         	this.label.setText(date);
         	this.repaint();
         }
	}
}