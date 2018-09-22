/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_1;

/**
 *
 * @author J Murphy
 */

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.WindowConstants;

class WindowEventHandler extends WindowAdapter {
    public void windowClosing(WindowEvent evt) {
        System.exit(0);
    }
}

public class Lab_1_Window{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        int windowWidth = 400;
        int windowHeight = 150;

        JFrame aWindow = new JFrame("This is the Window Title");

        aWindow.setBounds(50, 100, windowWidth, windowHeight);
        aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aWindow.setVisible(true);
        
        JFrame frame = new JFrame();
        frame.setTitle("My First Swing Application");
        frame.setBounds(50, 100, windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Welcome", JLabel.CENTER);
        frame.add(label);
        frame.pack();
        frame.setVisible(true);
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame2 = new JFrame();
        
        frame2.addWindowListener(new WindowEventHandler());
        frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setTitle("JFrame Test");
        frame2.setLayout(new GridLayout(3,2));
        frame2.add(new JLabel("First Name:"));
        frame2.add(new JTextField());
        frame2.add(new JLabel("Last Name:"));
        frame2.add(new JTextField());
        frame2.add(new JButton("Register"));
        
        int frameWidth = 200;
        int frameHeight = 100;
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame2.setBounds((int) screenSize.getWidth() - frameWidth, 0, frameWidth, frameHeight);
        frame2.setVisible(true);
    }
}