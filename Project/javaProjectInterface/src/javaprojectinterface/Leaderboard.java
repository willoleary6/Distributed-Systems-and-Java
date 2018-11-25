/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectinterface;

import TTTWebApplication.TTTWebService;
import javax.swing.table.*;
// thread for leaderboard


/**
 *
 * @author Aaron
 */
public class Leaderboard extends javax.swing.JFrame {
    private String leaderTable, newLeaderTable;
    private String[] results;
    private String[][] finalLeagueTable;
    private DefaultTableModel model;
    public final Runnable refreshLeaderboard;
    
    public Leaderboard(TTTWebService proxy,String leagueTable) {
        refreshLeaderboard = new Runnable() {
            public void run() {
                while(proxy.leagueTable() != null && !proxy.leagueTable().equals("ERROR-NOGAMES")) {
                    //System.out.print("\nThread running");
                    
                    newLeaderTable = proxy.leagueTable();
                    System.out.println(leaderTable);
                    System.out.println("===============================");
                    System.out.print("\n" + newLeaderTable);
                    
                    leaderTable ="";
                    
                    if (!newLeaderTable.equals(leaderTable)) {
                        leaderTable = newLeaderTable;
                        System.out.print(", In if statement");
                        results = newLeaderTable.split("\n");
                        finalLeagueTable = new String [results.length][];

                        model = new DefaultTableModel();
                        model = (DefaultTableModel)leaderboardTable.getModel();
                        model.setRowCount(0);

                        for (int i = 0; i < results.length; i++) {
                            System.out.print(", loop" + i);
                            String[] game = results[i].split(",");
                            finalLeagueTable[i] = game;

                            //if (!model.equals(finalLeagueTable[i]))
                                model.addRow(finalLeagueTable[i]);
                        }

                        /*try {
                            Thread.currentThread().sleep(5000);
                        } catch (Exception ex) {
                            System.out.print(ex);
                        }*/
                        //leaderboardTable.setVisible(false);
                        //leaderboardTable.setVisible(true);
                    }
                    
                    //leaderTable = newLeaderTable;
                    System.out.print(", Repeat");
                    
                    try {
                        Thread.currentThread().sleep(5000);
                    } catch (Exception ex) {
                        System.out.print(ex);
                    }
                }
            }
        };
        
        initComponents();
        /*leaderTable = leagueTable;
        // System.out.println(leaderTable);
        
        results = leaderTable.split("\n");
        finalLeagueTable = new String [results.length][];
        
        model = new DefaultTableModel();
        model = (DefaultTableModel)leaderboardTable.getModel();

        for (int i = 0; i < results.length; i++) {
            String[] game = results[i].split(",");
            
            finalLeagueTable[i] = game;
            model.addRow(finalLeagueTable[i]);
        }*/
        startLeaderboardThread();
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
                "Game ID", "Player 1 ", "Player 2 ", "Game Status", "Date "
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
            leaderboardTable.getColumnModel().getColumn(0).setMinWidth(25);
            leaderboardTable.getColumnModel().getColumn(0).setPreferredWidth(25);
            leaderboardTable.getColumnModel().getColumn(1).setMinWidth(75);
            leaderboardTable.getColumnModel().getColumn(1).setPreferredWidth(75);
            leaderboardTable.getColumnModel().getColumn(2).setMinWidth(75);
            leaderboardTable.getColumnModel().getColumn(2).setPreferredWidth(75);
            leaderboardTable.getColumnModel().getColumn(3).setMinWidth(30);
            leaderboardTable.getColumnModel().getColumn(3).setPreferredWidth(30);
            leaderboardTable.getColumnModel().getColumn(4).setMinWidth(100);
            leaderboardTable.getColumnModel().getColumn(4).setPreferredWidth(100);
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
                        .addGap(0, 493, Short.MAX_VALUE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
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