/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_2_multithreaded_client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author willo
 */
public class serverConnection extends Thread {
    private ServerSocket server;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private boolean shouldRun = true;
    private JTextArea textArea;
    private Socket client = null;
    
    public serverConnection(ServerSocket server, JTextArea textArea){
        super("ServerConnectionThread");
        this.textArea = textArea;
        this.server = server;
    }
   
    @Override
    public void run(){
        try{
            client = server.accept();
            while(true){
                try {
                    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    out = new PrintWriter(client.getOutputStream(), true);
                    out.println("Sucessful connection");
                } catch (IOException e) {
                    System.out.println("Accept failed: ");
                    System.exit(-1);
                }
                String line = null;
                while(shouldRun){
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
                    System.out.println(line);
                    textArea.setText(line);
                }
                in.close();
                out.close();
                client.close();
            }
            
            
    
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
