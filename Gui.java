/**
 * Class Gui
 *
 * It contains the Graphic User Interface for the Alarm Clock
 *
 * @author Francisco Garcia
 * @Edit Aaron Kobelsky
 * @version 2.1
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
    private boolean doAnalogDisplay = false;
    
    //storage for the alarms in the system
    private static threadSpawner alarms = new threadSpawner();

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
        panel.setLayout(null);

        //Initialize the first JButton of the GUI
        btnSwitch = new JButton("Switch");
        btnSwitch.addActionListener(this);
        btnSwitch.setBounds(175, 375, 120, 35);
        panel.add(btnSwitch);

        //Initialize the second JButton of the GUI
        btnAlarm = new JButton("Alarm");
        btnAlarm.addActionListener(this);
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


    }

    /**
     *  Function returns the time of the users machine
     *
     *  @return String containing the current time
     */

    public String getTime(){

        int second = -1;
        String AMPM = "";
        Calendar time = new GregorianCalendar();

        if (second != time.get(Calendar.SECOND)) {
            if (time.get(Calendar.AM_PM) == 1) {
                AMPM = "PM";
            }

            else {
                AMPM = "AM";
            }
        }


        String temp;

        if(time.get(Calendar.HOUR) == 0){
            temp =  12 + ":" + time.get(Calendar.MINUTE) + ":" + time.get(Calendar.SECOND) + " " + AMPM;
        }
        else {
            temp =  time.get(Calendar.HOUR) + ":" + time.get(Calendar.MINUTE) + ":" + time.get(Calendar.SECOND) + " " + AMPM;
        }

        return temp;
    }

    
    @Override
    /**
     * Function allows the GUI to respond to an action performed
     *
     * @param e     The action that will trigger a response in the GUI
     */
    public void actionPerformed(ActionEvent e) {
        String temp = e.getActionCommand();

        //this button changes whether the time should be displayed in an analog or digital format
        if(temp == "Switch"){
            this.doAnalogDisplay = !this.doAnalogDisplay;
        }

        else if(temp == "Alarm" ){

            JFrame frame = new JFrame("Alarm Menu");
            frame.setSize(500, 100);
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Spinner for days of the week
            //How do you adjust the size of the text box? I did it a chicky way... Insert some spaces after Monday
            String[] list = {"Monday       ","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
            SpinnerModel model1 = new SpinnerListModel(list);
            JSpinner day = new JSpinner(model1);

            //Spinner for the time
            SpinnerModel model2 = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
            JSpinner time = new JSpinner(model2);

            JSpinner.DateEditor de = new JSpinner.DateEditor(time, "HH:mm");
            time.setEditor(de);

            //Action Listener within Action Listener? How do you do that?
            JButton btn = new JButton("Save Alarm");

            Container cont = frame.getContentPane();
            cont.setLayout(new FlowLayout());

            cont.add(new JLabel("Select Day:"));
            cont.add(day);

            cont.add(new JLabel("Select Time:"));
            cont.add(time);

            cont.add(btn);

        }
    }

    /**
     * Main function of the class
     *
     * @param args Command Line Arguments
     */
    public static void main (String[] args){
        Gui g = new Gui();
        g.run();
    }
    
    @Override
	public void run() {
		
    	 //infinite while loop updates the GUI every second so that it always displays the correct time
		 while(true){
			 //if the time should be displayed in an analog format
			 if(this.doAnalogDisplay){
				 this.label.setText("Analog Display");
			 }
			 //else the time should be displayed in a digital format
			 else{
				 String date = this.getTime();
		         	this.label.setText(date);
			 }
			 //refresh the GUI to reflect the changed contents
         	 this.repaint();
         }
	}
}