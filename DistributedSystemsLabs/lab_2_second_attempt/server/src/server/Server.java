/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author willo
 */
public class Server extends JFrame {
    private ServerSocket ss;
    public ArrayList<ServerConnection> connections = new ArrayList<ServerConnection>();
    private boolean shouldRun = true;
    private int portNumber;
    private JLabel label = new JLabel("Text received over socket:");
    private JPanel panel;
    private JTextArea textArea = new JTextArea();
    /**
     * @param args the command line arguments
     */
    public Server(){
        System.out.println("Server establishing");
        portNumber = 4445;
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);
        getContentPane().add(panel);
        panel.add("North", label);
        panel.add("Center", textArea);
        textArea.setText("Waiting for client...");
        textArea.setPreferredSize(new Dimension(100,100));
        
        this.setTitle("Server Program");
         WindowListener l = new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                 System.exit(0);
             }
         };
         Dimension dim =  new Dimension(3000, 600);
         this.setLocation(dim.width/2-this.getSize().width, dim.height/2-this.getSize().height);
         this.setLocationRelativeTo(null); 
         this.addWindowListener(l);
         this.pack();
         this.setVisible(true);
        
        this.listenSocket();
    }
    public static void main(String[] args) {
        new Server();
        // TODO code application logic here
    }
    
    private void listenSocket(){
        try{
            System.out.println("Listening on port "+portNumber);
            ss = new ServerSocket(portNumber);
            while(shouldRun){
                Socket s = ss.accept();
                ServerConnection sc = new ServerConnection(s, this, textArea);
                sc.start();
                connections.add(sc);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
