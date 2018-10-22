/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webService;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author puser
 */
@WebService(serviceName = "webService")
public class webService {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "add2Numbers")
    public double add2Numbers(@WebParam(name = "x") int x, @WebParam(name = "y") int y) {
        //TODO write your implementation code here:
        return (x+y);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "doMaths")
    public double doMaths(@WebParam(name = "op") String op, @WebParam(name = "x") int x, @WebParam(name = "y") final int y) {
        //TODO write your implementation code here:
        switch (op) {
            case "ADD":
                return (x+y);
            case "SUB":
                return (x-y);
            case "MUL":
                return (x*y);
            case "DIV":   
                return (x/y);
            default:
                return 0;
        }
        
    }
}
