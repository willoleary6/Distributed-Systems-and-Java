package DatabaseWebServices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class MySQLAccess {
  private Connection connect = null;
  private Statement statement = null;                                                                                                                                    
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;
  
  public void connectToDatabase(){
      try {
          Class.forName("com.mysql.jdbc.Driver");
          // Setup the connection with the DB
          String url       = "jdbc:mysql://localhost/muprhys's_lab";
          String user      = "root";
          String password  = "";
          // create a connection to the database
          connect = DriverManager.getConnection(url, user, password);
      } catch(Exception e) {
          System.out.println("Unable to connect to database");
          System.out.println(e);
      } 
  }
  
  public String selectFromDatabase(String query){
      String result = "";
      try {
        // Statements allow to issue SQL queries to the database
        statement = connect.createStatement();
        // Result set get the result of the SQL query
        preparedStatement = connect
         .prepareStatement(query);
        resultSet = preparedStatement.executeQuery();
      
        result = writeResultSet(resultSet);
      } catch(Exception e) {
          result = e.toString();
          System.out.println("Unable to select from database");
          System.out.println(e);
      }
      return result;
  }
  
  public void insertIntoDatabase(String query, Object [] args){
      try {
        preparedStatement = connect
          .prepareStatement(query);
        /*
        INSERT INTO `flights` (`extendedDataInJSON`) 
	VALUES (NULL);
        */
        setPreparedStatement(args);
        preparedStatement.executeUpdate();
        } catch(Exception e) {
          System.out.println("Unable to insert into database");
          System.out.println(e);
      }
  }
  
  public void updateDatabase(String query, Object [] args){
      try {
        preparedStatement = connect
          .prepareStatement(query);
        setPreparedStatement(args);
        /*
        UPDATE `flights` 
		SET `flightNumber` = 'AP3213'
                WHERE `flights`.`autoID` = 29;
        */
        preparedStatement.executeUpdate();
        } catch(Exception e) {
          System.out.println("Unable to insert into database");
          System.out.println(e);
      }
  }
  
  public void setPreparedStatement(Object [] args){
     try{
      int i = 1;
        for (Object arg : args) {         
             if (arg instanceof Date) {
                preparedStatement.setDate(i++, (java.sql.Date) arg);
            } else if (arg instanceof Integer) {
                preparedStatement.setInt(i++, (Integer) arg);
            } else if (arg instanceof Long) {
                preparedStatement.setLong(i++, (Long) arg);
            } else if (arg instanceof Double) {
                preparedStatement.setDouble(i++, (Double) arg);
            } else if (arg instanceof Float) {
                preparedStatement.setFloat(i++, (Float) arg);
            } else {
                preparedStatement.setString(i++, (String) arg);
            }
        }
     } catch(Exception e){
        System.out.println("Unable to set statement");
        System.out.println(e);
     }  
  }
  public void deleteFromDatabase(String query, Object [] args){
      try {
        preparedStatement = connect
        .prepareStatement(query);
        setPreparedStatement(args);
        /*
        DELETE FROM `users` WHERE `users`.`autoID` = 1"
        */
        preparedStatement.executeUpdate();
      
      } catch(Exception e) {
          System.out.println("Unable to delete from database");
          System.out.println(e);
      }
  }

private void writeMetaData(ResultSet resultSet) throws SQLException {
    //   Now get some metadata from the database
    // Result set get the result of the SQL query
    
    System.out.println("The columns in the table are: ");
    
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private String writeResultSet(ResultSet resultSet) throws SQLException {
    String value = "";
    while (resultSet.next()) {
      value += resultSet.getString("username")+"\n";
    }
    return  value;
  }

  // You need to close the resultSet
  public void close() {
    try {
      if (resultSet != null) {
        resultSet.close();
      }

      if (statement != null) {
        statement.close();
      }

      if (connect != null) {
        connect.close();
      }
    } catch (Exception e) {

    }
  }

} 
