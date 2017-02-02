/**
 * Class Gui
 *
 * It contains the Graphic User Interface for the Alarm Clock
 *
 * @author Francisco Garcia
 * @version 0.5
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Gui extends JFrame implements ActionListener{

    //Variables for GUI Component
    private JFrame frame;
    private JPanel panel;
    private JButton btnSwitch, btnAlarm;

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

        //Add panel to frame
        frame.add(panel);

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
        new Gui();
    }
}
