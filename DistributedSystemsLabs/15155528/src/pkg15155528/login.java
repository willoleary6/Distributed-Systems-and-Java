/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg15155528;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import lab_9_web_service.Hangman;
import lab_9_web_service.Hangman_Service;
import javax.swing.*;
/**
 *
 * @author puser
 */
public class login extends JFrame implements ActionListener{
    private Hangman_Service link; 
    private Hangman proxy;
    private JLabel  username,password;
    private JTextField usernameInput, passwordInput;
    private JButton button;
    private JPanel panel;
    private JFrame frame = new JFrame();
    public login(){
       link = new Hangman_Service();
       proxy = link.getHangmanPort(); 
    }
    
        
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if(source == button) {
       
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            
            proxy.checkLogin(username, password);
            frame.setVisible(false);

        }
    }
    public void generateUI(){
        int windowWidth = 500;
        int windowHeight = 500;
        frame.setTitle("Login");
        frame.setBounds(500, 500, windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Welcome", JLabel.CENTER);
          
        username = new JLabel("Username:");
        usernameInput = new JTextField(20);
        
        password = new JLabel("password:");
        passwordInput = new JTextField(20);
        
        button = new JButton("Login");
        button.addActionListener(this);

        panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new GridLayout(10,1));
        panel.add(username);
        panel.add(usernameInput);
        panel.add(password);
        panel.add(passwordInput);        
        panel.add(button);
        
        
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
          
    }
}
