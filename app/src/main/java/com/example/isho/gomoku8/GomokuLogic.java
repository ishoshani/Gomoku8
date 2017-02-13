package com.example.isho.gomoku8;

import android.util.Log;

/**
 * Created by Jason on 2/12/2017.
 */

public class GomokuLogic {
    static int[][] boardMatrix;
    static int turn;//goes between 1 and -1 two decide between players
    public GomokuLogic(int n) {
        boardMatrix = new int[n][n];
        turn = 1;
    }
    static public int getTurn(){
        return turn;
    }
    static public void placePiece(int i, int j){
        Log.d("gameLogic","placed piece at "+i+" and "+j);

        boardMatrix[i][j]= turn;
        turn *= -1;
    }
    /*
    goes over board to check for a win. return 1 for white win, -1 for black win, return 0 for no win. 3 for draw.
     */
    static public int checkWin(){
        return 0;
    }

}
