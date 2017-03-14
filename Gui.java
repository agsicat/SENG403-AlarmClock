/**
 * Class Gui
 *
 * It contains the Graphic User Interface for the Alarm Clock
 *
 * @author Francisco Garcia
 * @Edit Aaron Kobelsky
 * @version 3.2
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


public class Gui extends JFrame implements ActionListener, Runnable{

    //Variables for GUI Component
    private JFrame frame;
    private JPanel panel;
    private JButton btnSwitch, btnList, btnAlarm;
    private JLabel label;
    private boolean doAnalogDisplay = false;
    AlarmClock a = new AlarmClock();

    //storage for the alarms in the system
    public static threadSpawner alarms = new threadSpawner();

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

        /*
        //Initialize the first JButton of the GUI
        btnSwitch = new JButton("Switch");
        btnSwitch.addActionListener(this);
        btnSwitch.setBounds(175, 375, 120, 35);
        panel.add(btnSwitch);
        */

        //Initialize the first JButton of the GUI
        btnList = new JButton("Alarms List");
        btnList.addActionListener(this);
        btnList.setBounds(175, 375, 120, 35);
        panel.add(btnList);

        //Initialize the second JButton of the GUI
        btnAlarm = new JButton("Set an Alarm");
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
        String tempminute = "";
        String tempsecond = "";
        
        if(10 > (int)time.get(Calendar.MINUTE)){
        	tempminute = "0"+time.get(Calendar.MINUTE);
        }
        else{
        	tempminute += time.get(Calendar.MINUTE);
        }
        
        if(10 > (int)time.get(Calendar.SECOND)){
        	tempsecond = "0"+time.get(Calendar.SECOND);
        }
        else{
        	tempsecond += time.get(Calendar.SECOND);
        }

        if(time.get(Calendar.HOUR) == 0){
            temp =  12 + ":" + tempminute + ":" + tempsecond + " " + AMPM;
        }
        else {
            temp =  time.get(Calendar.HOUR) + ":" + tempminute + ":" + tempsecond + " " + AMPM;
        }

        return temp;
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

    @Override
    /**
     * Function allows the GUI to respond to an action performed
     *
     * @param e     The action that will trigger a response in the GUI
     */
    public void actionPerformed(ActionEvent e) {
        String temp = e.getActionCommand();

        /*
        if(temp == "Switch"){
            this.doAnalogDisplay = !this.doAnalogDisplay;
        }
        */

        if(temp == "Alarms List"){
            AlarmsViewer av = new AlarmsViewer(a);
            av.run();
        }

        else if(temp == "Set an Alarm" ){
            AlarmGUI ag = new AlarmGUI(a);
            ag.run();
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
}
