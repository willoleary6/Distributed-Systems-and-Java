/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectinterface;

import TTTWebApplication.TTTWebService;
import TTTWebApplication.TTTWebService_Service;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author aaron
 */
public class MainMenu extends javax.swing.JFrame {

    private final Runnable updateGamesThread;
    private ArrayList<MainGame> games = new ArrayList<MainGame>();
    private int userID;
    private String []previousOpenTableData, previousMyOpenTableData, previousMyTableData;
    private String [][]tableOpenDataRef, tableMyOpenDataRef;
    private String username;
    TTTWebService_Service link;
    TTTWebService proxy;
    
    public MainMenu(TTTWebService proxy, int userID, String username) {
              
        updateGamesThread = new Runnable() {
            /*
            * Thread which check for a change in each table and if one is 
            * found it call the relavent update method
            */
            public void run() {
                while(true) {
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch( Exception e){
                        System.out.println(e);
                    }
                    
                    String newOpenGames = proxy.showOpenGames();
                    String [] newOpenGamesArr = newOpenGames.split("\n");
                    newOpenGamesArr = removeUserID(newOpenGamesArr);
                    
                    String newMyOpenGames = proxy.showMyOpenGames(userID);
                    String [] newMyOpenGamesArr = newMyOpenGames.split("\n");
                    
                    String newMyGames = proxy.showAllMyGames(userID);
                    String [] newMyGamesArr = newMyGames.split("\n");
                    
                    if(!(previousOpenTableData.equals(newOpenGamesArr))) {
                        populateOpenScrollPane(newOpenGamesArr);
                    }
                    if(!(previousMyOpenTableData.equals(newMyOpenGamesArr))) {
                        populateMyOpenGamesScrollPane(newMyOpenGamesArr);
                    }
                    if(!(previousMyTableData.equals(newMyGamesArr))) {
                        populateMyGamesScrollPane(newMyGamesArr);
                    }
                }  
            }
        };
        
        initComponents();
        this.proxy = proxy;
        this.userID = userID;
        this.username = username;
        
        // block of code to populate jtables on initialization
        String result = proxy.showOpenGames();
        String []resultArr = result.split("\n");
        resultArr = removeUserID(resultArr);
        previousOpenTableData = resultArr;
        populateOpenScrollPane(resultArr);
        
        result = proxy.showMyOpenGames(this.userID);
        resultArr = result.split("\n");
        previousMyOpenTableData = resultArr;
        populateMyOpenGamesScrollPane(resultArr);
        
        result = proxy.showAllMyGames(this.userID);
        resultArr = result.split("\n");
        previousMyTableData = resultArr;
        populateMyGamesScrollPane(resultArr);
        
        
        loggedInUser.setText(username);        
        
        
        jTable1.addMouseListener(new MouseAdapter() {
            /*
            * mouse listen which listens on the jtable if a cell recieves a 
            * double click it gets the cell num and starts a game using the id
            */
            public void mouseClicked(MouseEvent e) {
              //check for double click
              if (e.getClickCount() == 2) {
                JTable target = (JTable)e.getSource();
                //get source of click
                int row = target.getSelectedRow();
                //join game
                String result = proxy.joinGame(userID, 
                        Integer.parseInt(tableOpenDataRef[row][0]));
                if(Integer.parseInt(result) == 1)
                    //create game Window as P2
                    games.add(new MainGame(proxy, 
                            Integer.parseInt(tableOpenDataRef[row][0]), 
                            userID, 2));
              }
            }
          });
        
        jTable2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
              if (e.getClickCount() == 2) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                games.add(new MainGame(proxy, 
                        Integer.parseInt(tableMyOpenDataRef[row][0]), 
                        userID, 1));
              }
            }
          });
        
        
        jTable1.setDefaultEditor(Object.class, null);
        jTable2.setDefaultEditor(Object.class, null);
        jTable3.setDefaultEditor(Object.class, null);
       
        
        new Thread(updateGamesThread).start();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
      
    }
    
   
    public void populateOpenScrollPane(String []unsplitTableData) {
        /*
        * method which actually populate the jtables by getting the table model
        * and adding rows if theres a change in the data the rows are removed 
        * and re-added
        */
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        //check for a change in jtable data vs web service data
        if(previousOpenTableData.equals(unsplitTableData)) {
            String [][]tableData = new String[unsplitTableData.length][3];
            for(int i = 0; i < unsplitTableData.length;i++) {
                String [] game = unsplitTableData[i].split(",");
            for(int j = 0; j < game.length; j++)
                tableData[i][j] = game[j];
        }
            // assign table data to global for later reference
            tableOpenDataRef = tableData;
            for(int i = 0; i < tableData.length; i++) {
                if(!tableData[i][1].matches(username))
                    //add rows
                    model.addRow(new Object[]{tableData[i][0], tableData[i][1],
                        tableData[i][2]});       
            }
        } else {
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                // remove rows
                model.removeRow(i);
            }
            String [][]tableData = new String[unsplitTableData.length][3];
            for(int i = 0; i < unsplitTableData.length;i++) {
                String [] game = unsplitTableData[i].split(",");
            for(int j = 0; j < game.length; j++)
                tableData[i][j] = game[j];
        }
            // assign table data to global for later reference
            tableOpenDataRef = tableData;
            for(int i = 0; i < tableData.length; i++) {
                if(!tableData[i][1].matches(username))
                    model.addRow(new Object[]{tableData[i][0], tableData[i][1],
                        tableData[i][2]});       
            }
            previousOpenTableData = unsplitTableData;
            this.revalidate();
            this.repaint();
        }
    }
    
    public void populateMyOpenGamesScrollPane(String []unsplitTableData) {
         /*
        * method which actually populate the jtables by getting the table model
        * and adding rows if theres a change in the data the rows are removed 
        * and re-added
        */
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        
        if(previousMyOpenTableData.equals(unsplitTableData)) {
            String [][]tableData = new String[unsplitTableData.length][3];
            for(int i = 0; i < unsplitTableData.length;i++) {
                String [] game = unsplitTableData[i].split(",");
            for(int j = 0; j < game.length; j++)
                tableData[i][j] = game[j];
        }
            tableMyOpenDataRef = tableData;
            for(int i = 0; i < tableData.length; i++) {
                model.addRow(new Object[]{tableData[i][0], tableData[i][1],
                    tableData[i][2]});       
            }
        } else {
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            String [][]tableData = new String[unsplitTableData.length][3];
            for(int i = 0; i < unsplitTableData.length;i++) {
                String [] game = unsplitTableData[i].split(",");
            for(int j = 0; j < game.length; j++)
                tableData[i][j] = game[j];
        }
            tableMyOpenDataRef = tableData;
            for(int i = 0; i < tableData.length; i++) {
                model.addRow(new Object[]{tableData[i][0], tableData[i][1],
                    tableData[i][2]});       
            }
            previousMyOpenTableData = unsplitTableData;
            this.revalidate();
            this.repaint();
        }
    }
    
    public void populateMyGamesScrollPane(String []unsplitTableData) {
        /*
        * method which actually populate the jtables by getting the table model
        * and adding rows if theres a change in the data the rows are removed 
        * and re-added
        */
        
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        
        if(previousMyTableData.equals(unsplitTableData)) {
            String [][]tableData = new String[unsplitTableData.length][4];
            for(int i = 0; i < unsplitTableData.length;i++) {
                String [] game = unsplitTableData[i].split(",");
            for(int j = 0; j < game.length; j++)
                tableData[i][j] = game[j];
        }
            for(int i = 0; i < tableData.length; i++) {
                model.addRow(new Object[]{tableData[i][0], tableData[i][1],
                    tableData[i][2], tableData[i][3]});       
            }
        } else {
            int rowCount = model.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }
            String [][]tableData = new String[unsplitTableData.length][4];
            for(int i = 0; i < unsplitTableData.length;i++) {
                String [] game = unsplitTableData[i].split(",");
            for(int j = 0; j < game.length; j++)
                tableData[i][j] = game[j];
        }
            for(int i = 0; i < tableData.length; i++) {
                model.addRow(new Object[]{tableData[i][0], tableData[i][1],
                    tableData[i][2], tableData[i][3]});       
            }
            previousMyTableData = unsplitTableData;
            this.revalidate();
            this.repaint();
        }
    }
    
    private String[] removeUserID(String []unsplitTableData) {
        /*
        * Method which removes open games that correspond to the logged in user
        * to be used in the games which he can join table
        */
        int count = 0;
        for(int i =0; i < unsplitTableData.length; i++) {
            if(!(unsplitTableData[i].contains(username)))
                count++;
        }
        String[] newData = new String[count];
        count = 0;
        for(int i =0; i < unsplitTableData.length; i++) {
            if(!(unsplitTableData[i].contains(username))){ 
                newData[count] = unsplitTableData[i];
                count++;
            }
        }
        return newData;
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


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        logoutButton = new javax.swing.JButton();
        createGameButton = new javax.swing.JButton();
        scoreSystemButton = new javax.swing.JButton();
        leaderboardButton = new javax.swing.JButton();
        loggedInUser = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        errorMessage = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1300, 500));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tic Tac Toe");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "gameID", "username", "started"
            }
        ));
        jTable1.setPreferredSize(new java.awt.Dimension(320, 400));
        jScrollPane1.setViewportView(jTable1);

        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        createGameButton.setText("Create Game");
        createGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createGameButtonActionPerformed(evt);
            }
        });

        scoreSystemButton.setText("Player Stats");
        scoreSystemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scoreSystemButtonActionPerformed(evt);
            }
        });

        leaderboardButton.setText("Leaderboard");
        leaderboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaderboardButtonActionPerformed(evt);
            }
        });

        loggedInUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loggedInUser.setText("jLabel2");

        jScrollPane2.setPreferredSize(new java.awt.Dimension(400, 2000));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "gameID", "username", "Started"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setText("Joinable Games");

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setText("Your Open Games");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "GameID", "P1 Username", "P2 Username", "Started"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setText("All your Games");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(logoutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(createGameButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(scoreSystemButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(leaderboardButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(loggedInUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(errorMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(14, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(jLabel2)
                        .addGap(179, 179, 179)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(159, 159, 159))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(createGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scoreSystemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(leaderboardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(errorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loggedInUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void scoreSystemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scoreSystemButtonActionPerformed
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
    }//GEN-LAST:event_scoreSystemButtonActionPerformed

    private void leaderboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaderboardButtonActionPerformed
       Leaderboard leaderboard = new Leaderboard(proxy, proxy.leagueTable());
           leaderboard.setVisible(true);
    }//GEN-LAST:event_leaderboardButtonActionPerformed

    private void createGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createGameButtonActionPerformed
        String result = proxy.newGame(userID);
            try {
                int gameID = Integer.parseInt(result);
                System.out.println("success " + gameID);
                games.add(new MainGame(proxy, gameID, userID, 1));
            } catch( Exception ex)  {
                errorMessage.setForeground(Color.RED);
                setErrorMessage(result);
            }
    }//GEN-LAST:event_createGameButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createGameButton;
    private javax.swing.JLabel errorMessage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JButton leaderboardButton;
    private javax.swing.JLabel loggedInUser;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton scoreSystemButton;
    // End of variables declaration//GEN-END:variables
}
