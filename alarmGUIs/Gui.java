package alarmGUIs;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.imageio.ImageIO;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;

import alarmClockThreads.AlarmClock;
import alarmClockThreads.threadSpawner;
import alarmStartUpHelpers.AlarmsStartupReader;


@SuppressWarnings("serial")
/**
 * Class Gui
 *
 * It contains the Graphic User Interface for the Alarm Clock
 *
 * @author Francisco Garcia
 * @Edit Aaron Kobelsky
 * @version 3.2
 */
public class Gui extends JFrame implements ActionListener, Runnable{

    //Variables for GUI Component
    private JFrame frame;
    private JPanel panel;
    private JButton btnList, btnAlarm;
    private JLabel label;
    private BufferedImage image;

    TrayIcon trayIcon;
    SystemTray tray;

    private static final String FILENAME = "alarms.txt";

    //storage for the alarms in the system
    public static threadSpawner alarms = new threadSpawner();

    //list of alarms in the system
    public static AlarmsViewer alarmList = new AlarmsViewer();

    /**
     * Constructor
     *
     * It creates an instance of the Graphic User Interface
     */
    public Gui(){

        super("SystemTray test");

        //reads the image
        try {
            image = ImageIO.read(getClass().getResource("alarmclockbg.jpg"));

        } catch (IOException ioe) {
            System.out.println("Could not read in the pic");
        }

        //Initialize JFrame of the GUI
        frame = new JFrame("Alarm Clock");
        frame.setVisible(true);
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Source:
        //http://stackoverflow.com/questions/12601004/do-something-before-window-closes-after-user-presses-x
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                writeAlarms();
                super.windowClosing(e);
            }
        });

        //Initialize JPanel of the GUI
        panel = new JPanel(){

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };

        panel.setLayout(null);

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


        /*
            System tray code authored by Mohammad Faisal
                -ermohammadfaisal.blogspot.com
                -facebook.com/m.faisal6621
            http://stackoverflow.com/questions/7461477/how-to-hide-a-jframe-in-system-tray-of-taskbar
            * Taken code edited by Matteo Molnar
        */

        // START OF MINIMIZE TO TRAY CODE - MATTEO
        // ---------------------------------------
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Unable to set LookAndFeel");
        }

        if (SystemTray.isSupported()) {
            tray=SystemTray.getSystemTray();

            Image image=Toolkit.getDefaultToolkit().getImage("AlarmClockIcon.png");
            ActionListener exitListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	writeAlarms();
                    System.exit(0);
                }
            };

            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem=new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            popup.add(defaultItem);

            defaultItem=new MenuItem("Open");
            defaultItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(true);
                    frame.setExtendedState(JFrame.NORMAL);
                }
            });

            popup.add(defaultItem);
            trayIcon=new TrayIcon(image, "Alarm Clock", popup);
            trayIcon.setImageAutoSize(true);
        }

        else {
            System.out.println("system tray not supported");
        }

        frame.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == ICONIFIED || e.getNewState() == 7) {
                    try {
                        tray.add(trayIcon);
                        frame.setVisible(false);
                    } catch (AWTException ex) {
                        System.out.println("unable to add to tray");
                    }
                }

                if (e.getNewState() == MAXIMIZED_BOTH || e.getNewState() == NORMAL) {
                    tray.remove(trayIcon);
                    frame.setVisible(true);
                }
            }
        });

        //http://www.iconsdb.com/icons/preview/white/alarm-clock-2-xxl.png
        setIconImage(Toolkit.getDefaultToolkit().getImage("AlarmClockIcon.png"));
        // ----------------------------------
        // END MINIMIZE TO TRAY CODE - MATTEO

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
    	AlarmsStartupReader asr = new AlarmsStartupReader();
    	try {
			asr.readAndReconstructAlarms();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
        //infinite while loop updates the GUI every second so that it always displays the correct time
        while(true){
            //display the current time
            String date = this.getTime();
            this.label.setText(date);
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

        if(temp == "Alarms List"){
            alarmList.run();
        }

        else if(temp == "Set an Alarm" ){
            AlarmGUI ag = new AlarmGUI();
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

    private void writeAlarms(){
    	BufferedWriter bw = null;
        FileWriter fw = null;

        int hour;
        int minute;
        String label;
        int dailyRepeat;
        int weeklyRepeat;

        try {
            ArrayList<Long> allThreads = alarms.getAllThreadID();

            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);
            for(int i = 0; i < allThreads.size(); i++){
            	AlarmClock temp = alarms.getThreadByID(allThreads.get(i)).alarm;
            	String toWrite = "";
            	hour = temp.getAlarmHour();
                minute = temp.getAlarmMinute();
                label = temp.getAlarmLabel();
                if(temp.getRepeatDaily()){
                	dailyRepeat = 1;
                }
                else{
                	dailyRepeat = 0;
                }
                if(temp.getRepeatWeekly()){
                	weeklyRepeat = 1;
                }
                else{
                	weeklyRepeat = 0;
                }
                toWrite = hour + "\n" + minute + "\n" + label +
            			"\n" + dailyRepeat + "\n" +
            			weeklyRepeat + "\n";
                HashMap<Integer, Boolean> days = temp.getAlarmDays();
                if(days.containsKey(1) && days.get(1)){
                	toWrite += "1\n";
                }
                if(days.containsKey(2) && days.get(2)){
                	toWrite += "2\n";
                }
                if(days.containsKey(3) && days.get(3)){
                	toWrite += "3\n";
                }
                if(days.containsKey(4) && days.get(4)){
                	toWrite += "4\n";
                }
                if(days.containsKey(5) && days.get(5)){
                	toWrite += "5\n";
                }
                if(days.containsKey(6) && days.get(6)){
                	toWrite += "6\n";
                }
                if(days.containsKey(7) && days.get(7)){
                	toWrite += "7\n";
                }
                toWrite += "EOA" + "\n";
                
            	bw.write(toWrite);
            }
            bw.write("EOF");
            System.out.println("Done");

        } catch (IOException g) {

            g.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }
}
