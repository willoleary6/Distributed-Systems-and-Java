/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaprojectinterface;

import TTTWebApplication.TTTWebService;

/**
 *
 * @author Aidan
 */
public class GameStateThread extends Thread{
    TTTWebService proxy;
    
    public GameStateThread(TTTWebService proxy) {
        this.proxy = proxy;
    }
    
    /*public void getState() {
        proxy.
    }
    
    @Override
    public void run() {
        while(true) {
            myLabel.setText(getTime()); 
            
            try {
                sleep(interval);
            } catch(Exception e) {
                
            }
        }
    }*/
}
