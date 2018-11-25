/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectinterface;

/**
 *
 * @author aaron
 */
public class ScoreSystem extends javax.swing.JFrame {
    /**
     * Creates new form ScoreSystem
     * @param leagueTable
     * @param user
     */
    public ScoreSystem(String leagueTable, String user) {
        initComponents();
        int[] stats;
        //int g, w, l, d, wgr;
        //System.out.print(user);
        playerName.setText(user);
        stats = calculatePlayerStats(leagueTable);
        System.out.printf("%s, %s, %s", stats[0], stats[1], stats[2]);
        
        numberOfGames.setText(numberOfGames.getText() + "\t " + "16");
        numberOfWins.setText(numberOfWins.getText() + "\t " + "9");
        numberOfDraws.setText(numberOfDraws.getText() + "\t " + "1");
        numberOfLosses.setText(numberOfLosses.getText() + "\t " + "6");
        playerWinGameRatio.setText(playerWinGameRatio.getText() + "\t " + "0.56");
    }
    
    public int[]  calculatePlayerStats(String leagueTable) {
        int wins = 0, draws = 0, losses = 0, status;
        int[] stats = {wins, draws, losses};    
            //leagueTable =  $this->interface->getLeagueTable();
            //$gameDetails =  explode("\n",$leagueTables);
        String[] results = leagueTable.split("\n");
        String[][] finalLeagueTable = new String [results.length][];
        
        for (String result : results) {
            String[] game = result.split(",");
            status = Integer.parseInt(game[3]);
            if(game[1].equals(playerName.getText())) {
                switch (status) {
                    case 1:     wins++;     break;
                    case 2:     losses++;   break;
                    case 3:     draws++;    break;
                    default:                        break;
                }
            }
            else if (game[2].equals(playerName.getText())) {
                switch (status) {
                    case 1:     losses++;     break;
                    case 2:     wins++;   break;
                    case 3:     draws++;    break;
                    default:                        break;
                }
            }
        }
         return stats;
    }
         

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        playerLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        numberOfGames = new javax.swing.JLabel();
        numberOfDraws = new javax.swing.JLabel();
        numberOfWins = new javax.swing.JLabel();
        numberOfLosses = new javax.swing.JLabel();
        playerName = new javax.swing.JLabel();
        playerWinGameRatio = new javax.swing.JLabel();

        playerLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe - Score System");

        closeButton.setText("Close");
        closeButton.setPreferredSize(new java.awt.Dimension(75, 30));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Player Stats");

        numberOfGames.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        numberOfGames.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberOfGames.setText("Games:");

        numberOfDraws.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        numberOfDraws.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberOfDraws.setText("Draws:");

        numberOfWins.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        numberOfWins.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberOfWins.setText("Wins:");

        numberOfLosses.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        numberOfLosses.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberOfLosses.setText("Losses:");

        playerName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        playerName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playerName.setText("Name");

        playerWinGameRatio.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        playerWinGameRatio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playerWinGameRatio.setText("Win/Game Ratio:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(playerWinGameRatio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numberOfLosses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numberOfDraws, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numberOfGames, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(playerName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numberOfWins, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playerName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(numberOfGames)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(numberOfWins)
                .addGap(1, 1, 1)
                .addComponent(numberOfDraws, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(numberOfLosses, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(playerWinGameRatio, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        setVisible(false);
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
            java.util.logging.Logger.getLogger(ScoreSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ScoreSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ScoreSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ScoreSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new ScoreSystem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel numberOfDraws;
    private javax.swing.JLabel numberOfGames;
    private javax.swing.JLabel numberOfLosses;
    private javax.swing.JLabel numberOfWins;
    private javax.swing.JLabel playerLabel;
    private javax.swing.JLabel playerName;
    private javax.swing.JLabel playerWinGameRatio;
    // End of variables declaration//GEN-END:variables
}
