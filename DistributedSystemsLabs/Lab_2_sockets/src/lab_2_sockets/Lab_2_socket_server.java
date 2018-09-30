/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lab_2_sockets;

/**
 *
 * @author james.murphy
 */

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.*;

public class Lab_2_socket_server extends JFrame implements ActionListener {
    private JButton button;
    private JLabel label = new JLabel("Text received over socket:");
    private JPanel panel;
    private JTextArea textArea = new JTextArea();
    private ServerSocket server = null;
    private Socket client = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private String line;

    public Lab_2_socket_server() { //Begin Constructor
        button = new JButton("Click Me");
        button.addActionListener(this);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);
        getContentPane().add(panel);
        panel.add("North", label);
        panel.add("Center", textArea);
        panel.add("South", button);

    } //End Constructor

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if(source == button) {
            textArea.setText(line);
        }
    }

    public void listenSocket() {
        try {
            server = new ServerSocket(4444);
        } catch (IOException e) {
            System.out.println("Could not listen on port 4444");
            System.exit(-1);
        }

        try {
            client = server.accept();
        } catch (IOException e) {
            System.out.println("Accept failed: 4444");
            System.exit(-1);
        }

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Accept failed: 4444");
            System.exit(-1);
        }
 
        while(true) {
            try {
                line = in.readLine();
                int operationSize = 3;
                //Send data back to client
                String [] operation = line.split(",");
                if(operation.length == operationSize){
                    boolean allOperandsNumeric = true;
                    for(int i = 1; i < operation.length; i++){
                        try{
                            int test = Integer.parseInt(operation[i]);
                        }catch(NumberFormatException nfe){
                            allOperandsNumeric = false;
                        }
                    }
                    if(allOperandsNumeric){
                        // NOW CHECKING operators
                        String [] operators = {"ADD","SUB","MUL","DIV"};
                        boolean validOperator = false;
                        for(int i =0; i < operators.length; i++){
                            if(operation[0].toUpperCase().equals(operators[i])){
                                validOperator = true;
                            }
                        }
                        if(validOperator){
                            
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
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Read failed");
                System.exit(-1);
            }
        }
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

    public static void main(String[] args) {
        Lab_2_socket_server frame = new Lab_2_socket_server();
	frame.setTitle("Server Program");
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(l);
        frame.pack();
        frame.setVisible(true);
	frame.listenSocket();
    }
}