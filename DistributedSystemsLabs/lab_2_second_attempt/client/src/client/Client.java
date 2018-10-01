/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author willo
 */
public class Client extends JFrame implements ActionListener{
    Socket socket;
    private JLabel text, clicked;
    private JButton button;
    private JPanel panel;
    private JTextField textField;
    private PrintWriter out = null;
    private BufferedReader in = null;
    
    public Client(){
        text = new JLabel("Text to send over socket:");
        textField = new JTextField(20);
        button = new JButton("Click Me");
        button.addActionListener(this);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);
        getContentPane().add(panel);
        panel.add("North", text);
        panel.add("Center", textField);
        panel.add("South", button);
        
        this.setTitle("Client Program");
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        Dimension dim =  new Dimension(3000, 1240);
        this.setLocation(dim.width/2-this.getSize().width, dim.height/2-this.getSize().height);
        this.addWindowListener(l);
        this.pack();
        this.setVisible(true);
      
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Client();
    }
    
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        
        if(source == button) {
            try {
                socket = new Socket("localhost",4445);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            String text = textField.getText();
            out.println(text);
            textField.setText(new String(""));
            try{
                String reply = in.readLine();
                System.out.println(reply);
                
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }  
}
