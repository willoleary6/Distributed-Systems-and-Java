/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author willo
 */
public class ServerConnection extends Thread {
    private static Socket socket;
    private Server server;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private boolean shouldRun = true;
    private JTextArea textArea;
    
    public ServerConnection(Socket socket, Server server, JTextArea textArea) {
        super("ServerConnectionThread");
        this.socket = socket;
        this.server = server;
        this.textArea = textArea;
    }
    
    public void sendStringToClient(String message){
        out.println(message);
    }
    
    public void sendStringToAllClients(String message){
        for(int i = 0; i < server.connections.size(); i++){
            ServerConnection sc = server.connections.get(i);
            sc.sendStringToClient(message);
        }
    }
    
    public void run(){
        try {
            try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Sucessful connection");
                } catch (IOException e) {
                    System.out.println("Accept failed: ");
                    System.exit(-1);
                }
            while(shouldRun){
                String line = in.readLine();
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
                textArea.setText(textArea.getText()+"\n"+line);
                System.out.println(textArea.getText());
                sendStringToAllClients(line+" received");
            }
            in.close();
            out.close();
            socket.close();
           
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
