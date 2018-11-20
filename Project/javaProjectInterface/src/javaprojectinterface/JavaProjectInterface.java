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
import javax.swing.table.*;
/**
 *
 * @author Aidan
 */
public class JavaProjectInterface extends JFrame implements ActionListener {
    private JLabel title, errorMsg;
    private JFrame frame;
    private JButton login, register, registerLink, loginLink, logout, leaderboard, createGame, scoreSystem;
    private JPanel panel;
    private JTable lbTable, gameTable;
    private JTextField username, name, surname;
    private JPasswordField password;
    TTTWebService_Service link;
    TTTWebService proxy;
   

    public JavaProjectInterface() {
        // TODO code application logic here
        
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
        errorMsg = new JLabel("");
        
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
        panel.add(errorMsg);
        
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
        errorMsg = new JLabel("");
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
        panel.add(errorMsg);
        
        frame.setTitle("Tic Tac Toe - Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void gameScreen() {
        // Example
        String data[][] = {
            {"23","ChunkyMitts", "Available"},
            {"651","dxfc", "Available"},
        };
        String cols[] = {"Game","Host", "Status"};
        
        createGame = new JButton("Create Game");
        createGame.setBounds(20, 20, 140, 30);
        createGame.addActionListener(this);
        
        scoreSystem = new JButton("Score System");
        scoreSystem.setBounds(20, 60, 140, 30);
        scoreSystem.addActionListener(this);      
        
        leaderboard = new JButton("Leaderboard");
        leaderboard.setBounds(20, 100, 140, 30);
        leaderboard.addActionListener(this);      
        
        logout = new JButton("Logout");
        logout.setBounds(20, 390, 140, 30);
        logout.addActionListener(this);
        
        gameTable = new JTable(data, cols) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        gameTable.setBounds(200, 20, 320, 400);
        DefaultTableCellRenderer dtcr =  new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(JLabel.CENTER);
        gameTable.setBorder(BorderFactory.createCompoundBorder());
        //gameTable.setBackground(Color.white);
        gameTable.setShowGrid(true);
        
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        frame.getContentPane().add(panel);
        panel.add(createGame);
        panel.add(scoreSystem);
        panel.add(leaderboard);   
        panel.add(logout);
        panel.add(gameTable);
       
        frame.setTitle("Tic Tac Toe");
        frame.setSize(560, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        //TODO add error check + sql injection
        if(source == login) {
            /*int result;
            result = proxy.login(username.getText(), password.getText());
            if(result > 0) {
                System.out.print("Successful");*/
                frame.getContentPane().removeAll();
                gameScreen();
            /*}
            else{
                errorMsg.setText("Login Unsuccesful");
                errorMsg.setForeground(Color.RED);
            }*/
        }
        else if(source == loginLink){
            frame.getContentPane().removeAll();
            login();
        }
        else if(source == registerLink){
            frame.getContentPane().removeAll();
            register();
        }
        else if(source == register) {
            String result = proxy.register(username.getText(), password.getText(),
                    name.getText(), surname.getText());
            try {
                int userID = Integer.parseInt(result);
                System.out.println("success " + userID);
                frame.getContentPane().removeAll();
                gameScreen();
            } catch( Exception ex) {
                errorMsg.setForeground(Color.RED);
                System.out.println(result);
                System.out.println(username.getText());
                switch(result) {
                    case "ERROR-REPEAT":
                        errorMsg.setText("Repeated entry");
                        break;
                    case "ERROR-INSERT":
                        errorMsg.setText("Couldnt Add entry");
                        break;
                    case "ERROR-RETRIEVE":
                        errorMsg.setText("couldnt Retrive data");
                        break;
                    case "ERROR-DB":
                        errorMsg.setText("cannot find DB");
                        break;
                }
                
            }
        }
    }
    
    
}
