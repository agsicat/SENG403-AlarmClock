/**
 * Analog clock code.
 * @author nguyenphu27
 */


import java.awt.Color;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;

import javax.swing.JPanel;


public class AnalogClock extends JPanel {

    private static final int spacing = 35;
    private static final float radPerSecMin = (float) (Math.PI / 30.0);
    private static final float radPerNum = (float) (Math.PI / -6);
    private int size;
    private int centerX;
    private int centerY;

    SimpleDateFormat sf;

    Calendar cal;
    int hour;
    int minute;
    int second;
    Color colorSecond, colorMHour, colorNumber;

    Timer timer2 = new Timer();
    TimeZone timeZone = TimeZone.getDefault();
    

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // border clock
        g.setColor(new Color(124, 136, 162));
        g.fillOval(25, spacing, 350, 350);
        g.setColor(Color.WHITE);
        g.fillOval(35, spacing + 10, 330, 330);
        size = 400 - spacing;
        centerX = 400 / 2;
        centerY = 400 / 2 + 10;

        // clock face
        drawClockFace(g);

        // number clock face
        drawNumberClock(g);
        

        // get system time
        cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);

        // draw point clock
        g.setColor(Color.BLACK);
        g.fillOval(centerX - 5, centerY - 5, 10, 10);
        g.setColor(Color.RED);
        g.fillOval(centerX - 3, centerY - 3, 6, 6);
        drawHands(g, hour, minute, second, colorSecond.RED, colorMHour.BLACK);
    }

    /*-------------Clock Face----------------*/
    private void drawClockFace(Graphics g) {
			// TODO Auto-generated method stub

        // tick marks
        for (int sec = 0; sec < 60; sec++) {
            int ticStart;
            if (sec % 5 == 0) {
                ticStart = size / 2 - 10;
            } else {
                ticStart = size / 2 - 5;
            }

            drawRadius(g, centerX, centerY, radPerSecMin * sec, ticStart - 20, size / 2 - 20, colorNumber.BLACK);

        }
    }

    private void drawRadius(Graphics g, int x, int y, double angle, int minRadius, int maxRadius,
            Color colorNumber) {
        float sine = (float) Math.sin(angle);
        float cosine = (float) Math.cos(angle);
        int dxmin = (int) (minRadius * sine);
        int dymin = (int) (minRadius * cosine);
        int dxmax = (int) (maxRadius * sine);
        int dymax = (int) (maxRadius * cosine);
        g.setColor(colorNumber);
        g.drawLine(x + dxmin, y + dymin, x + dxmax, y + dymax);
    }
    /*---------------------------------------------*/

    /*----------------Clock Number-----------------*/
    private void drawNumberClock(Graphics g) {
        // TODO Auto-generated method stub
        for (int num = 12; num > 0; num--) {
            drawnum(g, radPerNum * num, num);
        }
    }

    private void drawnum(Graphics g, float angle, int n) {
        // TODO Auto-generated method stub
        float sine = (float) Math.sin(angle);
        float cosine = (float) Math.cos(angle);
        int dx = (int) ((size / 2 - 20 - 25) * -sine);
        int dy = (int) ((size / 2 - 20 - 25) * -cosine);

        g.drawString("" + n, dx + centerX - 5, dy + centerY + 5);
    }
    /*-----------------------------------------------*/

    /*----------------Clock Hands--------------------*/
    private void drawHands(Graphics g, double hour, double minute, double second, Color colorSecond,
            Color colorMHour) {
        // TODO Auto-generated method stub
        double rsecond = (second * 6) * (Math.PI) / 180;
        double rminute = ((minute + (second / 60)) * 6) * (Math.PI) / 180;
        double rhours = ((hour + (minute / 60)) * 30) * (Math.PI) / 180;

        g.setColor(colorSecond);
        g.drawLine(centerX, centerY, centerX + (int) (150 * Math.cos(rsecond - (Math.PI / 2))),
                centerY + (int) (150 * Math.sin(rsecond - (Math.PI / 2))));
        g.setColor(colorMHour);
        g.drawLine(centerX, centerY, centerX + (int) (120 * Math.cos(rminute - (Math.PI / 2))),
                centerY + (int) (120 * Math.sin(rminute - (Math.PI / 2))));
        g.drawLine(centerX, centerY, centerX + (int) (90 * Math.cos(rhours - (Math.PI / 2))),
                centerY + (int) (90 * Math.sin(rhours - (Math.PI / 2))));
    }
    /*-------------------------------------------------*/
}