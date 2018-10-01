/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_3_jdbc;

/**
 *
 * @author puser
 */
public class Lab_3_jdbc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MySQLAccess sql = new MySQLAccess();
        try{
            sql.connectToDatabase();
            sql.selectFromDatabase("select * from FEEDBACK.COMMENTS");
            Object [] values = {"Test", "TestEmail", "TestWebpage","2009/12/11","TestSummary", "TestComment"};
            sql.insertIntoDatabase("insert into  FEEDBACK.COMMENTS values (default, ?, ?, ?, ? , ?, ?)", values);
            sql.selectFromDatabase("SELECT myuser, webpage, datum, summery, COMMENTS from FEEDBACK.COMMENTS");
            // updateDatabase("U");
            Object [] deleteValues = {"test"};
            sql.deleteFromDatabase("delete from FEEDBACK.COMMENTS where myuser= ? ; ", deleteValues);
            sql.selectFromDatabase("select * from FEEDBACK.COMMENTS");
        }catch(Exception e) {
            System.out.println(e);
        }finally {
            sql.close();
        }
    }
}
