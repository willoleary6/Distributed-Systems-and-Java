/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectinterface;

import TTTWebApplication.TTTWebService;
import TTTWebApplication.TTTWebService_Service;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 *
 * @author Aidan
 */
public class JavaProjectInterface extends JFrame implements ActionListener {
    private JLabel errorMsg, successLabel;
    private ArrayList<MainGame> games = new ArrayList<MainGame>();
    private int userID, gameID;
    private JFrame frame;
    private JButton login, register, registerLink, createGame;
    private JPanel panel, gamePanel;
    private JTextField username, password, name, surname;
    TTTWebService_Service link;
    TTTWebService proxy;
   

    public JavaProjectInterface() {
        // TODO code application logic here
        
        link = new TTTWebService_Service();
        proxy = link.getTTTWebServicePort();
        
        System.out.println(proxy.test());
        
        username = new JTextField(20);
        password = new JTextField(20);
        errorMsg = new JLabel("");
        login = new JButton("Login");
        login.addActionListener(this);
        registerLink = new JButton("Register");
        registerLink.addActionListener(this);
        frame = new JFrame();
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
        
        frame.setTitle("Login");
        
        Dimension dim =  new Dimension(3000, 1240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(dim.width/2-this.getSize().width, dim.height/2-this.getSize().height);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void register() {
        
        username = new JTextField(20);
        password = new JTextField(20);
        name = new JTextField(20);
        surname = new JTextField(20);
        errorMsg = new JLabel("");
        register = new JButton("Register");
        register.addActionListener(this);
        
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
        panel.add(new JLabel(""));
        panel.add(errorMsg);
        
        frame.setTitle("Register");
        
        Dimension dim =  new Dimension(3000, 1240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(dim.width/2-this.getSize().width, dim.height/2-this.getSize().height);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void gameScreen() {
        errorMsg = new JLabel("");
        successLabel = new JLabel("");
        createGame = new JButton("Create Game");
        createGame.addActionListener(this);
        
        panel = new JPanel();
        //gamePanel = new JPanel();
        //gamePanel.setLayout(new GridLayout(3,3));
        panel.setLayout(new GridLayout(2,2));
        panel.setBackground(Color.white);
        frame.getContentPane().add(panel);
        //frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
        panel.add(createGame);
        panel.add(successLabel);
        panel.add(new JLabel("Password:"));
        panel.add(password);
        /*gamePanel.add(new JLabel("Name:"), BorderLayout.CENTER);
        gamePanel.add(new JLabel("Name:"));
        gamePanel.add(new JLabel("Surname:"));
        gamePanel.add(new JLabel("Name:"));
        gamePanel.add(new JLabel("Name:"));
        gamePanel.add(new JLabel(""));
        gamePanel.add(new JLabel("Name:"));
        gamePanel.add(new JLabel("Name:"));
        gamePanel.add(new JLabel("Name:"));*/
        
        frame.setTitle("Game");
        
        Dimension dim =  new Dimension(3000, 1240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(dim.width/2-this.getSize().width, dim.height/2-this.getSize().height);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        //TODO add error check + sql injection
        if(source == login){
            userID = proxy.login(username.getText(), password.getText());
            if(userID > 0) {
                System.out.print("Successful");
                frame.getContentPane().removeAll();
                gameScreen();
            }
            else{
                errorMsg.setText("Login Unsuccesful");
                errorMsg.setForeground(Color.RED);
            }
        }
        else if(source == registerLink){
            frame.getContentPane().removeAll();
            register();
        }
        else if(source == register) {
            String result = proxy.register(username.getText(), password.getText(),
                    name.getText(), surname.getText());
            try {
                userID = Integer.parseInt(result);
                System.out.println("success " + userID);
                frame.getContentPane().removeAll();
                gameScreen();
            } catch( Exception ex) {
                errorMsg.setForeground(Color.RED);
                System.out.println(result);
                System.out.println(username.getText());
                setErrorMsg(result);
            }
        }
        else if(source == createGame){
            String result = proxy.newGame(userID);
            try {
                gameID = Integer.parseInt(result);
                System.out.println("success " + gameID);
                games.add(new MainGame(proxy, gameID, userID));
                successLabel.setText("Successfully created game");
            } catch( Exception ex) {
                errorMsg.setForeground(Color.RED);
                System.out.println(result);
                System.out.println(username.getText());
                setErrorMsg(result);
            }
        }
    }
    
    private void setErrorMsg(String error) {
        switch(error) {
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
