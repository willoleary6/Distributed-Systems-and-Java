/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_2_multithreaded_client_server;

/**
 *
 * @author willo
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class client extends JFrame implements ActionListener{
    private JLabel text, clicked;
    private JButton button;
    private JPanel panel;
    private JTextField textField;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String ipAddress;
    private int portNumber;
    
    public client(String ipAddress, int portNumber){
        
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
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
        this.listenSocket();
        
	
    }
    
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if(source == button) {
        //Send data over socket
            String text = textField.getText();
            out.println(text);
            textField.setText(new String(""));
            //Receive text from server
            try {
                String line = in.readLine();
                System.out.println("Text received :" + line);
            } catch (IOException e) {
                System.out.println("Read failed");
                System.exit(1);
            }
        }
    }
    private void closeSocket() {
        //Clean up
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Could not close.");
            System.exit(-1);
        }
    }
    
     public void listenSocket() {
        //Create socket connection
        try {
            socket = new Socket(ipAddress, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: kq6py.eng");
            System.exit(1);
        } catch  (IOException e) {
            System.out.println("No I/O");
            System.exit(1);
        }
    }
}
