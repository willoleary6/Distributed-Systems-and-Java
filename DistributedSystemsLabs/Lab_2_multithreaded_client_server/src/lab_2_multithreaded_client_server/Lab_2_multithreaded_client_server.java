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
public class Lab_2_multithreaded_client_server  {

    public static void main(String[] args) {
        String ipAddress = "192.168.1.53";
        int port = 4444;
        // TODO code application logic here
        server serverOnj = new server(port);
        client clientObj = new client(ipAddress, port);
        client clientObj2 = new client(ipAddress, port);
        client clientObj3 = new client(ipAddress, port);
    }
    
}
