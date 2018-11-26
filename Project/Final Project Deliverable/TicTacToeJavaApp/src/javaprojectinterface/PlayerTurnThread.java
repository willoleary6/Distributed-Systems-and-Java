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
public class PlayerTurnThread extends Thread {
    private TTTWebService proxy;
    private int gameID;
    private String previousBoard;
    
   public PlayerTurnThread(TTTWebService proxy, int gameID) {
       this.proxy = proxy;
       this.gameID = gameID;
       previousBoard = proxy.getBoard(gameID);
   }
   
    public String getTurn(String[] boardArr) {
        String secondLastMove = boardArr[boardArr.length - 2];
        secondLastMove = secondLastMove.replace("{", "");
        secondLastMove = secondLastMove.replace("}", "");
        String[] moveArr = secondLastMove.split(",");
        return moveArr[0];
    }
    
    public String[] splitBoard(String board) {
        String [] boardArr = board.split("\n");
        return boardArr;
    }
   
   @Override
    public void run() {
        //TODO put in better check later
        while(Integer.parseInt(proxy.checkWin(gameID)) == 0) {
            String board = proxy.getBoard(gameID);
            if (!(board.matches(previousBoard))) {
                previousBoard = board;
                String[] boardArr = splitBoard(board);
                String pid = getTurn(boardArr);
                //updateBoard(boardArr);
                
            }
        }
    }
    
}
