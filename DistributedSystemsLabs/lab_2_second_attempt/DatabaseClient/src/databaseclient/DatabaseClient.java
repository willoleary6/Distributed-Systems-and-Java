/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseClient;
import databasewebservices.DatabaseWebServices;
import databasewebservices.DatabaseWebServices_Service;
/**
 *
 * @author puser
 */
public class DatabaseClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DatabaseWebServices proxy;
        DatabaseWebServices_Service link = new DatabaseWebServices_Service();
        
        proxy = link.getDatabaseWebServicesPort();
        
        System.out.println(proxy.selectQuery("*","users"));
        System.out.println(proxy.insertQuery("users","username,password","ab,password"));
    }
    
}
