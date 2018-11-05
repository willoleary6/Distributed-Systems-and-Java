/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_9_web_client;
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

/**
 *
 * @author puser
 */
public class Lab_9_web_client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
JFrame f=new JFrame("Button Example"); 
					//submit button
    JButton b=new JButton("Submit");    
    b.setBounds(100,100,140, 40);    
					//enter name label
    JLabel label = new JLabel();		
    label.setText("Enter Name :");
label.setBounds(10, 10, 100, 100);
					//empty label which will show event after button clicked
		JLabel label1 = new JLabel();
		label1.setBounds(10, 110, 200, 100);
					//textfield to enter name
		JTextField textfield= new JTextField();
		textfield.setBounds(110, 50, 130, 30);
					//add to frame
		f.add(label1);
		f.add(textfield);
		f.add(label);
		f.add(b);    
		f.setSize(300,300);    
		f.setLayout(null);    
		f.setVisible(true);    
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		
							//action listener
		b.addActionListener(new ActionListener() {
	        
			@Override
			public void actionPerformed(ActionEvent arg0) {
					label1.setText("Name has been submitted.");				
			}          
	      });

    }    
}
