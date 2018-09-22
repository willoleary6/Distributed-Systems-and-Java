/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_1.Threads;
import java.lang.*;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ThreadMain extends JFrame {

    private JPanel[] myPanels;
    private ThreadTimer[] myThreads;
    //private Thread[] myThreads;
    private int[] intervals = {1,2,5,10, 5, 6, 7, 6, 3, 4, 2, 1 , 3 , 45};
    
    public ThreadMain() {
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Thread Test");
        int layoutDimension = (int) Math.ceil(Math.sqrt(intervals.length));
        setLayout(new GridLayout(layoutDimension, layoutDimension));
        
        myPanels = new JPanel[intervals.length];
        myThreads = new ThreadTimer[intervals.length];
        
        for(int i = 0; i < intervals.length; i++) {
            myPanels[i] = new JPanel();
            add(myPanels[i]);
            myThreads[i] = new ThreadTimer(myPanels[i], intervals[i]);
            myThreads[i].start();
        }
        
        int frameWidth = 2000;
        int frameHeight = 1000;
        
        setBounds(1000,1000, frameWidth, frameHeight);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        ThreadMain t = new ThreadMain();
    }
    
}