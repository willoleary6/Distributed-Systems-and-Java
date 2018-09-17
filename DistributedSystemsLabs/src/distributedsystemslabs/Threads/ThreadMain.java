/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedsystemslabs.Threads;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author puser
 */
public class ThreadMain extends JFrame{
    private JPanel [] panels;
    private ThreadTimer [] threads;
    private int[] sleeps = {1,2,5,10};
    
    public ThreadTimerExample(){
        this.setTitle("Threaded Clock Example");
        this.setBounds(100,100,400,200);
        this.setPreferredSize(new Dimension(400,200));
        
        panels = new JPanel[4];
        threads = new ThreadTimer[4];
        
        for(int i = 0; i < 4; i++){
            panels[i] = new JPanel();
            JLabel myLabel = new JLabel("test");
            threads[i] = new ThreadTimer(myLabel, sleeps[i]);
        }
    }
}
