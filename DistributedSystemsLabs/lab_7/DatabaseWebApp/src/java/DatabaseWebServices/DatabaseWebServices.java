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

    /**
     * Web service operation
     */
    @WebMethod(operationName = "insertQuery")
    public String insertQuery(@WebParam(name = "tableName") String tableName, @WebParam(name = "columnNames") String columnNames, @WebParam(name = "values") String values) {
        //TODO write your implementation code here:
        return "INSERT INTO "+tableName+" ("+columnNames+") VALUES ("+values+");";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updateTable")
    public String updateTable(@WebParam(name = "tableName") String tableName, @WebParam(name = "columnNames") String columnNames, @WebParam(name = "newValues") String newValues, @WebParam(name = "whereClauseColumnNames") String whereClauseColumnNames, @WebParam(name = "whereClauseValues") String whereClauseValues) {
        //TODO write your implementation code here:
        /*
        UPDATE `flights` 
		SET `flightNumber` = 'AP3213'
        WHERE `flights`.`autoID` = 29;
        */
        String Query = "UPDATE '"+tableName+"' SET ";
        // sorted out the values we want to change
        String [] columnNamesArray = columnNames.split(",");
        String [] valuesArray =  newValues.split(",");
        int loops = valuesArray.length;
        // ensuring  no out of bounds exeptions
        if(columnNamesArray.length < loops){
            loops = columnNamesArray.length;
        }
        for(int i =0; i < loops; i++){
            Query += "'"+columnNamesArray[i]+"' = '"+valuesArray[i]+"' ";
            if(i < loops-1){
               Query +=", "; 
            }
        }
        // sorting where clause
        Query +=" WHERE "; 
        String [] whereClauseColumnNamesArray = whereClauseColumnNames.split(",");
        String [] whereClauseValuesArray =  whereClauseValues.split(",");
        loops = whereClauseValuesArray.length;
        // ensuring  no out of bounds exeptions
        if(whereClauseColumnNamesArray.length < loops){
            loops = whereClauseColumnNamesArray.length;
        }
        for(int i =0; i < loops; i++){
            Query += "'"+tableName+"'.'"+whereClauseColumnNamesArray[i]+"' = '"+whereClauseValuesArray[i]+"' ";
            if(i < loops-1){
               Query +=" AND "; 
            }
        }
        return Query;
    }

}


