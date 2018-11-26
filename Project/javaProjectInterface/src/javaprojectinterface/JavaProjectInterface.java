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

    private JFrame frame;
    private JPanel panel;
    private JLabel title, errorMessage, loggedInUser;
    private JButton login, registerLink, loginLink, logout, leaderboard, scoreSystem;

    TTTWebService_Service link;
    TTTWebService proxy;
   

    public JavaProjectInterface() {         
        link = new TTTWebService_Service();
        proxy = link.getTTTWebServicePort();
        
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
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == login) {
            frame.dispose();
            new Login(proxy);
        }
        else if (source == loginLink) {
           frame.dispose();
           new Login(proxy);
        }
        else if (source == registerLink) {
            frame.dispose();
            Register register = new Register(proxy);
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
                    Login login = new Login(proxy);
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

