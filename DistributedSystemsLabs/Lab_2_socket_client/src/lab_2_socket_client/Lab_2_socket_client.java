/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lab_2_socket_client;

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

class lab_2_socket_client extends JFrame implements ActionListener {
    private JLabel text, clicked;
    private JButton button;
    private JPanel panel;
    private JTextField textField;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public lab_2_socket_client() { //Begin Constructor
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
    } //End Constructor

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

    public void listenSocket() {
        //Create socket connection
        try {
            socket = new Socket("192.168.1.53", 4444);
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

    public static void main(String[] args) {
        lab_2_socket_client frame = new lab_2_socket_client();
	frame.setTitle("Client Program");
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