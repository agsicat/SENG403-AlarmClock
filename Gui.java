/**
 * Class Gui
 *
 * It contains the Graphic User Interface for the Alarm Clock
 *
 * @author Francisco Garcia
 * @version 1.0
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Gui extends JFrame implements ActionListener{

    //Variables for GUI Component
    private JFrame frame;
    private JPanel panel;
    private JButton btnSwitch, btnAlarm, btnCancel;
    private JLabel label;

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
        btnAlarm.addActionListener(this);
        btnAlarm.setBounds(425, 375, 120, 35);
        panel.add(btnAlarm);


        //Initialize the label, it contains the time
        String date = getTime();
        label = new JLabel(date);
        label.setBounds(250, 100, 300, 200);
        label.setFont(new Font("Serif", Font.PLAIN, 54));
        panel.add(label);

        //TEST
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(this);
        btnCancel.setBounds(425, 275, 120, 35); //PLACE BUTTON SOMEWHERE ELSE
        panel.add(btnCancel);
        //TEST

        //Add panel to frame
        frame.add(panel);


    }

    /**
     *  Function returns the time of the users machine
     *
     *  @return   temp      String containing the current time
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

        else if(temp == "Cancel"){
            JOptionPane.showMessageDialog(null, "Cancel Alarm");
        }
    }

    /**
     * Main function of the class
     *
     * @param args      Command Line Arguments
     */
    public static void main (String[] args){
        Gui g = new Gui();
        while(true){
            String date = g.getTime();
            g.label.setText(date);
            g.repaint();
        }
    }
}
