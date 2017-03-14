import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

/**
 * Class AlarmGUI
 *
 *  Class creates a new object for the Alarm Menu
 *  @author Francisco Garcia
 *  @version 1.0
 */

public class AlarmGUI extends JFrame implements Runnable, ActionListener {

    public Date alarmtime = new Date();
    public boolean end = false;
    public JSpinner time;

    public int returnHour = 0;
    public int returnMinute = 0;

    AlarmClock a;
    public static threadSpawner alarms;

    public AlarmGUI(AlarmClock param1, threadSpawner param2){
        this.a = param1;
        this.alarms = param2;
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Alarm Menu");
        frame.setSize(650, 100);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Spinner for days of the week
        //How do you adjust the size of the text box? I did it a chicky way... Insert some spaces after Monday
        String[] list = {"Monday       ","Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
        SpinnerModel model1 = new SpinnerListModel(list);
        JSpinner day = new JSpinner(model1);

        //Spinner for the time
        SpinnerModel model2 = new SpinnerDateModel(alarmtime, null, null, Calendar.HOUR_OF_DAY);
        time = new JSpinner(model2);

        JSpinner.DateEditor de = new JSpinner.DateEditor(time, "HH:mm");
        time.setEditor(de);

        //Action Listener within Action Listener? How do you do that?
        JButton btn = new JButton("Save Alarm");
        //JButton cancelBtn = new JButton("Cancel");
        //JButton listBtn = new JButton("Alarms List");
        btn.addActionListener(this);
        //cancelBtn.addActionListener(this);
        //listBtn.addActionListener(this);

        Container cont = frame.getContentPane();
        cont.setLayout(new FlowLayout());

        cont.add(new JLabel("Select Day:"));
        cont.add(day);

        cont.add(new JLabel("Select Time:"));
        cont.add(time);

        cont.add(btn);
        //cont.add(cancelBtn);
        //cont.add(listBtn);

    }

    //Used in AlarmsViewer to update time
    public int getReturnHour(){
        return this.returnHour;
    }

    //Used in AlarmsViewer to update time
    public int getReturnMinute(){
        return this.returnMinute;
    }

    public void actionPerformed(ActionEvent e) {
        String temp = e.getActionCommand();

        if(temp == "Save Alarm"){
            Date date = (Date)time.getModel().getValue();
            a.setInputHour(date.getHours());
            a.setInputMinute(date.getMinutes());
            a.setAlarmSet(true);
            alarms.spawnNewThread(a);
            //System.out.println(a.getAlarmID());
            //System.out.println(a.getInputMinute());
            JOptionPane.showMessageDialog(null, "Alarm set for: " + a.getInputHour() + ":" + a.getInputMinute());
            returnHour = a.getInputHour();
            returnMinute = a.getInputHour();

        }
/*
            else if (temp == "Cancel") {
                alarms.cancelAlarm(alarmID);
                JOptionPane.showMessageDialog(null, "Alarm Cancelled");
            }

            else if (temp == "Alarms List") {
                //JOptionPane.showMessageDialog(null, "List of Alarms");
                AlarmsViewer av = new AlarmsViewer();
                av._Run();
            }
*/
    }
}