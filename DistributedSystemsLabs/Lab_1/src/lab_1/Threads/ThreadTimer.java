package lab_1.Threads;

import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author puser
 */
public class ThreadTimer extends Thread {
//public class ThreadPanel implements Runnable {    
    private JLabel myLabel;
    private int interval;
    
    public ThreadTimer(JPanel p, int i) {
        interval = i*1000;
        myLabel = new JLabel(getTime());
        p.add(myLabel);
    }
    
    private String getTime() {
        String timeString 
         = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return timeString;
    }
    
    @Override
    public void run() {
        while(true) {
            myLabel.setText(getTime()); 
            
            try {
                sleep(interval);
            } catch(Exception e) {
                
            }
        }
    }
}
