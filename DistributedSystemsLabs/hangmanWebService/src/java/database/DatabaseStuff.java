package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;

public class DatabaseStuff {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public DatabaseStuff() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            
            // Setup the connection with the DB
            connect = DriverManager
                .getConnection("jdbc:mysql://localhost/hangman?"
                + "user=root&password=");

            statement = connect.createStatement();
            
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public int checkLogin(String user, String pass) {
        String sql = "SELECT autoID FROM users WHERE username = ? AND password = PASSWORD(?) AND isactive = 1;";
        try {
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();    
            if(resultSet.next() == false) {
                return 0;
            } else {
                String id = resultSet.getString("autoID");
                return Integer.parseInt(id);
            }
        } catch(SQLException s) {
            return -2;
        } catch(Exception i) {
            return -1;
        }
    }
    
    public int register(String name, String surname, String username, String password, String email) {
        try {
            String sql = "INSERT INTO users(name, surname, username, password, email) VALUES(?,?,?,PASSWORD(?),?)";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();
            
            sql = "SELECT autoID FROM users WHERE username = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next() == false) {
                return 0;
            } else {
                return resultSet.getInt("autoID");
            }
        } catch(Exception e) {
            return -1;
        }
    }
    
    public String getWordFromGame(int gid) {
        String new_word = "NO_WORD_FOUND";
        String sql = "SELECT theword FROM games WHERE autoID = ?";
        try {
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, gid);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next() != false) {
                new_word = resultSet.getString("theword");
            }
        } catch(Exception e) {
            return "EXCEPTION_ERROR";
        } 
        return new_word;
    }
    
    public String getRandomWord() {
        String new_word = "";
        String sql = "SELECT theword FROM words WHERE RAND() <= 0.00025 LIMIT 1";
        try {
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next() != false) {
                new_word = resultSet.getString("theword");
            }
        } catch(Exception e) {
            return "EXCEPTION_ERROR";
        } 
        return new_word;
    }
    
    public int addNewGame(int p) {
        int gamestate = 0;
        String newWord = "";
        while(newWord.length() == 0) {
            newWord = getRandomWord().toLowerCase();
        }
            
        try {
            String sql = "INSERT INTO games(p1, gameState, theword) VALUES(?,?,?)";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, p);
            preparedStatement.setInt(2, gamestate);
            preparedStatement.setString(3, newWord);
            preparedStatement.executeUpdate();
            
            sql = "SELECT autoID from games WHERE gameState = ? AND p1 = ? AND theword = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, gamestate);
            preparedStatement.setInt(2, p);
            preparedStatement.setString(3, newWord);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("autoID");
        } catch(Exception e) {
            return -1;
        }
    }
    
    public void guess(String l, int gid, int pid, int correct) {
        try {
            String sql = "INSERT INTO moves(pID, gID, guess, correct) VALUES(?,?,?,?)";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, pid);
            preparedStatement.setInt(2, gid);
            preparedStatement.setString(3, l);
            preparedStatement.setInt(4,correct);
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            
        }
    }
    
    public void resign(int p, int g) {
        try {
            String sql = "UPDATE games SET gameState = ? WHERE autoID = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, p);
            preparedStatement.setInt(2, g);
            preparedStatement.executeUpdate();
        } catch(Exception e) {
            
        }
    }
    
    public int guessesUsed(int g) {
       int guesses = 0;
        try {
            String sql = "SELECT COUNT(*) AS num_guesses FROM moves WHERE correct = 0 AND gID = ?";
            preparedStatement = connect.prepareStatement(sql);
            preparedStatement.setInt(1, g);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next() == false) {
                guesses = 0;
            } else {
                guesses = resultSet.getInt("num_guesses");
            }
        } catch(Exception e) {
            guesses = -1;
        } 
        return guesses;
    }
    
    public void shutdown() {
        try {
            resultSet = null;
            preparedStatement = null;
            statement = null;
            connect.close();
            connect = null;
        } catch(Exception e) {
            
        }
    }
}