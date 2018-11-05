/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangman;

import database.DatabaseStuff;
import java.sql.SQLException;
import javax.jws.Oneway;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author puser
 */
@WebService(serviceName = "hangman")
public class Hangman {

     /**
     * Web service operation
     */
    @WebMethod(operationName = "checkLogin")
    public int checkLogin(@WebParam(name = "uname") String uname, @WebParam(name = "pword") String pword) {
        int id = 0;
        DatabaseStuff h = new DatabaseStuff();
        id = h.checkLogin(uname, pword);
        h.shutdown();
        return id;
    }

    /**
     * Web service operation
     */
    public String getRandomWord() {
        DatabaseStuff h = new DatabaseStuff();
        String myWord = h.getRandomWord();
        h.shutdown();
        return myWord;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "newGame")
    public int newGame(@WebParam(name = "p1") int p1) {
        int gameId = 0;
        DatabaseStuff h = new DatabaseStuff();
        gameId = h.addNewGame(p1);
        h.shutdown();
        return gameId;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "registerUser")
    public int registerUser(@WebParam(name = "name") String name, @WebParam(name = "surname") String surname, @WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "email") String email) {
        int uid = 0;
        DatabaseStuff h = new DatabaseStuff();
        uid = h.register(name, surname, username, password, email);
        h.shutdown();
        return uid;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getWord")
    public String getWord(@WebParam(name = "gid") int gid) {
        String myword = "";
        DatabaseStuff h = new DatabaseStuff();
        myword = h.getWordFromGame(gid);
        h.shutdown();
        return myword;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addGuess")
    public String addGuess(@WebParam(name = "letter") String letter, @WebParam(name = "player") int player, @WebParam(name = "game") int game) {
        DatabaseStuff h = new DatabaseStuff();
        int correct = 0;
        String myword = h.getWordFromGame(game);
        String[] bits = myword .split("");
        String result = "";
        for(int i=0;i<bits.length;i++) {
            if(bits[i].equals(letter) == true) {
                result += i + ",";
            }
        }
        if(result.length() > 0) {
            correct = 1;
            result = result.substring(0,result.length()-1);
        }
        
        h.guess(letter, game, player, correct);
        h.shutdown();
        return result;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "guessesUsed")
    public int guessesUsed(@WebParam(name = "gID") int gID) {
        DatabaseStuff h = new DatabaseStuff();
        int guesses = h.guessesUsed(gID);
        h.shutdown();
        return guesses;
    }
    
    
}
