/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectinterface;

import TTTWebApplication.TTTWebService;
import TTTWebApplication.TTTWebService_Service;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author Aidan and Aaron
 */
public class JavaProjectInterface extends JFrame implements ActionListener {

    private int userID;
    private JFrame frame;
    private JPanel panel;
    private JLabel title, errorMessage, successLabel, loggedInUser;
    private JButton login, register, registerLink, loginLink, logout, leaderboard, createGame, scoreSystem;
    private JTextField username, name, surname;
    private JPasswordField password;

    TTTWebService_Service link;
    TTTWebService proxy;
   

    public JavaProjectInterface() {         
        link = new TTTWebService_Service();
        proxy = link.getTTTWebServicePort();
        
        System.out.println(proxy.test());
        
        title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setBounds(0, 20, 300, 20);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        loginLink = new JButton("Login");
        loginLink.setBounds(80, 70, 140, 30);
        loginLink.addActionListener(this);
        
        registerLink = new JButton("Register");
        registerLink.setBounds(80, 110, 140, 30);
        registerLink.addActionListener(this);
                        
        frame = new JFrame();
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        frame.getContentPane().add(panel);
        panel.add(title);
        panel.add(loginLink);
        panel.add(registerLink);
        
        frame.setTitle("Tic Tac Toe");
        frame.setSize(320,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void login() {
        username = new JTextField(20);
        password = new JPasswordField(20);
        errorMessage = new JLabel("");
        
        login = new JButton("Login");
        login.addActionListener(this);
        
        registerLink = new JButton("Register");
        registerLink.addActionListener(this);
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(4,2));
        panel.setBackground(Color.white);
        frame.getContentPane().add(panel);
        panel.add(new JLabel("Username:"));
        panel.add(username);
        panel.add(new JLabel("Password:"));
        panel.add(password);
        panel.add(login);
        panel.add(registerLink);
        panel.add(errorMessage);
        
        frame.setTitle("Tic Tac Toe - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void register() {
        username = new JTextField(20);
        password = new JPasswordField(20);
        name = new JTextField(20);
        surname = new JTextField(20);
        errorMessage = new JLabel("");
        register = new JButton("Register");
        register.addActionListener(this);
        loginLink = new JButton("Login");
        loginLink.addActionListener(this);
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(6,2));
        panel.setBackground(Color.white);
        frame.getContentPane().add(panel);
        panel.add(new JLabel("Username:"));
        panel.add(username);
        panel.add(new JLabel("Password:"));
        panel.add(password);
        panel.add(new JLabel("Name:"));
        panel.add(name);
        panel.add(new JLabel("Surname:"));
        panel.add(surname);
        panel.add(register);
        panel.add(loginLink);
        panel.add(new JLabel(""));
        panel.add(errorMessage);
        
        frame.setTitle("Tic Tac Toe - Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        //TODO add error check + sql injection
        if (source == login) {
            userID = proxy.login(username.getText(), password.getText());
            
            if(userID > 0) {
                System.out.print("Successful");
                frame.getContentPane().removeAll();
                frame.setVisible(false);
                MainMenu menu = new MainMenu(proxy,userID, username.getText());
            }
            else {
               errorMessage.setText("Login Unsuccesful");
                errorMessage.setForeground(Color.RED);
           }
        }
        else if (source == loginLink) {
           frame.getContentPane().removeAll();     
            login();
        }
        else if (source == registerLink) {
                frame.getContentPane().removeAll();
                register();
        }
        else if (source == register) {
            String result = proxy.register(username.getText(), password.getText(),
            name.getText(), surname.getText());
            try {
                userID = Integer.parseInt(result);
                System.out.println("success " + userID);
                frame.getContentPane().removeAll();
                frame.setVisible(false);
                MainMenu menu = new MainMenu(proxy,userID, username.getText());

            } catch( Exception ex)  {
                errorMessage.setForeground(Color.RED);
                System.out.println(result);
                System.out.println(username.getText());
                setErrorMessage(result);
            }
        }
       
        else if(source == scoreSystem) {
            if (proxy.leagueTable() != null && !proxy.leagueTable().equals("ERROR-NOGAMES") && proxy.leagueTable().contains(loggedInUser.getText())) {
                ScoreSystem scoreSystem = new ScoreSystem(proxy.leagueTable(), loggedInUser.getText());
                scoreSystem.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(null,
                        "There are no games details available to you. Please play a game to see your stats.",
                        "No Player Statistics available ",
                        JOptionPane.ERROR_MESSAGE);
            }            
        }
        else if(source == leaderboard) {
           Leaderboard leaderboard = new Leaderboard(proxy, proxy.leagueTable());
           leaderboard.setVisible(true);
            
        }
        else if(source == logout) {
            int opt;
            opt = JOptionPane.showConfirmDialog(null, "Are you sure you wish to log out?", "Log out", JOptionPane.YES_NO_OPTION);
            
            switch (opt) {
                case JOptionPane.YES_OPTION:
                    JOptionPane.showMessageDialog(null, "Successfully logged out");
                    frame.getContentPane().removeAll();
                    login();
                    break;
                case JOptionPane.NO_OPTION:    
                    hide();
                    break;
            }
        }
    }
    
    private void setErrorMessage (String error) {
        switch(error) {
            case "ERROR-REPEAT":
                errorMessage.setText("Repeated entry");
                break;
            case "ERROR-INSERT":
                errorMessage.setText("Couldnt Add entry");
                break;
            case "ERROR-RETRIEVE":
                errorMessage.setText("couldnt Retrive data");
                break;
            case "ERROR-DB":
                errorMessage.setText("cannot find DB");
                break;
        }
    }    
}

