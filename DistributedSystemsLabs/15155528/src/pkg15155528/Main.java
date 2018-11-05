/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg15155528;

import lab_9_web_service.Hangman;
import lab_9_web_service.Hangman_Service;
/**
 *
 * @author puser
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       registerUser user = new registerUser();
       user.generateUI();
       
       login loginCheck = new login();
       loginCheck.generateUI();
       
       
       //proxy.registerUser("William", "O'Leary", "willoleary6", "pass", "will@gmail.com");
    
       
    
    }
    
}
