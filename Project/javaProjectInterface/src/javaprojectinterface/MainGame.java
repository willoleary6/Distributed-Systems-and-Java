/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectinterface;

import TTTWebApplication.TTTWebService;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author Aidan
 */
public class MainGame extends javax.swing.JFrame implements WindowListener {
    private TTTWebService proxy;
    private int gameID;
    private boolean lock;
    private int gameState;
    public final Runnable playerTurn, checkPlayerJoin, checkWinThread;
    private String previousBoard;
    private int userID;
    private int playerNum;
    public boolean terminateThreads;

    
    public MainGame(TTTWebService proxy, int gameID, int userID, int playerNum) {
        
        playerTurn = new Runnable() {
            /*
            * Thread which checks which players turn it is, if its the opposeing
            * players turn then the thread calls a method to update the board
            */
            public void run() {
                while(Integer.parseInt(proxy.getGameState(gameID)) == 0 && !terminateThreads) {
                    try {
                        Thread.currentThread().sleep(500);
                    } catch( Exception e){
                        System.out.println(e);
                    }
                    //System.out.println("Player Turn thread sleep check");
                String board = proxy.getBoard(gameID);
                if(board.matches("ERROR-NOMOVES")) {
                    jLabelPlayer.setText("No move made yet");
                    lock = false;
                }
                else if (!(board.matches(previousBoard))) {
                    previousBoard = board;
                    String[] boardArr = splitBoard(board);
                    if(boardArr.length == 1)
                        startWinThread();
                    getTurn(boardArr);
                    updateBoard(boardArr);
                
                    }
               
                }
            }
        };
        
        checkWinThread = new Runnable() {
            /*
            * Thread which checks if the game has been won and if it has 
            * finished it displays the winner and locks the board
            */
            public void run() {
                String winState = proxy.checkWin(gameID);
                while(Integer.parseInt(winState) == 0 && !terminateThreads) {
                    try {
                   Thread.currentThread().sleep(1000);
                } catch( Exception e){
                    System.out.println(e);
                }
                    System.out.println("win thread sleep check");
                    if(Integer.parseInt(proxy.checkWin(gameID)) != 0) {
                        try {
                            Thread.currentThread().sleep(1000);
                         } catch( Exception e){
                             System.out.println(e);
                         }
                        winState = proxy.checkWin(gameID);
                        switch(winState) {
                            case "1":
                                jLabelPlayer.setText("Player 1 wins");
                                proxy.setGameState(gameID, 1);
                                break;
                            case "2":
                                jLabelPlayer.setText("Player 2 wins");
                                proxy.setGameState(gameID, 2);
                                break;
                            case "3":
                                jLabelPlayer.setText("Draw");
                                proxy.setGameState(gameID, 3);
                                break;
                        }
                        lock = true;
                    }
                }
                System.out.println("thread Finished");
            }
        };
        
        checkPlayerJoin = new Runnable() {
            /*
            * Thread which starts on initialization of the class it checks
            * for a player joining the game then kicks off the game by starting
            * the other threads and unlocking the board
            */
            public void run() {
                while(gameState == -1 && !terminateThreads) {
                    try {
                     Thread.currentThread().sleep(1000);
                  } catch( Exception e){
                      System.out.println(e);
                  }
                    if(Integer.parseInt(proxy.getGameState(gameID)) == 0) {
                        startPlayerThread();
                        lock = false;
                        gameState = 0;
                    }
                }
            }  
          };
        
        this.playerNum = playerNum;
        this.lock = true;
        this.proxy = proxy;
        this.gameID = gameID;
        this.userID = userID;
        
        previousBoard = proxy.getBoard(gameID);
        /*
        * checks if the player is the first or second and sets the game state
        * accordingly
        */ 
        
        if(playerNum == 1) {
            proxy.setGameState(this.gameID, -1);
            gameState = Integer.parseInt(proxy.getGameState(gameID));
            new Thread(this.checkPlayerJoin).start();
        }
        else if(playerNum == 2) {
            proxy.setGameState(this.gameID, 0);
            new Thread(this.playerTurn).start();
            lock = false;
            gameState = 0;
        }
        
        initComponents();
        
        jLabelPlayerNum.setText("You Are Player: " + playerNum);
        /* terminate threads is a boolean which will kill  all threads if
        * the window is disposed */
        terminateThreads = false;
        
        addWindowListener(this);
        this.setTitle("Tic Tac Toe");
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void startPlayerThread() {
        new Thread(this.playerTurn).start();
    }
    
    public void startWinThread(){
        new Thread(this.checkWinThread).start();
    }
    
    public void changeState(int state) {
        proxy.setGameState(this.gameID, state);
    }
    
    public void updateBoard(String[] board) {
        for(int i = 0; i < board.length; i++) {
            String[] move = board[i].split(",");
            if(Integer.parseInt(move[0]) != userID) {
                changeSquare(Integer.parseInt(move[1]),Integer.parseInt(move[2]));
            }
        }
    }  
    
    public void changeSquare(int x, int y) {
        /*
        * Method which changes the relevent square to an x or an o based on the 
        * opponents move
        */
        if(x == 0) {
            switch (y) {
                case 0:
                    jPanel1.remove(jButton1);
                    if(playerNum == 2)
                        jPanel1.add(jLabelX,0,0);
                    else
                    jPanel1.add(jLabelO,0,0);
                    jPanel1.revalidate();
                    break;
                case 1:
                    jPanel1.remove(jButton2);
                    if(playerNum == 2)
                        jPanel1.add(jLabelX1, 0, 1);
                    else 
                    jPanel1.add(jLabelO1,0,1);
                    jPanel1.revalidate();
                    break;
                case 2:
                    jPanel1.remove(jButton3);
                    if(playerNum == 2)
                        jPanel1.add(jLabelX2, 0, 2);
                    else 
                    jPanel1.add(jLabelO2,0,2);
                    jPanel1.revalidate();
                    break;
            }
        } else if(x == 1) {
            switch (y) {
                case 0:
                    jPanel1.remove(jButton4);
                     if(playerNum == 2)
                        jPanel1.add(jLabelX4, 0, 3);
                    else 
                    jPanel1.add(jLabelO3,0,3);
                    jPanel1.revalidate();
                    break;
                case 1:
                    jPanel1.remove(jButton5);
                    if(playerNum == 2)
                        jPanel1.add(jLabelX5, 0, 4);
                    else 
                    jPanel1.add(jLabelO4,0,4);
                    jPanel1.revalidate();
                    break;
                case 2:
                    jPanel1.remove(jButton6);
                    if(playerNum == 2)
                        jPanel1.add(jLabelX3, 0, 5);
                    else 
                    jPanel1.add(jLabelO5,0,5);
                    jPanel1.revalidate();
                    break;
            }           
        } else if(x == 2) {
            switch (y) {
                case 0:
                    jPanel1.remove(jButton7);
                     if(playerNum == 2)
                        jPanel1.add(jLabelX8, 0, 6);
                    else 
                    jPanel1.add(jLabelO6,0,6);
                    jPanel1.revalidate();
                    break;
                case 1:
                    jPanel1.remove(jButton8);
                    if(playerNum == 2)
                        jPanel1.add(jLabelX7, 0, 7);
                    else 
                    jPanel1.add(jLabelO7,0,7);
                    jPanel1.revalidate();
                    break;
                case 2:
                    jPanel1.remove(jButton9);
                    if(playerNum == 2)
                        jPanel1.add(jLabelX6, 0, 8);
                    else 
                    jPanel1.add(jLabelO8,0,8);
                    jPanel1.revalidate();
                    break;
            }
        }
    }
    
    public void getTurn(String[] boardArr) {
        /*
        * gets player turn by checking the second last turn made and if that
        * maatches the current player ID then it unlocks the board
        */
        if(boardArr.length > 1){
            String secondLastMove = boardArr[boardArr.length - 2];
            String[] moveArr = secondLastMove.split(",");
            if(Integer.parseInt(moveArr[0]) == userID) {
                lock = false;
                jLabelPlayer.setText("Your Turn");
            }
            else {
                lock = true;
                jLabelPlayer.setText("Not Your Turn");
            }
                
        } else {
            String[] moveArr = boardArr[0].split(",");
            if(Integer.parseInt(moveArr[0]) != userID) {
                lock = false;
                jLabelPlayer.setText("Your Turn");
            }
            else {
                lock = true;
                jLabelPlayer.setText("Not Your Turn");
            }
        }
        
    }
    
    public String[] splitBoard(String board) {
        String [] boardArr = board.split("\n");
        return boardArr;
    }
    
    private void setErrorMessage (String error) {
        switch(error) {
            case "ERROR-REPEAT":
                errorMsgLabel.setText("Repeated entry");
                break;
            case "ERROR-INSERT":
                errorMsgLabel.setText("Couldnt Add entry");
                break;
            case "ERROR-RETRIEVE":
                errorMsgLabel.setText("couldnt Retrive data");
                break;
            case "ERROR-DB":
                errorMsgLabel.setText("cannot find DB");
                break;
        }
    }
    
    private void checkAndTakeMove(int boardX, int boardY) {
        if(!lock) {
            try {
                if(Integer.parseInt(proxy.checkSquare(boardX, boardY,
                        gameID)) == 0) {
                    if(Integer.parseInt(proxy.takeSquare(boardX, boardY,
                            gameID, userID)) == 0) {
                        lock = true;
                    }
                }
            } catch (Exception e) {
                setErrorMessage(proxy.checkSquare(boardX, boardY, gameID));
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelX = new javax.swing.JLabel();
        jLabelO = new javax.swing.JLabel();
        jLabelX1 = new javax.swing.JLabel();
        jLabelX2 = new javax.swing.JLabel();
        jLabelX3 = new javax.swing.JLabel();
        jLabelX4 = new javax.swing.JLabel();
        jLabelO1 = new javax.swing.JLabel();
        jLabelO2 = new javax.swing.JLabel();
        jLabelO3 = new javax.swing.JLabel();
        jLabelO4 = new javax.swing.JLabel();
        jLabelX5 = new javax.swing.JLabel();
        jLabelX6 = new javax.swing.JLabel();
        jLabelX7 = new javax.swing.JLabel();
        jLabelX8 = new javax.swing.JLabel();
        jLabelX9 = new javax.swing.JLabel();
        jLabelO5 = new javax.swing.JLabel();
        jLabelO6 = new javax.swing.JLabel();
        jLabelO7 = new javax.swing.JLabel();
        jLabelO8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabelPlayer = new javax.swing.JLabel();
        jLabelPlayerNum = new javax.swing.JLabel();
        errorMsgLabel = new javax.swing.JLabel();

        jLabelX.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelX.setText("X");
        jLabelX.setAlignmentX(0.5F);

        jLabelO.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelO.setText("O");

        jLabelX1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelX1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelX1.setText("X");
        jLabelX1.setAlignmentX(0.5F);

        jLabelX2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelX2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelX2.setText("X");
        jLabelX2.setAlignmentX(0.5F);

        jLabelX3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelX3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelX3.setText("X");
        jLabelX3.setAlignmentX(0.5F);

        jLabelX4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelX4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelX4.setText("X");
        jLabelX4.setAlignmentX(0.5F);

        jLabelO1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelO1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelO1.setText("O");

        jLabelO2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelO2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelO2.setText("O");

        jLabelO3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelO3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelO3.setText("O");

        jLabelO4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelO4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelO4.setText("O");

        jLabelX5.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelX5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelX5.setText("X");
        jLabelX5.setAlignmentX(0.5F);

        jLabelX6.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelX6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelX6.setText("X");
        jLabelX6.setAlignmentX(0.5F);

        jLabelX7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelX7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelX7.setText("X");
        jLabelX7.setAlignmentX(0.5F);

        jLabelX8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelX8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelX8.setText("X");
        jLabelX8.setAlignmentX(0.5F);

        jLabelX9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelX9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelX9.setText("X");
        jLabelX9.setAlignmentX(0.5F);

        jLabelO5.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelO5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelO5.setText("O");

        jLabelO6.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelO6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelO6.setText("O");

        jLabelO7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelO7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelO7.setText("O");

        jLabelO8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabelO8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelO8.setText("O");

        jLabel1.setText("jLabel1");

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridLayout(3, 3, 10, 10));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jButton1PropertyChange(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jButton4.setText("jButton4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);

        jButton5.setText("jButton5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);

        jButton6.setText("jButton6");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6);

        jButton7.setText("jButton7");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7);

        jButton8.setText("jButton8");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8);

        jButton9.setText("jButton9");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton9);

        jPanel2.setLayout(new java.awt.GridLayout(1, 2));

        jLabelPlayer.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabelPlayer.setText("waiting on player 2");
        jPanel2.add(jLabelPlayer);

        jLabelPlayerNum.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabelPlayerNum);
        jPanel2.add(errorMsgLabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 49, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /*
    * The Action for each Jbutton is to set an x or o for the logged in user 
    * when a spot is clicked it first checks with the web service then changes the 
    * square
    */
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(gameState == 0 && !lock) {
            checkAndTakeMove(0, 0);
            jPanel1.remove(jButton1);
            if(playerNum == 1)
                jPanel1.add(jLabelX,0,0);
            else
                jPanel1.add(jLabelO,0,0);
            jPanel1.revalidate();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(gameState == 0 && !lock) {
            checkAndTakeMove(0, 1);
            jPanel1.remove(jButton2);
            if(playerNum == 1)
                jPanel1.add(jLabelX1, 0, 1);
            else 
                jPanel1.add(jLabelO1, 0, 1);
            jPanel1.revalidate();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(gameState == 0 && !lock) {
            checkAndTakeMove(0, 2);
            jPanel1.remove(jButton3);
            if(playerNum == 1)
                jPanel1.add(jLabelX2, 0, 2);
            else 
                jPanel1.add(jLabelO2, 0, 2);
            jPanel1.revalidate();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(gameState == 0 && !lock) {
            checkAndTakeMove(1, 2);
            jPanel1.remove(jButton6);
            if(playerNum == 1)
                jPanel1.add(jLabelX3, 0, 5);
            else 
                jPanel1.add(jLabelO3, 0, 5);
            jPanel1.revalidate();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if(gameState == 0 && !lock) {
            checkAndTakeMove(1, 0);
            jPanel1.remove(jButton4);
            if(playerNum == 1)
                jPanel1.add(jLabelX4, 0, 3);
            else 
                jPanel1.add(jLabelO4, 0, 3);
            jPanel1.revalidate();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if(gameState == 0 && !lock) {
            checkAndTakeMove(1, 1);
            jPanel1.remove(jButton5);
            if(playerNum == 1)
                jPanel1.add(jLabelX5, 0, 4);
            else 
                jPanel1.add(jLabelO5, 0, 4);
            jPanel1.revalidate();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(gameState == 0 && !lock) {
            checkAndTakeMove(2, 2);
            jPanel1.remove(jButton9);
            if(playerNum == 1)
                jPanel1.add(jLabelX6, 0, 8);
            else 
                jPanel1.add(jLabelO6, 0, 8);
            jPanel1.revalidate();
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(gameState == 0 && !lock) {
            checkAndTakeMove(2, 1);
            jPanel1.remove(jButton8);
            if(playerNum == 1)
                jPanel1.add(jLabelX7, 0, 7);
            else 
                jPanel1.add(jLabelO7, 0, 7);
            jPanel1.revalidate();
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if(gameState == 0 && !lock) {
            checkAndTakeMove(2, 0);
            jPanel1.remove(jButton7);
            if(playerNum == 1)
                jPanel1.add(jLabelX8, 0, 6);
            else 
                jPanel1.add(jLabelO8, 0, 6);
            jPanel1.revalidate();
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jButton1PropertyChange

    }//GEN-LAST:event_jButton1PropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel errorMsgLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelO;
    private javax.swing.JLabel jLabelO1;
    private javax.swing.JLabel jLabelO2;
    private javax.swing.JLabel jLabelO3;
    private javax.swing.JLabel jLabelO4;
    private javax.swing.JLabel jLabelO5;
    private javax.swing.JLabel jLabelO6;
    private javax.swing.JLabel jLabelO7;
    private javax.swing.JLabel jLabelO8;
    private javax.swing.JLabel jLabelPlayer;
    private javax.swing.JLabel jLabelPlayerNum;
    private javax.swing.JLabel jLabelX;
    private javax.swing.JLabel jLabelX1;
    private javax.swing.JLabel jLabelX2;
    private javax.swing.JLabel jLabelX3;
    private javax.swing.JLabel jLabelX4;
    private javax.swing.JLabel jLabelX5;
    private javax.swing.JLabel jLabelX6;
    private javax.swing.JLabel jLabelX7;
    private javax.swing.JLabel jLabelX8;
    private javax.swing.JLabel jLabelX9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
         terminateThreads = true;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        dispose(); 
    }
}
