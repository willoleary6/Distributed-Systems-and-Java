/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsclient;
import myWebService.WebService;
import myWebService.WebService_Service;
/**
 *
 * @author puser
 */
public class WsClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        WebService proxy;
        WebService_Service link = new WebService_Service();
        
        proxy = link.getWebServicePort();
        
        System.out.println(proxy.hello("Will"));
        
        double answer = proxy.add2Numbers(4, 5);
        for(int i =0; i < answer; i++){
            System.out.println("hi");
        }
        
        System.out.println("result of multipling 4 by 5 is");
        System.out.println(proxy.doMaths("MUL", 4, 5));
    }
    
}
