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
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
/**
 *
 * @author Aidan
 */
public class JavaProjectInterface extends JFrame implements ActionListener {
    //private JLabel errorMsg, successLabel;
    private ArrayList<MainGame> games = new ArrayList<MainGame>();
    private int userID, gameID;
    private String [][]tableData;
    //private JFrame frame;
   // private JButton login, register, registerLink, createGame;
    //private JTextField username, password, name, surname;
    private JLabel title, errorMessage, successLabel, loggedInUser;
    private JFrame frame, frame2;
    private JButton login, register, registerLink, loginLink, logout, leaderboard, createGame, scoreSystem;
    private JPanel panel, panel2, gamePanel;
    private JTable leaderboardTable, gameTable;
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
    
    public void gameScreen() {
        String result = proxy.showOpenGames();
        String [] resultArr = result.split("\n");
        tableData = new String[resultArr.length][3];
        for(int i = 0; i < resultArr.length;i++) {
            String [] game = resultArr[i].split(",");
            for(int j = 0; j < game.length; j++)
                tableData[i][j] = game[j];
        }
        /*String data[][] = {
            {"23","ChunkyMitts", "Available"},
            {"651","dxfc", "Available"},
        };*/
        String cols[] = {"Game","Host", "Date started"};
        
        createGame = new JButton("Create Game");
        createGame.setBounds(20, 20, 140, 30);
        createGame.addActionListener(this);
        
        scoreSystem = new JButton("Score System");
        scoreSystem.setBounds(20, 60, 140, 30);
        scoreSystem.addActionListener(this);      
        
        leaderboard = new JButton("Leaderboard");
        leaderboard.setBounds(20, 100, 140, 30);
        leaderboard.addActionListener(this);
        
        loggedInUser = new JLabel(username.getText());
        loggedInUser.setBounds(20, 360, 140, 30);
        
        logout = new JButton("Logout");
        logout.setBounds(20, 390, 140, 30);
        logout.addActionListener(this);
        
        gameTable = new JTable(tableData, cols) {
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
        gameTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        gameTable.setRowSelectionAllowed(true);
        
        gameTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
              if (e.getClickCount() == 2) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                String result = proxy.joinGame(userID, Integer.parseInt(tableData[row][0]));
                if(Integer.parseInt(result) == 1)
                    games.add(new MainGame(proxy, Integer.parseInt(tableData[row][0]), userID, 2));
              }
            }
          });
        
        JScrollPane pane = new JScrollPane(gameTable);
        pane.setBounds(200, 20, 320, 400);
        
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        frame.getContentPane().add(panel);
        panel.add(createGame);
        panel.add(scoreSystem);
        panel.add(leaderboard);
        panel.add(loggedInUser);   
        panel.add(logout);
        panel.add(pane);
       
        frame.setTitle("Tic Tac Toe");
        frame.setSize(560, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void leaderboard() {
        leaderboardTable = new JTable();
        leaderboardTable.setBounds(20, 20, 320, 400);
        
        panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBackground(Color.white);
        frame2.getContentPane().add(panel2);
        panel2.add(leaderboardTable);
         
        frame2.setTitle("Tic Tac Toe - Leaderboard");
        frame2.setSize(400, 300);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setLocationRelativeTo(null);
        frame2.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        //TODO add error check + sql injection
        if (source == login) {
            userID = proxy.login(username.getText(), password.getText());
            //if(userID > 0) {
               // System.out.print("Successful");
                frame.getContentPane().removeAll();
                gameScreen();
            //}
            //else{
              //  errorMessage.setText("Login Unsuccesful");
               // errorMessage.setForeground(Color.RED);
           // }
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
                gameScreen();
            } catch( Exception ex)  {
                errorMessage.setForeground(Color.RED);
                System.out.println(result);
                System.out.println(username.getText());
                setErrorMessage(result);
            }
        }
        else if(source == createGame){
            String result = proxy.newGame(userID);
            try {
                gameID = Integer.parseInt(result);
                System.out.println("success " + gameID);
                games.add(new MainGame(proxy, gameID, userID, 1));
                successLabel.setText("Successfully created game");
            } catch( Exception ex)  {
                errorMessage.setForeground(Color.RED);
                System.out.println(result);
                System.out.println(username.getText());
                setErrorMessage(result);
            }
        }
        else if(source == scoreSystem) {
            frame.getContentPane().removeAll();
            // TODO create game
            scoreSystem.setVisible(true);
            
        }
        else if(source == leaderboard) {
           Leaderboard leaderboard = new Leaderboard(proxy.leagueTable());
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
            
       /*else if(source == createGame) {
            frame.getContentPane().removeAll();
            // TODO create game
        }
        else if(source == scoreSystem) {
            frame.getContentPane().removeAll();
            // TODO create game
        }
        else if(source == leaderboard) {
            frame.getContentPane().removeAll();
            leaderboard();
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
            }*/
}

