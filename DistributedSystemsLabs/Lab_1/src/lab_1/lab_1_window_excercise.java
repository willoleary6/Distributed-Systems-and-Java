/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_1;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author puser
 */
public class lab_1_window_excercise {
    public static void main(String [] args) {
        int windowWidth = 400;
        int windowHeight = 150;
        
        
        JFrame frame2 = new JFrame();
        
        frame2.addWindowListener(new WindowEventHandler());
        frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setTitle("JFrame Test");
        frame2.setLayout(new GridLayout(2,2));
        frame2.add(new JLabel("First Name:"));
        frame2.add(new JLabel("First Name:"));
        frame2.add(new JLabel("First Name:"));
        frame2.add(new JLabel("First Name:"));
        
        int frameWidth = 200;
        int frameHeight = 100;
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame2.setBounds((int) screenSize.getWidth() - frameWidth, 0, frameWidth, frameHeight);
        frame2.setVisible(true);
    
    }
}
