/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DatabaseWebServices;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author puser
 */
@WebService(serviceName = "DatabaseWebServices")
public class DatabaseWebServices {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        MySQLAccess sql = new MySQLAccess();
        String result = "Hello " + txt + " !";
        try{
            sql.connectToDatabase();
            result = sql.selectFromDatabase("select * from users");
            // return sql.selectFromDatabase("select * from users");
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            sql.close();
        }
        return result;
    }

    /**
     * Web service operation
     * @param columns
     * @param table
     * @return 
     */
    @WebMethod(operationName = "selectQuery")
    public String selectQuery(@WebParam(name = "columns") String columns, @WebParam(name = "table") String table) {
        MySQLAccess sql = new MySQLAccess();
        String result = ""; 
        try{
            sql.connectToDatabase();
            result = sql.selectFromDatabase("select "+columns+" from "+table);
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            sql.close();
        }
        return result;
    }

}


