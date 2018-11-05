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
public class registerUser extends JFrame implements ActionListener{
    private Hangman_Service link; 
    private Hangman proxy;
    private JLabel forename, surname, username,password, email;
    private JTextField forenameInput, surnameInput, 
            usernameInput, passwordInput, emailInput;
    private JButton button;
    private JPanel panel;
    private JFrame frame = new JFrame();
    public registerUser(){
       link = new Hangman_Service();
       proxy = link.getHangmanPort(); 
    }
    
        
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if(source == button) {
        
            String forename = forenameInput.getText();
            String username = usernameInput.getText();
            String surname = surnameInput.getText();
            String password = passwordInput.getText();
            String email = emailInput.getText();
            
            proxy.registerUser(forename, surname,username, password, email);
            frame.setVisible(false);

        }
    }
    public void generateUI(){
        int windowWidth = 500;
        int windowHeight = 500;
        frame.setTitle("Register");
        frame.setBounds(500, 500, windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Welcome", JLabel.CENTER);
        
        forename = new JLabel("Forname:");
        forenameInput = new JTextField(20);
        
        surname = new JLabel("Surname:");
        surnameInput = new JTextField(20);
        
        username = new JLabel("Username:");
        usernameInput = new JTextField(20);
        
        password = new JLabel("password:");
        passwordInput = new JTextField(20);
        
        email = new JLabel("email:");
        emailInput = new JTextField(20);
        
        button = new JButton("Register");
        button.addActionListener(this);

        panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new GridLayout(10,1));
        panel.add(forename);
        panel.add(forenameInput);
        panel.add(surname);
        panel.add(surnameInput);
        panel.add(username);
        panel.add(usernameInput);
        panel.add(password);
        panel.add(passwordInput);        
        panel.add(email);
        panel.add(emailInput);
        panel.add(button);
        
        
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
          
    }
}
