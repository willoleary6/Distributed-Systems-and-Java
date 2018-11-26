/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectinterface;

import TTTWebApplication.TTTWebService;
import java.math.*;
import java.util.Arrays;
import javax.swing.table.*;

/**
 *
 * @author Aaron
 */
public class Leaderboard extends javax.swing.JFrame {
    private String leaderTable, newLeaderTable, players, table;
    private String[] results, listOfPlayers;
    private String[][] finalLeagueTable;
    private int games, wins, draws, losses;
    private int winGameRatio;
    private int[] playerStats, stats;
    private DefaultTableModel model;
    public final Runnable refreshLeaderboard;
    
    public Leaderboard(TTTWebService proxy,String leagueTable) {
        
        /*
        *    Thread that updates the player leaderboard as 
        *    games are played. Each players game statistics are refreshed. 
        */
        
        refreshLeaderboard = new Runnable() {
            public void run() {
                while(proxy.leagueTable() != null && !proxy.leagueTable().equals("ERROR-NOGAMES")) {
                    newLeaderTable = proxy.leagueTable();                   
                    leaderTable ="";
                    
                    if (!newLeaderTable.equals(leaderTable)) {
                        leaderTable = newLeaderTable;
                        System.out.println(leaderTable);
                        System.out.println("-----------------------------------------------------");
                        System.out.println(newLeaderTable);
                        System.out.println("-----------------------------------------------------");
                        
                        results = newLeaderTable.split("\n");
                        for (String result : results) {
                            String[] game = result.split(",");
                            players += game[1] + ","; 
                        }
                        
                        listOfPlayers = players.split(",");
                        if (listOfPlayers[0].contains("null")) {
                            String replace = listOfPlayers[0].replace("null", "");
                            listOfPlayers[0] = replace;
                        }
                        String[] uniquePlayers = Arrays.stream(listOfPlayers).distinct().toArray(String[]::new);
                                                    
                        for (String p : uniquePlayers) {
                            playerStats = calculatePlayerStats(newLeaderTable, p);
                            games = playerStats[0];
                            wins = playerStats[1];
                            draws = playerStats[2];
                            losses = playerStats[3];
                            winGameRatio = (int)((wins * 100.0f) / games);
                            table += p + "," + games + "," + wins + "," + draws + "," + losses + "," + winGameRatio + "\n";
                        }

                        results = table.split("\n");
                        finalLeagueTable = new String [results.length][];

                        model = new DefaultTableModel();
                        model = (DefaultTableModel)leaderboardTable.getModel();
                        model.setRowCount(0);

                        for (int i = 0; i < results.length; i++) {
                            String[] game = results[i].split(",");
                            finalLeagueTable[i] = game;
                            model.addRow(finalLeagueTable[i]);
                        }
                    }
                    
                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (Exception ex) {
                        System.out.print(ex);
                    }
                }
                        
            }   
        };
        initComponents();
        startLeaderboardThread();
    }
    
    public int[] calculatePlayerStats(String leagueTable, String player) {
        int gameCount = 0, winCount = 0, drawCount = 0, lossCount = 0, status;
        stats = new int[4];
        results = leagueTable.split("\n");
        
        for (String result : results) {
            String[] game = result.split(",");
            status = Integer.parseInt(game[3]);
            
            if (game[1].equals(player) && status != 0) {
                switch (status) {
                    case 1:     gameCount++;    winCount++;     break;
                    case 2:     gameCount++;    lossCount++;    break;
                    case 3:     gameCount++;    drawCount++;    break;
                    default:                                    break;
                }
            }
            else if (game[2].equals(player) && status != 0) {
                switch (status) {
                    case 1:     gameCount++;    lossCount++;    break;
                    case 2:     gameCount++;    winCount++;     break;
                    case 3:     gameCount++;    drawCount++;    break;
                    default:                                    break;
                }
            }            
        }
        stats[0] = gameCount; 
        stats[1] = winCount; 
        stats[2] = drawCount; 
        stats[3] = lossCount;
        
        return stats;
    }
        
    public void startLeaderboardThread() {
        new Thread(this.refreshLeaderboard).start();
    }
                    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        leaderboardTable = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe - Leaderbaord");
        setPreferredSize(new java.awt.Dimension(600, 400));

        leaderboardTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        leaderboardTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Games", "Wins", "Draws", "Losses", "Win/Game Ratio (%)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        leaderboardTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        leaderboardTable.setRowHeight(20);
        leaderboardTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(leaderboardTable);
        if (leaderboardTable.getColumnModel().getColumnCount() > 0) {
            leaderboardTable.getColumnModel().getColumn(0).setMinWidth(75);
            leaderboardTable.getColumnModel().getColumn(0).setPreferredWidth(75);
            leaderboardTable.getColumnModel().getColumn(1).setMinWidth(0);
            leaderboardTable.getColumnModel().getColumn(1).setPreferredWidth(0);
            leaderboardTable.getColumnModel().getColumn(2).setMinWidth(0);
            leaderboardTable.getColumnModel().getColumn(2).setPreferredWidth(0);
            leaderboardTable.getColumnModel().getColumn(3).setMinWidth(0);
            leaderboardTable.getColumnModel().getColumn(3).setPreferredWidth(0);
            leaderboardTable.getColumnModel().getColumn(4).setMinWidth(0);
            leaderboardTable.getColumnModel().getColumn(4).setPreferredWidth(0);
            leaderboardTable.getColumnModel().getColumn(5).setMinWidth(50);
            leaderboardTable.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        closeButton.setText("Close ");
        closeButton.setPreferredSize(new java.awt.Dimension(75, 30));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 543, Short.MAX_VALUE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
       dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Leaderboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Leaderboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Leaderboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Leaderboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Leaderboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable leaderboardTable;
    // End of variables declaration//GEN-END:variables
}
