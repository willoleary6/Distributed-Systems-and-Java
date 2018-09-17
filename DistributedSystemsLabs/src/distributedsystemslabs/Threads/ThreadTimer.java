package distributedsystemslabs.Threads;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

/**
 *
 * @author 15155528
 */
public class ThreadTimer extends Thread {
    // ALT: implements runnable
    private JLabel myLabel;
    private int sleep_interval;
    
    public ThreadTimer(JLabel p, int t){
        myLabel = p;
        sleep_interval = t*1000;
    }
    
   
    @Override
    public void run(){
        while (true) {
            String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
            myLabel.setText(timestamp);
            
            try {
                sleep(sleep_interval);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}

