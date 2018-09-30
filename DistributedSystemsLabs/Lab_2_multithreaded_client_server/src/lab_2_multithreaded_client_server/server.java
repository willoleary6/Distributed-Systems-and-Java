/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_2_multithreaded_client_server;

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
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author willo
 */
public class server extends JFrame implements ActionListener{
    private JButton button;
    private JLabel label = new JLabel("Text received over socket:");
    private JPanel panel;
    private JTextArea textArea = new JTextArea();
    private ServerSocket server = null;
    private Socket client = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String line;
    private Thread listenerThread;
    private int portNumber;
    
    
    public server(int portNumber){
       this.portNumber = portNumber;
       button = new JButton("Click Me");
       button.addActionListener(this);

       panel = new JPanel();
       panel.setLayout(new BorderLayout());
       panel.setBackground(Color.white);
       getContentPane().add(panel);
       panel.add("North", label);
       panel.add("Center", textArea);
       panel.add("South", button);
       this.setTitle("Server Program");
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                listenerThread.stop();
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
    
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if(source == button) {
            textArea.setText(line);
        }
    }
    
    public void listenSocket() {
        listenerThread = new Thread(new Runnable() {
        @Override
        public void run() {
        // code goes here.
        
        try {
            server = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println("Could not listen on port "+portNumber);
            System.exit(-1);
        }

        try {
            client = server.accept();
        } catch (IOException e) {
            System.out.println("Accept failed: "+portNumber);
            System.exit(-1);
        }

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
            out.println("Sucessful connection");
        } catch (IOException e) {
            System.out.println("Accept failed: "+portNumber);
            System.exit(-1);
        }
 
        while(true) {
            try {
                line = in.readLine();
                int operationSize = 3;
                //Send data back to client
                String [] operation = line.split(",");
                line = "Error";
                if(operation.length == operationSize){
                    try{
                        String answer = "";
                        String operator = operation[0];
                        switch(operator.toUpperCase()){
                            case "ADD":
                                answer = String.valueOf(Double.parseDouble(operation[1])
                                + Double.parseDouble(operation[2]));
                                break;
                            case "SUB":
                                answer = String.valueOf(Double.parseDouble(operation[1])
                                - Double.parseDouble(operation[2]));
                                break;
                            case "MUL":
                                answer = String.valueOf(Double.parseDouble(operation[1])
                                * Double.parseDouble(operation[2]));
                                break;
                            case "DIV":
                                answer = String.valueOf(Double.parseDouble(operation[1])
                                / Double.parseDouble(operation[2]));
                                 break;
                            default:
                                answer = "Error";
                                break;
                            }
                            line = answer;
                    }catch (Exception e) {
                       line = "Error";     
                    } 
                }
                textArea.setText(line);
            } catch (IOException e) {
                System.out.println("Read failed");
                System.exit(-1);
            }
        }
        }
        });  
        listenerThread.start();
    }
    
    protected void finalize() {
        //Clean up
        try {
            in.close();
            out.close();
            server.close();
        } catch (IOException e) {
            System.out.println("Could not close.");
            System.exit(-1);
        }
    }
    
    
}
